package jp.ac.it_college.std.s23018.marvelheroes

import MavelHero
import MavelHeroResponse
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s23018.marvelheroes.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val timestamp = System.currentTimeMillis().toString()

    companion object {
        private const val DEBUG_TAG = "MarvelHeroes"
        private const val HEROS_INFO_URL =
            "https://gateway.marvel.com/v1/public/characters"
        private const val PUBLIC_ID = BuildConfig.publickey
        private const val PRIVATE_ID = BuildConfig.privatekey
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            lvHeroList.apply {
                adapter = HeroListAdapter(Hero.list)
                val manager = LinearLayoutManager(this@MainActivity)
                layoutManager = manager
                addItemDecoration(
                    DividerItemDecoration(this@MainActivity, manager.orientation)
                )
            }
        }
    }

    @UiThread
    fun receiveHeroInfo(item: Hero) {
        lifecycleScope.launch {
            val hash = generateHash(timestamp, PUBLIC_ID, PRIVATE_ID)
            val url = "$HEROS_INFO_URL?ts=$timestamp&apikey=$PUBLIC_ID&hash=$hash&name=${item.q}"
            val hero = heroInfoBackgroundRunner(url) ?: return@launch

            binding.tvHeroName.text = hero.name
            binding.tvHeroDescription.text = hero.description
        }
    }


    @WorkerThread
    private suspend fun heroInfoBackgroundRunner(url: String): MavelHero? {
        return withContext(Dispatchers.IO) {
            val connection = URL(url).openConnection() as HttpURLConnection
            try {
                connection.requestMethod = "GET"
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val jsonResponse = inputStream.bufferedReader().use { it.readText() }

                    // JSONのデシリアライズ
                    try {
                        val json = Json { ignoreUnknownKeys = true }
                        val response = json.decodeFromString(MavelHeroResponse.serializer(), jsonResponse)
                        return@withContext response.data.results.firstOrNull()
                    } catch (e: Exception) {
                        Log.e(DEBUG_TAG, "JSONデシリアライズエラー: ${e.message}")
                        null
                    }
                } else {
                    Log.e(DEBUG_TAG, "HTTPエラー: ${connection.responseCode}")
                    null
                }
            } finally {
                connection.disconnect()
            }
        }
    }


    private fun generateHash(timestamp: String, publicId: String, privateId: String): String {
        val input = "$timestamp$privateId$publicId"
        val md = MessageDigest.getInstance("MD5")
        val hashBytes = md.digest(input.toByteArray(StandardCharsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}

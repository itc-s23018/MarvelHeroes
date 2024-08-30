package jp.ac.it_college.std.s23018.marvelheroes

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import java.util.Locale

data class Hero(
    val name: String,
    val q: String
) {
    companion object {
        val list: List<Hero>
            get() {
                val locales = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
                return if (locales.get(0)?.language == Locale.JAPAN.language) {
                    listOf(
                        Hero("アイアンマン","Iron Man"),
                        Hero("キャプテンアメリカ", "Captain America"),
                        Hero("ソー", "thor"),
                        Hero("ハルク", "Hulk"),
                        Hero("ブラックウィドウ", "black widow"),
                        Hero( "ホークアイ", "hawkeye"),
                    )
                } else {
                    listOf(
                        Hero( "Ironman", "Iron Man"),
                        Hero("Captain America", "Captain America"),
                        Hero("Thor", "Thor"),
                        Hero("Hulk", "Hulk"),
                        Hero( "BlackWidow","Black Widow"),
                        Hero("Hawkeye", "Hawkeye"),
                    )
                }
            }
    }
}
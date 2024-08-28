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
                        Hero("アイアンマン","Ironman"),
                        Hero("キャプテンアメリカ", "CaptainAmerica"),
                        Hero("ソー", "Thor"),
                        Hero("ハルク", "Hulk"),
                        Hero("ブラックウィドウ", "BlackWidow"),
                        Hero("ホークアイ", "Hawkeye"),
                    )
                } else {
                    listOf(
                        Hero("Ironman", "Ironman"),
                        Hero("Captain America", "Captain America"),
                        Hero("Thor", "Thor"),
                        Hero("Hulk", "Hulk"),
                        Hero("BlackWidow","BlackWidow"),
                        Hero("Hawkeye", "Hawkeye")


                    )
                }
            }
    }
}

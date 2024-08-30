import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MavelHeroResponse(
    val data: HeroData
)

@Serializable
data class HeroData(
    val results: List<MavelHero>
)

@Serializable
data class MavelHero(
    val id: Int,
    val name: String,
    val description: String
)

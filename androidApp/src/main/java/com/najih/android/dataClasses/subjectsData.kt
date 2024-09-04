import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSubjectsResponse(
    @SerialName("_id") val id: String,
    val name: LanguageContent,
    val level: LanguageContent,
    val lessonCount: Int,
    val startDate: String,
    val endDate: String,
    val availableSeats: Int,
    val remainingSeats: Int,
    val lessonDuration: String,
    val lessonPrice: String,
    val paymentMethod: String,
    val lessonPriceAll: String,
    @SerialName("class") val classNumber: Int,
    val otherPrices: List<OtherPrice>,
    @SerialName("lessonsIds") val lessons: List<String>,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)
@Serializable
data class LanguageContent(
    val en: String,
    val ar: String
)


@Serializable
data class OtherPrice(
    val key: Int,
    val value: Int
)
@Serializable
data class GetSubjectLessons(
    @SerialName("_id") val id: String,
    val name: LanguageContent,
    val level: LanguageContent,
    val lessonCount: Int,
    val startDate: String,
    val endDate: String,
    val availableSeats: Int,
    val remainingSeats: Int,
    val lessonDuration: String,
    val lessonPrice: String,
    val paymentMethod: String,
    val lessonPriceAll: String,
    @SerialName("class") val classNumber: Int,
    val otherPrices: List<OtherPrice>,
    @SerialName("lessonsIds") val listoflessons: List<Lesson>,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)
@Serializable
data class Lesson(
    @SerialName("_id") val id: String,
    val name: LanguageContent,
    val description: LanguageContent,
    val startDate: String,
    val endDate: String,
    val link: String,
    val subjectId: String,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("__v") val version: Int
)
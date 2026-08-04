package com.axiel7.moelist.data.model.anime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.axiel7.moelist.data.model.manga.RelatedManga
import com.axiel7.moelist.data.model.media.AlternativeTitles
import com.axiel7.moelist.data.model.media.BaseMediaDetails
import com.axiel7.moelist.data.model.media.Genre
import com.axiel7.moelist.data.model.media.ListStatusDto
import com.axiel7.moelist.data.model.media.MainPicture
import com.axiel7.moelist.data.model.media.MediaFormat
import com.axiel7.moelist.data.model.media.MediaStatus
import com.axiel7.moelist.data.model.media.RelationType
import com.axiel7.moelist.data.model.media.Statistics
import com.axiel7.moelist.data.model.media.StatisticsStatus
import com.axiel7.moelist.data.model.media.WeekDay
import com.axiel7.moelist.ui.generated.resources.UiRes
import com.axiel7.moelist.ui.generated.resources.hour_abbreviation
import com.axiel7.moelist.ui.generated.resources.minutes_abbreviation
import com.axiel7.moelist.ui.generated.resources.unknown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Immutable
@Serializable
data class AnimeDetails(
    override val id: Int = 0,
    override val title: String? = null,
    @SerialName("main_picture")
    override val mainPicture: MainPicture? = null,
    @SerialName("alternative_titles")
    override val alternativeTitles: AlternativeTitles? = null,
    @SerialName("start_date")
    override val startDate: String? = null,
    @SerialName("end_date")
    override val endDate: String? = null,
    override val synopsis: String? = null,
    override val mean: Float? = null,
    override val rank: Int? = null,
    override val popularity: Int? = null,
    @SerialName("num_list_users")
    override val numListUsers: Int? = null,
    @SerialName("num_scoring_users")
    override val numScoringUsers: Int? = null,
    override val nsfw: String? = null,
    @SerialName("created_at")
    override val createdAt: String? = null,
    @SerialName("updated_at")
    override val updatedAt: String? = null,
    @SerialName("media_type")
    override val mediaFormat: MediaFormat? = null,
    override val status: MediaStatus? = null,
    override val genres: List<Genre>? = null,
    override val pictures: List<MainPicture>? = null,
    override val background: String? = null,
    @SerialName("related_anime")
    override val relatedAnime: List<RelatedAnime>? = null,
    @SerialName("related_manga")
    override val relatedManga: List<RelatedManga>? = null,
    override val recommendations: List<Recommendations<AnimeNode>>? = null,
    @SerialName("my_list_status")
    override val myListStatus: MyAnimeListStatus? = null,
    @SerialName("num_episodes")
    val numEpisodes: Int? = null,
    @SerialName("start_season")
    val startSeason: StartSeason? = null,
    @SerialName("broadcast")
    val broadcast: Broadcast? = null,
    @SerialName("source")
    val source: AnimeSource? = null,
    @SerialName("average_episode_duration")
    val averageEpisodeDuration: Int? = null,
    @SerialName("rating")
    val rating: String? = null,
    @SerialName("studios")
    val studios: List<Studio>? = null,
    @SerialName("opening_themes")
    val openingThemes: List<Theme>? = null,
    @SerialName("ending_themes")
    val endingThemes: List<Theme>? = null,
    @SerialName("statistics")
    val statistics: Statistics? = null,
) : BaseMediaDetails() {

    fun toAnimeNode() = AnimeNode(
        id = id,
        title = title.orEmpty(),
        alternativeTitles = alternativeTitles,
        mainPicture = mainPicture,
        startSeason = startSeason,
        numEpisodes = numEpisodes,
        numListUsers = numListUsers,
        mediaFormat = mediaFormat,
        status = status,
        mean = mean,
    )

    @Composable
    fun episodeDurationLocalized() = when {
        averageEpisodeDuration == null || averageEpisodeDuration <= 0 -> stringResource(UiRes.string.unknown)
        averageEpisodeDuration > 3600 -> {
            val duration = averageEpisodeDuration.toDuration(DurationUnit.SECONDS)
            duration.toComponents { hours, minutes, _, _ ->
                "$hours ${stringResource(UiRes.string.hour_abbreviation)} $minutes ${stringResource(UiRes.string.minutes_abbreviation)}"
            }
        }
        averageEpisodeDuration == 3600 -> "1 ${stringResource(UiRes.string.hour_abbreviation)}"
        averageEpisodeDuration >= 60 -> "${averageEpisodeDuration / 60} ${stringResource(UiRes.string.minutes_abbreviation)}"
        averageEpisodeDuration < 60 -> "<1 ${stringResource(UiRes.string.minutes_abbreviation)}"
        else -> stringResource(UiRes.string.unknown)
    }

    companion object {
        val sample = AnimeDetails(
            id = 59193,
            title = "Mushoku Tensei III: Isekai Ittara Honki Dasu",
            mainPicture = MainPicture(
                medium = "https://cdn.myanimelist.net/images/anime/1527/158340.jpg",
                large = "https://cdn.myanimelist.net/images/anime/1527/158340l.jpg"
            ),
            pictures = listOf(
                MainPicture(
                    medium = "https://cdn.myanimelist.net/images/anime/1723/154941.jpg",
                    large = "https://cdn.myanimelist.net/images/anime/1723/154941l.jpg"
                ),
                MainPicture(
                    medium = "https://cdn.myanimelist.net/images/anime/1441/158090.jpg",
                    large = "https://cdn.myanimelist.net/images/anime/1441/158090l.jpg"
                ),
                MainPicture(
                    medium = "https://cdn.myanimelist.net/images/anime/1527/158340.jpg",
                    large = "https://cdn.myanimelist.net/images/anime/1527/158340l.jpg"
                )
            ),
            alternativeTitles = AlternativeTitles(
                synonyms = emptyList(),
                en = "Mushoku Tensei: Jobless Reincarnation Season 3",
                ja = "無職転生 III ～異世界行ったら本気だす～"
            ),
            startDate = "2026-07-06",
            synopsis = "Third season of Mushoku Tensei: Isekai Ittara Honki Dasu.",
            mean = 8.69f,
            rank = 71,
            popularity = 984,
            numListUsers = 289928,
            numScoringUsers = 37591,
            mediaFormat = MediaFormat.TV,
            status = MediaStatus.AIRING,
            genres = listOf(
                Genre(id = 2, name = "Adventure"),
                Genre(id = 8, name = "Drama"),
                Genre(id = 9, name = "Ecchi"),
                Genre(id = 10, name = "Fantasy"),
                Genre(id = 62, name = "Isekai"),
                Genre(id = 72, name = "Reincarnation")
            ),
            myListStatus = MyAnimeListStatus(
                status = ListStatusDto.PLAN_TO_WATCH,
                score = 0,
                progress = 0,
                isRepeating = false,
                updatedAt = "2024-07-15T09:20:38+00:00",
                repeatCount = 0
            ),
            numEpisodes = 14,
            startSeason = StartSeason(
                year = 2026,
                season = Season.SUMMER
            ),
            broadcast = Broadcast(
                dayOfTheWeek = WeekDay.MONDAY,
                startTime = "00:00"
            ),
            source = AnimeSource.LIGHT_NOVEL,
            averageEpisodeDuration = 1422,
            studios = listOf(
                Studio(id = 1993, name = "Studio Bind")
            ),
            openingThemes = listOf(
                Theme(
                    id = 84972,
                    animeId = 59193,
                    text = "\"Ketsui no Uta (決意の唄)\" by Yuiko Ohara (大原ゆい子)"
                )
            ),
            endingThemes = listOf(
                Theme(
                    id = 85539,
                    animeId = 59193,
                    text = "\"Inori, Owareba (祈り、終われば)\" by Mika Nakashima"
                )
            ),
            relatedAnime = listOf(
                RelatedAnime(
                    node = AnimeNode(
                        id = 55888,
                        title = "Mushoku Tensei II: Isekai Ittara Honki Dasu Part 2",
                        mainPicture = MainPicture(
                            medium = "https://cdn.myanimelist.net/images/anime/1876/141251.jpg",
                            large = "https://cdn.myanimelist.net/images/anime/1876/141251l.jpg"
                        ),
                        mediaFormat = MediaFormat.TV,
                        alternativeTitles = AlternativeTitles(
                            synonyms = listOf(
                                "Jobless Reincarnation: I Will Seriously Try If I Go To Another World",
                                "Mushoku Tensei: Isekai Ittara Honki Dasu 2nd Season Part 2"
                            ),
                            en = "Mushoku Tensei: Jobless Reincarnation Season 2 Part 2",
                            ja = "無職転生 II ～異世界行ったら本気だす～ (第2クール)"
                        )
                    ),
                    relationType = RelationType.PREQUEL
                )
            ),
            relatedManga = emptyList(),
            recommendations = listOf(
                Recommendations(
                    node = AnimeNode(
                        id = 52991,
                        title = "Sousou no Frieren",
                        mainPicture = MainPicture(
                            medium = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg",
                            large = "https://cdn.myanimelist.net/images/anime/1015/138006l.jpg"
                        ),
                        alternativeTitles = AlternativeTitles(
                            synonyms = listOf("Frieren at the Funeral", "Frieren The Slayer"),
                            en = "Frieren: Beyond Journey's End",
                            ja = "葬送のフリーレン"
                        )
                    ),
                    numRecommendations = 1
                )
            ),
            background = "",
            statistics = Statistics(
                status = StatisticsStatus(
                    watching = "132713",
                    completed = "0",
                    onHold = "1401",
                    dropped = "904",
                    planToWatch = "154901"
                ),
                numListUsers = 289919
            )
        )
    }
}
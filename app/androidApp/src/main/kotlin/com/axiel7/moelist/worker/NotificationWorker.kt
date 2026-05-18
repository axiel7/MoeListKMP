package com.axiel7.moelist.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.axiel7.moelist.MainActivity
import com.axiel7.moelist.R
import com.axiel7.moelist.data.model.media.MediaStatus
import com.axiel7.moelist.data.repository.AnimeRepository
import dev.brewkits.kmpworkmanager.KmpWorkManager
import dev.brewkits.kmpworkmanager.background.domain.AndroidWorker
import dev.brewkits.kmpworkmanager.background.domain.WorkerEnvironment
import dev.brewkits.kmpworkmanager.background.domain.WorkerResult
import kotlinx.serialization.json.Json

class NotificationWorker(
    private val context: Context,
    private val animeRepository: AnimeRepository,
    private val notificationWorkerManager: NotificationWorkerManager,
) : AndroidWorker {

    override suspend fun doWork(input: String?, env: WorkerEnvironment): WorkerResult {
        if (env.isCancelled() || input == null) return WorkerResult.Success()
        val scheduler = KmpWorkManager.getInstance().backgroundTaskScheduler

        val inputData = Json.decodeFromString<NotificationWorkData>(input)
        val animeId = inputData.animeId

        if (inputData.type == "start")
            notificationWorkerManager.removeStartAiringAnimeNotification(animeId)

        // remove periodic worker if anime ended
        val animeDetails = animeRepository.getAnimeAiringStatus(animeId)
        if (animeDetails?.status != MediaStatus.AIRING) {
            notificationWorkerManager.removeAiringAnimeNotification(animeId, scheduler)
            return WorkerResult.Success()
        }

        val resultPendingIntent = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(
                Intent(context, MainActivity::class.java).apply {
                    action = "details"
                    putExtra("media_id", animeId)
                    putExtra("media_type", "anime")
                }
            )
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val builder = NotificationCompat.Builder(context, AIRING_ANIME_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.airing))
            .setContentText(inputData.animeTitle)
            .setSmallIcon(R.drawable.ic_moelist_logo_white)
            .setAutoCancel(true)
            .setContentIntent(resultPendingIntent)

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return WorkerResult.Failure(
                    message = "Denied notification permissions",
                    shouldRetry = true
                )
            }
            notify(animeId, builder.build())
        }

        return WorkerResult.Success()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun createAiringAnimeNotificationChannel(context: Context) {
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel =
        NotificationChannel(AIRING_ANIME_CHANNEL_ID, context.getString(R.string.airing), importance)
    channel.description = ""
    // Register the channel with the system
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

const val AIRING_ANIME_CHANNEL_ID = "airing_notifications"
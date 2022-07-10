package io.github.jofr.capacitor.mediasessionplugin;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.Binder;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.media.session.MediaButtonReceiver;
import androidx.media.app.NotificationCompat.MediaStyle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MediaSessionService extends Service {
    private static final String TAG = "MediaSessionService";

    private MediaSessionPlugin plugin;
    private MediaSessionCallback callback;

    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder playbackStateBuilder;
    private MediaMetadataCompat.Builder mediaMetadataBuilder;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private MediaStyle notificationStyle;
    private final Map<String, NotificationCompat.Action> notificationActions = new HashMap<>();
    private static final int NOTIFICATION_ID = 1;

    private int playbackState = PlaybackStateCompat.STATE_NONE;
    private String title = "";
    private String artist = "";
    private String album = "";
    private long duration = 0;
    private long position = 0;
    private float playbackSpeed = 0;

    private boolean possibleActionsUpdate = true;
    private boolean playbackStateUpdate = false;
    private boolean mediaMetadataUpdate = false;
    private boolean notificationUpdate = false;

    private final IBinder binder = new LocalBinder();

    public final class LocalBinder extends Binder {
        MediaSessionService getService() {
            return MediaSessionService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void connectAndInitialize(MediaSessionPlugin plugin) {
        this.plugin = plugin;

        mediaSession = new MediaSessionCompat(this, "WebViewMediaSession");
        mediaSession.setCallback(new MediaSessionCallback(plugin));
        mediaSession.setActive(true);

        playbackStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY)
                .setState(PlaybackStateCompat.STATE_PAUSED, position, playbackSpeed);
        mediaSession.setPlaybackState(playbackStateBuilder.build());

        mediaMetadataBuilder = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration);
        mediaSession.setMetadata(mediaMetadataBuilder.build());

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("playback", "Playback", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationStyle = new MediaStyle().setMediaSession(mediaSession.getSessionToken());
        notificationBuilder = new NotificationCompat.Builder(this, "playback")
                .setStyle(notificationStyle)
                .setSmallIcon(R.drawable.ic_transparent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notificationBuilder.build(), ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
        }

        notificationActions.put("play", new NotificationCompat.Action(
                R.drawable.ic_baseline_play_arrow_24, "Play", MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE)
        ));
        notificationActions.put("pause", new NotificationCompat.Action(
                R.drawable.ic_baseline_pause_24, "Pause", MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE)
        ));
        notificationActions.put("seekbackward", new NotificationCompat.Action(
                R.drawable.ic_baseline_replay_30_24, "Previous Track", MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_REWIND)
        ));
        notificationActions.put("seekforward", new NotificationCompat.Action(
                R.drawable.ic_baseline_forward_30_24, "Next Track", MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_FAST_FORWARD)
        ));
        notificationActions.put("previoustrack", new NotificationCompat.Action(
                R.drawable.ic_baseline_skip_previous_24, "Previous Track", MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
        ));
        notificationActions.put("nexttrack", new NotificationCompat.Action(
                R.drawable.ic_baseline_skip_next_24, "Next Track", MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_NEXT)
        ));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaButtonReceiver.handleIntent(mediaSession, intent);
        return super.onStartCommand(intent, flags, startId);
    }

    public void setPlaybackState(int playbackState) {
        if (this.playbackState != playbackState) {
            this.playbackState = playbackState;
            playbackStateUpdate = true;
            possibleActionsUpdate = true;
        }
    }

    public void setTitle(String title)  {
        if (!this.title.equals(title)) {
            this.title = title;
            mediaMetadataUpdate = true;
            notificationUpdate = true;
        }
    }

    public void setArtist(String artist) {
        if (!this.artist.equals(artist)) {
            this.artist = artist;
            mediaMetadataUpdate = true;
            notificationUpdate = true;
        }
    }

    public void setAlbum(String album) {
        if (!this.album.equals(album)) {
            this.album = album;
            mediaMetadataUpdate = true;
            notificationUpdate = true;
        }
    }

    public void setDuration(long duration) {
        if (this.duration != duration) {
            this.duration = duration;
            mediaMetadataUpdate = true;
            notificationUpdate = true;
        }
    }

    public void setPosition(long position) {
        if (this.position != position) {
            this.position = position;
            playbackStateUpdate = true;
        }
    }

    public void setPlaybackSpeed(float playbackSpeed) {
        if (this.playbackSpeed != playbackSpeed) {
            this.playbackSpeed = playbackSpeed;
            playbackStateUpdate = true;
        }
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        if (possibleActionsUpdate) {
            final String[] possibleActions =
                    {"previoustrack", "seekbackward", "play", "pause", "seekforward", "nexttrack"};
            final Set<String> compactViewActions =
                    new HashSet<>(Arrays.asList("previoustrack", "play", "pause", "nexttrack"));
            Map<String, Long> actionToPlaybackAction = new HashMap<>();
            actionToPlaybackAction.put("previoustrack", PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);
            actionToPlaybackAction.put("seekbackward", PlaybackStateCompat.ACTION_REWIND);
            actionToPlaybackAction.put("play", PlaybackStateCompat.ACTION_PLAY_PAUSE);
            actionToPlaybackAction.put("pause", PlaybackStateCompat.ACTION_PLAY_PAUSE);
            actionToPlaybackAction.put("seekforward", PlaybackStateCompat.ACTION_FAST_FORWARD);
            actionToPlaybackAction.put("nexttrack", PlaybackStateCompat.ACTION_SKIP_TO_NEXT);

            notificationBuilder.mActions.clear();
            long playbackStateActions = PlaybackStateCompat.ACTION_SEEK_TO;
            Set<Integer> compactViewActionIndices = new HashSet<>();
            int actionIndex = 0;
            for (String actionName : possibleActions) {
                if (plugin.hasActionHandler(actionName)) {
                    if (actionName.equals("play") && playbackState != PlaybackStateCompat.STATE_PAUSED) {
                        continue;
                    }
                    if (actionName.equals("pause") && playbackState != PlaybackStateCompat.STATE_PLAYING) {
                        continue;
                    }

                    playbackStateActions = playbackStateActions | actionToPlaybackAction.get(actionName);

                    notificationBuilder
                            .addAction(notificationActions.get(actionName));
                    if (compactViewActions.contains(actionName)) {
                        compactViewActionIndices.add(actionIndex);
                    }

                    actionIndex++;
                }
            }

            if (!compactViewActions.isEmpty()) {
                notificationStyle
                        .setShowActionsInCompactView(compactViewActionIndices.stream().mapToInt(Number::intValue).toArray());
            }

            playbackStateBuilder
                    .setActions(playbackStateActions);

            possibleActionsUpdate = false;
            playbackStateUpdate = true;
            notificationUpdate = true;
        }

        if (playbackStateUpdate) {
            playbackStateBuilder.setState(this.playbackState, this.position, this.playbackSpeed);
            mediaSession.setPlaybackState(playbackStateBuilder.build());
            playbackStateUpdate = false;
        }

        if (mediaMetadataUpdate) {
            mediaMetadataBuilder
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration);
            mediaSession.setMetadata(mediaMetadataBuilder.build());
            mediaMetadataUpdate = false;
        }

        if (notificationUpdate) {
            notificationBuilder
                    .setContentTitle(title)
                    .setContentText(artist + " - " + album);
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            notificationUpdate = false;
        }
    }

    public void updatePossibleActions() {
        this.possibleActionsUpdate = true;
        this.update();
    }
}
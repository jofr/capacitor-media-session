package io.github.jofr.capacitor.mediasessionplugin;

import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

public class MediaSessionCallback extends MediaSessionCompat.Callback {
    private static final String TAG = "MediaSessionCallback";

    private final MediaSessionPlugin plugin;

    MediaSessionCallback(MediaSessionPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPlay() {
        Log.d(TAG, "onPlay");

        plugin.actionCallback("play");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");

        plugin.actionCallback("pause");
    }

    @Override
    public void onSeekTo(long pos) {
        Log.d(TAG, "onSeekTo");

        plugin.actionCallback("seekto");
    }

    @Override
    public void onRewind() {
        Log.d(TAG, "onRewind");

        plugin.actionCallback("seekbackward");
    }

    @Override
    public void onFastForward() {
        Log.d(TAG, "onFastForward");

        plugin.actionCallback("seekforward");
    }

    @Override
    public void onSkipToPrevious() {
        Log.d(TAG, "onSkipToPrevious");

        plugin.actionCallback("previoustrack");
    }

    @Override
    public void onSkipToNext() {
        Log.d(TAG, "onSkipToNext");

        plugin.actionCallback("nexttrack");
    }
}

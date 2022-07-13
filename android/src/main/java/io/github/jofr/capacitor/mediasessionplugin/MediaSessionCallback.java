package io.github.jofr.capacitor.mediasessionplugin;

import android.support.v4.media.session.MediaSessionCompat;

import com.getcapacitor.JSObject;

public class MediaSessionCallback extends MediaSessionCompat.Callback {
    private static final String TAG = "MediaSessionCallback";

    private final MediaSessionPlugin plugin;

    MediaSessionCallback(MediaSessionPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPlay() {
        plugin.actionCallback("play");
    }

    @Override
    public void onPause() {
        plugin.actionCallback("pause");
    }

    @Override
    public void onSeekTo(long pos) {
        JSObject data = new JSObject();
        data.put("seekTime", (double) pos/1000.0);
        plugin.actionCallback("seekto", data);
    }

    @Override
    public void onRewind() {
        plugin.actionCallback("seekbackward");
    }

    @Override
    public void onFastForward() {
        plugin.actionCallback("seekforward");
    }

    @Override
    public void onSkipToPrevious() {
        plugin.actionCallback("previoustrack");
    }

    @Override
    public void onSkipToNext() {
        plugin.actionCallback("nexttrack");
    }

    @Override
    public void onStop() {
        plugin.actionCallback("stop");
    }
}

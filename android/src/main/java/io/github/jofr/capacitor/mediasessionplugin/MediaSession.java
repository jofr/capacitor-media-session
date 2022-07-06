package io.github.jofr.capacitor.mediasessionplugin;

import android.util.Log;

public class MediaSession {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}

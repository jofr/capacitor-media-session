package io.github.jofr.capacitor.mediasessionplugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.HashMap;
import java.util.Map;

@CapacitorPlugin(name = "MediaSession")
public class MediaSessionPlugin extends Plugin {
    private static final String TAG = "MediaSessionPlugin";

    private MediaSessionService service = null;

    private boolean playing = false;
    private final Map<String, PluginCall> actionHandlers = new HashMap<>();

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "serviceConnection.onServiceConnected");

            MediaSessionService.LocalBinder binder = (MediaSessionService.LocalBinder) iBinder;
            service = binder.getService();
            service.connectAndInitialize(MediaSessionPlugin.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "serviceConnection.onServiceDisconnected");
        }
    };

    @Override
    public void load() {
        super.load();

        startMediaService();
    }

    public void startMediaService() {
        Intent intent = new Intent(getActivity(), MediaSessionService.class);
        ContextCompat.startForegroundService(getContext(), intent);
        getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @PluginMethod
    public void setMetadata(PluginCall call) {
        if (service == null) {
            return;
        }

        service.setTitle(call.getString("title", ""));
        service.setArtist(call.getString("album", ""));
        service.setAlbum(call.getString("album", ""));
        service.update();
    }

    @PluginMethod
    public void setPlaybackState(PluginCall call) {
        if (service == null) {
            return;
        }

        String state = call.getString("playbackState");
        int playbackState = PlaybackStateCompat.STATE_NONE;
        if (state != null && state.equals("paused")) {
            playbackState = PlaybackStateCompat.STATE_PAUSED;
        } else if (state != null && state.equals("playing")) {
            playbackState = PlaybackStateCompat.STATE_PLAYING;
        }

        service.setPlaybackState(playbackState);
        service.update();
    }

    @PluginMethod
    public void setPositionState(PluginCall call) {
        if (service == null) {
            return;
        }

        Double durationSeconds = call.getDouble("duration", 0.0);
        long duration = Math.round(durationSeconds * 1000);
        Double positionSeconds = call.getDouble("position", 0.0);
        long position = Math.round(positionSeconds * 1000);
        Float playbackSpeed = call.getFloat("playbackRate", 1.0F);

        service.setDuration(duration);
        service.setPosition(position);
        service.setPlaybackSpeed(playbackSpeed);
    }

    public void actionCallback(String action) {
        Log.d(TAG, "actionCallback (for action " + action + ")");

        PluginCall call = actionHandlers.get(action);
        if (call != null) {
            JSObject data = new JSObject();
            data.put("action", action);
            call.resolve(data);
        } else {
            Log.i(TAG, "No callback for action " + action);
        }
    }

    public void actionCallback(String action, JSObject data) {
        Log.d(TAG, "actionCallback (for action " + action + ")");

        PluginCall call = actionHandlers.get(action);
        if (call != null) {
            data.put("action", action);
            call.resolve(data);
        } else {
            Log.i(TAG, "No callback for action " + action);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void setActionHandler(PluginCall call) {
        Log.d(TAG, "setActionHandler (for action " + call.getString("action") + ")");

        call.setKeepAlive(true);
        actionHandlers.put(call.getString("action"), call);
    }
}

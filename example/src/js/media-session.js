import { MediaSession } from '@jofr/capacitor-media-session';

const audioElement = document.querySelector('audio');
const playbackStopped = true;

const updatePositionState = () => {
    MediaSession.setPositionState({
        position: audioElement.currentTime,
        duration: audioElement.duration,
        playbackRate: audioElement.playbackRate
    });
}

audioElement.addEventListener("durationchange", updatePositionState);
audioElement.addEventListener("seeked", updatePositionState);
audioElement.addEventListener("ratechange", updatePositionState);

const updatePlaybackState = () => {
    const playbackState = playbackStopped ? "none" : (audioElement.paused ? "paused" : "playing");
    MediaSession.setPlaybackState({
        playbackState: playbackState
    });
}

audioElement.addEventListener("play", () => {
    playbackStopped = false;
    updatePlaybackState();
});
audioElement.addEventListener("pause", updatePlaybackState);


MediaSession.setActionHandler({ action: 'play' }, () => {
    audioElement.play();
});

MediaSession.setActionHandler({ action: 'pause' }, () => {
    audioElement.pause();
});

MediaSession.setActionHandler({ action: 'seekto' }, (details) => {
    audioElement.currentTime = details.seekTime;
});

MediaSession.setActionHandler({ action: 'seekforward' }, (details) => {
    const seekOffset = details.seekOffset ?? 30;
    audioElement.currentTime = audioElement.currentTime + seekOffset;
});

MediaSession.setActionHandler({ action: 'seekbackward' }, (details) => {
    const seekOffset = details.seekOffset ?? 30;
    audioElement.currentTime = audioElement.currentTime + 30;
});

MediaSession.setActionHandler({ action: 'stop' }, () => {
    audioElement.pause();
    playbackStopped = true;
    updatePlaybackState();
});
import { MediaSession } from '@jofr/capacitor-media-session';

const audioElement = document.querySelector('audio');
let playbackStopped = true;

const updatePositionState = () => {
  MediaSession.setPositionState({
    position: audioElement.currentTime,
    duration: audioElement.duration,
    playbackRate: audioElement.playbackRate,
  });
};

audioElement.addEventListener('durationchange', updatePositionState);
audioElement.addEventListener('seeked', updatePositionState);
audioElement.addEventListener('ratechange', updatePositionState);
audioElement.addEventListener('play', updatePositionState);
audioElement.addEventListener('pause', updatePositionState);

const updatePlaybackState = () => {
  const playbackState = playbackStopped ? 'none' : audioElement.paused ? 'paused' : 'playing';
  MediaSession.setPlaybackState({
    playbackState: playbackState,
  });
};

audioElement.addEventListener('play', () => {
  playbackStopped = false;
  updatePlaybackState();

  MediaSession.setMetadata({
    title: 'Prelude',
    artist: 'Jan Morgenstern',
    album: 'Big Buck Bunny',
    artwork: [{ src: './assets/imgs/logo.png', type: 'image/png', sizes: '512x512' }],
  });
});
audioElement.addEventListener('pause', updatePlaybackState);

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
  audioElement.currentTime = audioElement.currentTime - 30;
});

MediaSession.setActionHandler({ action: 'stop' }, () => {
  playbackStopped = true;
  audioElement.pause();
});

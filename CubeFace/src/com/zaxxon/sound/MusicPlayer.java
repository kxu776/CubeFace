package com.zaxxon.sound;


import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem;

public class MusicPlayer {
	
	private Clip clip;

	public MusicPlayer(String Files) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(Files));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false);
			AudioInputStream dais =
					AudioSystem.getAudioInputStream(
							decodeFormat,ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if(clip == null) {
			return;
		}
			stop();
			clip.setFramePosition(0);
			clip.start();
	}
	
	public void stop() {
		if(clip.isRunning()) clip.stop();
	}
	
	public void close() {
		stop();
		clip.close();
	}

}

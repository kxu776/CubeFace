package com.zaxxon.sound;


import javax.sound.sampled.*;

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
		
		else {
			clip.start();
			loop();
		}
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void setVol(float sound) {
		FloatControl volume = 
			    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		volume.setValue(sound);
		
	}
	
	public void close() {
		stop();
		clip.close();
	}

	public void shoot(){
		if(clip == null)
			return;
		if(clip.isRunning()) clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}
}

package audio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

import engine.Game;


public class SoundClip 
{
	
	public static SoundClip stab = new SoundClip("/sounds/stab.wav", true);
	public static SoundClip pain = new SoundClip("/sounds/pain.wav", true);
	
	private Clip clip;
	private FloatControl gainControl;
	private boolean randomPitch;
	private Random random;
	private float frequency, pitch;
	
	public SoundClip(String path, boolean randomPitch) 
	{
		this.randomPitch = randomPitch;
		this.random = new Random();
		pitch = 1;
		try
		{
			InputStream audioSrc = getClass().getResourceAsStream(path);
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(	AudioFormat.Encoding.PCM_SIGNED, 
														baseFormat.getSampleRate(), 
														16, 
														baseFormat.getChannels(), 
														baseFormat.getChannels() * 2, 
														baseFormat.getSampleRate(), 
														false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			frequency = clip.getFormat().getSampleRate();
			gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void play()
	{
		if(clip == null)
		{
			return;
		}
		if(clip.isControlSupported((FloatControl.Type.SAMPLE_RATE)))
		{
			((FloatControl)clip.getControl((FloatControl.Type.SAMPLE_RATE))).setValue(frequency*pitch);
		}
		
		stop();
		
		clip.setFramePosition(0);
		while(!clip.isRunning())
		{
			clip.start();
		}
		pitch = 1;
	}
	
	public SoundClip randomPitch()
	{
		pitch = random.nextFloat()+0.5f;
		return this;
	}
	
	public void stop()
	{
		if(clip.isRunning())
		{
			clip.stop();
		}
	}
	
	public void close()
	{
		stop();
		clip.drain();
		clip.close();
	}
	
	public void loop()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		while(!clip.isRunning())
		{
			clip.start();
		}
	}
	
	public void setVolume(float value)
	{
		gainControl.setValue(value);
	}
}

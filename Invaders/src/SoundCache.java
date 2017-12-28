import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;

public class SoundCache extends ResourceCache  {

	protected Object loadResource(URL url) {
		return Applet.newAudioClip(url);
	}
	
	public AudioClip getAudioClip(String name) {
		return (AudioClip)getResource("sound/"+name);
	}
	
	
	public void playSound(final String name) {
		
		new Thread(
				new Runnable() {
			public void run() {
				getAudioClip(name).play();
			}
		}
	).start();
	}
	
	public void loopSound(final String name) {
		new Thread(
				new Runnable() {
					public void run() {
						getAudioClip(name).loop();
					}
				}).start();
		
	}

	
	
	
	
	
	
}

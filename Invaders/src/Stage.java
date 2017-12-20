import java.awt.image.ImageObserver;

public interface Stage extends ImageObserver {
	
	public static final int SZEROKOSC = 800;
	public static final int WYSOKOSC = 600;
	public static final int SZYBKOSC = 60;
	
	public SpriteCache getSpriteCache();
	
	

}

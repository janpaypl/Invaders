import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SpriteCache extends ResourceCache implements ImageObserver{
	public HashMap<String, Object> sprites;
	
	public SpriteCache() {
		sprites = new HashMap<String, Object>();
		}

	public BufferedImage getSprite(String name) {
		BufferedImage loaded = (BufferedImage)getResource("img/"+name);
		BufferedImage compatibile = createCompatibile(loaded.getWidth(), loaded.getHeight(), Transparency.BITMASK);
		Graphics g = compatibile.getGraphics();
		g.drawImage(loaded, 0, 0, (ImageObserver) this);
		return compatibile;
		}

	public BufferedImage createCompatibile(int width, int height, int transparency) {
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage compatibile = gc.createCompatibleImage(width, height, transparency);
		return compatibile;
	}
	
	
	@Override
	protected Object loadResource(URL url) {
		try {
			return ImageIO.read(url);
		}catch (Exception e) {
			System.out.println("Przy otwieraniu "+url);
			System.out.println("Wyst¹pi³ b³¹d: "+e.getClass().getName()+" "+e.getMessage());
			System.exit(0);
			return null;
		}
		
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return (infoflags & (ALLBITS|ABORT)) == 0;
	}
	}

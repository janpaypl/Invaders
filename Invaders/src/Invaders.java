import java.awt.Canvas;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Invaders extends Canvas implements Stage{
	public long usedTime;
	public BufferStrategy strategia;
	private SpriteCache spriteCache;
	private ArrayList actors;
	
	public Invaders() {
		spriteCache = new SpriteCache();
		JFrame okno = new JFrame(".: Wojna Swiatow :.");
		JPanel panel = (JPanel)okno.getContentPane();
		setBounds(0,0,Stage.SZEROKOSC,Stage.WYSOKOSC);
		panel.setPreferredSize(new Dimension(Stage.SZEROKOSC,Stage.WYSOKOSC));
		panel.setLayout(null);
		panel.add(this);
		okno.setBounds(0,0,Stage.SZEROKOSC,Stage.WYSOKOSC);
		okno.setVisible(true);
		okno.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		okno.setResizable(false);
		createBufferStrategy(2);
		strategia = getBufferStrategy();
		requestFocus();
		}
	
	public void initWorld() {
		actors = new ArrayList();
		for (int i = 0; i < 10; i++){
			Monster m = new Monster(this);
			m.setX( (int)(Math.random()*Stage.SZEROKOSC) );
			m.setY( i*20 );
			m.setVx( (int)(Math.random()*3)+1 );
			actors.add(m);
		}
		}
	
	public void paintWorld() {
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,getWidth(),getHeight());
		for (int i = 0; i < actors.size(); i++) {
			Actor m = (Actor)actors.get(i);
			m.paint(g);
		}
		g.setColor(Color.white);
		if (usedTime > 0)
			g.drawString(String.valueOf(1000/usedTime)+" fps",0,Stage.WYSOKOSC-50);
		else
			g.drawString("--- fps",0,Stage.WYSOKOSC-50);
		strategia.show();
		}
	
	public void updateWorld() {
		for (int i = 0; i < actors.size(); i++) {
			Actor m = (Actor)actors.get(i);
			m.act();
		}
		}
	
	public SpriteCache getSpriteCache() {
		return spriteCache;
	}
	
	public void game() {
		usedTime=1000;
		initWorld();
		while (isVisible()) {
			long startTime = System.currentTimeMillis();
			updateWorld();
			paintWorld();
			usedTime = System.currentTimeMillis()-startTime;
		try {
			Thread.sleep(Stage.SZYBKOSC);
			} catch (InterruptedException e) {}
			}
		}
	
	
		public static void main(String[] args) {
		Invaders inv = new Invaders();
		inv.game();
		}
		}

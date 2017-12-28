import java.awt.Canvas;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Invaders extends Canvas implements Stage,KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long usedTime;
	public BufferStrategy strategia;
	private SpriteCache spriteCache;
	private ArrayList<Actor> actors;
	private Player player;
	private boolean gameEnded=false;
	private SoundCache soundCache;
	private BufferedImage background, backgroundTile;
	private int backgroundY;
	
	public Invaders() {
		spriteCache = new SpriteCache();
		soundCache = new SoundCache();
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
		addKeyListener(this);
		BufferedImage cursor = spriteCache.createCompatibile(10, 10, Transparency.BITMASK);
		Toolkit t = Toolkit.getDefaultToolkit();
		Cursor c = t.createCustomCursor(cursor, new Point(5,5), "null");
		setCursor(c);
		
		setIgnoreRepaint(true);
		
		}
	
	public SoundCache getSoundCache() {
		return soundCache;
	}
	
	public void initWorld() {
		actors = new ArrayList<Actor>();
		for (int i = 0; i < 20; i++){
			Monster m = new Monster(this);
			m.setX( (int)(Math.random()*Stage.SZEROKOSC+2) );
			if (m.getX() < 0) m.setX(1);
			if (m.getX() >746) m.setX(745);
			m.setY( i*20 );
			m.setVx( (int)(Math.random()*2)+1 );
			actors.add(m);
		}
		player = new Player(this);
		player.setX(Stage.SZEROKOSC/2);
		player.setY(Stage.WYSOKOSC-2*player.getHeight());
		soundCache.loopSound("music.wav");
		
		backgroundTile = spriteCache.getSprite("oceano.jpg");
		background = spriteCache.createCompatibile(Stage.SZEROKOSC,
				Stage.WYSOKOSC+backgroundTile.getHeight(),
				Transparency.OPAQUE);
		Graphics2D g = (Graphics2D)background.getGraphics();
		g.setPaint(new TexturePaint(backgroundTile,
				new Rectangle(0,0,backgroundTile.getWidth(),backgroundTile.getHeight())));
		g.fillRect(0, 0, background.getWidth(), background.getHeight());
		backgroundY = backgroundTile.getHeight();
		}
	
	public void paintWorld() {
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.drawImage(background, 0, 0, Stage.SZEROKOSC, Stage.WYSOKOSC, 0, backgroundY, Stage.SZEROKOSC, backgroundY+Stage.WYSOKOSC, this);
		for (int i = 0; i < actors.size(); i++) {
			Actor m = (Actor)actors.get(i);
			m.paint(g);
		}
		player.paint(g);
		paintStatus(g);
		strategia.show();
		}
	
	public void updateWorld() {
		int i = 0;
		while (i<actors.size()) {
			Actor m = (Actor)actors.get(i);
			if (m.isMarkedForRemoval()) {
				actors.remove(i);
			}
			else {
				m.act();
				i++;
			}
		}
		player.act();
		}
	
	public SpriteCache getSpriteCache() {
		return spriteCache;
	}
	
	public void game() {
		usedTime=1000;
		initWorld();
		while (isVisible() && !gameEnded) {
			long startTime = System.currentTimeMillis();
			backgroundY--;
			if (backgroundY <0 )
				backgroundY = backgroundTile.getHeight();
			updateWorld();
			checkCollisions();
			paintWorld();
			usedTime = System.currentTimeMillis()-startTime;
		
			do {
				Thread.yield();
			}while(System.currentTimeMillis()-startTime < 27);
		}
		paintGameOver();
		}
	
	private void paintGameOver() {
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD,20));
		g.drawString("GAME OVER", Stage.SZEROKOSC/2-50, Stage.WYSOKOSC/2);
		strategia.show();
		
	}

	public void addActor(Actor a) {
		actors.add(a);
	}
	
	public void checkCollisions() {
		Rectangle playerBounds = player.getBounds();
		
		for(int i = 0; i< actors.size();i++) {
			Actor a1 = (Actor)actors.get(i);
			Rectangle r1 = a1.getBounds();
			
			if(r1.intersects(playerBounds)) {
				player.collision(a1);
				a1.collision(player);
			}
			
			for (int j = i+1; j<actors.size();j++) {
				Actor a2 = (Actor)actors.get(j);
				Rectangle r2 = a2.getBounds();
				
				if(r1.intersects(r2)) {
					a1.collision(a2);
					a2.collision(a1);
				}
			}
		}
	}
	
	public void paintShields(Graphics2D g) {
		g.setPaint(Color.red);
		g.fillRect(280, Stage.WYSOKOSC_GRY, Player.MAX_SHIELDS, 30);
		g.setPaint(Color.blue);
		g.fillRect(280+Player.MAX_SHIELDS-player.getShields(), Stage.WYSOKOSC_GRY, player.getShields(), 30);
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setPaint(Color.green);
		g.drawString("SHIELDS", 170, Stage.WYSOKOSC_GRY+20);
	}
	
	public void paintScore(Graphics2D g) {
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setPaint(Color.green);
		g.drawString("Score:", 20, Stage.WYSOKOSC_GRY+20);
		g.setPaint(Color.red);
		g.drawString(player.getScore()+"", 100, Stage.WYSOKOSC_GRY+20);
	}
		
	public void paintAmmo(Graphics2D g) {
		int xBase = 280+Player.MAX_SHIELDS+10;
		for(int i = 0 ; i< player.getClusterBombs(); i++) {
			BufferedImage bomb = spriteCache.getSprite("bombU.png");
			g.drawImage(bomb, xBase+i*20, Stage.WYSOKOSC_GRY, this);
		}
	}
	
	public void paintFps(Graphics2D g) {
		g.setFont( new Font("Arial",Font.BOLD,12));
		g.setColor(Color.WHITE);
		if (usedTime > 0)
			g.drawString(String.valueOf(1000/usedTime)+" fps", Stage.SZEROKOSC-50, Stage.WYSOKOSC_GRY);
		
		else
			g.drawString("------ FPS", Stage.WIDTH-50, Stage.WYSOKOSC_GRY);
	}
	
	public void paintStatus (Graphics2D g) {
		paintScore(g);
		paintShields(g);
		paintAmmo(g);
		paintFps(g);
		
	}

		public Player getPlayer() {
			return player;
		}
	
		@Override
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			player.keyTyped(e);
			
		}
		


public static void main(String[] args) {
	Invaders inv = new Invaders();
	inv.game();
	}

@Override
public void gameOver() {
	{
		gameEnded = true;
	}
	
	
}
}
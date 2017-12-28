import java.awt.event.KeyEvent;



public class Player extends Actor {
	
	protected static final int PLAYER_SPEED = 4;
	protected int vx;
	protected int vy;
	private boolean up,down,right,left;
	
	public static final int MAX_SHIELDS = 200;
	public static final int MAX_BOMBS = 5;
	private int score;
	private int shields;
	protected int clusterBombs=5;
	
	public Player(Stage stage) {
		super(stage);
		setSpriteName("nave.png");
		clusterBombs = MAX_BOMBS;
		setShields(MAX_SHIELDS);
	}
	
	protected void updateSpeed() {
		vx = 0;
		vy = 0;
		
		if(down) vy = PLAYER_SPEED;
		if(up)  vy = -PLAYER_SPEED;
		if(left) vx = -PLAYER_SPEED;
		if(right) vx = PLAYER_SPEED;
	}
	
	public void act() {
		super.act();
		x+=vx;
		y+=vy;
		
		if (x<1 ) x=1;
		if (x>Stage.SZEROKOSC-getHeight()) x = Stage.SZEROKOSC-getHeight();
		if (y<1) y = 1;
		if (y>Stage.WYSOKOSC-getHeight()) y = Stage.WYSOKOSC-getHeight();
			
	}
	
	public int getVx() {
		return vx;
	}
	
	public void setVx(int Vx){
		
		vx = Vx;
	}
	
	public int getVy(){
		return vy;
	}
	
	public void setVy(int Vy) {
		vy = Vy;
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void fire() {
		Bullet b = new Bullet(stage);
		b.setX(x+20);
		b.setY(y-b.getHeight()+10);
		stage.addActor(b);
		stage.getSoundCache().playSound("missile.wav");
	}
	
	public void fireCluster() {
		if (clusterBombs == 0)
			return;
		int xDiff = 15;
		int yDiff = 0;
		
		clusterBombs--;
		
		stage.addActor(new Bomb(stage,Bomb.UP_LEFT,x+xDiff,y+yDiff));
		stage.addActor(new Bomb(stage,Bomb.UP,x+xDiff,y+yDiff));
		stage.addActor(new Bomb(stage,Bomb.UP_RIGHT,x+xDiff,y+yDiff));
		stage.addActor(new Bomb(stage,Bomb.LEFT,x+xDiff,y+yDiff));
		stage.addActor(new Bomb(stage,Bomb.RIGHT,x+xDiff,y+yDiff));
		stage.addActor(new Bomb(stage,Bomb.DOWN_LEFT,x+xDiff,y+yDiff));
		stage.addActor(new Bomb(stage,Bomb.DOWN,x+xDiff,y+yDiff));
		stage.addActor(new Bomb(stage,Bomb.DOWN_RIGHT,x+xDiff,y+yDiff));
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN : down = false; break;
		case KeyEvent.VK_UP : up = false; break;
		case KeyEvent.VK_LEFT : left = false;break;
		case KeyEvent.VK_RIGHT : right = false;break;
		
		}
		updateSpeed();
	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
	case KeyEvent.VK_DOWN : down = true; break;
	case KeyEvent.VK_UP : up = true; break;
	case KeyEvent.VK_LEFT : left = true;break;
	case KeyEvent.VK_RIGHT : right = true;break;
	case KeyEvent.VK_SPACE : fire(); break;
	case KeyEvent.VK_B : fireCluster(); break;
		
	}
		updateSpeed();
}
	
	public void collision(Actor a) {
		if (a instanceof Monster) {
			a.remove();
			addScore(40);
			addShields(-40);
			stage.getSoundCache().playSound("explosion.wav");
		}
		
			if (a instanceof Laser) {
				a.remove();
				addShields(-10);
			}
			
			if(getShields()<0)
				stage.gameOver();
		
	}
	
	private void addShields(int i) {
		shields+=i;
		
	}

	public void addScore(int i) {
		score+=i;
	}
	

	public int getShields() {
		return shields;
	}

	public void setShields(int shields) {
		this.shields = shields;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getClusterBombs() {
		return clusterBombs;
	}

	public void setClusterBombs(int clusterBombs) {
		this.clusterBombs = clusterBombs;
	}
	
}

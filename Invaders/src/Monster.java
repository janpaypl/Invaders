public class Monster extends Actor {
	protected int vx;
	protected static final double FIRING_FREQUENCY = 0.01;
	
	public Monster(Stage stage) {
		super(stage);
		setSpriteName("monster.png");
	}

		public void act() {
			x+=vx;
			if (x < 0 || x > Stage.SZEROKOSC-54)
				vx = -vx;
			
			if (Math.random()< FIRING_FREQUENCY)
				fire();
		}
		
		public void collision(Actor a) {
			if (a instanceof Bullet  || a instanceof Bomb)
			{
			remove();
			stage.getSoundCache().playSound("explosion.wav");
			spawn();
			stage.getPlayer().addScore(20);
			
		}
			}
		
		public void fire() {
			Laser m = new Laser(stage);
			m.setX(x+getWidth()/2);
			m.setY(y+getHeight());
			stage.addActor(m);
			stage.getSoundCache().playSound("shooting.wav");
		}
		
		public void spawn() {
			Monster m = new Monster(stage);
			m.setX((int) (Math.random()*Stage.SZEROKOSC));
			m.setY( (int) (Math.random()*stage.WYSOKOSC_GRY/2));
			m.setVx( (int)(Math.random()*20-10)+1);
			stage.addActor(m);
		}
		
public int getVx() { return vx; }
public void setVx(int i) {vx = i; }
}

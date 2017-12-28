
public class Bullet extends Actor {
	
	protected static final int BULLET_SPEED = 18;
	
	public Bullet(Stage stage) {
		super(stage);
		setSpriteName("bullet.png");
	}

	public void act() {
		super.act();
		y-=BULLET_SPEED;
		if (y<0)
			remove();
	}
	
	public void collision(Actor a) {
		if (a instanceof Monster)
			remove();
	}
	

}

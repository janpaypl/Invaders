
public class Laser extends Actor{
	
	protected static final int BULLET_SPEED=3;
	
	
	public Laser(Stage stage) {
		super(stage);
		setSpriteName("laserr.png");
	//	setFrameSpeed(10);
	}

	
	public void act() {
		super.act();
		y+=BULLET_SPEED;
		if(y >Stage.WYSOKOSC_GRY)
			remove();
	}

}

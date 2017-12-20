public class Monster extends Actor {
	protected int vx;
	
	public Monster(Stage stage) {
		super(stage);
		setSpriteName("monster.png");
	}

		public void act() {
			x+=vx;
			if (x < 0 || x > Stage.SZEROKOSC)
				vx = -vx;
		}
		
public int getVx() { return vx; }
public void setVx(int i) {vx = i; }
}

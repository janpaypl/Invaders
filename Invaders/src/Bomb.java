
public class Bomb extends Actor {

	public static final int UP_LEFT = 0;
	public static final int UP =1;
	public static final int UP_RIGHT = 2;
	public static final int LEFT = 3;
	public static final int RIGHT =4;
	public static final int DOWN_LEFT =5;
	public static final int DOWN =6;
	public static final int DOWN_RIGHT =7;
	
	protected static final int BOMB_SPEED =20;
	protected int vx,vy;
	


public Bomb(Stage stage, int heading,int x,int y){
	super(stage);
	this.x =x;
	this.y=y;
	String sprite="";
	
	switch (heading) {
	case UP_LEFT :
		vx = -BOMB_SPEED;
		vy = -BOMB_SPEED;
		sprite="bombUL.png";
		break;
	
	case UP:
		vx=0;
		vy=-BOMB_SPEED;
		sprite="bombU.png";
		break;
	
	case UP_RIGHT:
		vx = BOMB_SPEED;
		vy= - BOMB_SPEED;
		sprite="bombUR.png";
		break;
		
	case LEFT :
		vx = -BOMB_SPEED;
		vy=0;
		sprite="bombL.png";
		break;
		
	case RIGHT:
		vx = BOMB_SPEED;
		vy = 0;
		sprite = "bombR.png";
		break;
		
	case DOWN_LEFT: 
		vx = -BOMB_SPEED;
		vy = BOMB_SPEED;
		sprite="bombDL.png";
		break;
		
	case DOWN:
		vx=0;
		vy=BOMB_SPEED;
		sprite = "bombD.png";
		break;
		
	case DOWN_RIGHT:
		vx=BOMB_SPEED;
		vy=BOMB_SPEED;
		sprite="bombDR.png";
		break;
	
	}
	
	setSpriteName(sprite);
}

public void act() {
	super.act();
	y+=vy;
	x+=vx;
	
	if(y <0 || y> Stage.WYSOKOSC || x<0  || x>Stage.SZEROKOSC)
		remove();
}

public void collision(Actor a) {
	if (a instanceof Monster)
		remove();
}

}
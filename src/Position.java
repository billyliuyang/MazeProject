

/*
 * Position coordinate
 */
public class Position {
	private int x;	//coordinate x
	private int y;	//coordinate y
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o == this) return true;	//improve efficiency
		if(o.getClass() != this.getClass())
			return false;	//return false if Object o is not a instance Position
		
		Position p = (Position) o;
		return (this.getX() == p.getX()) && (this.getY() == p.getY());	//return true if their coordinates equal
	}
	
	/*
	 * Each Position has a corresponding hashCode.
	 * If two Positions are equal, they have an equal hashCode.
	 * If two Positions are not equal, their hashCodes are different.  
	 */
	@Override
	public int hashCode(){
		final int PRIME=31;
		int hashResult = 1;
		hashResult = PRIME*hashResult+this.getX();
		hashResult = PRIME*hashResult+this.getY();
		return hashResult;
	}
	
	/*
	 * Method to move Position
	 */
	public Position move(DIR dir){
		int x = getX();
		int y = getY();
		
		switch(dir){
		case DIR_UP:
			x -= 1;
			break;
		case DIR_DOWN:
			x += 1;
			break;
		case DIR_LEFT:
			y -= 1;
			break;
		case DIR_RIGHT:
			y += 1;
			break;
		}
		
		Position movePosition = new Position(x,y);
		return movePosition;
	}
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * Load maze 
 */
public class LoadMaze {
	private int size;	//Size of maze (matrix size in .csv file)
	private CELL[][] mazeCell;	//A two-dimensional CELL array to store maze
	
	//Constructor
	public LoadMaze(int size,CELL[][] mazeCell){
		this.size = size;
		this.mazeCell = mazeCell;
	}
	
	//Get the two-dimensional CELL array of maze
	public CELL[][] getMazeCell() {
		return mazeCell;
	}
	
	//Get the type of CELL at a particular Position
	public CELL getCell(Position p){
		return mazeCell[p.getX()][p.getY()];
	}
	
	//Set a particular type of CELL at a particular Position
	public void setCell(Position p,CELL c){
		mazeCell[p.getX()][p.getY()] = c;
	}
	
	//Find Teleporter in maze and change it into Empty road
	public void removeTeleporter(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(mazeCell[i][j] == CELL.CELL_T){
					mazeCell[i][j] = CELL.CELL_E;
				}
			}
		}
	}
	
	//Find Land in maze and change it into Empty road
	public void removeLands(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(mazeCell[i][j] == CELL.CELL_L){
					mazeCell[i][j] = CELL.CELL_E;
				}
			}
		}
	}

	//Find the shortest path by Breath-First method
	public List<Position> findShortestPath(){
		CELL[][] cell = mazeCell;
		Position startPos = new Position(0,0);
		Queue<Position> posQueue = new LinkedList<Position>();	//A queue to store the position every step
		posQueue.add(startPos);
		Position prePosition;	// Previous position
		Position tempPosition = null;	// Temporary position
		HashMap<Position,Integer> path = new HashMap<Position,Integer>();	//A HashMap to store position as key and corresponding number of step as value
		path.put(startPos, 0);
		
		CELL[][] tempCell = new CELL[size][size];
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				tempCell[i][j] = cell[i][j];
			}
		}
		
		tempCell[0][0] = CELL.CELL_W;
		
		boolean flag = false;	// To decide whether find path to terminal or not, if find set it as true
		
		LinkedList<Position> shortestPath = new LinkedList<Position>();	// Store the shortest path in order
		
		int shortestStep = 0;
		int temp;
		
		//Loop until queue is empty or reach to the terminal
		while(!posQueue.isEmpty()){
			prePosition = posQueue.poll();
			
			if(prePosition.getX() == (size-1) && prePosition.getY() == (size-1)){
				flag = true;	// If reach to terminal, set flag as true.
				shortestStep = path.get(prePosition);
				break;
			}
			
			//From the previous position walk towards four different directions successively
			//If the way is not block, move to next position and store next position into queue, meanwhile save next position with number of step into HashMap
			for(DIR dir : DIR.values()){
				if(dir.equals(DIR.DIR_UP)){
					if(prePosition.getX() == 0){
						continue;
					}
					tempPosition = prePosition.move(DIR.DIR_UP);
				}else if(dir.equals(DIR.DIR_DOWN)){
					if(prePosition.getX() == (size-1)){
						continue;
					}
					tempPosition = prePosition.move(DIR.DIR_DOWN);
				}else if(dir.equals(DIR.DIR_LEFT)){
					if(prePosition.getY() == 0){
						continue;
					}
					tempPosition = prePosition.move(DIR.DIR_LEFT);
				}else if(dir.equals(DIR.DIR_RIGHT)){
					if(prePosition.getY() == (size-1)){
						continue;
					}
					tempPosition = prePosition.move(DIR.DIR_RIGHT);
				}
				
				if(tempCell[tempPosition.getX()][tempPosition.getY()] == CELL.CELL_E){
					temp = path.get(prePosition);
					path.put(tempPosition, temp + 1);
					tempCell[tempPosition.getX()][tempPosition.getY()] = CELL.CELL_W;
					posQueue.add(tempPosition);
				}else if(tempCell[tempPosition.getX()][tempPosition.getY()] == CELL.CELL_T){
					temp = path.get(prePosition);
					
					tempCell[tempPosition.getX()][tempPosition.getY()] = CELL.CELL_W;
					for(int i=0;i<size;i++){
						for(int j=0;j<size;j++){
							if(tempCell[i][j] == CELL.CELL_L){
								Position landsPosition = new Position(i,j);
								posQueue.add(landsPosition);
								path.put(landsPosition, temp + 1);
							}
						}
					}
				}
			}
		}
		
		//If find the way to the terminal. From terminal, find the shortest way in reverse direction.
		//Push the position into a LinkedList in turn
		if(flag == true){
			Position pathPos = new Position(size-1,size);
			for(int step=shortestStep;step>=0;step--){
				ArrayList<Position> keyPos = (ArrayList) mapValueGetKey(path,step);
				for(Position pos:keyPos){
					if(isLandsCell(pos,cell)){
						pathPos = findTeleporterCell(cell);
						shortestPath.push(pathPos);
					}
					if(isAdjacentPos(pos,pathPos)){
						shortestPath.push(pos);
						pathPos = pos;
					}
				}
				
			}
		}
		
		return shortestPath;
		
		
	}
	
	// Find the position of Teleporter
	private Position findTeleporterCell(CELL[][] cell){
		Position p = null;
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(cell[i][j].equals(CELL.CELL_T)){
					p = new Position(i,j);
				}
			}
		}
		return p;
	}
	
	// Check whether the position is land
	private boolean isLandsCell(Position p,CELL[][] cell){
		boolean b = false;
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(cell[i][j] == CELL.CELL_L){
					if(p.getX()==i && p.getY()==j){
						b = true;
					}
				}
			}
		}
		return b;
	}
	
	// Check whether two positions are adjacent.
	public boolean isAdjacentPos(Position p1,Position p2){
		boolean b = false;
		if((Math.abs(p1.getX()-p2.getX()) == 1 && p1.getY()-p2.getY() == 0) || (Math.abs(p1.getY()-p2.getY()) == 1 && p1.getX()-p2.getX() == 0)){
			b = true;
		}
		return b;
	}
	
	// Find the value in HashMap by key
	private Object mapValueGetKey(HashMap map,Object value){
		Object o = null;
		ArrayList allKey = new ArrayList();
		Set set = map.entrySet();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			if(entry.getValue().equals(value)){
				o = entry.getKey();
				allKey.add(o);
			}
			
		}
		return allKey;
		
	}
}

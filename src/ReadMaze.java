import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadMaze {
	private File inFile;
	private int size;
	
	public ReadMaze(File inFile,int size){
		this.inFile = inFile;
		this.size = size;
	}
	
	//Read csv file and store it into a two-dimentional CELL array
	public CELL[][] readCSV(){
		String[][] mazeString = new String[size][size];	// A SIZE X SIZE string array 
		CELL[][] cell = new CELL[size][size];	// A SIZE X SIZE CELL array
		String line = null;
		int row = 0;
		String value = null;
		
		// Read .csv file line-by-line and store it into a two-dimentional String array first
		try{
			BufferedReader reader = new BufferedReader(new FileReader(inFile));
			while((line=reader.readLine())!=null){
				String[] item = line.split(",",-1);
				mazeString[row] = item;
				row += 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error csv file");	// If csv file is incorrect, print error
		}
		
		// Change the String array to CELL array
		for(int i=0;i<mazeString.length;i++){
			for(int j=0;j<mazeString.length;j++){
				if("W".equals(mazeString[i][j])){
					value = "CELL_W";
				}else if("T".equals(mazeString[i][j])){
					value = "CELL_T";
				}else if("L".equals(mazeString[i][j])){
					value = "CELL_L";
				}else{
					value = "CELL_E";
				}
				cell[i][j] = CELL.valueOf(value);
			}
		}
		
		return cell;
	}
}

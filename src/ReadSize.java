import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadSize {
	private File inFile;
	public ReadSize(File inFile){
		this.inFile = inFile;
	}
	
	public int getMazeSize(){
		int size = 0;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(inFile));
			while((reader.readLine())!=null){
				size += 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error csv file");	// If csv file is incorrect, print error
		}
		return size;
	}
}

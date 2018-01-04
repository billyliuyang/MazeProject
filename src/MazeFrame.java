import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class MazeFrame extends JFrame {
	private int size;
	private LoadMaze loadMaze;
	private Cell cells;
	private Position p;
	private Color cellColor;
	private List<Position> shortestPath;
	
	public MazeFrame(File inFile){
		// set size, position, icon, and title for the JFrame
		setTitle("Maze");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setSize(dim.width/2, dim.height/2);
		setLocation(new Point(dim.width/4, dim.height/4));
		
		ReadSize rs = new ReadSize(inFile);
		size = rs.getMazeSize();
		loadMaze = new LoadMaze(size,(new ReadMaze(inFile,size)).readCSV());
		
		setLayout(new GridLayout(size,size));	// Grid maze panel into a size x size metric
		drawMaze();
	}
	
	// Draw maze by adding new JPanel in ContentPane
	private void drawMaze(){
		getContentPane().removeAll();
		CELL[][] cell = loadMaze.getMazeCell();
		shortestPath = loadMaze.findShortestPath();
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(cell[i][j] == CELL.CELL_E){
					cellColor = Color.white;
				}else if(cell[i][j] == CELL.CELL_W){
					cellColor = Color.black;
				}else if(cell[i][j] == CELL.CELL_L){
					cellColor = Color.green;
				}else if(cell[i][j] == CELL.CELL_T){
					cellColor = Color.red;
				}
				p = new Position(i,j);
				cells = new Cell(cellColor,shortestPath,p);
				getContentPane().add(cells);
			}
		}
		getContentPane().validate();
		getContentPane().repaint();

		
	}
	
	// JPanel for cells in maze
	// It has a MouseListener, will change the cell in maze according to the way of mouse click 
	private class Cell extends JPanel implements MouseListener{
		private Color c;
		private List<Position> path;
		private Position paintPosition;
		
		public Cell(Color c,List<Position> path,Position paintPosition){
			this.c = c;
			this.path = path;
			this.paintPosition = paintPosition;
			addMouseListener(this);
		}
		
		protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(c);
            g2.fillRect(0,0,getWidth(),getHeight());
            
            g2.setPaint(Color.blue);	// Set font color as blue
    		g2.setFont(new Font("Arial", Font.PLAIN, 16));
    		int width = this.getSize().width;	// Get width of this panel
    		int height = this.getSize().height;	// Get height of this panel
    		int fontSize = g2.getFont().getSize();	// Get height of current font
    		
    		// If this JPanel is part of the path, draw step number on this JPanel 
    		if(path.contains(paintPosition)){
    			String str = Integer.toString(path.indexOf(paintPosition)+1);	// Find the index of painting JPanel in path list, the step number equals this index plus 1
    			// Draw step string on the center of this panel
    			g2.drawString(str,(width-fontSize)/2,(height-fontSize)/2+fontSize);	
    		}
        }

		@Override
		public void mouseClicked(MouseEvent e) {
			CELL clickCell = loadMaze.getCell(paintPosition);
			if(clickCell == CELL.CELL_E || clickCell == CELL.CELL_W){
				loadMaze.setCell(paintPosition,clickCell == CELL.CELL_E? CELL.CELL_W:CELL.CELL_E);
			}
			if(e.isShiftDown()){
				loadMaze.removeTeleporter();
				loadMaze.setCell(paintPosition, CELL.CELL_T);
			}
			if(e.isControlDown()){
				loadMaze.removeLands();
				loadMaze.setCell(paintPosition, CELL.CELL_L);
			}
		   
		    
		    drawMaze();
			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	
	
}

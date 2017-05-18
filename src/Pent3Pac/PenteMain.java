package Pent3Pac;

import javax.swing.JFrame;


public class PenteMain {

	public static final int EMPTY = 0;
	public static final int BSTONE = 1;
	public static final int WSTONE = -1;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		int boardWidth = 720;
		int boardWidthInSquares = 19;
		
		JFrame f = new JFrame ("Play Pente");
		
		
		f.setSize(boardWidth, boardWidth);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		
		GameBoard p = new GameBoard(boardWidth, boardWidthInSquares);
		f.add(p);
		
		
		
		f.setVisible(true);
	}
}

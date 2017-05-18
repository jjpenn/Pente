package Pent3Pac;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GameBoard extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bWidthPixels;
	private int bWidthSquares;
	private int bSquareWidth;
	private int currentTurn = PenteMain.BSTONE;
	//Color boardSquareColor = new Color(150, 111, 51);
	
	//Capture Points
	int whitePoints = 0;
	int blackPoints = 0;

	
	Square [] [] theBoard;
	
	Hexe2 computerMoveGenerator = null;
	boolean playingHexe = false;
	int hexeStoneColor;
	
	public GameBoard(int bWPixel, int bWSquares) {
		
		 bWidthPixels = bWPixel;
		 bWidthSquares = bWSquares;
		//System.out.println("HI in Gameboard constructor, bWidthSquares is " + bWidthSquares);
		
		//compute width of the b squares...
		bSquareWidth = (int)(Math.ceil(bWidthPixels/bWidthSquares))+2;
		
		this.setSize(bWidthPixels, bWidthPixels);
	//	System.out.println("In gameboard constructor... bWidthPixels is " + bWidthPixels);
		this.setBackground(Color.CYAN);
		this.setVisible(true);
		
		theBoard = new Square[bWidthSquares] [bWidthSquares];
		
		//fill the board with squares...
		
		for(int row = 0; row < bWidthSquares; ++row){
			for(int col = 0; col < bWidthSquares; ++col){
				theBoard [row][col] = new Square((row*bSquareWidth), (col*bSquareWidth), bSquareWidth, row, col);
				
			}
		}
		
		//Activate MouseListening
		this.addMouseListener(this);
		
		//set the first stone
		theBoard[(int)(bWidthSquares/2)][(int)(bWidthSquares/2)].setState(currentTurn);
		
		String computerAnswer = JOptionPane.showInputDialog("Would you like to play against the AI Hexe? (Yes or no)");
		if(computerAnswer.contains("y")){
			computerMoveGenerator = new Hexe2(this, currentTurn);
			playingHexe = true;
			hexeStoneColor = currentTurn;
		}
		
		
		this.changeTurn();
		
	}
	
	//Overrides PaintComponent in JPanel
	
	public void paintComponent(Graphics g)
	{		
	//	System.out.println("Im in paintcomponent!!!!!");
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, bWidthPixels, bWidthPixels);
		
		//System.out.println("bWidthSquares is " + bWidthSquares);
		for(int row = 0; row < bWidthSquares; ++row){
			for(int col = 0; col < bWidthSquares; ++col){
				theBoard [row][col].drawMe(g);
			}
		}
		
		
	}
	
	
	public void changeTurn(){
		if(currentTurn == PenteMain.BSTONE){
			currentTurn = PenteMain.WSTONE;
		}else{
			currentTurn = PenteMain.BSTONE;
		}
	}
	public int getOppositeState(Square s){
		if (s.getState() == PenteMain.BSTONE){
			return PenteMain.WSTONE;
		} else {
			return PenteMain.BSTONE;
		}
	}
	
	public void winCheck(Square s){
		
		boolean done = false;
		int[] myDys = {-1, 0, 1};
		int whichDy = 0;
		
		while(!done && whichDy < 3){
			if(winCheckInOne(s, myDys[whichDy], 1) == true){
				weHaveAWinner();
				done = true;
			}
			whichDy++;
		}
		if(!done){
			if(winCheckInOne(s, 1, 0) == true){
				weHaveAWinner();
		}
		}
		
			}
	
	public boolean winCheckInOne(Square s, int dy, int dx){
		
		boolean isThereAWin = false;
		int sRow = s.getRow();
		int sCol = s.getCol();
	//	System.out.println("sRow = " + sRow + " and sCol = " + sCol);
		//For right horizontal check
		int howManyRight = 0;
		int howManyLeft = 0;
		int step = 1;
		//Check to the right
		while((sCol + (step * dx) < bWidthSquares) && (sRow + (step * dy) < bWidthSquares) &&
		(sCol + (step * dx) >= 0) && (sRow + (step * dy) >= 0) &&
		(theBoard[sRow + (step * dy)][sCol + (step * dx)].getState() == currentTurn)){
		
			howManyRight++;
			step++;
		}
		step = 1;
		//Check to the left
		while(  (sCol - (step * dx) >= 0) && (sRow - (step * dy) >= 0) &&
				(sCol - (step * dx) < bWidthSquares) && (sRow - (step * dy) < bWidthSquares) &&
				(theBoard[sRow - (step * dy)][sCol - (step * dx)].getState() == currentTurn)  ){
		{
			howManyLeft++;
			step++;
				}
				}
		
		if((howManyRight + howManyLeft + 1) >= 5){
			isThereAWin = true;
		}
		
		
	
		return isThereAWin;
	}
			
		public void weHaveAWinner(){
			if(currentTurn == PenteMain.BSTONE){
				JOptionPane.showMessageDialog(null, "Congratulations! Black Wins!");
			} else {
				JOptionPane.showMessageDialog(null, "Congratulations! White Wins!");
			}
		}
	
	
	public void checkForCaptures(Square s){
			
		int scorer = currentTurn;
		
		int sRow = s.getRow();
		int sCol = s.getCol();
		int theOpposite = getOppositeState(s);		
		
		
		
		//If placed above
		if(sCol < bWidthSquares - 3){
			if(theBoard[sRow][sCol+1].getState() == theOpposite){
				if(theBoard[sRow][sCol+2].getState() == theOpposite){
					if(theBoard[sRow][sCol+3].getState() == s.getState()){
						takeStones(sRow, sCol+1, sRow, sCol + 2, scorer);
					}
				}
			}
		}
		
		
		//If placed below
		if(sCol < bWidthSquares - 3){
			if(theBoard[sRow][sCol-1].getState() == theOpposite){
				if(theBoard[sRow][sCol-2].getState() == theOpposite){
					if(theBoard[sRow][sCol-3].getState() == s.getState()){
						takeStones(sRow, sCol-1, sRow, sCol-2, scorer);
					}
				}
			}
		}
		
		//If placed to the top right
				if(sRow < bWidthSquares - 3 && sCol < bWidthSquares - 3){
					if(theBoard[sRow+1][sCol+1].getState() == theOpposite){
						if(theBoard[sRow+2][sCol+2].getState() == theOpposite){
							if(theBoard[sRow+3][sCol+3].getState() == s.getState()){
								takeStones(sRow+1, sCol+1, sRow+2, sCol+2, scorer);
							}
						}
					}
				}
				
				//If placed to the bottom left
				if(sRow < bWidthSquares - 3 && sCol < bWidthSquares - 3){
					if(theBoard[sRow-1][sCol-1].getState() == theOpposite){
						if(theBoard[sRow-2][sCol-2].getState() == theOpposite){
							if(theBoard[sRow-3][sCol-3].getState() == s.getState()){
								takeStones(sRow-1, sCol-1, sRow-2, sCol-2, scorer);
							}
						}
					}
				}
				
				//If placed to the idk
				if(sRow < bWidthSquares - 3 && sCol < bWidthSquares - 3){
					if(theBoard[sRow+1][sCol-1].getState() == theOpposite){
						if(theBoard[sRow+2][sCol-2].getState() == theOpposite){
							if(theBoard[sRow+3][sCol-3].getState() == s.getState()){
								takeStones(sRow+1, sCol-1, sRow+2, sCol-2, scorer);
							}
						}
					}
				}
				
				//If placed to the idk
				if(sRow < bWidthSquares - 3 && sCol < bWidthSquares - 3){
					if(theBoard[sRow-1][sCol+1].getState() == theOpposite){
						if(theBoard[sRow-2][sCol+2].getState() == theOpposite){
							if(theBoard[sRow-3][sCol+3].getState() == s.getState()){
								takeStones(sRow-1, sCol+1, sRow-2, sCol+2, scorer);
							}
						}
					}
				}
		
		//If placed to the left
		if(sRow < bWidthSquares - 3){
			if(theBoard[sRow+1][sCol].getState() == theOpposite){
				if(theBoard[sRow+2][sCol].getState() == theOpposite){
					if(theBoard[sRow+3][sCol].getState() == s.getState()){
						takeStones(sRow+1, sCol, sRow+2, sCol, scorer);
					}
				}
			}
		}
		
		//If placed to the right
		if(sRow < bWidthSquares - 3){
			if(theBoard[sRow-1][sCol].getState() == theOpposite){
				if(theBoard[sRow-2][sCol].getState() == theOpposite){
					if(theBoard[sRow-3][sCol].getState() == s.getState()){
						takeStones(sRow-1, sCol, sRow-2, sCol, scorer);
					}
				}
			}
		}
		
	}
	
	int placeX;
	int placeY;

	
	
	public void takeStones(int r1, int c1, int r2, int c2, int taker){
		//this is to clear stones
		theBoard[r1][c1].setState(PenteMain.EMPTY);
		theBoard[r2][c2].setState(PenteMain.EMPTY);
		
		if(taker == PenteMain.BSTONE){
			blackPoints += 1;
		} else if(taker == PenteMain.WSTONE){
			whitePoints += 1;
		}
		
		this.checkForCaptureWin();	
		//this.winCheck();
		
	}
	
	public void checkForCaptureWin(){
		if(blackPoints >= 5){
			JOptionPane.showMessageDialog(null, "Black has won!");
		} else if (whitePoints >= 5){
			JOptionPane.showMessageDialog(null, "White has won!");
		} else {
			JOptionPane.showMessageDialog(null, "The score is Black: " + blackPoints + " White: " + whitePoints);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		
		//System.out.println("Clicked at" + e.getX() + ", " + e.getY());
		playGame(e);
		repaint();
		
		placeX = e.getX();
		placeY = e.getY();
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void playGame(MouseEvent e){
		
		//System.out.println("Hi from the top of playGame");
	
		Square s = findSquare(e.getX(), e.getY());
		if (s != null){
				if(s.getState() == PenteMain.EMPTY){
					doPlay(s);
					this.requestFocus();
					this.paintImmediately(0, 0, this.bWidthPixels, this.bWidthPixels);
					
					if(playingHexe == true && currentTurn == hexeStoneColor){
						//System.out.println("called");
						Square cs = computerMoveGenerator.computerMove(s.getRow(), s.getCol());
						doPlay(cs);
						repaint();
						this.requestFocus();
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "You can't click here");
				}
		
		} else {
			JOptionPane.showMessageDialog(null, "You didn't pick a square");
		}
		
		
	}	
	
	public void doPlay(Square s){
		
	//	System.out.println("HI I'm in doPlay for this square " + s);
		if(s !=null){
			s.setState(currentTurn);
			this.repaint();
			checkForCaptures(s);
			//System.out.println("HI I'm in doPlay just finished check for captures " + s);
			winCheck(s);
			//System.out.println("HI I'm in doPlay just finished winCheck for captures " + s);
			this.changeTurn();
			//System.out.println("HI at the end of doPlay for this square " + s);
		}
		
	}
	
	public Square findSquare(int mouseX, int mouseY){
		
		Square clickedOnSquare = null;
		
		//run through all of the squares and call youClickedMe()
		for(int row = 0; row < bWidthSquares; ++row){
			for(int col = 0; col < bWidthSquares; ++col){
				if(theBoard[row][col].youClickedMe(mouseX, mouseY) == true){
					clickedOnSquare = theBoard[row][col];
				}
			}
		}
		
		return clickedOnSquare;
		
	}
	
	public int getState(Square s){
		if (s.getState() == PenteMain.BSTONE){
			return PenteMain.BSTONE;
		} else {
			return PenteMain.WSTONE;
		}
	}
	
	public int getBWidthSquares(){
		return bWidthSquares;
	}
	
	public int getCurrentTurn(){
		return currentTurn;
	}
	
	public Square[][] getActualGameBoard(){
		return theBoard;
	}
	
}

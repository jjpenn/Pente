package Pent3Pac;

import javax.swing.JOptionPane;




public class Hexe2 {
	
	GameBoard myBoard;
	int mySColor;
	int boardWidthSquares;
	Square [][] theGameBoard;
	int [][] pointPerSquare;
	Square [] theArray;
	
	boolean winPriority = false;
	boolean capturePriority = false;
	
		
	public Hexe2(GameBoard b, int sColor) {
			
			myBoard = b;
			sColor = mySColor;
			boardWidthSquares = b.getBWidthSquares();
			theGameBoard = b.getActualGameBoard();
			JOptionPane.showMessageDialog(null, "Hexe says you don't stand a chance...");
		}
	
	

	public Square computerMove(int lastMoveR, int lastMoveC){
		Square nextMove = null;
		
		//System.out.println("It called computer Move");
		
		theArray = new Square[boardWidthSquares * boardWidthSquares];
		int theArrayIndex = 0;
		//Assign  point value to every board square...
		for(int r = 0; r < 19; ++r){
			for(int c = 0; c < 19; ++c){
				int n = findPoints(r,c,myBoard, theGameBoard[r][c]);
				
				theGameBoard[r][c].setPoints(n);
				
				
				theArray[theArrayIndex++] = theGameBoard[r][c];
			}
		}
	



		if(theGameBoard[8][8].getState() != 1){
			nextMove = theGameBoard[8][8];
		} else if(theGameBoard[10][10].getState() != 1){
			nextMove = theGameBoard[10][10];
		}else if (theGameBoard[11][10].getState() != 1){
			nextMove = theGameBoard[11][10];
		} else if (theGameBoard[7][6].getState() != 1){
			nextMove = theGameBoard[7][6];
		}
		




		
		//System.out.println("The best move found is: " + nextMove);
		return nextMove;
	}
	

	
	
	
	public int findPoints(int row, int col, GameBoard gb, Square s){
		

		
		int r = row;
		int c = col;
		int points = 0;
		int theOpposite = gb.getOppositeState(s);
		int theState = gb.getState(s);
		int currentTurn = gb.getCurrentTurn();
		Square[][] theBoard = gb.theBoard;
		
		//go through every direction...
		for(int rowM = -1; rowM <= 1; rowM++){
			for(int colM = -1; colM <= 1; colM++){
				if(rowM == 0 && colM == 0){
					
				}else{
				boolean continU = true;
				boolean block = false;
				int i = 0;
				int p = 0;
				
				//go in a direction and look for opponent stone
				while(theBoard[row+(rowM*i)][col+(colM*i)].getState() == s.getState() && continU == true){
					//System.out.println((row+rowM*i+rowM*1) +"\t"+(col+colM*i+colM*1));
					if(row+rowM*i + 1*rowM <= boardWidthSquares-1 && col+colM*i + 1*colM <= boardWidthSquares-1 && row+rowM*i + 1*rowM >= 0 && col+colM*i + 1*colM >= 0){
						i++;
						p++;
					} else {
						continU = false;
					}
				}
				
				int rr = row+(rowM * i);
				int cc = col+(colM * i);
				
				
				if(rr >= 0 && cc >= 0 && theBoard[rr][cc].getState() != PenteMain.EMPTY){
					p--;
					block = true;
					
				} else {
					
				}
				
				int negRR = row-(rowM*i);
				int negCC = col-(colM*i);
				
								
				i=0;
				while(negRR >= 0 && negCC >= 0 && negRR <= 18 && negCC <= 18 && theBoard[negRR][negCC].getState() == s.getState() && continU == true){
					if(row-rowM*i - 1*rowM < boardWidthSquares && col-colM*i - 1*colM < boardWidthSquares && row-rowM*i - 1*rowM >= 0 && col-colM*i - 1*colM >= 0){
						i++;
						p++;
					} else {
					//	System.out.println("Yeah I'm here!");
						continU = false;
						/*if(i == 2){
							capturePriority = true;
							points = 4000;
							System.out.println("Added 4000 points");
						}
						if(i == 4){
							winPriority = true;
							points = 20000;
							System.out.println("Added 200 points");
						}
					*/
					
				}
				}
				
			//	System.out.println("negRR is " + negRR + " and negCC is " + negCC);
				
				if(negRR >= 0 && negCC >= 0 && negRR <= 18 && negCC <= 18 && theBoard[negRR][negCC].getState() != PenteMain.EMPTY){
					p--;
					if(block){
						p = 0;
					} else {
				//		System.out.println("Something went wrong at line 206");
					}
				}
				System.out.println("Before (1st) points was: " + points);
				points += p*5;
				System.out.println("After (1st) points was: " + points);
				i=0;
				while(theBoard[row+(rowM*i)][col+(rowM*i)].getState() != s.getState() && continU == true){
					if(row+rowM*i + 1*i < boardWidthSquares && col+colM*i + 1*i < boardWidthSquares && row+rowM*i + 1*i >= 0 && col+colM*i + 1*i >= 0){
						i++;
						p++;
					} else {
						continU = false;
					//	System.out.println("Yeah I'm here!");
						/*if(i == 2){
							capturePriority = true;
							points = 3000;
							System.out.println("Added 30 points");
						}
						if(i == 4){
							winPriority = true;
							points = 10000;
							System.out.println("Added 100 points");
						*/
						
					
					}
				}if(theBoard[row+(rowM*i)][col+(rowM*i)].getState() != PenteMain.EMPTY){
					p--;
					block = true;
				}
				i=0;
				while(theBoard[row-(rowM*i)][col-(rowM*i)].getState() != s.getState() && continU == true){
					if(row-rowM*i - 1*i < boardWidthSquares && col-colM*i - 1*i < boardWidthSquares && row-rowM*i - 1*i >= 0 && col-colM*i - 1*i >= 0){
						i++;
						p++;
					} else {
						continU = false;
					}
					
				}if(theBoard[row-(rowM*i)][col-(rowM*i)].getState() != PenteMain.EMPTY){
					p--;
					if(block){
						p = 0;
					}
				}
				System.out.println("Before (2nd) points was: " + points);
				points += 5*p;
				System.out.println("After (2nd) points was: " + points);
			
			}
			}
		}
		
		int points2 = points;
		points = 0;
		System.out.println("The points are: " + points2);
		return points2;
		
	}
	
	
	public Square bestMove(Square [] tA){
		Square nextMove = null;
		Square tempValue;
		
		
		for(int end = tA.length; end  > 0; --end){
			for(int i = 0; i < end - 1; ++i){
			
				if(tA[i].getPoints() < tA[i+1].getPoints()){ // if number on left is less than number on right
					tempValue = tA[i]; 
					tA[i] = tA[i + 1]; 
					tA[i+1] = tempValue;
				}
				
			}

		
	}
		System.out.println("The best move possible is " + tA[0]);
		return tA[0];

	}
}

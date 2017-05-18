package Pent3Pac;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Square {

	int xLoc, yLoc; //top left corner position of square on board
	int sWidth; //This is the width/height of the square
	Color boardSquareColor = new Color(255, 209, 26);
	Color lineColor = new Color(150, 111, 51);
	int points = 0;
	int myRow;
	int myCol;
	
	int squareState = PenteMain.EMPTY;
	
	
	

	
	public Square(int x, int y, int w, int r, int c) {
		
		xLoc = x;
		yLoc = y;
		sWidth = w;	
		myRow = r;
		myCol = c;
		
		
		
	}
	
	public void setPoints(int set){
		points = set;
	}
	public int getPoints(){
		return points;
	}
	
	
	
	public void drawMe(Graphics g){
	
		//draws square
		g.setColor(boardSquareColor);
		g.fillRect(xLoc, yLoc, sWidth, sWidth);
		
		//draws lines
		g.setColor(lineColor);
		g.drawLine(xLoc + (int)(sWidth/2), yLoc, xLoc + (int)(sWidth/2), yLoc + sWidth);
		g.drawLine(xLoc, yLoc + (int)(sWidth/2), xLoc + sWidth, yLoc + (int)(sWidth/2));
		
		g.setColor(Color.RED);
		g.drawRect(xLoc,  yLoc,  sWidth,  sWidth);	
		
		if(squareState == PenteMain.BSTONE) {
			g.setColor(Color.BLACK);
			g.fillOval(xLoc+3, yLoc+3, sWidth-6, sWidth-6);
		}
		if(squareState == PenteMain.WSTONE) {
			g.setColor(Color.WHITE);
			g.fillOval(xLoc+3, yLoc+3, sWidth-6, sWidth-6);
		}
		
	}
	
	//This checks a mouseclick to see if it is inside a square
	
	
	
	public void setState(int newState){
		squareState = newState;
	}
	
	public int getState(){
		return squareState;
	}

	public int getRow(){
		return myRow;
	}
	
	public int getCol(){
		return myCol;
	}
	
public boolean youClickedMe(int mouseX, int mouseY) {
		
		
		boolean didYouClickIt = false;
		
		if(xLoc < mouseX && mouseX < xLoc + sWidth && yLoc < mouseY && mouseY < yLoc + sWidth){
			didYouClickIt = true;
		}
		
		return didYouClickIt;
		
	}




	

	
}

package floodControl;

import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Rectangle2D;

public class GameBoard {

	public static final int GAME_BOARD_WIDTH = 8;
	public static final int GAME_BOARD_HEIGHT = 10;
	private static final GamePiece[][] boardSquares = new GamePiece[GAME_BOARD_WIDTH][GAME_BOARD_HEIGHT];
	private final Random random;

	public GameBoard() {
		random = new Random();

		for (int i = 0; i < GAME_BOARD_WIDTH; ++i) {

			for (int j = 0; j < GAME_BOARD_HEIGHT; ++j) {

				boardSquares[i][j] = new GamePiece("Empty", "N");

			}

		}

	}

	public void randomPiece(int x, int y) {
		boardSquares[x][y].setPiece(GamePiece.pieceTypes[random.nextInt(GamePiece.maxPlayablePieceIndex + 1)], "N");
	}

	public Rectangle2D getSourceRect(int x, int y){
		return boardSquares[x][y].getSourceRect();
	}
	
	public void setPiece(int x,int y,String pieceType){
		boardSquares[x][y].setPiece(pieceType, "N");
	}
	
	public String getPieceType(int x, int y){
		return boardSquares[x][y].getPieceType();
	}
	
	public void rotatePiece(int x, int y, boolean isClockwise){
		boardSquares[x][y].rotatePiece(isClockwise);
	}
	
	public void generateNewPieces(){
		for (int y=0;y<GAME_BOARD_HEIGHT;y++){
			for (int x=0;x<GAME_BOARD_WIDTH;x++){
				randomPiece(x, y);
			}
		}
	}

	public ArrayList<GamePiece> getNeighbors(int x, int y){

		ArrayList<GamePiece> neighbors = new ArrayList<>();

		//troche brzydkie ale trudno
		switch (x){
			case 0:
				neighbors.add(new GamePiece("Left,Right","W"));
				neighbors.add((boardSquares[x+1][y]));
				switch (y){
					case 0:
						neighbors.add(new GamePiece("Empty","N"));
						neighbors.add(boardSquares[x][y+1]);
						break;
					case GAME_BOARD_HEIGHT - 1:
						neighbors.add(new GamePiece("Empty","N"));
						neighbors.add(boardSquares[x][y+1]);
					default:
						neighbors.add(boardSquares[x][y-1]);
						neighbors.add(boardSquares[x][y+1]);
						break;
				}
				break;
			case  GAME_BOARD_WIDTH - 1:
				neighbors.add(new GamePiece("Left,Right","E"));
				neighbors.add((boardSquares[x-1][y]));
				switch (y){
					case 0:
						neighbors.add(new GamePiece("Empty","N"));
						neighbors.add(boardSquares[x][y+1]);
						break;
					case GAME_BOARD_HEIGHT - 1:
						neighbors.add(new GamePiece("Empty","N"));
						neighbors.add(boardSquares[x][y+1]);
					default:
						neighbors.add(boardSquares[x][y-1]);
						neighbors.add(boardSquares[x][y+1]);
						break;
				}
			default:
				neighbors.add((boardSquares[x-1][y]));
				neighbors.add((boardSquares[x+1][y]));
				switch (y){
					case 0:
						neighbors.add(new GamePiece("Empty","N"));
						neighbors.add(boardSquares[x][y+1]);
						break;
					case GAME_BOARD_HEIGHT - 1:
						neighbors.add(new GamePiece("Empty","N"));
						neighbors.add(boardSquares[x][y+1]);
					default:
						neighbors.add(boardSquares[x][y-1]);
						neighbors.add(boardSquares[x][y+1]);
						break;
				}
				break;
		}

		return neighbors;
	}

//	private class PiecesNeighborhood{
//
//		private GamePiece topNeighbor;
//		private GamePiece bottomNeighbor;
//		private GamePiece rightNeighbor;
//		private GamePiece leftNeighbor;
//
//		public PiecesNeighborhood(){
//
//			topNeighbor = new GamePiece("Empty","N");
//			bottomNeighbor = new GamePiece("Empty","N");
//			leftNeighbor = new GamePiece("Empty","N");
//			rightNeighbor = new GamePiece("Empty","N");
//		}
//
//		public void setTopNeighbor(GamePiece NeighborPiece){
//			topNeighbor = NeighborPiece;
//		}
//
//		public void setBottomNeighbor(GamePiece NeighborPiece){
//			bottomNeighbor = NeighborPiece;
//		}
//
//		public void setLeftNeighbor(GamePiece NeighborPiece){
//			leftNeighbor = NeighborPiece;
//		}
//
//		public void setRightNeighbor(GamePiece NeighborPiece){
//			rightNeighbor = NeighborPiece;
//		}
//
//	}
}

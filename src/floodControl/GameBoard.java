package floodControl;

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
	
}

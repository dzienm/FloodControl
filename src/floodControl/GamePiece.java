package floodControl;

import javafx.geometry.Rectangle2D;

public class GamePiece {

	// moze lepiej zrobic final - czemu nie Enum??
	public static String[] pieceTypes = { "Left,Right", "Top,Bottom", "Left,Top", "Top,Right", "Right,Bottom",
			"Bottom,Left", "Empty" };

	public static final int pieceHeight = 40;
	public static final int pieceWidth = 40;
	public static final int maxPlayablePieceIndex = 2;
	public static final int emptyPieceIndex = pieceTypes.length - 1;
	private static final int textureOffsetX = 1;
	private static final int textureOffsetY = 1;
	private static final int texturePaddingX = 1;
	private static final int texturePaddingY = 1;

	private String pieceType = "";
	private String pieceSuffix = "";

	public String getPieceType() {
		return pieceType;
	}

	public String getSuffix() {
		return pieceSuffix;
	}

	public GamePiece(String type, String suffix) {
		pieceType = type;
		pieceSuffix = suffix;
	}

	public void setPiece(String type, String suffix) {
		pieceType = type;
		pieceSuffix = suffix;
	}

	public void setPieceByIndex(int ix, String suffix) {

		this.pieceType = pieceTypes[ix];
		this.pieceSuffix = suffix;
	}

	public void rotatePiece(boolean isClockwise) {

		int ix = this.getPieceIndex();
		int newIx = -1;

		if (ix < 2) {
			newIx = (ix + 1) % 2;
		}

		else if (ix >= 2 && ix < emptyPieceIndex) {

			if (isClockwise) {
				newIx = ix > 2 ? (ix - 1) : 5;

			}

			else {

				newIx = 2 + ((ix - 2) + 1) % 4;
			}
		}

		this.setPieceByIndex(newIx, this.pieceSuffix);
	}

	public int getPieceIndex() {

		int ix = -1;

		for (int i = 0; i < 7; ++i) {
			if (pieceType == pieceTypes[i])
				ix = i;
		}

		return ix;
	}

	public Rectangle2D getSourceRect() {
		int x = textureOffsetX;
		int y = textureOffsetY;
		if (pieceSuffix.contains("W")) {
			x += texturePaddingX + pieceWidth;
			y += texturePaddingY * getPieceIndex() + pieceHeight * getPieceIndex();
		} else {
			x += texturePaddingX;
			y += texturePaddingY * getPieceIndex() + pieceHeight * getPieceIndex();
		}

		return new Rectangle2D(x, y, pieceWidth, pieceHeight);
	}

}

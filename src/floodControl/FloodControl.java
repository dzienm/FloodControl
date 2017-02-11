package floodControl;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import myBox.AlertBox;

public class FloodControl {

	private GameAnimationTimer animationTimer;
	private Stage stage;
	private Image playingPieces;
	private Image backgroundScreen;
	private Image titleScreen;
	private Image titleIcon;

	private Group gameGroup;
	private Canvas gameCanvas;
	private GraphicsContext gameGraphicsContext;
	private Scene gameScene;

	private State gameState;

	private Point2D boardOrigin;
	private GameBoard gameBoard;

	private UserInputQueue userInputQueue;

	public FloodControl(Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("Flood Control");

	}

	public void run() {
		loadContent(); // jak sie zrobi odwrotnie to rysunki nie sa zaladowane i
						// aplikacja sie wysypuje
		initialize();

		stage.show();
		animationTimer = new GameAnimationTimer();
		animationTimer.start();
	}

	private void loadContent() {
		playingPieces = new Image("textures/Tile_Sheet.png"); // nazwa katalogu
																// content jest
																// jakas
																// kluczowa w
																// eclipse?
		backgroundScreen = new Image("textures/Background.png");
		titleScreen = new Image("textures/TitleScreen.png");
		titleIcon = new Image("icons/Game.png");
	}

	private void initialize() {

		gameState = State.TitleScreen;

		gameGroup = new Group();
		gameCanvas = new Canvas(800, 600);
		gameGroup.getChildren().add(gameCanvas);

		gameGraphicsContext = gameCanvas.getGraphicsContext2D();

		gameScene = new Scene(gameGroup);
		stage.setScene(gameScene);

		userInputQueue = new UserInputQueue();
		gameScene.setOnKeyPressed(keyEvent -> userInputQueue.addKey(keyEvent));
		gameScene.setOnMouseClicked(mouseEv -> userInputQueue.addMouse(mouseEv));

		boardOrigin = new Point2D(70, 89);
		gameBoard = new GameBoard();
		gameBoard.generateNewPieces();

		// stage.setAlwaysOnTop(true);
		// stage.initModality(Modality.APPLICATION_MODAL);
		stage.setIconified(true);
		stage.getIcons().add(titleIcon);
		stage.setOnCloseRequest(e -> stage_CloseRequest(e));
		stage.setResizable(false);
	}

	private void stage_CloseRequest(WindowEvent windowEvent) {

		// prosty sposob zamykania okna - ciekawe co psuje???s
		// if (AlertBox.showAndWait(AlertType.CONFIRMATION, "Flood Control", "Do
		// you want to stop the game?")
		// .orElse(ButtonType.CANCEL) == ButtonType.OK) {
		// animationTimer.stop();
		// unloadContent();
		// stage.close();
		// }

		// dlaczego runLater ??? - oryginalnie taki jest
		windowEvent.consume();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (AlertBox.showAndWait(AlertType.CONFIRMATION, "Flood Control", "Do you want to stop the game?")
						.orElse(ButtonType.CANCEL) == ButtonType.OK) {
					animationTimer.stop();
					unloadContent();
					stage.close();
				}
			}
		});
	}

	private void unloadContent() {
	}

	private void update(long currentNanoTime) {

		// mozna bylo z throws NullPointerException i zrobic catch

		KeyCode keyCode = userInputQueue.getKeyCode();
		MouseEvent mouseEv = userInputQueue.getMouse();
		
		switch (gameState) {
		case TitleScreen:
			if (keyCode == KeyCode.SPACE) {
				gameState = State.Playing;
				gameBoard.generateNewPieces();
			} else if (keyCode == KeyCode.ESCAPE) {
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}
			break;
		case Playing:
			
			if (keyCode == KeyCode.ESCAPE) {
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}
			
			if (mouseEv!=null&&mouseEv.getButton() == MouseButton.PRIMARY) {
			
				double mouseX = mouseEv.getSceneX();
				double mouseY = mouseEv.getSceneY();

				for (int y = 0; y < GameBoard.GAME_BOARD_HEIGHT; ++y) {
					for (int x = 0; x < GameBoard.GAME_BOARD_WIDTH; ++x) {
						double pixelX = boardOrigin.getX();
						double pixelY = boardOrigin.getY();

						pixelX += x * GamePiece.pieceWidth;
						pixelY += y * GamePiece.pieceHeight;

						Rectangle2D rectPiece = new Rectangle2D(pixelX, pixelY, GamePiece.pieceWidth,
								GamePiece.pieceHeight);
						if (rectPiece.contains(new Point2D(mouseX, mouseY))) {
							if (mouseEv.isPrimaryButtonDown()) {
								gameBoard.rotatePiece(x, y, true);
							}
						}
					}
				
			}
			}
			
			break;
		}

	}

	private void draw(long currentNanoTime) {
		gameGraphicsContext.clearRect(0, 0, 800, 600);

		switch (gameState) {
		case TitleScreen:
			gameGraphicsContext.drawImage(titleScreen, 0, 0);
			break;

		case Playing:
			gameGraphicsContext.drawImage(backgroundScreen, 0, 0);

			for (int y = 0; y < GameBoard.GAME_BOARD_HEIGHT; ++y) {
				for (int x = 0; x < GameBoard.GAME_BOARD_WIDTH; ++x) {
					double pixelX = boardOrigin.getX();
					double pixelY = boardOrigin.getY();

					pixelX += x * GamePiece.pieceWidth;
					pixelY += y * GamePiece.pieceHeight;

					PixelReader pixReader = playingPieces.getPixelReader();
					WritableImage emptyPiece = new WritableImage(pixReader, 1, 247, GamePiece.pieceWidth,
							GamePiece.pieceHeight);
					gameGraphicsContext.drawImage(emptyPiece, pixelX, pixelY);
					WritableImage actualPiece = new WritableImage(pixReader,
							(int) gameBoard.getSourceRect(x, y).getMinX(),
							(int) gameBoard.getSourceRect(x, y).getMinY(),
							(int) gameBoard.getSourceRect(x, y).getWidth(),
							(int) gameBoard.getSourceRect(x, y).getHeight());
					gameGraphicsContext.drawImage(actualPiece, pixelX, pixelY);
				}
			}
			break;

		}

	}

	private class GameAnimationTimer extends AnimationTimer {

		@Override
		public void handle(long currentNanoTime) {
			update(currentNanoTime);// aktualizacja stanu gry
			draw(currentNanoTime);// aktualizacja widoku
		}

	}

	private enum State {
		TitleScreen, Playing;
	}

}

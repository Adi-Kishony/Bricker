package bricker.main;

import bricker.brick_strategies.CollisionStrategiesFactory;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import danogl.gui.UserInputListener;

import java.awt.event.KeyEvent;


/**
 * The main class for the Bricker game, extending GameManager.
 */
public class BrickerGameManager extends GameManager {
    /**
     * numRows field represent the number of rows of the bricks.
     */
    private final int numRows;

    /**
     * numCols field represent the number of cols of the bricks.
     */
    private final int numCols;

    /**
     * windowDimensions field represent dimensions of the game's window.
     */
    private Vector2 windowDimensions;

    /**
     * bricksLeft field represent the number of the left bricks on the game.
     */
    private Counter bricksLeft;

    /**
     * imageReader field represent image reader.
     */
    private ImageReader imageReader;

    /**
     * livesManager field represent the lives manger of the game.
     */
    private LivesManager livesManager;

    /**
     * soundReader field represent the sound of the ball's collisions.
     */
    private SoundReader soundReader;

    /**
     * windowController field represent the window controller.
     */
    private WindowController windowController;

    /**
     * mainBall field represent the main ball of the game.
     */
    private Ball mainBall;

    /**
     * inputListener field represent the input from the user
     * regarding the direction for the paddle's movement.
     */
    private UserInputListener inputListener;

    /**
     * Constructs a BrickerGameManager with specified window title, dimensions, number of rows, and number of
     * columns.
     *
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param numRows          The number of rows of bricks in the game.
     * @param numCols          The number of columns of bricks in the game.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numRows, int numCols) {
        super(windowTitle, windowDimensions);
        if (numCols == 0 || numRows == 0) {
            this.numRows = Constants.NUM_ROWS_OF_BRICKS;
            this.numCols = Constants.NUM_COLS_OF_BRICKS;
        } else {
            this.numRows = numRows;
            this.numCols = numCols;
        }
    }

    /**
     * Removes the specified GameObject from the game at the given layer.
     *
     * @param obj   The GameObject to be removed.
     * @param layer The layer from which the GameObject should be removed.
     */
    public void removeGameObject(GameObject obj, int layer) {
        gameObjects().removeGameObject(obj, layer);
    }

    /**
     * Removes the specified GameObject from the game.
     *
     * @param obj The GameObject to be removed.
     */
    public void removeGameObject(GameObject obj) {
        gameObjects().removeGameObject(obj);
    }

    /**
     * Adds the specified GameObject to the game at the given layer.
     *
     * @param obj   The GameObject to be added.
     * @param layer The layer to which the GameObject should be added.
     */
    public void addGameObject(GameObject obj, int layer) {
        gameObjects().addGameObject(obj, layer);
    }

    /**
     * Adds the specified GameObject to the game.
     *
     * @param obj The GameObject to be added.
     */
    public void addGameObject(GameObject obj) {
        gameObjects().addGameObject(obj);
    }

    /**
     * Retrieves the UserInputListener associated with this game.
     *
     * @return The UserInputListener for this game.
     */
    public UserInputListener getInputListener() {
        return inputListener;
    }

    /**
     * Retrieves the ImageReader associated with this game.
     *
     * @return The ImageReader for this game.
     */
    public ImageReader getImageReader() {
        return imageReader;
    }

    /**
     * Retrieves the SoundReader associated with this game.
     *
     * @return The SoundReader for this game.
     */
    public SoundReader getSoundReader() {
        return soundReader;
    }

    /**
     * Initializes the game with provided resources and settings.
     *
     * @param imageReader      The ImageReader for loading images.
     * @param soundReader      The SoundReader for loading sounds.
     * @param inputListener    The UserInputListener for handling user input.
     * @param windowController The WindowController for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {
        // Initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.bricksLeft = new Counter(numRows * numCols);
        this.windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.inputListener = inputListener;
        windowController.setTargetFramerate(80);

        // create background
        Renderable bgImage = imageReader.readImage(Constants.BG_IMG_PATH, false);
        GameObject background =
                new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), windowDimensions.y()),
                        bgImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        addGameObject(background, Layer.BACKGROUND);

        // create ball
        Renderable ballImage = imageReader.readImage(Constants.BALL_IMG_PATH, true);
        this.mainBall = createBall(ballImage, new Vector2(Constants.BALL_RADIUS, Constants.BALL_RADIUS));

        // create user paddle
        createPaddle(new Vector2(windowDimensions.x() / 2,
                (int) windowDimensions.y() - Constants.DIST_FROM_EDGE_OF_DISPLAY));

        // create walls
        createWalls();

        //add remaining lives graphics
        Renderable heartImage = imageReader.readImage(Constants.HEART_IMG_PATH, true);
        this.livesManager = new LivesManager(new Vector2(Constants.LIVES_START_PIXEL, windowDimensions.y()
                - Constants.DISTANCE_FROM_BOTTOM),
                new Vector2(Constants.HEART_DIMENSIONS, Constants.HEART_DIMENSIONS),
                heartImage, Constants.INITIAL_NUM_LIVES, this);

        //add bricks
        Renderable brickImage = imageReader.readImage(Constants.BRICK_IMG_PATH, false);
        addBricks(brickImage);
    }

    /**
     * Updates the game state based on elapsed time.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        boolean isGameWon = gameWon();
        boolean isGameLost = gameLost();
        if (inputListener.isKeyPressed(KeyEvent.VK_W)) {
            endOfGameUI(Constants.WIN_MESSAGE);
        }
        if (isGameWon) {
            endOfGameUI(Constants.WIN_MESSAGE);
        } else if (isGameLost) {
            endOfGameUI(Constants.LOSE_MESSAGE);
        }
    }

    /**
     * Creates the walls surrounding the game area.
     */
    private void createWalls() {
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(Constants.BORDER_WIDTH,
                windowDimensions.y()), null);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(), 0), new Vector2
                (Constants.BORDER_WIDTH, windowDimensions.y()), null);
        GameObject topWall = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(),
                Constants.BORDER_WIDTH), null);
        gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(topWall, Layer.STATIC_OBJECTS);
    }

    /**
     * Adds bricks to the game based on the specified brick image and collision strategies.
     *
     * @param brickImage The Renderable representing the brick image.
     */
    private void addBricks(Renderable brickImage) {
        CollisionStrategy collisionStrategy;
        CollisionStrategiesFactory collisionStrategiesFactory = new CollisionStrategiesFactory(
                this, windowDimensions, mainBall, livesManager);

        float brickWidth = windowDimensions.x() / numCols - Constants.PADDING_PIXELS;
        Vector2 brickDims = new Vector2(brickWidth, Constants.BRICK_HEIGHT);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                collisionStrategy = collisionStrategiesFactory.generateCollisionStrategy();
                Vector2 brickLoc = new Vector2(j * (brickWidth + Constants.PADDING_PIXELS) +
                        Constants.PADDING_PIXELS, i * (Constants.BRICK_HEIGHT + Constants.PADDING_PIXELS) +
                        Constants.PADDING_PIXELS);
                createBrick(brickImage, collisionStrategy, brickDims, brickLoc);
            }
        }
    }

    /**
     * Creates a ball in the game with the specified image and dimensions.
     *
     * @param ballImage      The Renderable representing the ball image.
     * @param ballDimensions The dimensions of the ball.
     * @return The created Ball object.
     */
    private Ball createBall(Renderable ballImage, Vector2 ballDimensions) {
        Sound collisionSound = soundReader.readSound(Constants.BLOP_SOUND_PATH);
        Ball ball = new Ball(Vector2.ZERO, ballDimensions, ballImage, collisionSound);
        ball.reCenterBall(Constants.BALL_SPEED, windowDimensions.mult(Constants.MULTIPLY_FACTOR_HALF));
        addGameObject(ball);
        return ball;
    }

    /**
     * Creates the user paddle in the game with the specified center coordinates.
     *
     * @param paddleCenter The center coordinates of the user paddle.
     */
    private void createPaddle(Vector2 paddleCenter) {
        Renderable paddleImage = imageReader.readImage(Constants.PADDLE_IMG_PATH, true);
        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                paddleImage,
                inputListener,
                windowDimensions);
        userPaddle.setCenter(paddleCenter);
        gameObjects().addGameObject(userPaddle);
    }

    /**
     * Creates a brick in the game with the specified image, collision strategy, dimensions, and location.
     *
     * @param brickImage        The Renderable representing the brick image.
     * @param collisionStrategy The CollisionStrategy for the brick.
     * @param brickDims         The dimensions of the brick.
     * @param brickLoc          The location of the brick.
     */
    private void createBrick(Renderable brickImage, CollisionStrategy collisionStrategy, Vector2 brickDims,
                             Vector2 brickLoc) {
        GameObject brick = new Brick(brickLoc, brickDims, brickImage, collisionStrategy, bricksLeft);
        gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
    }

    /**
     * Checks if the game has been won based on the number of remaining bricks.
     *
     * @return True if the game is won, false otherwise.
     */
    private boolean gameWon() {
        return (bricksLeft.value() == 0);
    }


    /**
     * Checks if the game has been lost based on the current position of the ball and the remaining lives.
     * If the ball goes below the game window's height and there are no remaining lives, the game is
     * considered lost.
     * If there are remaining lives, one life is deducted, and the ball is recentered for the next attempt.
     *
     * @return True if the game is lost, false otherwise.
     */
    private boolean gameLost() {
        // Get the current height of the ball's center
        float ballHeight = mainBall.getCenter().y();

        // Check if the ball is below the game window's height
        if (ballHeight > windowController.getWindowDimensions().y()) {
            // Check if there are no remaining lives
            if (livesManager.getCurrentLives() == Constants.LAST_LIFE_NUMBER) {
                // No more lives, end the game
                return true;
            } else {
                // Deduct one life and recenter the ball for the next attempt
                livesManager.removeLife();
                mainBall.reCenterBall(Constants.BALL_SPEED, windowDimensions.mult
                        (Constants.MULTIPLY_FACTOR_HALF));
            }
        }
        // The game is not lost
        return false;
    }

    /**
     * Displays the end-of-game user interface with the specified prompt.
     * Appends the play-again message to the prompt and allows the user to reset the game or close the window.
     *
     * @param prompt The prompt to be displayed in the end-of-game UI.
     */
    private void endOfGameUI(String prompt) {
        prompt += Constants.PLAY_AGAIN_MESSAGE;
        if (windowController.openYesNoDialog(prompt)) {
            // User chose to play again, reset the game
            windowController.resetGame();
        } else {
            // User chose to close the window
            windowController.closeWindow();
        }
    }

    /**
     * The main method to launch the Bricker game.
     * Parses command-line arguments to determine the number of rows and columns for the game grid.
     * Creates an instance of BrickerGameManager and starts the game.
     *
     * @param args Command-line arguments. Expects two arguments: numRows and numCols.
     */
    public static void main(String[] args) {
        int numRows = 0;
        int numCols = 0;

        // Check if the expected number of command-line arguments is provided
        if (args.length == Constants.EXPECTED_ARGS_LEN) {
            // Parse command-line arguments to determine the number of rows and columns
            numRows = Integer.parseInt(args[0]);
            numCols = Integer.parseInt(args[1]);
        }

        // Create an instance of BrickerGameManager with specified parameters
        BrickerGameManager gameManager = new BrickerGameManager("Bricker",
                new Vector2(Constants.BOARD_GAME_WIDTH, Constants.BOARD_GAME_HIGH), numRows, numCols);

        // Start the game
        gameManager.run();
    }
}
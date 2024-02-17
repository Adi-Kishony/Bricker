package bricker;

/**
 * The Constants class contains static final variables that define various constant values
 * used throughout the Bricker game classes. These values include dimensions, thresholds,
 * paths to asset files, and messages displayed during the game.
 */
public class Constants {

    // Game's Board dimensions
    public static final int BOARD_GAME_WIDTH = 500;
    public  static  final int BOARD_GAME_HIGH = 500;

    // Expected command-line arguments length
    public static  final int EXPECTED_ARGS_LEN = 2;

    // Multiplication factor
    public static final float MULTIPLY_FACTOR_HALF = 0.5f;

    // Last life and brick numbers
    public static final  int LAST_LIFE_NUMBER = 1;
    public static final int LAST_BRICK_NUMBER = 1;

    // Collision strategy indices and range
    public static final int BASIC_STRATEGY_START_INDEX = 5;
    public static final int RANDOM_STRATEGY_RANGE = 10;

    // Ball speed and radius
    public static final float BALL_SPEED = 250;
    public static final int BALL_RADIUS = 25;

    // Border and paddle dimensions
    public static final int BORDER_WIDTH = 10;
    public static final int PADDLE_WIDTH = 150;
    public static final int PADDLE_HEIGHT = 20;

    // brick dimensions, and bricks amount
    public static  final int BRICK_HEIGHT = 15;
    public static final int NUM_ROWS_OF_BRICKS = 7;
    public static final int NUM_COLS_OF_BRICKS = 8;

    // space between each two bricks
    public static final int PADDING_PIXELS = 3;

    // the distance of the main paddle from the bottom of the game board
    public static final int DIST_FROM_EDGE_OF_DISPLAY = 50;

    // Lives display dimensions and initial values
    public static  final  int LIVES_START_PIXEL = 20;
    public static  final  int HEART_DIMENSIONS = 20;
    public static final  int INITIAL_NUM_LIVES = 3;
    public static final int MAX_NUM_LIVES = 4;
    public static final int DISTANCE_FROM_BOTTOM = 30;

    // Heart velocity
    public static final int HEART_VELOCITY = 100;

    // space between two hearts
    public final static float HEART_PADDING = 6;

    // win message
    public static final String WIN_MESSAGE = "You Win!";

    // lose message
    public static final String LOSE_MESSAGE = "You Lose!";

    // message for asking the player if he wants to play again
    public static final String PLAY_AGAIN_MESSAGE =" Play again?";

    // Asset paths
    public static final String HEART_IMG_PATH = "assets/heart.png";
    public static final String BALL_IMG_PATH = "assets/ball.png";
    public static final String BRICK_IMG_PATH = "assets/brick.png";
    public static final String PADDLE_IMG_PATH = "assets/paddle.png";
    public static final String PUCK_IMG_PATH = "assets/mockBall.png";
    public static final String BG_IMG_PATH = "assets/DARK_BG2_small.jpeg";
    public static final String BLOP_SOUND_PATH = "assets/blop_cut_silenced.wav";

}

package bricker.main;

/**
 * The Constants class contains static final variables that define various constant values
 * used throughout the Bricker game classes. These values include dimensions, thresholds,
 * paths to asset files, and messages displayed during the game.
 */
public class Constants {
    /**
     * BOARD_GAME_WIDTH constant represent the game's Board width.
     */
    public static final int BOARD_GAME_WIDTH = 500;

    /**
     * BOARD_GAME_HIGH constant represent the game's Board high.
     */
    public  static  final int BOARD_GAME_HIGH = 500;

    /**
     * EXPECTED_ARGS_LEN constant represent the expected command-line arguments length.
     */
    public static  final int EXPECTED_ARGS_LEN = 2;

    /**
     * MULTIPLY_FACTOR_HALF constant represent the multiplication factor.
     */
    public static final float MULTIPLY_FACTOR_HALF = 0.5f;

    /**
     * BASIC_STRATEGY_START_INDEX constant represent the collision strategy indices.
     */
    public static final int BASIC_STRATEGY_START_INDEX = 5;

    /**
     * LAST_LIFE_NUMBER constant represent the last life numbers.
     */
    public static final  int LAST_LIFE_NUMBER = 1;
    /**
     * RANDOM_STRATEGY_RANGE constant represent the collision strategy range.
     */
    public static final int RANDOM_STRATEGY_RANGE = 10;

    /**
     * BALL_SPEED constant represent the ball speed.
     */
    public static final float BALL_SPEED = 250;

    /**
     * BALL_RADIUS constant represent the ball radius.
     */
    public static final int BALL_RADIUS = 25;

    /**
     * BORDER_WIDTH constant represent the border width.
     */
    public static final int BORDER_WIDTH = 10;

    /**
     * PADDLE_WIDTH constant represent the paddle width.
     */
    public static final int PADDLE_WIDTH = 150;

    /**
     * PADDLE_HEIGHT constant represent the border high.
     */
    public static final int PADDLE_HEIGHT = 20;


    /**
     * BRICK_HEIGHT constant represent the brick high.
     */
    public static  final int BRICK_HEIGHT = 15;

    /**
     * NUM_ROWS_OF_BRICKS constant represent
     * the numbers of the bricks rows.
     */
    public static final int NUM_ROWS_OF_BRICKS = 7;

    /**
     * NUM_COLS_OF_BRICKS constant represent the numbers of the bricks columns.
     */
    public static final int NUM_COLS_OF_BRICKS = 8;

    /**
     *  PADDING_PIXELS constant represent the space between each two bricks.
     */
    public static final int PADDING_PIXELS = 3;

    /**
     *  DIST_FROM_EDGE_OF_DISPLAY constant represent
     *  the distance of the main paddle from the bottom of the game board.
     */
    public static final int DIST_FROM_EDGE_OF_DISPLAY = 50;

    /**
     * LIVES_START_PIXEL constant represent the graphical lives initial start pixel.
     */
    public static  final  int LIVES_START_PIXEL = 20;

    /**
     * HEART_DIMENSIONS constant represent the heart dimension.
     */
    public static  final  int HEART_DIMENSIONS = 20;

    /**
     * INITIAL_NUM_LIVES constant represent the initial lives number.
     */
    public static final  int INITIAL_NUM_LIVES = 3;

    /**
     * MAX_NUM_LIVES constant represent the maximum possible lives in the game.
     */
    public static final int MAX_NUM_LIVES = 4;

    /**
     * DISTANCE_FROM_BOTTOM constant represent the distance of the graphical hearts
     * which are not dynamic from the bottom of the screen.
     */
    public static final int DISTANCE_FROM_BOTTOM = 30;

    /**
     * HEART_VELOCITY constant represent the heart velocity.
     */
    public static final int HEART_VELOCITY = 100;

    /**
     * HEART_PADDING constant represent the space between two hearts.
     */
    public final static float HEART_PADDING = 6;

    /**
     * WIN_MESSAGE constant represent the win message.
     */
    public static final String WIN_MESSAGE = "You Win!";

    /**
     * LOSE_MESSAGE constant represent the loose message.
     */
    public static final String LOSE_MESSAGE = "You Lose!";

    /**
     * PLAY_AGAIN_MESSAGE constant represent the message
     * for asking the player if he wants to play again.
     */
    public static final String PLAY_AGAIN_MESSAGE =" Play again?";

    /**
     * HEART_IMG_PATH constant represent the asset path of the heart image.
     */
    public static final String HEART_IMG_PATH = "assets/heart.png";

    /**
     * BALL_IMG_PATH constant represent the asset path of the main ball image.
     */
    public static final String BALL_IMG_PATH = "assets/ball.png";

    /**
     * BRICK_IMG_PATH constant represent the asset path of the brick image.
     */
    public static final String BRICK_IMG_PATH = "assets/brick.png";

    /**
     * PADDLE_IMG_PATH constant represent the asset path of the paddle image.
     */
    public static final String PADDLE_IMG_PATH = "assets/paddle.png";

    /**
     * PUCK_IMG_PATH constant represent the asset path of the puck ball image.
     */
    public static final String PUCK_IMG_PATH = "assets/mockBall.png";

    /**
     * BG_IMG_PATH constant represent the asset path of the background image.
     */
    public static final String BG_IMG_PATH = "assets/DARK_BG2_small.jpeg";

    /**
     * BLOP_SOUND_PATH constant represent the asset path of the blop sound.
     */
    public static final String BLOP_SOUND_PATH = "assets/blop_cut_silenced.wav";

    private Constants() {
        // This constructor is private to prevent instantiation of the Constants class.
        // All members are accessed through static references.
    }

}

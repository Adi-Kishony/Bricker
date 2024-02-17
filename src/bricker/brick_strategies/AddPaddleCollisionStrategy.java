package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import bricker.gameobjects.SecondPaddle;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A collision strategy that adds a second paddle to the game when a collision with a brick occurs.
 * The second paddle is created at the center of the game window.
 */
public class AddPaddleCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy {

    /**
     * The dimensions of the game window.
     */
    private final Vector2 windowDimensions;

    /**
     * The SecondPaddle object representing the second paddle added to the game.
     */
    private SecondPaddle secondPaddle;

    /**
     * A flag indicating whether a second paddle already exists in the game.
     */
    private static boolean existsPaddle = false;

    /**
     * Constructs an AddPaddleCollisionStrategy with the specified parameters.
     *
     * @param brickerGameManager The BrickerGameManager associated with this collision strategy.
     * @param windowDimensions   The dimensions of the game window.
     */
    public AddPaddleCollisionStrategy(BrickerGameManager brickerGameManager, Vector2 windowDimensions) {
        super(brickerGameManager);
        this.windowDimensions = windowDimensions;
    }

    /**
     * Executes the AddPaddleCollisionStrategy, adding a second paddle to the game when a collision
     * occurs with the main ball.
     *
     * @param obj1 The first game object involved in the collision (to be removed).
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        // Check if a second paddle already exists
        if (!existsPaddle) {
            existsPaddle = true;
            // Create a SecondPaddle at the center of the game window
            Renderable paddleImage = brickerGameManager.getImageReader().readImage(Constants.PADDLE_IMG_PATH,
                    true);
            this.secondPaddle = new SecondPaddle(
                    windowDimensions.mult(Constants.MULTIPLY_FACTOR_HALF),
                    new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                    paddleImage,
                    brickerGameManager.getInputListener(),
                    windowDimensions, this);
            brickerGameManager.addGameObject(this.secondPaddle);
        }
    }

    /**
     * Removes the SecondPaddle from the game.
     * Resets the existsPaddle flag to indicate that no second paddle exists.
     */
    public void removePaddle() {
        brickerGameManager.removeGameObject(secondPaddle);
        existsPaddle = false;
    }
}
package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import bricker.main.LivesManager;
import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A collision strategy that adds an extra life to the player when a collision with a brick occurs.
 * This strategy creates a Heart object at the location of the collided brick, and catching the Heart
 * grants the player an additional life.
 */
public class AddLifeCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy {

    /**
     * The LivesManager responsible for managing the player's lives.
     */
    private final LivesManager livesManager;

    /**
     * The dimensions of the game window.
     */
    private final Vector2 windowDimensions;

    /**
     * Constructs an AddLifeCollisionStrategy with the specified parameters.
     *
     * @param brickerGameManager The BrickerGameManager associated with this collision strategy.
     * @param windowDimensions   The dimensions of the game window.
     * @param livesManager       The LivesManager responsible for managing the player's lives.
     */
    public AddLifeCollisionStrategy(BrickerGameManager brickerGameManager, Vector2 windowDimensions,
                                    LivesManager livesManager) {
        super(brickerGameManager);
        this.livesManager = livesManager;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Executes the AddLifeCollisionStrategy, creating a Heart object at the location of the collided brick
     * when a collision occurs with the main ball.
     *
     * @param obj1 The first game object involved in the collision (to be removed).
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        // Create a Heart at the location of the collided brick
        Renderable heartRenderable = brickerGameManager.getImageReader().readImage(Constants.HEART_IMG_PATH,
                true);
        Heart heart = new Heart(obj1.getCenter(), new Vector2(Constants.HEART_DIMENSIONS,
                Constants.HEART_DIMENSIONS), heartRenderable, this);
        heart.setVelocity(new Vector2(0, Constants.HEART_VELOCITY));
        brickerGameManager.addGameObject(heart);
    }

    /**
     * Handles the event when a Heart is caught by the player.
     * Removes the Heart from the game and adds an extra life to the player.
     *
     * @param heart The Heart object caught by the player.
     */
    public void caughtHeart(Heart heart) {
        brickerGameManager.removeGameObject(heart);
        livesManager.addLife();
    }

    /**
     * Checks if a Heart goes out of bounds and removes it from the game if necessary.
     *
     * @param heart The Heart object to check for being out of bounds.
     */
    public void checkOutOfBounds(Heart heart) {
        float heartHeight = heart.getDimensions().y();
        if (heartHeight > windowDimensions.y()) {
            brickerGameManager.removeGameObject(heart);
        }
    }
}
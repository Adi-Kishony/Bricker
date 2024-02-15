package bricker.gameobjects;

import bricker.brick_strategies.AddBallsCollisionStrategy;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Puck class represents a game object in the form of a puck ball in the Bricker game.
 * It extends the Ball class and introduces additional behavior related to a collision
 * strategy for adding balls. The puck has a specific tag, and its update method
 * includes a call to the creator's method for checking whether to remove the puck or not.
 */
public class Puck extends Ball {
    // Constants
    private static final String PUCK_TAG = "Puck";

    // Instance variable
    private final AddBallsCollisionStrategy myCreator;

    /**
     * Constructs a Puck ball object with the specified parameters.
     * @param topLeftCorner   The top-left corner position of the puck ball.
     * @param dimensions      The dimensions of the puck ball.
     * @param renderable      The renderable image for the puck ball.
     * @param collisionSound  The sound to play on collisions.
     * @param myCreator       The collision strategy responsible for creating the puck ball.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                AddBallsCollisionStrategy myCreator) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.myCreator = myCreator;
    }

    /**
     * Updates the puck's state, including a call to the creator's method for checking
     * whether to remove the puck ball from the game's objects or not.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // check if puck ball is out of the gme board range, and remove it if needed.
        myCreator.removeCheckPuck(this);
    }


    /**
     * Returns the tag associated with the puck.
     * @return The tag string representing the puck.
     */
    @Override
    public String getTag() {
        return PUCK_TAG;
    }
}

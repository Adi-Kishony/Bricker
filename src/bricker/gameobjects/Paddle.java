package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * The Paddle class represents a game object in the form of a paddle in the Bricker game.
 * It extends the danogl.GameObject class and defines the behavior of the paddle, including
 * user input handling for movement and tag information.
 * The class takes input from a UserInputListener to move the paddle left or right based on
 * keyboard events. It restricts movement within the window dimensions and sets the paddle's
 * velocity accordingly.
 */
public class Paddle extends GameObject {
    /**
     * PADDLE_TAG constant represent the paddle's tag.
     */
    public static final String PADDLE_TAG = "Paddle";

    /**
     * MOVEMENT_SPEED constant represent the speed of the paddle.
     */
    private static final float MOVEMENT_SPEED = 300;

    /**
     * inputListener field represent input from the player regarding the direction for moving the paddle.
     */
    private final UserInputListener inputListener;

    /**
     * windowDimensions field represent the dimensions of the game's board.
     */
    private final Vector2 windowDimensions;

    /**
     * Constructs a Paddle object with the specified parameters.
     * @param topLeftCorner     The top-left corner position of the paddle.
     * @param dimensions        The dimensions of the paddle.
     * @param renderable        The renderable image for the paddle.
     * @param inputListener     The user input listener for handling paddle movement.
     * @param windowDimensions  The dimensions of the game window.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Returns the tag associated with the paddle for.
     * @return The tag string representing the paddle.
     */
    @Override
    public String getTag() {
        return PADDLE_TAG;
    }

    /**
     * Updates the paddle's position based on user input and restricts movement within
     * the window dimensions. Sets the velocity accordingly for smooth movement.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;

        // handel case when the player tries to move the paddle to the left:
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            // if the paddle will not cross the game board range, enable the movement
            if (this.getTopLeftCorner().x() > 0) {
                movementDir = movementDir.add(new Vector2(Vector2.LEFT));
            }
        }

        // handel case when the player tries to move the paddle to the right:
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            // if the paddle will not cross the game board range, enable the movement
            if (this.getTopLeftCorner().x() + this.getDimensions().x() < windowDimensions.x()) {
                movementDir = movementDir.add(new Vector2(Vector2.RIGHT));
            }
        }

        // set the new velocity of the paddle
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}

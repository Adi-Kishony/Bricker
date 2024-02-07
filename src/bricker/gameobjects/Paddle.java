package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;

    private Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public String getTag() {
        return "Paddle";
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;

        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            if (this.getTopLeftCorner().x() > 0) {
                movementDir = movementDir.add(new Vector2(Vector2.LEFT));
            }
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            if (this.getTopLeftCorner().x() + this.getDimensions().x() < windowDimensions.x()) {
                movementDir = movementDir.add(new Vector2(Vector2.RIGHT));
            }
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}

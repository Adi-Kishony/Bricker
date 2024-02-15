package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    public static final String PADDLE_TAG = "Paddle";
    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public String getTag() {
        return PADDLE_TAG;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (this.getTopLeftCorner().x() > 0) {
                movementDir = movementDir.add(new Vector2(Vector2.LEFT));
            }
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (this.getTopLeftCorner().x() + this.getDimensions().x() < windowDimensions.x()) {
                movementDir = movementDir.add(new Vector2(Vector2.RIGHT));
            }
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}

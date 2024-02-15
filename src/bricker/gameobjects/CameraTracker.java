package bricker.gameobjects;

import bricker.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The CameraTracker class represents a game object responsible for tracking the main ball's
 * collisions and controlling the game camera in the Bricker game. It extends the danogl.GameObject
 * class and provides functionality to activate and deactivate the camera based on ball collisions.
 * The class interacts with the BrickerGameManager, Camera, and Ball to manage the camera's behavior.
 */
public class CameraTracker extends GameObject {
    // Constants
    private static final int MAX_BALL_COLLISIONS = 4;

    // Instance variables
    private final BrickerGameManager brickerGameManager;
    private final Camera camera;
    private final Ball mainBall;
    private int numCollisions;

    /**
     * Constructs a CameraTracker object with the specified parameters.
     * @param topLeftCorner      The top-left corner position of the camera tracker.
     * @param dimensions         The dimensions of the camera tracker.
     * @param renderable         The renderable image for the camera tracker.
     * @param brickerGameManager The game manager associated with the Bricker game.
     * @param camera             The camera object used for tracking.
     * @param mainBall           The main ball object that being tracked.
     */
    public CameraTracker(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager
            brickerGameManager, Camera camera, Ball mainBall) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.camera = camera;
        this.mainBall = mainBall;
        this.numCollisions = 0;
    }

    /**
     * Activates the camera to follow the specified ball.
     * @param ball The game object to be followed by the camera.
     */
    public void setCameraOn(GameObject ball) {
        brickerGameManager.setCamera(camera);
        // instruct the camera to follow the ball
        brickerGameManager.camera().setToFollow(ball, Vector2.ZERO);
        numCollisions = mainBall.getCollisionCounter().value();
    }

    /**
     * Deactivates the camera.
     */
    private void setCameraOff() {
        brickerGameManager.setCamera(null);
    }

    /**
     * Updates the camera tracker's state based on the number of ball collisions.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        // if the camera is on
        if (brickerGameManager.camera() != null) {
            // if since the camera was turned on 4 collision were made
            if (mainBall.getCollisionCounter().value() - numCollisions == MAX_BALL_COLLISIONS) {
                // set the camera off
                setCameraOff();
            }
        }
    }
}


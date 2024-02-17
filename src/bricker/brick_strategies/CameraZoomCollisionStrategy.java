package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.gameobjects.Ball;
import bricker.gameobjects.CameraTracker;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

/**
 * A collision strategy that triggers a camera zoom effect when a collision with a brick occurs.
 * This strategy creates a new camera and a CameraTracker to follow the main ball, providing a zoomed-in view.
 */
public class CameraZoomCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy {

    /**
     * The ratio by which the camera will zoom in.
     */
    private static final float ZOOM_RATIO = 1.2f;

    /**
     * The main ball object in the game.
     */
    private final Ball mainBall;

    /**
     * The dimensions of the game window.
     */
    private final Vector2 windowDimensions;

    /**
     * The camera responsible for the zoom effect.
     */
    private Camera camera;

    /**
     * The CameraTracker object that follows the main ball and controls the camera.
     */
    private CameraTracker cameraTracker;

    /**
     * Constructs a CameraZoomCollisionStrategy with the specified parameters.
     *
     * @param brickerGameManager The BrickerGameManager associated with this collision strategy.
     * @param windowDimensions   The dimensions of the game window.
     * @param mainBall           The main ball object in the game.
     */
    public CameraZoomCollisionStrategy(BrickerGameManager brickerGameManager, Vector2 windowDimensions,
                                       Ball mainBall) {
        super(brickerGameManager);
        this.camera = null;
        this.cameraTracker = null;
        this.mainBall = mainBall;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Executes the CameraZoomCollisionStrategy, triggering a camera zoom effect when a collision occurs
     * with the main ball and creating a new camera and CameraTracker.
     *
     * @param obj1 The first game object involved in the collision (to be removed).
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        // Check if a camera is not already present
        if (brickerGameManager.camera() == null) {
            // Create a new camera and CameraTracker
            camera = new Camera(null, Vector2.ZERO,
                    windowDimensions.mult(ZOOM_RATIO),
                    windowDimensions);

            cameraTracker = new CameraTracker(Vector2.ZERO, Vector2.ZERO, null,
                    brickerGameManager, camera, mainBall);
            brickerGameManager.addGameObject(cameraTracker);
            // Set the camera to follow the main ball if the collision involves the main ball
            if (obj2.getTag().equals(Ball.BALL_TAG)) {
                cameraTracker.setCameraOn(obj2);
            }
        }
    }
}
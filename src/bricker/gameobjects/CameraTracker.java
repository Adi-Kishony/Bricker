package bricker.gameobjects;

import bricker.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class CameraTracker extends GameObject {
    private static final int MAX_BALL_COLLISIONS = 4;
    private final BrickerGameManager brickerGameManager;
    private final Camera camera;
    private final Ball mainBall;
    private int numCollisions;

    public CameraTracker(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager
            brickerGameManager, Camera camera, Ball mainBall) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.camera = camera;
        this.mainBall = mainBall;
        this.numCollisions = 0;
    }

    public void setCameraOn(GameObject ball) {
        brickerGameManager.setCamera(camera);
        brickerGameManager.camera().setToFollow(ball, Vector2.ZERO);
        numCollisions = mainBall.getCollisionCounter().value();
    }

    private void setCameraOff() {
        brickerGameManager.setCamera(null);
    }

    @Override
    public void update(float deltaTime) {
        if (brickerGameManager.camera() != null) {
            if (mainBall.getCollisionCounter().value() - numCollisions == MAX_BALL_COLLISIONS) {
                System.out.println(mainBall.getCollisionCounter().value() - numCollisions);
                setCameraOff();
            }
        }
    }
}


package bricker.gameobjects;

import bricker.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class CameraTracker extends GameObject {
    private final BrickerGameManager brickerGameManager;
    private final Camera camera;
    private static final int MAX_BALL_COLLISIONS = 10;
    private final Ball mainBall;
    private int numCollisions;

    public CameraTracker(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager brickerGameManager, Camera camera) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.camera = camera;
        this.mainBall = brickerGameManager.getMainBall();
        this.numCollisions = 0;
    }

    public void setCameraOn(GameObject ball) {
        brickerGameManager.setCamera(camera);
        brickerGameManager.camera().setToFollow(ball, Vector2.ZERO);
        numCollisions = mainBall.getCollisionCounter().value();
        System.out.println("on");
    }


    private void setCameraOff() {
        brickerGameManager.setCamera(null);
    }

    @Override
    public void update(float deltaTime) {
        if (brickerGameManager.camera() != null) {
            System.out.println("firstIf");
            if (mainBall.getCollisionCounter().value() - numCollisions >= MAX_BALL_COLLISIONS) {
                System.out.println(mainBall.getCollisionCounter().value() - numCollisions);
                System.out.println("turnoff");
                setCameraOff();
            }
        }
    }
}


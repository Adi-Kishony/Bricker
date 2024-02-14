package bricker.gameobjects;

import bricker.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class CameraTracker extends GameObject {
    private final BrickerGameManager brickerGameManager;
    private final Camera myCamera;
    private static final int MAX_BALL_COLLISIONS = 10;
    private final Ball mainBall;
    private int numCollisions;

    public CameraTracker(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager brickerGameManager, Camera camera) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        myCamera = camera;
        this.mainBall = brickerGameManager.getMainBall();
        this.numCollisions = 0;
    }

    public void setCameraOn(GameObject ball){
        if(brickerGameManager.camera() == null) {
            myCamera.setToFollow(ball, Vector2.ZERO);
            brickerGameManager.setCamera(myCamera);
            numCollisions = mainBall.getCollisionCounter().value();
            System.out.println("on");
        }
    }


    private void setCameraOff(){
        brickerGameManager.setCamera(null);
    }

    @Override
    public void update(float deltaTime) {
        if (mainBall.getCollisionCounter().value() - numCollisions == MAX_BALL_COLLISIONS &&
                brickerGameManager.camera() != null){
            System.out.println(mainBall.getCollisionCounter().value() - numCollisions);
            System.out.println("turnoff");
            setCameraOff();
        }
    }
}

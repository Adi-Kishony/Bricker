package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.Constants;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.util.Random;

public class AddBallsCollisionStrategy extends  BasicCollisionStrategy implements CollisionStrategy{

    private final Vector2 windowDimensions;
    private final Ball mainBall;

    public AddBallsCollisionStrategy(BrickerGameManager brickerGameManager, Vector2 windowDimensions,
                                     Ball mainBall) {
        super(brickerGameManager);
        this.windowDimensions = windowDimensions;
        this.mainBall = mainBall;
    }

    private static final float PUCK_BALL_SIZE_RATIO = 0.75f;
    private void createPuckBall(ImageReader imageReader, Vector2 ballDimensions, Vector2 ballLoc){
        Random rand = new Random();
        Renderable puckBallImage = imageReader.readImage(Constants.PUCK_IMG_PATH, true);
        Sound collisionSound = brickerGameManager.getSoundReader().readSound(Constants.BLOP_SOUND_PATH);
        Puck puckBall = new Puck(ballLoc, ballDimensions, puckBallImage, collisionSound, this);
        puckBall.reCenterBall(Constants.BALL_SPEED, ballLoc);
        double angle = rand.nextDouble()* Math.PI;
        float velocityX = (float)Math.cos(angle) * Constants.BALL_SPEED;
        float velocityY = (float)Math.sin(angle) * Constants.BALL_SPEED;
        Vector2 newVel = new Vector2(velocityX, velocityY);
        puckBall.setVelocity(newVel);
        brickerGameManager.addGameObject(puckBall);
    }

    public void removeCheckPuck(Puck puckBall){
        float ballHeight = puckBall.getCenter().y();
            if (ballHeight > windowDimensions.y()){
                    brickerGameManager.removeGameObject(puckBall);
            }
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        Vector2 brickLoc = obj1.getCenter();
        Vector2 ballDimensions = mainBall.getDimensions();
        Vector2 puckBallDimensions = new Vector2(ballDimensions.x()*(PUCK_BALL_SIZE_RATIO),
                ballDimensions.y()*(PUCK_BALL_SIZE_RATIO));
        createPuckBall(brickerGameManager.getImageReader(), puckBallDimensions, brickLoc);
        createPuckBall(brickerGameManager.getImageReader(), puckBallDimensions, brickLoc);
    }
}

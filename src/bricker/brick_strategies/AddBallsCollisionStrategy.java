package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.Constants;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class AddBallsCollisionStrategy extends  BasicCollisionStrategy implements CollisionStrategy{

    public AddBallsCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }


    private void createPuckBall(ImageReader imageReader, Vector2 ballDimensions, Vector2 ballLoc){
        Random rand = new Random();
        Renderable puckBallImage = imageReader.readImage(Constants.PUCK_IMG_PATH, true);
        Sound collisionSound = brickerGameManager.getSoundReader().readSound(Constants.BLOP_SOUND_PATH);
        Puck puckBall = new Puck(ballLoc, ballDimensions, puckBallImage, collisionSound, this);
        puckBall.reCenterBall(brickerGameManager.getWindowDimensions(), Constants.BALL_SPEED, ballLoc);
        double angle = rand.nextDouble()* Math.PI;
        float velocityX = (float)Math.cos(angle) * Constants.BALL_SPEED;
        float velocityY = (float)Math.sin(angle) * Constants.BALL_SPEED;
        Vector2 newVel = new Vector2(velocityX, velocityY);
        puckBall.setVelocity(newVel);
        brickerGameManager.addGameObject(puckBall);
    }

    public void removePuckBall(Puck puckBall){
        float ballHeight = puckBall.getCenter().y();
            if (ballHeight > brickerGameManager.getWindowDimensions().y()){
                    brickerGameManager.removeGameObject(puckBall);
            }
    }


    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        Vector2 brickLoc = obj1.getCenter();
        Vector2 ballDimensions = brickerGameManager.getMainBallDims();
        Vector2 puckBallDimensions = new Vector2(ballDimensions.x()*((float)3/4),
                ballDimensions.y()*((float)3/4));
        createPuckBall(brickerGameManager.getImageReader(), puckBallDimensions, brickLoc);
        createPuckBall(brickerGameManager.getImageReader(), puckBallDimensions, brickLoc);
    }
}

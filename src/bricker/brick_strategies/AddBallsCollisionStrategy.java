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

public class AddBallsCollisionStrategy extends  BasicCollisionStrategy implements CollisionStrategy{

    public AddBallsCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }


    private void createPuckBall(ImageReader imageReader, Vector2 ballDimensions, Vector2 ballLoc){
        Renderable puckBallImage = imageReader.readImage("assets/mockBall.png", true);
        Sound collisionSound = brickerGameManager.getSoundReader().readSound("assets/blop_cut_silenced.wav");
        Puck puckBall = new Puck(ballLoc, ballDimensions, puckBallImage, collisionSound);
        puckBall.reCenterBall(brickerGameManager.getWindowDimensions(), Constants.BALL_SPEED, ballLoc);
        brickerGameManager.addGameObject(puckBall);
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        Vector2 brickLoc = obj1.getCenter();
        Vector2 ballDimensions = obj2.getDimensions();
        Vector2 puckBallDimensions = new Vector2(ballDimensions.x()*((float)3/4),
                ballDimensions.y()*((float)3/4));
        createPuckBall(brickerGameManager.getImageReader(), puckBallDimensions, brickLoc);
        createPuckBall(brickerGameManager.getImageReader(), puckBallDimensions, brickLoc);


    }
}

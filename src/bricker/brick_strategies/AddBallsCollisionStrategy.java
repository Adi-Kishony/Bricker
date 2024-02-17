package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.util.Random;
/**
 * A collision strategy that adds additional Puck balls to the game when a collision with a brick occurs.
 * Each collision triggers the creation of two Puck balls at the location of the collided brick.
 */
public class AddBallsCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy {

    /**
     * The ratio by which the dimensions of the Puck ball are scaled in comparison to the main ball.
     */
    private static final float PUCK_BALL_SIZE_RATIO = 0.75f;

    /**
     * The dimensions of the game window.
     */
    private final Vector2 windowDimensions;

    /**
     * The main ball associated with the game.
     */
    private final Ball mainBall;

    /**
     * Constructs an AddBallsCollisionStrategy with the specified parameters.
     *
     * @param brickerGameManager The BrickerGameManager associated with this collision strategy.
     * @param windowDimensions   The dimensions of the game window.
     * @param mainBall           The main ball associated with the game.
     */
    public AddBallsCollisionStrategy(BrickerGameManager brickerGameManager, Vector2 windowDimensions,
                                     Ball mainBall) {
        super(brickerGameManager);
        this.windowDimensions = windowDimensions;
        this.mainBall = mainBall;
    }

    /**
     * Creates a new Puck ball with the specified image, dimensions, and location.
     * The Puck ball is given a random initial velocity.
     *
     * @param imageReader    The ImageReader used to load the Puck ball image.
     * @param ballDimensions The dimensions of the Puck ball.
     * @param ballLoc        The initial location of the Puck ball.
     */
    private void createPuckBall(ImageReader imageReader, Vector2 ballDimensions, Vector2 ballLoc) {
        Random rand = new Random();
        Renderable puckBallImage = imageReader.readImage(Constants.PUCK_IMG_PATH, true);
        Sound collisionSound = brickerGameManager.getSoundReader().readSound(Constants.BLOP_SOUND_PATH);
        Puck puckBall = new Puck(ballLoc, ballDimensions, puckBallImage, collisionSound, this);
        puckBall.reCenterBall(Constants.BALL_SPEED, ballLoc);
        double angle = rand.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * Constants.BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * Constants.BALL_SPEED;
        Vector2 newVel = new Vector2(velocityX, velocityY);
        puckBall.setVelocity(newVel);
        brickerGameManager.addGameObject(puckBall);
    }

    /**
     * Checks and removes a Puck ball if it goes beyond the game window's height.
     *
     * @param puckBall The Puck ball to check and remove if necessary.
     */
    public void removeCheckPuck(Puck puckBall) {
        float ballHeight = puckBall.getCenter().y();
        if (ballHeight > windowDimensions.y()) {
            brickerGameManager.removeGameObject(puckBall);
        }
    }

    /**
     * Executes the AddBallsCollisionStrategy, creating two Puck balls at the location of the collided brick
     * when a collision occurs with the main ball.
     *
     * @param obj1 The first game object involved in the collision (to be removed).
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        Vector2 brickLoc = obj1.getCenter();
        Vector2 ballDimensions = mainBall.getDimensions();
        Vector2 puckBallDimensions = new Vector2(ballDimensions.x() * (PUCK_BALL_SIZE_RATIO),
                ballDimensions.y() * (PUCK_BALL_SIZE_RATIO));
        createPuckBall(brickerGameManager.getImageReader(), puckBallDimensions, brickLoc);
        createPuckBall(brickerGameManager.getImageReader(), puckBallDimensions, brickLoc);
    }
}
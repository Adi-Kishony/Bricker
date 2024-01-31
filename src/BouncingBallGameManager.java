//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.Paddle;

import java.util.Random;

public class BouncingBallGameManager extends GameManager{

    private static final float BALL_SPEED = 250;
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_WIDTH = 150;
    private static final int PADDLE_HEIGHT = 20;
    private static final int BALL_RADIUS = 35;
    private static final int DIST_FROM_EDGE_OF_DISPLAY = 30;


    public BouncingBallGameManager(String windowTitle, Vector2 windowDimensions){
        super(windowTitle, windowDimensions);
    }

    private void createWalls(Vector2 windowDimensions){
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH,windowDimensions.y()),null);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(),0), new Vector2(BORDER_WIDTH,windowDimensions.y()),null);
        GameObject topWall = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), BORDER_WIDTH),null);
        gameObjects().addGameObject(leftWall,Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(rightWall,Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(topWall,Layer.STATIC_OBJECTS);
    }

    private void createBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions){
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS,BALL_RADIUS), ballImage, collisionSound);
        ball.setCenter(windowDimensions.mult(0.5f));
        float ballVelY = BALL_SPEED;
        float ballVelX = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean()){
            ballVelX *= -1;
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX,ballVelY));
        gameObjects().addGameObject(ball);
    }

    private void createUserPaddle(Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions){
        // create user paddle
        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH,PADDLE_HEIGHT),
                paddleImage,
                inputListener);
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2,(int)windowDimensions.y()-DIST_FROM_EDGE_OF_DISPLAY));
        gameObjects().addGameObject(userPaddle);
    }

    private void createAIPaddle(Renderable paddleImage, Vector2 windowDimensions) {
        //crate ai paddles
        GameObject aiPaddle = new GameObject(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage);
        aiPaddle.setCenter(new Vector2(windowDimensions.x() / 2, DIST_FROM_EDGE_OF_DISPLAY));
        gameObjects().addGameObject(aiPaddle);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {

        // Initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        // create background
        Renderable bgImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background =
                new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), windowDimensions.y()), bgImage);
        //background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        //create ball
        createBall(imageReader, soundReader, windowDimensions);

        // create user paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        createUserPaddle(paddleImage, inputListener, windowDimensions);
        createAIPaddle(paddleImage, windowDimensions);

        // create walls
        createWalls(windowDimensions);

    }

    public static void main(String[] args) {
        BouncingBallGameManager gameManager = new BouncingBallGameManager("bouncing ball", new Vector2(500,500));
        gameManager.run();

    }
}
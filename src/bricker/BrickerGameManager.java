package bricker;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class BrickerGameManager extends GameManager{

    private static final float BALL_SPEED = 250;
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_WIDTH = 150;
    private static final int PADDLE_HEIGHT = 20;
    private static final int BALL_RADIUS = 35;
    private static final int DIST_FROM_EDGE_OF_DISPLAY = 30;
    private static  final int BRICK_HEIGHT = 15;
    private static final int NUM_ROWS_OF_BRICKS = 7;
    private static final int NUM_COLS_OF_BRICKS = 8;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions){
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

    private void addBricks(Vector2 windowDimensions ,Renderable brickImage, int numRows, int numCols) {
        CollisionStrategy collisionStrategy= new BasicCollisionStrategy(this);
        int brickWidth = windowDimensions.hashCode()/numCols - 1;
        Vector2 brickDims = new Vector2(brickWidth, BRICK_HEIGHT);

        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                Vector2 brickLoc = new Vector2(j*(brickWidth+3),i*(BRICK_HEIGHT+3));
                createBrick(brickImage, windowDimensions, collisionStrategy, brickDims, brickLoc);
            }
        }
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

    private void createBrick(Renderable brickImage, Vector2 windowDimensions,
                             CollisionStrategy collisionStrategy, Vector2 brickDims, Vector2 brickLoc) {
        GameObject brick = new Brick(brickLoc, brickDims, brickImage, collisionStrategy);
        gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
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

        // create walls
        createWalls(windowDimensions);

        //add bricks
        Renderable  brickImage = imageReader.readImage("assets/brick.png", false);
        addBricks(windowDimensions ,brickImage, NUM_ROWS_OF_BRICKS, NUM_COLS_OF_BRICKS);
    }


    public void removeGameObject(GameObject obj, int layer){
        gameObjects().removeGameObject(obj,layer);
    }

    public void removeGameObject(GameObject obj) {
        gameObjects().removeGameObject(obj);
    }

    public void addGameObject(GameObject obj, int layer){
        gameObjects().addGameObject(obj, layer);
    }

    public void addGameObject(GameObject obj){
        gameObjects().addGameObject(obj);
    }

    public static void main(String[] args) {
        BrickerGameManager gameManager = new BrickerGameManager("Bricker", new Vector2(500,500));
        gameManager.run();



    }
}
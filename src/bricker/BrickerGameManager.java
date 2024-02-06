package bricker;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.BrickStrategyType;
import bricker.brick_strategies.CollisionStrategiesFactory;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import danogl.gui.UserInputListener;


import java.awt.event.KeyEvent;
import java.util.Random;

import static bricker.brick_strategies.BrickStrategyType.BASIC;

public class BrickerGameManager extends GameManager{
    private final int numRows;
    private final int numCols;
    private Vector2 windowDimensions;
    private Counter bricksLeft;
    private ImageReader imageReader;
    private LivesManager livesManager;
    private SoundReader soundReader;
    private WindowController windowController;
    private Ball mainBall;
    private UserInputListener inputListener;


    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numRows, int numCols){
        super(windowTitle, windowDimensions);
        if (numCols == 0 || numRows == 0){
            this.numRows = Constants.NUM_ROWS_OF_BRICKS;
            this.numCols = Constants.NUM_COLS_OF_BRICKS;
        }
        else {
            this.numRows = numRows;
            this.numCols = numCols;
        }
    }

    private void createWalls(){
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(Constants.BORDER_WIDTH,
                windowDimensions.y()),null);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(),0), new Vector2
                (Constants.BORDER_WIDTH,windowDimensions.y()),null);
        GameObject topWall = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(),
                Constants.BORDER_WIDTH),null);
        gameObjects().addGameObject(leftWall,Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(rightWall,Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(topWall,Layer.STATIC_OBJECTS);
    }

    private void addBricks(Renderable brickImage) {
        CollisionStrategy collisionStrategy;
        float brickWidth = windowDimensions.x()/numCols - Constants.PADDING_PIXELS;
        Vector2 brickDims = new Vector2(brickWidth, Constants.BRICK_HEIGHT);
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                collisionStrategy = getRandomCollisionStrategy();
                Vector2 brickLoc = new Vector2(j*(brickWidth+Constants.PADDING_PIXELS) +
                        Constants.PADDING_PIXELS,i*(Constants.BRICK_HEIGHT+Constants.PADDING_PIXELS)+
                        Constants.PADDING_PIXELS);
                createBrick(brickImage, collisionStrategy, brickDims, brickLoc);
            }
        }
    }

    private CollisionStrategy getRandomCollisionStrategy(){
        Random random = new Random();
        int randomNumber = random.nextInt(10);
        BrickStrategyType strategy;
        if (randomNumber > 4){
            strategy = BASIC;
        }
        else{
            strategy = BrickStrategyType.values()[randomNumber];
        }
        CollisionStrategiesFactory collisionStrategiesFactory = new CollisionStrategiesFactory(this);
        CollisionStrategy collisionStrategy = collisionStrategiesFactory.generateCollisionStrategy(strategy);
        return collisionStrategy;
    }

    private Ball createBall(Renderable ballImage, Vector2 ballDimensions){
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Ball ball = new Ball(Vector2.ZERO, ballDimensions, ballImage, collisionSound);
        ball.reCenterBall(windowDimensions, Constants.BALL_SPEED, windowDimensions.mult(0.5f));
        addGameObject(ball);
        return ball;
    }

    public void createPaddle(Vector2 paddleCenter){
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                paddleImage,
                inputListener,
                getWindowDimensions());
        userPaddle.setCenter(paddleCenter);
        gameObjects().addGameObject(userPaddle);
    }

    private void createBrick(Renderable brickImage, CollisionStrategy collisionStrategy, Vector2 brickDims,
                             Vector2 brickLoc) {
        GameObject brick = new Brick(brickLoc, brickDims, brickImage, collisionStrategy, bricksLeft);
        gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        // Initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.bricksLeft = new Counter(numRows*numCols);
        this.windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.inputListener = inputListener;

        // create background
        Renderable bgImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background =
                new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), windowDimensions.y()), bgImage);
        //background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        addGameObject(background,Layer.BACKGROUND);

        //create ball
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        this.mainBall = createBall(ballImage, new Vector2(Constants.BALL_RADIUS, Constants.BALL_RADIUS));

        // create user paddle
        createPaddle(new Vector2(windowDimensions.x()/2,
                (int)windowDimensions.y()-Constants.DIST_FROM_EDGE_OF_DISPLAY));

        // create walls
        createWalls();

        //add bricks
        Renderable  brickImage = imageReader.readImage("assets/brick.png", false);
        addBricks(brickImage);

        //add remaining lives graphics
        Renderable heartImage = imageReader.readImage("assets/heart.png",true);
        this.livesManager = new LivesManager(new Vector2(Constants.LIVES_START_PIXEL, windowDimensions.y()
                - Constants.DISTANCE_FROM_BOTTOM),
                new Vector2(Constants.HEART_DIMENSIONS, Constants.HEART_DIMENSIONS),
                heartImage,Constants.INITIAL_NUM_LIVES,this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        boolean isGameWon = gameWon();
        boolean isGameLost = gameLost();
        if (inputListener.isKeyPressed(KeyEvent.VK_W)){
            endOfGameUI("You Win!");
        }
        if (isGameWon){
            endOfGameUI("You Win!");
        }
        else if(isGameLost){
            endOfGameUI("You Lose!");
        }
    }

    private boolean gameWon(){
        if (bricksLeft.value() <= 0){
            return true;
        }
        return false;
    }

    private boolean gameLost(){
        float ballHeight = mainBall.getCenter().y();
        if (ballHeight > windowController.getWindowDimensions().y()){
            if (livesManager.getCurrentLives() <= 1){
                livesManager.removeLife();
                return true;
            }
            else{
                livesManager.removeLife();
                mainBall.reCenterBall(windowController.getWindowDimensions(), Constants.BALL_SPEED,
                        windowDimensions.mult(0.5f));
            }
        }
        return false;
    }

    private void endOfGameUI(String prompt) {
        prompt += " Play again?";
        if (windowController.openYesNoDialog(prompt)) {
            windowController.resetGame();
        } else {
            windowController.closeWindow();
        }
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

    public Vector2 getWindowDimensions(){
        return windowDimensions;
    }

    public UserInputListener getInputListener(){
        return inputListener;
    }

    public ImageReader getImageReader(){
        return imageReader;
    }

    public SoundReader getSoundReader(){
        return soundReader;
    }
    public static void main(String[] args) {
        int numRows = 0;
        int numCols = 0;
        if (args.length == 2) {
            numRows = Integer.parseInt(args[0]);
            numCols = Integer.parseInt(args[1]);
        }
        BrickerGameManager gameManager = new BrickerGameManager("Bricker",
                new Vector2(500,500), numRows, numCols);
        gameManager.run();
    }
}
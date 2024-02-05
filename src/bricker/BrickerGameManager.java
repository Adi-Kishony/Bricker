package bricker;

import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager{
    private final int numRows;
    private final int numCols;
    private LivesManager livesManager;
    private WindowController windowController;
    private Ball ball;


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

    private void createWalls(Vector2 windowDimensions){
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(Constants.BORDER_WIDTH,windowDimensions.y()),null);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(),0), new Vector2(Constants.BORDER_WIDTH,windowDimensions.y()),null);
        GameObject topWall = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), Constants.BORDER_WIDTH),null);
        gameObjects().addGameObject(leftWall,Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(rightWall,Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(topWall,Layer.STATIC_OBJECTS);
    }

    private void addBricks(Vector2 windowDimensions ,Renderable brickImage, int numRows, int numCols) {
        CollisionStrategy collisionStrategy= new BasicCollisionStrategy(this);
        float brickWidth = windowDimensions.x()/numCols - Constants.PADDING_PIXELS;
        Vector2 brickDims = new Vector2(brickWidth, Constants.BRICK_HEIGHT);

        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                Vector2 brickLoc = new Vector2(j*(brickWidth+Constants.PADDING_PIXELS) + Constants.PADDING_PIXELS,
                        i*(Constants.BRICK_HEIGHT+Constants.PADDING_PIXELS)+Constants.PADDING_PIXELS);
                createBrick(brickImage, collisionStrategy, brickDims, brickLoc);
            }
        }
    }

    private Ball createBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions){
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Ball ball = new Ball(Vector2.ZERO, new Vector2(Constants.BALL_RADIUS, Constants.BALL_RADIUS), ballImage, collisionSound);
        ball.reCenterBall(windowDimensions, Constants.BALL_SPEED);
        addGameObject(ball);
        return ball;
    }

    private void createUserPaddle(Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions){
        // create user paddle
        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                paddleImage,
                inputListener,
                windowDimensions);
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2,
                (int)windowDimensions.y()-Constants.DIST_FROM_EDGE_OF_DISPLAY));
        gameObjects().addGameObject(userPaddle);
    }

    private void createBrick(Renderable brickImage, CollisionStrategy collisionStrategy, Vector2 brickDims, Vector2 brickLoc) {
        GameObject brick = new Brick(brickLoc, brickDims, brickImage, collisionStrategy);
        gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {

        // Initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;

        // create background
        Renderable bgImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background =
                new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), windowDimensions.y()), bgImage);
        //background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        addGameObject(background,Layer.BACKGROUND);

        //create ball
        this.ball = createBall(imageReader, soundReader, windowDimensions);

        // create user paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        createUserPaddle(paddleImage, inputListener, windowDimensions);

        // create walls
        createWalls(windowDimensions);

        //add bricks
        Renderable  brickImage = imageReader.readImage("assets/brick.png", false);
        addBricks(windowDimensions ,brickImage, numRows, numCols);

        //add remaining lives graphics
        Renderable heartImage = imageReader.readImage("assets/heart.png",true);
        this.livesManager = new LivesManager(new Vector2(Constants.LIVES_START_PIXEL, windowDimensions.y() - Constants.DISTANCE_FROM_BOTTOM),
                new Vector2(Constants.HEART_DIMENSIONS, Constants.HEART_DIMENSIONS),
                heartImage,Constants.INITIAL_NUM_LIVES,this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight > windowController.getWindowDimensions().y()){
            if (livesManager.getCurrentLives() <= 1){
                livesManager.removeLife();
                prompt = "You Lose!";
            }
            else{
                livesManager.removeLife();
                ball.reCenterBall(windowController.getWindowDimensions(), Constants.BALL_SPEED);
            }
        }
        if(!prompt.isEmpty()){
            prompt += " Play again?";
            if (windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
            }
            else {
                windowController.closeWindow();
            }
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
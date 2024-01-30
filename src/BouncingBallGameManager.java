//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;

public class BouncingBallGameManager extends GameManager{

    public BouncingBallGameManager(String windowTitle, Vector2 windowDimensions){
        super(windowTitle, windowDimensions);
    }

    private void createWalls(Vector2 windowDimensions){
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(10,windowDimensions.y()),null);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(),0), new Vector2(10,windowDimensions.y()),null);
        GameObject topWall = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(),10),null);
        gameObjects().addGameObject(leftWall);
        gameObjects().addGameObject(rightWall);
        gameObjects().addGameObject(topWall);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        // create background
        Renderable bgImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background =
                new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), windowDimensions.y()), bgImage);
        gameObjects().addGameObject(background);

        //create ball
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(50,50), ballImage);
        ball.setCenter(windowDimensions.mult(0.5f));
        ball.setVelocity(Vector2.DOWN.mult(200));
        gameObjects().addGameObject(ball);
        //crate paddles
        int[] paddleHeights = {(int)windowDimensions.y()-30, 30};
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);

        for (int i = 0; i < paddleHeights.length; i++) {
            GameObject paddle = new GameObject(Vector2.ZERO, new Vector2(200,20), paddleImage);
            paddle.setCenter(new Vector2(windowDimensions.x()/2,paddleHeights[i]));
            gameObjects().addGameObject(paddle);
        }

        // create walls
        createWalls(windowDimensions);

    }

    public static void main(String[] args) {
        BouncingBallGameManager gameManager = new BouncingBallGameManager("bouncing ball", new Vector2(500,500));
        gameManager.run();

    }
}
package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import bricker.main.LivesManager;
import bricker.gameobjects.Ball;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Factory class responsible for generating various collision strategies based on random selection.
 * It utilizes the CollisionStrategy interface and different concrete implementations.
 */
public class CollisionStrategiesFactory {

    /**
     * The BrickerGameManager associated with this factory.
     */
    private final BrickerGameManager brickerGameManager;

    /**
     * The dimensions of the game window.
     */
    private final Vector2 windowDimensions;

    /**
     * The main ball object in the game.
     */
    private final Ball mainBall;

    /**
     * The LivesManager object managing the player's lives.
     */
    private final LivesManager livesManager;

    /**
     * Constructs a CollisionStrategiesFactory with the specified parameters.
     *
     * @param brickerGameManager The BrickerGameManager associated with this factory.
     * @param windowDimensions   The dimensions of the game window.
     * @param mainBall           The main ball object in the game.
     * @param livesManager       The LivesManager object managing the player's lives.
     */
    public CollisionStrategiesFactory(BrickerGameManager brickerGameManager, Vector2 windowDimensions,
                                      Ball mainBall, LivesManager livesManager) {
        this.brickerGameManager = brickerGameManager;
        this.windowDimensions = windowDimensions;
        this.mainBall = mainBall;
        this.livesManager = livesManager;
    }

    /**
     * Generates a random BrickStrategyType and creates a corresponding CollisionStrategy.
     *
     * @return A CollisionStrategy based on the randomly selected BrickStrategyType.
     */
    public CollisionStrategy generateCollisionStrategy() {
        CollisionStrategy collisionStrategy;
        BrickStrategyType strategyType = getRandomCollisionStrategy();

        // Create a CollisionStrategy based on the randomly selected BrickStrategyType
        switch (strategyType) {
            case BASIC:
                collisionStrategy = new BasicCollisionStrategy(brickerGameManager);
                break;
            case ADD_PADDLE:
                collisionStrategy = new AddPaddleCollisionStrategy(brickerGameManager,
                        windowDimensions);
                break;
            case ADD_BALLS:
                collisionStrategy = new AddBallsCollisionStrategy(brickerGameManager, windowDimensions,
                        mainBall);
                break;
            case ADD_LIFE:
                collisionStrategy = new AddLifeCollisionStrategy(brickerGameManager, windowDimensions,
                        livesManager);
                break;
            case CAMERA_CHANGE:
                collisionStrategy = new CameraZoomCollisionStrategy(brickerGameManager, windowDimensions,
                        mainBall);
                break;
            case DOUBLE_STRATEGY:
                collisionStrategy = new DoubleCollisionStrategy(brickerGameManager, windowDimensions, mainBall
                        , livesManager);
                break;
            default:
                collisionStrategy = new BasicCollisionStrategy(brickerGameManager);
                break;
        }
        return collisionStrategy;
    }

    /**
     * Generates a random BrickStrategyType within a specified range.
     *
     * @return A random BrickStrategyType.
     */
    private BrickStrategyType getRandomCollisionStrategy() {
        Random random = new Random();
        int randomNumber = random.nextInt(Constants.RANDOM_STRATEGY_RANGE);
        int strategy;
        if (randomNumber >= Constants.BASIC_STRATEGY_START_INDEX) {
            strategy = Constants.BASIC_STRATEGY_START_INDEX;
        } else {
            strategy = randomNumber;
        }
        return BrickStrategyType.values()[strategy];
    }
}
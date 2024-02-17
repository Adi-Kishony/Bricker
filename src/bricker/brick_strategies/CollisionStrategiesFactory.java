package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.Constants;
import bricker.LivesManager;
import bricker.gameobjects.Ball;
import danogl.util.Vector2;
import java.util.Random;

public class CollisionStrategiesFactory {
    private final BrickerGameManager brickerGameManager;
    private final Vector2 windowDimensions;
    private final Ball mainBall;
    private final LivesManager livesManager;

    public CollisionStrategiesFactory(BrickerGameManager brickerGameManager, Vector2 windowDimensions, Ball mainBall, LivesManager livesManager) {
        this.brickerGameManager = brickerGameManager;
        this.windowDimensions = windowDimensions;
        this.mainBall = mainBall;
        this.livesManager = livesManager;
    }

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

    public CollisionStrategy generateCollisionStrategy() {
        CollisionStrategy collisionStrategy;
        BrickStrategyType strategyType = getRandomCollisionStrategy();
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
}

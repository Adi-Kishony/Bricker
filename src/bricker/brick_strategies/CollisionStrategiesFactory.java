package bricker.brick_strategies;

import bricker.BrickerGameManager;
import danogl.util.Vector2;

public class CollisionStrategiesFactory {
    private final BrickerGameManager brickerGameManager;

    public CollisionStrategiesFactory(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    public CollisionStrategy generateCollisionStrategy(BrickStrategyType strategyType){
        CollisionStrategy collisionStrategy;
        switch (strategyType){
            case BASIC:
                collisionStrategy = new BasicCollisionStrategy(brickerGameManager);
                break;
            case ADD_PADDLE:
                collisionStrategy = new AddPaddleCollisionStrategy(brickerGameManager);
                break;
            case ADD_BALLS:
                collisionStrategy = new AddBallsCollisionStrategy(brickerGameManager);
                break;
            default:
                collisionStrategy = new AddBallsCollisionStrategy(brickerGameManager);;
                break;
        }
        return collisionStrategy;
    }
}

package bricker.brick_strategies;

import bricker.BrickerGameManager;
import danogl.util.Vector2;

public class CollisionStrategiesFactory {
    private final BrickerGameManager brickerGameManager;

    public CollisionStrategiesFactory(BrickerGameManager brickerGameManager,
                                      Vector2 paddleDimensions) {
        this.brickerGameManager = brickerGameManager;
    }

    public CollisionStrategy generateCollisionStrategy(BrickStrategyType strategyType){
        CollisionStrategy collisionStrategy;
        switch (strategyType){
            case BASIC:
                collisionStrategy = new BasicCollisionStrategy(brickerGameManager);
                break;
            default:
                collisionStrategy = null;
                break;
        }
        return collisionStrategy;
    }
}

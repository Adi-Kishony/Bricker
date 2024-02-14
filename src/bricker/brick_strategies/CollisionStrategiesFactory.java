package bricker.brick_strategies;
import bricker.BrickerGameManager;

public class CollisionStrategiesFactory {
    private final BrickerGameManager brickerGameManager;

    public CollisionStrategiesFactory(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    public CollisionStrategy generateCollisionStrategy(BrickStrategyType strategyType){
        CollisionStrategy collisionStrategy;
        System.out.println("factory");
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
            case ADD_LIFE:
                collisionStrategy = new AddLifeCollisionStrategy(brickerGameManager);
                break;
            case CAMERA_CHANGE:
                collisionStrategy = new CameraZoomCollisionStrategy(brickerGameManager);
                break;
//            case DOUBLE_STRATEGY:
//                collisionStrategy = new DoubleCollisionStrategy(brickerGameManager);
            default:
                collisionStrategy = new CameraZoomCollisionStrategy(brickerGameManager);
                break;
        }
        return collisionStrategy;
    }
}

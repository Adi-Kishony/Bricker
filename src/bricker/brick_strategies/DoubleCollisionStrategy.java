package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.LivesManager;
import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.util.Vector2;

public class DoubleCollisionStrategy implements CollisionStrategy {
    private static final int MAX_STRATEGIES = 3;
    private final BrickerGameManager brickerGameManager;
    private final Ball mainBall;
    private final LivesManager livesManager;
    private final Vector2 windowsDimension;
    private CollisionStrategy strategy1;
    private CollisionStrategy strategy2;

    public DoubleCollisionStrategy(BrickerGameManager brickerGameManager, Vector2 windowDimensions,
                                   Ball mainBall, LivesManager livesManager) {
        this.brickerGameManager = brickerGameManager;
        this.windowsDimension = windowDimensions;
        this.mainBall = mainBall;
        this.livesManager = livesManager;
        initStrategies();
    }

    private void initStrategies() {
        CollisionStrategiesFactory collisionStrategiesFactory = new CollisionStrategiesFactory(
                brickerGameManager, windowsDimension, mainBall, livesManager);
        CollisionStrategy randomStrategy1 = collisionStrategiesFactory.generateCollisionStrategy();
        CollisionStrategy randomStrategy2 = collisionStrategiesFactory.generateCollisionStrategy();
        while (regularStrategyAmount(strategy1) + regularStrategyAmount(strategy2) > MAX_STRATEGIES) {
            randomStrategy1 = collisionStrategiesFactory.generateCollisionStrategy();
            randomStrategy2 = collisionStrategiesFactory.generateCollisionStrategy();
        }
        strategy1 = randomStrategy1;
        strategy2 = randomStrategy2;
    }

    private int regularStrategyAmount(CollisionStrategy strategy) {
        if (!(strategy instanceof DoubleCollisionStrategy)) {
            return 1;
        }
        return regularStrategyAmount(((DoubleCollisionStrategy) strategy).strategy1) +
                regularStrategyAmount(((DoubleCollisionStrategy) strategy).strategy2);
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        strategy1.onCollision(obj1, obj2);
        strategy2.onCollision(obj1, obj2);
    }
}

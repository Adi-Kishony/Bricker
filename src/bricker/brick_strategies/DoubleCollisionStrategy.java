package bricker.brick_strategies;
import bricker.BrickerGameManager;
import danogl.GameObject;

public class DoubleCollisionStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;
    private CollisionStrategy strategy1;
    private CollisionStrategy strategy2;
    private static final int MAX_STRATEGIES = 3;
    public DoubleCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
        initStrategies();
    }

    private void initStrategies() {
        CollisionStrategy randomStrategy1 = brickerGameManager.getRandomCollisionStrategy();
        CollisionStrategy randomStrategy2 = brickerGameManager.getRandomCollisionStrategy();
        while (regularStrategyAmount(strategy1) + regularStrategyAmount(strategy2) > MAX_STRATEGIES) {
            randomStrategy1 = brickerGameManager.getRandomCollisionStrategy();
            randomStrategy2 = brickerGameManager.getRandomCollisionStrategy();
        }
        strategy1 = randomStrategy1;
        strategy2 = randomStrategy2;
    }

    private int regularStrategyAmount(CollisionStrategy strategy){
        if (!(strategy instanceof DoubleCollisionStrategy)) {
            return 1;
        }
        return regularStrategyAmount(((DoubleCollisionStrategy)strategy).strategy1) +
                regularStrategyAmount(((DoubleCollisionStrategy)strategy).strategy2);
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        strategy1.onCollision(obj1, obj2);
        strategy2.onCollision(obj1, obj2);
    }
}

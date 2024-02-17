package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.LivesManager;
import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.util.Vector2;

/**
 * Collision strategy that combines two randomly selected collision strategies into a double strategy.
 * The number of regular strategies within the double strategy is limited to a maximum of three.
 */
public class DoubleCollisionStrategy implements CollisionStrategy {

    /**
     * The maximum number of regular strategies allowed within the double strategy.
     */
    private static final int MAX_STRATEGIES = 3;

    /**
     * The BrickerGameManager associated with this double strategy.
     */
    private final BrickerGameManager brickerGameManager;

    /**
     * The main ball object in the game.
     */
    private final Ball mainBall;

    /**
     * The LivesManager object managing the player's lives.
     */
    private final LivesManager livesManager;

    /**
     * The dimensions of the game window.
     */
    private final Vector2 windowsDimension;

    /**
     * The first collision strategy within the double strategy.
     */
    private CollisionStrategy strategy1;

    /**
     * The second collision strategy within the double strategy.
     */
    private CollisionStrategy strategy2;

    /**
     * Constructs a DoubleCollisionStrategy with the specified parameters.
     *
     * @param brickerGameManager The BrickerGameManager associated with this double strategy.
     * @param windowDimensions   The dimensions of the game window.
     * @param mainBall           The main ball object in the game.
     * @param livesManager       The LivesManager object managing the player's lives.
     */
    public DoubleCollisionStrategy(BrickerGameManager brickerGameManager, Vector2 windowDimensions,
                                   Ball mainBall, LivesManager livesManager) {
        this.brickerGameManager = brickerGameManager;
        this.windowsDimension = windowDimensions;
        this.mainBall = mainBall;
        this.livesManager = livesManager;
        initStrategies();
    }

    /**
     * Initializes the two random collision strategies within the double strategy,
     * ensuring that the total number of regular strategies does not exceed the maximum allowed.
     */
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

    /**
     * Recursively calculates the number of regular strategies within a double strategy.
     *
     * @param strategy The collision strategy to analyze.
     * @return The number of regular strategies within the given strategy.
     */
    private int regularStrategyAmount(CollisionStrategy strategy) {
        if (!(strategy instanceof DoubleCollisionStrategy)) {
            return 1;
        }
        return regularStrategyAmount(((DoubleCollisionStrategy) strategy).strategy1) +
                regularStrategyAmount(((DoubleCollisionStrategy) strategy).strategy2);
    }

    /**
     * Executes the onCollision method for both collision strategies within the double strategy.
     *
     * @param obj1 The first GameObject involved in the collision.
     * @param obj2 The second GameObject involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        strategy1.onCollision(obj1, obj2);
        strategy2.onCollision(obj1, obj2);
    }
}
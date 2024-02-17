package bricker.brick_strategies;

/**
 * An enumeration representing various types of brick collision strategies in the Bricker game.
 * Each enum constant corresponds to a specific strategy to be applied when a collision occurs with a brick.
 */
public enum BrickStrategyType {

    /**
     * Adds additional 2 puck balls to the game when a collision with the brick occurs.
     */
    ADD_BALLS,

    /**
     * Adds an extra paddle to the game when a collision with the brick occurs.
     */
    ADD_PADDLE,

    /**
     * Initiates a camera change when a collision with the brick occurs.
     */
    CAMERA_CHANGE,

    /**
     * Adds an extra life that the player can catch when a collision with the brick occurs.
     */
    ADD_LIFE,

    /**
     * Represents a combination of multiple brick collision strategies applied simultaneously.
     */
    DOUBLE_STRATEGY,

    /**
     * Represents the basic brick collision strategy without any additional effects.
     */
    BASIC
}
package bricker;

import bricker.gameobjects.Heart;
import bricker.gameobjects.NumericLivesCounter;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The LivesManager class is responsible for managing the player's lives in the Bricker game.
 * It keeps track of the current number of lives, updates the graphical representation of lives,
 * and provides methods to add or remove lives during gameplay.
 * The class uses Heart objects to represent individual lives and a NumericLivesCounter to display
 * the numeric count of lives. It interacts with the BrickerGameManager to add and remove graphical
 * elements for lives and updates the game state accordingly.
 */
public class LivesManager {
    // Instance variables
    private final int initialNumLives;
    private final BrickerGameManager brickerGameManager;
    private final Heart[] hearts;
    private final Vector2 dimensionsOfOneHeart;
    private final Vector2 topLeftCornerOfLivesPanel;
    private final Renderable renderable;
    private int currentLives;
    private final NumericLivesCounter numericLivesCounter;

    /**
     * Constructs a LivesManager with the specified parameters.
     * @param topLeftCornerOfFirstHeart The position of the top-left corner of the first heart.
     * @param dimensionsOfOneHeart      The dimensions of a single heart.
     * @param renderable                The renderer image for hearts.
     * @param initialNumLives           The initial number of lives for the player.
     * @param brickerGameManager        The bricker game manager.
     */
    public LivesManager(Vector2 topLeftCornerOfFirstHeart, Vector2 dimensionsOfOneHeart,
                        Renderable renderable, int initialNumLives, BrickerGameManager brickerGameManager) {
        this.initialNumLives = initialNumLives;
        this.brickerGameManager = brickerGameManager;
        this.hearts = new Heart[Constants.MAX_NUM_LIVES];
        this.topLeftCornerOfLivesPanel = topLeftCornerOfFirstHeart;
        this.currentLives = initialNumLives;
        this.dimensionsOfOneHeart = dimensionsOfOneHeart;
        this.renderable = renderable;
        this.numericLivesCounter = new NumericLivesCounter(new Vector2(topLeftCornerOfLivesPanel.x(),
                topLeftCornerOfLivesPanel.y() - Constants.HEART_PADDING), dimensionsOfOneHeart, initialNumLives);

        // add the numericLivesCounter to the game objects
        this.brickerGameManager.addGameObject(this.numericLivesCounter, Layer.BACKGROUND);
        createHearts();
    }

    /**
     * Gets the current number of lives.
     * @return The current number of lives.
     */
    public int getCurrentLives() {
        return currentLives;
    }

    /**
     * Adds a life to the player if the maximum number of lives has not been reached.
     * Updates the graphical representation of lives.
     */
    public void addLife() {
        // if current number of lives smaller than the maximum that allowed: add new life
        if (currentLives < Constants.MAX_NUM_LIVES) {
            // define new hear location on x-axis
            float xLoc = currentLives * (dimensionsOfOneHeart.x() + Constants.HEART_PADDING) +
                    topLeftCornerOfLivesPanel.x() + numericLivesCounter.getDimensions().x();

            Heart newHeart = new Heart(new Vector2(xLoc,
                    topLeftCornerOfLivesPanel.y()), dimensionsOfOneHeart, renderable);

            // add the new heart
            hearts[currentLives] = newHeart;
            brickerGameManager.addGameObject(newHeart, Layer.BACKGROUND);
            currentLives += 1;
            numericLivesCounter.incrementLives();
        }
    }

    /**
     * Removes a life from the player if there are remaining lives.
     * Updates the graphical representation of lives.
     */
    public void removeLife() {
        if (currentLives > 0) {
            currentLives--;
            brickerGameManager.removeGameObject(hearts[currentLives], Layer.BACKGROUND);
            numericLivesCounter.decrementLives();
        }
    }

    // Private helper methods

    /**
     * Creates the initial graphical representation of hearts based on the initial number of lives.
     */
    private void createHearts() {
        Heart heart;
        Vector2 heartLoc;
        float xLoc;
        for (int i = 0; i < initialNumLives; i++) {
            // define heart location on x-axis
            xLoc = topLeftCornerOfLivesPanel.x() + numericLivesCounter.getDimensions().x() +
                    i * (dimensionsOfOneHeart.x() + Constants.HEART_PADDING);

            heartLoc = new Vector2(xLoc, topLeftCornerOfLivesPanel.y());
            // create and add single heart
            heart = new Heart(heartLoc, dimensionsOfOneHeart, renderable);
            brickerGameManager.addGameObject(heart, Layer.BACKGROUND);
            hearts[i] = heart;
        }
    }
}

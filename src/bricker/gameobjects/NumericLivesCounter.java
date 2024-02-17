package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * The NumericLivesCounter class represents a game object that displays the numeric count
 * of remaining lives in the Bricker game. It extends the danogl.GameObject class and uses
 * a TextRenderable to render and display the numeric value. The counter dynamically changes
 * its color based on predefined thresholds for a visual indication of the remaining lives.
 * The class provides methods for incrementing and decrementing the life count, updating
 * the display text, and adjusting the color based on the remaining lives.
 */
public class NumericLivesCounter extends GameObject {
    /**
     * GREEN_THRESHOLD constant represent the green color threshold.
     */
    private static final int GREEN_THRESHOLD = 3;

    /**
     * YELLOW_THRESHOLD constant represent the yellow color threshold.
     */
    private static final int YELLOW_THRESHOLD = 2;

    /**
     * RED_THRESHOLD constant represent the red color threshold.
     */
    private static final int RED_THRESHOLD = 1;

    /**
     * numLives field counts the current lives in the game.
     */
    private int numLives;

    /**
     * textRenderable field enable to display the numeric counter on the screen.
     */
    private final TextRenderable textRenderable;

    /**
     * Constructs a NumericLivesCounter object with the specified parameters.
     * @param topLeftCorner The top-left corner position of the counter.
     * @param dimensions    The dimensions of the counter.
     * @param numLives      The initial number of lives to display.
     */
    public NumericLivesCounter(Vector2 topLeftCorner, Vector2 dimensions, int numLives) {
        super(topLeftCorner, dimensions, null);
        this.numLives = numLives;
        textRenderable = new TextRenderable(Integer.toString(numLives));
        textRenderable.setColor(Color.GREEN);
        this.renderer().setRenderable(textRenderable);
    }


    /**
     * Sets the color of the text based on the current number of lives.
     */
    private void setColor() {
        textRenderable.setString(Integer.toString(numLives));
        if (numLives >= GREEN_THRESHOLD) {
            textRenderable.setColor(Color.GREEN);
        } else if (numLives == YELLOW_THRESHOLD) {
            textRenderable.setColor(Color.YELLOW);
        } else if (numLives == RED_THRESHOLD) {
            textRenderable.setColor(Color.RED);
        }
    }

    /**
     * Increments the number of lives and updates the display text and color.
     */
    public void incrementLives() {
        numLives++;
        // update the graphical numeric counter
        textRenderable.setString(Integer.toString(numLives));
        setColor();
    }

    /**
     * Decrements the number of lives and updates the display text and color.
     */
    public void decrementLives() {
        numLives--;
        // update the graphical numeric counter
        textRenderable.setString(Integer.toString(numLives));
        setColor();
    }
}

package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLivesCounter extends GameObject {
    private static final int GREEN_THRESHOLD = 3;
    private static final int YELLOW_THRESHOLD = 2;
    private static final int RED_THRESHOLD = 1;
    private int numLives;
    private TextRenderable textRenderable;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public NumericLivesCounter(Vector2 topLeftCorner, Vector2 dimensions,  int numLives) {
        super(topLeftCorner, dimensions, null);
        this.numLives = numLives;
        textRenderable = new TextRenderable(Integer.toString(numLives));
        textRenderable.setColor(Color.GREEN);
        this.renderer().setRenderable(textRenderable);
    }

    private void setColor(){
        textRenderable.setString(Integer.toString(numLives));
        if (numLives >= GREEN_THRESHOLD) {
            textRenderable.setColor(Color.GREEN);
        }
        else if (numLives == YELLOW_THRESHOLD){
            textRenderable.setColor(Color.YELLOW);
        }
        else if (numLives == RED_THRESHOLD){
            textRenderable.setColor(Color.RED);
        }
    }

    public void incrementLives(){
        numLives ++;
        textRenderable.setString(Integer.toString(numLives));
        setColor();
    }

    public void decrementLives(){
        numLives --;
        textRenderable.setString(Integer.toString(numLives));
        setColor();
    }
}

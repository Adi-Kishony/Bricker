package bricker;

import bricker.gameobjects.Heart;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LivesManager {

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    private final int initialNumLives;
    private final BrickerGameManager brickerGameManager;
    private final Heart[] hearts;
    private final Vector2 dimensionsOfOneHeart;
    private final Vector2 topLeftCornerOfFirstHeart;
    private final Renderable renderable;
    private int currentLives;

    public LivesManager(Vector2 topLeftCornerOfFirstHeart, Vector2 dimensionsOfOneHeart, Renderable renderable,
                        int initialNumLives, BrickerGameManager brickerGameManager) {
        this.initialNumLives = initialNumLives;
        this.brickerGameManager = brickerGameManager;
        this.hearts = new Heart[initialNumLives];
        this.topLeftCornerOfFirstHeart = topLeftCornerOfFirstHeart;
        this.currentLives = initialNumLives;
        this.dimensionsOfOneHeart = dimensionsOfOneHeart;
        this.renderable = renderable;
        createHearts();
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void addLife(){
        if (currentLives < initialNumLives){
            float xLoc = currentLives*(dimensionsOfOneHeart.x() + Constants.HEART_PADDING) +
                    topLeftCornerOfFirstHeart.x();
            Heart newHeart = new Heart(new Vector2(xLoc,topLeftCornerOfFirstHeart.y() -
                    Constants.DISTANCE_FROM_BOTTOM), dimensionsOfOneHeart, renderable);
            hearts[currentLives] = newHeart;
            brickerGameManager.addGameObject(newHeart,Layer.BACKGROUND);
            currentLives += 1;
        }
    }

    public void removeLife(){
        if (currentLives > 0){
            currentLives--;
            brickerGameManager.removeGameObject(hearts[currentLives],Layer.BACKGROUND);

        }
    }

    private void createHearts(){
        Heart heart;
        Vector2 heartLoc;
        float xLoc;
        for(int i = 0; i< initialNumLives; i++){
            xLoc = topLeftCornerOfFirstHeart.x() + i*(dimensionsOfOneHeart.x() + Constants.HEART_PADDING);
            heartLoc = new Vector2(xLoc, topLeftCornerOfFirstHeart.y() - Constants.DISTANCE_FROM_BOTTOM);
            heart = new Heart(heartLoc, dimensionsOfOneHeart, renderable);
            brickerGameManager.addGameObject(heart, Layer.BACKGROUND);
            hearts[i] = heart;
        }
    }
}

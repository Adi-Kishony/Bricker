package bricker;

import bricker.gameobjects.Heart;
import bricker.gameobjects.NumericLivesCounter;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

public class LivesManager {
    public final static float HEART_PADDING = 6;
    public final static int DISTANCE_FROM_BOTTOM = 30;
    private final int initialNumLives;
    private final BrickerGameManager brickerGameManager;
    private final Heart[] hearts;
    private final Vector2 dimensionsOfOneHeart;
    private final Vector2 topLeftCornerOfFirstHeart;
    private final Renderable renderable;
    private int currentLives;
    private NumericLivesCounter numericLivesCounter;


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

    private NumericLivesCounter createNumericLivesCounter(){
        Vector2 counterLoc = new Vector2(topLeftCornerOfFirstHeart.x(), topLeftCornerOfFirstHeart.y()-DISTANCE_FROM_BOTTOM);

    }
    public int getCurrentLives() {
        return currentLives;
    }

    public void addLife(){
        if (currentLives < initialNumLives){
            float xLoc = currentLives*(dimensionsOfOneHeart.x() + HEART_PADDING) +
                    topLeftCornerOfFirstHeart.x();
            Heart newHeart = new Heart(new Vector2(xLoc,
                    topLeftCornerOfFirstHeart.y() - DISTANCE_FROM_BOTTOM), dimensionsOfOneHeart, renderable);
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
            xLoc = topLeftCornerOfFirstHeart.x() + i*(dimensionsOfOneHeart.x() + HEART_PADDING);
            heartLoc = new Vector2(xLoc, topLeftCornerOfFirstHeart.y() - DISTANCE_FROM_BOTTOM);
            heart = new Heart(heartLoc, dimensionsOfOneHeart, renderable);
            brickerGameManager.addGameObject(heart, Layer.BACKGROUND);
            hearts[i] = heart;
        }
    }
}

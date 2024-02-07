package bricker;

import bricker.gameobjects.Heart;
import bricker.gameobjects.NumericLivesCounter;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class LivesManager {
    public final static float HEART_PADDING = 6;
    private final int initialNumLives;
    private final BrickerGameManager brickerGameManager;
    private final Heart[] hearts;
    private final Vector2 dimensionsOfOneHeart;
    private final Vector2 topLeftCornerOfLivesPanel;
    private final Renderable renderable;
    private int currentLives;
    private final NumericLivesCounter numericLivesCounter;

    public LivesManager(Vector2 topLeftCornerOfFirstHeart, Vector2 dimensionsOfOneHeart, Renderable renderable,
                        int initialNumLives, BrickerGameManager brickerGameManager) {
        this.initialNumLives = initialNumLives;
        this.brickerGameManager = brickerGameManager;
        this.hearts = new Heart[Constants.MAX_NUM_LIVES];
        this.topLeftCornerOfLivesPanel = topLeftCornerOfFirstHeart;
        this.currentLives = initialNumLives;
        this.dimensionsOfOneHeart = dimensionsOfOneHeart;
        this.renderable = renderable;
        this.numericLivesCounter = new NumericLivesCounter(new Vector2(topLeftCornerOfLivesPanel.x(),
                topLeftCornerOfLivesPanel.y()-HEART_PADDING), dimensionsOfOneHeart, initialNumLives);
        this.brickerGameManager.addGameObject(this.numericLivesCounter);
        createHearts();
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void addLife(){
        if (currentLives < Constants.MAX_NUM_LIVES){
            float xLoc = currentLives*(dimensionsOfOneHeart.x() + HEART_PADDING) +
                    topLeftCornerOfLivesPanel.x() + numericLivesCounter.getDimensions().x();
            Heart newHeart = new Heart(new Vector2(xLoc,
                    topLeftCornerOfLivesPanel.y()), dimensionsOfOneHeart, renderable);
            hearts[currentLives] = newHeart;
            brickerGameManager.addGameObject(newHeart,Layer.BACKGROUND);
            currentLives += 1;
            numericLivesCounter.incrementLives();
        }
    }

    public void removeLife(){
        if (currentLives > 0){
            currentLives--;
            brickerGameManager.removeGameObject(hearts[currentLives],Layer.BACKGROUND);
            numericLivesCounter.decrementLives();
        }
    }

    private void createHearts(){
        Heart heart;
        Vector2 heartLoc;
        float xLoc;
        for(int i = 0; i< initialNumLives; i++){
            xLoc = topLeftCornerOfLivesPanel.x() + numericLivesCounter.getDimensions().x() +
                    i*(dimensionsOfOneHeart.x() + HEART_PADDING);
            heartLoc = new Vector2(xLoc, topLeftCornerOfLivesPanel.y());
            heart = new Heart(heartLoc, dimensionsOfOneHeart, renderable);
            brickerGameManager.addGameObject(heart, Layer.BACKGROUND);
            hearts[i] = heart;
        }
    }
}

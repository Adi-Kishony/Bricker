package bricker.brick_strategies;

import bricker.BrickerGameManager;
import bricker.Constants;
import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class AddLifeCollisionStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    public AddLifeCollisionStrategy(BrickerGameManager brickerGameManager) {
        super(brickerGameManager);
    }

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        Renderable heartRenderable = brickerGameManager.getImageReader().readImage(Constants.HEART_IMG_PATH, true);
        Heart heart = new Heart(obj1.getCenter(),new Vector2(Constants.HEART_DIMENSIONS,
                Constants.HEART_DIMENSIONS), heartRenderable, this);
        heart.setVelocity(new Vector2(0, Constants.HEART_VELOCITY));
        brickerGameManager.addGameObject(heart);
    }

    public void caughtHeart(Heart heart) {
        brickerGameManager.removeGameObject(heart);
        brickerGameManager.getLivesManager().addLife();
    }

    public void checkOutOfBounds(Heart heart) {
        float heartHeight = heart.getDimensions().y();
        if (heartHeight > brickerGameManager.getWindowDimensions().y()){
            brickerGameManager.removeGameObject(heart);
        }
    }
}

package bricker.gameobjects;

import bricker.brick_strategies.AddBallsCollisionStrategy;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Puck extends Ball {
    private static final String PUCK_TAG = "Puck";
    AddBallsCollisionStrategy myCreator;

    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                AddBallsCollisionStrategy myCreator) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.myCreator = myCreator;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        myCreator.removeCheckPuck(this);
    }

    @Override
    public String getTag() {
        return PUCK_TAG;
    }
}

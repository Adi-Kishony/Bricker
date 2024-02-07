package bricker.gameobjects;

import bricker.brick_strategies.AddBallsCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Puck extends Ball {
    AddBallsCollisionStrategy myCreator;

    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                AddBallsCollisionStrategy myCreator) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.myCreator = myCreator;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }

    @Override
    public void reCenterBall(Vector2 windowDimensions, float ballSpeed, Vector2 ballCenter) {
        super.reCenterBall(windowDimensions, ballSpeed, ballCenter);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        myCreator.removePuckBall(this);
    }

    @Override
    public String getTag() {
        return "Puck";
    }
}

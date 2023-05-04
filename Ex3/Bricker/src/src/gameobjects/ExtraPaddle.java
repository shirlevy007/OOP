package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.extraPaddle;

import java.sql.Struct;

public class ExtraPaddle extends Paddle{
    private Counter collisionCounter = new Counter();
    private GameObjectCollection gameObjectCollection = null;

    /**
     * This constructor creates the paddle object.
     *
     * @param topLeftCorner    the top left corner of the position of the text object
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the paddle (the image file of the paddle).
     * @param inputListener    The input listener which waits for user inputs and acts on them.
     * @param windowDimensions The dimensions of the screen, to know the limits for paddle movements.
     * @param minDistFromEdge  Minimum distance allowed for the paddle from the edge of the walls
     */
    public ExtraPaddle(Vector2 topLeftCorner,
                       Vector2 dimensions,
                       Renderable renderable,
                       UserInputListener inputListener,
                       Vector2 windowDimensions,
                       GameObjectCollection gameObjectCollection,
                       int minDistFromEdge,
                       int collisionsAllowed) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistFromEdge);

            this.collisionCounter = new Counter(collisionsAllowed);
            this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * This method overwrites the OnCollisionEnter of GameObject.
     * When it collides with the ball it decrements the collisions allowed
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionCounter.decrement();
        if (collisionCounter.value()<=0){
            extraPaddle.setFalseIsOn();
        }
    }

    /**
     * removes the extraPaddle if no more collisions are allowed
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (collisionCounter.value()<=0){
            this.gameObjectCollection.removeGameObject(this);
        }
    }
}

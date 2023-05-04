package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Heart extends GameObject {

    private static final int Y_SPEED = 100;
    private static final Vector2 VELOCITY = new Vector2(0, Y_SPEED);
    private final GameObjectCollection gameObjectsCollection;
    private final Counter livesCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Heart(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 Counter livesCounter,
                 GameObjectCollection gameObjectsCollection) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectsCollection = gameObjectsCollection;
        this.setVelocity(VELOCITY);
        this.gameObjectsCollection.addGameObject(this);
        this.livesCounter = livesCounter;
    }

    /**
     *
     * @param other The other GameObject.
     * @return true if the collision is with the main Paddle, else false
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other instanceof Paddle) {
            return !(other instanceof ExtraPaddle);
        }
        return false;
    }

    /**
     * when collision occurs: increments livesCounter if it's value is less than 4
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (shouldCollideWith(other)) {
            gameObjectsCollection.removeGameObject(this);
            if (this.livesCounter.value()<4){
                this.livesCounter.increment();
            }
        }
    }
}

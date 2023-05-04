package src.gameobjects;

import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject{
    /**
     * the strategy that will be used when the brick breaks.
     */
    private final CollisionStrategy collisionStrategy;
    /**
     * bricks left in the game
     */
    private final Counter bricksCounter;

    /**
     * This constructor extends the super's GameObject constructor, and also saves the strategy given.
     *
     * @param topLeftCorner the position in the window the top left corner of the object will be placed.
     * @param dimensions the 2d dimensions of the object on the screen.
     * @param renderable The renderable representing the object.
     * @param collisionStrategy the strategy that will be used when the brick breaks.
     * @param bricksCounter bricks left in the game
     */
    public Brick(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable,
                 CollisionStrategy collisionStrategy,
                 Counter bricksCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.bricksCounter = bricksCounter;
        this.bricksCounter.increaseBy(1);
    }

    /**
     * acts according to strategy when collision occurs
     * @param other The GameObject with which a collision occurred with this brick.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, bricksCounter);
    }
}

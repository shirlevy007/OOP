package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public interface CollisionStrategy {

    /**
     *
     * @param thisObj the object that was collided (the brick)
     * @param otherObj the object that collided with the brick (the ball).
     * @param bricksCounter bricks left in the game
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) ;

    /**
     * To be called on brick collision. All collision strategy objects should hold a
     * reference to the global game object collection and be able to return it.
     * @return global game object collection whose reference is held in object.
     */
    public GameObjectCollection getGameObjectCollection();
}

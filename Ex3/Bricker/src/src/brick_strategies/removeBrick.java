package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class removeBrick implements CollisionStrategy{

    /**
     *  An object which holds all game objects of the game running
     */
    GameObjectCollection gameObjectCollection;

    /**
     * Constructor
     * @param gameObjectCollection global game object collection
     */
    removeBrick(GameObjectCollection gameObjectCollection){
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * Removes brick from game object collection on collision.
     * @param thisObj the object that was collided (the brick)
     * @param otherObj the object that collided with the brick (the ball).
     * @param bricksCounter bricks left in the game
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) {
        if(gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS)){

            bricksCounter.decrement();
        }

    }

    /**
     * All collision strategy objects should hold a reference to the global game object collection
     * and be able to return it.
     * @return global game object collection whose reference is held in object.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }
}

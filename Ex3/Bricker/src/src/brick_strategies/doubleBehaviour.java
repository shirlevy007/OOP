package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.List;

public class doubleBehaviour extends removeBrick implements CollisionStrategy {

    /**
     * list of all the strategies the bricks holds
     */
    private final List<CollisionStrategy> strategies;

    /**
     *
     * @param gameObjectCollection global game object collection
     * @param strategies list of all the strategies the bricks holds
     */
    public doubleBehaviour(GameObjectCollection gameObjectCollection,
                           List<CollisionStrategy> strategies) {

        super(gameObjectCollection);
        this.strategies = strategies;
    }

    /**
     * implements all strategies that the brick holds
     * @param thisObj the object that was collided (the brick)
     * @param otherObj the object that collided with the brick (the ball).
     * @param bricksCounter bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) {
        super.onCollision(thisObj, otherObj, bricksCounter);
        for (CollisionStrategy strategy:strategies) {
            strategy.onCollision(thisObj, otherObj, bricksCounter);
        }
    }
}

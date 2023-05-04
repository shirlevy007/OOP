package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;

public class camChange extends removeBrick{

    /**
     * number of hits to return to normal state of camera
     */
    private static final int HITS_TO_RETURN = 4;
    /**
     * window controller
     */
    private final WindowController windowController;
    /**
     * the game manager
     */
    private final BrickerGameManager gameManager;
    /**
     * counter of the hits. decremented in every collision
     * STATIC VALUE - can be changed from other classes as well
     */
    private static Counter hitsCounter = new Counter();

    /**
     * The constructor of the Strategy object
     *
     * @param gameObjectCollection An object which holds all game objects of the game running
     */
    public camChange(GameObjectCollection gameObjectCollection,
                     WindowController windowController,
                     BrickerGameManager gameManager) {
        super(gameObjectCollection);
        this.windowController = windowController;
        this.gameManager = gameManager;

    }

    /**
     * Change camera's position on collision and delegate to held CollisionStrategy.
     * @param thisObj the object that was collided (the brick)
     * @param otherObj the object that collided with the brick (the ball).
     * @param bricksCounter bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) {
        super.onCollision(thisObj, otherObj, bricksCounter);
        if (gameManager.getCamera()==null)
        {
            hitsCounter = new Counter(HITS_TO_RETURN);
            gameManager.setCamera(
                    new Camera(
                            this.gameManager.getBall(),
                            Vector2.ZERO,
                            windowController.getWindowDimensions().mult(1.2f),
                            windowController.getWindowDimensions()));
        }
    }

    /**
     * used by Ball. whenever the ball collides, it decrements the counter of hits
     * STATIC FUNCTION. used in other classes of the game.
     */
    public static void decrementHitsCounter() {
        if (hitsCounter.value() > 0) {
            hitsCounter.decrement();
        }
    }

    /**
     *
     * @return number of hits left to get to normal state of camera
     * STATIC FUNCTION. used in other classes of the game.
     */
    public static int getHisCount(){
        return hitsCounter.value();
    }


}
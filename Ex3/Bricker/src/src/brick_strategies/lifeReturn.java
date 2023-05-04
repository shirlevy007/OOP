package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Heart;

public class lifeReturn extends removeBrick implements CollisionStrategy {
    /**
     * width of hearts
     */
    public static final int HEART_WIDTH = 20;
    /**
     * heart pic
     */
    private static final String HEART_PIC = "assets/heart.png";
    /**
     *
     */
    private final Counter livesCounter;
    /**
     * top left corner of brick
     */
    private final Vector2 topLeftCorner;
    /**
     * dimensions of brick
     */
    private final Vector2 dimensions;
    /**
     * image reader
     */
    private final ImageReader imageReader;


    /**
     *
     * @param livesCounter the counter which holds current lives count.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     * @param dimensions Width and height in window coordinates of each heart.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     * @param gameObjectCollection global game object collection
     */
    public lifeReturn(Counter livesCounter,
                      Vector2 topLeftCorner,
                      Vector2 dimensions,
                      ImageReader imageReader,
                      GameObjectCollection gameObjectCollection) {
        super(gameObjectCollection);
        this.livesCounter = livesCounter;
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.imageReader = imageReader;

    }

    /**
     * on collision: drops a heart to be collected by Paddle
     * @param thisObj the object that was collided (the brick)
     * @param otherObj the object that collided with the brick (the ball).
     * @param bricksCounter bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) {
        super.onCollision(thisObj, otherObj, bricksCounter);
        Renderable heartImage = this.imageReader.readImage(HEART_PIC, true);
        GameObject heart = new Heart(Vector2.ZERO,
                new Vector2(HEART_WIDTH, HEART_WIDTH),
                heartImage,
                livesCounter,
                super.getGameObjectCollection());
        heart.setCenter(thisObj.getCenter());
    }
}

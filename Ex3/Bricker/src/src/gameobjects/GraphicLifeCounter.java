package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.List;

public class GraphicLifeCounter extends GameObject {
    public static final int BORDER_WIDTH = 3;
    public static final int HEART_WIDTH = 20;
    private final Counter livesCounter;
    private final GameObjectCollection gameObjectsCollection;
    private final Vector2 dimensions;
    private final Renderable renderable;
    private int numOfLives;
    private final List <GameObject> hearts;

    /**
     * This is the constructor for the graphic lives counter. It creates a 0x0 sized object
     * (to be able to call its update method in game), Creates numOfLives hearts, and adds them to the game.
     *
     * @param topLeftCorner         Position of the object, in window coordinates (pixels).
     * @param dimensions            Width and height in window coordinates of each heart.
     * @param livesCounter          the counter which holds current lives count
     * @param renderable            the image renderable of the hearts
     * @param gameObjectsCollection the collection of all game objects currently in the game
     */
     public GraphicLifeCounter(Vector2 topLeftCorner,
                               Vector2 dimensions,
                               Counter livesCounter,
                               Renderable renderable,
                               GameObjectCollection gameObjectsCollection,
                               int numOfLives) {
        super(topLeftCorner, dimensions, null);
        this.dimensions = dimensions;
        this.renderable = renderable;
        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;

        this.hearts = new ArrayList<>();

         int rowStart = 0;
         int colStart= (int) dimensions.y()-HEART_WIDTH;
         for (int i = 0; i < this.numOfLives; i++) {
             rowStart +=  HEART_WIDTH;
             GameObject heart = new GameObject(
                     new Vector2(rowStart, colStart),
                     new Vector2(HEART_WIDTH, HEART_WIDTH),
                     renderable);
             heart.setTopLeftCorner(new Vector2(rowStart,  colStart));
             this.hearts.add(heart);
             gameObjectsCollection.addGameObject(heart, Layer.BACKGROUND);
             rowStart += BORDER_WIDTH+1;
         }
    }

    /**
     * when lifeCounter is incremented:
     * the function adds life to numOfLives, and as well one more graphic heart to the board
     */
    private void incrementLife() {
        numOfLives++;
        int rowStart = numOfLives*(HEART_WIDTH+BORDER_WIDTH+1);
        int colStart= (int) dimensions.y()-HEART_WIDTH;
        GameObject heart = new GameObject(
                new Vector2(rowStart, colStart),
                new Vector2(HEART_WIDTH, HEART_WIDTH),
                renderable);
        heart.setTopLeftCorner(new Vector2(rowStart,  colStart));
        this.hearts.add(heart);
        gameObjectsCollection.addGameObject(heart, Layer.BACKGROUND);
    }

    /**
     * This method is overwritten from GameObject It removes hearts from the screen
     * if there are more hearts than there are lives left
     * and handles it if there are fewer hearts than lives left
     *
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
        if (livesCounter.value()<numOfLives){
            gameObjectsCollection.removeGameObject(this.hearts.get(--numOfLives), Layer.BACKGROUND);
            hearts.remove(hearts.get(numOfLives));
        }
        while (livesCounter.value()>numOfLives){
            incrementLife();
        }
    }
}

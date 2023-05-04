package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.ExtraPaddle;
import src.gameobjects.Paddle;

import static src.BrickerGameManager.*;

public class extraPaddle extends removeBrick {

    /**
     * border width
     */
    private static final int BORDER_WIDTH = 4;
    /**
     * paddle width
     */
    private static final int PADDLE_WIDTH = 100;
    /**
     * paddle height
     */
    private static final int PADDLE_HEIGHT = 15;
    /**
     * collisions allowed in extraPaddle before it is removed according to instructions
     */
    private static final int COLLISIONS_ALLOWED = 3;
    /**
     * paddle img
     */
    private static final String PADDLE_PIC = "assets/botGood.png";
    /**
     * true if extraPaddle is on - so no more than 1 is created at same time
     * STATIC VALUE - can be changed from other classes as well
     */
    private static boolean isOn = false;

    /**
     * global game object collection
     */
    private final GameObjectCollection gameObjectCollection;
    /**
     * images reader
     */
    private final ImageReader imageReader;
    /**
     * gets user input to follow
     */
    private final UserInputListener inputListener;
    /**
     * window dimensions
     */
    private final Vector2 windowDimensions;

    /**
     *
     * @param gameObjectCollection global game object collection
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether a given key is
     *                       currently pressed by the user or not.
     * @param windowDimensions Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    public extraPaddle(GameObjectCollection gameObjectCollection,
                       ImageReader imageReader,
                       UserInputListener inputListener,
                       Vector2 windowDimensions) {
        super(gameObjectCollection);
        this.gameObjectCollection = gameObjectCollection;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;

    }

    /**
     * on collision: creates ExtraPaddle in the game if none is on already
     * @param thisObj the object that was collided (the brick)
     * @param otherObj the object that collided with the brick (the ball).
     * @param bricksCounter bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) {
        super.onCollision(thisObj, otherObj, bricksCounter);
        if(!isOn){
            Renderable extraPaddleImage = this.imageReader.readImage(PADDLE_PIC, true);
            ExtraPaddle extraPaddle = new ExtraPaddle(Vector2.ZERO,
                    new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                    extraPaddleImage, this.inputListener, this.windowDimensions,
                    gameObjectCollection, BORDER_WIDTH, COLLISIONS_ALLOWED);

            extraPaddle.setCenter(new Vector2(this.windowDimensions.x() / 2, this.windowDimensions.y() / 2));
            this.gameObjectCollection.addGameObject(extraPaddle);
            isOn=true;
        }
    }

    /**
     * sets isOn to false. STATIC FUNCTION. used in other classes of the game.
     */
    public static void setFalseIsOn(){
        isOn = false;
    }
}

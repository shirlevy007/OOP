package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;


public class NumericLifeCounter extends GameObject {
    /**
     * Numeric life counter representation
     */
    private final TextRenderable textRenderable;
    /**
     * counter of life lest in the game
     */
    private final Counter livesCounter;
    /**
     * the collection of all game objects currently in the game
     */
    private final GameObjectCollection gameObjectCollection;

    /**
     * The constructor of the textual representation object of how many strikes are left in the game.
     *
     * @param livesCounter The counter of how many lives are left right now.
     * @param topLeftCorner the top left corner of the position of the text object
     * @param dimensions the size of the text object
     * @param gameObjectCollection the collection of all game objects currently in the game
     */
    public NumericLifeCounter(Counter livesCounter,
                              Vector2 topLeftCorner,
                              Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        TextRenderable textRenderable = new TextRenderable(String.format("%d", this.livesCounter.value()));
        this.renderer().setRenderable(textRenderable);
        textRenderable.setColor(Color.GREEN);
        this.textRenderable = textRenderable;
    }

    /**
     * This method is overwritten from GameObject.
     * It sets the string value of the text object to the number of current lives left.
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
        if (this.livesCounter.value()>2){
            textRenderable.setColor(Color.GREEN);
        }
        else if (livesCounter.value()==2){
            textRenderable.setColor(Color.YELLOW);
        }
        else if (livesCounter.value() == 1) {
            textRenderable.setColor(Color.RED);
        }
        textRenderable.setString(String.format("%d", livesCounter.value()));
    }
}

package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    /**
     * The movement speed of the paddle
     */
    private static final int MOVEMENT_SPEED = 500;
    /**
     * The input listener which waits for user inputs and acts on them.
     */
    private final UserInputListener inputListener;
    /**
     * The dimensions of the screen, to know the limits for paddle movements.
     */
    private final Vector2 windowDimensions;
    /**
     * Minimum distance allowed for the paddle from the edge of the walls
     */
    private final int minDistFromEdge;

    /**
     * This constructor creates the paddle object.
     *
     * @param topLeftCorner the top left corner of the position of the text object
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the paddle (the image file of the paddle).
     * @param inputListener The input listener which waits for user inputs and acts on them.
     * @param windowDimensions The dimensions of the screen, to know the limits for paddle movements.
     * @param minDistFromEdge  Minimum distance allowed for the paddle from the edge of the walls
     */
    public Paddle(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  UserInputListener inputListener,
                  Vector2 windowDimensions,
                  int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;
    }

    /**
     * This method is overwritten from GameObject.
     * If right and/or left key is recognised as pressed by the input listener, it moves the paddle,
     * and check that it doesn't move past the borders.
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
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));

        transform().setTopLeftCornerX(Math.max(getTopLeftCorner().x(), minDistFromEdge));
        transform().setTopLeftCornerX(Math.min(getTopLeftCorner().x(),
                windowDimensions.x() - minDistFromEdge - getDimensions().x()));


    }
}

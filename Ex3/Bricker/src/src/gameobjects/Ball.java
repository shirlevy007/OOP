package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.camChange;

import java.util.Random;

public class Ball extends GameObject {

    /**
     * speed of ball
     */
    private static final int BALL_SPEED = 250;
    /**
     * sound when there's collision
     */
    private final Sound collisionSound;

    /**
     *
     * This is the ball object constructor. It uses it's super's constructor and saves the sound file.
     *
     * @param topLeftCorner Position of the ball, in window coordinates (pixels).
     * @param dimensions    Width and height of the ball in window coordinates.
     * @param renderable    The renderable representing the ball (the image object of the ball).
     * @param collisionSound sound when there's collision
     */
    public Ball(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    /**
     * set random velocity for the ball
     */
    public void setRandomVelocity(){
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()){
            ballVelX *= -1;
        }
        if (rand.nextBoolean()){
            ballVelY *= -1;
        }
        this.setVelocity(new Vector2(ballVelX, ballVelY));
    }


    /**
     * This method overwrites the OnCollisionEnter of GameObject.
     * When it collides with another object, it flips its direction.
     * @param other the object that the ball collided with
     * @param collision the collision parameters
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
        camChange.decrementHitsCounter();
    }
}

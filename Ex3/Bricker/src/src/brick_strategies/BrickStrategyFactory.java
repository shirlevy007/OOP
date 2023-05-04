package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BrickStrategyFactory {
    /**
     * Random object
     */
    private final Random rand;
    /**
     * global game object collection
     */
    private GameObjectCollection gameObjectCollection;
    /**
     * the game manager
     */
    private final BrickerGameManager gameManager;
    /**
     * user input listener
     */
    private final UserInputListener inputListener;
    /**
     * window controller
     */
    private final WindowController windowController;
    /**
     * window dimensions
     */
    private final Vector2 windowDimensions;
    /**
     * top left corner
     */
    private Vector2 topLeftCorner;
    /**
     * dimensions
     */
    private Vector2 dimensions;
    /**
     * image reader
     */
    private final ImageReader imageReader;
    /**
     * sound reader
     */
    private final SoundReader soundReader;


    /**
     * Constructor
     * @param gameObjectCollection global game object collection
     * @param gameManager of game
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     * @param soundReader Contains a single method: readSound, which reads a wav file from disk.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether a given key is
     *                      currently pressed by the user or not.
     * @param windowController Contains an array of helpful, self-explanatory methods concerning the window.
     * @param windowDimensions a 2d vector representing the height and width of the window
     */
    public BrickStrategyFactory(GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager,
                                ImageReader imageReader,
                                SoundReader soundReader,
                                UserInputListener inputListener,
                                WindowController windowController,
                                Vector2 windowDimensions){
        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
        this.rand = new Random();
    }

    /**
     * method randomly selects between 5 strategies and returns one CollisionStrategy object
     * which is a RemoveBrick extended
     * @return CollisionStrategy object.
     */
    public CollisionStrategy getStrategy() {
        int behaviourNum = rand.nextInt(Strategies.values().length);
        Strategies behaviour = Strategies.values()[behaviourNum];

        CollisionStrategy collisionStrategy = null;
        switch (behaviour){
            case BASIC:
                collisionStrategy = new removeBrick(gameObjectCollection);
                break;
            case EXTRA_BALLS:
                collisionStrategy = new extraBalls(gameObjectCollection,
                        imageReader,
                        soundReader);
                break;
            case EXTRA_PADDLE:
                collisionStrategy = new extraPaddle(gameObjectCollection,
                        imageReader,
                        inputListener,
                        windowDimensions);
                break;
            case CHANGE_CAMERA:
                collisionStrategy = new camChange(gameObjectCollection,
                        windowController,
                        gameManager);
                break;
            case EXTRA_LIFE:
                collisionStrategy = new lifeReturn(gameManager.livesCounter,
                        topLeftCorner,
                        dimensions,
                        imageReader,
                        gameObjectCollection);
                break;
            case DOUBLE:
                List<CollisionStrategy> strategies= handleDoubleStrategy();
                collisionStrategy = new doubleBehaviour(gameObjectCollection,
                        strategies);
                break;
        }
        return collisionStrategy;
    }

    /**
     * used in order to not repeat code
     * updates the list of Strategies objects: adds 2 more behaviours (as described in the forum)
     * @param strategiesNum a list of Strategies objects to be implemented to the brick
     */
    private void addTwoWithoutDouble(List<Strategies> strategiesNum){
        // behaviour2 - without DOUBLE
        int behaviourNum = rand.nextInt(Strategies.values().length-1);
        Strategies behaviour = Strategies.values()[behaviourNum];
        strategiesNum.add(behaviour);
        // behaviour3 - without DOUBLE
        behaviourNum = rand.nextInt(Strategies.values().length-1);
        behaviour = Strategies.values()[behaviourNum];
        strategiesNum.add(behaviour);
    }

    /**
     * method randomly selects between 5 strategies according to instructions
     * @return a list of CollisionStrategy objects.
     */
    private List<CollisionStrategy> handleDoubleStrategy(){
        List<Strategies> strategiesNum = new ArrayList<>();
        List<CollisionStrategy> strategies = new ArrayList<>();
        // behaviour1 - without BASIC
        int behaviourNum = rand.nextInt(1, Strategies.values().length);
        if (Strategies.values()[behaviourNum]==Strategies.DOUBLE){
            // behaviour1 - without BASIC, DOUBLE
            behaviourNum = rand.nextInt(1, Strategies.values().length-1);
            Strategies behaviour = Strategies.values()[behaviourNum];
            strategiesNum.add(behaviour);
            addTwoWithoutDouble(strategiesNum);
        }
        else {
            // behaviour1 - regular: than check second behaviour for DOUBLE
            Strategies behaviour = Strategies.values()[behaviourNum];
            strategiesNum.add(behaviour);
            // behaviour2 - from all
            behaviourNum = rand.nextInt(Strategies.values().length);
            if (Strategies.values()[behaviourNum]==Strategies.DOUBLE){
                addTwoWithoutDouble(strategiesNum);
            }
            else {
                // behaviour2 - from all
                behaviour = Strategies.values()[behaviourNum];
                strategiesNum.add(behaviour);
            }
        }
        // turn behaviours to CollisionStrategy
        for (Strategies s:strategiesNum) {
            CollisionStrategy collisionStrategy = null;
            switch (s){
                case BASIC:
                    collisionStrategy = new removeBrick(gameObjectCollection);
                    break;
                case EXTRA_BALLS:
                    collisionStrategy = new extraBalls(gameObjectCollection,
                            imageReader,
                            soundReader);
                    break;
                case EXTRA_PADDLE:
                    collisionStrategy = new extraPaddle(gameObjectCollection,
                            imageReader,
                            inputListener,
                            windowDimensions);
                    break;
                case CHANGE_CAMERA:
                    collisionStrategy = new camChange(gameObjectCollection,
                            windowController,
                            gameManager);
                    break;
                case EXTRA_LIFE:
                    collisionStrategy = new lifeReturn(gameManager.livesCounter,
                            topLeftCorner,
                            dimensions,
                            imageReader,
                            gameObjectCollection);
                    break;
            }
            strategies.add(collisionStrategy);
        }
        return strategies;
    }

}

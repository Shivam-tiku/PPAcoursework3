import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static  double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static  double RABBIT_CREATION_PROBABILITY = 0.08;
    // The probability that a wolf will be created in any given grid position.    
    private static double WOLF_CREATION_PROBABILITY = 0.01;
     // The probability that a hunter will be created in any given grid position.
    private static double HUNTER_CREATION_PROBABILITY = 0.002;
    // The probability that grass will be created in any given grid position.
    private static double GRASS_CREATION_PROBABILITY = 1;
    
    

    // List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        actors = new ArrayList<Actor>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Rabbit.class, Color.ORANGE);
        view.setColor(Fox.class, Color.BLUE);
        view.setColor(Wolf.class, Color.BLACK);
        view.setColor(Hunter.class, Color.RED);
        view.setColor(Grass.class, Color.GREEN);
        
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            // delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;
        //provide space for newborn actors.
        List<Actor> newActors = new ArrayList <Actor>();
        //let all actors act.
        for(Iterator<Actor> it = actors.iterator();it.hasNext();) {
            Actor actor = it.next();
            actor.act(newActors);
            if (actor instanceof Animal)     // check if actor is an animal
            {
                Animal animal =(Animal) actor;
                if(! animal.isAlive()) {
                    it.remove();
                }
            }
        }
        // Add all the newly born animals to the main lists
        actors.addAll(newActors);
        
        view.showStatus(step, field);
    }
    
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * get simulatorview
     * @return simulatorview
     */
    public SimulatorView getSimulatorView()
    {
        return view;
    }
    
    /**
     * get field
     * @return field
     */
    public Field getField()
    {
        return field;
    }
    
    /**
     * get step
     * @return int step
     */
    public int getStep()
    {
        return step;
    }
  
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    actors.add(rabbit);
                }
                else if (rand.nextDouble() <= WOLF_CREATION_PROBABILITY) {
                    Location location = new Location (row, col);
                    Wolf wolf = new Wolf(true, field, location);
                    actors.add(wolf);
                }
                 else if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Hunter hunter = new Hunter(field, location);
                    actors.add(hunter);
                }
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(true, field, location);
                    actors.add(grass);
                }
                // else leave the location empty.
            }
        }
    }
    
    
}

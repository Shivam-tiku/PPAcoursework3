import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 */
public class Rabbit extends Prey
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static int BREEDING_AGE = 3;
    // The age to which a rabbit can live.
    private static int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static double BREEDING_PROBABILITY = 0.98;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE = 4;
    // This is the number of steps a rabbit can go before it has to eat at the begining.
    private static final int INITIAL_HUNGER_VALUE = 8;
    // food value when eaten.
    private static final int FOOD_VALUE = 2;
    // This is the food level after which a rabbit cannot eat.
    private static final int MAX_FOOD_LEVEL = 16;
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    
    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        
        if(randomAge) {
        	age = rand.nextInt(MAX_AGE);
        	foodLevel = rand.nextInt( INITIAL_HUNGER_VALUE );
        }
        else {
            age = 0;
            foodLevel =  INITIAL_HUNGER_VALUE ;
        }
        
    }
    
    /**
     * Creates a new rabbit object.
     * @param randomAge If true, the rabbit will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @return animal of type rabbit.
     */
    protected Animal getNewAnimal(boolean randomAge, Field field, Location loc)
    {
        Animal young;
        return young = new Rabbit(false, field, loc);
    }
    
    /**
     * return the MAX_AGE of the rabbit.
     * @return int MAX_AGE
     */
    protected int getMAX_AGE()
    {
        return MAX_AGE;
    }
    
    
    
    /**
     * returns the breeding probability of the rabbit
     * @return double breeding probabilty
     */

    protected double getBREEDING_PROBABILITY()
    {
        return BREEDING_PROBABILITY;
    }
    
    /**
     * return the maximum food level  
     * @return int maximum food level.
     */
    protected int getMAX_FOOD_LEVEL()
    {
        return MAX_FOOD_LEVEL;
    }

    /**
     * return the max litter size
     * @return int max litter size
     */
    protected int getMAX_LITTER_SIZE()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * return the breeding age
     * @return int breeding age
     */
    protected int getBREEDING_AGE()
    {
        return BREEDING_AGE;
    }

    /**
     * return the foodvalue
     * @return int foodvalue
     */
    public int getFOOD_VALUE()
    {
        return FOOD_VALUE;
    }
      
    
}
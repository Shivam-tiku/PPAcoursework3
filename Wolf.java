import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * A simple model of a wolf
 * wolves age, move, eat fox and rabbits and die.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Wolf extends Predator
{
    // Characteristics shared by all Wolves (class variables).

    // The age at which a wolf can start to breed.
    private static int BREEDING_AGE = 15;
    // The age to which a wolf can live.
    private static int MAX_AGE = 150;
    // The likelihood of a wolf breeding.
    private static double BREEDING_PROBABILITY = 0.8;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE = 2;
    // This is the number of steps a wolf can go before it has to eat at the begining.
    private static final int INITIAL_HUNGER_VALUE = 13;
    
    // This is the food level after which a wolf cannot eat.
    private static final int MAX_FOOD_LEVEL = 30;
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a wolf. A wolf can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the wolf will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Wolf(boolean randomAge, Field field, Location location)
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
     * Creates a new Wolf object.
     * @param randomAge If true, the Wolf will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @return animal of type Wolf
     */
    protected Animal getNewAnimal(boolean randomAge, Field field, Location loc)
    {
        Animal young;
        return young = new Wolf(false, field, loc);
    }
    
    /**
     * Wolf sleeps between 0 and 6 hours.
     * @param current time.
     * @return true false if the WOlf is sleeping.
     */
    protected boolean isNotAsleep(int time)
    {
        if( (0 <= time) && (time <= 6)) {
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * return the MAX_AGE of the Wolf.
     * @return int MAX_AGE
     */
    protected int getMAX_AGE()
    {
        return MAX_AGE;
    }
    
    /**
     * returns the breeding probability of the wolf
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

    
}

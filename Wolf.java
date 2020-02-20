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
public class Wolf extends Animal
{
    // characteristics shared by all wolves (class variables).
    
    // the age at which a wolf can start to breed.
    private static int BREEDING_AGE = 30;
    // the age to which a wolf can live
    private static int MAX_AGE = 190;
    // the likelihood of a wolf breeding
    private static double BREEDING_PROBABILITY = 0.02;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE = 1;
    // The food value of a single rabbit and a single fox. In effect, this is the
    // number of steps a wolf can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;
    private static final int FOX_FOOD_VALUE = 18;
    
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
            setAge(getRandom().nextInt(MAX_AGE));
            setFoodLevel(getRandom().nextInt(RABBIT_FOOD_VALUE + FOX_FOOD_VALUE));
        }
        else{
	            setAge(0);
	            setFoodLevel(RABBIT_FOOD_VALUE + FOX_FOOD_VALUE);      
        	}
    }
    
    /**
     * This is what the wolf does most of the time: it hunts for
     * foxes and rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newwolves A list to return newly born wolves.
     */    
    public void act(List<Actor> newWolves)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newWolves);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    /**
     * returns the maximum age of a wolf can live
     * @return int maximum age of a wolf can live
     */
    protected int getMaxAge()
    {
    	return MAX_AGE;
    }
    
    /**
     * Make this wolf more hungry. This could result in the wolf's death.
     */
    private void incrementHunger()
    {
        setFoodLevel(getFoodLevel() - 1);
        if(getFoodLevel() <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for foxes and rabbits adjacent to the current location.
     * Only the first live foxes or rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) 
            {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) 
                { 
                    rabbit.setDead();
                    setFoodLevel(RABBIT_FOOD_VALUE);
                    return where;
                }
            }
            else if (animal instanceof Fox)
            {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) 
                { 
                    fox.setDead();
                    setFoodLevel(FOX_FOOD_VALUE);
                    return where;
                }
            	
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this wolf is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newwolfrs A list to return newly born wolves.
     */
    private void giveBirth(List<Actor> newWolves)
    {
        // New wolves are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Wolf young = new Wolf(false, field, loc);
            newWolves.add(young);
        }
    }

    /**
     * A wolf can breed if it has reached the breeding age.
     */
    protected boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }
    
    /**
     * Getter om BREEDING_AGE op te halen
     */
    protected int getBreedingAge()
    {
    	return BREEDING_AGE;
    }
    
    /**
     * Getter om MAX_LITTER_SIZE op te halen
     * @return MAX_LITTER_SIZE maximum litter
     */
    protected int getMaxLitterSize()
    {
    	return MAX_LITTER_SIZE;
    }
    
    /**
     * Getter om BREEDING_PROBABILITY op te halen
     * @return BREEDING_PROBABILITY breeding kansen
     */
    protected double getBreedingProbability()
    {
    	return BREEDING_PROBABILITY;
    }
    
    /**
     * setter for a new breeding age
     * @param newBREEDING_AGE
     */
    public static void setBreedingAge(int newBREEDING_AGE)
    {
    	BREEDING_AGE = newBREEDING_AGE;
    }
    
    /**
     * setter for a new max age
     * @param newMAX_AGE
     */
    public static void setMaxAge(int newMAX_AGE)
    {
    	MAX_AGE = newMAX_AGE;
    }
    
    /**
     * setter for a new breeding probability
     * @param newBREEDING_PROBABILITY
     */
    public static void setBreedingProbability(double newBREEDING_PROBABILITY)
    {
    	BREEDING_PROBABILITY = newBREEDING_PROBABILITY;
    }
    
    /**
     * setter for a new max litter size
     * @param newMAX_LITTER_SIZE
     */
    public static void setMaxLitterSize(int newMAX_LITTER_SIZE)
    {
    	MAX_LITTER_SIZE = newMAX_LITTER_SIZE;
    }
    
  
}

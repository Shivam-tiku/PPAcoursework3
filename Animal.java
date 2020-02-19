import java.util.List;
import java.util.Random;

/**
 * A simple model of a animal.
 * Animals age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Animal extends Actor
{
    // Characteristics shared by all animals (class variables).

    // The age at which a animal can start to breed.
    public static int BREEDING_AGE;
    // The age to which a animal can live.
    public static int MAX_AGE;
    // The likelihood of a animal breeding.
    public static double BREEDING_PROBABILITY;
    // The maximum number of births.
    public static int MAX_LITTER_SIZE;
    // A shared random number generator to control breeding.
    public static Random rand = Randomizer.getRandom();
    //The gender of the animal
    public Gender gender;
    public static enum Gender {
        MALE, FEMALE
    };
    
    
    // Individual characteristics (instance fields).
    
    // The animal's age.
    private int age;

    /**
     * Create a new animal. A animal may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the animal will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean randomAge, Field field, Location location, Gender gender)
    {
        super(field, location);
        age = 0;
        this.gender = gender;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * This is what the animal does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newAnimals A list to return newly born animals.
     */
    public void act(List<Actor> newAnimals)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newAnimals);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
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
     * Increase the age.
     * This could result in the animal's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born animals.
     */
    private void giveBirth(List<Actor> newAnimals)
    {
        // New animals are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            
            Animal young = new Animal(false, field, loc, gender);
            newAnimals.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A animal can breed if it has reached the breeding age.
     * @return true if the animal can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
    // randomizes the gender of a New animal 
    private Gender getGender() 
    {
        return gender; 
    }
}

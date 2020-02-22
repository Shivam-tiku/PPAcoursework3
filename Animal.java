import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * Animals age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Actor
{
    // Characteristics shared by all animals (class variables).
    //Probability that an animal will be randomly infected by disease.
    private static final double CHANCE_OF_INFECTION = 0.0001;
    //Probability that an infected animal will spread disease by contact.
    private static final double CHANCE_TO_SPREAD_INFECTION = 0.1;
    
    // The animal's food level. which is incremented by eating
    protected int foodLevel;
    // Male or female. Represented by 0 or 1.
    private boolean gender;
    //Empty disease object to represent an animal that is not infected.
    private Disease disease;
    
    
    // A shared number generator to control the animal's breeding.
   
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        super(field, location);
        // randomly generate a gender
        gender = rand.nextBoolean();
        
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    public void act(List<Actor> newActors) {
        incrementHealth();
        actMove();
    }
    
    /**
     * increments health of animal
     */
    protected void incrementHealth()
    {
        incrementAge();
        incrementHunger();
        // Checks if animal has died of a disease.
        getDiseaseFinished();
    }
    
    /**
     * If animal is not dead, asleep or blinded by fog:
     * They will try to give birth and
     * move to the nearest location or try to find food.
     * 
     * @param newActors A list to return newly created actors.
     */
    protected void actMove(List<Actor> newActors)
    {
        if(isAlive() && isNotAsleep(Simulator.getTime()) && isFogImmune()) {
                giveBirth(newActors);
                
                randomDisease();
                spreadDisease();
                
                Location newLocation = getNextLocation();
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
     * animals will always move to the nearest location to find food.
     */
    protected Location getNextLocation()
    {
        // Move towards a source of food if found.
        Location newLocation = findFood();
        if(newLocation == null || isFull()) {
            // No food found or the animal is full - try to move to a free location.
            newLocation = getField().freeAdjacentLocation(getLocation());
        }
        return newLocation;
    }
    
    /**
     * if the animal is affected with disease or not 
     * return true if the animal is affected, otherwise return false.
     */
    protected boolean isDiseased()
    {
        if(disease!=null)
        {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Animals will randomly get infected with a disease
     */
    protected void randomDisease()
    {
        if(!isDiseased() && (rand.nextDouble() <= PROBABILITY_OF_INFECTION_RANDOM)) {
            // Creates a new disease object.
            disease = new Disease();
            
        }
    }
    
    /**
     * Spreads disease to adjacent animals of the same species.
     * 
     */
    protected void spreadDisease()
    {
        if(isDiseased()) {
            Field field = getField();
            // get iteratable list of adjacent locations.
            List<Location> adjacent = field.adjacentLocations(getLocation());
            Iterator<Location> it = adjacent.iterator();
            // Check each location for animals.
            while(it.hasNext()) {
                Location where = it.next();
                // Get object in adjacent location
                Object animal = field.getObjectAt(where);
                Animal nextAnimal = (Animal) animal;
                //Check if location contains animal and if it is the same species.
                if(nextAnimal!=null && this.getClass().equals(nextAnimal.getClass())) {
                    // Probability check for infection by contact.
                    if (rand.nextDouble() <= PROBABILITY_OF_INFECTION_CONTACT ) {
                        // Give disease to adjacent animal.
                        nextAnimal.giveDisease();
                    }
                }
            }
        }
    }

    /**
     * Creates a new disease object for infected animal.
     * This disease has a new counter.
     */
    protected void giveDisease()
    {
        this.disease = getNewDisease();
        
    }
    
    /**
     * get newDisease.
     */
    protected Disease getNewDisease()
    {
        return new Disease();
    }
    
    /**
     * Check if disease has finished and killed the animal.
     * If not then the animal has survived.
     */
    protected void getDiseaseFinished()
    {
        if((isDiseased()) && disease.getDiseaseFinished())
        {
            if(disease.getDiseaseDeath()) {
                setDead();
                
            }
            else {
                disease = null;
            }
        }
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreedAge() && rand.nextDouble() <= getBREEDING_PROBABILITY()) {
            births = rand.nextInt(getMAX_LITTER_SIZE()) + 1;
        }
        return births;
    }
    
    /**
     * @return true if animal is old enough to breed.
     */
    protected boolean canBreed()
    {
        return age >= getBREEDING_AGE();
    }

    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born animals.
     */
    protected void giveBirth(List<Actor> newAnimals)
    {
        // New animals are born into adjacent locations.
        
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            Animal nextAnimal = (Animal) animal;
            // Check if adjacent animal is of same species.
            if(nextAnimal!=null && this.getClass().equals(nextAnimal.getClass())) {
            // Check if adjacent animal is of the opposite sex.
            if((this.getMale() && !nextAnimal.getMale()) || (!this.getMale() && nextAnimal.getMale()))
            {
                List<Location> free = field.getFreeAdjacentLocations(getLocation());
                int births = breed();
            for(int b = 0; b < births && free.size() > 0; b++) {
                Location loc = free.remove(0);
                Animal young = getNewAnimal(false, field, loc);
                newAnimals.add(young);
                
            }
            }
        }
        }

    }

    /**
     * @return true if animal is a male.
     * (gender = 1)
     * 0 if female.
     */
    private boolean getMale()
    {
        if(gender==true)
        {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Make this animal more hungry.
     * This could result in the animal's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Add the food value of an eaten animal to current food level.
     */
    protected void addFoodValue(int foodValue)
    {
        foodLevel += foodValue;
        isFull();
    }
    
    /**
     * @return boolean full tur if the foodlevel exceeds the max food level for that animal.
     */
    protected boolean isFull()
    {
        boolean full = false;
        if( foodLevel > getMAX_FOOD_LEVEL() ) {
            full = true;
        }
        return full;
    }

    /**
     * Look for food adjacent to the current location.
     * Only the first live animal of the right type is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            if(getFood(where))
            {
                return where;
            }
        }
        return null;
    }
    
    /**
    * @return true if there is edible food in given location.
    */
    protected boolean getFood(Location where)
    {
        Edible edible = getEdible(where);
            if(edible != null)
        {
            Actor animal = (Actor) edible;
            if(animal.isAlive()) {
               animal.setDead();
               addFoodValue(edible.getFOOD_VALUE());
               return true;
            }
        }
        return false;
    }
    
    /**
     * @return true if animal is asleep.
     */
    protected boolean isNotAsleep(int time)
    {
        return true;
    }
    
    /**
     * @return true if animal is not currently affected by fog.
     */
    protected boolean isFogImmune()
    {
        return true;
    }
    
    /**
     * Creates new object of animal.
     * @return newly born animal.
     * @param true for a randomly aged animal, current field, location to make new animal.
     */
    protected abstract Animal getNewAnimal(boolean randomAge, Field field, Location location);
    
    /**
     * @return edible entity at given location.
     * @param location to check.
     */
    protected abstract Edible getEdible(Location where);
    
    /**
     * @return probability of breeding.
     */
    protected abstract double getBREEDING_PROBABILITY();
    
    /**
     * @return age when animal can breed.
     */
    protected abstract int getBREEDING_AGE();
    
    /**
     * @return maximum litter size.
     */
    protected abstract int getMAX_LITTER_SIZE();
    
    /**
     * @return maximum food level.
     */
    protected abstract int getMAX_FOOD_LEVEL();
    
    /**
     * returns the chance of infection
     */
    protected double getCHANCE_OF_INFECTION () {
        return CHANCE_OF_INFECTION; 
    } 
    
}
    
    
    
   
    


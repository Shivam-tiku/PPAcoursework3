import java.util.List;
import java.util.Random;

/**
 * A simple model of a animal.
 * Animals age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal implements Actor
{
    //whether the animal is alive or not
    private boolean alive;
    // The animal's field
    private Field field;
    // The animal's position in the field.
    private Location location;
    //the animals age
    private int age;
    // the animals food level which is incremented by eating.
    private int foodLevel;
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
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    public abstract void act(List<Actor> newAnimals);
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * increase the age which could result in the animal's death.
     */
    protected void incrementAge()
    {
        setAge(getAge() +1);
        if (getAge() > getMaxAge()) {
            setDead(); 
        }
    }
    
    /**
     * returns the maximum age of an animal
     * @return the maximum age of an animal
     */
    protected abstract int getMaxAge();
    
    /**
     * return the animal's location
     * @return the animals location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && getRandom().nextDouble() <= getBreedingProbability()) {
            births = getRandom().nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed false otherwise.
     */
    protected abstract boolean canBreed();
    
    /**
     * returns the animals current age
     * @return age int the current age of the animal.
     */
    protected int getAge()
    {
        return age;
    }
    
    /**
     * set an animals current age.
     * @param age int set the current age
     */
    protected void setAge(int age)
    {
        this.age = age;
    }
    
    /**
     * reurns the animals current foodlevel
     * @param foodlevel int set the current foodlevel
     */
    protected int getFoodLevel()
    {
        return foodLevel;
    }
    
    /**
     * set an animals current foodLevel.
     * @param foodLevel int set the current foodLevel.
     */
    protected void setFoodLevel(int foodLevel)
    {
        this.foodLevel = foodLevel;
    }
    
    protected Random getRandom()
    {
        return rand;
    }
    
    /**
     * get Max Litter Size
     * @return Maxlittersize
     */
    protected abstract int getMaxLitterSize();
    
    /**
     * get breeding probability
     * @return breeding probability
     */
    protected abstract double getBreedingProbability();
    
   
    
}

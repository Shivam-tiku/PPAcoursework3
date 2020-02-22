import java.util.List;
import java.util.Random;
import java.util.Iterator;


/**
 * A class representing shared characteristics of actors.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Actor
{
    // Whether the animal is alive or not.
    private boolean alive;
    // Whether the animal is alive or not.
    protected int age;
    
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    protected Location location;
    
    private static final Random rand = Randomizer.getRandom();
    /**
     * Contstructor for the abstract class Actor;
     * create a new actor at a location in the field
     * 
     * @param field the field currently occupied
     * @param location the location within the field.
     */
    public Actor(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * returns the age to which an animal can live.
     * @return int the max age of an animal
     */
    protected abstract int getMAX_AGE();
    
    /**
     * check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive () {
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
     *  make this animal act - that is: make it do
     *  whatever it wants/needs to do.
     *  @param newAnimals A list to recieve newly born animals.
     */
    abstract public void act (List <Actor> newActors);
    
    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * return the animal's field.
     * @return the animal's field
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMAX_AGE()) {
            setDead();
        }
    }
    
    
    
}

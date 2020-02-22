/**
 * Abstract class Prey - write a description of the class here
 *
 * 
 */
public abstract class Prey extends Animal implements Edible
{
    /**
     * Creates prey at given location.
     */
    public Prey (Field field, Location location){
        super(field, location);
    }
    
    /**
     * Plants do not move 
     * Therefore prey randomly move to free locations
     * to find plants that are growing.
     */
    protected Location getNextLocation()
    {
        Location newLocation = getField().freeAdjacentLocation(getLocation());
        return newLocation;
    }
    
    /**
     * Checks if given locations contains a plant that prey can eat.
     * @param location.
     * @return Edible object of type plant.
     */
    protected Edible getEdible(Location where)
    {
      // Checks plant field for plant at location.
      Object animal = getField().getPlantAt(where);
      Edible plant = null;
      if(animal instanceof Plant)
      {
          plant = (Plant) animal;
      }
      return plant;
    }

    public abstract int getFOOD_VALUE();
}

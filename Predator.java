/**
 * Abstract class Predator - Predators eat prey.
 *
 * 
 */
public abstract class Predator extends Animal
{
    /**
     * Creates predator at given location.
     */
    public Predator (Field field, Location location){
        super(field, location);

    }

    /**
     * Checks if given location contains a prey which is edible for predators.
     * @param location to check.
     * @return Edible animal of type prey.
     */
    protected Edible getEdible(Location where)
    {
      Object animal = getField().getObjectAt(where);
      Edible prey = null;
      if(animal instanceof Prey) {
              prey = (Prey) animal;
      }
      return prey;
    }
}
import java.util.List;

/**
 * A class representing shared characteristics of actors.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public interface Actor
{
    /**
     * Make this actor do whatever it wants to do
     * @param newActors list to recieve newly born animals
     */
    
    void act (List<Actor> newActors);
    
    /**
     * place the animal at the new location in the given field
     * @param newLocation the animals new location.
     */
    void setLocation (Location newLocation);
    
}

package model;

/** 
 * This class holds information about a Copier job.

 * @author  Dymock Brett
 * @version v1.0
 */

public interface ListenerInterface
{

    void registerListener(Listener listener);
    void notifyListener(String[] msgs) throws ListenerException;

}


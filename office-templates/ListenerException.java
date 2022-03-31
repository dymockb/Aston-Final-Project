package model;

import util.LeadingZeros;

/*  
 * 
 *
 * @author  Dymock Brett
 * @version v1.0
 */

public class ListenerException extends Exception
{

    int machineCode;
    /**
     * Constructor - 
     */
    public ListenerException(int machineCode){
        super();
        this.machineCode = machineCode;
    }

    /**
     */
    public String toString(){
        System.out.println("ListenerException for machine " + LeadingZeros.convertInteger(machineCode));
        return "No Listener on record for machine " + LeadingZeros.convertInteger(machineCode);
    }

}

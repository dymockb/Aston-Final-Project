package model;

import util.LeadingZeros;

//import java.util.HashMap;

/**
 * Class OfficeMachine - an abstract class describing the general
 * functions of various types of Office Machine.
 *
 * @author  Dymock Brett
 * @version 1.0
 */

public abstract class OfficeMachine implements ListenerInterface
{
    
    /* 
     * Doc string 
     */

    protected String machineType;
    protected String machineDesc;
    protected int machineCode;
    protected boolean online;
    protected boolean error;
    protected Job job;
    protected Listener listener; 
    protected String[] notifications;

    public OfficeMachine(){
        online = false;
        error = false;
        job = null;
        listener = null;
        notifications = new String[4];
        notifications[2] = "is offline.";
    }

    protected String getType(){
        return machineType;
    }

    public String getDesc(){
        return machineDesc;
    }

    public void setCode(int code){
        machineCode = code;
    }

    protected int getCode(){
        return machineCode;
    }

    public Job getJob(){
        return job;
    }

    protected void setJobToNull(){
        job = null;
    }


    public String getMachineString(){
        return machineType + LeadingZeros.convertInteger(machineCode);
    }
    public String[] getNotifications(){
        return notifications;
    }

    protected boolean getOnlineStatus(){
        return online;
    }

    public void setErrorStatus(boolean isError){
        
        if (this.error != isError){
            if(isError){
                notifications[3] = "** ERROR **\n";
            } 
            
            if (!isError){
               notifications[3] = "No errors.\n";
            }

            try {
                notifyListener(notifications);            
            } catch (ListenerException e){
                System.out.println(e);
            }
        }

        this.error = isError;
    
    }



    public void setOnlineStatus(boolean nowOnline){
        
        if (this.online != nowOnline){
            if(nowOnline){
                notifications[2] = "is online. ";
            } 
            
            if (!nowOnline){
               notifications[2] = "is offline. ";
            }

            try {
                notifyListener(notifications);            
            } catch (ListenerException e){
                System.out.println(e);
            }
        }

        this.online = nowOnline;
    
    }

    protected boolean acceptJob(Job job){

        if(this.job == null && online == true && error == false && job.getJobType().equals(machineType)){
            this.job = job;
            return true;
        } else {
            System.out.println("job not accepted");
            return false;
        }  
    }

    protected void reset(){
        error = false;
    }

    public void setNotifications(){
        notifications[0] = getDesc() + " ";
        notifications[1] = getMachineString() + " ";
        if(!error){
            notifications[3] = "No errors.\n";
        } else {
            notifications[3] = " ** ERROR **.\n";
        }

    }

    public abstract void processJob() throws TemperatureException;

    public String toString() {
        return "desc of office machine";
    }

    public void registerListener(Listener listener){
        this.listener = listener;
    }

    public void notifyListener(String[] notifications) throws ListenerException {
        if (listener != null) {
            listener.receiveNotification(notifications);
        } else {
            throw new ListenerException(getCode());
        }

    }

}

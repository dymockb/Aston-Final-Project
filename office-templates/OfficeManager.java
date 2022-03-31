package model;

import java.util.ArrayList;

import util.Parser;
/**
 * The office manager class handles assignment of jobs to machines in the office.
 * 
 * @author  Dymock Brett
 * @version v1.0
 */

public class OfficeManager
{

    private Parser parser;
    private ArrayList<OfficeMachine> availableMachines;
    private ArrayList<Job> jobQueue;
    private String[] managerCommands;
    private ArrayList<Integer> jobCodes;
    //private boolean testingOn;

    
    /**
     * 
     * @param 
     
    public OfficeManager(ArrayList<OfficeMachine> availableMachines)
    {
        this.availableMachines = availableMachines;
        parser = new Parser();
        jobQueue = new ArrayList<Job>();
        managerCommands = new String[] {"stop", "add-jobs", "process-jobs", "queue", "help"};
        jobCodes = new ArrayList<Integer>();
        //testingOn = false;
    }
    
    */

    public OfficeManager(ArrayList<OfficeMachine> availableMachines, Parser parser)
    {
        this.availableMachines = availableMachines;
        this.parser = parser;
        jobQueue = new ArrayList<Job>();
        managerCommands = new String[] {"stop", "add-jobs", "process-jobs", "queue", "help"};
        jobCodes = new ArrayList<Integer>();
        //testingOn = true;

    }

    /**
     * @return 
     */
    public void start(){
        System.out.println("Office manager started.  Enter help for assistance.");
        boolean officeManagerRunning = true;
        while(officeManagerRunning){
            Command command = parser.getManagerCommand(managerCommands);
            officeManagerRunning = processCommand(command);
        }
        System.out.println("The Office Manager is now offline.  Type 'help' for assistance.");
    }

    private boolean processCommand(Command command){

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return true;
        }

        boolean officeManagerRunning = true;
        if (command.getCommandWord().equals("stop")){
            officeManagerRunning = false;
        } else if (command.getCommandWord().equals("add-jobs")){
            boolean addingJobs = true;
            String stringOfMachineTypes = "";
            for (OfficeMachine om : availableMachines){
                stringOfMachineTypes += om.getType() + ", ";
            }
            String commandsToUse = stringOfMachineTypes.substring(0,stringOfMachineTypes.length()-2);
            while(addingJobs){
                System.out.println("- Enter one of the available machine/job types.");
                System.out.println("- or enter 'done' to finish adding jobs.");
                System.out.println("Machines available: " + commandsToUse);
                Command jobCommand = parser.getJobCommand();
                addingJobs = processJobCommand(jobCommand);  
            }
            System.out.println("Finished adding jobs.  Enter a command:");
            System.out.println("( - help: view the help menu.)");  

        } else if (command.getCommandWord().equals("queue")){

            System.out.println("There are " + jobQueue.size() + " jobs in the queue.");
            for(Job j : jobQueue){
                System.out.println(" - " + j.getJobString() + ", owner (employee number): " + j.getJobOwner());
            }

        } else if (command.getCommandWord().equals("process-jobs")){

            System.out.println("** Processing jobs...");

            for(OfficeMachine om : availableMachines){
                om.setOnlineStatus(true);
            }

            processJobs(jobQueue);

            for(OfficeMachine om : availableMachines){
                om.setOnlineStatus(false);
            }

            System.out.println("** Finished processing jobs.");
            System.out.println("Type a command, or 'help':");            
        
        } else if (command.getCommandWord().equals("help")){
            System.out.println("Available commands:");
            System.out.println(" - add-jobs: Add a job to the queue");
            System.out.println(" - queue: View jobs currently in queue (not processed)");
            System.out.println(" - process-jobs: Process all jobs in the queue.");
            System.out.println(" - help: view this help menu");
            System.out.println(" - stop: stop the office manager");            
        }

        return officeManagerRunning;
    }

    public void processJobs(ArrayList<Job> jobQueue){
        
        if(jobQueue.size()>0){

            for(int j = 0; j < jobQueue.size(); j++){

                OfficeMachine om = findNextAvailableMachine(jobQueue.get(j));

                try {
                    om.processJob();
                } catch (TemperatureException e){
                    System.out.println(e);
                    //throw new RuntimeException(e);
                }

                if(om.getType().equals("SCN")){
                    OfficeMachine printer = findNextAvailableMachine(om.getJob());
                    if(printer != null){
                        try {
                            printer.processJob();
                        } catch (TemperatureException e){
                            System.out.println(e);
                        }

                        om.setJobToNull();
                        System.out.println(om.getMachineString() + ": scanner reset.");
                    } else {
                        System.out.println("Can't find a printer to print Scan job.");
                    }

                }

            }

            //flush jobQueue
            for(int j = jobQueue.size()-1; j >=0 ; j--){
                jobQueue.remove(j);
            }
            System.out.println("Job queue cleared.");

        } else {

            System.out.println("** No jobs in queue.");
        
        }

    }

    public OfficeMachine findNextAvailableMachine(Job job){
        for(OfficeMachine om : availableMachines){
            if(job.getJobType().equals(om.getType())){
                if(assignJob(job, om)){
                    return om;
                } else {
                    continue;
                }
            }
        }
        return null;
    }


    private boolean assignJob(Job job, OfficeMachine machine){
        return machine.acceptJob(job);
    }

    private boolean processJobCommand(Command command){

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return true;
        }

        String jobType = command.getCommandWord();
        
        if(jobType.equals("done")){
            return false;
        } else {
            int machinesAvailable = 0;
            for(OfficeMachine om : availableMachines){
                if(om.getType().equals(jobType)){
                    machinesAvailable++;
                }
            }
            if (machinesAvailable > 0){
                Job job = null;

                job = Job.createJob(jobType, parser);

                int jobCode = createJobCode(jobCodes);
                job.setJobCode(jobCode);
                
                addToQueue(job);
                return true;

            } else {
                System.out.println("The office does not contain any machines able to process that job.");
                return true;
            }
        }
    }

    public void addToQueue(Job job){
        jobQueue.add(job);
        System.out.println("* A new " + job.getJobType() + " job has been added to the queue. *");
    }

    public ArrayList<Job> getQueue(){
        return jobQueue;
    }

    public int createJobCode(ArrayList<Integer> jobCodes){
        int newCode = jobCodes.size() + 1;
        jobCodes.add(newCode);
        return newCode;
    }

    public void showStatus(String[] msgs){
        for(String m : msgs){
            System.out.print(m);
        }
    }

    public ArrayList<Integer> getJobCodes(){
        return jobCodes;
    }
}


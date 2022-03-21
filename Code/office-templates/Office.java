package controller;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import model.Command;
import util.Parser;
import util.MachineTypes;

import model.OfficeMachine;
import model.OfficeManager;
import model.Listener;

import model.Printer;
import model.Copier;
import model.FoodProcessor;
import model.Scanner;
import model.CoffeeMachine;
import model.VendingMachine;



/**
 *  
 *  This main class creates and initialises all the others: it creates 
 *  the office, creates the parser, populates the office with machines
 *  and starts the Office Manager.  DOWNCAST BRANCH 
 * 
 *  It also evaluates and executes the commands that the parser returns.
 * 
 * @author Dymock Brett
 * @version 1.0
 */

public class Office 
{
    private OfficeManager officeManager;
    private ArrayList<OfficeMachine> machineInventory;
    private Parser parser;
    private MachineTypes machineTypes;
    private ArrayList<Integer> machineCodes;
    //private boolean testingOn;
    private String testFile;
    private Listener listener;    
    /**
     * The main method: this is what happens first when the program is run.
     * It creates an Office object, then - with user prompts - adds machines to the Office and
     * optionally runs the office manager.
     */
    public static void main(String[] args) throws FileNotFoundException {
    	
        /** UnComment the appropriate line below */

        /** Run this line to run the office via System input.*/
       
            //Office office = new Office();
    	
        /** Run this line to run the office via the OfficeTest.txt file.*/

            Office office = new Office("OfficeTest.txt");

        office.openForBusiness();
    
    }
        
    /**
     * Create the office and initialise machine states.
     */
    public Office() 
    {
        parser = new Parser();
        machineTypes = new MachineTypes();
        machineInventory = new ArrayList<OfficeMachine>();
        machineCodes = new ArrayList<Integer>();
    }

    public Office(String testFile) throws FileNotFoundException
    {
        this.testFile = testFile;
        parser = new Parser(this.testFile);
        machineTypes = new MachineTypes();
        machineInventory = new ArrayList<OfficeMachine>();
        machineCodes = new ArrayList<Integer>();
    }

    /**
     * Add machines to the office
     */
    public void openForBusiness()
    {
        printWelcome();
        boolean closed = false;
        while (!closed) {
            Command command = parser.getInventoryCommand();
            closed = processCommand(command);
        }
        System.out.println("The office is now closed.");
    }

    /**
     * Print out the opening message.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to your new office.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, or if the Player 
     * 				dies, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean closeOffice = false;

        if(command.isUnknown()){
            System.out.println("Unknown command");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("quit")) {
            closeOffice = quit(command);
        }
        else if (commandWord.equals("add")) {
            if(command.getSecondWord() == null){
                System.out.println("Add what?");
                return closeOffice;
            }
            if(machineTypes.isValidCode(command.getSecondWord())){
                String[] mCodes = machineTypes.getMachineTypes();
                OfficeMachine newMachine;
                for (String m : mCodes){
                    if (command.getSecondWord().equals(m)){
                        newMachine = createMachine(m);
                        if(newMachine != null){
                            newMachine.setCode(createCode());
                            newMachine.setNotifications();
                            addToInventory(newMachine);
                            System.out.println("A " + newMachine.getDesc() + " has been installed in the office.");
                            printInventory();
                        }
                    }
                }
            } else {
                System.out.println("That's not a valid type of machine.");
            }

        }
        else if (commandWord.equals("status")) {
        	System.out.println("The office currently has " + machineInventory.size() + " machine(s) installed.");
            printInventory();
        }
        else if (commandWord.equals("start-manager")){

            if(machineInventory.size()>0){

                officeManager = new OfficeManager(machineInventory, parser);
                listener = new Listener(officeManager);
                
                for(int i = 0; i < machineInventory.size(); i ++){
                    //skip machines to test ListenerException
                    //if(i > 0){
                        machineInventory.get(i).registerListener(listener);                
                    //}
                }

                officeManager.start();

            } else {
                System.out.println("Please add some machines to the office before starting the manager.");
            }
            
        }

        return closeOffice;
    }

    public void addToInventory(OfficeMachine om){
        machineInventory.add(om);
    }

    public ArrayList<OfficeMachine> getInventory(){
        return machineInventory;
    }

    public OfficeMachine createMachine(String machineType){
        if (machineType.equals("PRT")){
            return new Printer();
        } else if (machineType.equals("CPY")){
            return new Copier();
        } else if (machineType.equals("SCN")){
            return new Scanner();
        } else if (machineType.equals("CFE")){
            return new CoffeeMachine();
        } else if (machineType.equals("VND")){
            return new VendingMachine();
        } else if (machineType.equals("FPR")){
            return new FoodProcessor();
        } else {
            System.out.println("Valid machine but no template available.");
            return null; 
        }
        
    }

    public int createCode(){
        int newCode = machineCodes.size() + 1;
        machineCodes.add(newCode);
        return newCode;
    }

    private void printInventory(){
        if(machineInventory.size() > 0) {
            System.out.println("Current inventory: ");
            for(OfficeMachine om : machineInventory){
                System.out.println(" - " + om.getMachineString()  + ": " + om.getDesc());
            }
            //System.out.println("");
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Available commands:");
        System.out.println(" - add MACHINE: e.g add PRT)");
        System.out.println("                Machine types:");
        System.out.println("                PRT - printer");
        System.out.println("                CPY - copier");
        System.out.println("                SCN - scanner");
        System.out.println("                CFE - coffee machine");
        System.out.println("                VND - vending machine");
        System.out.println("                FPR - food processor");
        System.out.println("- status: view current machines installed in the office.");
        System.out.println("- start-manager: start the office manager");
        System.out.println("- quit: exit and close the office.");
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}

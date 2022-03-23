package superclass;
import util.Router;

public class Screen {

    private Menu menu;
    private Router router;
    private String screenName;
    
    public Screen(String screenName){

        this.screenName = screenName;
        menu = new Menu();
    
    }

    public void registerRouter(Router router){

        this.router = router;

    }


    public void displayContent(){

    }

    public void displayMenu(){

        menu.displayPrompt();
        menu.displayOptions();

    }

    public void getUserInput(){

        Boolean gettingInput = true;
        String userInput = null;
        while(gettingInput){

            userInput = menu.getUserInput();
            if(userInput.equals("b")){
                System.out.println("browsing screen tbc");
                gettingInput = false;
            } else if (userInput.equals("l")){
                System.out.println("login screen tbc");
                gettingInput = false;
            } else if (userInput.equals("h")){
                router.newScreenRequest(userInput, screenName);
                gettingInput = false;
            } else {
                System.out.println("invalid command");                
            }

        }

    }

    
}

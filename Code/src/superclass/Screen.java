package superclass;
import util.User;
import java.util.HashMap;
import util.Parser;

public abstract class Screen {

    protected HashMap<String, String> options;
    protected User user;
    protected String screenName;
    protected Parser parser;
    
    public Screen(String screenName, Parser parser){

        this.screenName = screenName;
        this.parser = parser;
        
        options = new HashMap<String, String>();
        addOptions();
    
    }

    public void registerUser(User user){

        this.user = user;

    }

    public void setCurrentScreen(){
        user.setCurrentScreen(screenName);
    }

    public String getScreenName(){
        return screenName;
    }

    public abstract void getUserInput();

    public abstract void displayPrompt();

    public abstract void addOptions();

    public abstract void displayOptions();

    public abstract void displayMenu();


    
}

package model;
import java.util.HashMap;
import superclass.Screen;
import superclass.SearchDB;

public class History {

    private HashMap<Integer, SearchDB> searchHistory;
    private HashMap<Integer, Screen> screenHistory;
    private Object[] historyInstance; 

    public History(){
        searchHistory = new HashMap<Integer, SearchDB>();
        screenHistory = new HashMap<Integer, Screen>();
    }

    public void addToHistory(int index, SearchDB search, Screen screen){

        searchHistory.put(index, search);
        screenHistory.put(index, screen);

    }

    public Object[] retreiveFromHistory(int index){
        historyInstance = new Object[2];
        historyInstance[0] = searchHistory.get(index);
        historyInstance[1] = screenHistory.get(index);
        return historyInstance;
    }

    public int getSizeOfScreenHistoryHM(){
        return screenHistory.size();
    }
    
}

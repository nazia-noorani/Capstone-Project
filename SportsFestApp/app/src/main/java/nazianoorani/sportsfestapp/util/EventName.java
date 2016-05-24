package nazianoorani.sportsfestapp.util;

/**
 * Created by nazianoorani on 24/04/16.
 */
public class EventName {
    public static String getEventName(int eventNo){
        String eventName ="";
        switch (eventNo){
            case 1: eventName ="Badminton";
                break;
            case 2: eventName ="TableTennis";
                break;
            case 3: eventName ="LawnTennis";
                break;
            case 4: eventName ="Athletics";
                break;
            case 5: eventName ="Chess";
                break;
            case 6: eventName ="Cricket";
                break;
            case 7: eventName ="Football";
                break;
            case 8: eventName ="Basketball";
                break;
            case 9: eventName ="Volleyball";
                break;
        }
        return eventName;
    }
}

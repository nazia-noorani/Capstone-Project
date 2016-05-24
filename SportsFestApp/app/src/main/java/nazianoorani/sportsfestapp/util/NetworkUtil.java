package nazianoorani.sportsfestapp.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by nazianoorani on 21/05/16.
 */
public class NetworkUtil {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo() != null){
            return true;
        }
        else{
            return false;
        }
    }
}
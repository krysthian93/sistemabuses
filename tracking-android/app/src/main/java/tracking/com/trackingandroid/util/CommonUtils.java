package tracking.com.trackingandroid.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class CommonUtils {
    public static final String COMPLETE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String TOURS_MOVIE_DETAIL = "TOURS_MOVIE_DETAIL";

    public static void showSimpleToastMessages(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static Boolean isNetworkAvailable(Context context){
        try{
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }catch (Exception e){
            Log.d("NETWORK", "isNetworkAvailable Error: ", e);
            return false;
        }
    }

    public static String convertPasswordBCrypt(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}

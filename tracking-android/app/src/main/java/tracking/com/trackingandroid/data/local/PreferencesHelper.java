package tracking.com.trackingandroid.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    public static final String PREF_FILE_NAME = "tracking_pref_file";
    public static final String TOKEN = "TOKEN";

    private final SharedPreferences preferences;

    public PreferencesHelper(Context context) {
        preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setString(String key, String value) {
        preferences.edit().putString(key,value).commit();
    }

    public String getString(String key) {
        return preferences.getString(key,"");
    }
}

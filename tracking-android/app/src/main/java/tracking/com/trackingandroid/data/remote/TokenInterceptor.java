package tracking.com.trackingandroid.data.remote;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import tracking.com.trackingandroid.data.local.PreferencesHelper;

public class TokenInterceptor implements Interceptor {

    private PreferencesHelper preferencesHelper;

    public TokenInterceptor(PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = preferencesHelper.getString(PreferencesHelper.TOKEN);
        Request.Builder builder = chain.request().newBuilder();
        builder.header("Authorization", token);
        return chain.proceed(builder.build());
    }
}

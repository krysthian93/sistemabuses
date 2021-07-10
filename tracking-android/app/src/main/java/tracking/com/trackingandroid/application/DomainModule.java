package tracking.com.trackingandroid.application;

import android.content.Context;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tracking.com.trackingandroid.BuildConfig;
import tracking.com.trackingandroid.data.DataManager;
import tracking.com.trackingandroid.data.local.PreferencesHelper;
import tracking.com.trackingandroid.data.remote.TokenInterceptor;
import tracking.com.trackingandroid.data.remote.TrackingService;

/**
 * Contains all common object that will be provided to whole application.
 * */
@Module
public class DomainModule {

    /**
     * Provide Context Application.
     * @param application the new application, it replace to {@link tracking.com.trackingandroid.main.MainActivity}
     * @return application context
     * */
    @Provides
    Context provideContext(TrackingApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    PreferencesHelper providesPreferencesHelper(Context context){
        return new PreferencesHelper(context);
    }

    @Provides
    DataManager proviDataManager(TrackingService trackingService, PreferencesHelper preferencesHelper) {
        return new DataManager(trackingService, preferencesHelper);
    }

    @Provides
    TokenInterceptor providesTokenInterceptor(PreferencesHelper preferencesHelper) {
        return new TokenInterceptor(preferencesHelper);
    }

    @Provides
    TrackingService provideWhatMovieService(TokenInterceptor tokenInterceptor) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        //set logging interceptor, to catch request and responses and show them on Android Logcat.
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        httpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor);
        httpClientBuilder.addInterceptor(tokenInterceptor);

        OkHttpClient customOkHttpClient = httpClientBuilder.build();

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();

        return new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .client(customOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TrackingService.class);


    }

}

package tracking.com.trackingandroid.main.di;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import tracking.com.trackingandroid.application.TrackingApplication;

@Singleton
@Component(modules = {AndroidInjectionModule.class, BuildersModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(TrackingApplication application);
        AppComponent build();
    }
    void inject(TrackingApplication albumsApplication);
}

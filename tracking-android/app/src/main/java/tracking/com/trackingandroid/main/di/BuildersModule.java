package tracking.com.trackingandroid.main.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tracking.com.trackingandroid.application.DomainModule;
import tracking.com.trackingandroid.apps.login.di.LoginModule;
import tracking.com.trackingandroid.apps.login.di.LoginViewModule;
import tracking.com.trackingandroid.apps.login.ui.LoginFragment;
import tracking.com.trackingandroid.apps.record_tour.di.RecordToursModule;
import tracking.com.trackingandroid.apps.record_tour.di.RecordToursViewModule;
import tracking.com.trackingandroid.apps.record_tour.ui.RecordToursFragment;
import tracking.com.trackingandroid.apps.register.di.RegisterModule;
import tracking.com.trackingandroid.apps.register.di.RegisterViewModule;
import tracking.com.trackingandroid.apps.register.ui.RegisterFragment;
import tracking.com.trackingandroid.apps.tours.di.ToursModule;
import tracking.com.trackingandroid.apps.tours.di.ToursViewModule;
import tracking.com.trackingandroid.apps.tours.ui.ToursFragment;
import tracking.com.trackingandroid.main.DrawerActivity;
import tracking.com.trackingandroid.main.MainActivity;

/**
 * Binds all sub-components within the app.
 * Specifying the dependencies that will be provided to Activities and Fragments.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {DomainModule.class, LoginModule.class, LoginViewModule.class})
    abstract LoginFragment bindLoginFragment();

    @ContributesAndroidInjector
    abstract DrawerActivity bindDrawerActivity();

    @ContributesAndroidInjector(modules = {DomainModule.class, RecordToursModule.class, RecordToursViewModule.class})
    abstract RecordToursFragment bindRecordToursFragment();

    @ContributesAndroidInjector(modules = {DomainModule.class, ToursModule.class, ToursViewModule.class})
    abstract ToursFragment bindToursFragment();

    @ContributesAndroidInjector(modules = {DomainModule.class, RegisterModule.class, RegisterViewModule.class})
    abstract RegisterFragment bindRegisterFragment();

}

package tracking.com.trackingandroid.apps.register.di;

import dagger.Module;
import dagger.Provides;
import tracking.com.trackingandroid.apps.register.RegisterPresenter;
import tracking.com.trackingandroid.apps.register.RegisterPresenterImpl;
import tracking.com.trackingandroid.apps.register.ui.RegisterView;
import tracking.com.trackingandroid.data.DataManager;

@Module
public class RegisterModule {

    @Provides
    RegisterPresenter provideRegisterPresenter(DataManager dataManager, RegisterView registerView) {
        return new RegisterPresenterImpl(dataManager, registerView);
    }
}

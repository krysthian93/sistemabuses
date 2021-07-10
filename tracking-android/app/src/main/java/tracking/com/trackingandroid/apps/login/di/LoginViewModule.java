package tracking.com.trackingandroid.apps.login.di;

import dagger.Binds;
import dagger.Module;
import tracking.com.trackingandroid.apps.login.ui.LoginFragment;
import tracking.com.trackingandroid.apps.login.ui.LoginView;

@Module
public abstract class LoginViewModule {

    @Binds
    public abstract LoginView bindLogin(LoginFragment loginFragment);
}

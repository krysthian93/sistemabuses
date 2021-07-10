package tracking.com.trackingandroid.apps.register.di;

import dagger.Binds;
import dagger.Module;
import tracking.com.trackingandroid.apps.register.ui.RegisterFragment;
import tracking.com.trackingandroid.apps.register.ui.RegisterView;

@Module
public interface RegisterViewModule {

    @Binds
    public abstract RegisterView bindRegister(RegisterFragment registerFragment);
}

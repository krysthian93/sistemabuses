package tracking.com.trackingandroid.apps.tours.di;

import dagger.Binds;
import dagger.Module;
import tracking.com.trackingandroid.apps.tours.ui.ToursFragment;
import tracking.com.trackingandroid.apps.tours.ui.ToursView;

@Module
public abstract class ToursViewModule {

    @Binds
    public abstract ToursView bindTours(ToursFragment toursFragment);
}

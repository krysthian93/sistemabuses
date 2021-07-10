package tracking.com.trackingandroid.apps.tours.di;

import dagger.Module;
import dagger.Provides;
import tracking.com.trackingandroid.apps.tours.ToursPresenter;
import tracking.com.trackingandroid.apps.tours.ToursPresenterImpl;
import tracking.com.trackingandroid.apps.tours.ui.ToursView;
import tracking.com.trackingandroid.data.DataManager;

@Module
public class ToursModule {
    @Provides
    ToursPresenter provideTourPresenter(DataManager dataManager, ToursView toursView) {
        return new ToursPresenterImpl(dataManager, toursView);
    }
}

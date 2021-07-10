package tracking.com.trackingandroid.apps.record_tour.di;

import dagger.Module;
import dagger.Provides;
import tracking.com.trackingandroid.apps.record_tour.RecordToursPresenter;
import tracking.com.trackingandroid.apps.record_tour.RecordToursPresenterImpl;
import tracking.com.trackingandroid.apps.record_tour.ui.RecordToursView;
import tracking.com.trackingandroid.data.DataManager;

@Module
public class RecordToursModule {

    @Provides
    RecordToursPresenter provideRecordTourPresenter(DataManager dataManager,
                                                    RecordToursView recordToursView) {
        return new RecordToursPresenterImpl(recordToursView, dataManager);
    }
}

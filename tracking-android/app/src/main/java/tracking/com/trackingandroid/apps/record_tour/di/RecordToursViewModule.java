package tracking.com.trackingandroid.apps.record_tour.di;

import dagger.Binds;
import dagger.Module;
import tracking.com.trackingandroid.apps.record_tour.ui.RecordToursFragment;
import tracking.com.trackingandroid.apps.record_tour.ui.RecordToursView;

@Module
public abstract class RecordToursViewModule {

    @Binds
    public abstract RecordToursView bindRecordTours(RecordToursFragment recordToursFragment);
}

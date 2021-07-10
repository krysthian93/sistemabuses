package tracking.com.trackingandroid.apps.record_tour;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tracking.com.trackingandroid.apps.record_tour.ui.RecordToursView;
import tracking.com.trackingandroid.data.DataManager;
import tracking.com.trackingandroid.data.model.Tour;

public class RecordToursPresenterImpl implements RecordToursPresenter {
    private static final String TAG = RecordToursPresenterImpl.class.getSimpleName();

    private RecordToursView recordToursView;
    private DataManager dataManager;

    public RecordToursPresenterImpl(RecordToursView recordToursView, DataManager dataManager) {
        this.recordToursView = recordToursView;
        this.dataManager = dataManager;
    }

    @Override
    public void createTour(Tour tour) {
        recordToursView.showProgress();
        dataManager.createTour(tour).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Tour>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Tour tour) {
                        Log.i(TAG,"Saved Tour: " + tour.toString());
                        recordToursView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

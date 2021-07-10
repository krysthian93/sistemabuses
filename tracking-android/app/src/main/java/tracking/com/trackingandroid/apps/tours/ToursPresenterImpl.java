package tracking.com.trackingandroid.apps.tours;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tracking.com.trackingandroid.apps.tours.ui.ToursView;
import tracking.com.trackingandroid.data.DataManager;
import tracking.com.trackingandroid.data.model.Tour;

public class ToursPresenterImpl implements ToursPresenter {
    private static final String TAG = ToursPresenterImpl.class.getSimpleName();
    private Disposable mSubscription = null;

    private DataManager dataManager;
    private ToursView toursView;

    public ToursPresenterImpl(DataManager dataManager, ToursView toursView) {
        this.dataManager = dataManager;
        this.toursView = toursView;
    }

    @Override
    public void getTours() {
        dataManager.getTours()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Tour>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mSubscription = d;
                    }

                    @Override
                    public void onNext(List<Tour> tourList) {
                        toursView.showTours(tourList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDestroy() {
        mSubscription.dispose();
    }
}

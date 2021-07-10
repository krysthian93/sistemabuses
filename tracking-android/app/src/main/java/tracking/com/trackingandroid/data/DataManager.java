package tracking.com.trackingandroid.data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import tracking.com.trackingandroid.data.local.PreferencesHelper;
import tracking.com.trackingandroid.data.model.LoginUser;
import tracking.com.trackingandroid.data.model.Tour;
import tracking.com.trackingandroid.data.model.User;
import tracking.com.trackingandroid.data.remote.TrackingService;

/**
 * It keeps a reference to every helper class and uses
 * them to satisfy the requests coming from the presenters. Its methods make extensive use
 * of Rx operators to combine, transform or filter the output coming from the helpers in order
 * to generate the desired output ready for the Presenters. It returns observables that emit
 * data models.
 * */
public class DataManager {

    private TrackingService trackingService;
    private PreferencesHelper preferencesHelper;

    public DataManager(TrackingService trackingService, PreferencesHelper preferencesHelper) {
        this.trackingService = trackingService;
        this.preferencesHelper = preferencesHelper;
    }

    public Observable<Response<Void>> login(String username, String password) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        return trackingService.login(loginUser);
    }

    public Observable<Tour> createTour(Tour tour) {
        return trackingService.createTour(tour);
    }

    public Observable<List<Tour>> getTours() {
        return trackingService.getTours();
    }

    public Observable<User> registerUser(User user) {
        return trackingService.registerUser(user);
    }

    public void putString(String key, String value) {
        preferencesHelper.setString(key, value);
    }

    public String getString(String key) {
        return preferencesHelper.getString(key);
    }
}

package tracking.com.trackingandroid.data.remote;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tracking.com.trackingandroid.data.model.LoginUser;
import tracking.com.trackingandroid.data.model.Tour;
import tracking.com.trackingandroid.data.model.User;

public interface TrackingService {

    @POST("/login")
    Observable<Response<Void>> login(@Body LoginUser loginUser);

    @POST("/tour/register")
    Observable<Tour> createTour(@Body Tour tour);

    @GET("/tour/list")
    Observable<List<Tour>> getTours();

    @POST("/authenticate/register")
    Observable<User> registerUser(@Body User user);
}

package tracking.com.trackingandroid.apps.login;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import tracking.com.trackingandroid.apps.login.ui.LoginView;
import tracking.com.trackingandroid.data.DataManager;
import tracking.com.trackingandroid.data.local.PreferencesHelper;

public class LoginPresenterImpl implements LoginPresenter {
    private static final String TAG = LoginPresenterImpl.class.getSimpleName();

    private LoginView loginView;
    private DataManager dataManager;

    public LoginPresenterImpl(DataManager dataManager, LoginView loginView) {
        this.loginView = loginView;
        this.dataManager = dataManager;
    }

    @Override
    public void login(String username, String password) {
        loginView.showProgress();
        dataManager.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Void>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Void> voidResponse) {
                        Log.d(TAG, "RESPONSE: " + voidResponse.headers().get("Authorization"));
                        loginView.hideProgress();
                        if (voidResponse.headers().get("Authorization") != null) {
                            dataManager.putString(PreferencesHelper.TOKEN,
                                    voidResponse.headers().get("Authorization"));
                            loginView.showMainMenu();
                        } else {
                            if (voidResponse.code() == 401) {
                                loginView.showMessages("Credenciales Incorrectas");
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError Tracking: " + e.toString());
                        loginView.hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onDestroy() {

    }
}

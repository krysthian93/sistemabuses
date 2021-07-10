package tracking.com.trackingandroid.apps.register;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tracking.com.trackingandroid.apps.register.ui.RegisterView;
import tracking.com.trackingandroid.data.DataManager;
import tracking.com.trackingandroid.data.model.User;

public class RegisterPresenterImpl implements RegisterPresenter {
    private static final String TAG = RegisterPresenterImpl.class.getSimpleName();

    private DataManager dataManager;
    private RegisterView registerView;

    public RegisterPresenterImpl(DataManager dataManager, RegisterView registerView) {
        this.dataManager = dataManager;
        this.registerView = registerView;
    }

    @Override
    public void register(User user) {
        registerView.showProgress();
        dataManager.registerUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User user) {
                        registerView.hideProgress();
                        registerView.showMessages("Usuario creado correctamente");
                        registerView.clearFormAndNavigate();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "OnError RegisterUser: " + e.toString());
                        registerView.hideProgress();
                        registerView.showMessages("Ocurrió un error al crear el usuario");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

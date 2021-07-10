package tracking.com.trackingandroid.apps.login;

public interface LoginPresenter {
    void login(String username, String password);

    void onDestroy();
}

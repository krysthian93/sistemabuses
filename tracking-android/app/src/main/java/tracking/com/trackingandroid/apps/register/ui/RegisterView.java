package tracking.com.trackingandroid.apps.register.ui;

public interface RegisterView {
    void clearFormAndNavigate();
    void showMessages(String message);
    void showProgress();
    void hideProgress();
}

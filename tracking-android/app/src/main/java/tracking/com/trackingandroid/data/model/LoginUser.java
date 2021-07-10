package tracking.com.trackingandroid.data.model;

import com.google.gson.annotations.Expose;

public class LoginUser {
    @Expose()
    public String username;
    @Expose()
    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

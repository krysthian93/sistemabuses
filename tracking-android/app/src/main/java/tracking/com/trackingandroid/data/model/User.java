package tracking.com.trackingandroid.data.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class User implements Serializable {
    @Expose
    private String username;
    @Expose
    private String fullname;
    @Expose
    private String password;
    @Expose
    private Integer rol;

    public User(String username, String fullname, String password, Integer rol) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }
}

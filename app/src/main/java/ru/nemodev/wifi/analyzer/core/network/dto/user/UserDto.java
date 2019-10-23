package ru.nemodev.wifi.analyzer.core.network.dto.user;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.nemodev.wifi.analyzer.core.network.dto.BaseEntityDto;

public class UserDto extends BaseEntityDto {

    @SerializedName("login")
    @Expose
    private String login;


    @SerializedName("password")
    @Expose
    private String password;

    public UserDto() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

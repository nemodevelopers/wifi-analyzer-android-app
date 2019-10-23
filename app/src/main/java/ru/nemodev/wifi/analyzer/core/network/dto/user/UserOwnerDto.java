package ru.nemodev.wifi.analyzer.core.network.dto.user;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.nemodev.wifi.analyzer.core.network.dto.BaseEntityDto;

public class UserOwnerDto extends BaseEntityDto {

    @SerializedName("login")
    @Expose
    private String login;

    public UserOwnerDto() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

package ru.nemodev.wifi.analyzer.core.network.dto.user;


import ru.nemodev.wifi.analyzer.core.network.dto.BaseEntityDto;

public class UserOwnerDto extends BaseEntityDto {

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

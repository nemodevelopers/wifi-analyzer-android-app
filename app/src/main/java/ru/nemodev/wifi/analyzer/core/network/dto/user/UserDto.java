package ru.nemodev.wifi.analyzer.core.network.dto.user;


import ru.nemodev.wifi.analyzer.core.network.dto.BaseEntityDto;

public class UserDto extends BaseEntityDto {

    private String login;
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

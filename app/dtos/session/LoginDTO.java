package dtos.session;

import models.entities.User;

public class LoginDTO {

    private boolean success;
    private User user;

    public LoginDTO() {
    }

    public LoginDTO(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

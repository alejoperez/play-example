package business.users;

import models.entities.User;

import java.util.List;

public interface IUsersLogic {

    List<User> getUsers(int page, int pageSize);
}

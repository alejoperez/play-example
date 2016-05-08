package controllers;

import business.users.IUsersLogic;
import dtos.users.UserDTO;
import models.entities.User;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UsersController extends Controller {


    private final IUsersLogic usersLogic;

    @Inject
    public UsersController(IUsersLogic usersLogic) {
        this.usersLogic = usersLogic;
    }


    @Transactional(readOnly = true)
    public Result getUsers(int page, int pageSize) {

        if (SessionController.isLoggedUser()) {
            List<User> userList = usersLogic.getUsers(page,pageSize);
            return ok(Json.toJson(userList));
        }

        return unauthorized();
    }

}

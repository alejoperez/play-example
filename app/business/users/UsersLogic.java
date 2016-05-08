package business.users;

import models.entities.User;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.Query;
import java.util.List;

@Singleton
public class UsersLogic implements IUsersLogic {

    private final JPAApi jpaApi;

    @Inject
    public UsersLogic(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public List<User> getUsers(int page, int pageSize) {

        Query selectQuery = jpaApi.em().createNamedQuery(User.FIND_ALL);
        selectQuery.setFirstResult(page  * pageSize);
        selectQuery.setMaxResults(pageSize);
        List<User> userList = selectQuery.getResultList();

        return userList;
    }
}

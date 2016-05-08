package business.session;

import dtos.session.LoginDTO;
import dtos.session.LogoutDTO;
import dtos.session.RegisterDTO;
import models.UserTypeEnum;
import models.entities.User;
import play.db.jpa.JPAApi;
import requests.session.LoginRequest;
import requests.session.RegisterRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.Query;
import java.util.List;

@Singleton
public class SessionLogic implements ISessionLogic {

    private final JPAApi jpaApi;

    @Inject
    public SessionLogic(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        Query query = jpaApi.em().createNamedQuery(User.FIND_USER_BY_EMAIL_AND_PASSWORD);
        query.setParameter(User.EMAIL, loginRequest.getEmail());
        query.setParameter(User.PASSWORD, loginRequest.getPassword());

        List<User> userList = query.getResultList();

        LoginDTO loginDTO = new LoginDTO();

        if (!userList.isEmpty()) {
            loginDTO.setSuccess(true);
            loginDTO.setUser(userList.get(0));
        }

        return loginDTO;

    }

    @Override
    public RegisterDTO register(RegisterRequest registerRequest) {

        Query query = jpaApi.em().createNamedQuery(User.FIND_USER_BY_EMAIL);
        query.setParameter(User.EMAIL, registerRequest.getEmail());

        List<User> userList = query.getResultList();

        RegisterDTO registerDTO = new RegisterDTO();

        if (userList.isEmpty() && UserTypeEnum.existUserType(registerRequest.getUserType())) {

            User user = new User(
                    registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getName(),
                    registerRequest.getUserType(),
                    true
            );

            jpaApi.em().persist(user);

            registerDTO.setSuccess(true);
            registerDTO.setUser(user);
        }

        return registerDTO;
    }

    @Override
    public LogoutDTO logout(Long userId) {
        Query query = jpaApi.em().createNamedQuery(User.UPDATE_LOGGED_STATUS);
        query.setParameter(User.ID,userId);
        query.setParameter(User.LOGGED,false);

        boolean updated = query.executeUpdate() > 0;

        return new LogoutDTO(updated);
    }
}

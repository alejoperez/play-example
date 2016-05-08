package business.session;

import dtos.session.LoginDTO;
import dtos.session.LogoutDTO;
import dtos.session.RegisterDTO;
import requests.session.LoginRequest;
import requests.session.RegisterRequest;

public interface ISessionLogic {

    LoginDTO login(LoginRequest loginRequest);
    RegisterDTO register(RegisterRequest registerRequest);
    LogoutDTO logout(Long userId);

}

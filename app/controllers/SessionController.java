package controllers;

import business.session.ISessionLogic;
import dtos.session.LoginDTO;
import dtos.session.LogoutDTO;
import dtos.session.RegisterDTO;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import requests.session.LoginRequest;
import requests.session.RegisterRequest;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionController extends Controller {

    public static final String USER_ID = "user_id";
    public static final long NOT_LOGGED = -1L;

    private final ISessionLogic sessionLogic;

    @Inject
    public SessionController(ISessionLogic sessionLogic) {
        this.sessionLogic = sessionLogic;
    }

    @Transactional
    @BodyParser.Of(LoginRequest.LoginBodyParser.class)
    public Result login() {
        Http.RequestBody body = request().body();
        LoginRequest loginRequest = body.as(LoginRequest.class);

        if (loginRequest.isValid()) {

            LoginDTO loginDTO = sessionLogic.login(loginRequest);

            if (loginDTO.isSuccess()) {
                saveDataSession(loginDTO.getUser().getId());
                return ok(Json.toJson(loginDTO));
            }

            return notFound();
        }

        return badRequest();
    }

    @Transactional
    @BodyParser.Of(RegisterRequest.RegisterBodyParser.class)
    public Result register() {
        Http.RequestBody body = request().body();
        RegisterRequest registerRequest = body.as(RegisterRequest.class);

        if (registerRequest.isValid()) {
            RegisterDTO registerDTO = sessionLogic.register(registerRequest);

            if (registerDTO.isSuccess()) {
                saveDataSession(registerDTO.getUser().getId());
                return ok(Json.toJson(registerDTO));
            }
        }

        return badRequest();
    }

    @Transactional
    public Result logout() {
        LogoutDTO logoutDTO = sessionLogic.logout(getUserId());
        session().clear();
        return ok(Json.toJson(logoutDTO));
    }


    private void saveDataSession(Long id) {
        session().clear();
        session().put(USER_ID,id.toString());
    }

    public static boolean isLoggedUser() {
        return session().get(USER_ID) != null;
    }

    public static Long getUserId() {
        Long id = NOT_LOGGED;
        String userId = session().get(USER_ID);

        if (userId != null) {
            id = Long.parseLong(userId);
        }

        return id;
    }

}

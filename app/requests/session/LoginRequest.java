package requests.session;

import akka.util.ByteString;
import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.common.util.StringHelper;
import play.libs.F;
import play.libs.streams.Accumulator;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.concurrent.Executor;

public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isValid() {
        return !StringHelper.isEmpty(email) && !StringHelper.isEmpty(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public static class LoginBodyParser implements BodyParser<LoginRequest> {
        @Inject
        BodyParser.Json jsonParser;
        @Inject
        Executor executor;

        public Accumulator<ByteString, F.Either<Result, LoginRequest>> apply(Http.RequestHeader request) {
            Accumulator<ByteString, F.Either<Result, JsonNode>> jsonAccumulator = jsonParser.apply(request);
            return jsonAccumulator.map(resultOrJson -> {
                if (resultOrJson.left.isPresent()) {
                    return F.Either.Left(resultOrJson.left.get());
                } else {
                    JsonNode json = resultOrJson.right.get();
                    try {
                        LoginRequest loginRequest = play.libs.Json.fromJson(json, LoginRequest.class);
                        return F.Either.Right(loginRequest);
                    } catch (Exception e) {
                        return F.Either.Left(Results.badRequest("Unable to read request from json: " + e.getMessage()));
                    }
                }
            }, executor);
        }
    }
}

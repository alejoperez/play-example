package requests.session;


import akka.util.ByteString;
import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.internal.util.StringHelper;
import play.libs.F;
import play.libs.streams.Accumulator;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.concurrent.Executor;

public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String userType;

    public RegisterRequest() {
    }

    public RegisterRequest(String name, String email, String password, String userType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isValid() {
        return !StringHelper.isEmpty(name)
                && !StringHelper.isEmpty(email)
                && !StringHelper.isEmpty(password)
                && !StringHelper.isEmpty(userType);
    }

    public static class RegisterBodyParser implements BodyParser<RegisterRequest> {
        @Inject
        BodyParser.Json jsonParser;
        @Inject
        Executor executor;

        public Accumulator<ByteString, F.Either<Result, RegisterRequest>> apply(Http.RequestHeader request) {
            Accumulator<ByteString, F.Either<Result, JsonNode>> jsonAccumulator = jsonParser.apply(request);
            return jsonAccumulator.map(resultOrJson -> {
                if (resultOrJson.left.isPresent()) {
                    return F.Either.Left(resultOrJson.left.get());
                } else {
                    JsonNode json = resultOrJson.right.get();
                    try {
                        RegisterRequest registerRequest = play.libs.Json.fromJson(json, RegisterRequest.class);
                        return F.Either.Right(registerRequest);
                    } catch (Exception e) {
                        return F.Either.Left(Results.badRequest("Unable to read object from json: " + e.getMessage()));
                    }
                }
            }, executor);
        }
    }

}

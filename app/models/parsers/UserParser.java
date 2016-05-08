package models.parsers;

import akka.util.ByteString;
import com.fasterxml.jackson.databind.JsonNode;
import models.entities.User;
import play.libs.F;
import play.libs.streams.Accumulator;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.concurrent.Executor;

public class UserParser {

    public static class UserBodyParser implements BodyParser<User> {
        @Inject BodyParser.Json jsonParser;
        @Inject Executor executor;

        public Accumulator<ByteString, F.Either<Result, User>> apply(Http.RequestHeader request) {
            Accumulator<ByteString, F.Either<Result, JsonNode>> jsonAccumulator = jsonParser.apply(request);
            return jsonAccumulator.map(resultOrJson -> {
                if (resultOrJson.left.isPresent()) {
                    return F.Either.Left(resultOrJson.left.get());
                } else {
                    JsonNode json = resultOrJson.right.get();
                    try {
                        User user = play.libs.Json.fromJson(json, User.class);
                        return F.Either.Right(user);
                    } catch (Exception e) {
                        return F.Either.Left(Results.badRequest("Unable to read User from json: " + e.getMessage()));
                    }
                }
            }, executor);
        }
    }
}

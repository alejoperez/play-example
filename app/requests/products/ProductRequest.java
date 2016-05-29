package requests.products;

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

public class ProductRequest {

    private String name;
    private String description;
    private Double price;
    private String base64Image;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, Double price, String base64Image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.base64Image = base64Image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public boolean isValid() {
        return !StringHelper.isEmpty(name)
                && !StringHelper.isEmpty(description)
                && price != 0D
                && !StringHelper.isEmpty(base64Image);
    }

    public static class ProductBodyParser implements BodyParser<ProductRequest> {
        @Inject
        BodyParser.Json jsonParser;
        @Inject
        Executor executor;

        public Accumulator<ByteString, F.Either<Result, ProductRequest>> apply(Http.RequestHeader request) {
            Accumulator<ByteString, F.Either<Result, JsonNode>> jsonAccumulator = jsonParser.apply(request);
            return jsonAccumulator.map(resultOrJson -> {
                if (resultOrJson.left.isPresent()) {
                    return F.Either.Left(resultOrJson.left.get());
                } else {
                    JsonNode json = resultOrJson.right.get();
                    try {
                        ProductRequest productRequest = play.libs.Json.fromJson(json, ProductRequest.class);
                        return F.Either.Right(productRequest);
                    } catch (Exception e) {
                        return F.Either.Left(Results.badRequest("Unable to read object from json: " + e.getMessage()));
                    }
                }
            }, executor);
        }
    }
}

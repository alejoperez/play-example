package controllers;

import business.products.IProductsLogic;
import dtos.products.DeleteProductDTO;
import dtos.products.UpdateProductDTO;
import models.entities.Product;
import models.enums.UserTypeEnum;
import play.api.GlobalSettings$;
import play.api.Play;
import play.api.PlayConfig;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import requests.products.ProductRequest;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ProductsController extends Controller {


    private final IProductsLogic productsLogic;

    @Inject
    public ProductsController(IProductsLogic productsLogic) {
        this.productsLogic = productsLogic;
    }

    @Transactional(readOnly = true)
    public Result getProducts() {

        if (SessionController.isLoggedUser()) {
            List<Product> productList = productsLogic.getProducts();
            return ok(Json.toJson(productList));
        }

        return unauthorized();
    }


    @Transactional
    @BodyParser.Of(ProductRequest.ProductBodyParser.class)
    public Result createProduct() {

        Http.RequestBody body = request().body();
        ProductRequest productRequest = body.as(ProductRequest.class);

        if (SessionController.isLoggedUser()
        && SessionController.isLoggedUserType(UserTypeEnum.ADMIN)) {

            if (productRequest.isValid()) {

                Product product = productsLogic.createProduct(play.Play.application().configuration(),productRequest);
                return ok(Json.toJson(product));
            }
            return badRequest();
        }

        return unauthorized();
    }

    @Transactional
    @BodyParser.Of(ProductRequest.ProductBodyParser.class)
    public Result updateProduct(Long id) {

        Http.RequestBody body = request().body();
        ProductRequest productRequest = body.as(ProductRequest.class);

        if (SessionController.isLoggedUser()
                && SessionController.isLoggedUserType(UserTypeEnum.ADMIN)) {

            if (productRequest.isValid()) {
                UpdateProductDTO updateProductDTO = productsLogic.updateProduct(id,productRequest);
                if (updateProductDTO.isSuccess()) {
                    return ok(Json.toJson(updateProductDTO));
                }
                return notFound();
            }
            return badRequest();
        }

        return unauthorized();
    }

    @Transactional
    public Result deleteProduct(Long id) {

        if (SessionController.isLoggedUser()
                && SessionController.isLoggedUserType(UserTypeEnum.ADMIN)) {

                DeleteProductDTO deleteProductDTO = productsLogic.deleteProduct(id);
                return ok(Json.toJson(deleteProductDTO));
        }

        return unauthorized();
    }

}

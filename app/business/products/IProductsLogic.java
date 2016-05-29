package business.products;

import dtos.products.DeleteProductDTO;
import dtos.products.UpdateProductDTO;
import models.entities.Product;
import play.Configuration;
import requests.products.ProductRequest;

import java.util.List;

public interface IProductsLogic {

    List<Product> getProducts();
    Product createProduct(Configuration configuration,ProductRequest productRequest);
    UpdateProductDTO updateProduct(Long id, ProductRequest productRequest);
    DeleteProductDTO deleteProduct(Long id);
}

package business.products;

import com.amazonaws.services.s3.model.ObjectMetadata;
import dtos.products.DeleteProductDTO;
import dtos.products.UpdateProductDTO;
import models.entities.Product;
import play.Configuration;
import play.db.jpa.JPAApi;
import requests.products.ProductRequest;
import s3.S3Manager;
import util.ImageUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.Query;
import java.io.*;
import java.util.List;

@Singleton
public class ProductsLogic implements IProductsLogic {

    private final JPAApi jpaApi;

    @Inject
    public ProductsLogic(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public List<Product> getProducts() {
        Query query = jpaApi.em().createNamedQuery(Product.FIND_ALL);
        List<Product> productList = query.getResultList();
        return productList;
    }

    @Override
    public Product createProduct(Configuration configuration,ProductRequest productRequest) {
        Product product = new Product(
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getPrice()
        );

        byte[] imageByteArray = ImageUtil.getByteArrayFromBase64(productRequest.getBase64Image());
        ObjectMetadata metadata = ImageUtil.getMetadata(imageByteArray);
        InputStream inputStream = ImageUtil.getInputStreamFromByteArray(imageByteArray);

        S3Manager.getInstance(configuration).saveFile(product.getFileName(),inputStream,metadata);

        product.setImageUrl(S3Manager.getS3ImageUrl(product.getFileName()));

        jpaApi.em().persist(product);

        return product;
    }

    @Override
    public UpdateProductDTO updateProduct(Long id,ProductRequest productRequest) {
        Product product = jpaApi.em().find(Product.class,id);

        UpdateProductDTO updateProductDTO = new UpdateProductDTO();

        if (product != null) {

            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());

            jpaApi.em().merge(product);

            updateProductDTO.setProduct(product);
            updateProductDTO.setSuccess(true);
        }

        return updateProductDTO;
    }

    @Override
    public DeleteProductDTO deleteProduct(Long id) {
        Query query = jpaApi.em().createNamedQuery(Product.DELETE_BY_ID);
        query.setParameter(Product.ID,id);

        boolean deleted = query.executeUpdate() > 0;

        return new DeleteProductDTO(deleted);
    }
}

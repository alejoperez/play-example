package dtos.products;


import models.entities.Product;

public class UpdateProductDTO {

    private boolean success;
    private Product product;

    public UpdateProductDTO() {
    }

    public UpdateProductDTO(boolean success, Product product) {
        this.success = success;
        this.product = product;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

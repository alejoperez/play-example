package dtos.products;

public class DeleteProductDTO {

    private boolean success;

    public DeleteProductDTO() {
    }

    public DeleteProductDTO(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

package dtos.session;

public class LogoutDTO {

    private boolean success;

    public LogoutDTO(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

package dtos.users;

public class UserDTO {

    private String email;
    private String password;
    private String name;
    private String userType;

    public UserDTO() {
    }

    public UserDTO(String email, String password, String name, String userType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userType = userType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

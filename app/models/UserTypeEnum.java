package models;

public enum UserTypeEnum {
    CLIENT("client"),
    ADMIN("admin"),
    PROVIDER("provider");

    private final String name;

    private UserTypeEnum(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }

    public static boolean existUserType(String userType) {
        if (CLIENT.equalsName(userType)
                || ADMIN.equalsName(userType)
                || PROVIDER.equalsName(userType)
        ) {
            return true;
        }
        return false;
    }
}

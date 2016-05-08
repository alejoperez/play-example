package models.entities;

import javax.persistence.*;


@NamedQueries({

        @NamedQuery(
                name = User.COUNT_ALL,
                query = "SELECT COUNT(u.id) FROM User u"
        ),
        @NamedQuery(
                name = User.FIND_ALL,
                query = "FROM User"
        ),
        @NamedQuery(
                name = User.FIND_USER_BY_EMAIL,
                query = "FROM User u WHERE u.email=:"+User.EMAIL
        ),
        @NamedQuery(
                name = User.FIND_USER_BY_EMAIL_AND_PASSWORD,
                query = "FROM User u WHERE u.email=:"+User.EMAIL+" AND u.password=:"+User.PASSWORD
        ),
        @NamedQuery(
                name = User.UPDATE_LOGGED_STATUS,
                query = "UPDATE User u SET u.logged=:"+User.LOGGED+" WHERE u.id =:"+User.ID
        )
})
@Entity
public class User {

    public static final String FIND_ALL = "User.findAll";
    public static final String COUNT_ALL = "User.countAll";
    public static final String FIND_USER_BY_EMAIL = "User.findUserByEmail";
    public static final String FIND_USER_BY_EMAIL_AND_PASSWORD = "User.findUserByEmailAndPassword";
    public static final String UPDATE_LOGGED_STATUS = "User.updateLoggedStatus";


    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String LOGGED = "logged";
    public static final String USER_TYPE = "user_type";

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = EMAIL)
    private String email;

    @Column(name = PASSWORD)
    private String password;

    @Column(name = NAME)
    private String name;

    @Column(name = USER_TYPE)
    private String userType;

    @Column(name = LOGGED)
    private boolean logged;


    public User() {
    }

    public User(String email, String password, String name, String userType, boolean logged) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userType = userType;
        this.logged = logged;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

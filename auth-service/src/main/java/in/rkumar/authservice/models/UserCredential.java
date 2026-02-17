package in.rkumar.authservice.models;

import jakarta.persistence.*;

@Entity
public class UserCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(unique = true)
    private String userId; 

    @Column(nullable = false)
    private String name;   

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public UserCredential() {}

    public UserCredential(long id, String userId, String name, String email, String password) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
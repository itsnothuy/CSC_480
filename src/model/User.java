package model;

import java.time.LocalDateTime;

public class User {
    private String id, username, email, hashedPassword;
    private boolean supplier, verified;
    private LocalDateTime createdAt;

    public User(String id,String username,String email,String hashedPassword,
                boolean supplier,boolean verified,LocalDateTime createdAt){
        this.id=id;this.username=username;this.email=email;
        this.hashedPassword=hashedPassword;
        this.supplier=supplier;this.verified=verified;this.createdAt=createdAt;
    }
    // getters & setters
    public String getId(){return id;}
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public String getHashedPassword(){return hashedPassword;}
    public boolean isSupplier(){return supplier;}
    public boolean isVerified(){return verified;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setVerified(boolean v){this.verified=v;}
    @Override public String toString(){return username+
        (supplier? verified?" [✓verified]":" [supplier]":"");}
}

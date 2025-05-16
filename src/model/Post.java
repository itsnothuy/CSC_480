package model;
import java.time.LocalDateTime;
public class Post {
    private String id,title,description,status;
    private Service service;
    private User requester;
    private LocalDateTime createdAt;
    public Post(String id,String title,String description,String status,
                Service service,User requester,LocalDateTime createdAt){
        this.id=id;this.title=title;this.description=description;
        this.status=status;this.service=service;this.requester=requester;
        this.createdAt=createdAt;
    }
    public String getId(){return id;}
    public String getTitle(){return title;}
    public String getStatus(){return status;}
    public String getDescription(){return description;}
    public Service getService(){return service;}
    public User getRequester(){return requester;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setStatus(String s){status=s;}
    @Override public String toString(){
        return "["+status+"] "+title+" ("+service.getName()+")";
    }
}

package model;
import java.time.LocalDateTime;
import java.math.BigDecimal;
public class Bid {
    private String id;
    private Post post;
    private User supplier;
    private BigDecimal price;
    private String message,status;
    private LocalDateTime createdAt;
    public Bid(String id,Post post,User supplier,BigDecimal price,
               String message,String status,LocalDateTime createdAt){
        this.id=id;this.post=post;this.supplier=supplier;
        this.price=price;this.message=message;this.status=status;
        this.createdAt=createdAt;
    }
    public String getId(){return id;}
    public Post getPost(){return post;}
    public User getSupplier(){return supplier;}
    public BigDecimal getPrice(){return price;}
    public String getStatus(){return status;}
    public void setStatus(String s){status=s;}
    @Override public String toString(){
        return "$"+price+" "+supplier.getUsername()+" => "+post.getTitle()
               +" ("+status+")";
    }
}

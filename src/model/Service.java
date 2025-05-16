package model;
public class Service {
    private String id,name,description;
    public Service(String id,String name,String description){
        this.id=id;this.name=name;this.description=description;
    }
    public String getId(){return id;}
    public String getName(){return name;}
    public String getDescription(){return description;}
    @Override public String toString(){return name;}
}

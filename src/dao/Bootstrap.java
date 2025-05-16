package dao;
public class Bootstrap {
    public static void initSchema() throws Exception {
        new UserDAO().createTable();
        new ServiceDAO().createTable();
        new PostDAO().createTable();
        new BidDAO().createTable();
    }
}

import dao.*;
import model.*;
import util.ID;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        /* 1. build schema */
        Bootstrap.initSchema();

        /* 2. DAOs */
        UserDAO udao=new UserDAO();
        ServiceDAO sdao=new ServiceDAO();
        PostDAO pdao=new PostDAO();
        BidDAO  bdao=new BidDAO();

        /* 3. seed demo rows */
        User alice=new User(ID.newId(),"alice","alice@mail","x",
                            false,false,LocalDateTime.now());
        udao.add(alice);

        User bob=new User(ID.newId(),"bob","bob@shop","x",
                          true,true,LocalDateTime.now());
        udao.add(bob);

        Service plumbing=new Service(ID.newId(),"plumbing","Fix leaks");
        sdao.add(plumbing);

        Post sink=new Post(ID.newId(),"Leaky sink",
                           "Kitchen faucet dripping","open",
                           plumbing,alice,LocalDateTime.now());
        pdao.add(sink);

        Bid offer=new Bid(ID.newId(),sink,bob,
                          new BigDecimal("75.00"),
                          "Can be there tomorrow",
                          "pending",LocalDateTime.now());
        bdao.add(offer);

        /* 4. read back */
        System.out.println("📋 Open posts:");
        for(Post p : pdao.open()) System.out.println("  • "+p);

        System.out.println("\n📋 Bids on that post:");
        for(Bid b : bdao.forPost(sink)) System.out.println("  • "+b);
    }
}

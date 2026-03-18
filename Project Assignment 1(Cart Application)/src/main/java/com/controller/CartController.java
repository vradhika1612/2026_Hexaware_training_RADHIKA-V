package com.controller;

import com.config.ProjConfig;
import com.enums.membership;
import com.model.CartItem;
import com.service.CartService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.model.User;
import java.math.BigDecimal;
import java.util.*;

public class CartController {
    public static void main(String[] args)
    {
        var cxt=new AnnotationConfigApplicationContext(ProjConfig.class);
        CartService service=cxt.getBean(CartService.class);
        Scanner sc=new Scanner(System.in);


        while(true){
            System.out.println("1. Insert");
            System.out.println("2. Delete by id");
            System.out.println("3. Fetch all Items");
            System.out.println("4. Fetch by Username");
            System.out.println("0. Exit");

            int input = sc.nextInt();

            if(input == 0)
                break;

            switch(input){

                case 1:
                    User user = new User();
                    user.setId(1);
                    user.setName("Radhika");
                    user.setMembership(membership.GOLD);

                    CartItem item = new CartItem();
                    item.setId(1);
                    item.setName("Laptop");
                    item.setPrice(new BigDecimal(50000));
                    item.setQty(1);
                    item.setUser(user);

                    service.insert(item);
                    System.out.println("Item inserted in DB");
                    break;

                case 2:
                    System.out.println("Enter ID to delete");
                    int id = sc.nextInt();

                    service.deleteItem(id);
                    System.out.println("Item deleted");
                    break;

                case 3:
                    List<CartItem> list = service.fetchAllItems();
                    list.forEach(System.out :: println);
                    break;

                case 4:
                    System.out.println("Enter username:");
                    sc.nextLine();
                    String name = sc.nextLine();

                    List<CartItem> userItems = service.fetchByUsername(name);
                    userItems.forEach(System.out :: println);
                    break;
            }
        }
        sc.close();

    }

}

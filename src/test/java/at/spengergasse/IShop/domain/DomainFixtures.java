package at.spengergasse.IShop.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DomainFixtures {

    public static Customer defaultCustomer(Long id){
        Customer c1 = Customer.builder()
                .first_name("Franz")
                .last_name("Huber")
                .city("Vienna")
                .country("Austria")
                .email("dhgjodf@gmail.com")
                .credit(13.23)
                .gebdate(Date.valueOf(LocalDate.now()))
                .phone("+43 660 3948")
                .newsletter(Boolean.TRUE)
                .zipcode(1050l)
                .street("Spengergasse")
                .house_nr("11")
                .bank_details("AT9798 4937 9348")
                .build();
        c1.setId(id);
        return c1;
    }
    public static Customer defaultCustomer(){
        Customer c1 = Customer.builder()
                .first_name("Franz")
                .last_name("Huber")
                .city("Vienna")
                .country("Austria")
                .email("dhgjodf@gmail.com")
                .credit(13.23)
                .gebdate(Date.valueOf(LocalDate.now()))
                .phone("+43 660 3948")
                .newsletter(Boolean.TRUE)
                .zipcode(1050l)
                .street("Spengergasse")
                .house_nr("11")
                .bank_details("AT9798 4937 9348")
                .build();
        return c1;
    }
    public static Manufacturer defaultManufacturer(Integer id){
        Manufacturer m = Manufacturer.builder()
                .manufacturer_id(id)
                .email("adf@gmail.com")
                .headquarter("Vienna")
                .manufacturer_id(id)
                .name("Microsoft")
                .phone("+43-660-3674")
                .rating(3)
                .payment_note("kdslagbs")
                .build();
        return m;
    }
    public static Manufacturer defaultManufacturer(){
        Manufacturer m = Manufacturer.builder()
                .manufacturer_id(93487)
                .email("adf@gmail.com")
                .headquarter("Vienna")
                .manufacturer_id(327648)
                .name("Microsoft")
                .phone("+43-660-3674")
                .rating(3)
                .payment_note("kdslagbs")
                .build();
        return m;
    }

    public static Product defaultProduct(Manufacturer manufacturer){
        Product p = Product.builder()
                .category(Category.business)
                .ean(02357)
                .name("Iphone X")
                .shipping_cost(5.99)
                .price(998.99)
                .weight(0.2)
                .manufacturer(manufacturer)
                .build();
        return p;
    }

    public static Order defaultOrder(Customer customer, List<Order_item> order_items){
        Order o = Order.builder()
                .deliveryDate(Date.valueOf("2019-12-12"))
                .purchaseDate(Date.valueOf("2019-12-10"))
                .customer(customer)
                .order_items(order_items)
                .build();
        return o;
    }
    public static Order_item defaultOrder_item(Product product, Order order){
        Order_item oi = Order_item.builder()
                .product(product)
                .order(order)
                .build();
        return oi;
    }
    public static Shopping_cart defaultShopping_cart(Customer customer, List<Shopping_cart_item> shopping_cart_items){
        Shopping_cart sc = Shopping_cart.builder()
                .customer(customer)
                .shopping_cart_items(shopping_cart_items)
                .build();
        return sc;
    }
    public static Shopping_cart_item defaultShopping_cart_item(Shopping_cart shopping_cart, Product product){
        Shopping_cart_item sc = Shopping_cart_item.builder()
                .product(product)
                .shoppingCart(shopping_cart)
                .build();
        return sc;
    }


}

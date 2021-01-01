package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.*;
import at.spengergasse.IShop.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class InitializeService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ManufacturerRepository manufacturerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    Order_itemRepository order_itemRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    Shopping_cartRepository shoppingCartRepository;

    @PostConstruct
    public void initialize(){

        Customer c1 = Customer.builder()
                .first_name("Dennis")
                .last_name("SIMA")
                .city("Vienna")
                .country("Austria")
                .email("SIM18354@spengergasse.at")
                .credit(13.23)
                .gebdate(Date.valueOf("2001-11-26"))
                .phone("+43 660 3948")
                .newsletter(Boolean.FALSE)
                .zipcode(1110l)
                .street("Spengergasse")
                .house_nr("11")
                .bank_details("AT9798 4233 9348")
                .build();

        Customer c2 = Customer.builder()
                .first_name("Max")
                .last_name("DONNINGER")
                .city("Wiener Neustadt")
                .country("Austria")
                .email("Donninger@gmail.com")
                .credit(122.20)
                .gebdate(Date.valueOf(LocalDate.now()))
                .phone("+43 660 3948")
                .newsletter(Boolean.TRUE)
                .zipcode(2736l)
                .street("Spengergasse")
                .house_nr("11")
                .bank_details("AT9798 4937 9348")
                .build();

        Customer c3 = Customer.builder()
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

        customerRepository.saveAll(List.of(c1,c2,c3));
        Order o1 = Order.builder()
                .deliveryDate(Date.valueOf(LocalDate.of(2020,6,20)))
                .purchaseDate(Date.valueOf(LocalDate.of(2020,6,5)))
                .customer(customerRepository.findById(1l).get())
                .build();
        Order o2 = Order.builder()
                .deliveryDate(Date.valueOf(LocalDate.of(2020,6,25)))
                .purchaseDate(Date.valueOf(LocalDate.of(2020,6,3)))
                .customer(customerRepository.findById(1l).get())
                .build();
        Manufacturer m1 = Manufacturer.builder()
                .manufacturer_id(4321)
                .name("Donninger AG")
                .headquarter("1110 Wien")
                .phone("0676 3221981")
                .email("hr@donningerag.at")
                .payment_note("AT12 5433 8922")
                .rating(5)
                .build();
        Product p1 = Product.builder()
                .category(Category.clothes)
                .ean(123414)
                .manufacturer(m1)
                .name("Pullover")
                .price(19.99)
                .picture("https://www.sparkz-copenhagen.com/pub/media/catalog/product/cache/image/700x700/e9c3970ab036de70892d86c6d221abfe/a/u/autumn_pullover_blue_melange_2.jpg")
                .shipping_cost(2.99)
                .weight(0.3)
                .height(1.0)
                .length(1.2)
                .build();
        Product p2 = Product.builder()
                .category(Category.TV)
                .ean(231231)
                .manufacturer(m1)
                .name("Curved UHD")
                .price(899.99)
                .picture("https://bilder.macwelt.de/4237290_620x310_r.jpg")
                .shipping_cost(9.99)
                .weight(8.0)
                .height(0.9)
                .length(2.0)
                .build();
        Product p3 = Product.builder()
                .category(Category.outdoor)
                .ean(1231214)
                .manufacturer(m1)
                .name("Aufklappzelt")
                .picture("https://www.campingwagner.de/images/product_images/zoom_images/49171_0.jpg")
                .price(329.99)
                .shipping_cost(4.99)
                .weight(4.0)
                .height(2.5)
                .length(2.0)
                .build();

        Order_item oi1 = Order_item.builder().order(o1).product(p1).build();
        Order_item oi2 = Order_item.builder().order(o2).product(p2).build();
        Order_item oi3 = Order_item.builder().order(o1).product(p3).build();

        manufacturerRepository.saveAll(List.of(

                Manufacturer.builder()
                        .manufacturer_id(1234)
                        .name("Sima AG")
                        .headquarter("1100 Wien")
                        .phone("0676 1254781")
                        .email("hr@simaag.at")
                        .payment_note("AT12 2223 1232")
                        .rating(5)
                        .build(),
                m1

        ));

        productRepository.saveAll(List.of(p1,p2,p3));
        orderRepository.saveAll(List.of(o1,o2));
        order_itemRepository.saveAll(List.of(oi1,oi2,oi3));
        shoppingCartRepository.saveAll(List.of(
                Shopping_cart.builder().customer(c1).build(),
                Shopping_cart.builder().customer(c2).build()
        ));

    }
}

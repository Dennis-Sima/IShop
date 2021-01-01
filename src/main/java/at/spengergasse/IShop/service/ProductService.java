package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.*;
import at.spengergasse.IShop.persistence.OrderRepository;
import at.spengergasse.IShop.persistence.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = false)
    public Product createProduct(@Valid @NotBlank Integer ean, String name, Double price, Double shipping_cost, String picture, Double weight, Double length, Double height, Category category, Manufacturer manufacturer)
    {
        Product product = Product.builder()
                                    .ean(ean)
                                    .name(name)
                                    .price(price)
                                    .shipping_cost(shipping_cost)
                                    .picture(picture)
                                    .weight(weight)
                                    .length(length)
                                    .height(height)
                                    .category(category)
                                    .manufacturer(manufacturer)
                                    .build();

        return productRepository.save(product);
    }

    @Transactional(readOnly = false)
    public Product createProduct(Product product)
    {
        return productRepository.save(product);
    }

    @Transactional(readOnly = false)
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteAllProducts(List<Product> products) { productRepository.deleteAll(products); }

    @Transactional(readOnly = false)
    public void updateProduct(Integer id, final Product updatedProduct) {
        productRepository.findById(id)
                          .map(product -> {
                              product.setEan(updatedProduct.getEan());
                              product.setName(updatedProduct.getName());
                              product.setPrice(updatedProduct.getPrice());
                              product.setShipping_cost(updatedProduct.getShipping_cost());
                              product.setPicture(updatedProduct.getPicture());
                              product.setWeight(updatedProduct.getWeight());
                              product.setLength(updatedProduct.getLength());
                              product.setHeight(updatedProduct.getHeight());
                              product.setCategory(updatedProduct.getCategory());
                              product.setManufacturer(updatedProduct.getManufacturer());
                              return product;
                          })
                          .map(productRepository::save);
    }

    public List<Product> getProducts()
    {
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(Integer id)
    {
        return productRepository.findById(id);
    }

    public List<Product> getAllProductsByManufacturer(Manufacturer manufacturer) { return productRepository.findAllByManufacturer(manufacturer);   }


}

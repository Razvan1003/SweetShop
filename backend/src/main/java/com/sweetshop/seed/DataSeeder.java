package com.sweetshop.seed;

import com.sweetshop.entity.Address;
import com.sweetshop.entity.Category;
import com.sweetshop.entity.Product;
import com.sweetshop.entity.Role;
import com.sweetshop.entity.User;
import com.sweetshop.repository.AddressRepository;
import com.sweetshop.repository.CategoryRepository;
import com.sweetshop.repository.ProductRepository;
import com.sweetshop.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(CategoryRepository categoryRepository, ProductRepository productRepository,
                      UserRepository userRepository, AddressRepository addressRepository,
                      PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@sweetshop.com")) {
            User admin = new User("admin@sweetshop.com", passwordEncoder.encode("Admin1234"), "0700000000", Role.ADMIN);
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail("client@sweetshop.com")) {
            User client = new User("client@sweetshop.com", passwordEncoder.encode("Client123!"), "0700000001", Role.CLIENT);
            userRepository.save(client);
            Address clientAddress = new Address("Client Demo", "Strada Exemplu 10, Bucuresti", client);
            addressRepository.save(clientAddress);
        }

        if (categoryRepository.count() == 0) {
            Category chocolates = new Category("Chocolates");
            Category cakes = new Category("Cakes");
            Category candies = new Category("Candies");
            categoryRepository.saveAll(List.of(chocolates, cakes, candies));

            productRepository.saveAll(List.of(
                new Product("Dark Truffle Box", "Intense dark chocolate with cocoa dust", new BigDecimal("45.00"), chocolates),
                new Product("Hazelnut Praline", "Crunchy praline with roasted hazelnuts", new BigDecimal("32.50"), chocolates),
                new Product("Red Velvet Slice", "Classic red velvet with cream cheese", new BigDecimal("18.00"), cakes),
                new Product("Vanilla Cheesecake", "Silky cheesecake with vanilla bean", new BigDecimal("22.00"), cakes),
                new Product("Fruit Gummies", "Soft gummies with fruit flavors", new BigDecimal("12.00"), candies)
            ));
        }
    }
}

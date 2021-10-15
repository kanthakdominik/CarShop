package pl.dominik.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import pl.dominik.shop.entity.Product;
import pl.dominik.shop.entity.User;
import pl.dominik.shop.service.ProductService;
import pl.dominik.shop.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

@Component
public class StartupData implements CommandLineRunner {
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public StartupData(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws IOException {
        adminAccount();
        userAccount();
        exampleProducts();
    }

    private void userAccount() {
        User user = new User();

        user.setUsername("user");
        user.setPassword("user");
        user.setPasswordConfirm("user");
        user.setGender("Female");
        user.setEmail("user@example.com");

        userService.save(user);
    }

    private void adminAccount() {
        User admin = new User();

        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setPasswordConfirm("admin");
        admin.setGender("Male");
        admin.setEmail("admin@example.com");

        userService.save(admin);
    }

    private void exampleProducts() throws IOException {
        InputStream resource = new ClassPathResource("data/cars.dat").getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            String line;
            String[] data;

            while ((line = reader.readLine()) != null) {
                data = line.split(",");
                Product product = new Product();
                product.setName(data[0]);
                product.setDescription(data[1]);
                product.setPrice(new BigDecimal(data[2]));
                product.setImageUrl(data[3]);
                productService.save(product);
            }
        }
    }
}

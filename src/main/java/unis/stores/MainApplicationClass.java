package unis.stores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class MainApplicationClass extends SpringBootServletInitializer {

    public static void main(String args[]) {
        SpringApplication.run(MainApplicationClass.class, args);
    }
}




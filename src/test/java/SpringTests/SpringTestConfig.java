package SpringTests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.MyDependency;
import spring.MyDependencyImpl;
import spring.MyService;

@ComponentScan(basePackages = {"SpringTests", "spring"})
@Configuration
public class SpringTestConfig {
    @Bean
    public MyService myService() {
        return new MyService(myDependency());
    }

    @Bean
    public MyDependency myDependency() {
        return new MyDependencyImpl();
    }

    @Bean
    public MyDependency myDependency1() {
        return new MyDependencyImpl();
    }
}

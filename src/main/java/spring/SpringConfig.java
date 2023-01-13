package spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"spring"})
public class SpringConfig {
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
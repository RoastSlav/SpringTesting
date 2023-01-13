package SpringTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import spring.MyDependency;
import spring.MyService;
import spring.SpringConfig;

import static org.junit.jupiter.api.Assertions.*;

@Component
public class SpringTests {

    private ApplicationContext applicationContext;

    @BeforeEach
    public void createAppContext() {
        applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    }

    @Autowired
    private MyService myService;

    @Autowired
    @Qualifier("myDependency1")
    private MyDependency myDependency1;

    @Test
    public void testAutowired() {
        assertNotNull(myService);
    }

    @Test
    public void testQualifierNotExist() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(MyDependency.class, "nonExistentBean"));
    }

    @Test
    public void testQualifierOnField() {
        assertNotEquals(myService.getMyDependency(), myDependency1);
    }
}
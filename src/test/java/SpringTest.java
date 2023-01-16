import CircularDependency.Circular;
import TestClasses.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan("TestClasses")
@Configuration
public class SpringTest {
    @Bean
    public A a1() {
        return new A();
    }

    @Bean
    public B b1() {
        return new B();
    }

    @Bean
    public D d1() {
        return new D();
    }

    @Bean
    public F f1() {
        return new F();
    }

    @Bean
    public I i1() {
        return new I();
    }

    @Bean(name = "someString")
    public String string1() {
        return "This is someString";
    }

    ConfigurableApplicationContext context;

    @BeforeEach
    public void setUp() {
        context = SpringApplication.run(SpringTest.class);
    }


    @Test
    public void autoWireTest() {
        B b = context.getBean(B.class);

        assertNotNull(b.a);
    }

    @Test
    public void noBeanDeclaredTest() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> {
            C c = context.getBean(C.class);
        });
    }

    @Test
    public void defaultInterfaceTest() {
        B b = context.getBean(B.class);
        assertEquals(b.a.getClass(), A.class);
    }

    @Test
    public void namedAutowireBean() {
        D d = context.getBean(D.class);
        assertNotNull(d.dbURL);
        assertEquals(string1(), d.dbURL);
    }

    @Test
    public void autowireConstructor() {
        E e = context.getBean(E.class);
        assertNotNull(e.f);
    }

    @Test
    public void qualifierTest() {
        I i = context.getBean(I.class);
        assertNotNull(i.inter);
        assertEquals(i.inter.getClass(), G.class);
    }

    @Test
    public void noQualifierWithMultipleImplementationsTest() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> {
            J j = context.getBean(J.class);
            System.out.println(j.inter);
        });
    }

    @Test
    public void testWorkingCircularDependency() {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext("CircularDependency");
        Circular circular = configApplicationContext.getBean(Circular.class);
        assertNotNull(circular);
        assertNotNull(circular.dependency);
        assertEquals(circular.dependency.circular.dependency.circular.dependency, circular.dependency);
    }

    @Test
    public void testNotWorkingCircularDependency() {
        assertThrows(UnsatisfiedDependencyException.class, () -> {
            ConfigurableApplicationContext cirDepContext = SpringApplication.run(CircularDependencyConfig.class);
            Circular circular = cirDepContext.getBean(Circular.class);
        });
    }

    @Test
    public void lazyLoadingTest() {
        K k = context.getBean(K.class);
        assertFalse(K.loadedL);
        k.l.toString();
        assertTrue(K.loadedL);
    }

    @Test
    public void testNotAutowired() {
        A a = context.getBean(A.class);
        assertNull(a.c);
    }

    @Test
    public void testInterfaceWithOneImplementation() {
        IA ia = context.getBean(IA.class);
        assertEquals(ia.getClass(), A.class);
    }

    @Test
    public void testInterfaceWithMoreThanOneImplementation() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> {
            Inter inter = context.getBean(Inter.class);
        });
    }

    @Test
    public void initializeBeanTest() {
        M m = context.getBean(M.class);
        assertEquals(m.dbURL, string1() + " - url");
    }
}
package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyDependencyImpl implements MyDependency {

    @Override
    public String greet() {
        return "Hello World";
    }
}
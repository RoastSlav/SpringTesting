import org.springframework.stereotype.Component;

@Component
public class MyDependencyImpl implements MyDependency {

    @Override
    public String greet() {
        return "Hello World";
    }
}
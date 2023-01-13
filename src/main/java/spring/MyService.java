import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyService {

    private MyDependency myDependency;

    public MyService(MyDependency myDependency) {
        this.myDependency = myDependency;
    }

    public String greet() {
        return myDependency.greet();
    }

    public MyDependency getMyDependency() {
        return myDependency;
    }
}
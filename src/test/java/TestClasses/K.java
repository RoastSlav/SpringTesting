package TestClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("k")
public class K {
    public static boolean loadedL = false;
    @Autowired
    @Lazy
    public L l;
}
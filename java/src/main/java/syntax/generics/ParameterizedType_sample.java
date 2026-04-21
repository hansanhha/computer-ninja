package syntax.generics;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ParameterizedType_sample {
    
    void main() throws NoSuchFieldException {
        Field field = Box.class.getDeclaredField("items");
        Type type = field.getGenericType();
        
        if (type instanceof ParameterizedType pt) {
            Type raw = pt.getRawType();                 // List
            Type[] args = pt.getActualTypeArguments();  // String

            System.out.println(raw);
            System.out.println(args[0]);
        }
    }
}

class Box {
    List<String> items;
}

package syntax.generics;

import java.util.ArrayList;
import java.util.List;

public class Type_Erasure<T> {
    
    // 컴파일 후 타입 정보가 제거된다
    // 런타임에는 T가 무엇인지 알 수 없기 때문에 클래스 생성 불가능
    // <T> T create() {
    //     return new T();
    // }


    // instanceof T 불가능
    // @Override
    // public boolean equals(Object obj) {
    //     if (obj instanceof T t) {
    //         return true;
    //     }
    //     return false;
    // }

    // 제네릭 배열 생성 불가
    // T[] getArray(int size) {
    //     return new T[size];
    // }

    // 제네릭 메서드는 호출 시점에 타입이 결정되므로 컴파일러가 어떤 타입인지 알 수 있다
    // create(User.class) 호출 시 컴파일러는 T가 User, 반환 타입이 User라는 것을 안다
    // 런타임에는 class가 User.class가 되고 리플렉션으로 User 객체를 생성한다
    <T> T create(Class<T> clazz) throws Exception {
        
        // 해당 클래스에 선언된 생성자 중 파라미터 없는 생성자를 통해 새 인스턴스를 생성한다 (private/protected 포함)
        return clazz.getDeclaredConstructor().newInstance();
    }

    // 배열 대신 컬렉션을 사용한다
    List<T> getList(int size) {
        return new ArrayList<>();
    }
}

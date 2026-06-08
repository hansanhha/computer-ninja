package syntax.generics;

public class GenericTypeTest {
    

    // enum은 부모인 Enum에서 Enum<E extends Enum<E>>를 선언하기 때문에 타입 매개변수를 둘 수 없다
    // enum Color<T> {}

    // 어노테이션은 메타데이터를 표현하기 위한 특별한 인터페이스이므로 타입 매개변수를 둘 수 없게 제한한다
    // @interface Login<T> {}
}

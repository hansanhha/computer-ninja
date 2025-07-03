package sealed;


public sealed interface Expression
        permits Add, Subtract, Multiply, Divide {

    // Expression 인터페이스는 Add, Subtract, Multiply, Divide 클래스만 구현할 수 있다
    // permits 키워드를 사용하여 구현 가능한 클래스들을 제한한다
    double evaluate();

}

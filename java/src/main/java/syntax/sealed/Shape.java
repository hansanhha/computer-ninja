package syntax.sealed;


// Sealed Class 정의, permits 키워드를 사용하여 상속을 제한한다
// Circle, Rectangle, Triangle 클래스만 상속할 수 있다
public sealed class Shape
        permits Circle, Rectangle, Triangle { }

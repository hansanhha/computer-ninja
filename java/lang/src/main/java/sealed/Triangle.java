package sealed;


// Triangle 클래스는 Shape 클래스를 상속하며 sealed 키워드를 사용하여 하위 클래스를 제한한다
public sealed class Triangle
        extends Shape
        permits DoubleTriangle { }

package syntax.sealed;

public record Add(double x, double b)implements Expression {

    @Override
    public double evaluate() {
        return x + b;
    }

}

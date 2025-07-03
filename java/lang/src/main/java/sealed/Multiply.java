package sealed;


public record Multiply(double x, double y) implements Expression {

    @Override
    public double evaluate() {
        return x * y;
    }

}

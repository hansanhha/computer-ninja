package sealed;


public record Divide(double x, double y) implements Expression {

    @Override
    public double evaluate() {
        return x / y;
    }

}

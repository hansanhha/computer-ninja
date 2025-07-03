package sealed;


public record Subtract(double x, double y) implements Expression {

    @Override
    public double evaluate() {
        return x - y;
    }

}

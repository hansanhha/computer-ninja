package record;


// Vehicle.SportsCar
// Vehicle.Truck
// Vehicle.Motorcycle
public record Vehicle(String name, int price) {

    public record SportsCar(String name, int price, String color) { }

    public record Truck(String name, int price, int capacity) { }

    public record Motorcycle(String name, int price, String type) { }

}

import java.util.Comparator;


public class ComparableModel implements Comparator<Car> {
    public int compare(Car a, Car b) {
        return (a.getModel().compareToIgnoreCase(b.getModel()));
    }
}
package ext;

public class Tuple<T, E> {
    public final T first;
    public final E second;

    public Tuple(T first, E second){
        this.first = first;
        this.second = second;
    }
}

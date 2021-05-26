package test.mock;

public class AddServiceImpl implements AddService {
    private int value = 0;

    public AddServiceImpl() {

    }

    @Override
    public int current() {
        return value;
    }

    @Override
    public void add() {
        value = value + 1;
    }
}

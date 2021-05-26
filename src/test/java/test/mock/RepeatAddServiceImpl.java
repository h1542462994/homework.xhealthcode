package test.mock;

public class RepeatAddServiceImpl implements RepeatAddService {


    private final AddService addService;

    public RepeatAddServiceImpl(AddService addService) {
        this.addService = addService;
    }

    @Override
    public void add() {
        addService.add();
    }

    @Override
    public void add(int times) {
        for (int i = 0; i < times; ++i) {
            add();
        }
    }

    @Override
    public int current() {
        return addService.current();
    }
}

package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public abstract class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

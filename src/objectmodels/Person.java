package objectmodels;

/**
 * Created by timothy on 2016-11-28.
 */
public abstract class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    /**
     * Returns name of the person
     *
     * @return name of the person
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: " + getName();
    }
}

package Classes;

import java.io.*;

public class BookAMealInfo implements Serializable {
    private String pnr, name;
    private Integer age;

    public BookAMealInfo(String pnr, String name, Integer age) {
        this.age = age;
        this.pnr = pnr;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getPnr() {
        return pnr;
    }
}

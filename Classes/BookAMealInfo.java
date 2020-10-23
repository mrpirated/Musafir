package Classes;

import java.io.*;

public class BookAMealInfo implements Serializable {
    private String pnr;
    private Integer choice;

    public BookAMealInfo(String pnr, Integer choice) {
        this.choice = choice;
        this.pnr = pnr;
    }

    public Integer getChoice() {
        return choice;
    }

    public String getPnr() {
        return pnr;
    }
}

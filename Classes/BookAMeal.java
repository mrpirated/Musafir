package Classes;

public class BookAMeal {
    private String pnr;
    private Integer choice;

    public BookAMeal(String pnr, Integer choice) {
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

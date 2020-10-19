package Classes;

public class HaveAQuery {
    private Integer choice;
    private String otherQuery;

    public HaveAQuery(Integer choice, String otherQuery) {
        this.choice = choice;
        this.otherQuery = otherQuery;
    }

    public Integer getChoice() {
        return choice;
    }

    public String getOtherQuery() {
        return otherQuery;
    }
}

package Classes;

public class HaveAQueryInfo {
    private Integer choice;
    private String otherQuery;

    public HaveAQueryInfo(Integer choice, String otherQuery) {
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

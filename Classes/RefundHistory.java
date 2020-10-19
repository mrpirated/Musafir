package Classes;

public class RefundHistory {
    private String username;
    private String pnr;

    public RefundHistory(String username, String pnr) {
        this.pnr = pnr;
        this.username = username;
    }

    public String getPnr() {
        return pnr;
    }

    public String getUsername() {
        return username;
    }
}

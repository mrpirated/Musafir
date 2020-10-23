package Classes;

public class RefundHistoryInfo {
    private String username;
    private String pnr;

    public RefundHistoryInfo(String username, String pnr) {
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

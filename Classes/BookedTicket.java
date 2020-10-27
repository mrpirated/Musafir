package Classes;

public class BookedTicket {
    private String PNR;
    private int noofpassengers;
    private int[][] seats;
    
    public BookedTicket(int noofpassengers){
        this.noofpassengers = noofpassengers;
        this.seats = new int[3][noofpassengers];

    }
    public void setPNR(String pNR) {
        PNR = pNR;
    }
    public void setSeats(int[][] seats) {
        this.seats = seats;
    }
    public int getNoofpassengers() {
        return noofpassengers;
    }
    public String getPNR() {
        return PNR;
    }
    public int[][] getSeats() {
        return seats;
    }
}

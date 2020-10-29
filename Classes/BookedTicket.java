package Classes;

import java.io.Serializable;

public class BookedTicket implements Serializable{
    private String PNR;
    private int noofpassengers;
    private int[][] seats;
    
    public BookedTicket(int noofpassengers){
        this.noofpassengers = noofpassengers;
        this.seats = new int[noofpassengers][3];

    }
    public void setPNR(String PNR) {
        this.PNR = PNR;
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

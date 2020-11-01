package Classes;

import java.io.Serializable;

public class BookedTicket implements Serializable{
    private String PNR;
    private int noofpassengers;
    
    private int[][] seats;
    private boolean gotseat = false,tatkal=false;
    
    public BookedTicket(int noofpassengers){
        this.noofpassengers = noofpassengers;
        this.seats = new int[noofpassengers][3];

    }
    public void setTatkal(boolean tatkal) {
        this.tatkal = tatkal;
    }
    public void setGotseat(boolean gotseat) {
        this.gotseat = gotseat;
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
    public boolean getGotseat(){
        return gotseat;
    }
    public boolean getTatkal(){
        return tatkal;
    }
}

package Classes;

import java.io.Serializable;

public class BookedTicket implements Serializable{
    private String PNR;
    private int noofpassengers,type;
    private String name[];
    private int[] seats,coach;
    private boolean gotseat = false,tatkal=false;
    
    public BookedTicket(int noofpassengers){
        this.noofpassengers = noofpassengers;
        this.seats = new int[noofpassengers];
        this.coach = new int[noofpassengers];
        this.name = new String[noofpassengers];
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
    public void setCoach(int[] coach) {
        this.coach = coach;
    }
    public void setSeats(int[] seats) {
        this.seats = seats;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getNoofpassengers() {
        return noofpassengers;
    }
    public String getPNR() {
        return PNR;
    }
    public int[] getCoach() {
        return coach;
    }
    public int[] getSeats() {
        return seats;
    }
    public int getType() {
        return type;
    }

    public boolean getGotseat(){
        return gotseat;
    }
    public boolean getTatkal(){
        return tatkal;
    }
    public void setName(String[] name) {
        this.name = name;
    }
    public String[] getName() {
        return name;
    }
}

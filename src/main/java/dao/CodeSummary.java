package dao;

import java.util.GregorianCalendar;

public class CodeSummary {
    private int green;
    private int yellow;
    private int red;
    private int no;

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getCount(){
        return green + yellow + red + no;
    }

    public void increaseGreen() {
        ++green;
    }

    public void increaseYellow() {
        ++yellow;
    }

    public void increaseRed() {
        ++red;
    }

    public void increaseNo(){
        ++no;
    }
}

package mygame;

import java.util.Comparator;

public class Akce implements Comparator<Akce> {
    private String typ = "";
    private Pole puvodni = null;
    private Pole cil = null;
    private int jednotky = 0;
    private int clovek = 0;   
    private int budova = 0;
    private int pokrok = 0;
    private double priorita = 0;
    private int kam = 0;
    private int surovina = 0;
   
    public Akce() {
        
    }
    
    public Akce(String typ, Pole puvodni, Pole cil, int jednotky, int clovek, int budova, int pokrok, int priorita) {
        this.typ = typ;
        this.puvodni = puvodni;
        this.cil = cil;
        this.jednotky = jednotky;
        this.clovek = clovek;   
        this.budova = budova;
        this.pokrok = pokrok;
        this.priorita = priorita;
    }
    
    public Akce(String typ, Pole puvodni, Pole cil, int jednotky) {
        this.typ = typ;
        this.puvodni = puvodni;
        this.cil = cil;
        this.jednotky = jednotky;
     }

     public Akce(String typ, Pole puvodni) {
        this.typ = typ;
        this.puvodni = puvodni;
     }

   public Akce(String typ, Pole puvodni, int budova) {
        this.typ = typ;
        this.puvodni = puvodni; 
        this.budova = budova;
     }

   public Akce(String typ, int pokrok) {
        this.typ = typ;
        if (typ.equals("pokrok")) this.pokrok = pokrok;
        if (typ.equals("surovina")) this.surovina = pokrok;
     }

   public Akce(String typ, Pole puvodni, int clovek, int kam) {
        this.typ = typ;
        this.puvodni = puvodni;
        this.clovek = clovek;
        this.kam = kam;
     }

    public int compare(Akce a, Akce b) {
        int p = 0;
        if (a.priorita > b.priorita) p = -1;
        if (a.priorita == b.priorita) p = 0;
        if (a.priorita < b.priorita) p = 1;
        if (a ==null) p = 0;
        if (b ==null) p = 0;
        return p;
    }

    public String getTyp() {
        return typ;
    }

    public Pole getPuvodni() {
        return puvodni;
    }

    public Pole getCil() {
        return cil;
    }

    public int getJednotky() {
        return jednotky;
    }

    public int getClovek() {
        return clovek;
    }

    public int getBudova() {
        return budova;
    }

    public int getPokrok() {
        return pokrok;
    }

    public double getPriorita() {
        return priorita;
    }

    public void setPriorita(double priorita) {
        this.priorita = priorita;
    }

    public int getSurovina() {
        return surovina;
    }

}

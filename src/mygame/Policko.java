package mygame;

public class Policko {
    private int x = 0;
    private int y = 0;
    private double vlastnictvi = 0;
    private double bezbudovy = 0;
    private double zakladna = 0;
    private double laborator = 0;
    private double tovarna = 0;
    private double mesto = 0;
    private double katedrala = 0;
    private double armada = 0;
    
    public Policko(int xxx, int yyy) {
        x = xxx;
        y = yyy;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getVlastnictvi() {
        return vlastnictvi;
    }

    public void setVlastnictvi(double vlastnictvi) {
        this.vlastnictvi = vlastnictvi;
    }

    public double getZakladna() {
        return zakladna;
    }

    public void setZakladna(double zakladna) {
        this.zakladna = zakladna;
    }

    public double getLaborator() {
        return laborator;
    }

    public void setLaborator(double laborator) {
        this.laborator = laborator;
    }

    public double getTovarna() {
        return tovarna;
    }

    public void setTovarna(double tovarna) {
        this.tovarna = tovarna;
    }

    public double getMesto() {
        return mesto;
    }

    public void setMesto(double mesto) {
        this.mesto = mesto;
    }

    public double getKatedrala() {
        return katedrala;
    }

    public void setKatedrala(double katedrala) {
        this.katedrala = katedrala;
    }

    public double getArmada() {
        return armada;
    }

    public void setArmada(double armada) {
        this.armada = armada;
    }

    public double getBezbudovy() {
        return bezbudovy;
    }

    public void setBezbudovy(double bezbudovy) {
        this.bezbudovy = bezbudovy;
    }
 
}

package mygame;

import com.jme3.math.ColorRGBA;
import java.util.Comparator;

public interface Hrajici extends Comparator<Hrajici> {
    
    public void zahraj();
    public void postavBudovu(Pole uzemi, int budova);
    public void zautoc(Pole puvodni,Pole cil );
    public void vynalezniPokrok(int pokrok);
    public void poslatVojaky(Pole uzemi);
    public void ziskejSuroviny(int typsurovin);
    public void pridejClovekaDoBudovy(Pole uzemi);
    public void oddelejClovekaZBudovy(Pole uzemi);
    public void pridejClovekaNaSuroviny(Pole uzemi);
    public void oddelejClovekaZeSurovin(Pole uzemi);
    public void zbourejBudovu(Pole uzemi);
    public void znicArmadu(Pole uzemi);
    public void kupArmadu(Pole uzemi);
    public void presunJednotky(Pole puvodni, Pole cil);
    public int zkontrolujProdukci();
    public void ziskejSurovinyZaZlato(int typsurovin);
    public void produkuj();
    public int vratPokrok(int pokrok);
    public int getKamen();
    public int getZelezo();
    public int getObili();
    public int getRopa();
    public int getVeda();
    public int getKultura();
    public int getHrac();
    public int getAkce();
    public void setAkce(int akce);
    public int getProdukcezlata();
    public void setProdukcezlata(int produkcezlata);
    public int[] getCenapokroku();
    public void setCenapokroku(int[] cenapokroku);
    public String getChyba();
    public void setChyba(String chyba);
    public ColorRGBA vratBarvu();
    public int getSoucetPokroku();
    public int[] getPokroky();
}




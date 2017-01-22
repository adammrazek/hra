package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Collections;

public class Generator {
    ArrayList<Surovina> prvni = new ArrayList<Surovina>();
    ArrayList<Surovina> druhy = new ArrayList<Surovina>();
    ArrayList<Surovina> treti = new ArrayList<Surovina>();
    ArrayList<Surovina> ctvrty = new ArrayList<Surovina>();
    ArrayList<Surovina> prvnix = new ArrayList<Surovina>();
    ArrayList<Surovina> druhyx = new ArrayList<Surovina>();
    ArrayList<Surovina> tretix = new ArrayList<Surovina>();
    ArrayList<Surovina> ctvrtyx = new ArrayList<Surovina>();    
    ArrayList<Surovina> mezivlevo = new ArrayList<Surovina>();
    ArrayList<Surovina> mezivpravo = new ArrayList<Surovina>();
    ArrayList<Surovina> mezinahore = new ArrayList<Surovina>();
    ArrayList<Surovina> mezidole = new ArrayList<Surovina>();
    ArrayList<Surovina> prostredek = new ArrayList<Surovina>();
    ArrayList<Surovina> hraci = new ArrayList<Surovina>();
    ArrayList<Surovina> hracix = new ArrayList<Surovina>();    
    ArrayList<Surovina> mezix = new ArrayList<Surovina>();
    ArrayList<Surovina> meziy = new ArrayList<Surovina>();
    ArrayList<Surovina> prazdny = new ArrayList<Surovina>();
    
    private int[] plocha = {2,2,5,11,11,11,11,5,3,3,
                           2,2,2,5,11,11,11,5,3,3,3,
                           2,2,7,7,11,11,8,8,3,3,
                           5,5,7,7,7,5,8,8,8,5,5,
                           10,10,7,7,14,14,8,8,12,12,
                           10,10,10,5,14,14,14,5,12,12,12,
                           10,10,6,6,14,14,9,9,12,12,
                           5,5,6,6,6,5,9,9,9,5,5,
                           1,1,6,6,13,13,9,9,4,4,
                           1,1,1,5,13,13,13,5,4,4,4,
                           1,1,5,13,13,13,13,5,4,4 };
    
    Generator() {
    hraci.add(new Surovina(1,4));
    hraci.add(new Surovina(1,5));
    hraci.add(new Surovina(2,4));
    hraci.add(new Surovina(2,5));
    hraci.add(new Surovina(3,3));
    hraci.add(new Surovina(4,3));
    hraci.add(new Surovina(4,4));
    hracix.add(new Surovina(1,4));
    hracix.add(new Surovina(1,5));
    hracix.add(new Surovina(1,6));
    hracix.add(new Surovina(2,5));
    hracix.add(new Surovina(3,3));
    hracix.add(new Surovina(4,3));
    hracix.add(new Surovina(4,4));
       
    mezix.add(new Surovina(1,5));
    mezix.add(new Surovina(1,6));
    mezix.add(new Surovina(2,5));
    mezix.add(new Surovina(2,6));
    mezix.add(new Surovina(3,4));
    mezix.add(new Surovina(4,5));
    mezix.add(new Surovina(5,3));
    
    meziy.add(new Surovina(1,5));
    meziy.add(new Surovina(1,6));
    meziy.add(new Surovina(2,4));
    meziy.add(new Surovina(2,5));
    meziy.add(new Surovina(2,5));
    meziy.add(new Surovina(2,6));
    meziy.add(new Surovina(3,4));
    meziy.add(new Surovina(4,5));
    meziy.add(new Surovina(5,3));
    
    prostredek.add(new Surovina(1,6));
    prostredek.add(new Surovina(2,6));
    prostredek.add(new Surovina(2,6));
    prostredek.add(new Surovina(3,4));
    prostredek.add(new Surovina(4,5));
    prostredek.add(new Surovina(4,5));
    prostredek.add(new Surovina(5,4));
      
    prvni = new ArrayList<Surovina>(hraci);
    druhy = new ArrayList<Surovina>(hraci);
    treti = new ArrayList<Surovina>(hraci);
    ctvrty = new ArrayList<Surovina>(hraci);
    prvnix = new ArrayList<Surovina>(hracix);
    druhyx = new ArrayList<Surovina>(hracix);
    tretix = new ArrayList<Surovina>(hracix);
    ctvrtyx = new ArrayList<Surovina>(hracix);    
    mezivlevo = new ArrayList<Surovina>(mezix);
    mezivpravo = new ArrayList<Surovina>(mezix);
    mezinahore = new ArrayList<Surovina>(meziy);
    mezidole = new ArrayList<Surovina>(meziy);
    
    for (int i = 0; i < 100; i++) { prazdny.add(new Surovina(0,0)); }
    
    }
    
    public ArrayList<Pole> vygenerujePole(AssetManager assetManager, BitmapFont guiFont, Node rootNode) {
        ArrayList<Pole> pole = new ArrayList<Pole>();
        int i = 0;
        int xxx = 0;
        int yyy = 1;
        while (i<115) {
        xxx++;
        Surovina s = vratSurovinu(plocha[i]);
        pole.add(new Pole(assetManager,guiFont,xxx,yyy,plocha[i],s.getTyp(),s.getPocet(),rootNode));
        i++;

        if (xxx>9 && yyy % 2 == 1) {xxx=0; yyy++;}
        if (xxx>10) {xxx=0; yyy++;}
        }
        return pole;
    }
    
    public ArrayList<Surovina> vratSeznam(int oblast) {
        if (oblast==1) { return prvni;}
        else if (oblast==2) { return druhy;}
        else if (oblast==3) { return treti;}
        else if (oblast==4) { return ctvrty;}
        else if (oblast==10) { return mezivlevo;}
        else if (oblast==11) { return mezinahore;}
        else if (oblast==12) { return mezivpravo;}
        else if (oblast==13) { return mezidole;}
        else if (oblast==14) { return prostredek;}  
        else if (oblast==6) { return prvnix;}  
        else if (oblast==7) { return druhyx;}  
        else if (oblast==8) { return tretix;}  
        else if (oblast==9) { return ctvrtyx;}  
        else {
        return prazdny;}
    }
    
    public Surovina vratSurovinu(int oblast) {
     ArrayList<Surovina> s = vratSeznam(oblast);
     Collections.shuffle(s);     
     Surovina surovina = s.get(0);
     s.remove(0);
     return surovina;
    }

    private class Surovina {
        private int typ = 0;
        private int pocet = 0;

       public Surovina(int typ, int pocet) {
           this.typ = typ;
           this.pocet = pocet;
       }

       public int getTyp() {
           return typ;
       }

       public int getPocet() {
           return pocet;
       }
       
    }
  
}

package mygame;

import com.jme3.math.ColorRGBA;
import java.util.ArrayList;
import java.util.Iterator;

public class Historie {
    private String typ = "";
    private int polezx = 0;
    private int polezy = 0;
    private int polenax = 0;
    private int polenay = 0;
    private int pocet = 0;
    private int surovina;
    private int jednotky = 0;
    private int jednotkyobrana = 0;    
    private int utok = 0;
    private int obrana = 0;
    private int hrac = 0;
    private int budova = 0;
    private int pokrok = 0;
    private int clovek = 0;
    private int hracobrana = 0;
    private int protihrac = 0;
    private ColorRGBA barva = ColorRGBA.Gray;
    private int kolo = 0;
    private Vyhledavac tah = null;
    
  
    public Historie(String typ, Pole polez, Pole polena, int jednotky, int jednotkyobrana, int hracobrana, int protihrac, int utok, int obrana, Vyhledavac tah) {
        this.typ = typ;
        this.hrac = polez.getHrac();
        this.polezx = polez.getX();
        this.polezy = polez.getY();
        this.polenax = polena.getX();
        this.polenay = polena.getY();
        this.jednotky = jednotky;
        this.jednotkyobrana = jednotkyobrana;
        this.hracobrana = hracobrana;
        this.protihrac = protihrac;
        this.utok = utok;
        this.obrana = obrana;
        this.barva = tah.najdiHrace(polez.getHrac()).vratBarvu();
        this.kolo = tah.getKolo();
        this.tah = tah;
        zapis(polez, polena, tah);
    }
    
     public Historie(String typ, Pole polez, Vyhledavac tah) {  
        this.typ = typ;
        this.hrac = polez.getHrac();
        this.polezx = polez.getX();
        this.polezy = polez.getY();
        this.barva = tah.najdiHrace(polez.getHrac()).vratBarvu();
        this.kolo = tah.getKolo();
        this.tah = tah;
        zapis(polez, null, tah);
    }
        
    public Historie(String typ, int surovina, int pocet, Vyhledavac tah) {  
        this.typ = typ;
        this.hrac = tah.getHracnatahu();
        this.surovina = surovina;
        this.pocet = pocet;
        this.barva = tah.najdiHrace(hrac).vratBarvu();
        this.kolo = tah.getKolo();
        this.tah = tah;
        zapis(null, null, tah);
    } 

    public Historie(String typ, Pole polez, int budova, Vyhledavac tah) {
        this.typ = typ;
        this.hrac = tah.getHracnatahu();
        this.polezx = polez.getX();
        this.polezy = polez.getY();
        if (typ.equals("postavit")) this.budova = budova;
        if (typ.equals("zbourat")) this.budova = budova;
        if (typ.equals("clovek")) clovek = budova; 
        if (typ.equals("zrusclovek")) clovek = budova;
        this.barva = tah.najdiHrace(polez.getHrac()).vratBarvu();
        this.kolo = tah.getKolo();
        this.tah = tah;
        zapis(polez, null, tah);
    }
    
    public Historie(String typ, Pole polez, Pole polena, int jednotky, Vyhledavac tah) {
        this.typ = typ;
        this.hrac = polez.getHrac();
        this.polezx = polez.getX();
        this.polezy = polez.getY();
        this.polenax = polena.getX();
        this.polenay = polena.getY();
        this.jednotky = jednotky;
        this.barva = tah.najdiHrace(polez.getHrac()).vratBarvu();
        this.kolo = tah.getKolo();
        this.tah = tah;
        zapis(polez, polena, tah);
    }
        
    public Historie(String typ, int pokrok, Vyhledavac tah) {
        this.typ = typ;
        this.hrac = tah.getHracnatahu();
        this.barva = tah.najdiHrace(hrac).vratBarvu();
        this.pokrok = pokrok;
        this.kolo = tah.getKolo();
        this.tah = tah;
        zapis(null, null, tah);
    }
        
     public int najdiBudovyProtivnika (Pole puvodni, int typbudovy, int okoli) {
          ArrayList<Pole> uzemi = tah.vratOkolniPoleRadiusJedna(puvodni);
          if (okoli==2) uzemi = tah.vratOkolniPoleRadiusJedna(puvodni);
          if (okoli==3) uzemi = tah.vratOkolniPoleRadiusJedna(puvodni);
          int budova = 0;
          for (Iterator<Pole> it = uzemi.iterator(); it.hasNext();) {
           Pole polea = it.next();
           if ((polea.getHrac()!=tah.getHracnatahu())&&(polea.getBudova()==typbudovy)) {budova = budova + 1;} 
          }
      return budova;         
     }
           
     public int najdiVlastniBudovy (Pole puvodni, int typbudovy, int okoli) {
          ArrayList<Pole> uzemi = tah.vratOkolniPoleRadiusJedna(puvodni);
          if (okoli==2) uzemi = tah.vratOkolniPoleRadiusJedna(puvodni);
          if (okoli==3) uzemi = tah.vratOkolniPoleRadiusJedna(puvodni);
          int budova = 0;
               for (Iterator<Pole> it = uzemi.iterator(); it.hasNext();) {
           Pole polea = it.next();
           if ((polea.getHrac()==tah.getHracnatahu())&&(polea.getBudova()==typbudovy)) {budova = budova + 1;} 
       }
      return budova;         
      }

     public int najdiMojiArmadu (Pole puvodni, int okoli) {
          ArrayList<Pole> uzemi = tah.vratOkolniPoleRadiusJedna(puvodni);
          if (okoli==2) uzemi = tah.vratOkolniPoleRadiusJedna(puvodni);
          if (okoli==3) uzemi = tah.vratOkolniPoleRadiusJedna(puvodni);
          int okoliarmada = 0;
               for (Iterator<Pole> it = uzemi.iterator(); it.hasNext();) {
           Pole polea = it.next();
           if (polea.getHrac()==tah.getHracnatahu()) {okoliarmada = okoliarmada + polea.getArmada();} 
       }
      return okoliarmada;         
      }
    
        public void zapis(Pole polez, Pole polena, Vyhledavac tah) {
            Zaznamy zaznamy = new Zaznamy();
            if (polez==null) polez=new Pole();
            if (polena==null) polena=new Pole();
            int budovax = polez.getBudova();
            int[] puvodni = zaznamy.prevedSouradnice(polez, hrac);
            int[] cil = zaznamy.prevedSouradnice(polena, hrac);            
            if (typ.equals("zbourat")) budovax = this.budova;
            int cizipolea = tah.hledejPole(tah.vratOkolniPoleRadiusJedna(polez), "vsechny", 0, 0, 7, hrac).size();
            if (utok>obrana) cizipolea = cizipolea-1;
            int nepratelskapolea = tah.hledejPole(tah.vratOkolniPoleRadiusJedna(polez), "vsechny", 0, 0, 6, hrac).size();
            if (hracobrana != 0) nepratelskapolea = nepratelskapolea+1;
            int okolniarmadaa = tah.najdiArmaduProtivnika(polez, 1, hrac);
            if (utok>obrana) okolniarmadaa=okolniarmadaa-jednotkyobrana;
            else okolniarmadaa=okolniarmadaa-jednotkyobrana+polena.getArmada();
            int mojearmadaa = najdiMojiArmadu(polez, 1);
            if (utok>obrana) mojearmadaa=mojearmadaa-polena.getArmada();
            int veda = tah.najdiHrace(hrac).getVeda();
            if (typ.equals("pokrok")) veda = veda+tah.getCenapokroku()[pokrok-1];
            int obili = tah.najdiHrace(hrac).getObili();
            if ((typ.equals("ziskat"))&&(surovina==1)) obili=obili-4;
            int ropa = tah.najdiHrace(hrac).getRopa();
            if ((typ.equals("ziskat"))&&(surovina==2)) ropa=ropa-2;
            int zelezo = tah.najdiHrace(hrac).getZelezo();
            if ((typ.equals("ziskat"))&&(surovina==3)) zelezo=zelezo-2;
            if (typ.equals("kuparmadu")) zelezo=zelezo+5;            
            int kamen = tah.najdiHrace(hrac).getKamen();
            if ((typ.equals("ziskat"))&&(surovina==4)) kamen=kamen-2;
            if (typ.equals("postav")) kamen=kamen+tah.getCenabudov()[budova-1];
            
            String insert = "INSERT INTO tah (IDHRY, TYP, HRAC, VITEZ, TAH, PUVODNIX, PUVODNIY,"
               + " PUVODNITYP, PUVODNIPOCET, PUVODNIBUDOVA, PUVODNIARMADA, SUROVINA, POKROK, JEDNOTKY, ZBYLAARMADA,"
               + " CILX, CILY, CILTYP, CILPOCET, CILBUDOVA, CILARMADA, CIZIPOLEA, CIZIPOLEB, CIZIPOLEC, NEPRATELSKAPOLEA,"
               + " NEPRATELSKAPOLEB, NEPRATELSKAPOLEC, PROTIVNIKARMADAA, PROTIVNIKARMADAB, PROTIVNIKARMADAC, MOJEARMADAA"
               + ", MOJEARMADAB, MOJEARMADAC, MOJEKULTURA, MOJEVEDA, MOJEOBILI, MOJEROPA, MOJEZELEZO, MUJKAMEN, MOZNOOBILI,"
               + " MOZNOSPOTREBAOBILI, MOZNOROPA, MOZNOSPOTREBAROPY, MOZNOZELEZA, MOZNOSPOTREBAZELEZA, MOZNOKAMENE,"
               + " MOZNOZLATA, MOJEARMADACELKEM, MOJEZAKLADNY, MOJELABORATORE, MOJETOVARNY, MOJEMESTA, MOJEKATEDRALY,"
               + " MOJEPOBILI, MOJEPROPA, MOJEPZELEZO, MOJEPKAMEN, MOJEPUTOK, MOJEPOBRANA, MOJEPNAHODA, OKOLIMOJEZAKLADNY,"
               + " OKOLIMOJELABORATORE, OKOLIMOJETOVARNY, OKOLIMOJEMESTA, OKOLIMOJEKATEDRALY, OKOLIPROTIVNIKZAKLADNY,"
               + " OKOLIPROTIVNIKLABORATORE, OKOLIPROTIVNIKTOVARNY, OKOLIPROTIVNIKMESTA, OKOLIPROTIVNIKKATEDRALY,"
               + " PROTIVNIKKULTURA, PROTIVNIKVEDA, PROTIVNIKOBILI, PROTIVNIKROPA, PROTIVNIKZELEZO, PROTIVNIKKAMEN,"
               + " PROTIVNIKMOZNOOBILI, PROTIVNIKMOZNOSPOTREBAOBILI, PROTIVNIKMOZNOROPA, PROTIVNIKMOZNOSPOTREBAROPY,"
               + " PROTIVNIKMOZNOZELEZA, PROTIVNIKMOZNOSPOTREBAZELEZA, PROTIVNIKMOZNOKAMENE, PROTIVNIKMOZNOZLATA,"
               + " PROTIVNIKARMADA, PROTIVNIKZAKLADNY, PROTIVNIKLABORATORE, PROTIVNIKTOVARNY, PROTIVNIKMESTA,"
               + " PROTIVNIKKATEDRALY, PROTIVNIKPOBILI, PROTIVNIKPROPA, PROTIVNIKPZELEZO, PROTIVNIKPKAMEN, PROTIVNIKPUTOK,"
               + " PROTIVNIKPOBRANA, PROTIVNIKPNAHODA, CILOKOLIMOJEZAKLADNY, CILOKOLIMOJELABORATORE, CILOKOLIMOJETOVARNY,"
               + " CILOKOLIMOJEMESTA, CILOKOLIMOJEKATEDRALY, CILOKOLIPROTIVNIKZAKLADNY, CILOKOLIPROTIVNIKLABORATORE,"
               + " CILOKOLIPROTIVNIKTOVARNY, CILOKOLIPROTIVNIKMESTA, CILOKOLIPROTIVNIKKATEDRALY, CILCIZIPOLEA,"
               + " CILCIZIPOLEB, CILCIZIPOLEC, CILNEPRATELSKEPOLEA, CILNEPRATELSKEPOLEB, CILNEPRATELSKEPOLEC,"
               + " CILMOJEARMADAA, CILMOJEARMADAB, CILMOJEARMADAC, CILPROTIVNIKARMADAA, CILPROTIVNIKARMADAB, CILPROTIVNIKARMADAC"
               + ") VALUES ('"+tah.getAdresa()+"','"+typ+"',"+hrac+", 0, "+kolo+", "
               +puvodni[0]+", "+puvodni[1]+", "+polez.getTyp()+", "+polez.getPocet()+", "+budovax+", "+(polez.getArmada()+jednotky)
               +", "+surovina+", "+pokrok+", "+jednotky+","+polez.getArmada()+", "+cil[0]+", "+cil[1]+", "+polena.getTyp()
               +", "+polena.getPocet()+", "+polena.getBudova()+", "+jednotkyobrana+", "+cizipolea
               +", "+tah.hledejPole(tah.vratOkolniPoleRadiusDva(polez), "vsechny", 0, 0, 7, hrac).size()+", "+tah.hledejPole(tah.vratOkolniPoleRadiusTri(polez), "vsechny", 0, 0, 7, hrac).size()
               +", "+nepratelskapolea+", "+tah.hledejPole(tah.vratOkolniPoleRadiusDva(polez), "vsechny", 0, 0, 6, hrac).size()+", "+tah.hledejPole(tah.vratOkolniPoleRadiusTri(polez), "vsechny", 0, 0, 6, hrac).size()
               +", "+okolniarmadaa+", "+tah.najdiArmaduProtivnika(polez, 2, hrac)+", "+tah.najdiArmaduProtivnika(polez, 3, hrac)+", "+mojearmadaa
               +", "+najdiMojiArmadu(polez, 2)+", "+najdiMojiArmadu(polez, 3)+", "+tah.najdiHrace(hrac).getKultura()+","+veda+","+obili+","+ropa+","+zelezo+","+kamen
               +","+tah.najdiUdajeOHraci("obili", 2, hrac)+","+(tah.najdiUdajeOHraci("obili", 2, hrac)-(Math.min(tah.najdiUdajeOHraci("clovek", 2, hrac),tah.najdiUdajeOHraci("mesto", 2, hrac)*7+7)))
               +","+tah.najdiUdajeOHraci("ropa", 2, hrac)+","+(tah.najdiUdajeOHraci("ropa", 2, hrac)-tah.najdiUdajeOHraci("armada", 1, hrac))
               +","+tah.najdiUdajeOHraci("zelezo", 2, hrac)+","+(tah.najdiUdajeOHraci("zelezo", 2, hrac)-tah.najdiUdajeOHraci("tovarna", 2, hrac))
               +","+tah.najdiUdajeOHraci("kamen", 2, hrac)+","+tah.najdiUdajeOHraci("zlato", 2, hrac)+","+tah.najdiUdajeOHraci("armada", 1, hrac)
               +","+tah.najdiUdajeOHraci("zakladna", 2, hrac)+","+tah.najdiUdajeOHraci("laborator", 2, hrac)+","+tah.najdiUdajeOHraci("tovarna", 2, hrac)
               +","+tah.najdiUdajeOHraci("mesto", 2, hrac)+","+tah.najdiUdajeOHraci("katedrala", 2, hrac)+","+tah.najdiHrace(hrac).vratPokrok(1)
               +","+tah.najdiHrace(hrac).vratPokrok(2)+","+tah.najdiHrace(hrac).vratPokrok(3)+","+tah.najdiHrace(hrac).vratPokrok(4)
               +","+tah.najdiHrace(hrac).vratPokrok(5)+","+tah.najdiHrace(hrac).vratPokrok(6)+","+tah.najdiHrace(hrac).vratPokrok(7)
               +","+najdiVlastniBudovy(polez, 1, 1)+","+najdiVlastniBudovy(polez, 2, 1)+","+najdiVlastniBudovy(polez, 3, 1)
               +","+najdiVlastniBudovy(polez, 4, 1)+","+najdiVlastniBudovy(polez, 5, 1)+","+najdiBudovyProtivnika(polez, 1, 1)
               +","+najdiBudovyProtivnika(polez, 2, 1)+","+najdiBudovyProtivnika(polez, 3, 1)+","+najdiBudovyProtivnika(polez, 4, 1)
               +","+najdiBudovyProtivnika(polez, 5, 1)+","+tah.najdiHrace(protihrac).getKultura()
               +","+tah.najdiHrace(protihrac).getVeda()+","+tah.najdiHrace(protihrac).getObili()+","+tah.najdiHrace(protihrac).getRopa()
               +","+tah.najdiHrace(protihrac).getZelezo()+","+tah.najdiHrace(protihrac).getKamen()+","+tah.najdiUdajeOHraci("obili", 2, protihrac)
               +","+(tah.najdiUdajeOHraci("obili", 2, protihrac)-Math.min(tah.najdiUdajeOHraci("clovek", 2, protihrac), tah.najdiUdajeOHraci("mesto", 2, protihrac))*7+7)
               +","+tah.najdiUdajeOHraci("ropa", 2, protihrac)+","+(tah.najdiUdajeOHraci("ropa", 2, protihrac)-tah.najdiUdajeOHraci("armada", 1, protihrac))
               +","+tah.najdiUdajeOHraci("zelezo", 2, protihrac)+","+(tah.najdiUdajeOHraci("zelezo", 2, protihrac)-tah.najdiUdajeOHraci("tovarna", 2, protihrac))
               +","+tah.najdiUdajeOHraci("kamen", 2, protihrac)+","+tah.najdiUdajeOHraci("zlato", 2, protihrac)+","+tah.najdiUdajeOHraci("armada", 1, protihrac)
               +","+tah.najdiUdajeOHraci("zakladna", 2, protihrac)+","+tah.najdiUdajeOHraci("laborator", 2, protihrac)+","+tah.najdiUdajeOHraci("tovarna", 2, protihrac)
               +","+tah.najdiUdajeOHraci("mesto", 2, protihrac)+","+tah.najdiUdajeOHraci("katedrala", 2, protihrac)+","+tah.najdiHrace(protihrac).vratPokrok(1)
               +","+tah.najdiHrace(protihrac).vratPokrok(2)+","+tah.najdiHrace(protihrac).vratPokrok(3)+","+tah.najdiHrace(protihrac).vratPokrok(4)
               +","+tah.najdiHrace(protihrac).vratPokrok(5)+","+tah.najdiHrace(protihrac).vratPokrok(6)+","+tah.najdiHrace(protihrac).vratPokrok(7)
               +","+najdiVlastniBudovy(polena, 1, 1)+","+najdiVlastniBudovy(polena, 2, 1)+","+najdiVlastniBudovy(polena, 3, 1)
               +","+najdiVlastniBudovy(polena, 4, 1)+","+najdiVlastniBudovy(polena, 5, 1)+","+najdiBudovyProtivnika(polena, 1, 1)
               +","+najdiBudovyProtivnika(polena, 2, 1)+","+najdiBudovyProtivnika(polena, 3, 1)+","+najdiBudovyProtivnika(polena, 4, 1)
               +","+najdiBudovyProtivnika(polena, 5, 1)+","+tah.hledejPole(tah.vratOkolniPoleRadiusJedna(polena), "vsechny", 0, 0, 7, hrac).size()
               +","+tah.hledejPole(tah.vratOkolniPoleRadiusDva(polena), "vsechny", 0, 0, 7, hrac).size()+","+tah.hledejPole(tah.vratOkolniPoleRadiusTri(polena), "vsechny", 0, 0, 7, hrac).size()
               +","+tah.hledejPole(tah.vratOkolniPoleRadiusJedna(polena), "vsechny", 0, 0, 6, hrac).size()+","+tah.hledejPole(tah.vratOkolniPoleRadiusDva(polena), "vsechny", 0, 0, 6, hrac).size()
               +","+tah.hledejPole(tah.vratOkolniPoleRadiusTri(polena), "vsechny", 0, 0, 6, hrac).size()+","+najdiMojiArmadu(polena, 1)+","+najdiMojiArmadu(polena, 2)+","+najdiMojiArmadu(polena, 3)
               +","+tah.najdiArmaduProtivnika(polena, 1, hrac)+","+tah.najdiArmaduProtivnika(polena, 2, hrac)+","+tah.najdiArmaduProtivnika(polena, 3, hrac)+")"; 
        zaznamy.vloz(insert);
     }
    
    public String getTyp() {
        return typ;
    }

    public int getPolezx() {
        return polezx;
    }

    public int getPolezy() {
        return polezy;
    }

    public int getPolenax() {
        return polenax;
    }

    public int getPolenay() {
        return polenay;
    }

    public int getPocet() {
        return pocet;
    }

    public int getUtok() {
        return utok;
    }

    public int getObrana() {
        return obrana;
    }

    public int getHrac() {
        return hrac;
    }

    public ColorRGBA getBarva() {
        return barva;
    }

    public int getJednotky() {
        return jednotky;
    }

    public int getBudova() {
        return budova;
    }

    public int getPokrok() {
        return pokrok;
    }

    public int getClovek() {
        return clovek;
    }

    public int getSurovina() {
        return surovina;
    }

    public int getKolo() {
        return kolo;
    }

    public void setKolo(int kolo) {
        this.kolo = kolo;
    }
    
}

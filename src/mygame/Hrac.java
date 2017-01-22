package mygame;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Iterator;

public class Hrac implements Hrajici {
    private int hrac = 0;
    private int kamen = 10;
    private int zelezo = 10;
    private int obili = 10;
    private int ropa = 10;
    private int veda = 0;
    private int kultura = 0;
    private int akce = 3;
    private int produkcezlata = 0;
    private int zlatoobili = 0;
    private int zlatoropa = 0;
    private int zlatozelezo = 0;
    private int[] pokroky = {0,0,0,0,0,0,0};
    private Plocha kresli = null;
    private ArrayList<Pole> pole = null;
    private Vyhledavac vyhledavac = null;
    ArrayList<Historie> historie = null;
    private int[] cenabudov;
    private int[] cenapokroku;
    public Node rootNode;
    private int kolo = 0;
    private String chyba = "";
         
    
        public Hrac() {

        }

        public Hrac(int hrac, Plocha kresli, ArrayList<Pole> pole, Vyhledavac tah, ArrayList<Historie> historie, Node rootNode, Hra hra) {
            this.hrac = hrac;
            this.kresli = kresli;
            this.pole = pole;
            this.vyhledavac = tah;
            this.historie = historie;
            this.rootNode = rootNode;
            this.cenabudov = hra.getCenabudov();
            this.cenapokroku = hra.getCenapokroku();
        }
    
        public ColorRGBA vratBarvu() {
            ColorRGBA barva = ColorRGBA.Brown;
            if (getHrac()==0) barva = ColorRGBA.Gray;
            if (getHrac()==1) barva = ColorRGBA.Yellow;
            if (getHrac()==2) barva = ColorRGBA.Orange;
            if (getHrac()==3) barva = ColorRGBA.Green;
            if (getHrac()==4) barva = ColorRGBA.Cyan;     
            return barva;
         }
        
        public void nastavPokrok(int pokrok, int co) {
            pokroky[pokrok-1] = co;
        }
        
         public int vratPokrok(int pokrok) {
            return pokroky[pokrok-1];
        }
        
        public int[] getPokroky() {
            return pokroky;
        }

        public int getSoucetPokroku() {
          return pokroky[0]+pokroky[1]+pokroky[2]+pokroky[3]+pokroky[4]+pokroky[5]+pokroky[6];
        }


    public void pridejClovekaDoBudovy(Pole uzemi) {
        if ((uzemi.getClovekb()==0)&&(uzemi.getHrac()==hrac)) {
        uzemi.setClovekb(1);
        kresli.kresliTabulku(hrac);
        uzemi.kresliPole(vyhledavac, hrac);
        }
    }

    public void oddelejClovekaZBudovy(Pole uzemi) {
        if ((uzemi.getClovekb()==1)&&(uzemi.getHrac()==hrac))  {
        uzemi.setClovekb(0);
        kresli.kresliTabulku(hrac);
        uzemi.kresliPole(vyhledavac, hrac);
        }
    }

    public void pridejClovekaNaSuroviny(Pole uzemi) {
        if ((uzemi.getCloveks()==0)&&(uzemi.getHrac()==hrac))  {
        uzemi.setCloveks(1);
        kresli.kresliTabulku(hrac);
        uzemi.kresliPole(vyhledavac, hrac);
        }
    }

    public void oddelejClovekaZeSurovin(Pole uzemi) {
        if ((uzemi.getCloveks()==1)&&(uzemi.getHrac()==hrac))  {
        uzemi.setCloveks(0);
        kresli.kresliTabulku(hrac);
        uzemi.kresliPole(vyhledavac, hrac);
        }
    }   
    
     public void zbourejBudovu(Pole uzemi) {
        if ((getHrac()==uzemi.getHrac())&&(uzemi.getBudova()!=0)) {
        historie.add(new Historie("zbourat", uzemi, uzemi.getBudova(), vyhledavac));
        uzemi.setBudova(0);
        uzemi.setClovekb(0);
        vyhledavac.najdiHrace(uzemi.getHrac()).setAkce(vyhledavac.najdiHrace(uzemi.getHrac()).getAkce()-1);
        }
        kresli.kresliTabulku(hrac);
        kresli.kresliHistorii();
        uzemi.kresliPole(vyhledavac, hrac);
    } 
    
    public void znicArmadu(Pole uzemi) {
        if ((getHrac()==uzemi.getHrac())&&(uzemi.getArmada()!=0)) {
        uzemi.setArmada(uzemi.getArmada()-1);
        historie.add(new Historie("znicarmadu", uzemi, vyhledavac));
        }
        kresli.kresliTabulku(hrac);
        kresli.kresliHistorii();
        uzemi.kresliPole(vyhledavac, hrac);
    } 

    public void kupArmadu(Pole uzemi) {
        if ((getHrac()==uzemi.getHrac())&&(getZelezo()>=5)) {
        uzemi.setArmada(uzemi.getArmada()+2);
        setZelezo(getZelezo()-5);
        setAkce(getAkce()-1);
        historie.add(new Historie("kuparmadu", uzemi, vyhledavac));
        }
        kresli.kresliTabulku(hrac);
        kresli.kresliHistorii();
        uzemi.kresliPole(vyhledavac, hrac);
    }
    
    public void presunJednotky(Pole puvodni, Pole cil) {
        if ((vyhledavac.jePoleSousedni(puvodni, cil)==true)&&(cil!=null)) {
        int jednotky = puvodni.getPoslat();
        if ((cil.getHrac()==puvodni.getHrac())&&(puvodni.getArmada()>=jednotky)) {
        puvodni.setArmada(puvodni.getArmada()-jednotky);
        cil.setArmada(cil.getArmada()+jednotky);
        puvodni.setPoslat(0);
        cil.setPoslat(0);
        puvodni.kresliPole(vyhledavac, hrac);
        cil.kresliPole(vyhledavac, hrac);
        vyhledavac.najdiHrace(puvodni.getHrac()).setAkce(vyhledavac.najdiHrace(puvodni.getHrac()).getAkce()-1);
        historie.add(new Historie("presun", puvodni, cil, jednotky, vyhledavac));
        kresli.kresliTabulku(hrac);
        kresli.kresliHistorii();
         }  
        }
    }

    public void vynalezniPokrok(int pokrok) {
    if ((getVeda()>=cenapokroku[pokrok-1])&&(vratPokrok(pokrok)==0)) {
        nastavPokrok(pokrok, 1);
        setVeda(getVeda()-cenapokroku[pokrok-1]);  
        if (hrac==1) rootNode.detachChildNamed("pokrokx100x"+pokrok);
        setAkce(getAkce()-1);
        historie.add(new Historie("pokrok", pokrok, vyhledavac));
        kresli.kresliHistorii();
        kresli.kresliTabulku(hrac);
    }
    }

    public void postavBudovu(Pole uzemi, int budova) {
        if ((getHrac()==uzemi.getHrac())&&(uzemi.getBudova()==0)) {
        if (kamen>=cenabudov[budova-1]) {uzemi.setBudova(budova); kamen=(kamen-cenabudov[budova-1]); }
        vyhledavac.najdiHrace(uzemi.getHrac()).setAkce(vyhledavac.najdiHrace(uzemi.getHrac()).getAkce()-1);
        historie.add(new Historie("postavit", uzemi, budova, vyhledavac));
        kresli.kresliHistorii();
        kresli.kresliTabulku(hrac);
        uzemi.kresliPole(vyhledavac, hrac);
        }
    }    
    
    public void zautoc(Pole puvodni,Pole cil ) {
        if ((vyhledavac.jePoleSousedni(puvodni, cil)==true)&&(cil!=null)) {
            if ((puvodni.getHrac()==hrac)&&(puvodni.getHrac()!=cil.getHrac())) {
                int jednotky = puvodni.getPoslat();
                int jednotkyobrana = cil.getArmada();
                int hracobrana = cil.getHrac();
                int protihrac = cil.getHrac();
                if (jednotky>0) {
         int nahodautok = (int) (Math.random()*3);    
         int nahodaobrana = (int) (Math.random()*3);
         if (vyhledavac.najdiHrace(puvodni.getHrac()).vratPokrok(7)==1) {
         int sance = (int)Math.random()*6;
         if (sance==0) nahodautok=0;
         if ((sance==1)||(sance==2)) nahodautok=1;
         if (sance>2) nahodautok=2;
         }
         if (vyhledavac.najdiHrace(cil.getHrac()).vratPokrok(7)==1) {
         int sance = (int)Math.random()*6;
         if (sance==0) nahodaobrana=0;
         if ((sance==1)||(sance==2)) nahodaobrana=1;
         if (sance>2) nahodaobrana=2;
         }
         int obrana = cil.getArmada()+nahodaobrana+vyhledavac.najdiHrace(cil.getHrac()).vratPokrok(6);
         if ((cil.getBudova()==1)&&(cil.getClovekb()==1)) obrana = obrana + 4;
         if (cil.getHrac()==0) obrana = obrana + 1; 
         int utok = jednotky+vyhledavac.najdiHrace(puvodni.getHrac()).vratPokrok(5)+nahodautok; 

         puvodni.setArmada(puvodni.getArmada()-jednotky);
         if (utok>obrana) {
         int novejednotky = utok-obrana;
         if (novejednotky>=jednotky) novejednotky=jednotky;
         cil.setArmada(novejednotky);
         cil.setHrac(puvodni.getHrac());
         cil.setClovekb(0);
         cil.setCloveks(0);
         ArrayList<Pole> novapole = vyhledavac.vratOkolniPoleRadiusJedna(cil);
             for (Iterator<Pole> it = novapole.iterator(); it.hasNext();) {
                 Pole poleaaa = it.next();
                 vyhledavac.odkryjOkolniPole(poleaaa);
                 poleaaa.kresliPole(vyhledavac, hrac);
             }
         } else {
             if (cil.getHrac()!=0) cil.setArmada(obrana-utok);
         }
         vyhledavac.najdiHrace(puvodni.getHrac()).setAkce(vyhledavac.najdiHrace(puvodni.getHrac()).getAkce()-1);
         puvodni.kresliPole(vyhledavac, hrac);
         cil.kresliPole(vyhledavac, hrac);
         historie.add(new Historie("utok", puvodni, cil, jednotky, jednotkyobrana, hracobrana, protihrac, utok, obrana, vyhledavac));
         kresli.kresliHistorii();
                }
         puvodni.setPoslat(0);
         cil.setPoslat(0);
         puvodni.kresliPole(vyhledavac, hrac);
         cil.kresliPole(vyhledavac, hrac);
            }
            kresli.kresliTabulku(hrac);
        }
    }

    public void poslatVojaky(Pole uzemi) {
        if (uzemi.getArmada()>uzemi.getPoslat()) { uzemi.setPoslat(uzemi.getPoslat()+1);
        uzemi.kresliPole(vyhledavac, hrac); }
    }

    public void ziskejSuroviny(int typsurovin) {
        if (typsurovin==1) obili=obili+4;
        if (typsurovin==2) ropa=ropa+2;
        if (typsurovin==3) zelezo=zelezo+2;
        if (typsurovin==4) kamen=kamen+2;
        setAkce(getAkce()-1);
        historie.add(new Historie("ziskat", typsurovin, 2, vyhledavac));
        kresli.kresliHistorii();
        kresli.kresliTabulku(hrac);
    }

      public int zkontrolujProdukci() {
        double chybijidla = 0;
        int chybiropa = 0;
        int chybizeleza = 0;
        int zlato = vyhledavac.najdiUdajeOHraci("zlato", 1, hrac);
        int lidemaximum = vyhledavac.najdiUdajeOHraci("mesto", 1, hrac)*7+7;
        int lide = vyhledavac.najdiUdajeOHraci("clovek", 1, hrac);
        int armada = vyhledavac.najdiUdajeOHraci("armada", 1, hrac);
        int celkemropy = vyhledavac.najdiUdajeOHraci("ropa", 1, hrac)+getRopa();
        int tovarny = vyhledavac.najdiUdajeOHraci("tovarna", 1, hrac);
        int celkemzeleza = vyhledavac.najdiUdajeOHraci("zelezo", 1, hrac)+getZelezo();
        int celkemjidla = vyhledavac.najdiUdajeOHraci("obili", 1, hrac)+getObili();
        if (celkemjidla<lide) {
        chybijidla = (celkemjidla-lide)*(-1);
        zlato = zlato - (int)(Math.ceil(chybijidla/2.0f));
        celkemjidla += chybijidla;
        }
        if (celkemropy<armada) {
        chybiropa = (celkemropy-armada)*(-1);
        zlato = zlato - chybiropa;
        celkemropy += chybiropa;
        }
        if (celkemzeleza<tovarny) {
        chybizeleza = (celkemzeleza-tovarny)*(-1);
        zlato = zlato - chybizeleza;
        celkemzeleza += chybizeleza;
        } 

        if ((lide<=lidemaximum)&&(armada<=celkemropy)&&(tovarny<=celkemzeleza)&&(celkemjidla>=lide)) {      
        if (!((zlatoobili>0)||(zlatoropa>0)||(zlatozelezo>0))) {
            if (zlato>=0) {
                zlatoobili=((int)(chybijidla));
                zlatoropa=chybiropa;
                zlatozelezo=chybizeleza;
            }
        }    
        } else { zlato = - 1; }
        if (zlato<1) {           
            if (!(lide<=lidemaximum)) chyba="Prilis mnoho lidi.";
            if (chybiropa>0) { chyba="Nedostatek ropy.";}
            if (chybizeleza>0) chyba="Nedostatek zeleza.";
            if (chybijidla>0) chyba="Nedostatek obili.";
        } else { chyba=""; }
        return (int)(zlato);
    }

       public void produkuj() {
          double zlato = 0;
          for (Iterator<Pole> it = vyhledavac.najdiPolePatriciHraci(hrac).iterator(); it.hasNext();) {
              Pole polea = it.next();
              if ((polea.getTyp()==1)&&(polea.getCloveks()==1)) {setObili(getObili()+polea.getPocet()+vratPokrok(1));}
              if ((polea.getTyp()==2)&&(polea.getCloveks()==1)) {setRopa(getRopa()+polea.getPocet()+vratPokrok(2));}
              setRopa(getRopa()-polea.getArmada());
              if ((polea.getTyp()==3)&&(polea.getCloveks()==1)) {setZelezo(getZelezo()+polea.getPocet()+vratPokrok(3));}
              if ((polea.getTyp()==4)&&(polea.getCloveks()==1)) {kamen=kamen+polea.getPocet()+vratPokrok(4);}
              if ((polea.getTyp()==5)&&(polea.getCloveks()==1)) {zlato = zlato + polea.getPocet();}  
              if ((polea.getBudova()==2)&&(polea.getClovekb()==1)) {setVeda(getVeda()+1);}
              if ((polea.getBudova()==3)&&(polea.getClovekb()==1)) {polea.setArmada(polea.getArmada()+2); setZelezo(getZelezo()-1);}
              if ((polea.getBudova()==5)&&(polea.getClovekb()==1)) {kultura=kultura+1;}                 
              setObili(getObili()-polea.getCloveks()-polea.getClovekb());              
          }
          setObili(getObili()+zlatoobili);
          setRopa(getRopa()+zlatoropa);
          setZelezo(getZelezo()+zlatozelezo);
          zlatoobili=0;
          zlatoropa=0;
          zlatozelezo=0;     
          kresli.kresliTabulku(hrac);
      }

      public void ziskejSurovinyZaZlato(int typsurovin) {
        if (typsurovin==1) obili=obili+2;
        if (typsurovin==2) ropa=ropa+1;
        if (typsurovin==3) zelezo=zelezo+1;
        if (typsurovin==4) kamen=kamen+1;
        setProdukcezlata(getProdukcezlata()-1);
        if ((hrac==1)&&(getProdukcezlata()<1)) {
        rootNode.detachChildNamed("zlatox100x1");
        rootNode.detachChildNamed("zlatox100x2");
        rootNode.detachChildNamed("zlatox100x3");
        rootNode.detachChildNamed("zlatox100x4");  
        }
        kresli.kresliTabulku(hrac);
    }
      
    public int getKamen() {
        return kamen;
    }

    public void setKamen(int kamen) {
        this.kamen = kamen;
    }

    public int getZelezo() {
        return zelezo;
    }

    public void setZelezo(int zelezo) {
        this.zelezo = zelezo;
    }

    public int getObili() {
        return obili;
    }

    public void setObili(int obili) {
        this.obili = obili;
    }

    public int getRopa() {
        return ropa;
    }

    public void setRopa(int ropa) {
        this.ropa = ropa;
    }

    public int getVeda() {
        return veda;
    }

    public void setVeda(int veda) {
        this.veda = veda;
    }

    public int getKultura() {
        return kultura;
    }
 
    public int getHrac() {
        return hrac;
    }

    public int getAkce() {
        return akce;
    }

    public void setAkce(int akce) {
        this.akce = akce;
    }

    public int getProdukcezlata() {
        return produkcezlata;
    }

    public void setProdukcezlata(int produkcezlata) {
        this.produkcezlata = produkcezlata;
    }

    public int[] getCenapokroku() {
        return cenapokroku;
    }

    public void setCenapokroku(int[] cenapokroku) {
        this.cenapokroku = cenapokroku;
    }

    public ArrayList<Pole> getPole() {
        return pole;
    }

    public String getChyba() {
        return chyba;
    }

    public void setChyba(String chyba) {
        this.chyba = chyba;
    }

    public void zahraj() {
        
    }
    
    public int compare(Hrajici a, Hrajici b) {
        return a.getKultura() > b.getKultura()  ? -1 : a.getKultura()  == b.getKultura()  ? 0 : 1;
    }

}

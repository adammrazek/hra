package mygame;

import java.util.ArrayList;
import java.util.Iterator;

public class Vyhledavac {
    public ArrayList<Pole> pole = new ArrayList<Pole>();
    public ArrayList<Hrajici> hraci = null;
    public int hracnatahu = 1;
    private String adresa = "";
    private int kolo = 1;
    private int[] cenabudov;
    private int[] cenapokroku;
    
    public Vyhledavac(ArrayList<Pole> pole, ArrayList<Hrajici> hraci, int hracnatahu, Hra hra) {
        this.pole = pole;
        this.hraci = hraci;
        this.hracnatahu = hracnatahu;
        this.adresa = hra.getAdresa();
        this.cenabudov = hra.getCenabudov();
        this.cenapokroku = hra.getCenapokroku();
    }
  
     public boolean jePoleSousedni(Pole uzemi, Pole okoli) {
        boolean sousedni = false;
        ArrayList<Pole> okolnipole = vratOkolniPoleRadiusJedna(uzemi);
        for (Iterator<Pole> it = okolnipole.iterator(); it.hasNext();) {
            Pole polea = it.next();
            if ((polea.getX()==okoli.getX())&&(polea.getY()==okoli.getY())) sousedni=true;
        }        
        return sousedni;
    }
    
    
    public Pole najdiPole(int x, int y) {
        Pole uzemi = null;
        for (Iterator<Pole> it = pole.iterator(); it.hasNext();) {
            Pole polea = it.next();
            if ((polea.getX()==x)&&(polea.getY()==y)) uzemi=polea;  
        }
        return uzemi;
    }
    
    public ArrayList<Pole> najdiPolePatriciHraci(int cislohrace) {
        ArrayList<Pole> uzemi = new ArrayList<Pole>();
        for (Iterator<Pole> it = pole.iterator(); it.hasNext();) {
            Pole polea = it.next();
            if (polea.getHrac()==cislohrace)  {uzemi.add(polea);}   
        }
        return uzemi;
    }

    public Hrajici najdiHrace(int cislo) {
        return hraci.get(cislo);
    }
        
    public void odkryjOkolniPole(Pole uzemi) {
       if (uzemi.getOblast()!=5) {
       if (uzemi.getHrac()>0) uzemi.setOdhalene(1);
        try {if (najdiPole(uzemi.getX()+1,uzemi.getY()).getHrac()>0) {uzemi.setOdhalene(1); } } catch(NullPointerException e ) {}      
        try {if (najdiPole(uzemi.getX()-1,uzemi.getY()).getHrac()>0) {uzemi.setOdhalene(1);} } catch(NullPointerException e ) { }
        try {if ((najdiPole(uzemi.getX()-1,uzemi.getY()+1).getHrac()>0)&&(uzemi.getY()%2==0)) {uzemi.setOdhalene(1);} } catch(NullPointerException e ) { }
        try {if (najdiPole(uzemi.getX(),uzemi.getY()+1).getHrac()>0) {uzemi.setOdhalene(1);} } catch(NullPointerException e ) { }
        try {if ((najdiPole(uzemi.getX()+1,uzemi.getY()+1).getHrac()>0)&&(uzemi.getY()%2==1)) {uzemi.setOdhalene(1);} } catch(NullPointerException e ) { }
        try {if ((najdiPole(uzemi.getX()-1,uzemi.getY()-1).getHrac()>0)&&(uzemi.getY()%2==0)) {uzemi.setOdhalene(1);} } catch(NullPointerException e ) { }
        try {if (najdiPole(uzemi.getX(),uzemi.getY()-1).getHrac()>0) {uzemi.setOdhalene(1);}  } catch(NullPointerException e ) { }
        try {if ((najdiPole(uzemi.getX()+1,uzemi.getY()-1).getHrac()>0)&&(uzemi.getY()%2==1)) {uzemi.setOdhalene(1);} } catch(NullPointerException e ) { }   
       }
    } 
    
   public ArrayList<Pole> vratOkolniPoleRadiusJedna(Pole uzemi) {
       int hracx = 5;
       ArrayList<Pole> okolnipole = new ArrayList<Pole>();
       if (hracx==5) {
       try {if ((najdiPole(uzemi.getX()+1,uzemi.getY()).getOblast()!=5)&&((najdiPole(uzemi.getX()+1,uzemi.getY()).getHrac()==hracx)||hracx==5)) {okolnipole.add(najdiPole(uzemi.getX()+1,uzemi.getY()));} } catch(NullPointerException e ) {}      
       try {if ((najdiPole(uzemi.getX()-1,uzemi.getY()).getOblast()!=5)&&((najdiPole(uzemi.getX()-1,uzemi.getY()).getHrac()==hracx)||hracx==5)) {okolnipole.add(najdiPole(uzemi.getX()-1,uzemi.getY()));} } catch(NullPointerException e ) { }
       try {if ((najdiPole(uzemi.getX()-1,uzemi.getY()+1).getOblast()!=5)&&(uzemi.getY()%2==0)&&((najdiPole(uzemi.getX()-1,uzemi.getY()+1).getHrac()==hracx)||hracx==5)) {okolnipole.add(najdiPole(uzemi.getX()-1,uzemi.getY()+1));} } catch(NullPointerException e ) { }
       try {if ((najdiPole(uzemi.getX(),uzemi.getY()+1).getOblast()!=5)&&((najdiPole(uzemi.getX(),uzemi.getY()+1).getHrac()==hracx)||hracx==5)) {okolnipole.add(najdiPole(uzemi.getX(),uzemi.getY()+1));} } catch(NullPointerException e ) { }
       try {if ((najdiPole(uzemi.getX()+1,uzemi.getY()+1).getOblast()!=5)&&(uzemi.getY()%2==1)&&((najdiPole(uzemi.getX()+1,uzemi.getY()+1).getHrac()==hracx)||hracx==5)) {okolnipole.add(najdiPole(uzemi.getX()+1,uzemi.getY()+1));} } catch(NullPointerException e ) { }
       try {if ((najdiPole(uzemi.getX()-1,uzemi.getY()-1).getOblast()!=5)&&(uzemi.getY()%2==0)&&((najdiPole(uzemi.getX()-1,uzemi.getY()-1).getHrac()==hracx)||hracx==5)) {okolnipole.add(najdiPole(uzemi.getX()-1,uzemi.getY()-1));} } catch(NullPointerException e ) { }
       try {if ((najdiPole(uzemi.getX(),uzemi.getY()-1).getOblast()!=5)&&((najdiPole(uzemi.getX(),uzemi.getY()-1).getHrac()==hracx)||hracx==5)) {okolnipole.add(najdiPole(uzemi.getX(),uzemi.getY()-1));}  } catch(NullPointerException e ) { }
       try {if ((najdiPole(uzemi.getX()+1,uzemi.getY()-1).getOblast()!=5)&&(uzemi.getY()%2==1)&&((najdiPole(uzemi.getX()+1,uzemi.getY()-1).getHrac()==hracx)||hracx==5)) {okolnipole.add(najdiPole(uzemi.getX()+1,uzemi.getY()-1));} } catch(NullPointerException e ) { }     
       }       
       return okolnipole;
    }
   
     public ArrayList<Pole> vratOkolniPoleRadiusDva(Pole uzemi) {
       ArrayList<Pole> okolnipole = vratOkolniPoleRadiusJedna(uzemi);
       ArrayList<Pole> vsechnapole = new ArrayList<Pole>();
       if (uzemi!=null) {
       vsechnapole.add(najdiPole(uzemi.getX(), uzemi.getY()));
         for (Iterator<Pole> it = okolnipole.iterator(); it.hasNext();) {
             Pole polea = it.next();
             vsechnapole.add(polea);
         }
         for (Iterator<Pole> it = okolnipole.iterator(); it.hasNext();) {
             Pole polea = it.next();
             ArrayList<Pole> pom = vratOkolniPoleRadiusJedna(polea);
             for (Iterator<Pole> it1 = pom.iterator(); it1.hasNext();) {
                 Pole poled = it1.next();
                 if (!(vsechnapole.contains(poled))) vsechnapole.add(poled);
             }          
         }
         ArrayList<Pole> uzemipuvodni = new ArrayList<Pole>();
         uzemipuvodni.add(uzemi);
         vsechnapole.removeAll(uzemipuvodni);    
         vsechnapole.removeAll(okolnipole);
       }
       return vsechnapole;
    } 
     
      public ArrayList<Pole> vratOkolniPoleRadiusTri(Pole uzemi) {
       ArrayList<Pole> okolnipole = vratOkolniPoleRadiusJedna(uzemi);
       ArrayList<Pole> vsechnapole = new ArrayList<Pole>();
       if (uzemi!=null) {
       vsechnapole.add(najdiPole(uzemi.getX(), uzemi.getY()));
         for (Iterator<Pole> it = okolnipole.iterator(); it.hasNext();) {
             Pole polea = it.next();
             vsechnapole.add(polea);
         }
         for (Iterator<Pole> it = okolnipole.iterator(); it.hasNext();) {
             Pole polea = it.next();
             ArrayList<Pole> pom = vratOkolniPoleRadiusJedna(polea);
             
             for (Iterator<Pole> it1 = pom.iterator(); it1.hasNext();) {
                 Pole poled = it1.next();
                 ArrayList<Pole> pomx = vratOkolniPoleRadiusJedna(poled);
                 if (!(vsechnapole.contains(poled))) vsechnapole.add(poled);
                 for (Iterator<Pole> it2 = pomx.iterator(); it2.hasNext();) {
                     Pole polex = it2.next();
                     if (!(vsechnapole.contains(polex))) vsechnapole.add(polex);
                 }
             }          
         }
         ArrayList<Pole> uzemipuvodni = new ArrayList<Pole>();
         uzemipuvodni.add(uzemi);
         vsechnapole.removeAll(uzemipuvodni);    
         vsechnapole.removeAll(okolnipole);
         vsechnapole.removeAll(vratOkolniPoleRadiusDva(uzemi));
       }
       return vsechnapole;
    } 
      
      public ArrayList<Pole> hledejPole(ArrayList<Pole> uzemi, String typ, int budova, int obsazeno, int hracx, int hracnatahux) {
       ArrayList<Pole> seznampoli = new ArrayList<Pole>();
       if (uzemi!=null) {
           try {
           for (Iterator<Pole> it = uzemi.iterator(); it.hasNext();) {
               Pole polea = it.next();
               if ((typ.equals("budova"))&&(polea.getBudova()==budova)) {
                   if (polea.getHrac()==hracx) seznampoli.add(polea);
                   if (hracx==5) seznampoli.add(polea);
                   if ((hracx==6)&&(polea.getHrac()!=hracnatahux)&&(polea.getHrac()!=0)) seznampoli.add(polea);                   
               }
               if (typ.equals("vsechny")) {
                   if (hracx==5) seznampoli.add(polea);
                   if ((hracx==6)&&(polea.getHrac()!=hracnatahux)&&(polea.getHrac()!=0)) seznampoli.add(polea); 
                   if ((hracx==7)&&(polea.getHrac()!=hracnatahux)) seznampoli.add(polea);                    
               }        
               
           }
           } catch(java.lang.NullPointerException e) { }
       }
           return seznampoli; 
       } 
      
     public int najdiArmaduProtivnika (Pole puvodni, int okoli, int hracx) {
       ArrayList<Pole> uzemi = vratOkolniPoleRadiusJedna(puvodni);
       if (okoli==2) uzemi = vratOkolniPoleRadiusJedna(puvodni);
       if (okoli==3) uzemi = vratOkolniPoleRadiusJedna(puvodni);
       int okoliarmada = 0;
       for (Iterator<Pole> it = uzemi.iterator(); it.hasNext();) {
           Pole polea = it.next();
           if (polea.getHrac()!=hracx) {okoliarmada = okoliarmada + polea.getArmada();} 
       }
      return okoliarmada;         
   } 
       
   public int najdiUdajeOHraci(String hledat, int obsazeno, int hrachledat) {
        int celkem = 0;
        Hrajici hracx = najdiHrace(hrachledat);
        for (Iterator<Pole> it = pole.iterator(); it.hasNext();) {
            Pole poleab = it.next();
            if (poleab.getHrac()==hrachledat) {
            if ((hledat.equals("obili"))&&(obsazeno==0)&&(poleab.getCloveks()==0)&&(poleab.getTyp()==1)) celkem += poleab.getPocet()+hracx.vratPokrok(1);
            if ((hledat.equals("obili"))&&(obsazeno==1)&&(poleab.getCloveks()==1)&&(poleab.getTyp()==1)) celkem += poleab.getPocet()+hracx.vratPokrok(1);
            if ((hledat.equals("obili"))&&(obsazeno==2)&&(poleab.getTyp()==1)) celkem += poleab.getPocet()+hracx.vratPokrok(1);
            if ((hledat.equals("ropa"))&&(obsazeno==0)&&(poleab.getCloveks()==0)&&(poleab.getTyp()==2)) celkem += poleab.getPocet()+hracx.vratPokrok(2);
            if ((hledat.equals("ropa"))&&(obsazeno==1)&&(poleab.getCloveks()==1)&&(poleab.getTyp()==2)) celkem += poleab.getPocet()+hracx.vratPokrok(2);
            if ((hledat.equals("ropa"))&&(obsazeno==2)&&(poleab.getTyp()==2)) celkem += poleab.getPocet()+hracx.vratPokrok(2);
            if ((hledat.equals("zelezo"))&&(obsazeno==0)&&(poleab.getCloveks()==0)&&(poleab.getTyp()==3)) celkem += poleab.getPocet()+hracx.vratPokrok(3);
            if ((hledat.equals("zelezo"))&&(obsazeno==1)&&(poleab.getCloveks()==1)&&(poleab.getTyp()==3)) celkem += poleab.getPocet()+hracx.vratPokrok(3);
            if ((hledat.equals("zelezo"))&&(obsazeno==2)&&(poleab.getTyp()==3)) celkem += poleab.getPocet()+hracx.vratPokrok(3);
            if ((hledat.equals("kamen"))&&(obsazeno==0)&&(poleab.getCloveks()==0)&&(poleab.getTyp()==4)) celkem += poleab.getPocet()+hracx.vratPokrok(4);
            if ((hledat.equals("kamen"))&&(obsazeno==1)&&(poleab.getCloveks()==1)&&(poleab.getTyp()==4)) celkem += poleab.getPocet()+hracx.vratPokrok(4);
            if ((hledat.equals("kamen"))&&(obsazeno==2)&&(poleab.getTyp()==4)) celkem += poleab.getPocet()+hracx.vratPokrok(4);
            if ((hledat.equals("zlato"))&&(obsazeno==0)&&(poleab.getCloveks()==0)&&(poleab.getTyp()==5)) celkem += poleab.getPocet();
            if ((hledat.equals("zlato"))&&(obsazeno==1)&&(poleab.getCloveks()==1)&&(poleab.getTyp()==5)) celkem += poleab.getPocet();
            if ((hledat.equals("zlato"))&&(obsazeno==2)&&(poleab.getTyp()==5)) celkem += poleab.getPocet();
            
            if ((hledat.equals("zakladna"))&&(obsazeno==0)&&(poleab.getClovekb()==0)&&(poleab.getBudova()==1)) celkem++;
            if ((hledat.equals("zakladna"))&&(obsazeno==1)&&(poleab.getClovekb()==1)&&(poleab.getBudova()==1)) celkem++;
            if ((hledat.equals("zakladna"))&&(obsazeno==2)&&(poleab.getBudova()==1))  celkem++;
            if ((hledat.equals("laborator"))&&(obsazeno==0)&&(poleab.getClovekb()==0)&&(poleab.getBudova()==2)) celkem++;
            if ((hledat.equals("laborator"))&&(obsazeno==1)&&(poleab.getClovekb()==1)&&(poleab.getBudova()==2)) celkem++;
            if ((hledat.equals("laborator"))&&(obsazeno==2)&&(poleab.getBudova()==2))  celkem++;
            if ((hledat.equals("tovarna"))&&(obsazeno==0)&&(poleab.getClovekb()==0)&&(poleab.getBudova()==3)) celkem++;
            if ((hledat.equals("tovarna"))&&(obsazeno==1)&&(poleab.getClovekb()==1)&&(poleab.getBudova()==3)) celkem++;
            if ((hledat.equals("tovarna"))&&(obsazeno==2)&&(poleab.getBudova()==3)) celkem++;
            if ((hledat.equals("mesto"))&&(obsazeno==0)&&(poleab.getClovekb()==0)&&(poleab.getBudova()==4)) celkem++;
            if ((hledat.equals("mesto"))&&(obsazeno==1)&&(poleab.getClovekb()==1)&&(poleab.getBudova()==4)) celkem++;
            if ((hledat.equals("mesto"))&&(obsazeno==2)&&(poleab.getBudova()==4)) {celkem++;}
            if ((hledat.equals("katedrala"))&&(obsazeno==0)&&(poleab.getClovekb()==0)&&(poleab.getBudova()==5)) celkem++;
            if ((hledat.equals("katedrala"))&&(obsazeno==1)&&(poleab.getClovekb()==1)&&(poleab.getBudova()==5)) celkem++;
            if ((hledat.equals("katedrala"))&&(obsazeno==2)&&(poleab.getBudova()==5)) celkem++; 
            if ((hledat.equals("clovek"))&&(obsazeno==1)&&(poleab.getCloveks()==1)) { celkem = celkem + 1; }
            if ((hledat.equals("clovek"))&&(obsazeno==1)&&(poleab.getClovekb()==1)) { celkem = celkem + 1; }
            if ((hledat.equals("clovek"))&&(obsazeno==0)&&(poleab.getCloveks()==0)) celkem++;
            if ((hledat.equals("clovek"))&&(obsazeno==0)&&(poleab.getClovekb()==0)) celkem++;
            if ((hledat.equals("clovek"))&&(obsazeno==2)) celkem = celkem + 1;
            if ((hledat.equals("clovek"))&&(obsazeno==2)&&(poleab.getBudova()!=0)) celkem = celkem + 1;
            
            if (hledat.equals("armada")) celkem = celkem + poleab.getArmada();
            }
        }
        return celkem;
    }


    public ArrayList<Pole> getPole() {
        return pole;
    }

    public int getHracnatahu() {
        return hracnatahu;
    }

    public void setHracnatahu(int hracnatahu) {
        this.hracnatahu = hracnatahu;
    }

    public String getAdresa() {
        return adresa;
    }

    public int getKolo() {
        return kolo;
    }

    public void setKolo(int kolo) {
        this.kolo = kolo;
    }

    public int[] getCenabudov() {
        return cenabudov;
    }

    public int[] getCenapokroku() {
        return cenapokroku;
    }
    
}

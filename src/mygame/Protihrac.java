package mygame;

import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Protihrac extends Hrac implements Hrajici {
    public ArrayList<Akce> mozneakce = new ArrayList<Akce>();
    private Vyhledavac vyhledavac = null;
  
    public Protihrac(int hrac, Plocha kresli, ArrayList<Pole> pole, Vyhledavac vyhledavac, ArrayList<Historie> historie, Node rootNode, Hra hra) {
        super(hrac, kresli, pole, vyhledavac, historie, rootNode, hra);
        this.vyhledavac = vyhledavac;
    }
    
    public Protihrac() {
        
    }
    
    public Double max(Double... hodnoty) {
        Double vrat = null;
        for (Double hodnota : hodnoty) { if (vrat == null || (hodnota != null && hodnota > vrat)) vrat = hodnota; }
        return vrat;
    }
             
    public double maxmin(double min, double hodnota, double max) {
        double vysledek = hodnota;
        if (hodnota>max) vysledek=max;
        if (hodnota<min) vysledek=min; 
        return vysledek;
     }
      
    public void zjistiMozneAkce() {
        mozneakce = new ArrayList<Akce>();
        int lidemaximum = vyhledavac.najdiUdajeOHraci("mesto", 1, getHrac())*7+7;
        int lide = vyhledavac.najdiUdajeOHraci("clovek", 1, getHrac());
        int celkemobili = vyhledavac.najdiUdajeOHraci("obili", 1, getHrac())+getObili();
        int celkemzeleza = vyhledavac.najdiUdajeOHraci("zelezo", 1, getHrac())+getZelezo()-vyhledavac.najdiUdajeOHraci("tovarna", 1, getHrac());        
        ArrayList<Pole> polehrace = vyhledavac.najdiPolePatriciHraci(getHrac());
        for (Iterator<Pole> it = polehrace.iterator(); it.hasNext();) {
            Pole polea = it.next();
            if ((getZelezo()>=5)&&(getAkce()>0)) { mozneakce.add(new Akce("kuparmadu", polea)); }
            if ((polea.getArmada()>0)&&(getAkce()<1)) { mozneakce.add(new Akce("znicjednotku", polea)); }  
            ArrayList<Pole> okolnipole = vyhledavac.vratOkolniPoleRadiusJedna(polea);
            for (Iterator<Pole> it1 = okolnipole.iterator(); it1.hasNext();) {
                Pole poleb = it1.next();
                if (poleb.getHrac()!=getHrac()) {
                  for (int i = 1; i <= polea.getArmada(); i++) { if ((polea.getArmada()>0)&&(getAkce()>0)) mozneakce.add(new Akce("utok", polea, poleb, i)); }
                } else {
                for (int i = 1; i <= polea.getArmada(); i++) { 
                if ((polea.getArmada()>0)&&(getAkce()>0)) {mozneakce.add(new Akce("presun", polea, poleb, i));}  
                } 
                }
            }
            if ((polea.getCloveks()==0)&&(getAkce()<1)) {
               if ((lide<lidemaximum)&&((lide<celkemobili)||(polea.getTyp()==1))) mozneakce.add(new Akce("cloveks", polea, 1, 0));
            }
             if ((polea.getClovekb()==0)&&(polea.getBudova()>0)&&(getAkce()<1)) {
                if (((lide<lidemaximum)||(polea.getBudova()==4))&&(lide<celkemobili)) {
                    if (((polea.getBudova()==3)&&(celkemzeleza>0))||(polea.getBudova()!=3)) mozneakce.add(new Akce("clovekb", polea, 1, 1));
                }
                
             }
            
          if (getAkce()>0) {
            if (polea.getBudova()==0) { 
               if (vyhledavac.najdiHrace(getHrac()).getKamen()>=5) mozneakce.add(new Akce("postav", polea, 1));
               if (vyhledavac.najdiHrace(getHrac()).getKamen()>=10) mozneakce.add(new Akce("postav", polea, 2));
               if (vyhledavac.najdiHrace(getHrac()).getKamen()>=15) mozneakce.add(new Akce("postav", polea, 3));
               if (vyhledavac.najdiHrace(getHrac()).getKamen()>=20) mozneakce.add(new Akce("postav", polea, 4));
               if (vyhledavac.najdiHrace(getHrac()).getKamen()>=40) mozneakce.add(new Akce("postav", polea, 5));
            } else { mozneakce.add(new Akce("zbourat", polea)); }
            
             }
        }
        if (getAkce()>0) {
            for (int i = 1; i <= 7; i++) { if ((getVeda()>=getCenapokroku()[i-1])&&(vratPokrok(i)==0)) { mozneakce.add(new Akce("pokrok", i)); }   }
            mozneakce.add(new Akce("surovina", 1));
            mozneakce.add(new Akce("surovina", 2));
            mozneakce.add(new Akce("surovina", 3));
            mozneakce.add(new Akce("surovina", 4)); 
        }
    }
    
    public void zahrajAkci() {
       Akce novaakce = mozneakce.get(0);
       if (novaakce.getPriorita()>0) {
       if (novaakce.getTyp().equals("kuparmadu")) kupArmadu(mozneakce.get(0).getPuvodni());
       if (novaakce.getTyp().equals("utok")) {novaakce.getPuvodni().setPoslat(novaakce.getJednotky()); zautoc(novaakce.getPuvodni(),novaakce.getCil()); }    
       if (novaakce.getTyp().equals("presun")) {novaakce.getPuvodni().setPoslat(novaakce.getJednotky()); presunJednotky(novaakce.getPuvodni(),novaakce.getCil()); }     
       if (novaakce.getTyp().equals("clovekb")) {pridejClovekaDoBudovy(novaakce.getPuvodni()); }     
       if (novaakce.getTyp().equals("clovekbzrus")) {oddelejClovekaZBudovy(novaakce.getPuvodni()); }     
       if (novaakce.getTyp().equals("cloveks")) {pridejClovekaNaSuroviny(novaakce.getPuvodni()); }     
       if (novaakce.getTyp().equals("clovekszrus")) {oddelejClovekaZeSurovin(novaakce.getPuvodni()); }     
       if (novaakce.getTyp().equals("postav")) {postavBudovu(novaakce.getPuvodni(),novaakce.getBudova()); }     
       if (novaakce.getTyp().equals("pokrok")) {vynalezniPokrok(novaakce.getPokrok()); }     
       if (novaakce.getTyp().equals("znicjednotku")) {znicArmadu(novaakce.getPuvodni()); }     
       if (novaakce.getTyp().equals("surovina")) {ziskejSuroviny(novaakce.getSurovina()); }  
       if (novaakce.getTyp().equals("zbourat")) {zbourejBudovu(novaakce.getPuvodni()); }   
        }        
     }
 
    
    public void zkontroluj() {
        int lidemaximum = vyhledavac.najdiUdajeOHraci("mesto", 1, getHrac())*7+7;
        int lide = vyhledavac.najdiUdajeOHraci("clovek", 1, getHrac());
        int armada = vyhledavac.najdiUdajeOHraci("armada", 1, getHrac());
        int celkemropy = vyhledavac.najdiUdajeOHraci("ropa", 1, getHrac())+vyhledavac.najdiHrace(getHrac()).getRopa();
        int tovarny = vyhledavac.najdiUdajeOHraci("tovarna", 1, getHrac());
        int celkemzeleza = vyhledavac.najdiUdajeOHraci("zelezo", 1, getHrac())+vyhledavac.najdiHrace(getHrac()).getZelezo();
        int celkemjidla = vyhledavac.najdiUdajeOHraci("obili", 1, getHrac())+vyhledavac.najdiHrace(getHrac()).getObili();
        if (!((lide<=lidemaximum)&&(armada<=celkemropy)&&(tovarny<=celkemzeleza)&&(celkemjidla>=lide))) {
 
            for (Iterator<Akce> it = mozneakce.iterator(); it.hasNext();) {
            Akce akcea = it.next();
            if (akcea.getTyp().equals("clovekszrus")) {
                if ((lide>lidemaximum)&&(akcea.getPuvodni().getTyp()!=1)) {akcea.setPriorita(4);}
                if ((lide>lidemaximum)&&(akcea.getPuvodni().getTyp()==1)) {akcea.setPriorita(3);}              
                if ((lide>celkemjidla)&&(akcea.getPuvodni().getTyp()!=1)) {akcea.setPriorita(4);}
            
            }
            if (akcea.getTyp().equals("clovekbzrus")) {
                if ((akcea.getBudova()!=4)&&((celkemjidla<lide))) {akcea.setPriorita(3); }
                if ((akcea.getBudova()!=4)&&(lide>lidemaximum)) {akcea.setPriorita(3); }             
                if ((akcea.getBudova()==4)&&(((lide+7)<=lidemaximum)&&(celkemjidla<=lide))) {akcea.setPriorita(3);}  
                if ((akcea.getBudova()==3)&&(tovarny>celkemzeleza)) {akcea.setPriorita(5);}                  
            }
            if (akcea.getTyp().equals("znicjednotku")) { if (armada>celkemropy) {akcea.setPriorita(7);} else {akcea.setPriorita(-1);} }
                
             }
            Collections.shuffle(mozneakce);
            Collections.sort(mozneakce, new Akce());
            zahrajAkci();
    }
    }
    
}

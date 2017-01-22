package mygame;

import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class AlfaBetaGama extends Protihrac implements Hrajici  {
    private int zruslidi = 1;
    private String typhrace[] = null;
    private double produkcesurovin[] = {6.0f,6.0f,4.0f,5.0f,6.0f};
    private String nazev = "";
    private double genom_koefutok = 1;
    private double genom_koefpresun = 1;
    private double genom_koefbudova = 1;
    private double genom_koefnakup = 1;
    private double genom_koefpokrok = 1;
    private double genom_koefsuroviny = 1;
    private double genom_prioritaobili = 0.05f;
    private double genom_priortiaropa = 0.05f;
    private double genom_prioritazelezo = 0.1f;
    private double genom_prioritakamen = 0.005f;
    private double genom_koefzakladna = 1;
    private double genom_koeflaborator = 1;
    private double genom_koeftovarna = 1;
    private double genom_surovinaobili = 1;
    private double genom_surovinaropa = 1;
    private double genom_surovinazelezo = 1;
    private double genom_surovinakamen = 1;
    private double genom_utokstred = 0.05;
    private double genom_utokdalsipole = 0.05;
    private double genom_utokcizihrac = 0.1;
    private double genom_utoksilnejsihrac = 0.1;
    private double genom_utokstrategickepole = 0;
    private double genom_pjidlo = 0;
    private double genom_propa = 0;
    private double genom_pzelezo = 0;
    private double genom_pkamen = 0;
    private double genom_putok = 0;
    private double genom_pobrana = 0;
    private double genom_pnahoda = 0;
    private double genom_armadanicjednotky = 20;
    private double genom_nakuparmadaobrana = 7;
    private Zaznamy zaznamy = new Zaznamy();
    ArrayList<Policko> mapapoli = new ArrayList<Policko>();
    private double[] gama_moznosurovin = {0,0,0,0,0};
    private double[] gama_budovy = {0,0,0,0,0};
    private double[] gama_pokroky = {0,0,0,0,0,0,0};
    private double[] gama_celkemsurovin = {0,0,0,0};
    private double gama_armada = 0;
    private double gama_kultura = 0;
    private double gama_veda = 0;
    double[] prioritaakce = {0,0,0,0,0,0}; //utok, přesun armády, stavba budov, nákup jednotek, pokrok, získat suroviny
    private Vyhledavac vyhledavac = null;
    private Hra hra = null;

    
    public AlfaBetaGama(int hrac, Plocha kresli, ArrayList<Pole> pole, Vyhledavac tah, ArrayList<Historie> historie, Node rootNode, Hra hra, String typhracex) {
        super(hrac, kresli, pole, tah, historie, rootNode, hra);
        this.vyhledavac = tah;
        this.hra = hra;
        typhrace = typhracex.split("x");
        nazev = typhrace[0];
        if ((nazev.equals("beta"))||(nazev.equals("gamabeta"))) {
        genom_koefutok = Double.parseDouble(typhrace[1]);
        genom_koefpresun = Double.parseDouble(typhrace[2]);
        genom_koefbudova = Double.parseDouble(typhrace[3]);
        genom_koefnakup = Double.parseDouble(typhrace[4]);
        genom_koefpokrok = Double.parseDouble(typhrace[5]);
        genom_koefsuroviny = Double.parseDouble(typhrace[6]);
        genom_prioritaobili = Double.parseDouble(typhrace[7]);
        genom_priortiaropa = Double.parseDouble(typhrace[8]);
        genom_prioritazelezo = Double.parseDouble(typhrace[9]);
        genom_prioritakamen = Double.parseDouble(typhrace[10]);
        genom_koefzakladna = Double.parseDouble(typhrace[11]);
        genom_koeflaborator = Double.parseDouble(typhrace[12]);
        genom_koeftovarna = Double.parseDouble(typhrace[13]);
        genom_surovinaobili = Double.parseDouble(typhrace[14]);
        genom_surovinaropa = Double.parseDouble(typhrace[15]);
        genom_surovinazelezo = Double.parseDouble(typhrace[16]);
        genom_surovinakamen = Double.parseDouble(typhrace[17]);
        genom_utokstred = Double.parseDouble(typhrace[18]);
        genom_utokdalsipole = Double.parseDouble(typhrace[19]);
        genom_utokcizihrac = Double.parseDouble(typhrace[20]);
        genom_utoksilnejsihrac = Double.parseDouble(typhrace[21]);
        genom_utokstrategickepole = Double.parseDouble(typhrace[22]);
        genom_pjidlo = Double.parseDouble(typhrace[23]);
        genom_propa = Double.parseDouble(typhrace[24]);
        genom_pzelezo = Double.parseDouble(typhrace[25]);
        genom_pkamen = Double.parseDouble(typhrace[26]);
        genom_putok = Double.parseDouble(typhrace[27]);
        genom_pobrana = Double.parseDouble(typhrace[28]);
        genom_pnahoda = Double.parseDouble(typhrace[28]);
        genom_armadanicjednotky = Double.parseDouble(typhrace[30]);
        genom_nakuparmadaobrana = Double.parseDouble(typhrace[31]);
        }
        if (nazev.equals("gamabeta")) nazev="gama";
        if (nazev.equals("gama")) genom_koefutok=1.0f;
    }

    public double zjistiPriorituOblasti(Pole uzemi, int hracx) {
        int oblast = uzemi.getOblast();
        int prioritaoblast = 0;
        if (oblast==14) prioritaoblast=5;
        if ((oblast>=10)&&(oblast<=13)) prioritaoblast=4;
        if ((oblast>=6)&&(oblast<=9)&&(oblast!=(hracx+5))) prioritaoblast=3;
        if (oblast==(hracx+5)) prioritaoblast=2;       
        if ((oblast>=1)&&(oblast<=4)&&(oblast!=hracx)) prioritaoblast = 1;
        if (oblast==hracx) prioritaoblast = 0;    
        return prioritaoblast;
    }

    public double zjistiHodnotuPole(Pole uzemi, double[] surovinyp) {
           double hodnotacil = uzemi.getBudova()/2+2.5f;
           if (uzemi.getBudova()==3) hodnotacil=hodnotacil+1.0f;
           if (uzemi.getTyp()==1) { hodnotacil = hodnotacil+1.0f*surovinyp[0]*(uzemi.getPocet()/produkcesurovin[0]); }
           if (uzemi.getTyp()==2) { hodnotacil = hodnotacil+1.0f*surovinyp[1]*(uzemi.getPocet()/produkcesurovin[1]); }
           if (uzemi.getTyp()==3) { hodnotacil = hodnotacil+1.0f*surovinyp[2]*(uzemi.getPocet()/produkcesurovin[2]); }
           if (uzemi.getTyp()==4) { hodnotacil = hodnotacil+1.0f*surovinyp[3]*(uzemi.getPocet()/produkcesurovin[3]); }
           if (uzemi.getTyp()==5) { hodnotacil = hodnotacil+1.0f*surovinyp[4]*(uzemi.getPocet()/produkcesurovin[4]); } 
           return hodnotacil;
    }

       public double spocitejKoeficientUtoku(double utok, double obrana) {
           double koeficient = 0;
           double rozdil = utok-obrana;
           if (rozdil==0) koeficient=0.1f;
           if (rozdil==1) koeficient=0.25f;
           if (rozdil==2) koeficient=0.5f;
           if (rozdil>2) koeficient=1.0f;
           return koeficient;
      } 
       
       public double spocitejNepratelskaPoleVOblasti(int oblast) {
           double nepratelskapole=0;
           for (Iterator<Pole> it = getPole().iterator(); it.hasNext();) {
               Pole polea = it.next();
               if ((polea.getHrac()!=getHrac())&&(polea.getHrac()!=0)) nepratelskapole++;
           }
           return nepratelskapole;
       }
       
       public double spocitejMojePoleVOblasti(int oblast) {
           double mojepole=0;
           for (Iterator<Pole> it = getPole().iterator(); it.hasNext();) {
               Pole polea = it.next();
               if (polea.getHrac()==getHrac()) mojepole++;
           }
           return mojepole;
       }
       
      public double jeStrategickePole(Pole uzemi) {
            double typu = 0;
            ArrayList<Pole> polehrac = vyhledavac.vratOkolniPoleRadiusJedna(uzemi);
            for (Iterator<Pole> it = polehrac.iterator(); it.hasNext();) {
                Pole polex = it.next();
                if ((polex.getOblast()!=uzemi.getOblast())&&(typu==0)) typu = 1;
                if (polex.getOblast()<uzemi.getOblast()) typu = 2;
            }
            return typu;
        }
       
       public double jeObrannePole(Pole uzemi) {
           double oblasti = 0;
           ArrayList<Integer> sousednioblasti = new ArrayList<Integer>();
           ArrayList<Pole> okolnipole = vyhledavac.vratOkolniPoleRadiusJedna(uzemi);
           for (Iterator<Pole> it = okolnipole.iterator(); it.hasNext();) {
               Pole polea = it.next();
               int oblast = polea.getOblast();
               if (!(sousednioblasti.contains(oblast))) sousednioblasti.add(oblast);
           }
           for (Iterator<Integer> it = sousednioblasti.iterator(); it.hasNext();) {
               Integer oblast = it.next();
               if ((spocitejNepratelskaPoleVOblasti(oblast)==0)&&(spocitejMojePoleVOblasti(oblast)>3)) oblasti++;
           }
           return oblasti;
       } 
       
       public double spocitejMaxKultury() {
           double kultura = 0;
           for (Iterator<Hrajici> it = hra.getHraci().iterator(); it.hasNext();) {
               Hrajici hraca = it.next();
               if (hraca.getKultura()>kultura) kultura=hraca.getKultura();
           }
           return kultura;
       } 

       public double[] vypocitejPotrebnostSurovin(boolean lide) {
            double moznomaxlidi = vyhledavac.najdiUdajeOHraci("mesto", 2, getHrac())*7+7;    
            double moznolidi = vyhledavac.najdiUdajeOHraci("clovek", 2, getHrac());  
            double odectijidlo = Math.min(moznomaxlidi,moznolidi);
            double armada = vyhledavac.najdiUdajeOHraci("armada", 1, getHrac());
            double moznotovarny = vyhledavac.najdiUdajeOHraci("tovarna", 2, getHrac());  
            double moznozeleza = vyhledavac.najdiUdajeOHraci("zelezo", 2, getHrac())+getZelezo();
            double moznokamene = vyhledavac.najdiUdajeOHraci("kamen", 2, getHrac())+getKamen();  
            double moznojidla = vyhledavac.najdiUdajeOHraci("obili", 2, getHrac())+getObili();
            double moznoropy = vyhledavac.najdiUdajeOHraci("ropa", 2, getHrac())+getRopa();  
            double potrebajidla = moznojidla-odectijidlo;
            double potrebaropy = moznoropy-armada;
            double potrebazeleza = moznozeleza-moznotovarny;
            double[] surovinyp = {0,0,0,0,0};
            if ((nazev.equals("gama"))&&(lide==false)) {
            surovinyp[0]=maxmin(1, 3.0f+(gama_moznosurovin[0]-vyhledavac.najdiUdajeOHraci("obili", 2, getHrac()))*0.1f, 5);
            surovinyp[1]=maxmin(1, 3.0f+(gama_moznosurovin[1]-vyhledavac.najdiUdajeOHraci("ropa", 2, getHrac()))*0.1f, 5);
            surovinyp[2]=maxmin(1, 3.0f+(gama_moznosurovin[2]-vyhledavac.najdiUdajeOHraci("zelezo", 2, getHrac()))*0.1f, 5);
            surovinyp[3]=maxmin(1, 3.0f+(gama_moznosurovin[3]-vyhledavac.najdiUdajeOHraci("kamen", 2, getHrac()))*0.1f, 5);
            } else { 
            surovinyp[0]=maxmin(1, 4.0f-potrebajidla*genom_prioritaobili, 5);
            surovinyp[1]=maxmin(1, 4.0f-potrebaropy*genom_priortiaropa, 5);
            surovinyp[2]=maxmin(1, 4.0f-potrebazeleza*genom_prioritazelezo, 5);
            surovinyp[3]=maxmin(1, 5.0f-moznokamene*genom_prioritakamen, 5);
              }
            surovinyp[4]=max(surovinyp[0],surovinyp[1],surovinyp[2],surovinyp[3]);
            return surovinyp;
       }
       
       public double spocitejMaximalniOhrozeni(Pole uzemi) {
           double maxutok = 0;
           double polenepratel = vyhledavac.hledejPole(vyhledavac.vratOkolniPoleRadiusJedna(uzemi), "vsechny", 0, 0, 6, getHrac()).size();
           double polenepratelb = vyhledavac.hledejPole(vyhledavac.vratOkolniPoleRadiusDva(uzemi), "vsechny", 0, 0, 6, getHrac()).size();
           if (polenepratelb>0) maxutok=1;
           if (polenepratel>0) maxutok=2;
                    ArrayList<Pole> vetsiokoli = vyhledavac.vratOkolniPoleRadiusDva(uzemi);
                    ArrayList<Pole> velkeokoli = vyhledavac.vratOkolniPoleRadiusTri(uzemi);
                    for (Iterator<Pole> it1 = velkeokoli.iterator(); it1.hasNext();) {
                        Pole poleb = it1.next();
                        if ((poleb.getHrac()!=getHrac())&&(spocitejUtok(poleb, poleb.getArmada())>maxutok)) maxutok=spocitejUtok(poleb, poleb.getArmada());
                    }
                    for (Iterator<Pole> it1 = vetsiokoli.iterator(); it1.hasNext();) {
                        Pole poleb = it1.next();
                            if ((poleb.getHrac()!=getHrac())&&(spocitejUtok(poleb, poleb.getArmada())-3>maxutok)) maxutok=spocitejUtok(poleb, poleb.getArmada())-3;
                    }
                    for (Iterator<Pole> it1 = velkeokoli.iterator(); it1.hasNext();) {
                        Pole poleb = it1.next();
                        if ((poleb.getHrac()!=getHrac())&&(spocitejUtok(poleb, poleb.getArmada())-6>maxutok)) maxutok=spocitejUtok(poleb, poleb.getArmada())-6;
                    }
                    return maxutok;
       }
         
       public double spocitejUtok(Pole uzemi, int jednotky) {
           double utok = jednotky+vyhledavac.najdiHrace(uzemi.getHrac()).vratPokrok(5)+vyhledavac.najdiHrace(uzemi.getHrac()).vratPokrok(7)*0.5f;
           return utok;
       }
              
      public double spocitejObranu(Pole uzemi, boolean cizi) {
           double obrana = uzemi.getArmada()+vyhledavac.najdiHrace(uzemi.getHrac()).vratPokrok(6)+vyhledavac.najdiHrace(uzemi.getHrac()).vratPokrok(7)*0.5f;
           if ((uzemi.getBudova()==1)&&(uzemi.getClovekb()==1)&&(cizi==true)) obrana = obrana + 4;
           if ((uzemi.getBudova()==1)&&(cizi==false)) obrana = obrana + 4;           
           if (uzemi.getHrac()==0) obrana = obrana + 1;
           return obrana;
       }
      
      public double spocitejKoeficientOhrozeni(Pole uzemi) {
           double ohrozeni = vyhledavac.najdiArmaduProtivnika(uzemi, 1, getHrac())+vyhledavac.najdiArmaduProtivnika(uzemi, 2, getHrac())*0.5f+vyhledavac.najdiArmaduProtivnika(uzemi, 3, getHrac())*0.25f;
           ohrozeni=maxmin(0, ohrozeni*0.04f, 1);
           return ohrozeni;
       }

      
    @Override
    public void zahraj() {
        if (zruslidi==1) {
            for (Iterator<Pole> it = vyhledavac.najdiPolePatriciHraci(getHrac()).iterator(); it.hasNext();) {
                Pole poleabc = it.next();
                poleabc.setCloveks(0);
                poleabc.setClovekb(0); 
            }
            zruslidi = 0;
            
            if (nazev.equals("gama")) {
                prioritaakce = zaznamy.spocitejPravdepodobnostAkce(hra.getKolo());           
                mapapoli = zaznamy.vratInfluencniMapu(hra.getKolo(), getHrac());
                double[] gama_koneckola = zaznamy.spocitejVelicinyZaKolo(hra.getKolo());
                gama_armada=gama_koneckola[2];
                gama_kultura = gama_koneckola[0];
                gama_veda = gama_koneckola[1];
                gama_celkemsurovin[0] = gama_koneckola[3];
                gama_celkemsurovin[1] = gama_koneckola[4];
                gama_celkemsurovin[2] = gama_koneckola[5];
                gama_celkemsurovin[3] = gama_koneckola[6];
                gama_moznosurovin[0] = gama_koneckola[7];
                gama_moznosurovin[1] = gama_koneckola[8];
                gama_moznosurovin[2] = gama_koneckola[9];
                gama_moznosurovin[3] = gama_koneckola[10];
                gama_moznosurovin[4] = gama_koneckola[11];
                gama_budovy[0] = gama_koneckola[12];
                gama_budovy[1] = gama_koneckola[13];
                gama_budovy[2] = gama_koneckola[14];
                gama_budovy[3] = gama_koneckola[15];
                gama_budovy[4] = gama_koneckola[16];
                gama_pokroky[0] = gama_koneckola[17];
                gama_pokroky[1] = gama_koneckola[18];
                gama_pokroky[2] = gama_koneckola[19];
                gama_pokroky[3] = gama_koneckola[20];
                gama_pokroky[4] = gama_koneckola[21];
                gama_pokroky[5] = gama_koneckola[22];
                gama_pokroky[6] = gama_koneckola[23];
            }
            
        }
        zjistiMozneAkce();
        double moznomaxlidi = vyhledavac.najdiUdajeOHraci("mesto", 2, getHrac())*7+7;       
        double moznolidi = vyhledavac.najdiUdajeOHraci("clovek", 2, getHrac());  
        double pocetpoli = vyhledavac.najdiPolePatriciHraci(getHrac()).size();
        double armada = vyhledavac.najdiUdajeOHraci("armada", 1, getHrac());
        double celkemropy = vyhledavac.najdiUdajeOHraci("ropa", 1, getHrac())+getRopa();
        double moznotovarny = vyhledavac.najdiUdajeOHraci("tovarna", 2, getHrac());       
        double moznolaborator = vyhledavac.najdiUdajeOHraci("laborator", 2, getHrac());  
        double celkemzeleza = vyhledavac.najdiUdajeOHraci("zelezo", 1, getHrac())+getZelezo();
        double moznozeleza = vyhledavac.najdiUdajeOHraci("zelezo", 2, getHrac())+getZelezo();
        double moznokamene = vyhledavac.najdiUdajeOHraci("kamen", 2, getHrac())+getKamen();  
        double moznojidla = vyhledavac.najdiUdajeOHraci("obili", 2, getHrac())+getObili();
        double moznoropy = vyhledavac.najdiUdajeOHraci("ropa", 2, getHrac())+getRopa();  
        double odectijidlo = Math.min(moznomaxlidi,moznolidi);
        double ropapospotrebe = Math.max(moznoropy-armada,1);
        for (Iterator<Akce> it = mozneakce.iterator(); it.hasNext();) {
            Akce akce = it.next();
            double p = 0;
            double cizipole = vyhledavac.hledejPole(vyhledavac.vratOkolniPoleRadiusJedna(akce.getPuvodni()), "vsechny", 0, 0, 7, getHrac()).size();
            double cizipoleb = vyhledavac.hledejPole(vyhledavac.vratOkolniPoleRadiusDva(akce.getPuvodni()), "vsechny", 0, 0, 7, getHrac()).size();
            double polenepratel = vyhledavac.hledejPole(vyhledavac.vratOkolniPoleRadiusJedna(akce.getPuvodni()), "vsechny", 0, 0, 6, getHrac()).size(); 
            double polenepratelb = vyhledavac.hledejPole(vyhledavac.vratOkolniPoleRadiusDva(akce.getPuvodni()), "vsechny", 0, 0, 6, getHrac()).size(); 
            double polenepratelc = vyhledavac.hledejPole(vyhledavac.vratOkolniPoleRadiusTri(akce.getPuvodni()), "vsechny", 0, 0, 6, getHrac()).size(); 
            double strategickepole = jeStrategickePole(akce.getPuvodni());
            double prioritaoblast = 0;            
            if (akce.getPuvodni()!=null) prioritaoblast=zjistiPriorituOblasti(akce.getPuvodni(), getHrac());
            double[] surovinyp = vypocitejPotrebnostSurovin(false);
            
            if (getAkce()<1) {
                
            if (akce.getTyp().equals("znicjednotku")) { // Akce zničit jednotku
                if (armada>celkemropy) {
                    p=p+0.1f+1-spocitejKoeficientOhrozeni(akce.getPuvodni());
                if (cizipole==0) {
                    if (akce.getPuvodni().getBudova()!=3) p=p+0.1f;
                    if ((akce.getPuvodni().getBudova()==3)&&(akce.getPuvodni().getArmada()>10)) p=p+0.1f;
                }
                }
                if (((polenepratel+polenepratelb+polenepratelc)==0)&&(armada>genom_armadanicjednotky)&&((cizipole+cizipoleb)==0)&&(akce.getPuvodni().getArmada()<3)) p=p+0.05f; 
            }
      
            if (akce.getTyp().equals("cloveks")) { // Akce Umísti člověka na surovinové pole
                if (nazev.equals("gama")) surovinyp=vypocitejPotrebnostSurovin(true);
                if (akce.getPuvodni().getTyp()==1) p=p+surovinyp[0]*((double)(akce.getPuvodni().getPocet())/6.0f);
                if (akce.getPuvodni().getTyp()==2) p=p+surovinyp[1]*((double)(akce.getPuvodni().getPocet())/6.0f);
                if (akce.getPuvodni().getTyp()==3) { p=p+surovinyp[2]*(double)((akce.getPuvodni().getPocet())/4.0f);
                   if (celkemzeleza>50) p=0; } 
                if (akce.getPuvodni().getTyp()==4) p=p+surovinyp[3]*(double)((akce.getPuvodni().getPocet())/5.0f);
                if (akce.getPuvodni().getTyp()==5) p=p+surovinyp[4]*(double)((akce.getPuvodni().getPocet())/6.0f);
            }
            
            if (akce.getTyp().equals("clovekb")) { // Akce Umísti člověka do budovy          
              if (akce.getPuvodni().getBudova()==1) {
                  if ((polenepratel+polenepratelb+polenepratelc)>0) {
                      double ohr = 0;
                      if (polenepratel>0) ohr=ohr+3.0f;
                      ohr=ohr+maxmin(0,vyhledavac.najdiArmaduProtivnika(akce.getPuvodni(), 1, getHrac())*0.5f,2);
                      if (polenepratelb>0) ohr=ohr+1.0f;
                      ohr=ohr+maxmin(0,vyhledavac.najdiArmaduProtivnika(akce.getPuvodni(), 2, getHrac())*0.5f,2);
                      if (polenepratelc>0) ohr=ohr+0.5f;
                      ohr=ohr+maxmin(0,vyhledavac.najdiArmaduProtivnika(akce.getPuvodni(), 3, getHrac())*0.25f,1.5f);
                      ohr=ohr-maxmin(0,akce.getPuvodni().getArmada()*0.5f,7.5f);
                      p=zjistiHodnotuPole(akce.getPuvodni(), surovinyp)*ohr*0.04f;
                  }
              }
              if (akce.getPuvodni().getBudova()==2) { p=p+1-maxmin(0, (double)(getSoucetPokroku())/7.0f, 1); }
              if (akce.getPuvodni().getBudova()==3) {
                  if (cizipole>0) {
                      if (polenepratel>0) p=p+3.5f;
                      else p=p+3.25f;
                  } 
                  if (cizipoleb>0) {
                      if (polenepratelb>0) p=p+0.5f-akce.getPuvodni().getArmada()*0.05f;
                      else p=p+0.25f-akce.getPuvodni().getArmada()*0.05f;
                  } 
                  double cizipolec = vyhledavac.hledejPole(vyhledavac.vratOkolniPoleRadiusTri(akce.getPuvodni()), "vsechny", 0, 0, 7, getHrac()).size();
                  if ((cizipolec>0)&&(polenepratelc>0)) p=p+0.25f-akce.getPuvodni().getArmada()*0.05f;
                  p=p-maxmin(0, (moznoropy-armada)*-0.1f, 2);
                  
              }
              if (akce.getPuvodni().getBudova()==4) {
                  double volnamesta=vyhledavac.najdiUdajeOHraci("mesto", 0, getHrac());
                  double minuszelezo=0;
                  if (celkemzeleza>50) {
                  for (Iterator<Pole> it1 = vyhledavac.najdiPolePatriciHraci(getHrac()).iterator(); it1.hasNext();) {
                      Pole polea = it1.next();
                      if (polea.getTyp()==3) minuszelezo++;
                  }
                  }
                  double lide = vyhledavac.najdiUdajeOHraci("clovek", 1, getHrac());
                  double zbyva=moznolidi-lide-volnamesta-minuszelezo;
                  if (getSoucetPokroku()==7) zbyva=zbyva-moznolaborator;
                  double maxlidi = vyhledavac.najdiUdajeOHraci("mesto", 1, getHrac())*7+7;
                  if (((maxlidi-lide)==0)&&(zbyva>0)) p=p+6;
              }
              if (akce.getPuvodni().getBudova()==5) { p=p+3.5f;
                  if ((getKultura()+vyhledavac.najdiUdajeOHraci("katedrala", 2, getHrac()))>=100) p=p+1.0f; }  
            } 
            
            }
            if (akce.getTyp().equals("utok")) { // Akce Útok
                double utok = spocitejUtok(akce.getPuvodni(), akce.getJednotky());
                double obrana = spocitejObranu(akce.getCil(),true);
                if ((jeObrannePole(akce.getPuvodni())>0)||(akce.getPuvodni().getBudova()>3)) {
                    if ((spocitejMaximalniOhrozeni(akce.getPuvodni())>1)&&(akce.getJednotky()==(akce.getPuvodni().getArmada()-spocitejMaximalniOhrozeni(akce.getPuvodni())))) p=p+0.025f;
                } 
                if (akce.getJednotky()==akce.getPuvodni().getArmada()) p=p+0.01f;
                if (akce.getCil().getHrac()!=0) p=p+genom_utokcizihrac;
                if (vyhledavac.najdiHrace(akce.getCil().getHrac()).getKultura()==spocitejMaxKultury()) p=p+genom_utoksilnejsihrac;
                if (!(nazev.equals("gama"))) {
                p=p+jeStrategickePole(akce.getCil())*genom_utokstrategickepole;
                if (Math.abs(6-akce.getCil().getX())<Math.abs(6-akce.getPuvodni().getX())) p=p+genom_utokstred;
                if (Math.abs(6-akce.getCil().getY())<Math.abs(6-akce.getPuvodni().getY())) p=p+genom_utokstred;
                }
                double hodnotacil = zjistiHodnotuPole(akce.getCil(), surovinyp);
                if (nazev.equals("gama")) hodnotacil=hodnotacil+zaznamy.najdiPolicko(mapapoli, akce.getPuvodni().getX(), akce.getPuvodni().getY()).getVlastnictvi();
                double dalsi = 0;
                double dalsiutok = Math.max(utok-obrana,0);
                ArrayList<Pole> okolnipole = vyhledavac.vratOkolniPoleRadiusJedna(akce.getPuvodni());
                for (Iterator<Pole> it1 = okolnipole.iterator(); it1.hasNext();) {
                    Pole polea = it1.next();
                    double hodnotapole = 0;
                    if (polea.getHrac()!=getHrac()) {
                        double dalsiobrana = spocitejObranu(polea,true);
                        hodnotapole=zjistiHodnotuPole(polea, surovinyp)*spocitejKoeficientUtoku(dalsiutok, dalsiobrana);
                    }
                    if (hodnotapole>dalsi) dalsi=hodnotapole;
                }
                p = p + spocitejKoeficientUtoku(utok, obrana)*hodnotacil*(1-genom_utokdalsipole)+dalsi*genom_utokdalsipole;
                if ((moznoropy-armada)<=-5) p=p+maxmin(0, spocitejObranu(akce.getCil(),true)*0.05f, 0.5f);          
                
                p=p*genom_koefutok;
                if (nazev.equals("gama")) p=p+p*maxmin(0.9, (prioritaakce[0]*0.1f)+1.0f, 1.1);
            }
            
            if (akce.getTyp().equals("kuparmadu")) { // Akce Koupit armádu
                double maxhodnota = 0;
                ArrayList<Pole> okolnipole = vyhledavac.vratOkolniPoleRadiusJedna(akce.getPuvodni());
                for (Iterator<Pole> it1 = okolnipole.iterator(); it1.hasNext();) {
                Pole polea = it1.next();
                if ((polea.getHrac()!=getHrac())&&(polea.getArmada()<1)&&(zjistiHodnotuPole(polea, surovinyp)>maxhodnota)) maxhodnota=zjistiHodnotuPole(polea, surovinyp);
                }
                if (akce.getPuvodni().getArmada()<=2) {
                    p=p+maxhodnota*0.1f;
                    double maxarmady = 0;
                    ArrayList<Pole> polehrace = vyhledavac.najdiPolePatriciHraci(getHrac());
                    for (Iterator<Pole> it1 = polehrace.iterator(); it1.hasNext();) {
                        Pole polea = it1.next();
                        if (polea.getArmada()>maxarmady) maxarmady=polea.getArmada();
                    }
                    if (maxarmady<3) p=p+1.0f+akce.getPuvodni().getArmada()*0.1f;
                }
                
                if ((jeObrannePole(akce.getPuvodni())>0)||(akce.getPuvodni().getBudova()>=3)) {
                    double rozdilutokobrana=spocitejMaximalniOhrozeni(akce.getPuvodni())-spocitejObranu(akce.getPuvodni(),false);
                    if ((rozdilutokobrana>0)&&(rozdilutokobrana<genom_nakuparmadaobrana)) p=p+maxmin(0, rozdilutokobrana*0.25f, 1.25f);
                }
                
                boolean okolnitovarna = false;
                for (Iterator<Pole> it1 = vyhledavac.vratOkolniPoleRadiusJedna(akce.getPuvodni()).iterator(); it1.hasNext();) {
                Pole polea = it1.next();
                if ((polea.getHrac()!=getHrac())&&(polea.getBudova()==3)&&((spocitejUtok(akce.getPuvodni(), akce.getJednotky())+2)>spocitejObranu(polea,true))) { okolnitovarna=true; }
                }
                
                if (okolnitovarna==true) p=p+1;
                if (nazev.equals("gama")) p=p+maxmin(-0.1f,zaznamy.najdiPolicko(mapapoli, akce.getPuvodni().getX(), akce.getPuvodni().getY()).getArmada()*0.01f-akce.getPuvodni().getArmada()*0.01f,0.1f);
                else p=p+prioritaoblast*0.01f;
                p=p*genom_koefnakup;
                if (nazev.equals("gama")) p=p+p*maxmin(0.9, (prioritaakce[3]*0.1f)+1.0f, 1.1);
            }
            
            if (akce.getTyp().equals("surovina")) { // Akce Získat suroviny
                double procentolidi = Math.min(Math.min(moznomaxlidi/moznolidi,moznojidla-odectijidlo),1);
                double asijidla = Math.max(vyhledavac.najdiUdajeOHraci("obili", 2, getHrac())*procentolidi+getObili()-odectijidlo,1);
                double asiropy = Math.max(vyhledavac.najdiUdajeOHraci("ropa", 2, getHrac())*procentolidi+getRopa()-armada,1);
                double asizeleza = Math.max(vyhledavac.najdiUdajeOHraci("zelezo", 2, getHrac())*procentolidi+getZelezo()-moznotovarny,1);
                if (akce.getSurovina()==1) p=p+1-maxmin(0, asijidla*0.05f, 1)*genom_surovinaobili;
                if (akce.getSurovina()==2) p=p+1-maxmin(0, asiropy*0.05f, 1)*genom_surovinaropa;
                if (akce.getSurovina()==3) p=p+1-maxmin(0, asizeleza*0.05f, 1)*genom_surovinazelezo;
                if (akce.getSurovina()==4) p=p+1-maxmin(0, moznokamene*0.02f, 1)*genom_surovinakamen; 
                if (p<=0) p = 0.05f;
                p=p*genom_koefsuroviny;
                if (nazev.equals("gama")) p=p+p*maxmin(0.9, (prioritaakce[5]*0.1f)+1.0f, 1.1);
            }
            
            if (akce.getTyp().equals("presun")) { // Akce Přesun jednotek
                double cizipolecil = vyhledavac.hledejPole(vyhledavac.vratOkolniPoleRadiusJedna(akce.getCil()), "vsechny", 0, 0, 7, getHrac()).size();
                if ((jeObrannePole(akce.getCil())>0)||(akce.getCil().getBudova()>3)) {
                    if ((spocitejMaximalniOhrozeni(akce.getCil())-spocitejMaximalniOhrozeni(akce.getPuvodni()))>0) {
                       double maxposlat = akce.getPuvodni().getArmada()-spocitejMaximalniOhrozeni(akce.getPuvodni());
                       if (maxposlat>2) { p=p+maxmin(0.5f, maxposlat*0.1f, 1.0f);
                       if (cizipole==0) p=p*2.0f; }
                       if (akce.getJednotky()!=maxposlat) p=0;
                       if (cizipolecil==0) p=p-0.25f;
                    }                    
                } else {
                if (cizipole==0) {
                if (cizipolecil>0) p=p+0.5f;      
                if (Math.abs(6-akce.getCil().getX())<Math.abs(6-akce.getPuvodni().getX())) p=p+0.05f;
                if (Math.abs(6-akce.getCil().getY())<Math.abs(6-akce.getPuvodni().getY())) p=p+0.05f;
                p=p+jeStrategickePole(akce.getCil())/20+maxmin(0, akce.getCil().getArmada()*0.02f, 0.2f)+maxmin(0, akce.getJednotky()*0.1f, 2.0f);
                }
                }
                p=p*genom_koefpresun;
                if (nazev.equals("gama")) p=p+p*maxmin(0.9, (prioritaakce[1]*0.1f)+1.0f, 1.1);
            }
            
            if (akce.getTyp().equals("postav")) { // Akce Postav budovu
                double budovy[] = {0,0,0,0,0};
                double moznozakladny = vyhledavac.najdiUdajeOHraci("zakladna", 2, getHrac());
                if (polenepratel>0) budovy[0]=budovy[0]+4.0f-maxmin(0, moznozakladny*0.1f, 0.5f);
                if (nazev.equals("gama")) { budovy[0]=budovy[0]+zaznamy.najdiPolicko(mapapoli, akce.getPuvodni().getX(), akce.getPuvodni().getY()).getZakladna();
                } else { budovy[0]=budovy[0]+strategickepole*0.125f; }
                
                budovy[1] = budovy[1]+3.5f-moznolaborator*0.25f;
                if (getSoucetPokroku()==7) budovy[1]=0;
                
                budovy[2] = budovy[2]+cizipole/6;
                if (ropapospotrebe>50) budovy[2] = budovy[2]+0.5f;
                if (polenepratel>0) budovy[2] = budovy[2]+0.5f; 
                if (spocitejMaximalniOhrozeni(akce.getPuvodni())>(akce.getPuvodni().getArmada()+4)) p=p-1.0f;
                budovy[2] = budovy[2]+4-maxmin(0, vyhledavac.najdiUdajeOHraci("tovarna", 2, getHrac())*0.75f-pocetpoli*0.05f, 4);
                if (hra.getKolo()<=3) budovy[2]=budovy[2]+1.0f;
                for (Iterator<Pole> it1 = vyhledavac.vratOkolniPoleRadiusJedna(akce.getPuvodni()).iterator(); it1.hasNext();) {
                    Pole polea = it1.next();
                    if ((polea.getHrac()==getHrac())&&(polea.getBudova()==3)) budovy[2]=budovy[2]-0.5f;
                }
                if (nazev.equals("gama")) {
                    if (jeObrannePole(akce.getPuvodni())>0) budovy[2]=budovy[2]+0.25f;
                    budovy[2]=budovy[2]+zaznamy.najdiPolicko(mapapoli, akce.getPuvodni().getX(), akce.getPuvodni().getY()).getTovarna();
                } else {
                    if (jeObrannePole(akce.getPuvodni())>0) budovy[2]=budovy[2]+0.5f;
                    budovy[2]=budovy[2]+prioritaoblast*0.01f; 
                }
                
                double potrebalidi = moznolidi-moznomaxlidi;
                if (potrebalidi>14) budovy[3] = budovy[3]+5;
                else if ((potrebalidi<14)&&(potrebalidi>=7)) budovy[3] = budovy[3]+4.75f;
                else if ((potrebalidi<7)&&(potrebalidi>=1)) budovy[3] = budovy[3]+4.5f;                
                else if ((potrebalidi<1)&&(potrebalidi>=-3)) budovy[3] = budovy[3]+4.0f;                
                else if ((potrebalidi<-3)&&(potrebalidi>=-7)) budovy[3] = budovy[3]+3.0f;
                else if ((potrebalidi<-7)&&(potrebalidi>-14)) budovy[3] = budovy[3]+2.0f;
                else if ((potrebalidi<-14)&&(potrebalidi>0)) budovy[3] = budovy[3]+0.5f;
                
                budovy[4] = budovy[4]+2;
                if (hra.getKolo()>4) budovy[4] = budovy[4]+1;
                if (hra.getKolo()>9) budovy[4] = budovy[4]+1;
                if (getKamen()>50) budovy[4] = budovy[4]+0.5f;
                
                budovy[0]=budovy[0]*genom_koefzakladna;
                budovy[1]=budovy[1]*genom_koeflaborator;
                budovy[2]=budovy[2]*genom_koeftovarna;
                if (nazev.equals("gama")) {
                    double moznomesta = vyhledavac.najdiUdajeOHraci("mesto", 2, getHrac());          
                    double moznokatedral = vyhledavac.najdiUdajeOHraci("katedrala", 2, getHrac());   
                    budovy[0]=budovy[0]+maxmin(-1, (gama_budovy[0]-moznozakladny)*0.1f, 1);
                    budovy[1]=budovy[1]+maxmin(-1, (gama_budovy[1]-moznolaborator)*0.1f, 1);
                    budovy[2]=budovy[2]+maxmin(-1, (gama_budovy[2]-moznotovarny)*0.1f, 1);
                    budovy[3]=budovy[3]+maxmin(-1, (gama_budovy[3]-moznomesta)*0.1f, 1);
                    budovy[4]=budovy[4]+maxmin(-1, (gama_budovy[4]-moznokatedral)*0.1f, 1);            
                }
                
                if (getSoucetPokroku()==7) budovy[1] = 0;
                double priorita = max(budovy[0],budovy[1],budovy[2],budovy[3],budovy[4]);

                
                if (akce.getBudova()==1) { if (budovy[0]>=priorita) p=p+budovy[0];  }
                
                if (akce.getBudova()==2) {
                    if (budovy[1]>=priorita) {
                        p=p+budovy[1]-cizipole/6-spocitejKoeficientOhrozeni(akce.getPuvodni())*1.5f+maxmin(0,akce.getPuvodni().getArmada()*0.1f,1);
                        if (prioritaoblast==0) p=p-0.75f;
                        if (nazev.equals("gama")) p=p+zaznamy.najdiPolicko(mapapoli, akce.getPuvodni().getX(), akce.getPuvodni().getY()).getLaborator();
                        else p=p-prioritaoblast/5-strategickepole/4;
                    }
                }
                
                if (akce.getBudova()==3) { if (budovy[2]>=priorita) p=p+budovy[2]; }
                
                if (akce.getBudova()==4) {
                   if (budovy[3]>=priorita) { 
                       p=p+budovy[3]-cizipole/6-spocitejKoeficientOhrozeni(akce.getPuvodni())*1.5f+maxmin(0,akce.getPuvodni().getArmada()*0.1f,1);
                       if (nazev.equals("gama")) p=p+zaznamy.najdiPolicko(mapapoli, akce.getPuvodni().getX(), akce.getPuvodni().getY()).getMesto();
                       else p=p-prioritaoblast/5-strategickepole/4;
                   }
                }
                
                if (akce.getBudova()==5) {
                   if (budovy[4]>=priorita) {
                       p=p+budovy[4]-cizipole/6-spocitejKoeficientOhrozeni(akce.getPuvodni())*1.5f+maxmin(0,akce.getPuvodni().getArmada()*0.1f,1);
                       if (polenepratel>0) p=p-0.5f;
                       if (nazev.equals("gama")) p=p+zaznamy.najdiPolicko(mapapoli, akce.getPuvodni().getX(), akce.getPuvodni().getY()).getKatedrala();
                       else p=p-prioritaoblast/5-strategickepole/4;
                }
                }
                p = p + maxmin(0, getKamen()*0.02f, 2);
                p=p*genom_koefbudova;
                if (nazev.equals("gama")) p=p+p*maxmin(0.9, -(prioritaakce[2]*0.1f)+1.0f, 1.1);
            }    
            
            if (akce.getTyp().equals("pokrok")) { // Akce Vynalézt pokrok
                double[] potrebnosts = {0,0,0,0}; 
                potrebnosts[0] = 0.5f+maxmin(-0.5f, -Math.max(moznojidla-odectijidlo,1)*0.04f, 0.5f);
                potrebnosts[1] = 0.5f+maxmin(-0.5f, -ropapospotrebe*0.04f, 0.5f);
                potrebnosts[2] = 0.5f+maxmin(-0.5f, -Math.max(moznozeleza-moznotovarny,1)*0.05f, 0.5f);
                potrebnosts[3] = 1+maxmin(-1, -moznokamene*0.04f, 0);
                double pokroky[] = {0,0,0,0,0,0,0};
                pokroky[0] = 2.5f+potrebnosts[0]+genom_pjidlo;
                pokroky[1] = 3.25f+potrebnosts[1]+genom_propa;
                if (hra.getKolo()>20) pokroky[1]=pokroky[1]+0.25f;
                pokroky[2] = 1.5f+potrebnosts[2]+genom_pzelezo;
                pokroky[3] = 3.5f+potrebnosts[3]+genom_pkamen;
                pokroky[4] = 4.5f-Math.max(pocetpoli*0.05f,0.75f)+genom_putok;
                pokroky[5] = 3.5f+Math.max(pocetpoli*0.05f,1)+genom_pobrana;
                pokroky[6] = 2+Math.max(pokroky[4],pokroky[5])/4.5f+genom_pnahoda;
                
                for (int i = 1; i <= 7; i++) {
                    if (nazev.equals("gama")) pokroky[i-1]=1+gama_pokroky[i-1]*9;
                    if (vratPokrok(i)==1) pokroky[i-1]=0;
                }
                if (pokroky[akce.getPokrok()-1]>=max(pokroky[0],pokroky[1],pokroky[2],pokroky[3],pokroky[4],pokroky[5],pokroky[6])) {
                if (!(nazev.equals("gama")))  p=p+pokroky[akce.getPokrok()-1]+1.0f;
            }

            p=p*genom_koefpokrok;
            if (nazev.equals("gama")) p=p+p*maxmin(0.9, (prioritaakce[4]*0.1f)+1.0f, 1.1);
            }
            
            akce.setPriorita(p);
        }
        
        Collections.shuffle(mozneakce);
        if (mozneakce.size()>0) {
           Collections.sort(mozneakce, new Akce());
           Akce vybranaakace = mozneakce.get(0);
           if (vybranaakace.getTyp().equals("utok")) prioritaakce[0]=prioritaakce[0]-0.33f;
           if (vybranaakace.getTyp().equals("presun")) prioritaakce[1]=prioritaakce[1]-0.33f;
           if (vybranaakace.getTyp().equals("postav")) prioritaakce[2]=prioritaakce[2]-0.33f;
           if (vybranaakace.getTyp().equals("kuparmadu")) prioritaakce[3]=prioritaakce[3]-0.33f;
           if (vybranaakace.getTyp().equals("pokrok")) prioritaakce[4]=prioritaakce[4]-0.33f;
           if (vybranaakace.getTyp().equals("surovina")) prioritaakce[5]=prioritaakce[5]-0.33f;
        if (mozneakce.get(0).getPriorita()>0) { 
        zahrajAkci();
        zahraj();
        } else {
           zruslidi=1;
           if (zkontrolujProdukci()>0) produkujZlato(zkontrolujProdukci());
        }
        } else {
            zruslidi=1;
            if (zkontrolujProdukci()>0) produkujZlato(zkontrolujProdukci());
        }
    }
    
    public void produkujZlato(int zlato) {
        double surovinyp[]=vypocitejPotrebnostSurovin(true);
        double maxpriorita=max(surovinyp[0],surovinyp[1],surovinyp[2],surovinyp[3]);
        if (surovinyp[0]>=maxpriorita) setObili(getObili()+2);
        if (surovinyp[1]>=maxpriorita) setRopa(getRopa()+1);
        if (surovinyp[2]>=maxpriorita) setZelezo(getZelezo()+1);
        if (surovinyp[3]>=maxpriorita) setKamen(getKamen()+1);        
        zlato = zlato - 1;
        if (zlato>0) produkujZlato(zlato);
    }
    
}

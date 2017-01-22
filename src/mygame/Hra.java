package mygame;
 
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.Menu;
import de.lessvoid.nifty.controls.MenuItemActivatedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.tools.SizeValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import org.bushe.swing.event.EventTopicSubscriber;

public class Hra extends SimpleApplication {
    private ArrayList<Pole> pole = new ArrayList<Pole>();
    private ArrayList<Hrajici> hraci = new ArrayList<Hrajici>();
    private Objekt oznaceno = null;
    private Objekt prave = null;
    private Objekt prostredni = null;
    private int hracnatahu = 1;
    private Element popup;
    private Nifty nifty;
    private ArrayList<Historie> historie = new ArrayList<Historie>();
    private Vyhledavac vyhledavac = null;
    private NiftyJmeDisplay niftyDisplay;
    private Hrajici prvni;
    private Hrajici druhy;
    private Hrajici treti;
    private Hrajici ctvrty;
    private String adresa;
    private boolean vzitzlato = false;
    private int kolo = 1;
    private boolean automat = false;
    private Plocha kresli;
    private Zaznamy zaznamy = new Zaznamy();
    private int[] cenabudov = {5,10,15,20,40};
    private int[] cenapokroku = {6,6,6,6,9,7,4};
    
   
    public static void main(String[] args){
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Hra app = new Hra();
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1650);
        settings.setHeight(900);
        settings.setTitle("Empire Wars");
        app.setShowSettings(false);
        app.setSettings(settings);
        app.start();
    }
 
    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.addScreen("Screen_ID", new ScreenBuilder("Hello Nifty Screen"){{  }}.build(nifty));   
        nifty.gotoScreen("Screen_ID");
        Long aktualnicas = new Date().getTime();
        adresa = Long.toString(aktualnicas);
        Generator generator = new Generator();
        pole=generator.vygenerujePole(assetManager, guiFont, rootNode);
        vyhledavac = new Vyhledavac(pole, hraci, hracnatahu, this);
        setDisplayFps(false);
        setDisplayStatView(false);
        cam.setParallelProjection(true); 
        kresli = new Plocha(assetManager,guiFont,rootNode,guiNode, vyhledavac, hraci, historie);  
        inputManager.addMapping("leve", new MouseButtonTrigger(MouseInput.BUTTON_LEFT) );
        inputManager.addMapping("prave", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT) );
        inputManager.addMapping("prostredni", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE) );
        inputManager.addListener(actionListener, "leve");
        inputManager.addListener(actionListener, "prave");
        inputManager.addListener(actionListener, "prostredni");
        inputManager.setCursorVisible(true); 
        prvni = new AlfaBetaGama(1, kresli, pole, vyhledavac, historie, rootNode, this, ""); 
        druhy = new AlfaBetaGama(2, kresli, pole, vyhledavac, historie, rootNode, this, "");  
        treti = new AlfaBetaGama(3, kresli, pole, vyhledavac, historie, rootNode, this, "gama");
        ctvrty = new AlfaBetaGama(4, kresli, pole, vyhledavac, historie, rootNode, this, "betax0.82x0.92x1.07x1.04x0.93x0.96x0.13x0.13x0.13x0.061x0.91x1.2x0.79x0.88x0.83x1x1.15x-0.1x0.09x0.25x-0.16x-0.19x0.1x0.7x1x0.6x0.2x0.7x0.5x20x4");
        hraci.add(new Hrac(0, kresli, pole, vyhledavac, historie, rootNode, this));
        hraci.add(prvni);
        hraci.add(druhy);
        hraci.add(treti);
        hraci.add(ctvrty);
        kresli.setHraci(hraci);
        kresli.kresliObrazky();
        kresli.kresliPlochu();
        if (automat==true) {prvni.zahraj(); dalsiKolo(); }    
        kresli.kresliTabulku(hracnatahu);
    }
    

    public void dalsiKoloVezmiZlato() {
        if (vyhledavac.najdiHrace(hracnatahu).zkontrolujProdukci()==0) { dalsiKolo(); rootNode.detachChildNamed("chybax100x100");} 
        else if (vyhledavac.najdiHrace(hracnatahu).zkontrolujProdukci()>0) {
            vyhledavac.najdiHrace(hracnatahu).setProdukcezlata(vyhledavac.najdiHrace(hracnatahu).zkontrolujProdukci());
            vzitzlato=true;
            kresli.kresliSurovinyZlato();
            rootNode.detachChildNamed("chybax100x100");
            rootNode.attachChild(kresli.kresliChybu("Vybrerte si suroviny za zlato."));
        } else {
        rootNode.detachChildNamed("chybax100x100");
        rootNode.attachChild(kresli.kresliChybu(vyhledavac.najdiHrace(hracnatahu).getChyba()));
        }
    }
     
    public void dalsiKolo() {
        kresli.kresliTabulku(hracnatahu);
        vzitzlato=false;
        if (vyhledavac.najdiHrace(hracnatahu).zkontrolujProdukci()>=0) {
            vyhledavac.najdiHrace(hracnatahu).produkuj();
            zaznamy.zapisKonecKola(vyhledavac);
            vyhledavac.najdiHrace(hracnatahu).setAkce(3);
            hracnatahu++;
            if (hracnatahu>4) hracnatahu=1;
            vyhledavac.setHracnatahu(hracnatahu);
            if (hracnatahu!=1) {
                if (hracnatahu==2) {druhy.zahraj();}
                if (hracnatahu==3) { treti.zahraj();}
                if (hracnatahu==4) { ctvrty.zahraj();} 
                dalsiKolo();
            } else {
                kresli.kresliPlochu();
                kolo++;
                kresli.kresliTabulku(hracnatahu);
                vyhledavac.setKolo(kolo);
                if (automat==true) { 
                if (zkontrolujKonecHry()==0) {
                prvni.zahraj();
                dalsiKolo();
                }
                }
            }
        }
        if (automat==false) zkontrolujKonecHry();  
    }
    
    
    public int zkontrolujKonecHry() {
        int konechry = 0;
        if ((vyhledavac.najdiHrace(1).getKultura()>=100)||(vyhledavac.najdiHrace(2).getKultura()>=100)||(vyhledavac.najdiHrace(3).getKultura()>=100)||(vyhledavac.najdiHrace(4).getKultura()>=100)) {
            Collections.shuffle(hraci);
            Collections.sort(hraci, new Protihrac(){});
            konechry = hraci.get(0).getHrac();
            System.out.println("Konec hry. Vyhral hrac cislo "+konechry+".");
            rootNode.detachChildNamed("chybax100x100");
            rootNode.attachChild(kresli.kresliChybu("Konec hry. Vyhral hrac cislo "+konechry+"."));
            zaznamy.nastavViteznehoHrace(hraci.get(0).getHrac(),adresa);
        }    
       return konechry;
    }
    
   
private ActionListener actionListener = new ActionListener() {
  public void onAction(String name, boolean keyPressed, float tpf) {
   if (hracnatahu==1) {
       if (name.equals("leve")&& !keyPressed) {
        CollisionResults vysledky = new CollisionResults();
        Vector2f klik2d = inputManager.getCursorPosition();
        Vector3f klik3d = cam.getWorldCoordinates(new Vector2f(klik2d.x, klik2d.y), 0f).clone();
        Vector3f smer = cam.getWorldCoordinates(new Vector2f(klik2d.x, klik2d.y), 1f).subtractLocal(klik3d).normalizeLocal();
        rootNode.collideWith(new Ray(klik3d, smer), vysledky);
        if (vysledky.size() > 0) {
            oznaceno = new Objekt(vysledky.getCollision(0));      
         if ((vzitzlato==false)&&(vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY())!=null)) {
         if (vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()).getHrac()==1) {              
         if (oznaceno.getNazev().contains("clovekb")) vyhledavac.najdiHrace(hracnatahu).oddelejClovekaZBudovy(vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()));
         if (oznaceno.getNazev().contains("cloveks")) vyhledavac.najdiHrace(hracnatahu).oddelejClovekaZeSurovin(vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()));            
         if (oznaceno.getNazev().equals("budova")) vyhledavac.najdiHrace(hracnatahu).pridejClovekaDoBudovy(vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()));
         if (oznaceno.getNazev().equals("surovina")) {vyhledavac.najdiHrace(hracnatahu).pridejClovekaNaSuroviny(vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()));}
         if (oznaceno.getNazev().equals("centrum")) { zobrazMenu();} 
         if (oznaceno.getNazev().equals("utok")) {
             vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()).setPoslat(vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()).getPoslat()-1);
             vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()).kresliPole(vyhledavac, hracnatahu);  }
             }
           } 
         if (vzitzlato==false) {if (oznaceno.getNazev().equals("konectahu")) { dalsiKoloVezmiZlato();} }
           
         if (oznaceno.getNazev().equals("zlato")) { vyhledavac.najdiHrace(hracnatahu).ziskejSurovinyZaZlato(oznaceno.getY()); 
          if (vyhledavac.najdiHrace(hracnatahu).getProdukcezlata()<1) dalsiKolo(); } 
         
         if ((vyhledavac.najdiHrace(hracnatahu).getAkce()>0)&&(vzitzlato==false)) { 
         if (oznaceno.getNazev().equals("pokrok")) {vyhledavac.najdiHrace(hracnatahu).vynalezniPokrok(oznaceno.getY());}
         if (vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY())!=null) {
         if ((vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()).getHrac()==1)&&(oznaceno.getNazev().equals("vojaci"))) { vyhledavac.najdiHrace(hracnatahu).poslatVojaky(vyhledavac.najdiPole(oznaceno.getX(),oznaceno.getY())); } }
         if (oznaceno.getNazev().equals("ziskat")) { vyhledavac.najdiHrace(hracnatahu).ziskejSuroviny(oznaceno.getY()); }         
        }       
         if (oznaceno.getNazev().equals("pravidla")) { kresli.otevriPravidla(); }
         
        }
        
      } else if ((name.equals("prave"))&&(oznaceno!=null)) {
        if ((oznaceno.getNazev().equals("vojaci"))||(oznaceno.getNazev().equals("valka"))) {
        CollisionResults vysledky = new CollisionResults();
        Vector2f klik2d = inputManager.getCursorPosition();
        Vector3f klik3d = cam.getWorldCoordinates(new Vector2f(klik2d.x, klik2d.y), 0f).clone();
        Vector3f smer = cam.getWorldCoordinates(new Vector2f(klik2d.x, klik2d.y), 1f).subtractLocal(klik3d).normalizeLocal();
        rootNode.collideWith(new Ray(klik3d, smer), vysledky);
        if (vysledky.size() > 0) {
            prave = new Objekt(vysledky.getCollision(0));
            if ((oznaceno!=null)&&(prave!=null)) vyhledavac.najdiHrace(hracnatahu).presunJednotky(vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()), vyhledavac.najdiPole(prave.getX(), prave.getY()));
            if ((oznaceno!=null)&&(prave!=null)) vyhledavac.najdiHrace(hracnatahu).zautoc(vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()), vyhledavac.najdiPole(prave.getX(), prave.getY()));
            oznaceno = null;
            prave = null;
        }
        }   
          
      } else if ((name.equals("prostredni"))&&(vyhledavac.najdiHrace(hracnatahu).getAkce()>0)) {
        CollisionResults results = new CollisionResults();
        Vector2f klik2d = inputManager.getCursorPosition();
        Vector3f klik3d = cam.getWorldCoordinates(new Vector2f(klik2d.x, klik2d.y), 0f).clone();
        Vector3f smer = cam.getWorldCoordinates(new Vector2f(klik2d.x, klik2d.y), 1f).subtractLocal(klik3d).normalizeLocal();
        rootNode.collideWith(new Ray(klik3d, smer), results);
        if (results.size() > 0) {
            prostredni = new Objekt(results.getCollision(0));
            oznaceno = new Objekt(results.getCollision(0));     
            if (prostredni!=null) {
            if ((prostredni.getNazev().equals("vojaci"))||(prostredni.getNazev().equals("valka"))||(prostredni.getNazev().equals("utok"))) {
                if (vyhledavac.najdiPole(prostredni.getX(), prostredni.getY()).getHrac()==1) {   
                    if ((prostredni.getNazev().equals("vojaci"))||(prostredni.getNazev().equals("valka"))) {
                    vyhledavac.najdiPole(prostredni.getX(), prostredni.getY()).setPoslat(vyhledavac.najdiPole(prostredni.getX(), prostredni.getY()).getArmada());
                    vyhledavac.najdiPole(prostredni.getX(), prostredni.getY()).kresliPole(vyhledavac, hracnatahu); }
                    if (prostredni.getNazev().equals("utok")) {
                    vyhledavac.najdiPole(prostredni.getX(), prostredni.getY()).setPoslat(0);
                    vyhledavac.najdiPole(prostredni.getX(), prostredni.getY()).kresliPole(vyhledavac, hracnatahu); }
                }
            }
          }
         }
        
      }
    
   }
  }
};


  public void zobrazMenu() { 
    popup = nifty.createPopup("niftyPopupMenu");
    Menu polemenu = popup.findNiftyControl("#menu", Menu.class);
    polemenu.setWidth(new SizeValue("225px"));
    if (vyhledavac.najdiHrace(hracnatahu).getAkce()>0) {
    if (vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()).getBudova()==0) {
        if (vyhledavac.najdiHrace(hracnatahu).getKamen()>=5) polemenu.addMenuItem("Postav Vojenskou zakladnu", "obrazky/b11.gif",  new MenuPolozka("zakladna", oznaceno.getX()+"x"+oznaceno.getY()));
        if (vyhledavac.najdiHrace(hracnatahu).getKamen()>=10) polemenu.addMenuItem("Postav Laborator", "obrazky/b21.gif",  new MenuPolozka("laborator",  oznaceno.getX()+"x"+oznaceno.getY()));
        if (vyhledavac.najdiHrace(hracnatahu).getKamen()>=15) polemenu.addMenuItem("Postav Továrnu", "obrazky/b31.gif",  new MenuPolozka("tovarna",  oznaceno.getX()+"x"+oznaceno.getY()));
        if (vyhledavac.najdiHrace(hracnatahu).getKamen()>=20) polemenu.addMenuItem("Postav Mesto", "obrazky/b41.gif",  new MenuPolozka("mesto",  oznaceno.getX()+"x"+oznaceno.getY()));
        if (vyhledavac.najdiHrace(hracnatahu).getKamen()>=40) polemenu.addMenuItem("Postav Katedralu", "obrazky/b51.gif",  new MenuPolozka("katedrala",  oznaceno.getX()+"x"+oznaceno.getY()));
    }
   if (vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()).getBudova()!=0) polemenu.addMenuItem("Zbourat budovu", "obrazky/budovane.gif",  new MenuPolozka("zbourat",  oznaceno.getX()+"x"+oznaceno.getY()));
   if (vyhledavac.najdiHrace(hracnatahu).getZelezo()>=5) polemenu.addMenuItem("Koupit 2 jednotky", "obrazky/armada.gif",  new MenuPolozka("koupitjednotky",  oznaceno.getX()+"x"+oznaceno.getY()));
        }
    
   if (vyhledavac.najdiPole(oznaceno.getX(), oznaceno.getY()).getArmada()>0) polemenu.addMenuItem("Znicit jednotku", "obrazky/armadane.gif",  new MenuPolozka("znicitjednotku",  oznaceno.getX()+"x"+oznaceno.getY()));
   nifty.subscribe(nifty.getCurrentScreen(), polemenu.getId(),  MenuItemActivatedEvent.class, new MenuItemActivatedEventSubscriber());  
   nifty.showPopup(nifty.getCurrentScreen(), popup.getId(), null); 
    }
  
  
      private class MenuPolozka {
          public String id;
          public String nazev;
          public MenuPolozka(String id, String nazev){
            this.id = id;
            this.nazev = nazev;
          }
        }

      
    private class MenuItemActivatedEventSubscriber implements EventTopicSubscriber<MenuItemActivatedEvent> {
 
    @Override
    public void onEvent(final String id, final MenuItemActivatedEvent event) {
    	MenuPolozka item = (MenuPolozka) event.getItem();
        String[] parts = item.nazev.split("x");  
        int xxx = Integer.parseInt(parts[0]); 
        int yyy = Integer.parseInt(parts[1]); 
        if ("zakladna".equals(item.id)) { vyhledavac.najdiHrace(hracnatahu).postavBudovu(vyhledavac.najdiPole(xxx, yyy), 1); }
        if ("laborator".equals(item.id)) { vyhledavac.najdiHrace(hracnatahu).postavBudovu(vyhledavac.najdiPole(xxx, yyy), 2); }
        if ("tovarna".equals(item.id)) { vyhledavac.najdiHrace(hracnatahu).postavBudovu(vyhledavac.najdiPole(xxx, yyy), 3); }
        if ("mesto".equals(item.id)) { vyhledavac.najdiHrace(hracnatahu).postavBudovu(vyhledavac.najdiPole(xxx, yyy), 4); }
        if ("katedrala".equals(item.id)) { vyhledavac.najdiHrace(hracnatahu).postavBudovu(vyhledavac.najdiPole(xxx, yyy), 5); }
        if ("zbourat".equals(item.id)) { vyhledavac.najdiHrace(hracnatahu).zbourejBudovu(vyhledavac.najdiPole(xxx, yyy)); }
        if ("koupitjednotky".equals(item.id)) { vyhledavac.najdiHrace(hracnatahu).kupArmadu(vyhledavac.najdiPole(xxx, yyy)); }
        if ("znicitjednotku".equals(item.id)) { vyhledavac.najdiHrace(hracnatahu).znicArmadu(vyhledavac.najdiPole(xxx, yyy)); }
        getNifty().closePopup(getPopup().getId());       
    }
  };
    

    public int[] getCenabudov() {
        return cenabudov;
    }

    public int[] getCenapokroku() {
        return cenapokroku;
    }
    
    public ArrayList<Pole> getPole() {
        return pole;
    }

    public ArrayList<Hrajici> getHraci() {
        return hraci;
    }

    public int getHracnatahu() {
        return hracnatahu;
    }

    public Element getPopup() {
        return popup;
    }

    public Nifty getNifty() {
        return nifty;
    }
    
    public String getAdresa() {
        return adresa;
    }

    public int getKolo() {
        return kolo;
    }

}
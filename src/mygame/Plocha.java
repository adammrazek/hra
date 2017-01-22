package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import java.util.ArrayList;
import java.util.Iterator;

public class Plocha {
    private AssetManager assetManager = null;
    private BitmapFont guiFont = null;
    public Node rootNode = null;
    public Node guiNode = null;
    private Vyhledavac tah = null;
    private ArrayList<Hrajici> hraci = null;
    private ArrayList<Historie> historie;
    
    
    public Plocha(AssetManager manager, BitmapFont guifont, Node rootNode, Node guiNode, Vyhledavac tah, ArrayList<Hrajici> hraci, ArrayList<Historie> historie) {
     this.assetManager = manager;
     this.guiFont = guifont;
     this.rootNode = rootNode;
     this.guiNode = guiNode;
     this.tah = tah;
     this.hraci = hraci;
     this.historie = historie;
    }
    
    
   public void kresliPlochu() {
        rootNode.detachAllChildren();
        int i = 0;
        int[] pokroky = tah.najdiHrace(tah.getHracnatahu()).getPokroky();
        while (i<tah.getPole().size()) {
        tah.getPole().get(i).kresliPole(tah, tah.getHracnatahu());
        i++;
        }
        for (int j = 1; j < 8; j++) { if (pokroky[j-1]==0) rootNode.attachChild(kresliVedu(j)); }
        for (int j = 1; j < 5; j++) { rootNode.attachChild(kresliSuroviny(j)); }
        rootNode.attachChild(kresliDalsiKolo());
        rootNode.attachChild(kresliPravidla());
        rootNode.attachChild(kresliPozadi());
        rootNode.attachChild(kresliTextPokroky());
        rootNode.attachChild(kresliTextSuroviny());
        kresliHistorii();
    }
    
    
    public void kresliObrazky() {
        ArrayList<String> obrazky = new ArrayList<String>();
        obrazky.add("akce.gif");
        obrazky.add("b51.gif");
        obrazky.add("b21.gif");
        obrazky.add("clovek.png");
        obrazky.add("armada.gif");
        obrazky.add("s11.gif");
        obrazky.add("s21.gif");
        obrazky.add("s31.gif");
        obrazky.add("s41.gif");
        obrazky.add("s51.gif");
        obrazky.add("p1.gif");
        obrazky.add("p2.gif");
        obrazky.add("p3.gif");
        obrazky.add("p4.gif");
        obrazky.add("p5.gif");
        obrazky.add("p6.gif");
        obrazky.add("p7.gif");
        int i=0;
        for (Iterator<String> it = obrazky.iterator(); it.hasNext();) {
        String obr = it.next();
        Picture pic = new Picture("HUD Picture");
        pic.setImage(assetManager, "obrazky/"+obr, true);
        pic.setWidth(45);
        pic.setHeight(45);
        if (i<10) pic.setPosition(1085+i*58,850);
        else pic.setPosition(600+i*50,730);
        guiNode.attachChild(pic);
        i++;
        }
    }
    
       public void kresliSurovinyZlato() {      
          for (int i = 1; i < 5; i++) {  rootNode.attachChild(kresliZlato(i));  }
       }
        
      public BitmapText kresliChybu(String text) {
        BitmapText geo = new BitmapText(guiFont, false);
        geo.setSize(guiFont.getCharSet().getRenderedSize()/5);
        geo.setText(text);
        geo.setColor(ColorRGBA.Red);
        geo.setName("chybax"+100+"x"+100);
        geo.setLocalTranslation(geo.getLocalTranslation().x+0.4355f,geo.getLocalTranslation().y+0.14f,geo.getLocalTranslation().z+1.0f);
        geo.setLocalScale(geo.getLocalScale().x/125.0f,geo.getLocalScale().y/125.0f,geo.getLocalScale().z/125.0f); 
        return geo;
      } 
      
      public BitmapText kresliTextSuroviny() {
        rootNode.detachChildNamed("chybax100x101");
        BitmapText geo = new BitmapText(guiFont, false);
        geo.setSize(guiFont.getCharSet().getRenderedSize()/5);
        geo.setText("Ziskat suroviny"); 
        geo.setColor(ColorRGBA.Red);
        geo.setName("chybax"+100+"x"+101);
        geo.setLocalTranslation(geo.getLocalTranslation().x+0.6095f,geo.getLocalTranslation().y+0.305f,geo.getLocalTranslation().z+1.0f);
        geo.setLocalScale(geo.getLocalScale().x/200.0f,geo.getLocalScale().y/200.0f,geo.getLocalScale().z/200.0f); 
        return geo;
      } 
      
      public BitmapText kresliTextPokroky() {
        rootNode.detachChildNamed("chybax100x102");
        BitmapText geo = new BitmapText(guiFont, false);
        geo.setSize(guiFont.getCharSet().getRenderedSize()/5);
        geo.setText("Vynalezt pokrok");
        geo.setColor(ColorRGBA.Red);
        geo.setName("chybax"+100+"x"+102);
        geo.setLocalTranslation(geo.getLocalTranslation().x+0.24f,geo.getLocalTranslation().y+0.184f,geo.getLocalTranslation().z+1.0f);
        geo.setLocalScale(geo.getLocalScale().x/110.0f,geo.getLocalScale().y/110.0f,geo.getLocalScale().z/110.0f); 
        return geo;
      } 
      
      
     public String nazevBudovy(int typbudovy) {
        String nazev = "";
        if (typbudovy==1) nazev="Vojenska zakladna";
        if (typbudovy==2) nazev="Laborator";
        if (typbudovy==3) nazev="Tovarna";
        if (typbudovy==4) nazev="Mesto";
        if (typbudovy==5) nazev="Katedrala";        
        return nazev;
    }
    
     public String nazevSuroviny(int surovina) {
        String nazev = "";
        if (surovina==1) nazev="obili";
        if (surovina==2) nazev="ropy";
        if (surovina==3) nazev="zeleza";
        if (surovina==4) nazev="kamene";
        if (surovina==5) nazev="zlata";        
        return nazev;
    }
    
     public String nazevPokroku(int pokrok) {
        String nazev = "";
        if (pokrok==1) nazev="Geneticky upravene obili";
        if (pokrok==2) nazev="Hlubinne vrty";
        if (pokrok==3) nazev="Metalurgie";
        if (pokrok==4) nazev="Dynamit";
        if (pokrok==5) nazev="Stihacky";
        if (pokrok==6) nazev="Opevneni";
        if (pokrok==7) nazev="Bojova taktika";     
        return nazev;
    }  
      
       public void kresliHistorii() {
            int i = historie.size();
            if (i<1) i = 0;
            int j = 0;
            for (Iterator<Historie> it = historie.iterator(); it.hasNext();) {  
               Historie hh = it.next();
               guiNode.detachChildNamed("historie"+j); 
               guiNode.detachChildNamed("historie"+(j+1)); 
               rootNode.detachChildNamed("obrazekx100x1");
               j++;
            }
            j = 0;
            int k = 1; 
            while (i>0) {
            Historie h = historie.get(historie.size()-k);
            k++;
            BitmapText historietext = new BitmapText(guiFont, false);
            historietext.setSize(guiFont.getCharSet().getRenderedSize()/1.5f);   
            historietext.setColor(h.getBarva());  
            historietext.setName("historie"+i);
            historietext.setText("");
            String obrazek = "pokrok0.gif";
            if (h.getTyp().equals("utok")) {obrazek = "pokrok0.gif"; historietext.setText("("+h.getKolo()+") Hrac zautocil "+h.getJednotky()+" tanky z ["+h.getPolezx()+","+h.getPolezy()+"] na ["+h.getPolenax()+","+h.getPolenay()+"] ("+h.getUtok()+"=>"+h.getObrana()+")."); }
            if (h.getTyp().equals("presun")) {historietext.setText("("+h.getKolo()+") Hrac presunul "+h.getJednotky()+" z pole["+h.getPolezx()+","+h.getPolezy()+"] na pole ["+h.getPolenax()+","+h.getPolenay()+"]."); }
            if (h.getTyp().equals("zbourat")) {obrazek = "b"+h.getBudova()+"0.gif"; historietext.setText("("+h.getKolo()+") Hrac zboural budovu "+nazevBudovy(h.getBudova())+" na poli ["+h.getPolezx()+","+h.getPolezy()+"]."); }
            if (h.getTyp().equals("postavit")) {obrazek = "b"+h.getBudova()+"1.gif"; historietext.setText("("+h.getKolo()+") Hrac postavil budovu "+nazevBudovy(h.getBudova())+" na poli ["+h.getPolezx()+","+h.getPolezy()+"]."); }
            if (h.getTyp().equals("pokrok")) {obrazek = "pokrok1.gif"; historietext.setText("("+h.getKolo()+") Hrac vynalezl pokrok "+nazevPokroku(h.getPokrok())+"."); }
            if (h.getTyp().equals("kuparmadu")) {obrazek = "armada.gif"; historietext.setText("("+h.getKolo()+") Hrac koupil 2 jednotky na pole ["+h.getPolezx()+","+h.getPolezy()+"]."); }            
            if (h.getTyp().equals("ziskat")) {obrazek = "s"+h.getSurovina()+"1.gif"; historietext.setText("("+h.getKolo()+") Hrac ziskal "+h.getPocet()+" kusu "+nazevSuroviny(h.getSurovina())+"."); }                                   
            if (h.getTyp().equals("znicarmadu")) {obrazek = "armadane.gif"; historietext.setText("("+h.getKolo()+") Hrac znicil jednotku na poli ["+h.getPolezx()+","+h.getPolezy()+"]."); }                                   

            if (j<33) historietext.setLocalTranslation(1110, 575-j*15, 0);   
            else historietext.setLocalTranslation(1380, 575-j*15+495, 0);  
            guiNode.attachChild(historietext);
            float x = 0;
            float y = 0;
            if (j<33) { 
            x=0.255f; y=-0.347f-j*0.0138f+0.0138f*33; }
            else {  
            x=0.505f; y=-0.347f-j*0.0138f+2*0.0138f*33; }
            rootNode.attachChild(kresliObrazekHistorie(obrazek, x, y));
        
            i--;  
            if (!(h.getTyp().equals("clovek"))) j++;
            if (j>=66) i = 0;
                }
    }
       
      public Geometry kresliObrazekHistorie(String obr, float x, float y) {
        Box b = new Box(1, 1, 1);
        Geometry geo = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = assetManager.loadTexture("obrazky/"+obr); 
        geo.setName("obrazekx100x1");
        mat.setTexture("ColorMap", textura);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geo.setMaterial(mat);
        geo.setLocalTranslation(x,y,1.0f);
        geo.setLocalScale(0.005f);
        return geo;
      }
      
       
       public void kresliTabulku(int hracnatahu) {
            if (hracnatahu==1) {
           for (Iterator<Hrajici> it = hraci.iterator(); it.hasNext();) {          
            Hrajici hraca = it.next();
            if (hraca.getHrac()!=0) {
                ArrayList<String> hodnota = new ArrayList<String>();
                hodnota.add(Integer.toString(hraca.getAkce()));
                hodnota.add(Integer.toString(hraca.getKultura())+" (+"+Integer.toString(tah.najdiUdajeOHraci("katedrala", 1, hraca.getHrac()))+")");
                hodnota.add(Integer.toString(hraca.getVeda())+" (+"+Integer.toString(tah.najdiUdajeOHraci("laborator", 1, hraca.getHrac()))+")");
                hodnota.add(Integer.toString((tah.najdiUdajeOHraci("clovek", 1, hraca.getHrac())))+"/"+Integer.toString((tah.najdiUdajeOHraci("mesto", 1, hraca.getHrac())*7+7)));
                hodnota.add(Integer.toString((tah.najdiUdajeOHraci("armada", 1, hraca.getHrac())))+" (+"+Integer.toString(tah.najdiUdajeOHraci("tovarna", 1, hraca.getHrac())*2)+")");             
                hodnota.add(Integer.toString(hraca.getObili())+" (+"+Integer.toString(tah.najdiUdajeOHraci("obili", 1, hraca.getHrac())-tah.najdiUdajeOHraci("clovek", 1, hraca.getHrac()))+")");
                hodnota.add(Integer.toString(hraca.getRopa())+" (+"+Integer.toString(tah.najdiUdajeOHraci("ropa", 1, hraca.getHrac())-tah.najdiUdajeOHraci("armada", 1, hraca.getHrac()))+")");
                hodnota.add(Integer.toString(hraca.getZelezo())+" (+"+Integer.toString(tah.najdiUdajeOHraci("zelezo", 1, hraca.getHrac())-tah.najdiUdajeOHraci("tovarna", 1, hraca.getHrac()))+")");
                hodnota.add(Integer.toString(hraca.getKamen())+" (+"+Integer.toString(tah.najdiUdajeOHraci("kamen", 1, hraca.getHrac()))+")");
                hodnota.add("(+"+Integer.toString(tah.najdiUdajeOHraci("zlato", 1, hraca.getHrac()))+")"); 
                for (int i = 1; i <=7; i++) { hodnota.add(Integer.toString(hraca.vratPokrok(i))); }
                int i = 0;
                for (Iterator<String> it1 = hodnota.iterator(); it1.hasNext();) {
                    String hodnotax = it1.next();
            guiNode.detachChildNamed("tabulkahrac"+hraca.getHrac()+"x"+i);
            BitmapText textb = new BitmapText(guiFont, false);
            textb.setSize(guiFont.getCharSet().getRenderedSize()/1.4f);   
            textb.setColor(hraca.vratBarvu());  
            textb.setName("tabulkahrac"+hraca.getHrac()+"x"+i);
            textb.setText(hodnotax);   
            if (i<10) {
                textb.setLocalTranslation(1090+i*58, 860-hraca.getHrac()*15, 0);
                guiNode.attachChild(textb);
            } else {  
             Picture pic = new Picture("HUD Picture");
             pic.setImage(assetManager, "obrazky/pokrok"+hodnotax+".gif", true);
             pic.setWidth(10);
             pic.setHeight(10);
             pic.setPosition(615+i*50,730-hraca.getHrac()*15);
             guiNode.attachChild(pic);
             textb.setLocalTranslation(605+i*50, 730-hraca.getHrac()*15, 0);
            }
            i++;
                }
            }
         }
            }
      } 

    
     public Geometry kresliDalsiKolo() {
        Box b = new Box(2, 1, 1);
        Geometry geo = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = assetManager.loadTexture("obrazky/tah.png"); 
        geo.setName("konectahux100x1");
        mat.setTexture("ColorMap", textura);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geo.setMaterial(mat);
        geo.setLocalTranslation(0.69f,-0.38f,1.0f);
        geo.setLocalScale(0.035f);
        return geo;
      }
     

     public Geometry kresliPozadi() {
        Box b = new Box(1, 1, 0);
        Geometry geo = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = assetManager.loadTexture("obrazky/pozadi1.jpg"); 
        geo.setName("pozadix100x1");
        mat.setTexture("ColorMap", textura);
        geo.setMaterial(mat);
        geo.setLocalTranslation(-0.7f,0.0f,-2.0f);
        geo.setLocalScale(1.5f);
        return geo;
      }
     

      public Geometry kresliPravidla() {
        Box b = new Box(1, 1, 1);
        Geometry geo = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = assetManager.loadTexture("obrazky/napoveda.png"); 
        mat.setTexture("ColorMap", textura);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geo.setMaterial(mat);
        geo.setName("pravidlax100x100");
        geo.setLocalTranslation(geo.getLocalTranslation().x-0.731f,geo.getLocalTranslation().y+0.385f,geo.getLocalTranslation().z+1.0f);
        geo.setLocalScale(geo.getLocalScale().x/40.0f,geo.getLocalScale().y/40.0f,geo.getLocalScale().z/40.0f); 
        return geo;
      }
      
    
      public Geometry kresliVedu(int pokrok) {
        Box b = new Box(1, 1, 1);
        Geometry geo = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = assetManager.loadTexture("obrazky/p"+pokrok+".gif"); 
        geo.setName("pokrokx100x"+pokrok);
        mat.setTexture("ColorMap", textura);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha); 
        geo.setMaterial(mat);
        geo.setLocalTranslation(0.42f+0.044f*pokrok,0.17f,1.0f);
        geo.setLocalScale(0.022f);
        return geo;
      }

    public Geometry kresliSuroviny(int surovina) {
        Box b = new Box(1, 1, 1);
        Geometry geo = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = assetManager.loadTexture("obrazky/s"+surovina+"1.gif"); 
        geo.setName("ziskatx100x"+surovina);
        mat.setTexture("ColorMap", textura);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha); 
        geo.setMaterial(mat);
        float x = 0.59f+0.044f*surovina;
        float y = 0.26f;
        if (surovina>2) {
            x=x-0.044f*2;
            y=y-0.044f;
        }
        geo.setLocalTranslation(x,y,1.0f);
        geo.setLocalScale(0.022f);
        return geo;
      }
    
    
    public Geometry kresliZlato(int surovina) {
        Box b = new Box(1, 1, 1);
        Geometry geo = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = assetManager.loadTexture("obrazky/s"+surovina+"1.gif"); 
        geo.setName("zlatox100x"+surovina);
        mat.setTexture("ColorMap", textura);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha); 
        geo.setMaterial(mat);
        geo.setLocalTranslation(0.22f+0.044f*surovina,0.135f,1.0f);
        geo.setLocalScale(0.018f);
        return geo;
      }
    

    public void setHraci(ArrayList<Hrajici> hraci) {
        this.hraci = hraci;
    }
    
    public void otevriPravidla() {
        Pravidla pravidla = new Pravidla();
    }
       
}

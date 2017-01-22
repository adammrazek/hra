package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;

public class Pole {
    private AssetManager manager = null;
    private BitmapFont guiFont = null;
    private int hrac = 0;
    private int typ = 0; //obilí, ropa, železo, kámen, zlato
    private int pocet = 0;
    private int budova = 0;
    private int armada = 0;
    private int x = 0;
    private int y = 0;
    private int odhalene = 0;
    private int oblast = 0;
    private int clovekb = 0;
    private int cloveks = 0;
    private int poslat = 0;
    private Node rootNode = null;
        
    public Pole(AssetManager manager, BitmapFont guiFont, int xxx, int yyy, int druh, int typ, int pocet, Node rootNode) {
        this.manager = manager;
        x = xxx;
        y = yyy;
        this.guiFont = guiFont;
        oblast = druh;
        hrac = 0;
        if ((xxx==2)&&(yyy==2)) hrac=2;
        if ((xxx==2)&&(yyy==10)) hrac=1;
        if ((xxx==10)&&(yyy==2)) hrac=3;
        if ((xxx==10)&&(yyy==10)) hrac=4;
        if (hrac != 0) { odhalene=1; armada=5; }
        this.typ = typ;
        this.pocet = pocet;
        this.rootNode = rootNode;
    }
    
    public Pole() {
        
    }
    
      public ColorRGBA vratBarvu(int hrac) {
            ColorRGBA barva = ColorRGBA.Brown;
            if (hrac==0) barva = ColorRGBA.Gray;
            if (hrac==1) barva = ColorRGBA.Yellow;
            if (hrac==2) barva = ColorRGBA.Orange;
            if (hrac==3) barva = ColorRGBA.Green;
            if (hrac==4) barva = ColorRGBA.Cyan;     
            return barva;
        }
     
     public ColorRGBA vratBarvuSuroviny(int typ) {
            ColorRGBA barva = ColorRGBA.Brown;
            if (typ==1) barva = ColorRGBA.Red;
            if (typ==2) barva = ColorRGBA.Red;
            if (typ==3) barva = ColorRGBA.Red;
            if (typ==4) barva = ColorRGBA.Red;
            if (typ==5) barva = ColorRGBA.Red; 
            return barva;
         }
         
     public ColorRGBA vratBarvuArmada(int hrac) {
            ColorRGBA barva = ColorRGBA.Brown;
            if (hrac==0) barva = ColorRGBA.Gray;
            if (hrac==1) barva = ColorRGBA.Red;
            if (hrac==2) barva = ColorRGBA.Red;
            if (hrac==3) barva = ColorRGBA.Red;
            if (hrac==4) barva = ColorRGBA.Red;     
            return barva;
         }
         
      public void kresliPole(Vyhledavac tah, int hracnatahu) {
        tah.odkryjOkolniPole(this);
        if (hracnatahu==1) {
        rootNode.detachChildNamed("vojacix"+getX()+"x"+getY());
        rootNode.detachChildNamed("armadax"+getX()+"x"+getY());
        rootNode.detachChildNamed("valkax"+getX()+"x"+getY());
        rootNode.detachChildNamed("polex"+getX()+"x"+getY());
        rootNode.detachChildNamed("clovekbx"+getX()+"x"+getY());
        rootNode.detachChildNamed("cloveksx"+getX()+"x"+getY());
        rootNode.detachChildNamed("budovax"+getX()+"x"+getY());
        rootNode.detachChildNamed("centrumx"+getX()+"x"+getY());  
        rootNode.detachChildNamed("surovinax"+getX()+"x"+getY());
        rootNode.detachChildNamed("utokx"+getX()+"x"+getY());
        rootNode.detachChildNamed("surovinax"+getX()+"x"+getY());
        rootNode.attachChild(kresliSestiuhelnik());
        if (getHrac()>0) rootNode.attachChild(kresliPocetVojaku());
        if (getHrac()==1) rootNode.attachChild(kresliCentrum());          
        if (odhalene==1) {  
            rootNode.attachChild(kresliPocetSurovin());
            rootNode.attachChild(kresliSurovinu());
        }
        if (getPoslat()>0) { rootNode.attachChild(kresliPoslaniVojaci()); }
        rootNode.attachChild(kresliObrys());
        if (getClovekb()==1)    rootNode.attachChild(kresliCloveka(1));
        if (getCloveks()==1)    rootNode.attachChild(kresliCloveka(0));
        if (getBudova()>0)   rootNode.attachChild(kresliBudovu());
        if (getHrac()>0)    rootNode.attachChild(kresliArmadu());
      }
    }
     
    public Geometry velikost(Geometry geo) {
        float posun = 0.045f;
        if (getY() % 2 == 0) posun = 0.0f;
        geo.setLocalTranslation(-0.84f+getX()*0.09f+posun,0.385f-getY()*0.071f,0.0f);
        geo.setLocalScale(0.03f);
        return geo;
    }
    
     public BitmapText velikost(BitmapText geo) {
        float posun = 0.045f;    
        if (getY() % 2 == 0) posun = 0.0f;
        geo.setLocalTranslation(-0.84f+getX()*0.09f+posun,0.385f-getY()*0.071f,0.0f);
        geo.setLocalScale(0.03f);
        return geo;
    }
     
     public Spatial velikost(Spatial geo) {
        float posun = 0.045f;    
        if (getY() % 2 == 0) posun = 0.0f;
        geo.setLocalTranslation(-0.84f+getX()*0.09f+posun,0.385f-getY()*0.071f,0.0f);
        geo.setLocalScale(0.03f);
        return geo;
    }
     
     public Vector3f[] vrcholy() {
        Vector3f[] vertices = new Vector3f[6];
        vertices[0] = new Vector3f(1.5f, 0, 0);
        vertices[1] = new Vector3f(3, 0.634f, 0);
        vertices[2] = new Vector3f(3, 2.366f, 0);
        vertices[3] = new Vector3f(1.5f, 3, 0); 
        vertices[4] = new Vector3f(0, 2.366f, 0);
        vertices[5] = new Vector3f(0, 0.634f, 0); 
        return vertices;
     }
 
    public Geometry kresliSestiuhelnik() {
        Mesh mesh = new Mesh();        
        Vector3f[] vertices = vrcholy();
        Vector2f[] texCoord = new Vector2f[6];       
        texCoord[0] = new Vector2f(0.5f, 0.0f);
        texCoord[1] = new Vector2f(1.0f, 0.33f);
        texCoord[2] = new Vector2f(1, 0.66f);
        texCoord[3] = new Vector2f(0.5f, 1.0f);
        texCoord[4] = new Vector2f(0.0f, 0.66f);
        texCoord[5] = new Vector2f(0.0f, 0.33f); 
        int[] indexes = {
            0,1,3,
            1,2,4,
            2,3,5,
            3,4,0,
            4,5,1,
            5,0,1    
        };
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(indexes));
        mesh.updateBound();
        Geometry geo = new Geometry("", mesh);
        geo.setName("polex"+x+"x"+y); 
        Material mat = new Material(getManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", vratBarvu(getHrac()));
        Texture hory = getManager().loadTexture("Textures/Terrain/Pond/Pond.jpg"); 
        Texture textura = getManager().loadTexture("obrazky/pozadipole.jpg"); 
        if (odhalene==1) mat.setTexture("ColorMap", textura);
        if (getOblast()==5) mat.setTexture("ColorMap", hory);       
        geo.setMaterial(mat);
        geo = velikost(geo);
        return geo;
    }
    
    public BitmapText kresliPocetVojaku() {
        Material mat = new Material(getManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        BitmapText geo = new BitmapText(getGuiFont(), false);
        geo.setSize(getGuiFont().getCharSet().getRenderedSize()/5);
        geo.setText(Integer.toString(armada));
        geo.setColor(vratBarvuArmada(hrac));
        geo.setName("vojacix"+x+"x"+y);
        geo = velikost(geo);
        geo.setLocalTranslation(geo.getLocalTranslation().x+0.037f,geo.getLocalTranslation().y+0.035f,geo.getLocalTranslation().z+1.0f);
        geo.setLocalScale(geo.getLocalScale().x/5.0f,geo.getLocalScale().y/5.0f,geo.getLocalScale().z/5.0f); 
        return geo;
    } 
    
     public BitmapText kresliPocetSurovin() {
        BitmapText geo = new BitmapText(getGuiFont(), false);
        geo.setSize(getGuiFont().getCharSet().getRenderedSize()/5);
        geo.setText(Integer.toString(pocet)); 
        geo.setColor(vratBarvuSuroviny(typ));
        geo.setName("surovinax"+x+"x"+y);
        geo = velikost(geo);
        geo.setLocalTranslation(geo.getLocalTranslation().x+0.0355f,geo.getLocalTranslation().y+0.055f,geo.getLocalTranslation().z+2.0f);
        geo.setLocalScale(geo.getLocalScale().x/5.0f,geo.getLocalScale().y/5.0f,geo.getLocalScale().z/5.0f); 
        return geo;
    } 
      
     public BitmapText kresliPoslaniVojaci() {
        BitmapText geo = new BitmapText(getGuiFont(), false);
        geo.setSize(getGuiFont().getCharSet().getRenderedSize()/5);
        geo.setText("("+Integer.toString(poslat)+")");
        geo.setColor(vratBarvuArmada(hrac));
        geo.setName("utokx"+x+"x"+y);
        geo = velikost(geo);
        geo.setLocalTranslation(geo.getLocalTranslation().x+0.0565f,geo.getLocalTranslation().y+0.035f,geo.getLocalTranslation().z+1.0f);
        geo.setLocalScale(geo.getLocalScale().x/5.0f,geo.getLocalScale().y/5.0f,geo.getLocalScale().z/5.0f); 
        return geo;
    }       
       
     public Geometry kresliObrys() {         
        Vector3f[] vertices = vrcholy();          
        Mesh m = new Mesh();
        m.setMode(Mesh.Mode.Lines);
        m.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));      
        int[] indexes = {0,1,1,2,2,3,3,4,4,5,5,0};        
        m.setBuffer(VertexBuffer.Type.Index, 2, indexes);
        m.updateBound();
        m.updateCounts();
        Geometry geo=new Geometry("line",m);
        Material mat = new Material(getManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        geo.setMaterial(mat);      
        geo = velikost(geo);
        geo.setName("polex"+x+"x"+y);
        geo.setLocalTranslation(geo.getLocalTranslation().x,geo.getLocalTranslation().y,geo.getLocalTranslation().z+0.5f);
        return geo;
    }
      
      public Geometry kresliCloveka(int kde) {
        Box b = new Box(1, 1, 1);
        Geometry geo = new Geometry("Box", b);
        geo = velikost(geo);
        geo.setName("centrumx"+x+"x"+y);        
        Material mat = new Material(getManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = getManager().loadTexture("obrazky/clovek.png"); 
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        if (kde==0) {
            geo.setName("cloveksx"+x+"x"+y);
            geo.setLocalTranslation(geo.getLocalTranslation().x+0.055f,geo.getLocalTranslation().y+0.045f,geo.getLocalTranslation().z+0.99f);
        }
        if (kde==1) {
            geo.setName("clovekbx"+x+"x"+y);
            geo.setLocalTranslation(geo.getLocalTranslation().x+0.046f,geo.getLocalTranslation().y+0.07f,geo.getLocalTranslation().z+0.99f);
        }
        
        geo.setLocalScale(geo.getLocalScale().x/2.5f,geo.getLocalScale().y/2.5f,geo.getLocalScale().z/2.5f); 
        mat.setTexture("ColorMap", textura);
        geo.setMaterial(mat);
        return geo;
      }
      
     public Geometry kresliArmadu() {
        Box b = new Box(1, 1, 1);
        Geometry geo = new Geometry("Box", b);
        geo = velikost(geo);
        geo.setName("valkax"+x+"x"+y);        
        Material mat = new Material(getManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = getManager().loadTexture("obrazky/armada.gif"); 
        mat.setTexture("ColorMap", textura);
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha); 
        geo.setMaterial(mat);
        geo.setLocalScale(geo.getLocalScale().x/3.0f,geo.getLocalScale().y/3.0f,geo.getLocalScale().z/3.0f);      
        geo.setLocalTranslation(geo.getLocalTranslation().x+0.03f,geo.getLocalTranslation().y+0.025f,geo.getLocalTranslation().z+1.0f);
        return geo;
      }
     
      public Geometry kresliSurovinu() {
        Box b = new Box(1, 1, 1);
        Geometry geo = new Geometry("Box", b);
        geo = velikost(geo);
        geo.setName("surovinapozadix"+x+"x"+y);        
        Material mat = new Material(getManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Texture textura = getManager().loadTexture("obrazky/s"+typ+"1.gif"); 
        mat.setTexture("ColorMap", textura);
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha); 
        geo.setMaterial(mat);
        geo.setLocalScale(geo.getLocalScale().x/1.1f,geo.getLocalScale().y/1.1f,geo.getLocalScale().z/1.1f);      
        geo.setLocalTranslation(geo.getLocalTranslation().x+0.04f,geo.getLocalTranslation().y+0.045f,geo.getLocalTranslation().z+0.75f);
        return geo;
      }
     
            
       public Geometry kresliCentrum() {
          Box b = new Box(1, 1, 1);
          Geometry geo = new Geometry("Box", b);
          geo = velikost(geo);
          geo.setName("centrumx"+x+"x"+y);        
          Material mat = new Material(getManager(), "Common/MatDefs/Misc/Unshaded.j3md");
          Texture textura = getManager().loadTexture("obrazky/centrum.png"); 
          mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
          mat.setTexture("ColorMap", textura);
          geo.setMaterial(mat);
          geo.setLocalScale(geo.getLocalScale().x/3.0f,geo.getLocalScale().y/3.0f,geo.getLocalScale().z/3.0f);      
          geo.setLocalTranslation(geo.getLocalTranslation().x+0.011f,geo.getLocalTranslation().y+0.05f,geo.getLocalTranslation().z+1.0f);
          return geo;
      }
        
    
      public Geometry kresliBudovu() {
            Box b = new Box(1, 1, 1);
            Geometry geo = new Geometry("Box", b);
            Material mat = new Material(getManager(), "Common/MatDefs/Misc/Unshaded.j3md");       
            Texture textura = getManager().loadTexture("obrazky/b"+getBudova()+"1.gif"); 
            geo.setName("budovax"+x+"x"+y);
            mat.setTexture("ColorMap", textura);
            mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha); 
            geo.setMaterial(mat);
            geo = velikost(geo);
            geo.setLocalTranslation(geo.getLocalTranslation().x+0.03f,geo.getLocalTranslation().y+0.07f,geo.getLocalTranslation().z+1.0f);
            geo.setLocalScale(0.01f);
            return geo;
        }
          

    public AssetManager getManager() {
        return manager;
    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }

    public int getHrac() {
        return hrac;
    }

    public void setHrac(int hrac) {
        this.hrac = hrac;
    }

    public int getTyp() {
        return typ;
    }

    public int getPocet() {
        return pocet;
    }

    public int getBudova() {
        return budova;
    }

    public void setBudova(int budova) {
        this.budova = budova;
    }

    public int getArmada() {
        return armada;
    }

    public void setArmada(int armada) {
        this.armada = armada;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setOdhalene(int odhalene) {
        this.odhalene = odhalene;
    }

    public int getOblast() {
        return oblast;
    }

    public void setOblast(int oblast) {
        this.oblast = oblast;
    }

    public int getClovekb() {
        return clovekb;
    }

    public void setClovekb(int clovekb) {
        this.clovekb = clovekb;
    }

    public int getCloveks() {
        return cloveks;
    }

    public void setCloveks(int cloveks) {
        this.cloveks = cloveks;
    }

    public int getPoslat() {
        return poslat;
    }

    public void setPoslat(int poslat) {
        this.poslat = poslat;
    }
               
}

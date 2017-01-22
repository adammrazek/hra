package mygame;

import com.jme3.collision.CollisionResult;
import java.util.ArrayList;
import java.util.Iterator;

public class Objekt {
   private String nazev = "";
   private int x = 0;
   private int y = 0;
   private ArrayList<String> povoleno = new ArrayList<String>();
   

  public Objekt(CollisionResult collision) {
        povoleno.add("vojaci");
        povoleno.add("pole");
        povoleno.add("clovek");
        povoleno.add("valka");
        povoleno.add("budova");
        povoleno.add("surovina");
        povoleno.add("pokrok");    
        povoleno.add("utok"); 
        povoleno.add("centrum"); 
        povoleno.add("ziskat");
        povoleno.add("zlato");     
        povoleno.add("konectahu");
        povoleno.add("pravidla");
        povoleno.add("pozadi");
        zjistiNazev(collision);
    }

    public void zjistiNazev(CollisionResult collision) { 
        String text="";
        text = collision.getGeometry().getName();
        if (obsahuje(text)==false) { text = collision.getGeometry().getParent().getName();   }
        if (obsahuje(text)==false) {
        String targetxx="";
        try   {targetxx = collision.getGeometry().getParent().getParent().getName();
             if ((targetxx!="")||(targetxx!="Root Node")) text = collision.getGeometry().getParent().getParent().getName(); 
        } catch(NullPointerException e ) {} 
        }
        String[] parts = text.split("x");
        nazev = parts[0]; 
        if (parts.length>1) x=Integer.parseInt(parts[1]); 
        if (parts.length>2) y=Integer.parseInt(parts[2]); 
    }
    
    public boolean obsahuje (String text) {
        boolean obsahuje = false;
              for (Iterator<String> it = povoleno.iterator(); it.hasNext();) {
              String dalsi = it.next();
              if (text.contains(dalsi)) obsahuje=true;
              }
        return obsahuje;
    }

    public String getNazev() {
        return nazev;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
  
}

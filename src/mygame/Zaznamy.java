package mygame;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Zaznamy {
        private String dbURL = "jdbc:derby://localhost:1527/Hra;create=true;user=Uzivatel;password=heslo";
        private Connection conn = null;
        private Statement stmt = null;
        private String limit = "";

        public Zaznamy() {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                conn = DriverManager.getConnection(dbURL); 
            } catch (Exception except)  { except.printStackTrace(); }
        }

        
        public int prepocitejTah(int tah) {
            if (tah>33) tah=33;
            return tah;
        }
    

        public void vloz(String vloz) {
            try {
                stmt = conn.createStatement();
                stmt.execute(vloz);
                stmt.close();
            } catch (SQLException sqlExcept) { sqlExcept.printStackTrace(); }
        }
    
    
        public void nastavViteznehoHrace(int vyhral, String adresa) {
            String inserta="UPDATE koneckola SET vitez="+vyhral+" WHERE idhry='"+adresa+"'";
            String insertb="UPDATE tah SET vitez="+vyhral+" WHERE idhry='"+adresa+"'";
            String insertc="UPDATE pole SET vitez="+vyhral+" WHERE idhry='"+adresa+"'";      
            vloz(inserta);
            vloz(insertb);
            vloz(insertc); 
       }
        
        
      public void zapisKonecKola(Vyhledavac tah) { 
          int hracnatahu = tah.getHracnatahu();
          int kolo = tah.getKolo();
        String insert = "INSERT INTO koneckola (IDHRY, HRAC, VITEZ, TAH, KULTURA, VEDA, OBILI, ROPA, ZELEZO, KAMEN, ARMADA, LIDE, CELKEMPOLI, MOZNOOBILI, OBILIPRODUKCE, MOZNOROPA, ROPAPRODUKCE, MOZNOZELEZO, ZELEZOPRODUKCE, MOZNOKAMEN, KAMENPROUDKCE, MOZNOZLATO, ZLATOPRODUKCE, OBILIMINUS, ROPAMINUS, ZELEZOMINUS, ZAKLADNY, ZAKLADNYC, ZAKLADNYN, LABORATORE, LABORATOREC, LABORATOREN, TOVARNY, TOVARNYC, TOVARNYN, MESTA, MESTAC, MESTAN, KATEDRALY, KATEDRALYC, KATEDRALYN, POBILI, PROPA, PZELEZO, PKAMEN, PUTOK, POBRANA, PNAHODA)  " 
                + "VALUES ('"+tah.getAdresa()+"', "+hracnatahu+", 0, "+kolo+", "+tah.najdiHrace(hracnatahu).getKultura()+", "+tah.najdiHrace(hracnatahu).getVeda()+", "+tah.najdiHrace(hracnatahu).getObili()
                +", "+tah.najdiHrace(hracnatahu).getRopa()+", "+tah.najdiHrace(hracnatahu).getZelezo()+", "+tah.najdiHrace(hracnatahu).getKamen()
                +", "+tah.najdiUdajeOHraci("armada", 1, hracnatahu)+", "+tah.najdiUdajeOHraci("clovek", 1, hracnatahu)
                +", "+tah.najdiPolePatriciHraci(hracnatahu).size()+", "+tah.najdiUdajeOHraci("obili", 2, hracnatahu)+", "+tah.najdiUdajeOHraci("obili", 1, hracnatahu)
                +", "+tah.najdiUdajeOHraci("ropa", 2, hracnatahu)+", "+tah.najdiUdajeOHraci("ropa", 1, hracnatahu)+", "+tah.najdiUdajeOHraci("zelezo", 2, hracnatahu)
                +", "+tah.najdiUdajeOHraci("zelezo", 1, hracnatahu)+", "+tah.najdiUdajeOHraci("kamen", 2, hracnatahu)+", "+tah.najdiUdajeOHraci("kamen", 1, hracnatahu)
                +", "+tah.najdiUdajeOHraci("zlato", 2, hracnatahu)+", "+tah.najdiUdajeOHraci("zlato", 1, hracnatahu)+", "+(tah.najdiUdajeOHraci("obili", 1, hracnatahu)-tah.najdiUdajeOHraci("clovek", 1, hracnatahu))
                +", "+(tah.najdiUdajeOHraci("ropa", 1, hracnatahu)-tah.najdiUdajeOHraci("armada", 1, hracnatahu))+", "+(tah.najdiUdajeOHraci("zelezo", 1, hracnatahu)-tah.najdiUdajeOHraci("tovarna", 1, hracnatahu))
                +", "+tah.najdiUdajeOHraci("zakladna", 2, hracnatahu)+", "+tah.najdiUdajeOHraci("zakladna", 1, hracnatahu)+", "+tah.najdiUdajeOHraci("zakladna", 0, hracnatahu)
                +", "+tah.najdiUdajeOHraci("laborator", 2, hracnatahu)+", "+tah.najdiUdajeOHraci("laborator", 1, hracnatahu)+", "+tah.najdiUdajeOHraci("aborator", 0, hracnatahu)
                +", "+tah.najdiUdajeOHraci("tovarna", 2, hracnatahu)+", "+tah.najdiUdajeOHraci("tovarna", 1, hracnatahu)+", "+tah.najdiUdajeOHraci("tovarna", 0, hracnatahu)
                +", "+tah.najdiUdajeOHraci("mesto", 2, hracnatahu)+", "+tah.najdiUdajeOHraci("mesto", 1, hracnatahu)+", "+tah.najdiUdajeOHraci("mesto", 0, hracnatahu)
                +", "+tah.najdiUdajeOHraci("katedrala", 2, hracnatahu)+", "+tah.najdiUdajeOHraci("katedrala", 1, hracnatahu)+", "+tah.najdiUdajeOHraci("katedrala", 0, hracnatahu)
                +", "+tah.najdiHrace(hracnatahu).vratPokrok(1)+", "+tah.najdiHrace(hracnatahu).vratPokrok(2)+", "+tah.najdiHrace(hracnatahu).vratPokrok(3)+", "+tah.najdiHrace(hracnatahu).vratPokrok(4)+", "+tah.najdiHrace(hracnatahu).vratPokrok(5)+", "+tah.najdiHrace(hracnatahu).vratPokrok(6)+", "+tah.najdiHrace(hracnatahu).vratPokrok(7)+")";
                vloz(insert);
                for (Iterator<Pole> it = tah.najdiPolePatriciHraci(hracnatahu).iterator(); it.hasNext();) {
                    Pole poleh = it.next();
                    String poledb = "INSERT INTO pole (IDHRY, HRAC, VITEZ, TAH, X, Y, TYPPOLE, POCET, BUDOVA, ARMADA, CLOVEKS, CLOVEKB) " 
                            + "VALUES ('"+tah.getAdresa()+"', "+hracnatahu+", 0, "+kolo+","+prevedSouradnice(poleh,hracnatahu)[0]+","+prevedSouradnice(poleh,hracnatahu)[1]+","+poleh.getTyp()
                            +","+poleh.getPocet()+","+poleh.getBudova()+","+poleh.getArmada()+","+poleh.getCloveks()+","+poleh.getClovekb()+")";
                    vloz(poledb);  
                }
      }
    
    
        public double vratCelkovyPocetHer(int tah) {
            tah=prepocitejTah(tah);
            double pocether = 0;
            try {
            stmt = conn.createStatement();
            ResultSet celkem = stmt.executeQuery("select COUNT(DISTINCT idhry) from KONECKOLA where vitez=hrac "+limit+" AND tah="+tah+"");
            if(celkem.next()) pocether = celkem.getInt(1);
            celkem.close();
            stmt.close();
              } catch (SQLException sqlExcept)  { sqlExcept.printStackTrace(); }
            return pocether;
        }
          
 
      public double[] spocitejPravdepodobnostAkce(int tah) {
            tah=prepocitejTah(tah);
            double pocettahu = vratCelkovyPocetHer(tah)*3;
            double[] procento = {0,0,0,0,0,0};
            try  {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select typ, COUNT(*) AS typakce from TAH where vitez=hrac "+limit+"  AND tah="+tah+" GROUP BY typ");
            while(rs.next()){
            if (rs.getString("typ").equals("utok")) procento[0] = rs.getDouble("typakce")/pocettahu;
            if (rs.getString("typ").equals("presun")) procento[1] = rs.getDouble("typakce")/pocettahu;
            if (rs.getString("typ").equals("postavit")) procento[2] = rs.getDouble("typakce")/pocettahu;
            if (rs.getString("typ").equals("kuparmadu")) procento[3] = rs.getDouble("typakce")/pocettahu;
            if (rs.getString("typ").equals("pokrok")) procento[4] = rs.getDouble("typakce")/pocettahu;
            if (rs.getString("typ").equals("ziskat")) procento[5] = rs.getDouble("typakce")/pocettahu;
            }
            } catch (SQLException sqlExcept) { sqlExcept.printStackTrace(); }
            return procento;
        }   
      
      
      public double[] spocitejVelicinyZaKolo(int tah) {
            tah=prepocitejTah(tah);
            double[] produkce = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            double celkemher = vratCelkovyPocetHer(tah);
            try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select SUM(kultura) AS kultura, SUM(veda) AS veda, SUM(obili) AS obili, SUM(ropa) AS ropa, "
                    + "SUM(zelezo) AS zelezo, SUM(kamen) AS kamen, SUM(armada) AS armada, SUM(moznoobili) AS moznoobili, SUM(moznoropa) AS moznoropa, "
                    + "SUM(moznozelezo) AS moznozelezo, SUM(moznokamen) AS moznokamen, SUM(moznozlato) AS moznozlato, SUM(zakladny) AS zakladny, "
                    + "SUM(laboratore) AS laboratore, SUM(tovarny) AS tovarny, SUM(mesta) AS mesta, SUM(katedraly) AS katedraly, SUM(pobili) as pobili, "
                    + "SUM(propa) AS propa, SUM(pzelezo) AS pzelezo, SUM(pkamen) AS pkamen, SUM(putok) AS putok, SUM(pobrana) AS pobrana, "
                    + "SUM(pnahoda) AS pnahoda from KONECKOLA where vitez=hrac "+limit+"  AND tah="+tah+"");
            if(rs.next()) {
            produkce[0] = rs.getDouble("kultura")/celkemher;
            produkce[1] = rs.getDouble("veda")/celkemher;
            produkce[2] = rs.getDouble("armada")/celkemher;
            produkce[3] = rs.getDouble("obili")/celkemher;
            produkce[4] = rs.getDouble("ropa")/celkemher;
            produkce[5] = rs.getDouble("zelezo")/celkemher;
            produkce[6] = rs.getDouble("kamen")/celkemher;
            produkce[7] = rs.getDouble("moznoobili")/celkemher;
            produkce[8] = rs.getDouble("moznoropa")/celkemher;
            produkce[9] = rs.getDouble("moznozelezo")/celkemher;
            produkce[10] = rs.getDouble("moznokamen")/celkemher;
            produkce[11] = rs.getDouble("moznozlato")/celkemher;
            produkce[12] = rs.getDouble("zakladny")/celkemher;
            produkce[13] = rs.getDouble("laboratore")/celkemher;
            produkce[14] = rs.getDouble("tovarny")/celkemher;
            produkce[15] = rs.getDouble("mesta")/celkemher;
            produkce[16] = rs.getDouble("katedraly")/celkemher;
            produkce[17] = rs.getDouble("pobili")/celkemher;
            produkce[18] = rs.getDouble("propa")/celkemher;
            produkce[19] = rs.getDouble("pzelezo")/celkemher;
            produkce[20] = rs.getDouble("pkamen")/celkemher;
            produkce[21] = rs.getDouble("putok")/celkemher;
            produkce[22] = rs.getDouble("pobrana")/celkemher;
            produkce[23] = rs.getDouble("pnahoda")/celkemher;
            }
            rs.close();
             } catch (SQLException sqlExcept)  { sqlExcept.printStackTrace(); }
            return produkce;
        }            
            
            
      public int[] prevedSouradnice (int x, int y, int hrac) { // select z databáze
        int[] souradnice = {0,0};
        if (hrac==1) {souradnice[0]=x; souradnice[1]=12-y;}
        if (hrac==2) {souradnice[0]=x; souradnice[1]=y;}
        if (hrac==3) {
            if (y%2==1) souradnice[0]=11-x;
            else souradnice[0]=12-x;
            souradnice[1]=y;}
        if (hrac==4) {
            if (y%2==1) souradnice[0]=11-x;
            else souradnice[0]=12-x;
            souradnice[1]=12-y;}        
        return souradnice;
    }
      
      public int[] prevedSouradnice (Pole uzemi, int hrac) { // do databáze
        int[] souradnice = {0,0};
        if (hrac==1) {souradnice[0]=uzemi.getX(); souradnice[1]=12-uzemi.getY();}
        if (hrac==2) {souradnice[0]=uzemi.getX(); souradnice[1]=uzemi.getY();}
        if (hrac==3) {
            if (uzemi.getY()%2==1) souradnice[0]=11-uzemi.getX();
            else souradnice[0]=12-uzemi.getX();
            souradnice[1]=uzemi.getY();}
        if (hrac==4) {
            if (uzemi.getY()%2==1) souradnice[0]=11-uzemi.getX();
            else souradnice[0]=12-uzemi.getX();
            souradnice[1]=12-uzemi.getY();}        
        return souradnice;
    }         

          
     public ArrayList<Policko> vratInfluencniMapu(int tah, int hrac) {
        tah=prepocitejTah(tah);
        ArrayList<Policko> seznampoli = new ArrayList<Policko>();
        int i = 0;
        int xxx = 0;
        int yyy = 1;
        double vydelit = vratCelkovyPocetHer(tah);
        while (i<115) {
        xxx++;
        seznampoli.add(new Policko(xxx, yyy));
        i++;
        if (xxx>9 && yyy % 2 == 1) {xxx=0; yyy++;}
        if (xxx>10) { xxx=0; yyy++;}
        }
                
        try {
        stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select x, y, budova, COUNT(*) AS pocet from Pole where tah="+tah+" "+limit+"  AND hrac=vitez GROUP BY x,y,budova");
            while(rs.next()){
                int[] souradnice = prevedSouradnice(rs.getInt("x"), rs.getInt("y"), hrac);
                double budova = rs.getDouble("budova");
                double pocet = 0;
                pocet = rs.getDouble("pocet")/vydelit;
                Policko uzemi = najdiPolicko(seznampoli, souradnice[0], souradnice[1]);
                if (budova==0) uzemi.setBezbudovy(pocet);
                if (budova==1) uzemi.setZakladna(pocet);
                if (budova==2) uzemi.setLaborator(pocet);
                if (budova==3) uzemi.setTovarna(pocet);
                if (budova==4) uzemi.setMesto(pocet);
                if (budova==5) uzemi.setKatedrala(pocet);
                }
            
       rs = stmt.executeQuery("select x, y, COUNT(*) AS celkem, AVG(armada) AS vojaci from Pole where tah="+tah+" AND hrac=vitez GROUP BY x,y");
            while(rs.next()){
                int[] souradnice = prevedSouradnice(rs.getInt("x"), rs.getInt("y"), hrac);
                Policko uzemi = najdiPolicko(seznampoli, souradnice[0], souradnice[1]);
                uzemi.setVlastnictvi(rs.getDouble("celkem")/vydelit);
                uzemi.setArmada(rs.getDouble("vojaci")/vydelit);
                }
   
        } catch (SQLException sqlExcept)  { sqlExcept.printStackTrace(); }
        
            return seznampoli;
        }
          
     
     public Policko najdiPolicko(ArrayList<Policko> pole, int x, int y) {
        Policko uzemi = null;
        for (Iterator<Policko> it = pole.iterator(); it.hasNext();) {
            Policko polea = it.next();
            if ( (polea.getX()==x) && (polea.getY()==y) )  {uzemi = polea;}   
        }
        return uzemi;
    }

}
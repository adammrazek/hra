package mygame;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pravidla {
    String nadpis = "<h1>Pravidla hry Empire Wars</h1>";
    String akce = "<h2>Akce</h2>"
            + "Následující akce může hráč během hry provést. S výjimkou ničení jednotek a přemisťování lidí stojí všechny akce jeden tah. Celkem hráč může zahrát tři akce stojící tahy během svého jednoho kola.<br /><br />"
            + "<b>Zaútočit</b> — Útočit lze vždy pouze na sousední pole (ne na hory). Útočná síla se rovná počet útočících jednotek plus bonusy za vědecké pokroky a "
            + "obranná síla odpovídá počtu jednotek na bránícím se poli plus <br />bonusy za pokroky a případně vojenskou základnu. Útočit lze tak, "
            + "že hráč levým tlačítkem myši klikne na jednotky na poli, postupně vybere, kolika jednotkami chce útočit a po té pravým tlačítkem klikne na cílové pole.<br />"
            + "<b>Postavit budovu</b> — K postavení budovy je třeba míst dostatek kamene (dle typu budovy). Postavení budovy probíhá kliknutím na centrum pole (dvě figurky uprostřed vlevo) a vybráním možnosti 'postavit budovu'.<br />"
            + "<b>Koupit dvě jednotky</b> — Za cenu pět železa lze koupit na libovolné pole patřící hráči dvě vojenské jednotky. Nákup jednotek probíhá kliknutím na centrum pole a vybráním možnosti 'koupit 2 jednotky'.<br />"
            + "<b>Získat suroviny dle výběru</b> — Hráč může během zdarma (za cenu jedné akce) získat dva kusy ropy, železa nebo kamene nebo čtyři kusy obilí. <br />Akce se provede kliknutím na jednu ze čtyř surovin pod nápisem vpravo nahoře vedle herní plochy 'získat suroviny'.<br />"
            + "<b>Vynalézt vědecký pokrok</b> — Výměnou za určitý počet technologií (vědy) může každý hráč vynalézt až sedm pokroků vylepšujících produkci surovin nebo válečné schopnosti. "
            + "<br />Akce se provede kliknutím na jeden obrázků reprezentujících vědecké pokroky vedle nápisu 'vynalézt pokrok'.<br />"
            + "<b>Zbourat budovu</b> — Zbourat lze libovolnou budovu, kterou hráč vlastní. Akce se provede kliknutím na centrum pole a vybráním možnosti 'zbourat budovu'.<br />"
            + "<b>Přemístit pracovníky</b> — Umístěním/odebráním pracovníka se určuje, zda se daná surovina bude produkovat, popřípadě, jestli je bude daná budova v provozu.<br />"
            + "Lidé se umísťují na práci kliknutím na číslo označující počet surovin na poli/příslušnou budovu. Naopak oddělat je lze kliknutím na příslušného pracovníka.<br />"
            + "<b>Zničit jednotky</b> — Jednotky se dají zničit kliknutím na centrum pole a vybráním možnosti zničit jednotky.<br />";
    String suroviny = "<h2>Suroviny</h2>Suroviny se produkují na polích obsazených lidmi (klikněte na číslo zobrazené uprostřed pole nad surovinou)<br />"
            + "<b>Obilí</b> — Slouží k nakrmení lidí. Každý pracující člověk spotřebovává jedno obilí za tah.<br />"
            + "<b>Ropa</b> — Slouží jako pohon pro jednotky. Každá jednotka spotřebovává jeden barel ropy za tah.<br />"
            + "<b>Železo</b> — Slouží k výrobě jednotek a jejich nákupu. Výroba dvou jednotek v jedné továrně stojí každé kolo jeden kus železa.<br />"
            + "<b>Kámen</b> — Slouží ke stavbě budov."
            + "<br /><b>Zlato</b> — Hned po vyprodukování je potřeba vyměnit za jednu ze čtyř ostatních surovin (jeden kus ropy, železa nebo kamene nebo dva kusy obilí).";
    String budovy = "<h2>Budovy</h2>Každá budova plní svou funkci pouze, pokud je obsazena člověkem.<br />"
            + "<b>Vojenská základna (5 kamene)</b> — Zvyšuje obranu pole o čtyři.<br />"
            + "<b>Laboratoř (10 kamene)</b> — Produkuje na konci každého kola jednu technologii.<br />"
            + "<b>Továrna (15 kamene)</b> — Produkuje na konci každého kola dvě jednotky. Spotřebovává jeden kus železa.<br />"
            + "<b>Město (20 kamene)</b> — Zvyšuje maximální možný počet lidí o sedm. Celkem je možno mít (7 + 7 * počet měst) lidí.<br />"
            + "<b>Katedrála (40 kamene)</b> — Produkuje jeden kulturní bod za kolo.";
    String pokroky = "<h2>Pokroky</h2>"
            + "<b>Geneticky upravené obilí (6 technologií)</b> — Každé pole s obilím produkuje o jeden kus obilí navíc.<br />"
            + "<b>Hlubinné vrty (6 technologií)</b> — Každý ropný vrt produkuje o jeden barel ropy navíc.<br />"
            + "<b>Metalurgie (6 technologií)</b> — Každá železná ruda produkuje o jeden kus železa navíc.<br />"
            + "<b>Dynamit (6 technologií)</b> — Každý kamenolom produkuje o jeden kus kamene navíc.<br />"
            + "<b>Stíhačky (9 technologií)</b> — Síla útoku je větší o jedna.<br />"
            + "<b>Opevnění (7 technologií)</b> — Síla obrany je větší o jedna.<br />"
            + "<b>Bojová taktika (4 technologií)</b> — Náhodný faktor při útoku a obraně je na vaší straně.";
    String cilhry = "<h2>Cíl hry</h2>Cílem hry je získat 100 kulturních bodů.";
  
    public Pravidla() {
                zobrazPravidla();
            }
      
    public void zobrazPravidla() {
        JFrame frame = new JFrame("Pravidla");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JPanel panel = new JPanel();
                JLabel label = new JLabel();
                Font f = label.getFont();
                label.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
                label.setText("<html>"+nadpis+akce+suroviny+budovy+pokroky+cilhry+"</html>");
                panel.add(label);
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
    }
    
}

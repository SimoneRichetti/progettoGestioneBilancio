package grafica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import vociBilancio.VoceDiBilancio;

/**
 * Classe utilizzata per creare il pannello della schermata principale. Sono
 * creati e configurati i componenti grafici per la visualizzazione e la 
 * gestione della tabella e per la ricerca di voci in quest'ultima.
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see PannelloAggiungiModifica
 * @see ModelloTabellaVoci
 *
 */
public class PannelloPrincipale extends JPanel implements ActionListener{
	/**
	 * Attributo per evitare problemi di serializzazione
	 */
	private static final long serialVersionUID = 1L;
	/** Vettore di voci rappresentante l'attuale bilancio */
	private Vector<VoceDiBilancio> bilancio;
	/** Bottone per l'aggiunta di una voce di bilancio */
	private JButton aggiungi;
	/** Bottone per la rimozione di una voce di bilancio */
	private JButton rimuovi;
	/** Bottone per la modifica di una voce di bilancio */
	private JButton modifica;
	/** Bottone per la ricerca di una determinata voce nella tabella */
	private JButton cerca;
	/** Bottone per proseguire nella ricerca di una voce */
	private JButton successivo;
	/**
	 * Modello della tabella contenente il bilancio
	 * @see ModelloTabellaVoci 
	 */
	private ModelloTabellaVoci modelloTabella;
	/** Tabella per la visualizzazione grafica del bilancio */
	private JTable tabella;
	/** Frame della schermata principale contenente il pannello */
	private JFrame framePrincipale;
	/** Label utilizzata nel riquadro in cui viene mostrato il totale del bilancio */
	private JLabel totale;
	/** Casella testuale in cui viene mostrato il totale del bilancio */
	private JTextField sommaTotale;
	/** Casella testuale in cui viene inserita la parola chiave per la ricerca */
	private JTextField barraRicerca;
	
	/**
	 * Costruttore del pannello, il quale inizializza tutti i componenti
	 * grafici, li lega all'<code>ActionListener</code> e li dispone nella 
	 * finestra mediante un <code>MigLayout</code>.
	 * @param bilancio Vettore di voci contenente il bilancio attuale
	 * @param framePrincipale Frame contenente il pannello
	 * @see FramePrincipale
	 * @see ModelloTabellaVoci 
	 */
	public PannelloPrincipale(Vector<VoceDiBilancio> bilancio, JFrame framePrincipale) {
		super();
		this.bilancio=bilancio;
		this.framePrincipale=framePrincipale;
		
		sommaTotale=new JTextField(12);
		sommaTotale.setText("0");
		sommaTotale.setEditable(false);
		
		modelloTabella=new ModelloTabellaVoci(bilancio,sommaTotale);
		tabella=new JTable(modelloTabella);
		
		totale=new JLabel("Totale:");
		
		barraRicerca=new JTextField(20);
		
		aggiungi=new JButton("Aggiungi");
		rimuovi=new JButton("Rimuovi");
		modifica=new JButton("Modifica");
		cerca=new JButton("Cerca");
		successivo=new JButton("Successivo");
		successivo.setEnabled(false);
		
		aggiungi.addActionListener(this);
		rimuovi.addActionListener(this);
		modifica.addActionListener(this);
		cerca.addActionListener(this);
		successivo.addActionListener(this);
		
		MigLayout layout=new MigLayout();
		setLayout(layout);
		
		add(aggiungi, "split");
		add(rimuovi, "split");
		add(modifica, "wrap");
		add(new JScrollPane(tabella), "span");
		add(totale, "split");
		add(sommaTotale,"wrap");
		add(barraRicerca, "split");
		add(cerca, "split");
		add(successivo);
	}
	
	/**
	 * Ascoltatore degli eventi dei componento grafici del pannello.
	 * Gestisce gli eventi dei seguenti bottoni:
	 * -Aggiungi: crea una finestra per l'aggiunta di una voce nel bilancio;
	 * 
	 * -Rimuovi: se almeno una casella e' selezionata, rimuove la relativa
	 *  voce. Nel caso siano selezionate piu' caselle, rimuove solo la prima;
	 *  
	 * -Modifica: se almeno una casella e' selezionata, crea una finestra per
	 *  la sua modifica. Nel caso siano selezionate piu' caselle, modifica
	 *  solo la prima;
	 *  
	 * -Cerca: invoca la funzione di ricerca di una voce nella tabella basandosi
	 *  sulla stringa inserita dall'utente nel TextField <code>barraRicerca</code>; 
	 *   
	 * -Successivo: cerca la successiva voce corrispondente all'ultima ricerca
	 *  fatta;
	 * @param e Evento generato dai componenti grafici del pannello
	 * @see PannelloAggiungiModifica
	 * @see ModelloTabellaVoci#aggiornaTabella()
	 * @see ModelloTabellaVoci#nuovaRicercaTabella(String, JTable, JButton)
	 * @see ModelloTabellaVoci#ricercaTabella(JTable, JButton)
	 * @see AscoltatoreFrame
	 */
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Aggiungi": {
			JFrame f=new JFrame("Aggiungi");
			f.addWindowListener(new AscoltatoreFrame(f, framePrincipale));
			f.add(new PannelloAggiungiModifica(f, bilancio, modelloTabella, framePrincipale,-1));
			f.pack();
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.setVisible(true);
			framePrincipale.setEnabled(false);
			break;
		}
		case "Rimuovi": {
			int idx=tabella.getSelectedRow();
			if(idx!=-1){
				/* Non viene passato l'indice della voce nel bilancio, bensi'
				 * nella tabella, e non e' scontato che siano uguali.
				 */
				bilancio.remove(bilancio.indexOf(modelloTabella.getVettoreBase().get(idx)));
				modelloTabella.aggiornaTabella();
			}
			break;
		}
		case "Modifica":
			int idx=tabella.getSelectedRow();
			if(idx!=-1){
				JFrame f=new JFrame("Modifica");
				f.addWindowListener(new AscoltatoreFrame(f, framePrincipale));
				f.add(new PannelloAggiungiModifica(f, bilancio, modelloTabella, framePrincipale,
						idx));
				f.pack();
				f.setResizable(false);
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setVisible(true);
				framePrincipale.setEnabled(false);
			}
			break;
		case "Cerca":
			String ricerca=barraRicerca.getText();
			if(ricerca.isEmpty())
				break;
			else
				modelloTabella.nuovaRicercaTabella(ricerca, tabella, successivo);
			break;
		case "Successivo":
			modelloTabella.ricercaTabella(tabella, successivo);
		default:
			break;
		}
	}

	/**
	 * Restituisce il modello della tabella delle voci.
	 * @return Modello della tabella delle voci
	 */
	public ModelloTabellaVoci getModelloTabella() {
		return modelloTabella;
	}
	
	/**
	 * Restituisce la tabella delle voci.
	 * @return Tabella delle voci
	 */
	public JTable getTabella() {
		return tabella;
	}
}

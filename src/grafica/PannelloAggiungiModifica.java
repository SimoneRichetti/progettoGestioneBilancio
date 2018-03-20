package grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import net.miginfocom.swing.MigLayout;
import vociBilancio.ComparatoreVoci;
import vociBilancio.VoceDiBilancio;
import vociBilancio.VoceInEntrata;
import vociBilancio.VoceInUscita;

/**
 * Classe utilizzata per creare il pannello della schermata di aggiunta o 
 * modifica di una voce. Sono creati e configurati i componenti grafici per
 * l'inserimento e la modifica dei dati della voce da parte dell'utente ed e' 
 * gestita l'aggiunta della nuova voce al bilancio.
 * 
 * @author Simone Richetti
 * @version 1.0
 *
 */
public class PannelloAggiungiModifica extends JPanel implements ActionListener {
	/** Label per indicare dove inserire l'importo */
	private JLabel importo;
	/** Label per indicare dove inserire la descrizione */
	private JLabel descrizione;
	/** Label per indicare dove inserire la data */
	private JLabel data;
	/** Casella di testo in cui inserire l'importo */
	private JTextField importoT;
	/** Casella di testo in cui inserire la descrizione */
	private JTextField descrizioneT;
	/** Gruppo di radio button usato per la scelta di una voce di entrata o uscita */
	private ButtonGroup grp;
	/** RadioButton per selezionare una voce di bilancio in entrata */
	private JRadioButton entrata;
	/** RadioButton per selezionare una voce di bilancio in uscita */
	private JRadioButton uscita;
	/** Selettore data della voce di bilancio */
	private JDateChooser selettoreData;
	/** Bottone per concludere l'aggiunta/la modifica della voce */
	private JButton ok;
	/** Bottone per annullare la modifica o l'aggiunta */
	private JButton annulla;
	/** Frame contenitore del pannello */
	private JFrame frameContenitore;
	/** Frame contenente la schermata principale del nostro programma */
	private JFrame framePrincipale;
	/** Vettore di voci rappresentante l'attuale bilancio */
	private Vector<VoceDiBilancio> bilancio;
	/** 
	 * Modello tabella per la visualizzazione del bilancio
	 * @see ModelloTabellaVoci
	 */
	private ModelloTabellaVoci modelloTabella;
	/** 
	 * Booleano utilizzato in caso di modifica di una voce, indica se la voce sia
	 * in uscita o in entrata
	 */
	private boolean isUscita;
	/** Voce di bilancio modificata, attributo non utilizzato in caso di aggiunta */
	private VoceDiBilancio voce;
	/** 
	 * Indice della riga della voce da modificare nella tabella. Se il pannello
	 * e' utilizzato per l'aggiunta, l'indice vale -1
	 */
	private int indice;
	/**
	 * UID per evitare problemi di serializzazione.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore che inizializza tutti i componenti grafici della finestra,
	 * li lega all'<code>ActionListener</code> e li dispone utilizzando un <code>MigLayout</code>. In caso
	 * di modifica, recupera dal bilancio la voce da modificare e imposta
	 * i componenti utilizzati per modificarla sui suoi valori attuali.
	 * @param frameContenitore Frame contenitore del pannello
	 * @param bilancio Vettore contenente il bilancio
	 * @param modelloTabella Modello della tabella utilizzata per visualizzare il bilancio
	 * @param framePrincipale Frame contenente la finestra principale del programma
	 * @param indice Indice della riga della voce da modificare nella tabella
	 */
	public PannelloAggiungiModifica(JFrame frameContenitore, Vector<VoceDiBilancio> bilancio, 
			ModelloTabellaVoci modelloTabella, JFrame framePrincipale,
			int indice){
		
		super();
		this.frameContenitore=frameContenitore;
		this.framePrincipale=framePrincipale;
		this.bilancio=bilancio;
		this.modelloTabella=modelloTabella;
		this.indice=indice;
		this.voce=null;
		
		importo=new JLabel("Importo:");
		descrizione=new JLabel("Descrizione:");
		data=new JLabel("Data:");
		
		importoT=new JTextField(12);
		descrizioneT=new JTextField(30);
		
		entrata=new JRadioButton("Entrata");
		entrata.setActionCommand("entrata");
		uscita=new JRadioButton("Uscita");
		uscita.setActionCommand("uscita");
		grp=new ButtonGroup();
		grp.add(entrata);
		grp.add(uscita);
				
		selettoreData=new JDateChooser();
		selettoreData.getDateEditor().setEnabled(false);

		ok=new JButton("OK");
		annulla=new JButton("Annulla");
		ok.addActionListener(this);
		annulla.addActionListener(this);
		
		MigLayout layout = new MigLayout();
		setLayout(layout);
		
		if(indice>=0){
			voce=modelloTabella.getVettoreBase().elementAt(indice);
			if(voce==null){
				JOptionPane.showMessageDialog(this,
					    "Errore nella selezione della voce. Annullo modifica.",
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
				framePrincipale.setEnabled(true);
				frameContenitore.dispose();
			}
			importoT.setText(Double.toString(Math.abs(voce.getAmmontare())));
			descrizioneT.setText(voce.getDescrizione());
			selettoreData.setDate(voce.getData());
			isUscita=(voce.getAmmontare()<0);
		} else {
			selettoreData.setCalendar(Calendar.getInstance());
			isUscita=true;
		}
		
		if(isUscita)
			grp.setSelected(uscita.getModel(), true);
		else
			grp.setSelected(entrata.getModel(), true);
		
		add(importo, "split");
		add(importoT, "wrap");
		add(entrata, "split");
		add(uscita, "wrap");
		add(descrizione, "wrap");
		add(descrizioneT, "wrap");
		add(data, "split");
		add(selettoreData, "wrap");
		add(ok, "split");
		add(annulla);
		
	}

	/**
	 * Gestisce gli eventi dei bottoni "OK" e "Annulla". Nel caso
	 * l'utente prema ok, prende i campi inseriti nei vari campi del
	 * pannello e controlla la loro validita'.
	 * Per l'importo, la funzione <code>Double.parseDouble(String)</code> 
	 * controlla che la stringa inserita contenga effettivamente un numero.
	 * Per la descrizione, controlla che sia stata effettivamente inserita una stringa.
	 * Per la data, controlla che sia stata effettivamente inserita una data e,
	 * nel caso sia stata utilizzata la casella di testo del <code>JDateChooser</code>,
	 * controlla che la stringa inserita corrisponda ad una data. Viene considerata
	 * valida anche una data futura. Se l'input e' valido in tutti e tre i campi, 
	 * invoca la funzione di modifica o di aggiunta, a seconda dei casi. 
	 * Infine, sia che si prema ok che annulla, chiude la finestra .
	 * @param e Evento generato da un bottone
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="OK") {
			boolean inputCorretto = true;
			
			/* Prendo descrizione */
			String descrizione=descrizioneT.getText();
			if(descrizione=="" || descrizione==null){
				JOptionPane.showMessageDialog(this,
					    "Errore nell'inserimento dei dati. Annullo Aggiunta.",
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
				inputCorretto=false;
			}
			
			/* Controllo e prendo importo */
			double importo;
			try {
				importo=Double.parseDouble(importoT.getText());
			} catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,
					    "Errore nell'inserimento dei dati. Annullo Aggiunta. "
						+ex.getMessage(),
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
				importo=0;
				inputCorretto=false;
			}
			
			/* Controllo e prendo data */
			Date data = selettoreData.getDate();
			if(data==null) {
				inputCorretto=false;
				JOptionPane.showMessageDialog(this,
					    "Errore nell'inserimento dei dati. Annullo Aggiunta.",
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
			}
			
			/* Se input consistente, aggiungo a vettore */
			if(inputCorretto){
				if(indice>=0)
					modificaVoce(data,descrizione,importo);
				else
					aggiungiVoce(data,descrizione,importo);
				
				bilancio.sort(new ComparatoreVoci());
				modelloTabella.aggiornaTabella();
			}
		}
		framePrincipale.setEnabled(true);
		frameContenitore.dispose();
	}
	
	/**
	 * Crea una nuova voce.
	 * Prende in ingresso i campi di una voce di bilancio e, in
	 * base alla selezione dell'utente, crea una voce in entrata o in uscita e
	 * la aggiunge al bilancio
	 * @param data Data della nuova voce di bilancio
	 * @param descrizione Descrizione della nuova voce di bilancio
	 * @param importo Importo della nuova voce di bilancio
	 */
	private void aggiungiVoce(Date data, String descrizione, double importo) {
		VoceDiBilancio bil;
		if(grp.getSelection().getActionCommand()=="uscita")
			bil=new VoceInUscita(data,descrizione,importo);
		else
			bil=new VoceInEntrata(data,descrizione,importo);
		
		bilancio.addElement(bil);
	}
	
	/**
	 * Modifica una voce.
	 * Prende in ingresso i campi di una voce di bilancio e modifica
	 * la voce selezionata con tali campi. Nel caso si passi da una voce in entrata
	 * a una in uscita o viceversa, e' necessario rimuovere la voce precedente e
	 * crearne una nuova
	 * @param data Nuova data della voce
	 * @param descrizione Nuova descrizione della voce
	 * @param importo Nuovo importo della voce
	 */
	private void modificaVoce(Date data, String descrizione, double importo) {
		if(voce==null)
			return;
		if(isUscita && grp.getSelection().getActionCommand()=="entrata") {
			bilancio.remove(voce);
			bilancio.add(new VoceInEntrata(data,descrizione,importo));
		} else if(!isUscita && grp.getSelection().getActionCommand()=="uscita") {
			bilancio.remove(voce);
			bilancio.add(new VoceInUscita(data,descrizione,importo));
		} else {
			/*
			 * Nel caso sia attivo un filtro, indice della riga nella tabella
			 * e indice della voce nel bilancio non coincidono!
			 */
			int idx=bilancio.indexOf(voce);
			bilancio.get(idx).setDescrizione(descrizione);
			bilancio.get(idx).setData(data);
			bilancio.get(idx).setAmmontare(importo);
		}
	}
	
}

package grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import net.miginfocom.swing.MigLayout;

/**
 * Classe utilizzata per creare il pannello della schermata di selezione del
 * filtro data. Sono creati e configurati i componenti grafici per la selezione
 * del periodo da parte dell'utente. Il periodo selezionato viene poi passato
 * al modello della tabella.
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see ModelloTabellaVoci
 * @see PannelloPrincipale
 *
 */
public class PannelloFiltra extends JPanel implements ActionListener{
	/**
	 * UID per evitare problemi di serializzazione.
	 */
	private static final long serialVersionUID = 1L;
	/** Radio button per la selezione di un periodo corrispondente ad un giorno */
	private JRadioButton giorno;
	/** Radio button per la selezione di un periodo corrispondente ad un mese */
	private JRadioButton mese;
	/** Radio button per la selezione di un periodo corrispondente ad un anno */
	private JRadioButton anno;
	/** Radio button per la selezione di un periodo personalizzato */
	private JRadioButton personalizza;
	/** Radio button per rimuovere il filtro */
	private JRadioButton mostraTutto;
	/** Button group per gestire la selezione del tipo di periodo */
	private ButtonGroup bg;
	/** Label del JDateChooser per la selezione dell'inizio del periodo personalizzato */
	private JLabel da;
	/** Label del JDateChooser per la selezione della fine del periodo personalizzato */
	private JLabel a;
	/** Selettore del giorno nel caso del periodo corrispondente ad un giorno */
	private JDateChooser dataGiorno;
	/** Selettore del giorno di inizio nel caso del periodo personalizzato */
	private JDateChooser dataInizio;
	/** Selettore del giorno di fine nel caso del periodo personalizzato */
	private JDateChooser dataFine;
	/** Selettore del mese nel caso del periodo corrispondente ad un mese */
	private JMonthChooser selMese;
	/** Selettore dell'anno nel caso del periodo corrispondente ad un mese */
	private JYearChooser selAnnoMese;
	/** Selettore dell'anno nel caso del periodo corrispondente ad un anno */
	private JYearChooser selAnno;
	/** Checkbox per la selezione di un'intera settimana */
	private JCheckBox settimana;
	/** Bottone per confermare il filtro scelto */
	private JButton ok;
	/** Bottone per annullare l'impostazione del filtro data */
	private JButton annulla;
	/** 
	 * Modello della tabella per la visualizzazione del bilancio nella
	 * schermata principale.
	 * @see ModelloTabellaVoci
	 */
	private ModelloTabellaVoci modelloTabella;
	/** Frame contenitore del pannello */
	private JFrame frameContenitore;
	/** Frame contenitore della schermata principale */
	private JFrame framePrincipale;
	
	/**
	 * Costruttore che inizializza tutti i componenti grafici della finestra e
	 * li dispone utilizzando un <code>MigLayout</code>. Inizializza i campi di selezione 
	 * delle date con il giorno, il mese e l'anno correnti, e seleziona
	 * inizialmente il periodo corrispondente ad un giorno,
	 * disattivando il resto.
	 * @param modelloTabella Modello tabella per la visualizzazione del bilancio
	 * @param frameContenitore Frame contenitore del pannello
	 * @param framePrincipale Frame contenitore della finestra principale
	 */
	public PannelloFiltra(ModelloTabellaVoci modelloTabella, JFrame frameContenitore,
			JFrame framePrincipale){
		super();
		this.modelloTabella=modelloTabella;
		this.frameContenitore=frameContenitore;
		this.framePrincipale=framePrincipale;
		
		bg=new ButtonGroup();
		giorno=new JRadioButton("Giorno: ");
		giorno.setActionCommand("giorno");
		giorno.addActionListener(this);
		mese=new JRadioButton("Mese: ");
		mese.setActionCommand("mese");
		mese.addActionListener(this);
		anno=new JRadioButton("Anno: ");
		anno.setActionCommand("anno");
		anno.addActionListener(this);
		personalizza=new JRadioButton("Personalizzato: ");
		personalizza.setActionCommand("pers");
		personalizza.addActionListener(this);
		mostraTutto=new JRadioButton("Mostra tutto");
		mostraTutto.setActionCommand("mostra tutto");
		mostraTutto.addActionListener(this);
		bg.add(giorno);
		bg.add(mese);
		bg.add(anno);
		bg.add(personalizza);
		bg.add(mostraTutto);
		bg.setSelected(giorno.getModel(), true);
		
		da=new JLabel("Da:");
		a=new JLabel("A:");
		
		settimana=new JCheckBox("Seleziona intera settimana");
		settimana.setSelected(false);
		
		ok=new JButton("OK");
		ok.addActionListener(this);
		annulla=new JButton("Annulla");
		annulla.addActionListener(this);
		
		dataGiorno=new JDateChooser();
		dataGiorno.setCalendar(Calendar.getInstance());
		dataInizio=new JDateChooser();
		dataInizio.setCalendar(Calendar.getInstance());
		dataFine=new JDateChooser();
		dataFine.setCalendar(Calendar.getInstance());
		
		selMese=new JMonthChooser();
		selMese.setMonth(Calendar.getInstance().get(Calendar.MONTH));
		
		/* Rendo il campo testuale degli YearChooser non editabile */
		selAnnoMese=new JYearChooser();
		selAnnoMese.setYear(Calendar.getInstance().get(Calendar.YEAR));
		JSpinner s=(JSpinner)selAnnoMese.getSpinner();
		((javax.swing.JTextField)s.getEditor()).setEditable(false);
		selAnno=new JYearChooser();
		s=(JSpinner)selAnno.getSpinner();
		((javax.swing.JTextField)s.getEditor()).setEditable(false);
		selAnno.setYear(Calendar.getInstance().get(Calendar.YEAR));
		
		enableRadioButton(1);
		
		MigLayout layout=new MigLayout();
		setLayout(layout);
		
		add(giorno,"split");
		add(dataGiorno);
		add(settimana, "wrap");
		add(mese,"split");
		add(selMese,"split");
		add(selAnnoMese,"wrap");
		add(anno,"split");
		add(selAnno,"wrap");
		add(personalizza,"split");
		add(da,"split");
		add(dataInizio);
		add(a,"split");
		add(dataFine,"wrap");
		add(mostraTutto,"wrap");
		add(ok,"split");
		add(annulla);
		
	}

	/**
	 * Abilita certi selettori di giorni/mesi/anni in base al
	 * RadioButton selezionato, disattivando tutti gli altri.
	 * @param indice Indice del radio button selezionato all'interno del button group
	 */
	private void enableRadioButton(int indice){
		switch(indice){
		case 1:
			dataGiorno.setEnabled(true);
			settimana.setEnabled(true);
			selMese.setEnabled(false);
			selAnnoMese.setEnabled(false);
			selAnno.setEnabled(false);
			dataInizio.setEnabled(false);
			dataFine.setEnabled(false);
			break;
		case 2:
			dataGiorno.setEnabled(false);
			settimana.setEnabled(false);
			selMese.setEnabled(true);
			selAnnoMese.setEnabled(true);
			selAnno.setEnabled(false);
			dataInizio.setEnabled(false);
			dataFine.setEnabled(false);
			break;
		case 3:
			dataGiorno.setEnabled(false);
			settimana.setEnabled(false);
			selMese.setEnabled(false);
			selAnnoMese.setEnabled(false);
			selAnno.setEnabled(true);
			dataInizio.setEnabled(false);
			dataFine.setEnabled(false);
			break;
		case 4:
			dataGiorno.setEnabled(false);
			settimana.setEnabled(false);
			selMese.setEnabled(false);
			selAnnoMese.setEnabled(false);
			selAnno.setEnabled(false);
			dataInizio.setEnabled(true);
			dataFine.setEnabled(true);
			break;
		case 5:
			dataGiorno.setEnabled(false);
			settimana.setEnabled(false);
			selMese.setEnabled(false);
			selAnnoMese.setEnabled(false);
			selAnno.setEnabled(false);
			dataInizio.setEnabled(false);
			dataFine.setEnabled(false);
			break;
		default: break;
		}
		/* Rendo gli editor dei DateChooser non editabili per evitare input errati */
		dataGiorno.getDateEditor().setEnabled(false);
		dataInizio.getDateEditor().setEnabled(false);
		dataFine.getDateEditor().setEnabled(false);
	}
	
	/**
	 * Ascoltatore degli eventi generati da bottoni e RadioButton. Nel caso di
	 * un RadioButton, invoca la funzione {@link #enableRadioButton(int)}
	 * per abilitare i relativi selettori di periodo. Nel caso di un bottone,invece,
	 * se si preme ok invoca {@link #filtraPeriodo()} per impostare
	 * il filtro data, ed in entrambi i casi chiude la finestra riabilitando 
	 * quella principale.
	 * @param e Evento da gestire
	 */
	public void actionPerformed(ActionEvent e) {
		String s=e.getActionCommand();
		switch(s){
		case "giorno":
			this.enableRadioButton(1);
			break;
		case "mese":
			this.enableRadioButton(2);
			break;
		case "anno":
			this.enableRadioButton(3);
			break;
		case "pers":
			this.enableRadioButton(4);
			break;
		case "mostra tutto":
			this.enableRadioButton(5);
			break;
		case "OK":
			this.filtraPeriodo();
		case "Annulla":
			framePrincipale.setEnabled(true);
			frameContenitore.dispose();
			break;
		default: break;
		}
		
	}

	/**
	 * Definisce le date di inizio e di fine del filtro data selezionate.
	 * In base alla selezione dell'utente e ai periodi selezionati,
	 * definisce le date di inizio e di fine del filtro data e le passa al modello
	 * della tabella.
	 * @see ModelloTabellaVoci
	 */
	private void filtraPeriodo() {
		String selezione=bg.getSelection().getActionCommand();
		Date inizio=null,fine = null;
		switch(selezione){
		case "giorno":
			Date gg=dataGiorno.getDate();
			if(!settimana.isSelected())
				inizio=fine=gg;
			else{
				Calendar c=Calendar.getInstance();
				c.setTime(gg);
			    while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			        c.add(Calendar.DATE, -1);
			    }
			    inizio=c.getTime();
			    c.setTime(gg);
			    while (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			        c.add(Calendar.DATE, 1);
			    }
			    fine=c.getTime();
			}
			
			modelloTabella.setDate(inizio, fine);
			modelloTabella.aggiornaTabella();
			break;
		case "mese":{
			int mese=selMese.getMonth();
			String m;
			if(mese<9)
				m="0"+(mese+1);
			else
				m=""+(mese+1);
			int anno=selAnnoMese.getYear();
			int lunghezzaMese=
					new GregorianCalendar(anno,mese,1).getActualMaximum(Calendar.DAY_OF_MONTH);
			try {
				inizio=new SimpleDateFormat("dd-MM-yyyy").parse("01-"+m+"-"+anno);
				fine=new SimpleDateFormat("dd-MM-yyyy").parse(""+
						lunghezzaMese+"-"+m+"-"+anno);
			} catch (ParseException e) {				
				JOptionPane.showMessageDialog(this,
					    "Errore nel filtrare le date. "
						+e.getMessage(),
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			modelloTabella.setDate(inizio, fine);
			modelloTabella.aggiornaTabella();
			break;
		}
		case "anno":
			int anno=selAnno.getYear();
			try {
				inizio=new SimpleDateFormat("dd-MM-yyyy").parse("01-01-"+anno);
				fine=new SimpleDateFormat("dd-MM-yyyy").parse("31-12-"+anno);
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(this,
					    "Errore nel filtrare le date. "
						+e.getMessage(),
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
				return;
			}
			modelloTabella.setDate(inizio, fine);
			modelloTabella.aggiornaTabella();
			break;
		case "pers":
			inizio=dataInizio.getDate();
			fine=dataFine.getDate();
			modelloTabella.setDate(inizio, fine);
			modelloTabella.aggiornaTabella();
			break;
		case "mostra tutto":
			modelloTabella.setDate(null, null);
			modelloTabella.aggiornaTabella();
			break;
		default: break;
		}
	}
}

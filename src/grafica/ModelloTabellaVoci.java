package grafica;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import vociBilancio.VoceDiBilancio;

/**
 * Classe che implementa la gestione dei contenuti della tabella principale,
 * tra cui la loro visualizzazione, la ricerca e il calcolo del totale.
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see PannelloPrincipale
 *
 */
public class ModelloTabellaVoci extends AbstractTableModel {
	/**
	 * UID per evitare problemi di serializzazione.
	 */
	private static final long serialVersionUID = 1L;
	/** Vettore contenente il bilancio attuale */
	private Vector<VoceDiBilancio> bilancio;
	/** 
	 * Vettore contenente solo le voci che devono essere visualizzate
	 * nella tabella, in accordo con i filtri della data.
	 */
	private Vector<VoceDiBilancio> vociSelezionate;
	/** Data di inizio del filtro data */
	private Date dataInizio;
	/** Data di fine del filtro data */
	private Date dataFine;
	/** Vettore di stringhe contenenti le intestazioni delle colonne della tabella */
	private String[] NomeCol = {"Data", "Descrizione", "Ammontare" };
	/** TextField del pannello principale contenente il totale del bilancio */
	private JTextField totale;
	/** Parola chiave dell'ultima ricerca effettuata dall'utente */
	private String ultimaRicerca;
	/** Indice della tabella in cui si e' fermata l'ultima ricerca */
	private int ultimoIndiceRicerca;
	
	/**
	 * Costruttore in cui vengono inizializzati il vettore del bilancio e il
	 * totale, mentre gli attributi riguardanti il filtro data sono temporaneamente
	 * impostati a null.
	 * @param bilancio vettore di voci rappresentante il bilancio attuale
	 * @param totale TextField contenente il totale del bilancio
	 */
	public ModelloTabellaVoci(Vector<VoceDiBilancio> bilancio, JTextField totale) {
		this.bilancio=bilancio;
		this.totale=totale;
		vociSelezionate=new Vector<VoceDiBilancio>();
		dataInizio=null;
		dataFine=null;
		ultimoIndiceRicerca=0;
		ultimaRicerca=null;
	}
	
	/**
	 * Restituisce il numero di colonne della tabella.
	 * @return Numero di colonne della tabella
	 */
	public int getColumnCount() {
		return NomeCol.length;
	}
	
	/**
	 * Restituisce il numero di righe della tabella.
	 * @return Numero di righe della tabella
	 */
	public int getRowCount() {
		return vociSelezionate.size();
	}
	
	/**
	 * Restituisce il contenuto di una cella della tabella.
	 * La riga indica l'indice del vettore <code>vociSelezionate</code> in cui si trova
	 * la voce selezionata, la colonna indica quale campo ritornare.
	 * @param riga Riga della cella nella tabella
	 * @param col Colonna della cella nella tabella
	 * @return Campo dell'oggetto selezionato in base a riga e col
	 * @see vociBilancio.VoceDiBilancio
	 */
	public Object getValueAt(int riga, int col) {
		VoceDiBilancio v=vociSelezionate.get(riga);
		
		switch(col) {
		case 0: return v.getFormatData();
		case 1: return v.getDescrizione();
		case 2: return v.getAmmontare();
		default: return "";
		}
	}
	
	/**
	 * Restituisce il nome di una colonna.
	 * @param col Indice colonna
	 * @return Nome colonna
	 */
	public String getColumnName(int col) {
		return NomeCol[col];
	}
	
	/**
	 * Setta le date di inizio e fine del filtro data. Il
	 * controllo sulla validita' delle date viene fatto nella funzione
	 * {@link #aggiornaTabella()}
	 * @param inizio Data di inizio del filtro data
	 * @param fine Data di fine del filtro data
	 * @see #rimuoviOre(Date)
	 */
	public void setDate(Date inizio, Date fine) {
		dataInizio=rimuoviOre(inizio);
		dataFine=rimuoviOre(fine);
	}
	
	
	/**
	 * Aggiorna la tabella mediante il filtro data.
	 * Il vettore <code>vociSelezionate</code> viene svuotato, dopodiche' vengono aggiunte
	 * tutte le voci le cui date sono comprese tra <code>dataInizio</code> e 
	 * <code>dataFine</code>. Infine, viene aggiornato il totale. Nel caso il 
	 * filtro non sia valido (date settate a null o data di inizio dopo quella
	 * di fine), esso viene rimosso e vengono visualizzate tutte le voci.
	 */
	@SuppressWarnings("unchecked") /* il metodo clone ritornera' per forza un vector */
	public void aggiornaTabella() {
		
		if(dataInizio==null || dataFine==null || dataInizio.after(dataFine)) {
			vociSelezionate=(Vector<VoceDiBilancio>) bilancio.clone();
		}else{
			vociSelezionate.removeAllElements();
			for(int i=0; i<bilancio.size(); i++) {
				Date dataVoce=rimuoviOre(bilancio.get(i).getData());
				if((dataVoce.after(dataInizio) && dataVoce.before(dataFine))
						||(dataVoce.equals(dataInizio))||(dataVoce.equals(dataFine)))
					vociSelezionate.addElement(bilancio.get(i));
			}
		}
		this.fireTableDataChanged();
		
		aggiornaTotale();
	}
	
	/**
	 * Aggiorna il totale del bilancio.
	 * Somma l'ammontare di ogni voce visualizzata nella tabella, approssimando
	 * il risultato alla seconda cifra decimale.
	 */
	private void aggiornaTotale() {
		double sum=0;
		for(int i=0; i<vociSelezionate.size(); i++) {
			sum+=vociSelezionate.get(i).getAmmontare();
		}
		sum=Math.round(sum*100);
		sum=sum/100;
		totale.setText(Double.toString(sum));
	}

	/**
	 * Restituisce il vettore di voci visualizzate nella tabella.
	 * @return Il vettore di voci visualizzate nella tabella
	 */
	public Vector<VoceDiBilancio> getVettoreBase() {
		return vociSelezionate;
	}
	
	/**
	 * Implementa la ricerca di una voce nella tabella. Partendo
	 * dall'indice in cui si e' fermata l'ultima ricerca e dall'ultima parola chiave
	 * cercata, la funzione scorre il vettore delle voci visualizzate cercando
	 * una corrispondenza nei campi importo e descrizione (per la data c'e' gia' 
	 * il filtro data). Se viene trovata, viene salvato l'ultimo indice e 
	 * attivato il bottone "Successivo", in caso contrario il bottone viene
	 * disattivato.
	 * @param tabella Tabella in cui selezionare l'eventuale cella trovata
	 * @param successivo Bottone da attivare/disattivare
	 */
	public void ricercaTabella(JTable tabella, JButton successivo) {
		for(int i=ultimoIndiceRicerca; i<vociSelezionate.size(); i++){
			if(vociSelezionate.get(i).getDescrizione().contains(ultimaRicerca)){
				tabella.changeSelection(i, 1, false, false);
				successivo.setEnabled(true);
				ultimoIndiceRicerca=i+1;
				return;
			}
			String ammontare=Double.toString(vociSelezionate.get(i).getAmmontare());
			if(ammontare.contains(ultimaRicerca)){
				tabella.changeSelection(i, 2, false, false);
				successivo.setEnabled(true);
				ultimoIndiceRicerca=i+1;
				return;
			}
		}
		successivo.setEnabled(false);
	}
	
	/**
	 * Imposta una nuova ricerca. L'ultimo indice viene posto a
	 * zero e la parola chiave sostituita con quella nuova.
	 * @param ricerca Nuova stringa cercata dall'utente
	 * @param tabella Tabella in cui selezionare un eventuale cella trovata
	 * @param successivo Bottone da attivare/disattivare
	 */
	public void nuovaRicercaTabella(String ricerca, JTable tabella, JButton successivo) {
		ultimaRicerca=ricerca;
		ultimoIndiceRicerca=0;
		ricercaTabella(tabella, successivo);
	}
	
	/**
	 * Rimuove le ore, i minuti, i secondi e i millisecondi da un oggetto di tipo <code>Date</code>.
	 * Questo permette di far funzionare il metodo <code>equals()</code> tra due oggetti 
	 * <code>Date</code> tenendo in considerazione solo il loro giorno.
	 * @param data Data da cui rimuovere ore, minuti e secondi.
	 * @return L'oggetto data senza ore, minuti e secondi
	 */
	private Date rimuoviOre(Date data) {
		if(data!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();
		} else
			return null;
	}
	
	/**
	 * Restituisce la data di inizio del filtro data.
	 * @return Data di inizio del filtro data
	 */
	public Date getDataInizio(){
		return dataInizio;
	}
	
	/**
	 * Restituisce la data di fine del filtro data.
	 * @return Data di fine del filtro data
	 */
	public Date getDataFine(){
		return dataFine;
	}
	
}

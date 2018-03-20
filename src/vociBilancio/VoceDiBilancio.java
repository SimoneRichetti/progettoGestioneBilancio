package vociBilancio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe astratta per l'implementazione di una generica voce di bilancio.
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see ComparatoreVoci
 */
public abstract class VoceDiBilancio implements Serializable{
	/**
	 * UID per evitare problemi di serializzazione.
	 */
	private static final long serialVersionUID = 1L;
	/** Data della transazione */
	private Date data;
	/** Descrizione della transazione */
	private String descrizione;
	/** Importo della transazione */
	protected double ammontare;
	
	/** 
	 * Costruttore che inizializza i campi della voce
	 * @param data Data della voce
	 * @param descrizione Descrizione della voce
	 * @param ammontare Importo della voce
	 */
	public VoceDiBilancio(Date data, String descrizione, double ammontare) {
		this.setData(data);
		this.setDescrizione(descrizione);
		this.ammontare=ammontare;
	}
	
	/**
	 * Restituisce la data della voce.
	 * @return Data della voce
	 */
	public Date getData() {
		return data;
	}
	
	/**
	 * Restituisce la data come stringa nel formato giorno-mese-anno.
	 * @return Una stringa rappresentante la data della voce
	 */
	public String getFormatData(){
		return new SimpleDateFormat("dd-MM-yyyy").format(data);
	}

	/**
	 * Imposta l'attributo data.
	 * @param data Valore con cui settare l'attributo data
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * Restituisce la descrizione della voce 
	 * @return La descrizione della voce
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * Imposta la descrizione della voce.
	 * @param descrizione Descrizione della voce da settare
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	/**
	 * Restituisce l'ammontare della voce.
	 * @return L'ammontare della voce
	 */
	public abstract double getAmmontare();
	
	/**
	 * Imposta l'ammontare della voce.
	 * @param ammontare Ammontare della voce di bilancio
	 */
	public void setAmmontare(double ammontare) {
		this.ammontare=ammontare;
	}
}

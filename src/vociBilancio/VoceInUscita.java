package vociBilancio;

import java.util.Date;

/**
 * Classe figlia di <code>VoceDiBilancio</code> per implementare una voce di 
 * bilancio in uscita.
 * 
 * @author Simone Richetti
 * @version 1.0
 *
 */
public class VoceInUscita extends VoceDiBilancio{

	/**
	 * UID per evitare problemi di serializzazione.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see VoceDiBilancio#VoceDiBilancio(Date, String, double) VoceDiBilancio(costruttore)
	 */
	public VoceInUscita(Date data, String descrizione, double ammontare) {
		super(data, descrizione, ammontare);
	}
	
	/**
	 * Ritorna l'ammontare della voce, che, essendo questa in uscita, sara' sempre una
	 * quantita' negativa. Implementazione del metodo astratto della classe padre. 
	 * @return Ammontare della voce
	 */
	@Override
	public double getAmmontare() {
		return (Math.abs(super.ammontare))*(-1);
	}
}

package vociBilancio;

import java.util.Date;

/**
 * Classe figlia di <code>VoceDiBilancio</code> per implementare una voce di
 * bilancio in entrata.
 * 
 * @author Simone Richetti
 * @version 1.0
 *
 */
public class VoceInEntrata extends VoceDiBilancio{
	
	/**
	 * UID per evitare problemi di serializzazione.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see VoceDiBilancio#VoceDiBilancio(Date, String, double) VoceDiBilancio(costruttore)
	 */
	public VoceInEntrata(Date data, String descrizione, double ammontare) {
		super(data, descrizione, ammontare);
	}
	
	/**
	 * Ritorna l'ammontare della voce, che, essendo questa in entrata, sara' sempre una
	 * quantita' positiva. Implementazione del metodo astratto della classe padre. 
	 * @return Ammontare della voce
	 */
	@Override
	public double getAmmontare() {
		return Math.abs(super.ammontare);
	}
}

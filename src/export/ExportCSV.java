package export;

import java.util.Vector;

import vociBilancio.VoceDiBilancio;

/**
 * Classe figlia di <code>ExportTesto</code> adibita ad esportare il bilancio nel formato
 * Comma Separated Values, ovvero un file con una riga per ogni voce di 
 * questo tipo:
 * 
 * data,descrizione,importo 
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see ExportTesto
 * @see Export
 *
 */
public class ExportCSV extends ExportTesto{
	
	/**
	 * Costruttore della classe che inizializza il vettore delle voci e il
	 * separatore.
	 * @param bilancio vettore di voci di bilancio da esportare.
	 * @see ExportTesto#ExportTesto(Vector)
	 */
	public ExportCSV(Vector<VoceDiBilancio> bilancio) {
		super(bilancio);
		super.setSeparatore(",");
	}

}

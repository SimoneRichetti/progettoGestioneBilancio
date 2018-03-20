package vociBilancio;
import java.util.Vector;

import grafica.FramePrincipale;

/**
 * Classe contenente il main del programma, nel quale vengono creati il vettore
 * contenente le voci di bilancio e la finestra principale.
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see grafica.FramePrincipale
 * @see VoceDiBilancio
 */
public class GestioneBilancio {

	/**
	 * Funzione main del programma. Istanzia il vettore
	 * delle voci di bilancio che verra' utilizzato dal resto delle classi e
	 * crea il Frame della finestra principale.
	 * @param args Eventuali argomenti passati in ingresso al programma
	 * @see grafica.FramePrincipale
	 */
	public static void main(String[] args) {
		Vector<VoceDiBilancio> bilancio = new Vector<VoceDiBilancio>();
		FramePrincipale f = new FramePrincipale("Gestione bilancio", bilancio);
		f.setVisible(true);
	}

}

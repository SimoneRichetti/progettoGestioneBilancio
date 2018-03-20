package grafica;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Classe che implementa un ascoltatore di un <code>JFrame</code>. In particolare, e'
 * utilizzato per gestire l'uscita da una finestra secondaria (es. aggiunta,
 * modifica, filtro data) mediante la chiusura del frame e non l'utilizzo dei
 * bottoni, che altrimenti lascierebbe il frame disabilitato e il programma
 * inutilizzabile.
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see PannelloPrincipale
 * @see FramePrincipale
 *
 */
public class AscoltatoreFrame extends WindowAdapter{
	/** Frame di cui gestire la chiusura */
	private JFrame frameContenitore;
	/** Frame da riattivare in caso di chiusura del framePrincipale */
	private JFrame framePrincipale;
	
	/**
	 * Costruttore che inizializza i due frame da gestire
	 * @param frameContenitore Frame di cui gestire la chiusura
	 * @param framePrincipale Frame da riattivare
	 */
	public AscoltatoreFrame(JFrame frameContenitore, JFrame framePrincipale) {
		super();
		this.frameContenitore=frameContenitore;
		this.framePrincipale=framePrincipale;
	}
	
	/**
	 * Gestisce l'evento di chiusura del <code>framePrincipale</code>,
	 * distruggendolo e riattivando il <code>frameContenitore</code>
	 */
	public void windowClosing(WindowEvent evt){
		if(framePrincipale != null)
			framePrincipale.setEnabled(true);
		if (frameContenitore != null)
			frameContenitore.dispose();
	}
}

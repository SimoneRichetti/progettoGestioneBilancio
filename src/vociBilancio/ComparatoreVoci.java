package vociBilancio;

import java.util.Comparator;

/**
 * Classe utilizzata per definire i criteri di ordinamento delle
 * voci di bilancio.
 * 
 * @author Simone Richetti
 * @version 1.0
 *
 */
public class ComparatoreVoci implements Comparator<VoceDiBilancio>{
	/**
	 * Determina quale tra due voci di bilancio viene posizionata per
	 * prima in un vettore di voci ordinate. L'ordinamento, in questo caso,
	 * avviene utilizzando come criterio la data.
	 * @param v1 Prima voce di bilancio
	 * @param v2 Seconda voce di bilancio
	 * @see grafica.PannelloAggiungiModifica#actionPerformed(java.awt.event.ActionEvent) PannelloAggiungiModifica
	 */
	public int compare(VoceDiBilancio v1, VoceDiBilancio v2) {
		if(v1.getData().after(v2.getData()))
			return 1;
		else if(v1.getData().before(v2.getData()))
			return -1;
			else
				return 0;
	}

}

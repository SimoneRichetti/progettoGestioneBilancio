package export;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import vociBilancio.VoceDiBilancio;

/**
 * Classe utilizzata per esportare il bilancio nel formato OpenDocument.
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see Export
 *
 */
public class ExportOpenDoc extends Export{

	/**
	 * Costruttore che inizializza il vettore di voci di bilancio da esportare.
	 * @param bilancio vettore di voci di bilancio
	 * @see Export#Export(Vector)
	 */
	public ExportOpenDoc(Vector<VoceDiBilancio> bilancio) {
		super(bilancio);
	}

	/**
	 * Gestisce la procedura di creazione del foglio di calcolo
	 * e di esportazione. Fa selezionare il path all'utente attraverso il FileChooser e
	 * chede conferma nel caso di sovrascrittura. Crea poi una matrice
	 * di <code>Object</code> in cui vengono salvate le varie voci e, infine, la inserisce
	 * nel foglio di calcolo, che viene salvato nel path selezionato.
	 */
	@Override
	public void exportBilancio() {
		if(!getBilancio().isEmpty()){
			int retVal=super.getFileChooser().showSaveDialog(null);
			if(retVal==JFileChooser.APPROVE_OPTION){
				if(checkSovrascrittura()){
					Object data[][]=new Object[getBilancio().size()][3];
					for(int i=0; i<getBilancio().size(); i++){
						VoceDiBilancio v=getBilancio().get(i);
						data[i]=new Object[] 
								{ v.getFormatData(), v.getDescrizione(), v.getAmmontare() };
					}
					String[] nomiColonne=new String[] { "Data", "Descrizione", "Ammontare" };
					TableModel model = new DefaultTableModel(data, nomiColonne);
					try {
						SpreadSheet.createEmpty(model).saveAs(new File(getNomeFile()));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,
								"Errore nell'esportazione del bilancio. "
						    	+e.getMessage(),
						    	"Errore",
						    	JOptionPane.ERROR_MESSAGE);
					}	
				}
			}
		}
	}
	
	/**
	 * Setta il nome del file di output.
	 * Prende in ingresso una stringa contenente un path,
	 * controlla che esso contenga l'estensione ".ods" e, in caso contrario,
	 * la aggiunge. Utile in particolare per sistemi Windows.
	 * @param nomeFile Stringa contenente il path del file in cui esportare
	 */
	protected void setNomeFile(String nomeFile) {
		if(!nomeFile.endsWith(".ods"))
			nomeFile=nomeFile+".ods";
		super.setNomeFile(nomeFile);
	}

}

package export;
import vociBilancio.VoceDiBilancio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Classe adibita a esportare il bilancio in formato testo. L'output e'
 * un file con una riga per ogni voce, in questo formato:
 * 
 * data		descrizione		importo
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see Export
 * @see ExportCSV
 *
 */
public class ExportTesto extends Export {
	/** 
	 * Il carattere utilizzato per i separare i campi delle voci 
	 * @see ExportCSV
	 */
	private String separatore;
	
	/**
	 * Costruttore della classe, inizializza vettore voci da esportare e separatore
	 * @param bilancio vettore di voci da esportare
	 * @see Export#Export(Vector)
	 */
	public ExportTesto(Vector<VoceDiBilancio> bilancio) {
		super(bilancio);
		this.separatore="\t";
	}

	/**
	 * Salva il bilancio in un file di testo.
	 * Attraverso il FileChooser fa selezionare all'utente il path in cui salvare
	 * il file, chiede conferma in caso di sovrascrittura e infine utilizza un
	 * <code>BufferedWriter</code> per salvare le voci nel vettore bilancio. Utilizzata
	 * anche in {@link ExportCSV}, in quanto la struttura del file di output e' la 
	 * medesima, cambia solo il separatore.
	 * @see ExportTesto#checkSovrascrittura()
	 */
	@Override
	public void exportBilancio() {
		if(!getBilancio().isEmpty()){
			int retVal=super.getFileChooser().showSaveDialog(null);
			if(retVal==JFileChooser.APPROVE_OPTION){
				if(checkSovrascrittura()){
					try {
						FileWriter file = new FileWriter(getNomeFile());
						BufferedWriter buff = new BufferedWriter(file);
						buff.write("Data" + separatore);
						buff.write("Descrizione" + separatore);
						buff.write("Ammontare");
						for (int i = 0; i < getBilancio().size(); i++) {
							buff.newLine();
							buff.write(getBilancio().elementAt(i).getFormatData()
									+ separatore);
							buff.write((getBilancio().elementAt(i).getDescrizione()
									+ separatore));
							buff.write(Double.toString(getBilancio().elementAt(i).getAmmontare()));
						}
						buff.close();
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
	 * controlla che esso contenga l'estensione ".txt" e, in caso contrario,
	 * la aggiunge. Utile in particolare per sistemi Windows
	 * @param nomeFile Stringa contenente il path del file in cui esportare
	 */
	protected void setNomeFile(String nomeFile) {
		if(!nomeFile.endsWith(".txt"))
			nomeFile=nomeFile+".txt";
		super.setNomeFile(nomeFile);
	}

	/**
	 * Restituisce il separatore dei campi delle voci.
	 * @return Carattere separatore
	 */
	protected String getSeparatore() {
		return separatore;
	}

	/**
	 * Imposta il separatore.
	 * @param separatore Carattere con cui settare il separatore
	 */
	protected void setSeparatore(String separatore) {
		this.separatore = separatore;
	}

}

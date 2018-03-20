package export;
import vociBilancio.VoceDiBilancio;

import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Classe astratta per gestire un'esportazione generica del bilancio.
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see ExportTesto
 * @see ExportCSV
 * @see ExportExcel
 * @see ExportOpenDoc
 *
 */
public abstract class Export {
	/** Vettore di voci da esportare */
	private Vector<VoceDiBilancio> bilancio;
	/** FileChooser per selezionare il path del file in cui esportare */
	private JFileChooser fileChooser;
	/** Nome del file in cui esportare il bilancio */
	private String nomeFile;
	
	/**
	 * Costruttore della classe, che inizializza il vettore di voci da
	 * esportare e il filechooser.
	 * @param bilancio vettore di voci da esportare
	 */
	public Export(Vector<VoceDiBilancio> bilancio){
		this.setBilancio(bilancio);
		this.setFileChooser(new JFileChooser());
	}
	
	/**
	 * Gestisce l'effettiva procedura di esportazione
	 * del bilancio. Metodo astratto implementato nelle classi figlie.
	 * @see ExportTesto#exportBilancio()
	 * @see ExportExcel#exportBilancio()
	 * @see ExportOpenDoc#exportBilancio()
	 */
	public abstract void exportBilancio();
	
	/**
	 * Restituisce il vettore bilancio.
	 * @return Il vettore di voci da esportare
	 */
	protected Vector<VoceDiBilancio> getBilancio() {
		return bilancio;
	}
	
	/**
	 * Imposta il vettore bilancio.
	 * @param bilancio Vettore di voci da esportare
	 */
	protected void setBilancio(Vector<VoceDiBilancio> bilancio) {
		this.bilancio = bilancio;
	}
	
	/**
	 * Restituisce il FileChooser.
	 * @return Il FileChooser usato per esportare
	 */
	protected JFileChooser getFileChooser() {
		return fileChooser;
	}
	
	/**
	 * Imposta il FileChooser.
	 * @param fileChooser il FileChooser da impostare
	 */
	protected void setFileChooser(JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}
	
	/**
	 * Restituisce il nome del file in cui esportare il bilancio.
	 * @return Il nome del file in cui esportare il bilancio
	 */
	protected String getNomeFile() {
		return nomeFile;
	}
	
	/**
	 * Imposta il nome del file in cui esportare il bilancio.
	 * @param nomeFile nome del file in cui esportare
	 */
	protected void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	/**
	 * Controlla se si sta cercando di sovrascrivere su un file
	 * gia' esistente e, nel caso, chiede la conferma all'utente.
	 * @return True se l'utente acconsente a sovrascrivere o se non c'e'
	 * alcuna sovrascrittura, false se non si deve sovrascrivere.
	 */
	protected boolean checkSovrascrittura(){
		setNomeFile(getFileChooser().getSelectedFile().getAbsolutePath());
		if(new File(getNomeFile()).exists()){
			int ret=JOptionPane.showConfirmDialog(null, "Sicuro di voler sovrascrivere "
					+ "un file gia' esistente?", "Sovrascrivere?",
					JOptionPane.YES_NO_OPTION);
			if(ret==JOptionPane.NO_OPTION)
				return false;
		}
		return true;
	}
}

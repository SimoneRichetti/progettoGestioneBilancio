package export;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import vociBilancio.VoceDiBilancio;

/**
 * Classe utilizzata per esportare il bilancio in formato Excel.
 * @author Simone Richetti
 * @version 1.0
 * @see Export
 *
 */
public class ExportExcel extends Export {

	/**
	 * Costruttore che inizializza il vettore di voci di bilancio da esportare
	 * @param bilancio vettore di voci da esportare
	 * @see Export#Export(Vector)
	 */
	public ExportExcel(Vector<VoceDiBilancio> bilancio) {
		super(bilancio);
	}

	/**
	 * Implementazione del metodo che gestisce la procedura di esportazione.
	 * Fa selezionare il path attraverso il FileChooser, chiede
	 * conferma nel caso di sovrascrittura, dopodiche' crea il file
	 * Excel con il criterio una voce=una riga, un campo(data, desc..)=una cella.
	 * Infine, salva il file Excel nel path selezionato.
	 * @see Export#checkSovrascrittura()
	 */
	@Override
	public void exportBilancio() {
		if(!getBilancio().isEmpty()){
			int retVal=super.getFileChooser().showSaveDialog(null);
				if(retVal==JFileChooser.APPROVE_OPTION){
					if(checkSovrascrittura()){
					Workbook wb=new HSSFWorkbook();
					Sheet sheet1=wb.createSheet(
							WorkbookUtil.createSafeSheetName("Bilancio"));
					Row row=sheet1.createRow(0);
					row.createCell(0).setCellValue("Data");
					row.createCell(1).setCellValue("Descrizione");
					row.createCell(2).setCellValue("Ammontare");
					for(int i=0; i<getBilancio().size(); i++){
						VoceDiBilancio v=getBilancio().get(i);
						Row newRow=sheet1.createRow(i+1);
						newRow.createCell(0).setCellValue(v.getFormatData());
						sheet1.autoSizeColumn(0);
						newRow.createCell(1).setCellValue(v.getDescrizione());
						sheet1.autoSizeColumn(1);
						newRow.createCell(2).setCellValue(v.getAmmontare());
						sheet1.autoSizeColumn(2);
					}
					
					 FileOutputStream fileOut;
					try {
						fileOut=new FileOutputStream(getNomeFile());
						wb.write(fileOut);
					    fileOut.close();
						wb.close();
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(null,
							    "Errore nell'esportazione del bilancio. "
								+e.getMessage(),
							    "Errore",
							    JOptionPane.ERROR_MESSAGE);
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
	 * controlla che esso contenga l'estensione ".xls" e, in caso contrario,
	 * la aggiunge. Utile in particolare per sistemi Windows.
	 * @param nomeFile Stringa contenente il path del file in cui esportare
	 */
	protected void setNomeFile(String nomeFile) {
		if(!nomeFile.endsWith(".xls"))
			nomeFile=nomeFile+".xls";
		super.setNomeFile(nomeFile);
	}

}

package grafica;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import export.*;
import vociBilancio.VoceDiBilancio;

/**
 * Classe utilizzata per creare il Frame della finestra principale del programma
 * e per gestire alcune delle funzioni legate alla MenuBar: salvataggio, caricamento e
 * stampa del bilancio.
 * 
 * @author Simone Richetti
 * @version 1.0
 * @see PannelloPrincipale
 * @see PannelloFiltra
 * @see vociBilancio.GestioneBilancio
 * @see export.Export
 *
 */
public class FramePrincipale extends JFrame implements ActionListener{
	/**
	 * UID per evitare problemi di serializzazione.
	 */
	private static final long serialVersionUID = 1L;
	/** Barra dei menu della finestra */
	private MenuBar mb;
	/** Menu all'interno della MenuBar contenente voci per la gestione del bilancio*/
	private Menu file;
	/** Menu all'interno della MenuBar contenente voci per la gestione della tabella*/
	private Menu tabella;
	/** Menu all'interno della MenuBar contenente voci per l'esportazione del bilancio*/
	private Menu esporta;
	/** Voce del menu per salvare il bilancio */
	private MenuItem salva;
	/** Voce del menu per caricare il bilancio */
	private MenuItem carica;
	/** Voce del menu per stampare il bilancio */
	private MenuItem stampa;
	/** Voce del menu per filtrare la tabella */
	private MenuItem filtraTab;
	/** Voce del menu per esportare il bilancio in formato CSV */
	private MenuItem csv;
	/** Voce del menu per esportare il bilancio in formato testo */
	private MenuItem testo;
	/** Voce del menu per esportare il bilancio in formato Excel */
	private MenuItem excel;
	/** Voce del menu per esportare il bilancio in formato Open Document */
	private MenuItem opendoc;
	/** Vettore di voci di bilancio */
	private Vector<VoceDiBilancio> bilancio;
	/** 
	 * Pannello contenuto all'interno del Frame
	 * @see PannelloPrincipale
	 */
	private PannelloPrincipale p;
	
	/**
	 * Costruttore della classe, il quale istanzia tutti gli oggetti
	 * della menu bar, li lega ad un <code>ActionListener</code> e li aggiunge 
	 * al frame. Infine, aggiunge il pannello che andra' a contenere tutti 
	 * gli altri componenti grafici. 
	 * @param titolo Titolo della finestra
	 * @param bilancio Vettore di voci di bilancio rappresentante il bilancio attuale
	 */
	public FramePrincipale(String titolo, Vector<VoceDiBilancio> bilancio) {
		super(titolo);
		this.bilancio=bilancio;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		salva=new MenuItem("Salva");
		carica=new MenuItem("Carica");
		stampa=new MenuItem("Stampa bilancio");
		filtraTab=new MenuItem("Filtra per data");
		csv=new MenuItem("CSV");
		testo=new MenuItem("Testo");
		excel=new MenuItem("Excel");
		opendoc=new MenuItem("Open Document");
		file=new Menu("File");
		tabella=new Menu("Tabella");
		esporta=new Menu("Esporta");
		mb=new MenuBar();
		
		salva.addActionListener(this);
		carica.addActionListener(this);
		stampa.addActionListener(this);
		filtraTab.addActionListener(this);
		testo.addActionListener(this);
		csv.addActionListener(this);
		excel.addActionListener(this);
		opendoc.addActionListener(this);
		
		file.add(salva);
		file.add(carica);
		file.add(stampa);
		tabella.add(filtraTab);
		esporta.add(testo);
		esporta.add(csv);
		esporta.add(excel);
		esporta.add(opendoc);
		mb.add(file);
		mb.add(tabella);
		mb.add(esporta);
		
		setMenuBar(mb);
		p=new PannelloPrincipale(bilancio,this);
		add(p);
		
		pack();
	}
	
	/**
	 * ActionListener degli eventi generati dagli elementi della MenuBar.
	 * Gestisce i seguenti eventi:
	 * -Salva: se il bilancio non e', vuoto, chiama la funzione di salvataggio;
	 * 
	 * -Carica: chiama la funzione di caricamento del bilancio da file;
	 * 
	 * -Stampa: se il bilancio non e' vuoto, chiama la funzione di stampa;
	 * 
	 * -Filtra per data: crea una finestra per selezionare un periodo
	 *  "filtro" per le voci;
	 *  
	 * -Testo: esporta il bilancio in formato testo;
	 * 
	 * -CSV: esporta il bilancio in formato CSV;
	 *  
	 * -Excel: esporta il bilancio in formato Excel;
	 *  
	 * -Open Document: esporta il bilancio in formato Open Document;
	 */
	public void actionPerformed(ActionEvent e) {
		String s=e.getActionCommand();
		switch(s){
		case "Salva":
			if(bilancio.isEmpty())
				JOptionPane.showMessageDialog(this,
					    "Impossibile salvare il bilancio: il bilancio e' vuoto",
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
			else
				salvaBilancio();
			break;
		case "Carica":
			caricaBilancio();
			break;
		case "Stampa bilancio":
			if(bilancio.isEmpty())
				JOptionPane.showMessageDialog(this,
					    "Impossibile stampare il bilancio: il bilancio e' vuoto",
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
			else
				stampaBilancio();
			break;
		case "Filtra per data":
			JFrame f=new JFrame("Filtra per data");
			f.addWindowListener(new AscoltatoreFrame(f, this));
			f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			f.add(new PannelloFiltra(p.getModelloTabella(),f, this));
			f.pack();
			f.setResizable(false);
			f.setVisible(true);
			this.setEnabled(false);
			break;
		case "Testo":
			actionMenuExport("Testo");
			break;
		case "CSV":
			actionMenuExport("CSV");
			break;
		case "Excel":
			actionMenuExport("Excel");
			break;
		case "Open Document":
			actionMenuExport("Open Document");
			break;
		default:
			break;
		}
		return;	
	}
	
	/**
	 * Stampa il bilancio. Poiche' la funzione <code>print()</code> e' un metodo
	 * della classe <code>JTable</code>, e' necessario eliminare un eventuale filtro di quest'
	 * ultima, per stampare l'intero bilancio non solo una parte. Percio',
	 * la funzione salva il filtro attuale, lo rimuove, stampa il bilancio e,
	 * infine, ripristina il filtro precedente.
	 * @see ModelloTabellaVoci
	 */
	private void stampaBilancio() {
		Date inizio=p.getModelloTabella().getDataInizio();
		Date fine=p.getModelloTabella().getDataFine();
		p.getModelloTabella().setDate(null, null);
		p.getModelloTabella().aggiornaTabella();
		try {
			p.getTabella().print();
		} catch (PrinterException e1) {
			JOptionPane.showMessageDialog(this,
				    "Errore nella stampa del bilancio. "
					+e1.getMessage(),
				    "Errore",
				    JOptionPane.ERROR_MESSAGE);
		}
		p.getModelloTabella().setDate(inizio, fine);
		p.getModelloTabella().aggiornaTabella();
	}

	/**
	 * Carica il bilancio da file. Attraverso un FileChooser,
	 * l'utente seleziona un file da caricare, che viene deserializzato in un 
	 * vettore temporaneo. La funzione controlla che il file caricato contenesse
	 * effetivamente un vettore e elimina tutte le voci del
	 * bilancio attuale per poi aggiungere quelle del nuovo bilancio.
	 * @see ModelloTabellaVoci#aggiornaTabella()
	 */
	@SuppressWarnings("unchecked") /* Ho aggiunto un semplice controllo dopo il caricamento */
	private void caricaBilancio() {
		Vector<VoceDiBilancio> newBilancio=null;
		JFileChooser fileChooser = new JFileChooser();
		int retVal=fileChooser.showOpenDialog(this);
		if(retVal==JFileChooser.APPROVE_OPTION){
			File fileCarica=fileChooser.getSelectedFile();
			FileInputStream fIn;
			ObjectInputStream objIn;
			try {
				fIn=new FileInputStream(fileCarica);
				objIn=new ObjectInputStream(fIn);
				newBilancio=(Vector<VoceDiBilancio>)objIn.readObject();
				objIn.close();
			} catch(IOException e1) {
				JOptionPane.showMessageDialog(this,
					    "Errore nel caricamento del file. "
						+e1.getMessage(),
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e2) {
				JOptionPane.showMessageDialog(this,
					    "Errore nel caricamento del file: "
						+e2.getMessage(),
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
			}
			
			/*
			 * Controllo che in newBilancio ci  sia effettivamente un
			 * Vector<VoceDiBilancio>.
			 */
			if(newBilancio!=null){
				for(int i=0; i<newBilancio.size(); i++){
					if(!(newBilancio.get(i) instanceof VoceDiBilancio )){
						newBilancio=null;
						break;
					}
				}
			}
			
			if(newBilancio != null) {
				bilancio.removeAllElements();
				for(int i=0; i<newBilancio.size(); i++)
					bilancio.addElement(newBilancio.get(i));
				
				this.p.getModelloTabella().aggiornaTabella();
			}
		}
	}

	/**
	 * Salva il bilancio su file. Attraverso un FileChooser
	 * l'utente seleziona il percorso in cui salvare il file, la funzione
	 * controlla un'eventuale sovrascrittura e l'estensione del file. Infine,
	 * serializza il vettore bilancio sul file.
	 */
	private void salvaBilancio() {
		JFileChooser fileChooser = new JFileChooser();
		int retVal=fileChooser.showSaveDialog(this);
		if(retVal==JFileChooser.APPROVE_OPTION){
			String path=fileChooser.getSelectedFile().getAbsolutePath();
			if(!path.endsWith(".bil"))
				path=path+".bil";
			
			File fileSalva=new File(path);
			if(fileSalva.exists()){
				int ret=JOptionPane.showConfirmDialog(this, "Sicuro di voler sovrascrivere "
						+ "un file gia' esistente?", "Sovrascrivere?",
						JOptionPane.YES_NO_OPTION);
				if(ret==JOptionPane.NO_OPTION)
					return;
			}
			
			FileOutputStream fOut=null;
			ObjectOutputStream objOut=null;
			try {
				fOut=new FileOutputStream(fileSalva);
				objOut=new ObjectOutputStream(fOut);
				objOut.writeObject(bilancio);
				objOut.flush();
				objOut.close();
			} catch(IOException ex) {
				JOptionPane.showMessageDialog(this,
					    "Errore nel salvataggio del bilancio. "
						+ex.getMessage(),
					    "Errore",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Gestisce le voci del menu Esporta. Crea una variabile
	 * <code>Export</code> nella quale, sfruttando il polimorfismo, instanzia un oggetto
	 * del tipo di esportazione che si vuole fare (testo, csv, excel o opendoc).
	 * Infine, invoca la funzione {@link export.Export#exportBilancio()} della relativa classe.
	 * @param actionCommand nome del MenuItem selezionato
	 * @see export
	 */
	private void actionMenuExport(String actionCommand) {
		if(bilancio.isEmpty()){
			JOptionPane.showMessageDialog(this,
				    "Impossibile esportare il bilancio: il bilancio e' vuoto",
				    "Errore",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Export exp=null;
		switch(actionCommand){
		case "Testo":
			exp=new ExportTesto(bilancio);
			break;
		case "CSV":
			exp=new ExportCSV(bilancio);
			break;
		case "Excel":
			exp=new ExportExcel(bilancio);
			break;
		case "Open Document":
			exp=new ExportOpenDoc(bilancio);
			break;
		default: break;
		}
		if(exp!=null) exp.exportBilancio();
	}
}
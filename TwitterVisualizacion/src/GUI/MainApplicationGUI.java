package GUI;

import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import Dominio.ControladorPrincipal;
import Dominio.Entrada;
import Dominio.Politico;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class MainApplicationGUI {

	private JFrame frame;
	private static JTextField pathInput;
	private JButton cargarArchivosButton;
	private ControladorPrincipal controlador;
	private JButton botonActualizar;
	private JList<Politico> politicosList;
	private JList<Entrada> entradasList;
	private DefaultListModel<Politico> politicosListModel;
	private DefaultListModel<Entrada> entradasListModel;
	private JScrollPane politicosListScroll;
	private JScrollPane entradasListScroll;
	
	private JTextPane entradaContenido;
	private JTextPane entradaMetadatos;
	private JTextPane entradaSentiment;;
	
	private JScrollPane entradaContenidoScroll; 
	private JScrollPane entradaMetadatosScroll;
	private JScrollPane entradaSentimentScroll;
	private JTextPane politicoSentiment;
	private JScrollPane politicoSentimentScroll;
	




	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

/*		EventQueue.invokeLater(new Runnable() {
			public void run() {
		*/
		String url="";
		if(args.length>0) url=args[0];
		try {
					MainApplicationGUI window = new MainApplicationGUI(url);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	/*	});
	}*/

	/**
	 * Create the application.
	 */
	public MainApplicationGUI(String url) {
		initialize(url);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String url) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1073, 631);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);


		controlador = new ControladorPrincipal();

		JButton examinarButton = new JButton("Examinar");
		examinarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Elegir Directorio");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);



				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					pathInput.setText(chooser.getSelectedFile().toString());
					//System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
					//System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
				} else {
					System.out.println("No Selection ");
				}
			}
		});


		examinarButton.setBounds(595, 76, 95, 29);
		frame.getContentPane().add(examinarButton);

		pathInput = new JTextField();
		pathInput.setBounds(140, 75, 443, 29);
		frame.getContentPane().add(pathInput);
		pathInput.setColumns(10);

		cargarArchivosButton = new JButton("Cargar archivos");
		cargarArchivosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.cargarDirectorio(pathInput.getText());
				actualizarTodo();
			}
		});
		cargarArchivosButton.setBounds(288, 115, 142, 29);
		frame.getContentPane().add(cargarArchivosButton);

		JLabel addJLabel = new JLabel("Añadir archivos del directorio");
		addJLabel.setBounds(144, 53, 394, 16);
		frame.getContentPane().add(addJLabel);



		politicosListModel = new DefaultListModel<Politico>();
		/*politicosListModel.addElement(new Politico("Holis"));
		politicosListModel.addElement(new Politico("karakolis"));
		politicosListModel.addElement(new Politico("aiou"));*/

		politicosListScroll = new JScrollPane();
		politicosListScroll.setBounds(16, 184, 184, 411);
		frame.getContentPane().add(politicosListScroll);
		politicosList = new JList<Politico>(politicosListModel);
		politicosList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e){
				entradasListModel.removeAllElements();
				if(!politicosList.isSelectionEmpty())
				{
					Politico p = politicosList.getSelectedValue();
					Vector<Entrada> entradasDeP = controlador.getEntradasDe(p.getNombre());
					for(int i=0;i<entradasDeP.size();i++)
					{
						entradasListModel.addElement(entradasDeP.elementAt(i));
					}
					politicoSentiment.setText(p.getSentiment());
				}

			}
		});
		politicosListScroll.setViewportView(politicosList);


		entradasListModel = new DefaultListModel<Entrada>();

		entradasListScroll = new JScrollPane();
		entradasListScroll.setBounds(237, 184, 260, 313);
		frame.getContentPane().add(entradasListScroll);

		entradasList = new JList<Entrada>(entradasListModel);
		entradasList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(entradasList.isSelectionEmpty())
				{
					entradaContenido.setText("");
					entradaMetadatos.setText("");
					entradaSentiment.setText("");
				}
				else
				{
					Entrada en = entradasList.getSelectedValue();
					entradaContenido.setText(en.getContenidoSinMetadatos());
					entradaMetadatos.setText(en.getMetaDatos());
					entradaSentiment.setText(en.getSentiment());
				}
			}
		});
		entradasListScroll.setViewportView(entradasList);
		
		entradaMetadatosScroll = new JScrollPane();
		entradaMetadatosScroll.setBounds(517, 185, 514, 159);
		frame.getContentPane().add(entradaMetadatosScroll);
		
		entradaMetadatos = new JTextPane();
		entradaMetadatosScroll.setViewportView(entradaMetadatos);
		entradaMetadatos.setEnabled(false);
		entradaMetadatos.setEditable(false);
		
		entradaContenidoScroll = new JScrollPane();
		entradaContenidoScroll.setBounds(517, 356, 514, 138);
		frame.getContentPane().add(entradaContenidoScroll);
		
		entradaContenido = new JTextPane();
		entradaContenido.setEditable(false);
		entradaContenidoScroll.setViewportView(entradaContenido);
		
		entradaSentimentScroll = new JScrollPane();
		entradaSentimentScroll.setBounds(517, 537, 514, 58);
		frame.getContentPane().add(entradaSentimentScroll);
		
		entradaSentiment = new JTextPane();
		entradaSentiment.setEnabled(false);
		entradaSentimentScroll.setViewportView(entradaSentiment);
		entradaSentiment.setEditable(false);
		
		politicoSentimentScroll = new JScrollPane();
		politicoSentimentScroll.setBounds(237, 537, 260, 58);
		frame.getContentPane().add(politicoSentimentScroll);
		
		politicoSentiment = new JTextPane();
		politicoSentimentScroll.setViewportView(politicoSentiment);
		politicoSentiment.setEnabled(false);
		politicoSentiment.setEditable(false);
		
		JLabel lblPolticosPartidos = new JLabel("Políticos / Partidos");
		lblPolticosPartidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblPolticosPartidos.setBounds(18, 156, 182, 16);
		frame.getContentPane().add(lblPolticosPartidos);
		
		JLabel lblPublicaciones = new JLabel("Publicaciones");
		lblPublicaciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblPublicaciones.setBounds(237, 156, 260, 16);
		frame.getContentPane().add(lblPublicaciones);
		
		JLabel lblValoracinGeneral = new JLabel("Valoración General");
		lblValoracinGeneral.setHorizontalAlignment(SwingConstants.CENTER);
		lblValoracinGeneral.setBounds(237, 509, 260, 16);
		frame.getContentPane().add(lblValoracinGeneral);
		
		JLabel lblSentimetDeLa = new JLabel("Sentimet de la publicación");
		lblSentimetDeLa.setHorizontalAlignment(SwingConstants.CENTER);
		lblSentimetDeLa.setBounds(517, 506, 514, 16);
		frame.getContentPane().add(lblSentimetDeLa);
		
		JLabel lblDatosSobreLa = new JLabel("Datos sobre la publicación");
		lblDatosSobreLa.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatosSobreLa.setBounds(517, 157, 514, 16);
		frame.getContentPane().add(lblDatosSobreLa);


		if(url.length()>0) 	pathInput.setText(url);

	}

	private void actualizarTodo()
	{
		politicosListModel.removeAllElements();
		Vector<Politico> ve = controlador.getPoliticos();
		for(int i=0;i<ve.size();i++)
		{
			politicosListModel.addElement(ve.elementAt(i));
		}


	}
}

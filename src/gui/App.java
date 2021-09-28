package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaSource;

import controller.Complejidad;
import controller.Halstead;

public class App {

	private JFrame frmTestingtool;
	private JTextField fileField;
	private JavaProjectBuilder builder;
	private JList<Object> listClasses;
	private JList<Object> listMethods;
	private JTextField lineasTot;
	private JTextField lineasCommentTot;
	private JTextField plcc;
	private JTextField cc;
	private JTextField fanIn;
	private JTextField fanOut;
	private JTextField hLong;
	private JTextField hVol;

	private JavaSource src;
	private JavaClass cls;
	private JavaMethod method;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmTestingtool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestingtool = new JFrame();
		frmTestingtool.setResizable(false);
		frmTestingtool.setTitle("TestingTool");
		frmTestingtool.setSize(1105, 662);
		frmTestingtool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTestingtool.setLocationRelativeTo(null);
		frmTestingtool.getContentPane().setLayout(null);

		JButton openFile = new JButton("Abrir Archivo");
		openFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Java Files", "java");
				chooser.setFileFilter(filter);

				if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
					fileField.setText(chooser.getSelectedFile().getName());
					builder = new JavaProjectBuilder();
					try {
						src = builder.addSource(chooser.getSelectedFile());
						LinkedList<String> names = new LinkedList<String>();
						for (JavaClass clas : src.getClasses()) {
							names.add(clas.getName());
						}
						listClasses.setListData(names.toArray());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		openFile.setBounds(10, 10, 150, 25);
		frmTestingtool.getContentPane().add(openFile);

		fileField = new JTextField();
		fileField.setHorizontalAlignment(SwingConstants.CENTER);
		fileField.setBounds(10, 46, 150, 25);
		frmTestingtool.getContentPane().add(fileField);
		fileField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 162, 750, 450);
		frmTestingtool.getContentPane().add(scrollPane);
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane.setViewportView(textArea);

		JScrollPane scrollPaneClasses = new JScrollPane();
		scrollPaneClasses.setBounds(170, 46, 150, 60);
		frmTestingtool.getContentPane().add(scrollPaneClasses);

		listClasses = new JList<Object>();
		scrollPaneClasses.setViewportView(listClasses);
		listClasses.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cls = src.getClassByName((String) listClasses.getSelectedValue());
				LinkedList<String> methods = new LinkedList<String>();
				for (JavaMethod method : cls.getMethods()) {
					methods.add(method.getName());
				}
				listMethods.setListData(methods.toArray());
			}
		});

		JScrollPane scrollPaneMethods = new JScrollPane();
		scrollPaneMethods.setBounds(330, 46, 150, 60);
		frmTestingtool.getContentPane().add(scrollPaneMethods);

		listMethods = new JList<Object>();
		listMethods.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				method = cls.getMethods().get(listMethods.getSelectedIndex());
				textArea.setText(method.getCodeBlock());
			}
		});
		scrollPaneMethods.setViewportView(listMethods);

		JLabel lblClasses = new JLabel("Seleccione una Clase");
		lblClasses.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblClasses.setHorizontalAlignment(SwingConstants.CENTER);
		lblClasses.setBounds(170, 15, 150, 14);
		frmTestingtool.getContentPane().add(lblClasses);

		JLabel lblMethods = new JLabel("Seleccione un Metodo");
		lblMethods.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMethods.setHorizontalAlignment(SwingConstants.CENTER);
		lblMethods.setBounds(330, 15, 150, 14);
		frmTestingtool.getContentPane().add(lblMethods);

		JLabel lblCodigo = new JLabel("Codigo del Metodo Seleccionado ");
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCodigo.setBounds(10, 131, 750, 20);
		frmTestingtool.getContentPane().add(lblCodigo);

		JLabel lblAnalisis = new JLabel("Analsis del Metodo");
		lblAnalisis.setHorizontalAlignment(SwingConstants.LEFT);
		lblAnalisis.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAnalisis.setBounds(770, 131, 250, 20);
		frmTestingtool.getContentPane().add(lblAnalisis);

		JLabel lblLineasTot = new JLabel("Lineas de Codigo Totales:");
		lblLineasTot.setBounds(770, 168, 200, 20);
		frmTestingtool.getContentPane().add(lblLineasTot);

		JLabel lblComment = new JLabel("Lineas de Codigo Comentadas:");
		lblComment.setBounds(770, 199, 200, 20);
		frmTestingtool.getContentPane().add(lblComment);

		JLabel lblPorcComment = new JLabel("Porcentaje de Lineas Comentadas:");
		lblPorcComment.setBounds(770, 230, 200, 20);
		frmTestingtool.getContentPane().add(lblPorcComment);

		JLabel lblComp = new JLabel("Complejidad Ciclomatica:");
		lblComp.setBounds(770, 261, 200, 20);
		frmTestingtool.getContentPane().add(lblComp);

		JLabel lblFanIn = new JLabel("Fan In:");
		lblFanIn.setBounds(770, 292, 200, 20);
		frmTestingtool.getContentPane().add(lblFanIn);

		JLabel lblFanOut = new JLabel("Fan Out:");
		lblFanOut.setBounds(770, 323, 200, 20);
		frmTestingtool.getContentPane().add(lblFanOut);

		JLabel lblHaslteadLong = new JLabel("Halstead Longitud:");
		lblHaslteadLong.setBounds(770, 354, 200, 20);
		frmTestingtool.getContentPane().add(lblHaslteadLong);

		JLabel lblHalsteadVol = new JLabel("HalsteadVolumen:");
		lblHalsteadVol.setBounds(770, 385, 200, 20);
		frmTestingtool.getContentPane().add(lblHalsteadVol);

		lineasTot = new JTextField();
		lineasTot.setFont(new Font("Tahoma", Font.BOLD, 13));
		lineasTot.setHorizontalAlignment(SwingConstants.CENTER);
		lineasTot.setText("0");
		lineasTot.setBounds(993, 165, 86, 20);
		frmTestingtool.getContentPane().add(lineasTot);
		lineasTot.setColumns(10);

		lineasCommentTot = new JTextField();
		lineasCommentTot.setFont(new Font("Tahoma", Font.BOLD, 13));
		lineasCommentTot.setText("0");
		lineasCommentTot.setHorizontalAlignment(SwingConstants.CENTER);
		lineasCommentTot.setColumns(10);
		lineasCommentTot.setBounds(993, 199, 86, 20);
		frmTestingtool.getContentPane().add(lineasCommentTot);

		plcc = new JTextField();
		plcc.setFont(new Font("Tahoma", Font.BOLD, 13));
		plcc.setText("0%");
		plcc.setHorizontalAlignment(SwingConstants.CENTER);
		plcc.setColumns(10);
		plcc.setBounds(993, 230, 86, 20);
		frmTestingtool.getContentPane().add(plcc);

		cc = new JTextField();
		cc.setFont(new Font("Tahoma", Font.BOLD, 13));
		cc.setText("0");
		cc.setHorizontalAlignment(SwingConstants.CENTER);
		cc.setColumns(10);
		cc.setBounds(993, 261, 86, 20);
		frmTestingtool.getContentPane().add(cc);

		fanIn = new JTextField();
		fanIn.setFont(new Font("Tahoma", Font.BOLD, 13));
		fanIn.setText("0");
		fanIn.setHorizontalAlignment(SwingConstants.CENTER);
		fanIn.setColumns(10);
		fanIn.setBounds(993, 292, 86, 20);
		frmTestingtool.getContentPane().add(fanIn);

		fanOut = new JTextField();
		fanOut.setFont(new Font("Tahoma", Font.BOLD, 13));
		fanOut.setText("0");
		fanOut.setHorizontalAlignment(SwingConstants.CENTER);
		fanOut.setColumns(10);
		fanOut.setBounds(993, 323, 86, 20);
		frmTestingtool.getContentPane().add(fanOut);

		hLong = new JTextField();
		hLong.setFont(new Font("Tahoma", Font.BOLD, 13));
		hLong.setText("0");
		hLong.setHorizontalAlignment(SwingConstants.CENTER);
		hLong.setColumns(10);
		hLong.setBounds(993, 354, 86, 20);
		frmTestingtool.getContentPane().add(hLong);

		hVol = new JTextField();
		hVol.setFont(new Font("Tahoma", Font.BOLD, 13));
		hVol.setText("0");
		hVol.setHorizontalAlignment(SwingConstants.CENTER);
		hVol.setColumns(10);
		hVol.setBounds(993, 385, 86, 20);
		frmTestingtool.getContentPane().add(hVol);

		JButton btnAnalizarCodigo = new JButton("Analizar Codigo");
		btnAnalizarCodigo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lineasTot.setText(String.valueOf(method.getCodeBlock().split("\n").length));


				cc.setText(String.valueOf(new Complejidad(method.getCodeBlock().split("\n")).complejidad()));
				Halstead h = new Halstead(method.getCodeBlock().split("\n"));
				hVol.setText(String.format("%.2f", h.halsteadVol()));
				hLong.setText(String.format("%.2f", h.halsteadLong()));
				if (method.getComment() != null) {
					lineasCommentTot.setText(String.valueOf(method.getComment().split("\n").length + h.commentedLines()));
				} else
					lineasCommentTot.setText(String.valueOf(h.commentedLines()));
				double division = (Double.valueOf(lineasCommentTot.getText())*100/method.getCodeBlock().split("\n").length);
				plcc.setText(String.valueOf(division) + "%");
			}
		});
		btnAnalizarCodigo.setBounds(770, 587, 309, 25);
		frmTestingtool.getContentPane().add(btnAnalizarCodigo);

	}
}

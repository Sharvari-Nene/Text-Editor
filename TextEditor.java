package text_editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {

	JTextArea textArea;
	JScrollPane scrollpane;
	JSpinner fontsizespinner;
	JLabel fontlabel;
	JButton fontColorButton;
	JComboBox fontBox;

	JMenuBar menubar;
	JMenu filemenu;
	JMenuItem open;
	JMenuItem exit;
	JMenuItem save;

	public TextEditor() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Text Editor");
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);

		textArea = new JTextArea();
		// textArea.setPreferredSize(new Dimension(450, 450));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));

		fontlabel = new JLabel("Font Size: ");

		scrollpane = new JScrollPane(textArea);
		scrollpane.setPreferredSize(new Dimension(450, 450));
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		fontsizespinner = new JSpinner();
		fontsizespinner.setPreferredSize(new Dimension(50, 25));
		fontsizespinner.setValue(20);
		fontsizespinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(
						new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontsizespinner.getValue()));
			}
		});

		fontColorButton = new JButton("Color");
		fontColorButton.addActionListener(this);

		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");

		// menu bar

		menubar = new JMenuBar();
		filemenu = new JMenu("File");
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");

		open.addActionListener(this);
		save.addActionListener(this);
		exit.addActionListener(this);

		filemenu.add(open);
		filemenu.add(save);
		filemenu.add(exit);
		menubar.add(filemenu);

		// menu bar end

		this.setJMenuBar(menubar);
		this.add(fontlabel);
		this.add(fontsizespinner);
		this.add(fontColorButton);
		this.add(fontBox);
		this.add(scrollpane);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fontColorButton) {
			JColorChooser colorchooser = new JColorChooser();
			Color color = colorchooser.showDialog(null, "Choose a color ", Color.black);
			textArea.setForeground(color);
		}

		if (e.getSource() == fontBox) {
			textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
		}

		if (e.getSource() == open) {

			JFileChooser filechooser = new JFileChooser();
			filechooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
			filechooser.setFileFilter(filter);

			int reponse = filechooser.showOpenDialog(null);

			if (reponse == JFileChooser.APPROVE_OPTION) {
				File file = new File(filechooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				try {
					fileIn = new Scanner(file);

					if (file.isFile()) {
						while (fileIn.hasNextLine()) {
							String line = fileIn.nextLine() + "\n";
							textArea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				} finally {
					fileIn.close();
				}
			}

		}

		if (e.getSource() == exit) {
			System.exit(0);

		}

		if (e.getSource() == save) {

			JFileChooser filechooser = new JFileChooser();
			filechooser.setCurrentDirectory(new File(""));

			int reponse = filechooser.showSaveDialog(null);

			if (reponse == JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileout = null;

				file = new File(filechooser.getSelectedFile().getAbsolutePath());

				try {
					fileout = new PrintWriter(file);
					fileout.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					fileout.close();
				}
			}

		}
	}

}

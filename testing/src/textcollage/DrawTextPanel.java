package textcollage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * A panel that contains a large drawing area where strings
 * can be drawn.  The strings are represented by objects of
 * type DrawTextItem.  An input box under the panel allows
 * the user to specify what string will be drawn when the
 * user clicks on the drawing area.
 */
public class DrawTextPanel extends JPanel  {
	
    /* Holds the text data */
	private ArrayList<DrawTextItem> itemList = new ArrayList<DrawTextItem>();

	/* Variables for text */
    private Color  currentTextColor    = Color.BLACK;  // Color applied to new strings.
    private Color  currentTextBckColor = null;         // Color applied to new strings.
    private double currentTextBckTrans = 0;            // Color applied to new strings.
    private String currentFontName     = Font.SERIF;   // Font applied to new strings.
    private int    currentFontSize     = 24;           // Font applied to new strings.
    /* Labels - Text changes to reflect current font size and text background alpha */
    private JLabel labelMenuTextBckTrans;
    private JLabel labelMenuFontSize;
    /* MenuItems - Disables to at certain values to prevent extreme displays */
    private JMenuItem menuTextBckTransPlus;  // Disabled when currentTextBckTrans = 1
    private JMenuItem menuTextBckTransLess;  // Disabled when currentTextBckTrans = 0
    private JMenuItem menuFontSizePlus;      // Disabled when currentFontSize = 44
    private JMenuItem menuFontSizeLess;      // Disabled when currentFontSize = 8
    /* CheckBoxes - Bold checked by default */
    private JCheckBoxMenuItem btnCheckFontStyleBold;
    private JCheckBoxMenuItem btnCheckFontStyleItalic;
    /* RadioButtons - Controls the type of font */
    private JRadioButtonMenuItem btnRadioFontNameSerif;
    private JRadioButtonMenuItem btnRadioFontNameSansSerif;
    private JRadioButtonMenuItem btnRadioFontNameMono;
    /* Strings - For ease when checking the event commands to the MenuItems */
    private String strMenuTextBckTrans     = "  Current Text Background Transparency: ";
    private String strMenuTextBckTransPlus = "Increase Text Background Transparency";
    private String strMenuTextBckTransLess = "Decrease Text Background Transparency";
    private String strMenuFontNameString   = "  Set Font Name";
    private String strMenuFontStyleString  = "  Set Font Style";
    private String strMenuFontSize         = "  Cureent Font Size: ";
    private String strMenuFontSizePlus     = "Increase Font Size";
    private String strMenuFontSizeLess     = "Decrease Font Size";
    
	private Canvas canvas;  // the drawing area.
	private JTextField input;  // where the user inputs the string that will be added to the canvas
	private SimpleFileChooser fileChooser;  // for letting the user select files
	private JMenuBar menuBar; // a menu bar with command that affect this panel
	private MenuHandler menuHandler; // a listener that responds whenever the user selects a menu command
	private JMenuItem undoMenuItem;  // the "Remove Item" command from the edit menu
	
	
	/**
	 * An object of type Canvas is used for the drawing area.
	 * The canvas simply displays all the DrawTextItems that
	 * are stored in the ArrayList, strings.
	 */
	private class Canvas extends JPanel {
		Canvas() {
			setPreferredSize( new Dimension(800,600) );
			setBackground(Color.LIGHT_GRAY);
			setFont( new Font( "Serif", Font.BOLD, 24 ));
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_ON);
            if (! itemList.isEmpty())
                for(DrawTextItem item: itemList)
                item.draw(g);
		}
	}
	
	/**
	 * An object of type MenuHandler is registered as the ActionListener
	 * for all the commands in the menu bar.  The MenuHandler object
	 * simply calls doMenuCommand() when the user selects a command
	 * from the menu.
	 */
	private class MenuHandler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			doMenuCommand( evt.getActionCommand());
		}
	}

	/**
	 * Creates a DrawTextPanel.  The panel has a large drawing area and
	 * a text input box where the user can specify a string.  When the
	 * user clicks the drawing area, the string is added to the drawing
	 * area at the point where the user clicked.
	 */
	public DrawTextPanel() {
		fileChooser = new SimpleFileChooser();
		undoMenuItem = new JMenuItem("Remove Item");
		undoMenuItem.setEnabled(false);
		menuHandler = new MenuHandler();
		setLayout(new BorderLayout(3,3));
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		canvas = new Canvas();
		add(canvas, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
        bottom.add(new JLabel("Text to add: "));
		input = new JTextField("Hello World!", 40);
		bottom.add(input);
		add(bottom, BorderLayout.SOUTH);
		canvas.addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				doMousePress( e );
			}
		} );
	}
		
	/**
	 * This method is called when the user clicks the drawing area.
	 * A new string is added to the drawing area.  The center of
	 * the string is at the point where the user clicked.
	 * @param e the mouse event that was generated when the user clicked
	 */
	public void doMousePress( MouseEvent e ) {
		String text = input.getText().trim();
		if (text.length() == 0) {
			input.setText("Hello World!");
			text = "Hello World!";
		}
		// Font Style
		int style = 0;
        if(btnCheckFontStyleBold.isSelected())
            style += Font.BOLD;
        if(btnCheckFontStyleItalic.isSelected())
            style += Font.ITALIC;
        // Font Name
        if(btnRadioFontNameSansSerif.isSelected())
            currentFontName = Font.SANS_SERIF;
        else if(btnRadioFontNameMono.isSelected())
            currentFontName = Font.MONOSPACED;
        else
            currentFontName = Font.SERIF;
		DrawTextItem s = new DrawTextItem( text, e.getX(), e.getY() );
		s.setTextColor(currentTextColor);  // Default is null, meaning default color of the canvas (black).
		s.setBackground(currentTextBckColor);
		s.setBackgroundTransparency(currentTextBckTrans);
		s.setFont( new Font(currentFontName, style, currentFontSize ));
		
//   SOME OTHER OPTIONS THAT CAN BE APPLIED TO TEXT ITEMS:
//		s.setFont( new Font( "Serif", Font.ITALIC + Font.BOLD, 12 ));  // Default is null, meaning font of canvas.
//		s.setMagnification(3);  // Default is 1, meaning no magnification.
//		s.setBorder(true);  // Default is false, meaning don't draw a border.
//		s.setRotationAngle(25);  // Default is 0, meaning no rotation.
//		s.setTextTransparency(0.3); // Default is 0, meaning text is not at all transparent.
//		s.setBackground(Color.BLUE);  // Default is null, meaning don't draw a background area.
//		s.setBackgroundTransparency(0.7);  // Default is 0, meaning background is not transparent.
		
		itemList.add(s);
		undoMenuItem.setEnabled(true);
		canvas.repaint();
	}
	
	/**
	 * Returns a menu bar containing commands that affect this panel.  The menu
	 * bar is meant to appear in the same window that contains this panel.
	 */
	public JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			
			String commandKey; // for making keyboard accelerators for menu commands
			if (System.getProperty("mrj.version") == null)
				commandKey = "control ";  // command key for non-Mac OS
			else
				commandKey = "meta ";  // command key for Mac OS
			
			JMenu fileMenu = new JMenu("File");
			menuBar.add(fileMenu);
			JMenuItem saveItem = new JMenuItem("Save...");
			saveItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "N"));
			saveItem.addActionListener(menuHandler);
			fileMenu.add(saveItem);
			JMenuItem openItem = new JMenuItem("Open...");
			openItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "O"));
			openItem.addActionListener(menuHandler);
			fileMenu.add(openItem);
			fileMenu.addSeparator();
			JMenuItem saveImageItem = new JMenuItem("Save Image...");
			saveImageItem.addActionListener(menuHandler);
			fileMenu.add(saveImageItem);
			
			JMenu editMenu = new JMenu("Edit");
			menuBar.add(editMenu);
			undoMenuItem.addActionListener(menuHandler); // undoItem was created in the constructor
			undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "Z"));
			editMenu.add(undoMenuItem);
			editMenu.addSeparator();
			JMenuItem clearItem = new JMenuItem("Clear");
			clearItem.addActionListener(menuHandler);
			editMenu.add(clearItem);
			
			JMenu optionsMenu = new JMenu("Options");
			menuBar.add(optionsMenu);
            JMenuItem colorItem = new JMenuItem("Set Text Color...");
            colorItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "T"));
            colorItem.addActionListener(menuHandler);
            optionsMenu.add(colorItem);
/* Text Background Color **************************************************** */
            JMenuItem textBgColorItem = new JMenuItem("Set Text Background Color...");
            textBgColorItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "alt T"));
            textBgColorItem.addActionListener(menuHandler);
            optionsMenu.add(textBgColorItem);
            optionsMenu.addSeparator();
/* Text Background Transparency ********************************************* */
            labelMenuTextBckTrans = new JLabel(strMenuTextBckTrans + currentTextBckTrans*100 + "%");
            optionsMenu.add(labelMenuTextBckTrans);
            menuTextBckTransPlus = new JMenuItem(strMenuTextBckTransPlus);
            menuTextBckTransPlus.setAccelerator(KeyStroke.getKeyStroke("alt E"));
            menuTextBckTransPlus.addActionListener(menuHandler);
            optionsMenu.add(menuTextBckTransPlus);
            menuTextBckTransLess = new JMenuItem(strMenuTextBckTransLess);
            menuTextBckTransLess.setAccelerator(KeyStroke.getKeyStroke("alt Q"));
            menuTextBckTransLess.addActionListener(menuHandler);
            menuTextBckTransLess.setEnabled(false);
            optionsMenu.add(menuTextBckTransLess);
            optionsMenu.addSeparator();
/* Font Name **************************************************************** */
            optionsMenu.add(new JLabel(strMenuFontNameString));
            ButtonGroup fontNameBg = new ButtonGroup();
            btnRadioFontNameSerif = new JRadioButtonMenuItem(Font.SERIF, true);
            fontNameBg.add(btnRadioFontNameSerif);
            optionsMenu.add(btnRadioFontNameSerif);
            btnRadioFontNameSansSerif = new JRadioButtonMenuItem(Font.SANS_SERIF);
            fontNameBg.add(btnRadioFontNameSansSerif);
            optionsMenu.add(btnRadioFontNameSansSerif);
            btnRadioFontNameMono = new JRadioButtonMenuItem(Font.MONOSPACED);
            fontNameBg.add(btnRadioFontNameMono);
            optionsMenu.add(btnRadioFontNameMono);
            optionsMenu.addSeparator();
/* Font Style *************************************************************** */
            optionsMenu.add(new JLabel(strMenuFontStyleString));
            btnCheckFontStyleBold = new JCheckBoxMenuItem("Bold", true);
            btnCheckFontStyleBold.setAccelerator(KeyStroke.getKeyStroke(commandKey + "B"));
            optionsMenu.add(btnCheckFontStyleBold);
            btnCheckFontStyleItalic = new JCheckBoxMenuItem("Italic", false);
            btnCheckFontStyleItalic.setAccelerator(KeyStroke.getKeyStroke(commandKey + "I"));
            optionsMenu.add(btnCheckFontStyleItalic);
            optionsMenu.addSeparator();
/* Font Size **************************************************************** */
            labelMenuFontSize = new JLabel(strMenuFontSize + currentFontSize);
            optionsMenu.add(labelMenuFontSize);
            menuFontSizePlus = new JMenuItem(strMenuFontSizePlus);
            menuFontSizePlus.setAccelerator(KeyStroke.getKeyStroke(commandKey + "E"));
            menuFontSizePlus.addActionListener(menuHandler);
            optionsMenu.add(menuFontSizePlus);
            menuFontSizeLess = new JMenuItem(strMenuFontSizeLess);
            menuFontSizeLess.setAccelerator(KeyStroke.getKeyStroke(commandKey + "Q"));
            menuFontSizeLess.addActionListener(menuHandler);
            optionsMenu.add(menuFontSizeLess);
            optionsMenu.addSeparator();
/* ************************************************************************** */
			JMenuItem bgColorItem = new JMenuItem("Set Background Color...");
			bgColorItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "shift T"));
			bgColorItem.addActionListener(menuHandler);
			optionsMenu.add(bgColorItem);
			
		}
		return menuBar;
	}
	
	/**
	 * Carry out one of the commands from the menu bar.
	 * @param command the text of the menu command.
	 */
	private void doMenuCommand(String command) {
/* Save ********************************************************************* */
		if (command.equals("Save...")) { // save all the string info to a file
			File saveFile = fileChooser.getOutputFile(this, "Select File Name", "lab10ImageInfo.txt");
            if (saveFile == null)
                return;
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new FileWriter(saveFile));
                writer.println(canvas.getBackground().getRed());
                writer.println(canvas.getBackground().getGreen());
                writer.println(canvas.getBackground().getBlue());
                if(! itemList.isEmpty())
                    for(DrawTextItem item: itemList) {
                        Color c = item.getTextColor();
                        int rgb[] =  {c.getRed(),  c.getGreen(), c.getBlue()};
                        int rgbBck[] = new int[]{-1, -1, -1};
                        c = item.getBackground();
                        if( c != null)
                            rgbBck =  new int[]{c.getRed(),  c.getGreen(), c.getBlue()};
                        double alpha = item.getBackgroundTransparency();
                        writer.println(item.getString());
                        writer.println(item.getX());
                        writer.println(item.getY());
                        writer.println(rgb[0]);
                        writer.println(rgb[1]);
                        writer.println(rgb[2]);
                        writer.println(rgbBck[0]);
                        writer.println(rgbBck[1]);
                        writer.println(rgbBck[2]);
                        writer.println(alpha);
                        writer.println(item.getFont().getFontName());
                        writer.println(item.getFont().getStyle());
                        writer.println(item.getFont().getSize());
                    }
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Sorry, an error occurred while trying to save the file:\n" + e);
            }
            finally {
                if (writer != null)
                    writer.close();
            }
		}
/* Open ********************************************************************* */
		else if (command.equals("Open...")) { // read a previously saved file, and reconstruct the list of strings
			File openFile = fileChooser.getInputFile(this, "Select File To Open");
            if (openFile == null)
                return;
            ArrayList<DrawTextItem> localList = new ArrayList<DrawTextItem>();
            Scanner scn = null;
            try {
                scn = new Scanner(new FileReader(openFile));
                int rgb[] = {0, 0, 0};
                int rgbBck[] = {-1, -1, -1};
                int xy[] = {0, 0};
                for (int i = 0; i < 3; i++) // rgb of background color
                    rgb[i] = Integer.parseInt(scn.nextLine());
                
                Color bgc = new Color(rgb[0], rgb[1], rgb[2]); // save color
                
                while (scn.hasNextLine()) { // DrawTextItem data
                    String text = scn.nextLine();
                    for (int i = 0; i < 2; i++) // x and y points
                        xy[i] = Integer.parseInt(scn.nextLine());
                    
                    for (int i = 0; i < 3; i++) // rgb info
                        rgb[i] = Integer.parseInt(scn.nextLine());
                    
                    for (int i = 0; i < 3; i++) // rgb info for background
                        rgbBck[i] = Integer.parseInt(scn.nextLine());
                    double bckAlpha = Double.parseDouble(scn.nextLine()); // alpha for text background
                    String fontName = scn.nextLine();
                    int fontStyle = Integer.parseInt(scn.nextLine());
                    int fontSize = Integer.parseInt(scn.nextLine());
                    
                    DrawTextItem item = new DrawTextItem(text, xy[0], xy[1]);
                    item.setTextColor(new Color(rgb[0], rgb[1], rgb[2]));
                    if ( rgbBck[0] < 0 )  // no text background
                        item.setBackground(null);
                    else
                        item.setBackground(new Color( rgbBck[0], rgbBck[1], rgbBck[2]));
                    item.setBackgroundTransparency(bckAlpha);
                    item.setFont(new Font(fontName, fontStyle, fontSize));
                    localList.add(item);
                }
                // No errors from reading the file
                canvas.setBackground(bgc);
                itemList.clear();
                itemList.addAll(localList);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                        "Sorry, an error occurred while trying to open the file:\n" + e);
            } 
            finally {
                if (scn != null)
                    scn.close();
            }
			canvas.repaint(); // (you'll need this to make the new list of strings take effect)
		}
/* Clear and Remove ********************************************************* */
		else if (command.equals("Clear")) {  // remove all strings
			itemList.clear();
		    undoMenuItem.setEnabled(false);
			canvas.repaint();
		}
		else if (command.equals("Remove Item")) { // remove the most recently added string
			itemList.remove(itemList.size()-1);
		    undoMenuItem.setEnabled(false);
			canvas.repaint();
		}
/* Text Color and Text Background Color ************************************* */
        else if (command.equals("Set Text Color...")) {
            Color c = JColorChooser.showDialog(this, "Select Text Color", currentTextColor);
            if (c != null)
                currentTextColor = c;
        }
        else if (command.equals("Set Text Background Color...")) {
            Color c = JColorChooser.showDialog(this, "Select Text Background Color", currentTextColor);
            if (c != null)
                currentTextBckColor = c;
        }
/* Text Background Transparency ********************************************* */
        else if (command.equals(strMenuTextBckTransPlus)) {
            if(currentTextBckTrans == 0) 
                menuTextBckTransLess.setEnabled(true);
            currentTextBckTrans = ((int)(currentTextBckTrans *100) + 10) / 100.0;
            if(currentTextBckTrans == 1)
                menuTextBckTransPlus.setEnabled(false);
            labelMenuTextBckTrans.setText(strMenuTextBckTrans + (int)(currentTextBckTrans*100) + "%");
        }
        else if (command.equals(strMenuTextBckTransLess)) {
            if(currentTextBckTrans == 1) 
                menuTextBckTransPlus.setEnabled(true);
            currentTextBckTrans = ((int)(currentTextBckTrans *100) - 10) / 100.0;
            if(currentTextBckTrans == 0)
                menuTextBckTransLess.setEnabled(false);
            labelMenuTextBckTrans.setText(strMenuTextBckTrans + (int)(currentTextBckTrans*100) + "%");
        }
/* Font Size **************************************************************** */
        else if (command.equals(strMenuFontSizePlus)) {
            if(currentFontSize == 8) 
                menuFontSizeLess.setEnabled(true);
            currentFontSize += 2;
            if(currentFontSize == 44)
                menuFontSizePlus.setEnabled(false);
            labelMenuFontSize.setText(strMenuFontSize + currentFontSize);
        }
        else if (command.equals(strMenuFontSizeLess)) {
            if(currentFontSize == 44) 
                menuFontSizePlus.setEnabled(true);
            currentFontSize -= 2;
            if(currentFontSize == 8)
                menuFontSizeLess.setEnabled(false);
            labelMenuFontSize.setText(strMenuFontSize + currentFontSize);
        }
/* ************************************************************************** */
        else if (command.equals("Set Background Color...")) {
            Color c = JColorChooser.showDialog(this, "Select Background Color", canvas.getBackground());
            if (c != null) {
                canvas.setBackground(c);
                canvas.repaint();
            }
        }
		else if (command.equals("Save Image...")) {  // save a PNG image of the drawing area
			File imageFile = fileChooser.getOutputFile(this, "Select Image File Name", "textimage.png");
			if (imageFile == null)
				return;
			try {
				// Because the image is not available, I will make a new BufferedImage and
				// draw the same data to the BufferedImage as is shown in the panel.
				// A BufferedImage is an image that is stored in memory, not on the screen.
				// There is a convenient method for writing a BufferedImage to a file.
				BufferedImage image = new BufferedImage(canvas.getWidth(),canvas.getHeight(),
						BufferedImage.TYPE_INT_RGB);
				Graphics g = image.getGraphics();
				g.setFont(canvas.getFont());
				canvas.paintComponent(g);  // draws the canvas onto the BufferedImage, not the screen!
				boolean ok = ImageIO.write(image, "PNG", imageFile); // write to the file
				if (ok == false)
					throw new Exception("PNG format not supported (this shouldn't happen!).");
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(this, 
						"Sorry, an error occurred while trying to save the image:\n" + e);
			}
		}
	}
	

}

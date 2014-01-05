import java.awt.GridLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.lang.NumberFormatException;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JInternalFrame;
import javax.swing.JDialog;
import java.io.*;
import java.util.Scanner;
import java.util.Formatter;

/** This class provides a window that enables users to set preferences
  * 
  * @author Shirley Du
  * @version May.30/12
  */
public class PrefsWindow extends JDialog
{
    // instance variables
    private JButton save, cancel;
    private JComboBox colorComboBox, shapeComboBox, color2ComboBox; 
    private JCheckBox filled, gradient, dashed;
    private Color colorComboBoxes[] = {Color.BLACK, Color.BLACK, Color.BLUE, Color.CYAN, 
        Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, 
        Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, 
        Color.YELLOW};
    private String myColors1[] = {"Primary Color", "Black", "Blue", "Cyan", "Dark gray", "Gray",
        "Green", "Light gray", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};
    private String myColors2[] = {"Secondary Color", "Black", "Blue", "Cyan", "Dark gray", "Gray",
        "Green", "Light gray", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};
    private String myShapes[] = {"Line","Rectangle", "Oval" };
    private JPanel panel, label1Panel, label2Panel, buttonPanel;
    private JTextField strokeWidthField, dashLengthField;
    private int strokeWidth, dashLength;
    private FlowLayout flowLayout;
    private GridBagConstraints constraint;
    private JLabel defaultColor1, defaultColor2, defaultShape, strokeWidthLabel, dashLengthLabel;
    private DrawPanel drawPanel;
    
    /** Constructor sets up GUI and registers mouse event handlers.
      * creates buttons, dropdowns and checkboxes needed for drawing a shapeComboBox
      */
    public PrefsWindow()
    {
        super((JFrame)null, "Preferences", true);
        setResizable(false);
        
        drawPanel = new DrawPanel(new JLabel(""));
        flowLayout = new FlowLayout();
        // sets up different panels for later use
        panel = new JPanel();
        label1Panel = new JPanel();
        label2Panel = new JPanel();
        buttonPanel = new JPanel();
        
        // initialize colorComboBox
        colorComboBox = new JComboBox(myColors1);
        colorComboBox.setSelectedItem(myColors1[drawPanel.getColor1Index()]);
        panel.add(colorComboBox);
        
        // initialize color2ComboBox
        color2ComboBox = new JComboBox(myColors2);
        color2ComboBox.setSelectedItem(myColors2[drawPanel.getColor2Index()]);
        panel.add(color2ComboBox);
        
        // initialize shapeComboBox
        shapeComboBox = new JComboBox(myShapes);
        shapeComboBox.setSelectedItem(myShapes[drawPanel.getShape()]);
        panel.add(shapeComboBox);
        
        filled = new JCheckBox("Filled");
        filled.setSelected(drawPanel.getIsFilled());
        panel.add(filled);
        
        gradient = new JCheckBox("Gradient");
        gradient.setSelected(drawPanel.getIsGradient());
        panel.add(gradient);
        
        dashed = new JCheckBox("Dashed");
        dashed.setSelected(drawPanel.getIsDashed());
        panel.add(dashed);
        CheckBoxHandler checkBoxHandler = new CheckBoxHandler();
        dashed.addItemListener(checkBoxHandler);
        
        label1Panel.setLayout(flowLayout);
        strokeWidthLabel = new JLabel("Stroke width");
        flowLayout.setAlignment(FlowLayout.RIGHT);
        label1Panel.add(strokeWidthLabel);
        panel.add(label1Panel);
        
        strokeWidthField = new JTextField(3);
        strokeWidthField.setText(String.valueOf(drawPanel.getStrokeWidth()));
        panel.add(strokeWidthField);
        
        label2Panel.setLayout(flowLayout);
        dashLengthLabel = new JLabel("Dash length");
        flowLayout.setAlignment(FlowLayout.RIGHT);
        label2Panel.add(dashLengthLabel);
        panel.add(label2Panel);
        
        dashLengthField = new JTextField(3);
        dashLengthField.setText(String.valueOf(drawPanel.getDashLength()));
        dashLengthField.setEditable(drawPanel.getIsDashed());
        panel.add(dashLengthField);
        
        save = new JButton("Save");
        ButtonHandler buttonHandler = new ButtonHandler();
        save.addActionListener(buttonHandler);
        buttonPanel.add(save);
        
        cancel = new JButton("Cancel");
        cancel.addActionListener(buttonHandler);
        buttonPanel.add(cancel);
        
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    //Inner class for button event handling
    private class ButtonHandler implements ActionListener
    {
        // instance variables
        private String strokeWidthStr, dashLengthStr;
        private float strokeWidth, dashLength;
        private boolean isDashed;
        
        // handle button event
        public void actionPerformed(ActionEvent event)
        {
            if (event.getActionCommand()== "Save")
            {
                try
                {
                    Formatter fileOutput = new Formatter(new File("default settings.txt"));
                    isDashed = dashed.isSelected();
                    strokeWidthStr = strokeWidthField.getText();
                    strokeWidth = check(strokeWidthStr, "stroke");
                    dashLengthStr = dashLengthField.getText();
                    dashLength = check(dashLengthStr, "dash");
                    fileOutput.format("%d\n%d\n%d\n%b\n%b\n%b\n%s\n%s\n",colorComboBox.getSelectedIndex(),color2ComboBox.getSelectedIndex(), 
                                      shapeComboBox.getSelectedIndex(), filled.isSelected(), gradient.isSelected(),
                                      isDashed, strokeWidth, dashLength);
                    fileOutput.close();
                    dispose();
                }
                catch (IOException i)
                {}
                
            }
            
            else if (event.getActionCommand() == "Cancel")
            {
                // close the prefs window
                dispose();
            }
        }
        
        // this method checks if the passed-in value is valid, and makes relavent changes
        private float check(String text, String whichOne)
        {
            try 
            {
                float num = Float.parseFloat(text);
                // deals with input values less than or equal to 0
                if (whichOne.equals("stroke") && num <= 0)
                    // sets default strokeWidth value to 1
                    return 1;
                else if (whichOne.equals("dash") && num <= 0)
                {
                    // sets "not dashed" as default
                    isDashed = false;
                    return 10;
                }
                return num;
            }
            // if the value user entered is not a number
            catch (NumberFormatException n)
            {
                if (whichOne.equals("stroke"))
                        return 1;
                else
                {
                    isDashed = false;
                    return 10;
                }
            }
        }
    }
    
    // private inner class for ItemListener event handling
    private class CheckBoxHandler implements ItemListener
    {
        // respond to checkbox events
        public void itemStateChanged(ItemEvent event)
        {
            if (event.getSource() == dashed)
            {
                dashLengthField.setEditable(dashed.isSelected());
            }
        }
    }
    
}
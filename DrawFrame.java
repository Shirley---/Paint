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


/** This class provides a GUI that enables the user to control various aspects of drawing.
  * The layout is BorderLayout.
  * 
  * @author Shirley Du
  * @version May.30/12
  */
public class DrawFrame extends JInternalFrame
{
    // instance variables
    private JButton undo, clear, redo;
    private JComboBox colorComboBox, shapeComboBox, color2ComboBox; 
    private JCheckBox filled, gradient, dashed;
    private Color colorComboBoxes[] = {Color.BLACK, Color.BLACK, Color.BLUE, Color.CYAN, 
        Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, 
        Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, 
        Color.YELLOW};
    private String myColors[] = {"Primary Color", "Black", "Blue", "Cyan", "Dark gray", "Gray",
        "Green", "Light gray", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};
    private String myColors2[] = {"Secondary Color", "Black", "Blue", "Cyan", "Dark gray", "Gray",
        "Green", "Light gray", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};
    private String myShapes[] = {"Line","Rectangle", "Oval" };
    private DrawPanel panel;
    private JPanel top, label1Panel, label2Panel;
    private JTextField strokeWidthField, dashLengthField;
    private int strokeWidth, dashLength;
    private FlowLayout flowLayout;
    private GridBagConstraints constraint;
    
    /** Constructor sets up GUI and registers mouse event handlers.
      * creates buttons, dropdowns and checkboxes needed for drawing a shapeComboBox
      */
    public DrawFrame()
    {
        super("", true, true, true, true);
        
        // creates constraint object
        constraint = new GridBagConstraints();
        
        setLayout(new BorderLayout());
        flowLayout = new FlowLayout();
        top = new JPanel(new GridBagLayout());
        // set up two panels for labels
        label1Panel = new JPanel();
        label2Panel = new JPanel();
        // creating a label object and pass in to create a DrawPanel object
        JLabel myLabel = new JLabel("");
        panel = new DrawPanel(myLabel); 
        
        //Deals with extra space when window is resized
        constraint.fill = GridBagConstraints.BOTH;
        //weight on how extra space is dealt with for a component
        constraint.weighty = 1.0; 
        constraint.weightx = 1.0;
        //position
        constraint.gridx = 1;
        constraint.gridy = 0;
        undo = new JButton("Undo");
        top.add(undo, constraint);
        // create new ButtonHandler for button event handling
        ButtonHandler buttonHandler = new ButtonHandler();
        undo.addActionListener(buttonHandler);
        
        //position
        constraint.gridx = 1;
        constraint.gridy = 1;
        redo = new JButton("Redo");
        top.add(redo,constraint);
        redo.addActionListener(buttonHandler);
        
        // this button will take 2 cells vertically
        constraint.gridheight = 2;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 0;
        clear = new JButton("Clear");
        top.add(clear, constraint);
        clear.addActionListener(buttonHandler);
        
        // this button will take 1 cell vertically
        constraint.gridheight = 1;
        constraint.gridx = 6;
        constraint.gridy = 0;
        colorComboBox = new JComboBox(myColors);
        colorComboBox.setSelectedItem(myColors[panel.getColor1Index()]);
        top.add(colorComboBox, constraint);
        // register listeners for JComboBoxes
        ColorHandler colorComboBoxHandler = new ColorHandler();
        colorComboBox.addItemListener(colorComboBoxHandler);  
        
        //Places the componenet in the next possible position
        constraint.gridx = 6;
        constraint.gridy = 1;
        color2ComboBox = new JComboBox(myColors2);
        color2ComboBox.setSelectedItem(myColors2[panel.getColor2Index()]);
        top.add(color2ComboBox,constraint);
        color2ComboBox.addItemListener(colorComboBoxHandler);
        
        constraint.gridx = 2;
        constraint.gridy = 0;
        shapeComboBox = new JComboBox(myShapes);
        shapeComboBox.setSelectedItem(myShapes[panel.getShape()]);
        top.add(shapeComboBox, constraint);
        // register listeners for JComboBoxes
        ShapeHandler shapeComboBoxHandler = new ShapeHandler();
        shapeComboBox.addItemListener(shapeComboBoxHandler);
        
        constraint.gridx = 3;
        constraint.gridy = 0;
        filled = new JCheckBox("Filled");
        filled.setSelected(panel.getIsFilled());
        top.add(filled, constraint);
        // register listeners for JCheckBoxes
        CheckBoxHandler checkBoxHandler = new CheckBoxHandler();
        filled.addItemListener(checkBoxHandler);
        
        constraint.gridx = 5;
        constraint.gridy = 0;
        gradient = new JCheckBox("Gradient");
        gradient.setSelected(panel.getIsGradient());
        top.add(gradient, constraint);
        gradient.addItemListener(checkBoxHandler);
        
        constraint.gridx = 4;
        constraint.gridy = 0;
        dashed = new JCheckBox("Dashed");
        dashed.setSelected(panel.getIsDashed());
        top.add(dashed, constraint);
        dashed.addItemListener(checkBoxHandler);
        
        constraint.gridx = 2;
        constraint.gridy = 1;
        label1Panel.setLayout(flowLayout);
        JLabel label1 = new JLabel("Stroke width");
        flowLayout.setAlignment(FlowLayout.RIGHT);
        label1Panel.add(label1);
        top.add(label1Panel, constraint);
        
        constraint.gridx = 3;
        constraint.gridy = 1;
        strokeWidthField = new JTextField(3);
        strokeWidthField.setText(String.valueOf(panel.getStrokeWidth()));
        top.add(strokeWidthField, constraint);
        TextFieldHandler textFieldHandler = new TextFieldHandler();
        strokeWidthField.addActionListener(textFieldHandler);
        
        constraint.gridx = 4;
        constraint.gridy = 1;
        label2Panel.setLayout(flowLayout);
        JLabel label2 = new JLabel("Dash length");
        // put it to the right of the label1Panel
        flowLayout.setAlignment(FlowLayout.RIGHT);
        label2Panel.add(label2);
        top.add(label2Panel, constraint);
        
        constraint.gridx = 5;
        constraint.gridy = 1;
        dashLengthField = new JTextField(3);
        // if the user did not set dashed as default, he/she will not be able to edit dashLengthField unless otherwise selected in the panel
        dashLengthField.setEditable(panel.getIsDashed());
        dashLengthField.setText(String.valueOf(panel.getDashLength()));
        top.add(dashLengthField, constraint);
        dashLengthField.addActionListener(textFieldHandler);
        
        // adds components to JFrame
        add(top,BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(myLabel, BorderLayout.SOUTH);
    }
    
    //Inner class for button event handling
    private class ButtonHandler implements ActionListener
    {
        // handle button event
        public void actionPerformed(ActionEvent event)
        {
            if (event.getActionCommand()== "Undo")
                panel.clearLastShape();
            else if (event.getActionCommand() == "Redo")
                panel.redoLastShape();
            else
                panel.clearDrawing();
        }
    }
    
    // Inner class for combo box event handling
    private class ColorHandler implements ItemListener
    {
        // handle comboBox event
        public void itemStateChanged(ItemEvent event)
        {
            if (event.getSource() == colorComboBox)
                panel.setColor1(colorComboBoxes[colorComboBox.getSelectedIndex()]);
            if (event.getSource() == color2ComboBox)
                panel.setColor2(colorComboBoxes[color2ComboBox.getSelectedIndex()]);
        }
    }
    
    // private inner class for ItemListener event handling
    private class ShapeHandler implements ItemListener
    {
        // respond to combo box events
        public void itemStateChanged(ItemEvent event)
        {
            // determine whether check box selected
            if (event.getStateChange() == ItemEvent.SELECTED)
                panel.setCurrentShapeType(shapeComboBox.getSelectedIndex());
        }
    }
    
    // private inner class for ItemListener event handling
    private class CheckBoxHandler implements ItemListener
    {
        // respond to checkbox events
        public void itemStateChanged(ItemEvent event)
        {
            if (event.getSource() == filled)
                panel.setCurrentShapeFilled(filled.isSelected());
            if (event.getSource() == dashed)
            {
                dashLengthField.setEditable(dashed.isSelected());
                panel.setIsDashed();
            }
            if (event.getSource() == gradient)
            {
                panel.setIsGradient();
            }
        }
    }
    
    // private inner class for ActionListener event handling
    private class TextFieldHandler implements ActionListener
    {
        // instance variables
        private String strokeWidthStr, dashLengthStr;
        private float strokeWidth, dashLength, previousStroke, previousDash;
        
        // respond to textField events
        public void actionPerformed(ActionEvent event)
        {
            previousStroke = panel.getStrokeWidth();
            previousDash = panel.getDashLength();
            
            if (event.getSource() == strokeWidthField)
            {
                try
                {
                    strokeWidthStr = event.getActionCommand();
                    strokeWidth = check(strokeWidthStr, "stroke");
                    panel.setStroke(strokeWidth);
                }
                // if user entered a non-numeric value, it will ignore it
                catch (NumberFormatException n)
                {}
            }
            if (event.getSource() == dashLengthField)
            {
                try
                {
                    dashLengthStr = event.getActionCommand();
                    dashLength = check(dashLengthStr, "dash");
                    panel.setDashed(dashLength);
                }
                // if user entered a non-numeric value, it will do nothing
                catch (NumberFormatException n)
                {}
            }
        }
        
        // helper method checks if the value entered is valid
        private float check(String text, String whichOne)
        {
            try 
            {
                float num = Float.parseFloat(text);
                
                if (whichOne.equals("stroke") && num <= 0)
                    // The previous valid value will be used as strokeWidth
                    return previousStroke;
                // if the value entered for strokeWidth is valid, store it to previousStroke and return the valid number
                else if (whichOne.equals("stroke") && num > 0)
                {
                    previousStroke = num;
                    return num;
                }
                // if the value entered for dashLength is smaller or equal to 0, a previous valid value will be used
                else if (whichOne.equals("dash") && num <= 0)
                    return previousDash;
                // if the value entered for dashLength is valid, store it to previousDash and return the valid number
                else
                {
                    previousDash = num;
                    return num;
                }
            }
            // if user entered a non-numeric value, it will return a previous valid value
            catch (NumberFormatException n)
            {
                if (whichOne.equals("stroke"))
                    return previousStroke;
                else 
                {
                    return previousDash;
                }
            }
        }
    }
    
    
}
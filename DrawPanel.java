import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Graphics2D;
import java.util.Scanner;
import java.util.Formatter;
import java.io.*;
import java.util.NoSuchElementException;

/**
 * This class represent sthe area on which the user draws the shapes.
 * Each shape is added to an array for drawing and deleting purposes.
 * It also provides event handling to enable the user to draw with the mouse.
 * paintComponent() method will go through each shape and draw them on screen.
 * 
 * @author Shirley Du
 * version May.30/12
 */
public class DrawPanel extends JPanel
{
    // instance variables for DrawPanel 
    private MyShape shapeObjects[] ;   // store all of the shape objects the user draws.
    private int numOfShapes;  // count the number of shapes in the array
    private int currentShapeType;  // determine the type of shape to draw
    private MyShape currentShapeObject;  // represent the current shape the user is drawing
    private Color color1, color2;  // represent the current drawing colours
    private boolean currentShapeFilled, isDashed, isGradient;  
    private JLabel statusLabel = new JLabel();  // label to display mouse position
    private final int LINES = 0;
    private final int RECTS = 1;
    private final int OVALS = 2;
    private float strokeWidth, dashLength ;
    private int color1Index, color2Index;
    private Graphics2D g2d;
    
    // Constructor creating a panel with random shapes
    public DrawPanel(JLabel label)
    {
        // local variable
        Color myColors[] = {Color.BLACK, Color.BLACK, Color.BLUE, Color.CYAN, 
            Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, 
            Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, 
            Color.YELLOW};
        try
        {
            // trying to read "default settings.txt" from the same directory
            Scanner fileInput = new Scanner(new File("default settings.txt"));
            // Assigns each value with a title
            color1Index = fileInput.nextInt();
            color1 = myColors[color1Index];
            color2Index = fileInput.nextInt();
            color2 = myColors[color2Index];
            currentShapeType = fileInput.nextInt();
            currentShapeFilled = fileInput.nextBoolean();
            isGradient = fileInput.nextBoolean();
            isDashed = fileInput.nextBoolean();
            strokeWidth = fileInput.nextFloat();
            dashLength = fileInput.nextFloat();
            fileInput.close();
        }
        // if the file doesn't exist, it will jump to the following default settings
        catch (IOException i)
        {
            color1 = Color.BLACK;
            color2 = Color.BLACK;
            currentShapeType = LINES;
            currentShapeFilled = false;
            isGradient = false;
            isDashed = false;
            strokeWidth = 1;
            dashLength = 10;
        }
        
        statusLabel = label;
        shapeObjects = new MyShape[100];
        numOfShapes = 0;
        
        setBackground( Color.WHITE );
        
        // create and register listener for mouse and mouse motion events
        MouseHandler mouseHandler = new MouseHandler();  
        addMouseMotionListener( mouseHandler );  
        addMouseListener(mouseHandler);  
    } 
    
    // Mutator method for currentShapeType
    public void setCurrentShapeType(int type)
    {
        currentShapeType = type;
    }
    
    // Mutator method for color1
    public void setColor1(Color color)
    {
        color1 = color;
    }
    
    // Mutator method for color2
    public void setColor2(Color color)
    {
        color2 = color;
    }
    
    // Mutator method for currentShapefilled
    public void setCurrentShapeFilled(boolean filled)
    {
        currentShapeFilled = filled;
    }
    
    // Mutator method for dashLength
    public void setDashed(float dashLength)
    {
        
        this.dashLength = dashLength;
    }
    
    // Mutator method for isDashed
    public void setIsDashed()
    {
        isDashed = !isDashed;
    }
    
    // Mutator method for strokeWidth
    public void setStroke(float strokeWidth)
    {
        this.strokeWidth = strokeWidth;
    }
    
    // Mutator method for isGradient
    public void setIsGradient()
    {
        isGradient = !isGradient;
    }
    
    // Accessor method returning color1Index
    public int getColor1Index()
    {
        return color1Index;
    }
    
    // Accessor method returning color2Index
    public int getColor2Index()
    {
        return color2Index;
    }
    
    // Accessor method returning currentShapeType
    public int getShape()
    {
        return currentShapeType;
    }
    
    //Accessor method returning currentShapeFilled
    public boolean getIsFilled()
    {
        return currentShapeFilled;
    }
    
    // Accessor method returning isGradient
    public boolean getIsGradient()
    {
        return isGradient;
    }
    
    // Accessor method returning isDashed
    public boolean getIsDashed()
    {
        return isDashed;
    }
    
    // Accessor method returning strokeWidth
    public float getStrokeWidth()
    {
        return strokeWidth;
    }
    
    // Accessor method returning dashLength
    public float getDashLength()
    {
        return dashLength;
    }
    
    // Clear the last shape drawn by decrementing the instance variable numOfShapes
    public void clearLastShape()
    {
        if (numOfShapes > 0)
        {
            numOfShapes --;
            repaint();
        }
    }
    
    // Redo the last shape cleared by user
    public void redoLastShape()
    {
        if (shapeObjects[numOfShapes] != null)
        {
            numOfShapes ++;
            repaint();
        }
    }
    
    // Remove all the shapes in the current drawing by setting numOfShapes to zero
    public void clearDrawing()
    {
        numOfShapes = 0;
        shapeObjects = new MyShape[100];
        repaint();
    }
    
    // This method draws the individual shapes in the shapeObjects array
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        g2d = ( Graphics2D ) g;
        
        for (int i = 0; i < numOfShapes; i++)
            shapeObjects[i].draw(g2d);
        
        if (currentShapeObject != null)
        {
            currentShapeObject.draw(g2d);
        }
    }
    
    // Inner class handles all mouse events
    private class MouseHandler extends MouseAdapter implements MouseMotionListener
    {
        // MouseListener event handlers.
        // Assign currentShapeObject a new shape of the type specified by currentShapeType and initializes both points to the mosue position. 
        public void mousePressed( MouseEvent event ) 
        { 
            int x = event.getX();
            int y = event.getY();
            if (currentShapeType == LINES)
            {
                // calls different constructors accordingly
                if (isDashed && isGradient)
                    currentShapeObject = new MyLine(x,y,x,y,color1,strokeWidth, dashLength, color2);
                else if (isDashed)
                    currentShapeObject = new MyLine(x,y,x,y,color1,strokeWidth, dashLength);
                else if (isGradient)
                    currentShapeObject = new MyLine(x,y,x,y,color1, strokeWidth,color2);               
                else
                    currentShapeObject = new MyLine(x,y,x,y,color1, strokeWidth);
            }
            else if (currentShapeType == RECTS)
            {  
                // calls different constructors accordingly
                if (isDashed && isGradient)
                    currentShapeObject = new MyRectangle(x,y,x,y,color1,currentShapeFilled,strokeWidth, dashLength, color2);
                else if (isDashed)
                    currentShapeObject = new MyRectangle(x,y,x,y,color1, currentShapeFilled,strokeWidth, dashLength);
                else if (isGradient)
                    currentShapeObject = new MyRectangle(x,y,x,y,color1, currentShapeFilled, strokeWidth, color2);
                
                else
                    currentShapeObject = new MyRectangle(x,y,x,y,color1, currentShapeFilled, strokeWidth);
            }
            else
            {
                // calls different constructors accordingly
                if (isDashed && isGradient)
                    currentShapeObject = new MyOval(x,y,x,y,color1,currentShapeFilled,strokeWidth, dashLength, color2);
                else if (isDashed)
                    currentShapeObject = new MyOval(x,y,x,y,color1, currentShapeFilled,strokeWidth, dashLength);
                else if (isGradient)
                    currentShapeObject = new MyOval(x,y,x,y,color1, currentShapeFilled,strokeWidth,color2);
                
                else
                    currentShapeObject = new MyOval(x,y,x,y,color1, currentShapeFilled, strokeWidth);  
            }
        }
        
        // Finish drawing the current shape and place it in the shapeObjects array.
        // Set the second point of currentShapeObject to the current mouse position and add it to the array.
        public void mouseReleased(MouseEvent event)
        {
            if (currentShapeObject == null)
                return ;
            currentShapeObject.setX2(event.getX());
            currentShapeObject.setY2(event.getY());
            
            shapeObjects[numOfShapes++] = currentShapeObject;
            currentShapeObject = null;
            repaint();     
        }
        
        // MouseAdapter event handlers
        // Set the text of the statusLabel so that it displays the mouse coordinates
        public void mouseMoved(MouseEvent event)
        {
            String str = event.getX() + ", " + event.getY();
            statusLabel.setText(str);
        }
        
        // Set the second point of the currentShapeObjects to the current mouse position and calls method repaint()
        public void mouseDragged(MouseEvent event)
        {
            if (currentShapeObject == null)
                return ;
            
            currentShapeObject.setX2(event.getX());
            currentShapeObject.setY2(event.getY());
            if (isGradient)
                currentShapeObject.setGradient();
            
            String str = event.getX() + ", " + event.getY();
            statusLabel.setText(str);
            repaint();
        }
    }
}

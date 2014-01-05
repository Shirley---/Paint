import java.awt.Color;
import java.awt.Graphics;
import java.awt.GradientPaint;

/** Abstract class inheriting from MyShape class
  * Provides a template for subclasses to override.
  * Provides accessors in regard of upper left x and y coordinates, width, height, and boolean filled.
  * 
  * @author Shirley Du
  * @version May.30/12
  */
abstract class MyBoundedShape extends MyShape
{
    private boolean filled;
    
    // Constructor setting default values for x1,x2,y1,y2,color, and filled
    public MyBoundedShape()
    {
        super();
        filled = false;
    }
    
    // Constructor that takes input values x1, y1, x2, y2, color, filled, and strokeWidth
    public MyBoundedShape(int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth)
    {
        super(x1,y1,x2,y2,color, strokeWidth);
        this.filled = filled;
    }
    
    // Constructor that takes an extra input value dashLength 
    public MyBoundedShape(int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth, float dashLength)
    {
        super(x1,y1,x2,y2,color,strokeWidth,dashLength);
        this.filled = filled;
    }
    
    // Constructor that takes an extra input value color2 instead of dashLength
    public MyBoundedShape(int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth, Color color2)
    {
        super(x1,y1,x2,y2,color, strokeWidth, color2);
        this.filled = filled;
    }
    
    // Constructor that takes both extra input values color2 and dashLength
    public MyBoundedShape(int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth, float dashLength, Color color2)
    {
        super(x1,y1,x2,y2,color,strokeWidth,dashLength, color2);
        this.filled = filled;
    }
    // Accessor returns the upper left x coordinate
    public int getUpperLeftX()
    {
        return Math.min(getX1(),getX2());
    }
    
    // Accessor returns the upper left y coordinate
    public int getUpperLeftY()
    {
        return Math.min(getY1(),getY2());
    }
    
    // Accessor returns the width of the bounded rectangle
    public int getWidth()
    {
        return Math.abs(getX1()-getX2());
    }
    
    // Accessor returns the height of the bounded rectangle
    public int getHeight()
    {
        return Math.abs(getY1()-getY2());
    }
    
    // Accessor returns if the shape is filled
    public boolean getFilled()
    {
        return filled;
    }     
    
    // Mutator gets passed in a boolean value and sets "filled" that value.
    public void setFilled(boolean filled)
    {
        this.filled = filled;
    }
}

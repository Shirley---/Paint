import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.GradientPaint;

/** Abstract class providing a template for MyLine and MyBoundedShape to override.
  * Provides mutators and accessors in regard of x1,x2,y1,y2, color, stroke, dashed, and gradient
  * 
  * @author Shirley Du
  * @version May.30/12
  */
abstract class MyShape
{
    // instance variables for MyShape
    // x coordinate of first endpoint
    private int x1; 
    // y coordinate of first endpoint
    private int y1; 
    // x coordinate of second endpoint
    private int x2; 
    // y coordinate of second endpoint
    private int y2; 
    // color of this shape
    private Color color1; 
    private Color color2;
    private BasicStroke stroke;
    private float[] dash = new float[1];
    private GradientPaint gradient;
    
    
    // Constructor setting x1,y1,x2,y2 to 0 and color1 to black
    public MyShape()
    {
        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
        color1 = Color.BLACK;
        color2 = Color.BLACK;
        stroke = new BasicStroke(1);
        gradient =  new GradientPaint(x1,y1, color1, x2,y2, color2, false);
    }
    
    // Constructor with input values x1, y1, x2, y2, color, and strokeWidth
    public MyShape(int x1,int y1,int x2,int y2,Color color, float strokeWidth)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2; 
        this.y2 = y2;
        this.color1 = color;
        stroke = new BasicStroke(strokeWidth);
    }
    
    // Constructor with extra input value dashLength
    public MyShape(int x1,int y1,int x2,int y2,Color color1, float strokeWidth, float dashLength)
    {
        this(x1,y1,x2,y2,color1,strokeWidth);
        setDashed(strokeWidth, dashLength);
    }
    
    // Constructor with extra input value color2 instead of dashLength
    public MyShape(int x1,int y1,int x2,int y2,Color color1, float strokeWidth, Color color2)
    {
        this(x1,y1,x2,y2,color1,strokeWidth);
        this.color2 = color2;
        gradient = new GradientPaint(x1, y1, color1, x2, y2, color2, false);
    }
    
    // Constructor with both extra input values dashLength and color2
    public MyShape(int x1,int y1,int x2,int y2,Color color1, float strokeWidth, float dashLength, Color color2)
    {
        this(x1,y1,x2,y2,color1,strokeWidth,dashLength);
        this.color2 = color2;
        gradient = new GradientPaint(x1, y1, color1, x2, y2, color2, false);
    }
    
    
    // Mutator sets the x-coordinate of first endpoint
    public void setX1(int x1)
    {
        this.x1 = x1;
    }
    
    // Mutator sets the x-coordnate of second endpoint
    public void setX2(int x2)
    {
        this.x2 = x2;
    }
    
    // Mutator sets the y-coordinate of first endpoint
    public void setY1(int y1)
    {
        this.y1 = y1;
    }
    
    // Mutator sets the y-coordinate of second endpoint
    public void setY2(int y2)
    {
        this.y2 = y2;
    }
    
    // Mutator sets the color.
    public void setColor(Color color)
    {
        color1 = color;
    }
    
    // Mutator sets stroke
    public void setStroke(BasicStroke stroke)
    {
        this.stroke = stroke;
    }
    
    // Mutator sets strokeWidth and dashLength
    public void setDashed(float strokeWidth,float dashLength)
    {
        dash[0] = dashLength;
        stroke = new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dash, 0);
    }
    
    // Mutator sets gradient object
    public void setGradient()
    {
        gradient = new GradientPaint(x1, y1, color1, x2, y2, color2, false);
    }
    
    // Accessor returns the x-coordinate of first endpoint
    public int getX1()
    {
        return x1;
    }
    
    // Accessor returns the x-coordinate of second endpoint
    public int getX2()
    {
        return x2;
    }
    
    // Accessor returns the y-coordinate of first endpoint
    public int getY1()
    {
        return y1;
    }
    
    // Accessor returns the y-coordinate fo second endpoint
    public int getY2()
    {
        return y2;
    }
    
    // Accessor returns the color
    public Color getColor()
    {
        return color1;
    }
    
    // Accessor returns stroke
    public BasicStroke getStroke()
    {
        return stroke;
    }
    
    // Accessor returns gradient
    public GradientPaint getGradient()
    {
        return gradient;
    }
    
    // This method provides a template of the draw method for a subclass.
    public abstract void draw(Graphics g);
}
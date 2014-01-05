import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/** This class inherits from MyShape and draws a line
  * 
  * @author Shirley Du
  * @version May.30/12
  */
public class MyLine extends MyShape
{
    
    // Constructor calling superclass MyShape to initiate x1,y1,x2,y2,and color
    public MyLine()
    {
        super();
    }
    
    // Constructor with input values x1,y1,x2,y2, color, and strokeWidth.
    // Calls MyShape to initiate these values.
    public MyLine( int x1, int y1, int x2, int y2, Color color, float strokeWidth)
    {
        super(x1,y1,x2,y2,color,strokeWidth);
    }
    
    // Constructor with extra input value dashLength
    public MyLine( int x1, int y1, int x2, int y2, Color color, float strokeWidth, float dashLength)
    {
        super(x1,y1,x2,y2,color,strokeWidth,dashLength);
    }
    
    // Constructor with extra input value color2 instead of dashLength
    public MyLine( int x1, int y1, int x2, int y2, Color color, float strokeWidth, Color color2)
    {
        super(x1,y1,x2,y2,color,strokeWidth,color2);
    }
    
    // Constructor with both extra input values color2 and dashLength
    public MyLine( int x1, int y1, int x2, int y2, Color color, float strokeWidth, float dashLength, Color color2)
    {
        super(x1,y1,x2,y2,color,strokeWidth,dashLength, color2);
    }
    
    // This method actually draws the line
    public void draw( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        if (getGradient() == null)
            g2d.setColor(getColor());
        else
            g2d.setPaint(getGradient());
        g2d.setStroke(getStroke());
        g2d.drawLine( getX1(), getY1(), getX2(), getY2() );
    } 
} 
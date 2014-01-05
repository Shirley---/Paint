import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/** This class inherits from MyBoundedShape and draws a rectangle
  * 
  * @author Shirley Du
  * @version May.30/12
  */
public class MyRectangle extends MyBoundedShape
{
    // Constructor calling direct superclass MyBoundedShape to initiate x1,y1,x2,y2,color, and filled
    public MyRectangle()
    {
        super();
    }
    
    // Constructor with input values x1,y1,x2,y2,color, and filled.
    // Calls MyBoundedShape to initiate these values.
    public MyRectangle(int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth)
    {
        super(x1,  y1, x2, y2, color, filled, strokeWidth);
    } 
    
    // Constructor with extra input value dashLength
    public MyRectangle(int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth, float dashLength)
    {
        super(x1,  y1, x2, y2, color, filled, strokeWidth, dashLength);
    }
    
    // Constructor with extra input value color2 instead of dashLength
    public MyRectangle(int x1,int y1,int x2,int y2,Color color, boolean filled,  float strokeWidth,Color color2)
    {
        super(x1,  y1, x2, y2, color, filled, strokeWidth, color2);
    }
    
    // Constructor with both extra input values dashLength and color2
    public MyRectangle(int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth, float dashLength, Color color2)
    {
        super(x1,  y1, x2, y2, color, filled, strokeWidth, dashLength, color2);
    }
    
    // This method actually draws the rectangle
    public void draw( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        g.setColor( getColor() );
        g2d.setStroke(getStroke());
        g2d.setPaint(getGradient());
        if (getFilled())
            g.fillRect( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() );
        else
            g.drawRect( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() );
    } 
}


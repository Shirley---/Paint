import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/** This class inherits from MyBoundedShape and draws an oval
  * 
  * @author Shirley Du
  * @version May.30/12
  */
public class MyOval extends MyBoundedShape
{
    // Constructor calling direct superclass MyBoundedShape to initiate x1,y1,x2,y2,color, and filled.
    // Calls MyBoundedShape to initiate these values.
    public MyOval()
    {
        super();
    }
    
    // Constructor with input values x1,y1,x2,y2,color, filled, and strokeWidth
    public MyOval( int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth)
    {
        super(x1,  y1, x2, y2, color, filled, strokeWidth);
    } 
    
    // Constructor with extra input value dashLength
    public MyOval(int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth, float dashLength)
    {
        super(x1,  y1, x2, y2, color, filled, strokeWidth, dashLength);
    }
    
    // Constructor with extra input value color2 instead of dashLength
    public MyOval(int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth,Color color2)
    {
        super(x1,  y1, x2, y2, color, filled, strokeWidth,color2);
    }
    
    // Constructor with both extra input values dashLength and color2
    public MyOval(int x1,int y1,int x2,int y2,Color color, boolean filled, float strokeWidth, float dashLength, Color color2)
    {
        super(x1,  y1, x2, y2, color, filled, strokeWidth, dashLength, color2);
    }
    
    // This method actually draws the oval
    public void draw( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        g.setColor( getColor() );
        g2d.setStroke(getStroke());
        g2d.setPaint(getGradient());
        if (getFilled())
            g.fillOval( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight());
        else
            g.drawOval( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() );
    } 
} 


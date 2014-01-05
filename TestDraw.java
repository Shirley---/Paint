import javax.swing.JFrame;

/** Test application to display a DrawPanel.
  * 
  * @author Shirley Du
  * @version May.30/12
  */
public class TestDraw
{
    public static void main( String args[] )
    {
        InternalFrames application = new InternalFrames();
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        application.setSize( 1000,700 );
        application.setVisible( true );
    } 
}
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane; 
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.DefaultDesktopManager;
import java.beans.PropertyVetoException; 
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.KeyEvent;    
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;

public class InternalFrames extends JFrame
{
    // instance variables
    private JDesktopPane desktop = new JDesktopPane(); 
    private DrawFrame frame = new DrawFrame();
    private int framesCount;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem about, prefs, exit, newFrame;
    
    // Constructor
    public InternalFrames()
    {
        super("Super Paint");
        
        //Create the menu bar.
        menuBar = new JMenuBar();
        
        //Build the first menu.
        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        
        menuBar.add(file);
        
        //a group of JMenuItems
        about = new JMenuItem("About", KeyEvent.VK_A);       
        prefs = new JMenuItem("Prefs", KeyEvent.VK_P);
        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.ALT_MASK));
        newFrame = new JMenuItem("New");
        newFrame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        
        // assign action listeners
        MenuHandler menuHandler = new MenuHandler();
        menuHandler.setFrame(this);
        newFrame.addActionListener(menuHandler);
        prefs.addActionListener(menuHandler);
        about.addActionListener(menuHandler);
        exit.addActionListener(menuHandler);
        
        // add menu items to menu
        file.add(newFrame);
        file.add(prefs);
        file.add(about);
        file.add(exit);
        
        // put the menuBar on top of window
        setJMenuBar(menuBar); 
        
        desktop = new JDesktopPane();
        framesCount = 0;
        
        add(desktop, BorderLayout.CENTER);
        
        desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        desktop.setDesktopManager(new DefaultDesktopManager());  
    }
    
    // this method creates an internal frame
    private void makeInternalFrame()
    {  
        frame = new DrawFrame();
        // utilize frame count
        frame.setTitle("Frame # " + (framesCount + 1)); 
        // registers an internal frame event handler
        frame.addInternalFrameListener(new FramesHandler());
        frame.setSize(800,600);
        frame.setLocation(framesCount*10,framesCount*10);
        frame.setVisible(true);
        desktop.add(frame);
        
        try
        {
            frame.setSelected(true);
        }
        catch (PropertyVetoException e)
        {}
        
        framesCount++;
    }
    
    // inner class that takes control over framesCount
    private class FramesHandler extends InternalFrameAdapter
    {
        public void internalFrameClosed(InternalFrameEvent e)
        {
            framesCount--;
        }
    }
    
    // private inner class for ActionListener event handling
    private class MenuHandler implements ActionListener
    {
        private JFrame thisFrame;
        // respond to menu events
        public void actionPerformed(ActionEvent event)
        {
            if (event.getSource() == about)
                JOptionPane.showMessageDialog(getFrame(), "Super Paint\nAuthor: Shirley Du\n", "About", JOptionPane.INFORMATION_MESSAGE);
            
            else if (event.getSource() == exit)
                System.exit(0);
            
            else if (event.getSource() == newFrame)
            {
                JInternalFrame frames[] = desktop.getAllFrames();
                makeInternalFrame();
            }
            
            else
            {
                PrefsWindow prefsWindow = new PrefsWindow();
                prefsWindow.setSize( 200,300 );
                prefsWindow.setVisible( true );
                prefsWindow.setLocationRelativeTo(getFrame());
            }
        }
        
        // Mutator method sets thisFrame
        public void setFrame(JFrame frame)
        {
            thisFrame = frame;
        }
        
        // Accessor method returns thisFrame
        public JFrame getFrame()
        {
            return thisFrame;
        }
    }
}
package database;
import java.awt.event.WindowEvent;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import javax.swing.*;

public class DATABASE {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        JFrame frame = new JFrame("PILL MAP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        component comp = new component();
        frame.setUndecorated(true);
        frame.pack();
        frame.add(comp);
        frame.setSize(1024,768);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.addMouseListener(comp);
        frame.addMouseWheelListener(comp);
        frame.addMouseMotionListener(comp);
        while(true){
            if(comp.exit){
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
            comp.refresh();
            sleep(2);
        }
    }
}
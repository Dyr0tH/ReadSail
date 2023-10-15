import javax.swing.*;
import java.awt.*;

public class DBManager {
    public DBManager(){
        JFrame frame = new JFrame("Database manager");
        frame.setSize(450, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Enter a query");

        JTextField queryField = new JTextField();

        JButton execBtn = new JButton("Execute query ?");

        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(queryField);
        mainPanel.add(execBtn, BorderLayout.SOUTH);

        frame.add(mainPanel);

        frame.setVisible(true);
    }
}

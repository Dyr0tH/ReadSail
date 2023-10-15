import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Popup {

    public void PopupWindow(String popupMsg, String popupTitle, Boolean terminateChoice) {
        JFrame frame = new JFrame(popupTitle);
        frame.setSize(300, 150);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel(new GridLayout(0, 2, 2, 2));

        JLabel popupLabel = new JLabel(popupMsg);
        popupLabel.setHorizontalAlignment(0);

        JButton  yesBtn = new JButton("Exit");
        yesBtn.setSize(20, 10);

        JButton noBtn = new JButton("Cancel");
        noBtn.setSize(20, 10);

        panel.add(btnPanel, BorderLayout.SOUTH);
        panel.add(popupLabel, BorderLayout.CENTER);

        // to show buttons or not
        if(terminateChoice){
            btnPanel.add(yesBtn);
            btnPanel.add(noBtn);
        }

        frame.add(panel);
        centerFrameOnScreen(frame);
        frame.setVisible(true);

        yesBtn.addActionListener(e -> System.exit(0));
        noBtn.addActionListener(e -> frame.dispose());
    }

    public static void conLoad(int interval){
        JFrame frame = new JFrame("Connecting to Database...");
        frame.setSize(300, 100);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Connecting...", JLabel.CENTER);

        panel.add(label, BorderLayout.CENTER);
        frame.add(panel);
        centerFrameOnScreen(frame);
        frame.setVisible(true);

        Timer timer = new Timer(interval*60, new ActionListener() {
            int progress = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (progress <= interval && !Library.connectionStatus){
                    progress++;
                }else{
                    ((Timer) e.getSource()).stop();
                    frame.dispose();
                }
            }
        });
        timer.start();
    }

    // method to use to open the window in the center of the screen
    private static void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        frame.setLocation(x, y);
    }
}

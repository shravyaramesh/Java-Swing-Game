import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainFrame extends FileOperation implements ActionListener {
    private MyButton newGameBtn;
    private MyButton exitBtn;
    private MyButton scoreBtn;
    private JLabel welcomeLabel;

    JLayeredPane layeredPane;
    JPanel backgroundPanel;

    MainFrame(){
        this.setTitle("Welcome to Hens and Eggs Game");
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - Constant.WINDOWWIDTH) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - Constant.WINDOWHEIGHT) / 2);

        layeredPane = new JLayeredPane();
        backgroundPanel = new BackgroundPanel(new ImageIcon("./data/background.jpeg").getImage());
        backgroundPanel.setBounds(0, 0, Constant.WINDOWWIDTH,Constant.WINDOWHEIGHT);

        this.setLayout(null);

        welcomeLabel = new JLabel("Welcome " + this.getUserName() + " !");
        Font labelFont = new Font("Chalkboard SE", Font.BOLD, 40);
        welcomeLabel.setFont(labelFont);
        welcomeLabel.setForeground(new Color(255,165,0));
        welcomeLabel.setBounds(Constant.XOFFSET - 100, Constant.YOFFSET - 50, 400, 55);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        newGameBtn = new MyButton("New Game");
        newGameBtn.setBounds(Constant.XOFFSET, Constant.YOFFSET + 50, 200, Constant.BUTTONHEIGHT);

        scoreBtn = new MyButton("Highest Score");
        scoreBtn.setBounds(Constant.XOFFSET, Constant.YOFFSET + 120, 200, Constant.BUTTONHEIGHT);

        exitBtn = new MyButton("Exit");
        exitBtn.setBounds(Constant.XOFFSET, Constant.YOFFSET + 190, 200, Constant.BUTTONHEIGHT);

        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(welcomeLabel, JLayeredPane.MODAL_LAYER);
        layeredPane.add(newGameBtn, JLayeredPane.MODAL_LAYER);
        layeredPane.add(scoreBtn, JLayeredPane.MODAL_LAYER);
        layeredPane.add(exitBtn, JLayeredPane.MODAL_LAYER);

        this.setLayeredPane(layeredPane);

        newGameBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        scoreBtn.addActionListener(this);

        this.setSize(Constant.WINDOWWIDTH, Constant.WINDOWHEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void updateHighestInformation() {

    }

    @Override
    public void updateUserName(String userName) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameBtn){
            this.dispose();
            new GameFrame();
        }else if (e.getSource() == exitBtn){
            this.setVisible(false);
            this.dispose();
            System.exit(0);
        }else if (e.getSource() == scoreBtn){
            ImageIcon icon = new ImageIcon("./data/egg.png");
            Image smallEgg = icon.getImage().getScaledInstance(50,50, Image.SCALE_FAST);
            ImageIcon eggIcon = new ImageIcon(smallEgg);

            JOptionPane.showMessageDialog(this,"Highest Score Name is: " + this.getHighScoreUserName() + "\n" + "Highest Score is: " + this.getHighScore(), "Show Highest Score", JOptionPane.PLAIN_MESSAGE, eggIcon);
        }
    }
}

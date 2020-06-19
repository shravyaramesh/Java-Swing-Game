import static javax.swing.JOptionPane.PLAIN_MESSAGE;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.tools.Tool;

public class GameFrame extends FileOperation implements ActionListener{


  private final int framewidth = Toolkit.getDefaultToolkit().getScreenSize().width - 50;
  private final int frameHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 20;
  private MyButton returnButton;
  Dimension frameSize = new Dimension(framewidth,frameHeight);
  GamePanel gamePanel;
  Timer frameTimer;

  boolean flag = true;
  private MyButton pauseButton;


  public GameFrame(){

    this.setBounds(0,0, frameSize.width, frameSize.height);
    this.addButton();

    BoardStatus boardStatus = new BoardStatus();
    gamePanel = new GamePanel(boardStatus);
    gamePanel.setBounds(0,0, frameSize.width, frameSize.height);
    gamePanel.paintBackground();
    this.getContentPane().add(gamePanel);

    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setVisible(true);

    frameTimer = new Timer((int)(boardStatus.time * 1000),this);
    frameTimer.start();

  }

  @Override
  public void updateHighestInformation() {
    String namePath = "./data/highestScore.txt";
    try{
      File nameFile = new File(namePath);
      FileWriter nameWriter = new FileWriter(nameFile, true);
      BufferedWriter bw = new BufferedWriter(nameWriter);
      bw.newLine();
      bw.write(this.getUserName() + "," + this.gamePanel.boardStatus.player.score);
      bw.flush();
      bw.close();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void updateUserName(String userName) {

  }

  public void addButton(){
    returnButton = new MyButton("Return");
    returnButton.setBounds(frameSize.width - 300, frameSize.height - 100, 250, 50);
    returnButton.setFont(new Font("Courier New", Font.BOLD, 30));
    returnButton.addActionListener(this);
    this.add(returnButton);

    pauseButton = new MyButton("Pause");
    pauseButton.setBounds(frameSize.width - 600, frameSize.height - 100, 250, 50);
    pauseButton.setFont(new Font("Courier New", Font.BOLD, 30));
    pauseButton.addActionListener(this);
    this.add(pauseButton);
  }

  @Override
  public void actionPerformed(ActionEvent e){
    ImageIcon icon = new ImageIcon("./data/egg.png");
    Image smallEgg = icon.getImage().getScaledInstance(50,50, Image.SCALE_FAST);
    ImageIcon eggIcon = new ImageIcon(smallEgg);

    ImageIcon rottenIcon = new ImageIcon("./data/rottenegg.png");
    Image smallRottenEgg = rottenIcon.getImage().getScaledInstance(50,50, Image.SCALE_FAST);
    ImageIcon rottenEgg = new ImageIcon(smallRottenEgg);



    Object src = e.getSource();
    if (src == returnButton){

      frameTimer.stop();


      Integer input = null;
      if (this.gamePanel.boardStatus.player.score >= this.getHighScore()) {
        updateHighestInformation();
        input = JOptionPane
                .showOptionDialog(null, "Congratulation, you got the highest score!" + "\n"+"Your score is " + this.gamePanel.boardStatus.player.score + ".", "Message", JOptionPane.DEFAULT_OPTION, PLAIN_MESSAGE, eggIcon, null, null);
      }else {
        input = JOptionPane
                .showOptionDialog(null, "You exited the game. Your score is " + this.gamePanel.boardStatus.player.score + ".", "Message", JOptionPane.DEFAULT_OPTION, PLAIN_MESSAGE, rottenEgg, null, null);
      }
      if (input == JOptionPane.OK_OPTION) {
        this.dispose();
        new MainFrame();
      }
    }
    if(src == pauseButton){
      if (flag){
        frameTimer.stop();
        this.gamePanel.pauseButton.setToolTipText("Restart");
        this.pauseButton.setText("Restart");
        flag = !flag;
      }else {
        frameTimer.start();
        this.gamePanel.pauseButton.setToolTipText("Pause");
        this.pauseButton.setText("Pause");
        this.gamePanel.addNotify();
        flag = !flag;
      }

    }
    if(src == frameTimer){
      updateFrame();
    }

  }

  void updateFrame(){
    ImageIcon icon = new ImageIcon("./data/egg.png");
    Image smallEgg = icon.getImage().getScaledInstance(50,50, Image.SCALE_FAST);
    ImageIcon eggIcon = new ImageIcon(smallEgg);

    ImageIcon rottenIcon = new ImageIcon("./data/rottenegg.png");
    Image smallRottenEgg = rottenIcon.getImage().getScaledInstance(50,50, Image.SCALE_FAST);
    ImageIcon rottenEgg = new ImageIcon(smallRottenEgg);

    gamePanel.updatePanel();
    if(gamePanel.boardStatus.player.livesRemaining == 0){
      SoundEffect.GAMEOVER.play();
      frameTimer.stop();
//      System.out.println("Game panel timer stop.");

      Integer input = null;
      if (this.gamePanel.boardStatus.player.score >= this.getHighScore()) {
        updateHighestInformation();
        input = JOptionPane
                .showOptionDialog(null, "Congratulation, you got the highest score!" + "\n"+"Your score is " + this.gamePanel.boardStatus.player.score + ".", "Message", JOptionPane.DEFAULT_OPTION, PLAIN_MESSAGE, eggIcon, null, null);
      }else {
        input = JOptionPane
                .showOptionDialog(null, "You failed. Your score is " + this.gamePanel.boardStatus.player.score + ".", "Message", JOptionPane.DEFAULT_OPTION, PLAIN_MESSAGE, rottenEgg, null, null);
      }
      if(input == JOptionPane.OK_OPTION){
        this.dispose();
        new MainFrame();
      }
    } else {
      frameTimer.start();
    }

  }


//    public static void main(String[] args) {
//      new GameFrame();
//    }

  }


import static javax.swing.JOptionPane.PLAIN_MESSAGE;

import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Main extends FileOperation{

    public Main(String userName){
        updateUserName(userName);

    }
    public static void main(String[] args){
        String userName = null;
        try{
            ImageIcon icon = new ImageIcon("./data/egg.png");
            Image smallEgg = icon.getImage().getScaledInstance(50,50, Image.SCALE_FAST);
            ImageIcon eggIcon = new ImageIcon(smallEgg);

            userName = (String) JOptionPane.showInputDialog(null,"Please enter your name!","Welcome to Hens and Eggs", PLAIN_MESSAGE, eggIcon,null, null);

            while(userName == null || userName.equals("")) {
            	if (userName.equals("")) {
            		JOptionPane.showConfirmDialog(null,"Must enter an user name :)","Warning!", PLAIN_MESSAGE, JOptionPane.OK_OPTION, eggIcon);
            		userName = (String) JOptionPane.showInputDialog(null,"Please enter your name!","Welcome to Hens and Eggs", PLAIN_MESSAGE, eggIcon,null, null);
            	}
            }
            Main curMain = new Main(userName);
            new MainFrame();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void updateHighestInformation() {

    }

    @Override
    public void updateUserName(String userName) {
        String namePath = "./data/userName.txt";
        try{
            File nameFile = new File(namePath);
            FileWriter nameWriter = new FileWriter(nameFile, true);
            BufferedWriter bw = new BufferedWriter(nameWriter);
            bw.newLine();
            bw.write(userName);
            bw.flush();
            bw.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

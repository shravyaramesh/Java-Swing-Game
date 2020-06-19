
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FileOperation extends JFrame {
    private String userName;
    private String highScoreUserName;
    private int highScore;
    private BufferedReader nameBufferReader;
    private BufferedReader scoreBufferReader;


    public FileOperation(){
        try{
            String userNamePath = "./data/userName.txt";
            File userFile = new File(userNamePath);
            //read data from file
            nameBufferReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(userFile))
            );

            String scorePath = "./data/highestScore.txt";
            File scoreFile = new File(scorePath);
            //read data from file
            scoreBufferReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(scoreFile))
            );
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        userName = getUserName(nameBufferReader);
        String highestInfor = getScore(scoreBufferReader);
        if (highestInfor == ""){
            highScoreUserName = null;
            highScore = 0;
        }else {
            String[] scoreArr = highestInfor.split(",");
            highScoreUserName = scoreArr[0];
            highScore = Integer.valueOf(scoreArr[1]);
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getHighScoreUserName() {
        return highScoreUserName;
    }

    public int getHighScore() {
        return highScore;
    }

    public String getUserName(BufferedReader in) {
        List<String> input = new ArrayList<>();
        try{
            String s;
            while((s = in.readLine()) != null) {
                if (s.equals("")) {
                    //Step over the line with all whitespace
                    continue;
                }
                //Omit the leading and trailing whitespace of s
                s = s.trim();
                input.add(s);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (input.size() == 0){
            return "";
        }
        return input.get(input.size() - 1);
    }

    public String getScore(BufferedReader in) {
        List<String> input = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            Thread mtr = new Thread(new MultiThreadRead(input, in));
            mtr.start();
        }
        if (input.size() < 1){
            return "";
        }
        return input.get(input.size() - 1);
    }

    public abstract void updateHighestInformation();

    public abstract void updateUserName(String userName);

}

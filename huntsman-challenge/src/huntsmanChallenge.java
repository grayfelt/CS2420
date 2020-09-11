import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
public class huntsmanChallenge {
    huntsmanChallenge(){

    }
    private void pressAnyKeyToContinue()
    {
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        JFrame jFrame = new JFrame();
        JTextArea jTextArea = new JTextArea();
        jFrame.add(jTextArea);
        jFrame.setPreferredSize(new Dimension(400, 300));
        jFrame.setVisible(true);
        jTextArea.append("The Huntsman Challenge - Grayson Felt");
        jTextArea.append("\n");
        for(int i = 0; i < 37; i++){
            jTextArea.append("-");
            TimeUnit.MILLISECONDS.sleep(50);
        }
        jTextArea.append("\n");
        jTextArea.append("This was my first year as a Huntsman Scholar.\n");
        TimeUnit.SECONDS.sleep(4);
        jFrame.add(jTextArea);
        jTextArea.append("Looking back, I can't believe how much I have changed in my short time as a student.\n");
        TimeUnit.SECONDS.sleep(4);
        jTextArea.append("\n");
        jTextArea.append("Given all of this change, I decided to create a timeline using");
        jTextArea.append("\n");
        jTextArea.append("my computer science skills I have picked up through my minor.\n");
        TimeUnit.SECONDS.sleep(6);
        jTextArea.append("In doing this, I hope to show what I have done with the Huntsman Scholar investment,");
        jTextArea.append("\n");
        jTextArea.append("and how important it is to both me and education as a whole.\n");
        TimeUnit.SECONDS.sleep(6);
        jTextArea.append("\n");
        jTextArea.append("\n");
        jTextArea.append("January 06, 2020 - First day of classes.");
        jTextArea.append("\n");
        jTextArea.append("First day as a Huntsman Scholar.");
        TimeUnit.SECONDS.sleep(3);
        jTextArea.append("\n");
        jTextArea.append("\n");
        jTextArea.append("January 15, 2020 - Second Huntsman Scholar Lab meeting.");
        jTextArea.append("\n");
        jTextArea.append("Introduced myself to the speaker Prof. Brough, told him I was interested in");
        jTextArea.append("\n");
        jTextArea.append("machine learning. Invited me to his office hours to further discuss the topic.");
        jTextArea.append("\n");
        jTextArea.append("\n");
        TimeUnit.SECONDS.sleep(3);
        jTextArea.append("January 23, 2020 - Financial Management (scholars section).");
        jTextArea.append("\n");
        jTextArea.append("A student referred me to a couple of internships, motivating me to start applying.");
        jTextArea.append("\n");
        jTextArea.append("\n");
        TimeUnit.SECONDS.sleep(3);
        jTextArea.append("February 05, 2020 - Student panel Huntsman Scholar meeting.");
        jTextArea.append("\n");
        jTextArea.append("Asked for advice from two Huntsman Scholar seniors, resulting in joining the Computational Finance");
        jTextArea.append("\n");
        jTextArea.append("Research Lab, and applying to the Center for Growth and Opportunity.");
        jTextArea.append("\n");
        jTextArea.append("\n");
        TimeUnit.SECONDS.sleep(3);
        jTextArea.append("February 20, 2020 - Computational Finance Research Lab meeting.");
        jTextArea.append("\n");
        jTextArea.append("Attended this meeting held by Prof. Brough and gained valuable insight on my future career");
        jTextArea.append("\n");
        jTextArea.append("\n");
        TimeUnit.SECONDS.sleep(3);
        jTextArea.append("February 21, 2020 - Attended a Huntsman Scholar discussion about Becoming a Learner.");
        jTextArea.append("\n");
        jTextArea.append("Reinforced my values surrounding education and how I can continue to be a learner.");
        jTextArea.append("\n");
        jTextArea.append("\n");
        TimeUnit.SECONDS.sleep(3);
        jTextArea.append("February 21, 2020 - Attended a kayaking event with my cohort.");
        jTextArea.append("\n");
        jTextArea.append("Gained new friends and fun memories");
        jTextArea.append("\n");
        jTextArea.append("\n");
        TimeUnit.SECONDS.sleep(3);
        jTextArea.append("February 24, 2020 - Met with Prof. Brough in his office.");
        jTextArea.append("\n");
        jTextArea.append("Learned more about machine learning and computational finance. Was given an internship and");
        jTextArea.append("\n");
        jTextArea.append("research opportunity for this next upcoming summer and school year. Now consider him my mentor.");
        jTextArea.append("\n");
        jTextArea.append("\n");
        TimeUnit.SECONDS.sleep(3);
        jTextArea.append("March 12, 2020 - Classes transition to online.");
        jTextArea.append("\n");
        jTextArea.append("An unforeseen event that made this semester truly unique. Nevertheless I continued to learn.");
        jTextArea.append("\n");
        jTextArea.append("\n");
        TimeUnit.SECONDS.sleep(3);
        jTextArea.append("March 23, 2020 - Wrote this program.");
        jTextArea.append("\n");
        for(int i = 0; i < 37; i++){
            jTextArea.append("-");
            TimeUnit.MILLISECONDS.sleep(50);
        }
        jTextArea.append("\n");
        jTextArea.append("Before this semester, I wasn't very involved and I was not confident about my education");
        jTextArea.append("\n");
        TimeUnit.MILLISECONDS.sleep(120);
        jTextArea.append("or the choices that led me to be where I was. Now, I know I have found the right school,");
        jTextArea.append("\n");
        TimeUnit.MILLISECONDS.sleep(120);
        jTextArea.append("the right career, and the right network. I hope to use these skills to serve my community");
        jTextArea.append("\n");
        TimeUnit.MILLISECONDS.sleep(120);
        jTextArea.append("and influence others as they have influenced me. It only took one Huntsman Scholar,");
        jTextArea.append("\n");
        TimeUnit.MILLISECONDS.sleep(120);
        jTextArea.append("one professor, one semester, to change the course of my life. I plan to be that individual,");
        jTextArea.append("\n");
        TimeUnit.MILLISECONDS.sleep(120);
        jTextArea.append("who influences future students to pursue their passions and enjoy their education.");
        jTextArea.append("\n");
        TimeUnit.MILLISECONDS.sleep(800);
        jTextArea.append("This program has had a lasting effect on my life in just one semester, and I cannot wait");
        jTextArea.append("\n");
        TimeUnit.MILLISECONDS.sleep(120);
        jTextArea.append("to experience what the next 4 semesters have in store for me.");
        jTextArea.append("\n");
        TimeUnit.MILLISECONDS.sleep(500);
        jTextArea.append("Thank you.");
        jTextArea.append("\n");
        TimeUnit.MILLISECONDS.sleep(300);
        jTextArea.append("-Grayson Felt, March 23.");

    }

}

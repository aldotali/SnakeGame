/*
 *This is the Frame for the snake game.
 * The code was writen by Aldo Tali, Student 2nd year Bilkent University.
 * Date: 30/07/2017
 * */

import javax.swing.*;
import java.awt.*;

public class SnakeGameFrame{
    private static final int DIMENSIONx = 1200;
    private static final int DIMENSIONy = 700;
    public static void main(String[] args){
        //this will be our game frame on which we will play
        JFrame snakeFrame = new JFrame("Snake Game By Aldo Tali");
        //once the X button is pressed on the window close the application
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        snakeFrame.setBackground(Color.cyan);
        snakeFrame.add(new SnakeGamePanel());
        //set the initial size of the frame
        snakeFrame.setPreferredSize(new Dimension(DIMENSIONx,DIMENSIONy) );
        //get the screen size so that we can position our frame on the center of the screen
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        snakeFrame.setLocation(screenDim.width/2-DIMENSIONx/2, (screenDim.height/2-DIMENSIONy/2) - 30);
        //don't allow the user to resize the screen 
        //(This way we will be sure that there is no tweeking or malfunctioning in the game)
        snakeFrame.pack();
        snakeFrame.setResizable(false);
        snakeFrame.setVisible(true);
    }
}
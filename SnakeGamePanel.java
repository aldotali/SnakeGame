/*
 * This is the panel where the snake game will be played.
 * This code was writen by Aldo Tali, Student 2nd year Bilkent University.
 * Date: 30/07/2017
 * */

import javax.net.ssl.ExtendedSSLSession;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeGamePanel extends JPanel implements ActionListener{

    //define the constants that we will be using along our code
    private static final int DIMENSIONx = 1200;
    private static final int DIMENSIONy = 700;
    private final int Title_IMG_SIZE_X = 400;
    private final int Title_IMG_SIZE_Y = 140;
    private final int OVERHEAD_X = 400;
    private final int OVERHEAD_y = 10;
    
    //define the variables that we will be using during our code
    private ImageIcon theTitleBar;
    private ImageIcon snakeHead;
    private ImageIcon snakeBody;
    private ImageIcon collectibles;
    private boolean gameStartFlag = true;
    private boolean rightMovement = false;
    private boolean leftMovement = false;
    private boolean upMovement = false;
    private boolean downMovement = false;
    private int snakePanelSizeX;
    private int snakePanelSizeY;
    private int snakeLength;
    private int timerDelay;
    private int movementAmount;
    private int collectibleXpos;
    private int collectibleYpos;
    private int[] snakeX ;
    private int[] snakeY ; 
    private Timer timer;
    private Random random;

    public SnakeGamePanel(){
        addKeyListener(new ButtonListener());
        //our gamePlay panel will now have a width of 1175px nad height of 500px
        snakePanelSizeX = (DIMENSIONx - 25);
        snakePanelSizeY = DIMENSIONy - (Title_IMG_SIZE_Y + OVERHEAD_y + 50);

        snakeLength = 3;
        timerDelay = 75;

        random = new Random();
        
        collectibleXpos = random.nextInt(46)*25 + 10;
        collectibleYpos = random.nextInt(19)*25 + 160;

        //25 is our snake cell icon size : 25 by 25 px
        int size = (snakePanelSizeX/25)* (snakePanelSizeY/25);
        snakeX = new int [size];
        snakeY = new int [size];

        //intitialize the images to be drawn;
        theTitleBar = new ImageIcon("snakeTitleBar.jpg");
        snakeHead = new ImageIcon("snakeHead.png");
        snakeBody = new ImageIcon("snakeBody.png");
        collectibles = new ImageIcon("collectibles.png");
        
        timer  = new Timer(timerDelay,this);
        timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void paint(Graphics page){
         
        //if the game has just started initialize the locations for our snake
        if(gameStartFlag){
            snakeX[0] = 110;
            snakeX[1] = 85;
            snakeX[2] = 60;

            snakeY[0] = 160;
            snakeY[1] = 160;
            snakeY[2] = 160;
        }
        //draw the title bar and the gameplay section accordingly. Put the image and the score for the user
        if(snakeLength%2 == 1)
        page.setColor(Color.white);
        else
        page.setColor(Color.cyan);
        page.fillRect(10,10,DIMENSIONx - 20, Title_IMG_SIZE_Y);
        page.drawImage(theTitleBar.getImage(), OVERHEAD_X, OVERHEAD_y, Title_IMG_SIZE_X, Title_IMG_SIZE_Y, null, null);
        page.setColor(Color.red);
        page.setFont(new Font("TimesRoman", Font.BOLD, 20));
        page.drawString("Score: " + (snakeLength - 3) , 1090, 140);
        page.setColor(Color.red);
        page.setFont(new Font("TimesRoman", Font.BOLD, 20));
        page.drawString("Have Fun!", 65, 75);

        //Here (in our case) for convinience we have let the gameplay width be 500;
        page.setColor(Color.black);
        page.fillRect(10,(Title_IMG_SIZE_Y + OVERHEAD_y + 10),snakePanelSizeX +3, snakePanelSizeY+5);

        //draw the images for the length of the snake
        //for the first one we always pain the snake head
        snakeHead.paintIcon(this, page, snakeX[0], snakeY[0]);
      
        for (int index = 1 ; index < snakeLength; index++){
            //for the others we paint the snake body    
            snakeBody.paintIcon(this,page,snakeX[index], snakeY[index]);
    
        }

        //check if snake head touched its body or not
        for (int i = 1 ; i < snakeLength; i++){
            if (snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]){
                rightMovement = false;
                leftMovement = false;
                upMovement = false;
                downMovement = false;

                page.setColor(Color.white);
                page.setFont(new Font("TimesRoman", Font.BOLD, 60));
                page.drawString("Game is over", snakePanelSizeX/2 - 150, snakePanelSizeY/2 + 80);

                collectibleXpos = random.nextInt(46)*25 + 10;
                collectibleYpos = random.nextInt(19)*25 + 160;
                timer.stop();
                
                page.setFont(new Font("TimesRoman", Font.BOLD, 25));
                page.drawString("Press enter to restart", snakePanelSizeX/2 - 110, snakePanelSizeY/2 + 140);
            }
        }

        //this is the collectible for the snake
        collectibles = new ImageIcon("collectibles.png");

        //check if the collectible collides with the head of the snake
        if (collectibleXpos == snakeX[0] && collectibleYpos == snakeY[0]){
            snakeLength++;
            //score++;
            collectibleXpos = random.nextInt(46)*25 + 10;
            collectibleYpos = random.nextInt(19)*25 + 160;
        }
        //paint the new collectible
        collectibles.paintIcon(this,page,collectibleXpos,collectibleYpos);

        page.dispose();

    }
    public void actionPerformed(ActionEvent event){
        timer.start();
        //the following clauses make the locations of the snakebody shift on the arra of locations 
        //this means that body2 takes the location of body 1. Body 3 takes the location of body 2 and so on.
        //to make this efficient it is convinient that we start shifting from the last body 
        if (rightMovement || downMovement)
             movementAmount = 25;
        if(leftMovement || upMovement)  
            movementAmount = -25;
        for (int shiftIndex = snakeLength-1 ; shiftIndex >= 0 ; shiftIndex--){
            if(rightMovement || leftMovement)
            //the y positioning does not have to change as such just a normal shift
            snakeY[shiftIndex + 1 ] = snakeY[shiftIndex];
            else if ((upMovement || downMovement))
            snakeX[shiftIndex + 1 ] = snakeX[shiftIndex];
            else 
            shiftIndex = -1;
        }
        for (int shiftIndex2 = snakeLength; shiftIndex2 >=0; shiftIndex2--){
            //when we move the head then we make a movement to the right
            if(shiftIndex2 == 0){
                if(rightMovement || leftMovement)
                snakeX[shiftIndex2] = snakeX[shiftIndex2] + movementAmount;
                else if ((upMovement || downMovement))
                snakeY[shiftIndex2] = snakeY[shiftIndex2] + movementAmount;
                else{
                    shiftIndex2 = -1;
                }
            }
            //otherwise just proceed with the normal shifting
            else{
                if(rightMovement || leftMovement)
                snakeX[shiftIndex2 ] = snakeX[shiftIndex2 - 1];
                else if ((upMovement || downMovement))
                snakeY[shiftIndex2 ] = snakeY[shiftIndex2 - 1];
            }
            if(rightMovement && snakeX[shiftIndex2] > 1165 )
                snakeX[shiftIndex2] = 10;
            else if (leftMovement && snakeX[shiftIndex2] < 10)
                snakeX[shiftIndex2] = 1160;
            else if (upMovement && snakeY[shiftIndex2] < 160)
                snakeY[shiftIndex2] = 635;
            else if (downMovement && snakeY[shiftIndex2] > 635)
                snakeY[shiftIndex2] = 160;
            else{}
        }
        repaint();
    }

    private class ButtonListener implements KeyListener{
        public void keyTyped(KeyEvent event){
        }
        public void keyPressed(KeyEvent event){
        
           gameStartFlag = false;
           switch (event.getKeyCode()){
                case KeyEvent.VK_UP:
                    if (!downMovement)
                        upMovement = true;
                    else{
                        upMovement = false;
                        downMovement = true;
                    }
                    leftMovement = false;
                    rightMovement = false;
                break;
                case KeyEvent.VK_DOWN:
                    if (!upMovement)
                        downMovement = true;
                    else{
                        downMovement = false;
                        upMovement = true;
                    }
                    leftMovement = false;
                    rightMovement = false;
                break;
                case KeyEvent.VK_LEFT:
                    if (!rightMovement)
                        leftMovement = true;
                    else{
                        leftMovement = false;
                        rightMovement = true;
                    }
                    upMovement = false;
                    downMovement = false;
                break;
                case KeyEvent.VK_RIGHT:
                    if (!leftMovement)
                        rightMovement = true;
                    else{
                        rightMovement = false;
                        leftMovement = true;
                    }
                    upMovement = false;
                    downMovement = false;
                break;
                case KeyEvent.VK_ENTER:
                    snakeLength = 3;
                    gameStartFlag = true;
                    timer.start();
                    rightMovement = true;
                break;
            }
            
            repaint();
     
        }
        public void keyReleased (KeyEvent event){
        }
    }
}


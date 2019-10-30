package startupv2;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JPanel implements ActionListener {
    private static ImageIcon image, bg, bomb, front;
    private int x = 20;
    private Player p;
    private Bow bow;
    private Balloon ball;
    private OneBow b1;
    private TwoBow b2;
    private TimeLine time;
    private Timer t;
    private OnePlayer p1;
    private TwoPlayer p2;
    boolean a = false;
    private int cnt;
    private ArrayList<Balloon> arr = new ArrayList<>();
    static ArrayList<Bow> ar = new ArrayList<>();
    static ArrayList<String> sc = new ArrayList<>();
    static ArrayList<Explode> ex = new ArrayList<>();
    static ArrayList<Explode1> ex1 = new ArrayList<>();
    static ArrayList<Explode2> ex2 = new ArrayList<>();
    static ArrayList<OneBow> ob = new ArrayList<>();
    static ArrayList<TwoBow> tb = new ArrayList<>();
    static ArrayList<String> sc1 = new ArrayList<>();
    static ArrayList<String> sc2 = new ArrayList<>();
    private ArrayList<OneBalloon> ball1 = new ArrayList<>();
    private ArrayList<TwoBalloon> ball2 = new ArrayList<>();
    int score = 0;
    int score1 = 0;
    int score2 = 0;
    private boolean MultiKey, keyBow1, keyBow2;
    private boolean pause = true;
    private boolean exit = false;
    private boolean pause1 = false;
    private boolean pause2 = false;
    private boolean gameover1 = false;
    private boolean gameover2 = false;
    private boolean select = false;
    private boolean pauseall = false;
    private int state = 0;
    private int loop1 = 0;
    
    public Game(){
        bg = new ImageIcon(this.getClass().getResource("/Image/background.png"));
        image = new ImageIcon(this.getClass().getResource("/Image/cartoon.png"));
        front = new ImageIcon(this.getClass().getResource("/Image/front.png"));
        p = new Player();
        bow = new Bow(p);
        ball = new Balloon(bow, p ,this);
        p1 = new OnePlayer();
        p2 = new TwoPlayer();
        b1 = new OneBow(p1);
        b2 = new TwoBow(p2);
        time = new TimeLine();
        arr.add(new Balloon(bow, p,this));
        ball1.add(new OneBalloon(b1, p1,this));
        ball2.add(new TwoBalloon(b2, p2,this));
        sc.add("0");
        sc1.add("0");
        sc2.add("0");
        t = new Timer(10, this);
        t.start();
        setFocusable(true);
        addKeyListener(new KeyAdapter()
        {
             
            @Override                
            public void keyReleased(KeyEvent e) {
                p.keyReleased(e);
                p1.keyReleased(e);
                p2.keyReleased(e);
                MultiKey = false;
                keyBow1 = false;
                keyBow2 = false;
                shoot();
            }
            @Override
            public void keyPressed(KeyEvent e) {
                p.keyPressed(e);
                p1.keyPressed(e);
                p2.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_C && state == 4) {
                    keyBow1 = true;
                    shoot();
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE && state == 4) {
                    keyBow2 = true;
                    shoot();
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE && state == 1) {
                    MultiKey = true;
                    shoot();
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                if(state == 0 && exit == false && !select) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 350 && x < 650 && y > 350 && y < 410) select = true;
                    if(x > 350 && x < 650 && y > 450 && y < 510) exit = true;
                }
                else if(state == 0 && exit == true && !select) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 370 && x < 490 && y > 380 && y < 425) {
                        arr.clear();
                        ar.clear();
                        System.exit(ABORT);
                    }
                    else if(x > 520 && x < 640 && y > 380 && y < 425) exit = false;
                }
                else if(select && state == 0 && !exit) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 350 && x < 650 && y > 210 && y < 270) state = 1;
                    else if(x > 350 && x < 650 && y > 320 && y < 380) state = 4;
                    else if(x > 350 && x < 650 && y > 430 && y < 490) select = false;
                    
                }
                else if(state == 1 && pause == true) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 830 && x < 980 && y > 610 && y < 655) pause = false;
                }
                else if(state == 1 && pause == false) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 370 && x < 490 && y > 380 && y < 425) {
                        state = 2;
                        select = false;
                    }
                    else if(x > 520 && x < 640 && y > 380 && y < 425) pause = true;
                }
                else if(state == 2) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 440 && x < 560 && y > 380 && y < 425) {
                        state = 0;
                        pause = true;
                        arr.clear();
                        ar.clear();
                        score = 0;
                        sc.clear();
                        sc.add("0");
                    }
                }
                else if(state == 3) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 440 && x < 560 && y > 480 && y < 525) {
                        state = 0;
                        pause = true;
                        arr.clear();
                        ar.clear();
                        score = 0;
                        sc.clear();
                        sc.add("0");
                        select = false;
                    }
                }
                else if(state == 4 && pause1 && pause2 && !pauseall) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 550 && x < 670 && y > 415 && y < 460) {
                        ob.clear(); tb.clear();
                        ball1.clear(); ball2.clear();
                        score1 = score2 = 0;
                        sc1.clear(); sc2.clear();
                        sc1.add("0"); sc2.add("0");
                        pause1 = pause2 = gameover1 = gameover2 = false;
                        time.startAgain();
                        time.startTimeLine();
                        loop1 = 0;
                    }
                    else if(x > 340 && x < 460 && y > 415 && y < 460) {
                        ob.clear(); tb.clear();
                        ball1.clear(); ball2.clear();
                        score1 = score2 = 0;
                        sc1.clear(); sc2.clear();
                        sc1.add("0"); sc2.add("0");
                        pause1 = pause2 = gameover1 = gameover2 = false;
                        time.startAgain();
                        time.startTimeLine();
                        loop1 = 0;
                        select = false;
                        state = 0;
                    }
                }
                else if(state == 4 && !pauseall && !pause1 && !pause2 && !gameover1 && !gameover2) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 484 && x < 529 && y > 630 && y < 675) pauseall = true;
                }
                else if(state == 4 && !pauseall && pause1 && !pause2 && gameover1) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 484 && x < 529 && y > 630 && y < 675) pauseall = true;
                }
                else if(state == 4 && !pauseall && !pause1 && pause2 && gameover2) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 484 && x < 529 && y > 630 && y < 675) pauseall = true;
                }
                else if(state == 4 && pauseall && pause1 && pause2 && !gameover1 && !gameover2) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 484 && x < 529 && y > 630 && y < 675) {
                        pauseall = false;
                        pause1 = pause2 = false;
                        time.startTimeLine();
                    }
                }
                else if(state == 4 && pauseall && pause1 && pause2 && gameover1) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 484 && x < 529 && y > 630 && y < 675) {
                        pauseall = false;
                        pause2 = false;
                        time.startTimeLine();
                    }
                }
                else if(state == 4 && pauseall && pause1 && pause2 && gameover2) {
                    int x = e.getX();
                    int y = e.getY();
                    if(x > 484 && x < 529 && y > 630 && y < 675) {
                        pauseall = false;
                        pause1 = false;
                        time.startTimeLine();
                    }
                }
            }
        });
        
    }
    
    //เอาค่าทุกตัวของคลาส
    public Player getP() {
        return p;
    }
    
    public Bow getB() {
        return bow;
    }
    
    //วาดการ์ตูนกับพื้นหลัง
    public void paint(Graphics g){ 
        super.paintComponent(g);
        if(state == 1) {
            Graphics2D g2d = (Graphics2D) g;
            g.drawImage(bg.getImage(),0 , 0, 1000, 700, this);
            g.drawImage(image.getImage(), x , p.getY(), 100, 80, this);
            g.setFont(new Font("Impact", Font.PLAIN, 30));
            g.setColor(Color.WHITE);
            g.drawString("SCORE = " + score ,840,50);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(830, 610, 150, 45);    // pause button
            g.setFont(new Font("Impact", Font.PLAIN, 30));
            g.setColor(Color.BLACK);
            g.drawString(" PAUSE ", 862, 645);
            try{ for(Balloon i : arr) i.paint(g);
                } catch ( Exception e){
            }
            try{ for(Bow i : ar) i.paint(g);
                } catch ( Exception e){
            }
            try{ for(Explode i : ex) i.paint(g);
                } catch ( Exception e){
            }
            if(!pause) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 120));
                g2.fillRect(0, 0, 1000, 700);
                g2.setStroke(new BasicStroke(1f));
                g2.setColor(Color.WHITE);
                g2.drawRect(0, 0, 1000, 700);
                g2.dispose();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(330, 250, 350, 200);
                g.setFont(new Font("Impact", Font.PLAIN, 25));
                g.setColor(Color.BLACK);
                g.drawString(" Are you sure you want to ", 375, 290);
                g.drawString(" mainmenu or replay? ", 390, 340);
                g2d.setColor(Color.RED);   // mainmenu button
                g2d.fillRect(370, 380, 120, 45);
                g.setFont(new Font("Impact", Font.PLAIN, 25));
                g.setColor(Color.WHITE);
                g.drawString("Mainmenu", 376, 412);
                g2d.setColor(Color.green);   // replay button
                g2d.fillRect(520, 380, 120, 45);
                g.setFont(new Font("Impact", Font.PLAIN, 25));
                g.setColor(Color.WHITE);
                g.drawString("Replay", 545, 412);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(332, 252, 345, 195);
                
            }
        }
        else if(state == 0) {
            g.drawImage(bg.getImage(),0 , 0, 1000, 700, this);
            g.drawImage(front.getImage(), 460, 150, 80, 100, this);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);   // startgame button
            g2d.fillRect(350, 350, 300, 60);
            g.setFont(new Font("Impact", Font.PLAIN, 35));
            g.setColor(Color.BLACK);
            g.drawString(" START GAME ", 412, 395);
            g.setFont(new Font("Euclid Extra", Font.PLAIN, 35));
            g.setColor(Color.WHITE);
            g.drawString(" SUPER SHOOTING GAME ", 275, 305);
            g.setFont(new Font("Menlo", Font.PLAIN, 15));
            g.setColor(Color.WHITE);
            g.drawString("Copyright © 2017 Jarig Silpapinun 5933609023 Chulalongkorn University. All rights reserved", 180, 660);
            g2d.setColor(Color.RED);
            g2d.fillRect(350, 450, 300, 60);
            g.setFont(new Font("Impact", Font.PLAIN, 35));
            g.setColor(Color.WHITE);
            g.drawString(" EXIT ", 465, 495);
            
            if(select) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fillRect(0, 0, 1000, 700);
                g2.setStroke(new BasicStroke(1f));
                g2.setColor(Color.WHITE);
                g2.drawRect(0, 0, 1000, 700);
                g2.dispose();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(330, 110, 340, 450);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(332, 112, 335, 445);
                g.setFont(new Font("Impact", Font.PLAIN, 30));
                g.setColor(Color.BLACK);
                g.drawString("Choose your play mode.", 355, 160);
                g2d.setColor(Color.CYAN);    // sigle player button
                g2d.fillRect(350, 210, 300, 60);
                g.setFont(new Font("Impact", Font.PLAIN, 35));
                g.setColor(Color.WHITE);
                g.drawString("Single Player", 405, 255);
                g2d.setColor(Color.CYAN);
                g2d.fillRect(350, 320, 300, 60);    // double player button
                g.setFont(new Font("Impact", Font.PLAIN, 35));
                g.setColor(Color.WHITE);
                g.drawString("Double Player", 405, 365);
                g2d.setColor(Color.RED);
                g2d.fillRect(350, 430, 300, 60);    // cancel button
                g.setFont(new Font("Impact", Font.PLAIN, 35));
                g.setColor(Color.WHITE);
                g.drawString("Cancel", 450, 475);
                
            }
            
            else if(exit) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fillRect(0, 0, 1000, 700);
                g2.setStroke(new BasicStroke(1f));
                g2.setColor(Color.WHITE);
                g2.drawRect(0, 0, 1000, 700);
                g2.dispose();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(330, 250, 350, 200);
                g.setFont(new Font("Impact", Font.PLAIN, 25));
                g.setColor(Color.BLACK);
                g.drawString(" Are you sure you want to ", 370, 290);
                g.drawString(" exit Super Shooting Game? ", 360, 335);
                g2d.setColor(Color.GREEN);
                g2d.fillRect(370, 380, 120, 45);
                g.setFont(new Font("Impact", Font.PLAIN, 25));
                g.setColor(Color.WHITE);
                g.drawString("YES", 413, 412);
                g2d.setColor(Color.RED);
                g2d.fillRect(520, 380, 120, 45);
                g.setFont(new Font("Impact", Font.PLAIN, 25));
                g.setColor(Color.WHITE);
                g.drawString("NO", 565, 412);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(332, 252, 345, 195);
            }
        }
        else if(state == 2) {
            Graphics2D g2d = (Graphics2D) g;
            g.drawImage(bg.getImage(),0 , 0, 1000, 700, this);
            g.setFont(new Font("Impact", Font.PLAIN, 55));
            g.setColor(Color.WHITE);
            g.drawString("Your high score is " + sc.get(sc.size()-1), 280, 290);
            g2d.setColor(Color.green);
            g2d.fillRect(440, 380, 120, 45);
            g.setFont(new Font("Impact", Font.PLAIN, 25));
            g.setColor(Color.WHITE);
            g.drawString("OK", 485, 413);
        }
        else if(state == 3) {
            Graphics2D g2d = (Graphics2D) g;
            g.drawImage(bg.getImage(),0 , 0, 1000, 700, this);
            g.setFont(new Font("Impact", Font.PLAIN, 85));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 315, 240);
            g.setFont(new Font("Impact", Font.PLAIN, 55));
            g.setColor(Color.WHITE);
            g.drawString("Your high score is " + sc.get(sc.size()-1), 280, 380);
            g2d.setColor(Color.green);
            g2d.fillRect(440, 480, 120, 45);
            g.setFont(new Font("Impact", Font.PLAIN, 25));
            g.setColor(Color.WHITE);
            g.drawString("OK", 485, 513);
        }
        else if(state == 4) {
            Graphics2D g2d = (Graphics2D) g;
            g.drawImage(bg.getImage(),0 , 0, 1000, 700, this);
            g.drawImage(front.getImage(), p1.getX() , p1.getY(), 60, 85, this);
            g.drawImage(front.getImage(), p2.getX() , p2.getY(), 60, 85, this);
            try{ for(OneBow i : ob) i.paint(g);
                } catch ( Exception e){
            }
            try{ for(TwoBow i : tb) i.paint(g);
                } catch ( Exception e){
            }
            try{ for(OneBalloon i : ball1) i.paint(g);
                } catch ( Exception e){
            }
            try{ for(Explode1 i : ex1) i.paint(g);
                } catch ( Exception e){
            }
            try{ for(Explode2 i : ex2) i.paint(g);
                } catch ( Exception e){
            }
            try{ for(TwoBalloon i : ball2) i.paint(g);
                } catch ( Exception e){
            }
            g.setFont(new Font("Impact", Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString("Player1", 200, 20);
            g.setFont(new Font("Impact", Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString("Player2", 730, 20);
            g.setFont(new Font("Impact", Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString("SCORE = " + score1, 10, 20);
            g.setFont(new Font("Impact", Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString("SCORE = " + score2, 905, 20);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(499, -5, 17, 700);
            time.paint(g);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(482, 628, 50, 50);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(484, 630, 45, 45);    // pauseall button
            g.setFont(new Font("Impact", Font.PLAIN, 35));
            g.setColor(Color.BLACK);
            g.drawString("P", 498, 667);
            if((time.getY() >= 630 && !gameover1 && !pauseall) || (time.getY() >= 690 && !gameover2 && !pauseall)) {   // timeup
                pause1 = true;
                pause2 = true;
                loop1 += t.getDelay();
                if(!gameover1 && gameover2) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.fillRect(0, 0, 499, 700);
                    g2.setStroke(new BasicStroke(1f));
                    g2.dispose();
                }
                else if(!gameover2 && gameover1) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.fillRect(515, 0, 499, 700);
                    g2.setStroke(new BasicStroke(1f));
                    g2.dispose();
                }
                else if(!gameover1 && !gameover2) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.fillRect(0, 0, 1000, 700);
                    g2.setStroke(new BasicStroke(1f));
                    g2.setColor(Color.WHITE);
                    g2.drawRect(0, 0, 1000, 700);
                    g2.dispose();
                }
                if(loop1 <= 4000) {  //popup
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(499, -5, 17, 700);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(465, 0, 80, 30);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(469, 2, 70, 25);
                    g.setFont(new Font("Impact", Font.PLAIN, 25));
                    g.setColor(Color.BLACK);
                    g.drawString("Timer", 475, 24);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(150, 300, 200, 100);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(155, 305, 189, 90);
                    g.setFont(new Font("Impact", Font.PLAIN, 50));
                    g.setColor(Color.GREEN);
                    g.drawString("Time Up.", 163, 370);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(653, 300, 200, 100);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(658, 305, 189, 90);
                    g.setFont(new Font("Impact", Font.PLAIN, 50));
                    g.setColor(Color.GREEN);
                    g.drawString("Time Up.", 666, 370);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(482, 628, 50, 50);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(484, 630, 45, 45);    // pauseall button
                    g.setFont(new Font("Impact", Font.PLAIN, 35));
                    g.setColor(Color.BLACK);
                    g.drawString("P", 498, 667);
                }
                else if(loop1 > 4000) {  //score conclude dialog
                    if(gameover1) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(new Color(0, 0, 0, 120));
                        g2.fillRect(0, 0, 515, 700);
                        g2.setStroke(new BasicStroke(1f));
                        g2.dispose();
                    }
                    else if(gameover2) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(new Color(0, 0, 0, 120));
                        g2.fillRect(499, 0, 510, 700);
                        g2.setStroke(new BasicStroke(1f));
                        g2.dispose();
                    }
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(300, 190, 400, 300);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(305, 195, 390, 290);
                    g.setFont(new Font("Impact", Font.PLAIN, 32));
                    g.setColor(Color.BLACK);
                    g.drawString("Score.", 465, 225);
                    g.setFont(new Font("Impact", Font.PLAIN, 25));
                    g.setColor(Color.BLACK);
                    g.drawString("Player1 :                      " + Game.sc1.get(Game.sc1.size()-1), 320, 275);
                    g.drawString("Player2 :                     " + Game.sc2.get(Game.sc2.size()-1), 320, 325);
                    g.drawString("Winner :                      " + getWinner(), 320, 375);
                    g2d.setColor(Color.RED);    // back to mainmenu button
                    g2d.fillRect(340, 415, 120, 45);
                    g.setFont(new Font("Impact", Font.PLAIN, 25));
                    g.setColor(Color.WHITE);
                    g.drawString("Mainmenu", 345, 447);
                    g2d.setColor(Color.GREEN);   // resume button
                    g2d.fillRect(550, 415, 120, 45);
                    g.setFont(new Font("Impact", Font.PLAIN, 25));
                    g.setColor(Color.WHITE);
                    g.drawString("Resume", 570, 447);
                }
                
            }
            if(gameover1) {   //player1 gameover
                if(loop1 <= 4000) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.fillRect(0, 0, 499, 700);
                    g2.setStroke(new BasicStroke(1f));
                    g2.setColor(Color.WHITE);
                    g2.drawRect(0, 0, 499, 700);
                    g2.dispose();
                    time.paint(g);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(150, 300, 200, 100);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(155, 305, 189, 90);
                    g.setFont(new Font("Impact", Font.PLAIN, 40));
                    g.setColor(Color.RED);
                    g.drawString("GameOver.", 163, 370);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(482, 628, 50, 50);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(484, 630, 45, 45);    // pauseall button
                    g.setFont(new Font("Impact", Font.PLAIN, 35));
                    g.setColor(Color.BLACK);
                    g.drawString("P", 498, 667);
                }
            }
            if(gameover2) {
                if(loop1 <= 4000) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.fillRect(515, 0, 499, 700);
                    g2.setStroke(new BasicStroke(1f));
                    g2.setColor(Color.WHITE);
                    g2.drawRect(515, 0, 499, 700);
                    g2.dispose();
                    time.paint(g);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(653, 300, 200, 100);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(658, 305, 189, 90);
                    g.setFont(new Font("Impact", Font.PLAIN, 40));
                    g.setColor(Color.RED);
                    g.drawString("GameOver.", 666, 370);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(482, 628, 50, 50);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(484, 630, 45, 45);    // pauseall button
                    g.setFont(new Font("Impact", Font.PLAIN, 35));
                    g.setColor(Color.BLACK);
                    g.drawString("P", 498, 667);
                }
            }
            if(gameover1 && gameover2) {
                loop1 += t.getDelay();
                time.stopTimeLine();
                if(loop1 > 4000) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.fillRect(0, 0, 1000, 700);
                    g2.setStroke(new BasicStroke(1f));
                    g2.setColor(Color.WHITE);
                    g2.drawRect(0, 0, 1000, 700);
                    g2.dispose();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(300, 190, 400, 300);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(305, 195, 390, 290);
                    g.setFont(new Font("Impact", Font.PLAIN, 32));
                    g.setColor(Color.BLACK);
                    g.drawString("Score.", 465, 225);
                    g.setFont(new Font("Impact", Font.PLAIN, 25));
                    g.setColor(Color.BLACK);
                    g.drawString("Player1 :                      " + Game.sc1.get(Game.sc1.size()-1), 320, 275);
                    g.drawString("Player2 :                     " + Game.sc2.get(Game.sc2.size()-1), 320, 325);
                    g.drawString("Winner :                      " + getWinner(), 320, 375);
                    g2d.setColor(Color.RED);    // back to mainmenu button
                    g2d.fillRect(340, 415, 120, 45);
                    g.setFont(new Font("Impact", Font.PLAIN, 25));
                    g.setColor(Color.WHITE);
                    g.drawString("Mainmenu", 345, 447);
                    g2d.setColor(Color.GREEN);   // resume button
                    g2d.fillRect(550, 415, 120, 45);
                    g.setFont(new Font("Impact", Font.PLAIN, 25));
                    g.setColor(Color.WHITE);
                    g.drawString("Resume", 570, 447);
                }
            }
            if(pauseall) {
                pause1 = pause2 = true;
                g2d.setColor(Color.WHITE);
                g2d.fillRect(499, -5, 17, 700);
                time.stopTimeLine();
                g.setFont(new Font("Impact", Font.PLAIN, 35));
                g.setColor(Color.WHITE);
                g.drawString("P", 498, 667);
                if(!gameover1) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.fillRect(0, 0, 499, 700);
                    g2.setStroke(new BasicStroke(1f));
                    g2.setColor(Color.WHITE);
                    g2.drawRect(0, 0, 499, 700);
                    g2.dispose();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(150, 300, 200, 100);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(155, 305, 189, 90);
                    g.setFont(new Font("Impact", Font.PLAIN, 50));
                    g.setColor(Color.CYAN);
                    g.drawString("PAUSE.", 183, 370);
                    time.paint(g);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(482, 628, 50, 50);
                    g2d.setColor(Color.CYAN);
                    g2d.fillRect(484, 630, 45, 45);    // pauseall button
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(484, 630, 45, 45);
                    g.setFont(new Font("Impact", Font.PLAIN, 35));
                    g.setColor(Color.WHITE);
                    g.drawString("P", 498, 667);
                    
                }
                if(!gameover2) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 120));
                    g2.fillRect(515, 0, 499, 700);
                    g2.setStroke(new BasicStroke(1f));
                    g2.setColor(Color.WHITE);
                    g2.drawRect(515, 0, 499, 700);
                    g2.dispose();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(653, 300, 200, 100);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(658, 305, 189, 90);
                    g.setFont(new Font("Impact", Font.PLAIN, 50));
                    g.setColor(Color.CYAN);
                    g.drawString("PAUSE.", 686, 370);
                    time.paint(g);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(482, 628, 50, 50);
                    g2d.setColor(Color.CYAN);
                    g2d.fillRect(484, 630, 45, 45);    // pauseall button
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(484, 630, 45, 45);
                    g.setFont(new Font("Impact", Font.PLAIN, 35));
                    g.setColor(Color.WHITE);
                    g.drawString("P", 498, 667);
                }
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    //เพิ่มลูกโป่ง
    public void add() {
        if( cnt%25 == 0 && state == 1 && pause == true){
            arr.add(new Balloon(bow, p,this));
        }
        if( cnt%25 == 0 && state == 4 && !pause1){
            ball1.add(new OneBalloon(b1, p1,this));
        }
        if( cnt%25 == 0 && state == 4 && !pause2){
            ball2.add(new TwoBalloon(b2, p2,this));
        }
    }
    
    //balloon and timeline move
    public void move(){
        if(state == 1 && pause == true) {
            try { for(Balloon b:arr) 
              b.move();
              cnt++;  
            } catch(Exception e){
              }
            try { for(Bow b:ar)
              b.move();
            } catch(Exception e){
              }
        }
        if(state == 4 && !pause1) {
            p1.move();
            try { for(OneBow b1 : ob)
              b1.move();
            } catch(Exception e){
              }
            try { for(OneBalloon b : ball1) 
              b.move();
              cnt++;  
            } catch(Exception e){
              }
        }
        if(state == 4 && !pause2) {
            p2.move();
            try { for(TwoBow b2 : tb)
              b2.move();
            } catch(Exception e){
              }
            try { for(TwoBalloon b : ball2) 
              b.move();
            if(pause1) cnt++;  
            } catch(Exception e){
              }
        }
        if(state == 4) {
            time.move();
        }
    }
    
    public void doGameOver(){
        if(state == 1) {
            for(Balloon i : arr) {
                if (score < 0) {
                    state = 3;
                    pause = false;
                }
                if(getHorizon().intersects(i.getBoundsY1())){
                    arr.remove(i);
                    score = score - 2;
                }
                for( Bow j : ar) {
                    if(j.getBounds().intersects(i.getBoundsY1()) && ball.getColour() == 0) {
                        arr.remove(i);
                        ar.remove(j);
                        ex.add(new Explode(i));
                        score++;
                        String spot = Integer.toString(score);
                        sc.add(spot);
                    }
                    else if(j.getBounds().intersects(i.getBoundsY1()) && ball.getColour() == 1) {
                        arr.remove(i);
                        ar.remove(j);
                        ex.add(new Explode(i));
                        score += 2;
                        String spot = Integer.toString(score);
                        sc.add(spot);
                    }
                    else if(j.getBounds().intersects(i.getBoundsY1()) && ball.getColour() == 2) {
                        arr.remove(i);
                        ar.remove(j);
                        ex.add(new Explode(i));
                        score += 3;
                        String spot = Integer.toString(score);
                        sc.add(spot);
                    }
                    if(state == 1 && j.getXb() > 1000) {
                        ar.remove(j);
                    }
                    if(state == 4 && j.getYb() < 0) {
                        ar.remove(j);
                    }
                }
            }   
        }
        else if(state == 4) {
            for(OneBalloon i : ball1) {
                if (score1 < 0) {
                    gameover1 = true;
                    pause1 = true;
                }
                if(getHorizon1().intersects(i.getBounds())){
                    ball1.remove(i);
                    score1 = score1 - 2;
                }
                for( OneBow j : ob) {
                    if(j.getBounds().intersects(i.getBounds())) {
                        ball1.remove(i);
                        ob.remove(j);
                        ex1.add(new Explode1(i));
                        score1++;
                        String spot = Integer.toString(score1);
                        sc1.add(spot);
                    }
                    if(j.getY() < 0) {
                        ob.remove(j);
                    }
                }
            }
            for(TwoBalloon i : ball2) {
                if (score2 < 0) {
                    gameover2 = true;
                    pause2 = true;
                }
                if(getHorizon1().intersects(i.getBounds())){
                    ball2.remove(i);
                    score2 = score2 - 2;
                }
                for( TwoBow j : tb) {
                    if(j.getBounds().intersects(i.getBounds())) {
                        ball2.remove(i);
                        tb.remove(j);
                        ex2.add(new Explode2(i));
                        score2++;
                        String spot = Integer.toString(score2);
                        sc2.add(spot);
                    }
                    if(j.getY() < 0) {
                        tb.remove(j);
                    }
                }
            }
        }
        
        //arr.add(new Balloon(bow, p,this));
    }
    
    public ArrayList getAr() {
        return ar;
    }
    
    public Rectangle getHorizon(){ 
        return new Rectangle(0, 0, 10, 700);
    }
    
    public Rectangle getHorizon1(){ 
        return new Rectangle(0, 670, 1000, 10);
    }

    public void shoot() {
        if(MultiKey) ar.add(new Bow(p));
        if(keyBow1) ob.add(new OneBow(p1));
        if(keyBow2) tb.add(new TwoBow(p2));
    }
    
    public String getWinner() {
        String win = null;
        int high1 = Integer.parseInt(Game.sc1.get(Game.sc1.size()-1));
        int high2 = Integer.parseInt(Game.sc2.get(Game.sc2.size()-1));
        if(high1 > high2) win = "Player1";
        else if(high2 > high1) win = "player2";
        else win = "None";
        return win;
    }
}

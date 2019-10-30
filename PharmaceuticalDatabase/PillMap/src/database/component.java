package database;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import ConnectPharma.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//hi im from atom!m
public class component extends JPanel implements MouseWheelListener,MouseListener,KeyListener,MouseMotionListener    {
    private BufferedImage bg,search_btnI,show,bar_alertI,logo,add_btnI,delete_btnI,back_btnI,dim,insert_btnI,edit_btnI,deletesign,bar_shadow,delete_btnA,edit_btnA,closeI;
    public String input ,secondinput="";
    public static final int WIDTH = 1024,HEIGHT = 768;
    JTextField mainField = new JTextField("");
    String scene,isover,editmode="",alertmes="";
    animation search_btn,bar_alert,add_btn,delete_btn,back_btn,insert_btn,edit_btn,close;
    Rectangle search_btn_rect = new Rectangle(700,280,68,68),mouserect;
    int cusor,scusor;

    ArrayList<String> list = new ArrayList<String>(),compare = new ArrayList<String>(Arrays.asList("GenericName","TradeName","Group","CommonName","Preg","Indication","DultDose","Precaution","ADR","AdditionInformation","Caution"))
            ,column = new ArrayList<String>(Arrays.asList("genericname","tradename","preg","indication","adultdose","precaution","adr","additionalinformation")),calculate=new ArrayList<String>(),temp = new ArrayList<String>()
            ,removelist = new ArrayList<String>(),editcompare = new ArrayList<String>(Arrays.asList("GenericName","TradeName","Group","CommonName","Preg","Indication","DultDose","Precaution","ADR","AdditionInformation","Caution"));
   
    ArrayList<ArrayList<String>> detaillist=new ArrayList();

    Point pin = new Point(0,0),target=new Point(0,0),menuPin = new Point(0,0),menutarget=new Point(0,0),logoPin= new Point(0,0),logotarget = new Point(0,0),choicePin = new Point(0,0)
            ,choicetargrt = new Point(0,HEIGHT/2-25),selectorPin=new Point(0,0),selectortarget= new Point(0,0),editPin = new Point(0,0),edittarget = new Point(0,0),subchoicePin = new Point(0,0),subchoicetarget = new Point(0,0)
            ,subselectorPin = new Point(0,0),subselectortarget = new Point(0,0);
    double idPinX,idPinY,showoffset=0,menuPinX,menuPinY,logoPinX,logoPinY,choicePinX,choicePinY,selectorPinX,editPinX,editPinY,schoicePinX,schoicePinY,subchoicePinX,subchoicePinY,subselectorPinX;
    boolean alert,over=false,isEnglish,login=false,click,select=false,trade,press=false,selectedit=false,exit = false;
    ConnectPharma connect;

    //String language = DetectLanguage.simpleDetect("Hello world");



    component() throws IOException, SQLException{
        mouserect = new Rectangle(0,0,0,0);
        bg = ImageIO.read(new File("src/pic/bg.png"));
        deletesign = ImageIO.read(new File("src/pic/deletesign.png"));
        bar_shadow = ImageIO.read(new File("src/pic/bar_shadow.png"));
        dim = ImageIO.read(new File("src/pic/dimmer.png"));
        search_btnI = ImageIO.read(new File("src/pic/search_btn.png"));
        bar_alertI = ImageIO.read(new File("src/pic/bar_alert.png"));
        add_btnI = ImageIO.read(new File("src/pic/add_btn.png"));
        delete_btnI = ImageIO.read(new File("src/pic/delete_btn.png"));
        delete_btnA = ImageIO.read(new File("src/pic/delete_btnA.png"));
        back_btnI = ImageIO.read(new File("src/pic/back_btn.png"));
        insert_btnI = ImageIO.read(new File("src/pic/insert_btn.png"));
        edit_btnI = ImageIO.read(new File("src/pic/edit_btn.png"));
        edit_btnA = ImageIO.read(new File("src/pic/edit_btnA.png"));
        logo = ImageIO.read(new File("src/pic/logo.png"));
        closeI = ImageIO.read(new File("src/pic/close.png"));
        show=null;
        connect = new ConnectPharma();

        add(mainField);
        mainField.setLocation(0,0);
        scene = "input";
        mainField.selectAll();
        mainField.addActionListener(fieldListener);
        mainField.addKeyListener(this);
        alert=false;


        idPinX=pin.x;idPinY=pin.y;
        search_btn = new animation(search_btnI,9);
        bar_alert = new animation(bar_alertI,20);
        add_btn = new animation(add_btnI,10);
        delete_btn = new animation(delete_btnI,10);
        back_btn = new animation(back_btnI,10);
        insert_btn = new animation(insert_btnI,10);
        edit_btn = new animation(edit_btnI,10);
        close = new animation(closeI,10);



    }
    Action fieldListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            
        }
    };
    public void refresh() throws SQLException{
        isEnglish = (Pattern.matches("[a-zA-Z]",mainField.getText().length()>0?mainField.getText().charAt(0)+"":""));
        input = mainField.getText().length()>22? mainField.getText().substring(mainField.getText().length()-21):mainField.getText();
        search_btn_rect = new Rectangle(pin.x+700,pin.y+(280),68,68);
        
        edittarget.setLocation((selectedit?new Point(0,300):new Point(0,0)));
        
        logotarget = scene.equals("input")?target:scene.equals("singlepic")?new Point(-350,target.y==-350?target.y-100:target.y+100):new Point(-350,-100);
        menutarget = scene.equals("input")?new Point(0,0):new Point(300+60*list.size(),0);
        idPinY-=(idPinY-target.y)/60;
        idPinX-=(idPinX-target.x)/60;
        logoPinY-=(logoPinY-logotarget.y)/120;
        logoPinX-=(logoPinX-logotarget.x)/120;
        menuPinY-=(menuPinY-menutarget.y)/60;
        menuPinX-=(menuPinX-menutarget.x)/60;
        choicePinY-=(choicePinY-choicetargrt.y)/20;
        choicePinX-=(choicePinX-choicetargrt.x)/20;
        subchoicePinY-=(subchoicePinY-subchoicetarget.y)/20;
        subchoicePinX-=(subchoicePinX-subchoicetarget.x)/20;
        selectorPinX-=(selectorPinX-selectortarget.x)/20;
        subselectorPinX-=(subselectorPinX-subselectortarget.x)/20;
        editPinX-=(editPinX-edittarget.x)/20;
        editPinY-=(editPinY-edittarget.y)/20;
        
        pin.y = (int) idPinY ;
        pin.x = (int) idPinX ;
        logoPin.y = (int) logoPinY ;
        logoPin.x = (int) logoPinX ;
        menuPin.y = (int) menuPinY ;
        menuPin.x = (int) menuPinX ;
        choicePin.y = (int) choicePinY;
        choicePin.x = (int) choicePinX;
        subchoicePin.y = (int) subchoicePinY;
        subchoicePin.x = (int) subchoicePinX;
        selectorPin.x = (int) selectorPinX;
        subselectorPin.x = (int) subselectorPinX;
        editPin.x = (int) editPinX;
        editPin.y = (int) editPinY;

        switch(scene){
            case "input":
                break;
            case "singlepic":break;
            case "multiplepic":break;
            case "edit":
                if((!login||editmode.equals("add"))&&!list.isEmpty())list.set(list.size()-1-cusor, mainField.getText());
                break;
        }
        if(bar_alert.getCurrent()==bar_alert.getMax()-1){
            bar_alert.reset();
            alert=false;
        }
        repaint();
        //System.out.println(pin.y);
        //System.out.println(isEnglish);
        //System.out.println(list.size()+" "+cusor);

    }
    public void toInput(){
        isover = "";
        list.clear();
        showoffset = 0;
        target.setLocation(0,0);
        scene="input";
    }
    public void toMulti(){
        scene = "multiplepic";
        target.setLocation(0, -100);
    }
    public void toSingle(){
        list.clear();
        target.setLocation(0, -200);
        showoffset = 0;
        
        if(isover.length()>22)
            isover = isover.substring(0,22);
        mainField.setText(isover);
        isover = mainField.getText();
        mainField.selectAll();
        scene = "singlepic";
    }
    @Override
    public void paint(Graphics g) {
            try {
                if(!scene.equals("edit"))requestData();
            } catch (SQLException ex) {
            Logger.getLogger(component.class.getName()).log(Level.SEVERE, null, ex);
        }
                g.setColor(Color.white);
                g.drawImage(bg, pin.y+(pin.y*20)/2,pin.y+(pin.y*20)/2,1024-(pin.y)*20,768-(pin.y)*20, this);
        switch(scene){
            case "input" :
                target = new Point(0,0);
                //g.fillRect(0, 0, WIDTH, HEIGHT);
                if(!alert){
                    g.setColor(Color.decode("#06BEB4"));
                    g.fillRect(pin.x+220, pin.y+500, 480, 68);
                }else{
                    g.drawImage(bar_alert.getImgMax(),pin.x+220,pin.y+500, 480, 68, this);
                    if(bar_alert.getCurrent()== bar_alert.max-1){
                        alert=false;
                        bar_alert.reset();
                    }
                }
                //g.drawImage(logo,pin.x+320,pin.y+124,logo.getWidth()-60,logo.getHeight()-30, this);
                g.setColor(Color.decode("#BFFFFF"));
                g.setFont(new Font("TH Sarabun",0,40));
                g.drawString(input, pin.x+WIDTH/4+1,pin.y+550);
                if(mouserect.intersects(search_btn_rect))
                    g.drawImage(search_btn.getImgMax(),pin.x+700,pin.y+(500), search_btn.getWidth()-20, search_btn.getHeight()-20, this);
                else
                    g.drawImage(search_btn.getImgMin(),pin.x+700,pin.y+(500), search_btn.getWidth()-20, search_btn.getHeight()-20, this);

                break;
            case "singlepic":
                if(!alert)g.drawImage(show,50,(int)showoffset+200, (int)(show.getWidth()/2.7),(int)(show.getHeight()/2.7), this);
                if(!alert)g.drawImage(dim,0,(int)showoffset+200, (int)(dim.getWidth()),150, this);

                if(!alert){
                    g.setColor(Color.decode("#06BEB4"));
                    g.fillRect(pin.x+220, pin.y+280, 480, 68);
                }else{
                    g.drawImage(bar_alert.getImgMax(),pin.x+220,pin.y+280, 480, 68, this);
                    if(bar_alert.getCurrent()== bar_alert.max-1){
                        alert=false;
                        bar_alert.reset();
                    }
                }
                g.setColor(Color.decode("#BFFFFF"));
                g.setFont(new Font("TH Sarabun",0,40));
                g.drawString(input, pin.x+WIDTH/4+1,pin.y+330);

                if(mouserect.intersects(search_btn_rect)){
                    g.drawImage(search_btn.getImgMax(),pin.x+700,pin.y+(280), search_btn.getWidth()-20, search_btn.getHeight()-20, this);
                    //isover = "search";
                }
                else
                    g.drawImage(search_btn.getImgMin(),pin.x+700,pin.y+(280), search_btn.getWidth()-20, search_btn.getHeight()-20, this);
                break;
            case "multiplepic":

                for(int index = 0;index<list.size();index++){
                    if(mouserect.intersects(new Rectangle(275, 252+54*index+(int)showoffset, 450, 50))){
                        g.setColor(Color.decode("#49D0BF"));
                    }else{
                        g.setColor(Color.decode("#49AB9E"));
                    }

                    g.fillRect(275, 252+54*index+(int)showoffset, 450, 50);
                    g.setColor(Color.WHITE);
                    if(list.size()>0)g.drawString(list.get(index), 290, 285+54*index+(int)showoffset);
                    g.setColor(Color.black);
                    //g.drawRect(275, 252+54*index+(int)showoffset, 450, 50);
                }
                g.setColor(Color.white);
                g.drawImage(dim,0,155, (int)(dim.getWidth()),120, this);
                g.fillRect(0, 0, WIDTH, 155);
                g.setColor(Color.WHITE);
                //g.fillRect(0, 0, WIDTH, 250);
                if(!alert){
                    g.setColor(Color.decode("#06BEB4"));
                    g.fillRect(pin.x+220, pin.y+280, 480, 68);
                }else{
                    g.drawImage(bar_alert.getImgMax(),pin.x+220,pin.y+280, 480, 68, this);
                    if(bar_alert.getCurrent()== bar_alert.max-1){
                        alert=false;
                        bar_alert.reset();
                    }
                }
                g.setColor(Color.black);
                g.setColor(Color.decode("#BFFFFF"));
                g.setFont(new Font("Angsana",0,40));
                g.drawString(input, pin.x+WIDTH/4+1,pin.y+330);
                if(mouserect.intersects(search_btn_rect))
                    g.drawImage(search_btn.getImgMax(),pin.x+700,pin.y+(280), search_btn.getWidth()-20, search_btn.getHeight()-20, this);
                else
                    g.drawImage(search_btn.getImgMin(),pin.x+700,pin.y+(280), search_btn.getWidth()-20, search_btn.getHeight()-20, this);
                break;
            case "edit":
                target = new Point(0,-250);
                if (login) {
                    ///////ADD//////////////////////////////////////////////
                    if(editmode.equals("add")){
                        for(int index = 0;index<list.size();index++){
                            g.setColor(Color.decode("#49AB9E"));
                            g.fillRect(WIDTH/2-150,choicePin.y+index*54, 300, 50);
                            g.setColor(Color.white);
                            g.drawString(list.get(index).length()>37?list.get(index).substring(0, 35):list.get(index), WIDTH/2-130,choicePin.y+index*54+25);
                            g.setColor(Color.decode("#49D0BF"));
                            if(index>list.size()-2)g.drawImage(bar_shadow,WIDTH/2-200+(selectorPin.x/2)-50, HEIGHT/2-30-35, 400-(selectorPin.x)+100, 60+60,this);
                            if(!alert)g.fillRect(WIDTH/2-200+(selectorPin.x/2), HEIGHT/2-30, 400-(selectorPin.x), 60);
                            else g.drawImage(bar_alert.getImgMax(),WIDTH/2-200+(selectorPin.x/2), HEIGHT/2-30, 400-(selectorPin.x), 60, this);
                            g.setColor(Color.decode("#06AFBF"));
                            if(!alert)g.fillRect(WIDTH/2-200+(selectorPin.x/2), HEIGHT/2-30-20, compare.get(compare.size()-cusor-1).length()*12, 20);
                            else g.drawImage(bar_alert.getImgMax(), WIDTH/2-200+(selectorPin.x/2), HEIGHT/2-30-20, alertmes.length()*9, 20, this);
                        }
                        g.setColor(Color.white);
                        g.setFont(new Font("TH Sarabun",1,41));
                        g.drawString(input, WIDTH/2-170+selectorPin.x/2, HEIGHT-370);
                        g.setFont(new Font("TH Sarabun",0,15));
                        if(!alert)g.drawString(compare.get(compare.size()-cusor-1), WIDTH/2-200+(selectorPin.x/2)+3, HEIGHT/2-30-3);
                        else g.drawString(alertmes, WIDTH/2-200+(selectorPin.x/2)+3, HEIGHT/2-30-3);
                        g.setColor(Color.white);
                        g.fillRect(0, 0, 1024, 160);
                        g.drawImage(dim,0,160,1024,100,this);
                        g.drawImage(dim,0,HEIGHT,1024,-250,this);

                    ///////DELETE//////////////////////////////////////////////
                    }else if(editmode.equals("delete")){
                        for(int index = 0;index<list.size();index++){
                            g.setColor(Color.decode("#49AB9E"));
                            g.fillRect(WIDTH/2-150,choicePin.y+index*54, 300, 50);
                            g.setColor(Color.white);
                            g.drawString(list.get(index).length()>37?list.get(index).substring(0, 35):list.get(index), WIDTH/2-130,choicePin.y+index*54+25);
                            if(index>list.size()-2)g.drawImage(bar_shadow,WIDTH/2-200+(selectorPin.x/2)-50, HEIGHT/2-30-35, 400-(selectorPin.x)+100, 60+60,this);
                            g.setColor(Color.decode("#49D0BF"));
                            g.fillRect(WIDTH/2-200+(selectorPin.x/2), HEIGHT/2-30, 400-(selectorPin.x), 60);
                        }
                        g.setColor(Color.white);
                        g.setFont(new Font("TH Sarabun",1,41));
                        g.drawString(list.get(list.size()-1-cusor), WIDTH/2-170+selectorPin.x/2, HEIGHT-370);
                        g.setFont(new Font("TH Sarabun",0,40));
                        if(select)
                            g.drawImage(deletesign, WIDTH/2+200-selectorPin.x/2-58, HEIGHT/2-30, this);
                        g.fillRect(0, 0, 1024, 160);
                        g.drawImage(dim,0,160,1024,100,this);
                        g.drawImage(dim,0,HEIGHT,1024,-250,this);
                        
                        if(mouserect.intersects(new Rectangle(WIDTH/2-200+(selectorPin.x/2), HEIGHT/2-30, 400-(selectorPin.x), 60)) && click){
                            if(!select){
                                select = true;
                            }
                            else{
                                String selectedtext="";
                                if(list.size()==1){
                                    selectedtext =list.get(0);
                                    try {
                                        connect.deleteRow(selectedtext);
                                    } catch (SQLException ex) {}
                                    
                                    editmode = "";
                                    click = false;
                                    return;
                                }else if(cusor>0){
                                    selectedtext = list.get(list.size()-cusor-1);
                                    cusor--;
                                    mainField.setText(list.get(list.size()-1-cusor));
                                }else{
                                    selectedtext = list.get(list.size()-cusor-1);
                                    mainField.setText(list.get(list.size()-1-cusor));
                                    choiceUp();
                                    cusor--;
                                }
                                try {
                                    connect.deleteRow(selectedtext);
                                    list = connect.getOrder(secondinput, "GenericName", "pharmacyinformation", "GenericName", false);
                                    editmode="delete";
                                } catch (SQLException ex){}
                                
                                select=false;
                            }
                            
                            click=false;
                        }
                        
                    ///////EDIT//////////////////////////////////////////////
                    }else if(editmode.equals("edit")){
                        for(int index = 0;index<list.size();index++){
                            g.setColor(Color.decode("#49AB9E"));
                            g.fillRect(WIDTH/2-150+editPin.x,choicePin.y+index*54+editPin.y, 300, 50);
                            g.setColor(Color.white);
                            g.drawString(list.get(index).length()>37?list.get(index).substring(0, 35):list.get(index), WIDTH/2-130+editPin.x,choicePin.y+index*54+25+editPin.y);
                            if(index>list.size()-2)g.drawImage(bar_shadow,WIDTH/2-200+(selectorPin.x/2)-50+editPin.x, HEIGHT/2-30-35+editPin.y, 400-(selectorPin.x)+100, 60+60,this);
                            g.setColor(Color.decode("#49D0BF"));
                            g.fillRect(WIDTH/2-200+(selectorPin.x/2)+editPin.x, HEIGHT/2-30+editPin.y, 400-(selectorPin.x), 60);
                        }
                        g.setColor(Color.white);
                        g.setFont(new Font("TH Sarabun",1,41));
                        g.drawString(list.get(list.size()-1-cusor), WIDTH/2-170+selectorPin.x/2+editPin.x, HEIGHT-370+editPin.y);
                        g.setFont(new Font("TH Sarabun",0,40));
                        g.fillRect(0, 0, 1024, 160);
                        g.drawImage(dim,0,160,1024,100+editPin.y,this);
                        g.drawImage(dim,0,HEIGHT,1024,-250-editPin.y,this);
                        
                        if(selectedit){
                            //System.out.println(detaillist);
                            for(int index = 0;index<detaillist.size();index++){
                                g.setFont(new Font("TH Sarabun",0,15));
                                g.setColor(Color.decode("#49AB9E"));
                                g.fillRect(WIDTH/2-150,subchoicePin.y+index*54, 300, 50);                           
                                g.setColor(Color.white);
                                g.drawString(detaillist.get(index).get(0),WIDTH/2-130,subchoicePin.y+index*54+25);
                                g.setColor(Color.decode("#49D0BF"));
                                if(index>detaillist.size()-2)g.drawImage(bar_shadow,WIDTH/2-200+(subselectorPin.x/2)-50, HEIGHT/2-30-35, 400-(subselectorPin.x)+100, 60+60,this);
                                if(!alert)g.fillRect(WIDTH/2-200+(subselectorPin.x/2), HEIGHT/2-30, 400-(subselectorPin.x), 60);
                                else g.drawImage(bar_alert.getImgMax(),WIDTH/2-200+(subselectorPin.x/2), HEIGHT/2-30, 400-(subselectorPin.x), 60, this);
                                g.setColor(Color.decode("#06AFBF"));
                                if(!alert)g.fillRect(WIDTH/2-200+(subselectorPin.x/2), HEIGHT/2-30-20, editcompare.get(editcompare.size()-scusor-1).length()*12, 20);
                                else g.drawImage(bar_alert.getImgMax(), WIDTH/2-200+(subselectorPin.x/2), HEIGHT/2-30-20, alertmes.length()*9, 20, this);
                                g.setFont(new Font("TH Sarabun",0,10));
                            }
                            g.setColor(Color.white);
                            g.setFont(new Font("TH Sarabun",1,41));
                            g.drawString(input, WIDTH/2-170+subselectorPin.x/2, HEIGHT-370);
                            g.setFont(new Font("TH Sarabun",0,15));
                            if(!alert)g.drawString(editcompare.get(editcompare.size()-scusor-1), WIDTH/2-200+(subselectorPin.x/2)+3, HEIGHT/2-30-3);
                            else g.drawString(alertmes, WIDTH/2-200+(subselectorPin.x/2)+3, HEIGHT/2-30-3);
                            g.setColor(Color.white);
                            g.fillRect(0, 0, 1024, 160);
                            g.drawImage(dim,0,160,1024,100,this);
                            g.drawImage(dim,0,HEIGHT,1024,-250,this);
                        }

                        if(mouserect.intersects(new Rectangle(WIDTH/2-200+(selectorPin.x/2), HEIGHT/2-30, 400-(selectorPin.x), 60)) && click){
                            if(!selectedit){
                                try {
                                    detaillist = connect.getAllData(list.get(list.size()-1-cusor));
                                    } catch (SQLException ex) {}
                                scusor = detaillist.size() - 1;
                                mainField.setText(detaillist.get(detaillist.size() - scusor - 1).get(0));
                                mainField.selectAll();
                                subchoicetarget.setLocation(0, HEIGHT / 2 - 25);
                                if(detaillist.get(10).get(0).equals("จำหน่ายตามแพทย์สั่งขายตามหมอสั่งจำหน่ายตามหมอสั่งขายตามใบรับรองแพทย์จำหน่ายตามใบรับรองแพทย์"))
                                    detaillist.get(10).set(0, "1");
                                else if(detaillist.get(10).get(0).equals("จำหน่ายได้ทั่วไปตามร้านขายยาหาซื้อได้ทั่วไปตามร้านขายยามีขายได้ทั่วไปตามร้านขายยา"))
                                    detaillist.get(10).set(0, "2");
                                else if(detaillist.get(10).get(0).equals("ไม่มีจำหน่ายวางจำหน่ายไม่มีขายไม่ขาย"))
                                detaillist.get(10).set(0, "3");
                                
                                selectedit = true;
                            }
                            else{
                                mainField.setText(list.get(list.size()-1-cusor));
                            }
                            click=false;
                        }
                    }else{
                        //ADD/////////////////////////////////////////////////
                        if (mouserect.intersects(WIDTH / 2 - insert_btn.width / 2, HEIGHT / 2 - insert_btn.height / 2, insert_btn.getWidth(), insert_btn.getHeight())) {
                            if(click){
                                list = new ArrayList(Arrays.asList("GenericName","TradeName","Group","CommonName","Preg","Indication","DultDose","Precaution","ADR","AdditionInformation","Caution"));
                                cusor=list.size()-1;
                                mainField.setText(list.get(list.size()-cusor-1));
                                mainField.selectAll();
                                choicetargrt.setLocation(0, HEIGHT/2-25);
                                editmode = "add";
                            }
                            g.drawImage(insert_btn.getImgMax(), WIDTH / 2 - insert_btn.width / 2, HEIGHT / 2 - insert_btn.height / 2, insert_btn.getWidth(), insert_btn.getHeight(), this);
                        } else {
                            g.drawImage(insert_btn.getImgMin(), WIDTH / 2 - insert_btn.width / 2, HEIGHT / 2 - insert_btn.height / 2, insert_btn.getWidth(), insert_btn.getHeight(), this);
                        }
                        
                        //EDIT/////////////////////////////////////////////////
                        if (mouserect.intersects(WIDTH / 4 - insert_btn.width / 2, HEIGHT / 2 - insert_btn.height / 2, edit_btn.getWidth(), edit_btn.getHeight())) {
                            try {
                                list = connect.getGenericName("", "GenericName", "pharmacyinformation", false);
                            } catch (SQLException ex) {}
                            if(click){
                                if (!list.isEmpty()) {
                                    selectedit = false;
                                    cusor = list.size() - 1;
                                    mainField.setText(list.get(list.size() - cusor - 1));
                                    mainField.selectAll();
                                    choicetargrt.setLocation(0, HEIGHT / 2 - 25);
                                    editmode = "edit";
                                }
                                click = false;
                            }
                            g.drawImage(edit_btn.getImgMax(), WIDTH / 4 - insert_btn.width / 2, HEIGHT / 2 - insert_btn.height / 2, edit_btn.getWidth(), edit_btn.getHeight(), this);
                            
                        } else {
                            g.drawImage(edit_btn.getImgMin(), WIDTH / 4 - insert_btn.width / 2, HEIGHT / 2 - insert_btn.height / 2, edit_btn.getWidth(), edit_btn.getHeight(), this);
                        }
                        if(list.isEmpty())g.drawImage(edit_btnA, WIDTH / 4 - insert_btn.width / 2, HEIGHT / 2 - insert_btn.height / 2, edit_btn.getWidth(), edit_btn.getHeight(), this);
                        
                        
                        //DELETE////////////////////////////////////////////////
                        if (mouserect.intersects(WIDTH / 2 - delete_btn.width / 2 + WIDTH / 4, HEIGHT / 2 - delete_btn.height / 2, delete_btn.getWidth(), delete_btn.getHeight())) {
                            try {
                                list = connect.getGenericName("","GenericName", "pharmacyinformation", false);
                            } catch (SQLException ex) {
                                Logger.getLogger(component.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if(click){
                                if (!list.isEmpty()) {
                                    cusor = list.size() - 1;
                                    mainField.setText(list.get(list.size() - cusor - 1));
                                    mainField.selectAll();
                                    choicetargrt.setLocation(0, HEIGHT / 2 - 25);
                                    editmode = "delete";
                                }else delete_btn.getImgMin();
                            }
                            if(!list.isEmpty())g.drawImage(delete_btn.getImgMax(), WIDTH / 2 - delete_btn.width / 2 + WIDTH / 4, HEIGHT / 2 - delete_btn.height / 2, delete_btn.getWidth(), delete_btn.getHeight(), this);
                            else g.drawImage(delete_btnA, WIDTH / 2 - delete_btn.width / 2 + WIDTH / 4, HEIGHT / 2 - delete_btn.height / 2, delete_btn.getWidth(), delete_btn.getHeight(), this);
                        } else {
                            if(!list.isEmpty())g.drawImage(delete_btn.getImgMin(), WIDTH / 2 - delete_btn.width / 2 + WIDTH / 4, HEIGHT / 2 - delete_btn.height / 2, delete_btn.getWidth(), delete_btn.getHeight(), this);
                            else g.drawImage(delete_btnA, WIDTH / 2 - delete_btn.width / 2 + WIDTH / 4, HEIGHT / 2 - delete_btn.height / 2, delete_btn.getWidth(), delete_btn.getHeight(), this);
                        }
                    }
                }else if(!login){
                    for(int index = 0;index<list.size();index++){
                        g.setColor(Color.decode("#49AB9E"));
                        g.fillRect(WIDTH/2-150,choicePin.y+index*54, 300, 50);
                        g.setColor(Color.white);
                        g.drawString(list.get(index).length()>37?list.get(index).substring(0, 35):list.get(index), WIDTH/2-130,choicePin.y+index*54+25);
                        if(index>list.size()-2)g.drawImage(bar_shadow,WIDTH/2-200+(selectorPin.x/2)-50, HEIGHT/2-30-35, 400-(selectorPin.x)+100, 60+60,this);
                        g.setColor(Color.decode("#49D0BF"));
                        if(!alert)g.fillRect(WIDTH/2-200+(selectorPin.x/2), HEIGHT/2-30, 400-(selectorPin.x), 60);
                        else g.drawImage(bar_alert.getImgMax(), WIDTH/2-200+(selectorPin.x/2), HEIGHT/2-30, 400-(selectorPin.x), 60, this);
                    }
                    g.setColor(Color.white);
                    g.setFont(new Font("TH Sarabun",1,41));
                    g.drawString(input, WIDTH/2-170+selectorPin.x/2, HEIGHT-370);
                    g.setFont(new Font("TH Sarabun",0,40));
                    g.fillRect(0, 0, 1024, 160);
                    g.drawImage(dim,0,160,1024,100,this);
                    g.drawImage(dim,0,HEIGHT,1024,-250,this);
                }
                if(mouserect.intersects(new Rectangle(WIDTH-60,HEIGHT-60,back_btn.width,back_btn.height)))
                    g.drawImage(back_btn.getImgMax(), WIDTH-60,HEIGHT-60, this);
                else
                    g.drawImage(back_btn.getImgMin(), WIDTH-60,HEIGHT-60, this);
                break;

        }

        if(mouserect.intersects(new Rectangle(WIDTH-60+menuPin.x,HEIGHT-60+menuPin.y,add_btn.width,add_btn.height)))
            g.drawImage(add_btn.getImgMax(), WIDTH-60+menuPin.x,HEIGHT-60+menuPin.y, this);
        else
            g.drawImage(add_btn.getImgMin(), WIDTH-60+menuPin.x,HEIGHT-60+menuPin.y, this);
        
        if(mouserect.intersects(new Rectangle(WIDTH-close.getWidth()-10, 10,close.getWidth(),close.getHeight()))){
            if(click)exit = true;
            g.drawImage(close.getImgMax(), WIDTH-close.getWidth()-10, 10, this);
        }else
            g.drawImage(close.getImgMin(), WIDTH-close.getWidth()-10, 10, this);
        g.drawImage(logo,logoPin.x+320,logoPin.y+100,350+logoPin.x/3,350+logoPin.x/3, this);
    }
    
    void requestData() throws SQLException{
        selectortarget.setLocation(mainField.getText().length()>11?(mainField.getText().length()<22?-(mainField.getText().length()-11)*15-(select?30:0):-(11)*15)-(select?30:0):0-(select?30:0),0);
        try {
            if(scene.equals("input") && !mainField.getText().equals("")&& !mainField.getText().equals("Data not found!")){
                if (!mainField.getText().contains(",")) {
                    if (isEnglish) {
                        if(connect.getOrder(mainField.getText(), "TradeName", "pharmacyinformation", "TradeName", false).size()>=1 && connect.getOrder(mainField.getText(), "TradeName", "pharmacyinformation", "TradeName", false).get(0).split(",").length>0){
                            toMulti();
                        }else if(connect.getOrder(mainField.getText(), "GenericName", "pharmacyinformation", "GenericName", false).size() > 1){
                            list = connect.getOrder(mainField.getText(), "GenericName", "pharmacyinformation", "GenericName", false);
                            toMulti();
                        }else toInput();
                    } else {//thai
                        if (connect.CommonToGeneric(mainField.getText()).size() > 0) {
                            list = connect.CommonToGeneric(mainField.getText());
                            toMulti();
                        }else if(connect.CautionToGeneric(mainField.getText()).size()>0){
                            toMulti();
                        }
                    }
                }else{
                    if(mainField.getText().split(",").length ==2)
                        if(column.contains(mainField.getText().split(",")[1].toLowerCase()))
                            if(connect.getOrder(mainField.getText().split(",")[0], mainField.getText().split(",")[1], "pharmacyinformation", "GenericName",false).size()>=1){
                                toMulti();
                                mainField.selectAll();
                            }
                }
            }else if(scene.equals("multiplepic")){
                if (isEnglish) {
                    if (!mainField.getText().equals("") && !mainField.getText().contains(",")) {
                        if(connect.getOrder(mainField.getText(), "TradeName", "pharmacyinformation", "TradeName", false).size()>=1&&connect.getOrder(mainField.getText(), "TradeName", "pharmacyinformation", "TradeName", false).get(0).split(",").length>0){
                            if (press) {
                                list.clear();
                                for (String thing : connect.getOrder(mainField.getText(), "TradeName", "pharmacyinformation", "TradeName", false)) {
                                    for (String subthing : thing.split(",")) {
                                        list.add(subthing + " (" + connect.getGenericName(thing, "TradeName", "pharmacyinformation", true).get(0) + ")");
                                    }
                                }
                            }
                        }else if (connect.getOrder(mainField.getText(), "GenericName", "pharmacyinformation", "GenericName", false).size() > 1) {
                            list = connect.getOrder(mainField.getText(), "GenericName", "pharmacyinformation", "GenericName", false);
                        } else if (connect.getOrder(mainField.getText(), "GenericName", "pharmacyinformation", "GenericName", false).size() == 1) {
                            mainField.setText(connect.getOrder(mainField.getText(), "GenericName", "pharmacyinformation", "GenericName", false).get(0));
                            isover = mainField.getText();
                            show = connect.getDetail(mainField.getText());
                            toSingle();
                        } else {
                            toInput();
                        }
                    } else if (mainField.getText().contains(",")) {
                        if (mainField.getText().split(",").length == 2) {
                            list = column.contains(mainField.getText().split(",")[1].toLowerCase()) ? connect.getOrder(mainField.getText().split(",")[0], mainField.getText().split(",")[1], "pharmacyinformation", "GenericName", false) : list;
                            if (!column.contains(mainField.getText().split(",")[1].toLowerCase()) || list.size() < 1) {
                                toInput();
                            }
                        } else {
                            toInput();
                        }
                    }
                }else{//ภาษไทย
                    if (!mainField.getText().equals("") && !mainField.getText().contains(",")) {
                        ArrayList<String> add = new ArrayList(),remove = new ArrayList(),calculate = new ArrayList();
                        if (connect.CommonToGeneric(mainField.getText()).size() > 0) {
                            calculate = connect.CommonToGeneric(mainField.getText());
                            add.clear();
                            remove.clear();
                            for(String thing:calculate){
                                if(thing.split(",").length>0){
                                    for(int index=0;index<thing.split(",").length;index++){
                                        add.add(thing.split(",")[index]);
                                    }
                                    remove.add(thing);
                                }
                            }
                            calculate.clear();
                            calculate.addAll(add);
                            list = calculate;
                            //System.out.println(calculate);
                            
                            target.setLocation(0, -100);
                        }else if(connect.CommonToGeneric(mainField.getText()).size() == 1){
                            System.out.println("out");
                            calculate = connect.CommonToGeneric(mainField.getText());
                            add.clear();
                            remove.clear();
                            if(calculate.get(0).split(",").length>0){
                                for(int index=0;index<calculate.get(0).split(",").length;index++){
                                    add.add(calculate.get(0).split(",")[index]);
                                }
                                remove.add(calculate.get(0));
                            }
                            calculate.addAll(add);
                            calculate.removeAll(remove);
                            if(calculate.size()>1)
                                list = calculate;
                            else if(calculate.size()==1){
                                show = connect.getDetail(calculate.get(0));
                                mainField.setText(calculate.get(0));
                                toInput();
                            }
                        }else if(connect.CautionToGeneric(mainField.getText()).size()>0){
                            System.out.println(mouserect.x+"by caution");
                            list = connect.CautionToGeneric(mainField.getText());
                        }else{
                            toInput();
                        }
                    }else if (mainField.getText().contains(",")) {
                        if (mainField.getText().split(",").length == 2) {
                            list = column.contains(mainField.getText().split(",")[1].toLowerCase())?connect.getOrder(mainField.getText().split(",")[0], mainField.getText().split(",")[1], "pharmacyinformation", "GenericName", false):list;
                            if(!column.contains(mainField.getText().split(",")[1].toLowerCase()) || list.size()<1){
                                toInput();
                            }
                        }else toInput();
                    }else toInput();
                }
            }else if(scene.equals("singlepic")){
                if(!mainField.getText().equals(isover) || mainField.getText().equals("")){
                    toInput();
                }
            }
        } catch (SQLException ex) {}
    }
    @Override
    public void keyTyped(KeyEvent ke) {
        if(!selectedit)selectortarget.setLocation(mainField.getText().length()>11?(mainField.getText().length()<22?-(mainField.getText().length()-11)*15-(select?30:0):-(11)*15)-(select?30:0):0-(select?30:0),0);
        else subselectortarget.setLocation(mainField.getText().length()>11?(mainField.getText().length()<22?-(mainField.getText().length()-11)*15:-(11)*15):0,0);
    }
    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        press = true;
        //System.out.println(ke.getKeyCode());
        if(ke.getKeyCode() == 27 || ke.getKeyCode() == 38 && scene.equals("edit")){
            if(!(ke.getKeyCode()==27 && editmode.equals("delete")))if(!selectedit){
                choiceUp();
            }else subchoiceUp();
        }else if(ke.getKeyCode() == 10 || ke.getKeyCode() == 40 && scene.equals("edit")){
            if(!(ke.getKeyCode()==10 && editmode.equals("delete")))if(!selectedit){
                choiceDown();
            }else subchoiceDown();
            else{
                if(!select)select = true;
                else{
                    String selectedtext="";
                    if(list.size()==1){
                        selectedtext =list.get(0);
                        try {
                            connect.deleteRow(selectedtext);
                        } catch (SQLException ex) {}

                        editmode = "";
                        click = false;
                        return;
                    }else if(cusor>0){
                        selectedtext = list.get(list.size()-cusor-1);
                        cusor--;
                        mainField.setText(list.get(list.size()-1-cusor));
                    }else{
                        selectedtext = list.get(list.size()-cusor-1);
                        mainField.setText(list.get(list.size()-1-cusor));
                        choiceUp();
                        cusor--;
                    }
                    try {
                        connect.deleteRow(selectedtext);
                        list = connect.getOrder(secondinput, "GenericName", "pharmacyinformation", "GenericName", false);
                        editmode="delete";
                    } catch (SQLException ex){}

                    select=false;
                }
                click=false;
            }
        }
    }
    public void choiceUp(){
        select=false;
       if(cusor<list.size()-1){
            list.set(list.size()-1-cusor, mainField.getText());
            cusor++;
            choicetargrt.setLocation(choicetargrt.x, choicetargrt.y+54);
            mainField.setText(list.get(list.size()-1-cusor));
            if(!login || editmode.equals("add"))mainField.selectAll();
        }
        selectortarget.setLocation(mainField.getText().length()>11?(mainField.getText().length()<22?-(mainField.getText().length()-11)*15-(select?30:0):-(11)*15)-(select?30:0):0-(select?30:0),0);
    }
    public void choiceDown(){
        select=false;
        mainField.selectAll();
            try {
                requestData();
            } catch (SQLException ex) {
                Logger.getLogger(component.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(scene.equals("edit")){
                if(editmode.equals("add")){
                    if(cusor==0 && Pattern.matches("[1-3]",list.get(10))){
                        try {
                            connect.insertRow(list.get(0), list.get(1), list.get(4), list.get(5), list.get(6), list.get(7), list.get(8), list.get(9), list.get(2), list.get(3), Integer.valueOf(list.get(10)));
                            //insertRow(String GenericName, String TradeName, String Preg, String Indication, String AdultDose, String Precaution, String ADR, String AdditionalInformation, String Group, String CommonName, int Caution)
                            editmode = "";
                            list = new ArrayList(Arrays.asList("GenericName","TradeName","Group","CommonName","Preg","Indication","DultDose","Precaution","ADR","AdditionInformation","Caution"));
                            cusor=list.size()-1;
                            mainField.setText(list.get(list.size()-cusor-1));
                            mainField.selectAll();
                            choicetargrt.setLocation(0, HEIGHT/2-25);
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }else if(cusor==0){
                        bar_alert.reset();
                        bar_alert.setSpeed(0.02);
                        alertmes = "INVALID INPUT";
                        alert=true;
                    }
                    
                }
                
                if (!login) {
                    if (list.get(0).equals("a") && list.get(1).equals("a")){
                        try {
                            list = connect.getGenericName("","GenericName", "pharmacyinformation", false);
                        } catch (SQLException ex) {}
                        login = true;
                    }
                     else if (!login && cusor == 0) {
                        bar_alert.reset();
                        bar_alert.setSpeed(0.5);
                        alertmes = "WRONG USER/PASSWORD";
                        alert = true;
                    }
                }
                if (cusor != 0) {
                    if(!login)list.set(list.size() - 1 - cusor, mainField.getText());
                        cusor--;
                        choicetargrt.setLocation(choicetargrt.x, choicetargrt.y - 54);
                        mainField.setText(list.get(list.size() - 1 - cusor));
                        if (!login || editmode.equals("add")) {
                            mainField.selectAll();
                    }
                }
            }
        if(!alert)selectortarget.setLocation(mainField.getText().length()>11?(mainField.getText().length()<22?-(mainField.getText().length()-11)*15-(select?30:0):-(11)*15)-(select?30:0):0-(select?30:0),0);
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if(scene.equals("singlepic")){
            if(mwe.getPreciseWheelRotation()<0 ){
            if(showoffset<0)showoffset+=10*mwe.getScrollAmount();
            }
            else if(showoffset>=-show.getHeight()/3)showoffset-=10*mwe.getScrollAmount();
            if(showoffset==0)
                target.setLocation(0, -200);
            else target.setLocation(0, -350);
        }
        if(scene.equals("multiplepic")){
            if(mwe.getPreciseWheelRotation()<0 ){
            if(showoffset<0)showoffset+=10*mwe.getScrollAmount();
            }
            else if(showoffset-450 > -54*list.size())showoffset-=10*mwe.getScrollAmount();

            if(showoffset>=0)
                target.setLocation(0, -100);
            else target.setLocation(0, -200);
        }
        if(scene.equals("edit")){
            if(mwe.getPreciseWheelRotation()<0){
                if((!editmode.equals("")||!login)&&!selectedit)choiceUp();
            }else{
                if((!editmode.equals("")||!login)&&!selectedit)choiceDown();
            }
        }if(selectedit){
            if(mwe.getPreciseWheelRotation()<0){
                subchoiceUp();
            }else subchoiceDown();
        }

        //System.out.println(choicetargrt.x+" "+choicetargrt.y+" "+cusor);
    }
    public void subchoiceDown(){
        mainField.selectAll();
            if(scene.equals("edit")){
                if(editmode.equals("edit")){
                    if(scusor==0 && Pattern.matches("[1-3]",detaillist.get(10).get(0))){
                        try {
                            System.out.println(detaillist.get(10).get(0));
                            detaillist.get(detaillist.size() - 1 - scusor).set(0, mainField.getText());
                            if (Pattern.matches("[1-3]",detaillist.get(10).get(0))) {
                                scusor = detaillist.size() - 1;
                                subchoicetarget.setLocation(0, HEIGHT / 2 - 25);
                                connect.updateRow(detaillist.get(0).get(0), detaillist.get(1).get(0), detaillist.get(4).get(0), detaillist.get(5).get(0), detaillist.get(6).get(0), detaillist.get(7).get(0), detaillist.get(8).get(0), detaillist.get(9).get(0), detaillist.get(2).get(0), detaillist.get(3).get(0), Integer.valueOf(detaillist.get(10).get(0)));
                                selectedit = false;
                            }else if(scusor==0){
                                bar_alert.reset();
                                bar_alert.setSpeed(0.02);
                                alertmes = "INVALID INPUT";
                                alert=true;
                                detaillist.get(detaillist.size() - 1 - scusor).set(0, mainField.getText());
                            }
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                if (scusor != 0) {
                    detaillist.get(detaillist.size() - 1 - scusor).set(0, mainField.getText());
                    scusor--;
                    subchoicetarget.setLocation(subchoicetarget.x, subchoicetarget.y - 54);
                    mainField.setText(detaillist.get(detaillist.size() - 1 - scusor).get(0));
                    mainField.selectAll();
                }
            }
        if(!alert)subselectortarget.setLocation(mainField.getText().length()>11?(mainField.getText().length()<22?-(mainField.getText().length()-11)*15:-(11)*15):0,0);
    }
    public void subchoiceUp(){
        if(scusor<detaillist.size()-1){
            detaillist.get(detaillist.size()-1-scusor).set(0, mainField.getText());
            scusor++;
            subchoicetarget.setLocation(subchoicetarget.x, subchoicetarget.y+54);
            mainField.setText(detaillist.get(detaillist.size()-1-scusor).get(0));
            mainField.selectAll();
        }
        if(!alert)subselectortarget.setLocation(mainField.getText().length()>11?(mainField.getText().length()<22?-(mainField.getText().length()-11)*15:-(11)*15):0,0);
    }
    
    public void listClick() throws SQLException{
        if(!list.isEmpty()){
            if(connect.getOrder(mainField.getText(), "TradeName", "pharmacyinformation", "TradeName", false).size()>=1 && connect.getOrder(mainField.getText(), "TradeName", "pharmacyinformation", "TradeName", false).get(0).split(",").length>0){
                show = connect.getDetail(isover.substring(isover.indexOf("(")+1,isover.indexOf(")") ));
            }else show = connect.getDetail(isover);
            toSingle();
        }
    }
    @Override
    public void mouseClicked(MouseEvent me) {
        try {
            if (scene.equals("multiplepic")) {
                for (int index = 0; index < list.size(); index++) {
                    if (mouserect.intersects(new Rectangle(275, 252 + 54 * index + (int) showoffset, 450, 50))) {
                        if (!trade) {
                            isover = (list.get(index));
                            listClick();
                        }else{
                            isover = (temp.get(index));
                        }
                        if (!list.isEmpty()) {
                            listClick();
                            mainField.setText(list.get(index));
                        }
                        mainField.selectAll();
                        //System.out.println(temp.get(index));
                    }
                }
            }
            if(mouserect.intersects(new Rectangle(WIDTH-60,HEIGHT-60,add_btn.width,add_btn.height))){
                //scene = scene.equals("edit")?"input":scene.equals("input")?"edit":scene;
                if(scene.equals("edit")){
                    editmode="";
                    scene = "input";
                    mainField.setText("");
                    select = false;
                    selectedit = false;
                    try {
                        list = connect.getGenericName("","GenericName", "pharmacyinformation", false);
                    } catch (SQLException ex) {}
                }else{
                    if(scene.equals("input")){
                        if(!login)list = new ArrayList(Arrays.asList("USER","PASSWORD"));
                        cusor=list.size()-1;
                        scene = "edit";
                        if(list.size()!=0)mainField.setText(list.get(list.size()-1-cusor));
                        mainField.selectAll();
                        choicetargrt.setLocation(0, HEIGHT/2-25);
                    }

                }
            }
            if(mouserect.intersects(search_btn_rect))
                requestData();
            //System.out.println(me.getPoint().x+" "+me.getPoint().y);
        } catch (SQLException ex) {
            Logger.getLogger(component.class.getName()).log(Level.SEVERE, null, ex);
        }
        selectortarget.setLocation(mainField.getText().length()>11?(mainField.getText().length()<22?-(mainField.getText().length()-11)*15-(select?30:0):-(11)*15)-(select?30:0):0-(select?30:0),0);
    }
    @Override
    public void mousePressed(MouseEvent me) {
        click = true;
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        click = false;
    }
    @Override
    public void mouseEntered(MouseEvent me) {
    }
    @Override
    public void mouseExited(MouseEvent me) {
        //target.setLocation(0, 0);
    }


    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        mouserect = new Rectangle(me.getX()-5,me.getY(),5,5);
        //menutarget = me.getPoint();
        //System.out.println(me.getSource().getClass());
    }
}

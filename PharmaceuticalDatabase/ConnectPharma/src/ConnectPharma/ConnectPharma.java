package ConnectPharma;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ConnectPharma {
    
    private static Connection con = null;
    //private static int MaxD = 0;
    
    public ConnectPharma() throws SQLException {
        getConnect();
    }
    
    public static void main(String[] args) throws SQLException {}
    
    public static void getConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/PharmaDatabase?useUnicode=true&characterEncoding=utf-8", "root","Kn5933609023");
            //mysql_query("SET NAMES UTF8");
        } catch(ClassNotFoundException e) {System.out.println(e.getMessage());}
          catch(SQLException e) {System.out.println(e.getMessage());}
    }
    
    public BufferedImage getDetail(String input) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rec;
        if(!input.equals(",")) rec = s.executeQuery("SELECT * FROM PharmacyInformation WHERE GenericName LIKE '%" + input + "%' ");
        else return null;
        String det = null;
        while((rec != null) && (rec.next())) det = Integer.valueOf(rec.getString("Detail"))>84?"0":rec.getString("Detail");
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("medpic/" + det + ".jpg"));
        } catch (IOException e) {System.out.println(e.getMessage());}
        s.close();
        return img;
    }
    
    public ArrayList<String> getGenericName(String input, String column, String table, boolean fix) throws SQLException {
        Statement s = con.createStatement();
        ArrayList<String> output = new ArrayList<String>();
        if(!fix) {
            ResultSet rec = s.executeQuery("SELECT * FROM " + table + " WHERE " + column + " LIKE '%" + input + "%'");
            while(rec.next()) output.add(rec.getString("GenericName"));
        }
        else {
            ResultSet rec = s.executeQuery("SELECT * FROM " + table + " WHERE " + column + " = '" + input + "'");
            while(rec.next()) output.add(rec.getString("GenericName"));
        }
        s.close();
        return output;
    }
    
    public ArrayList<String> getOrder(String input, String column, String table, String selectCol, boolean fix) throws SQLException {   //want to fix : fix = true
        Statement s = con.createStatement();
        ArrayList<String> output = new ArrayList<String>();
        if(!fix) {
            ResultSet rec = s.executeQuery("SELECT * FROM " + table + " WHERE " + column + " LIKE '%" + input + "%'");
            while(rec.next()) output.add(rec.getString(selectCol));
        }
        else {
            ResultSet rec = s.executeQuery("SELECT * FROM " + table + " WHERE " + column + " = '" + input + "'");
            while(rec.next()) output.add(rec.getString(selectCol));
        }
        s.close();
        return output;
    }
    
    public void insertRow(String GenericName, String TradeName, String Preg, String Indication, String AdultDose, String Precaution, String ADR, String AdditionalInformation, String Group, String CommonName, int Caution) throws SQLException {
        Statement s1 = con.createStatement();
        ResultSet rec1 = s1.executeQuery("SELECT DISTINCT MAX(Detail) FROM PharmacyInformation");
        int MaxD = 0;
        while((rec1 != null) && (rec1.next())) MaxD = rec1.getInt("MAX(Detail)");
        s1.close();
        if(getGenericName(GenericName, "GenericName", "PharmacyInformation", true).isEmpty()) {
            Statement s2 = con.createStatement();
            s2.executeUpdate("INSERT INTO PharmacyInformation VALUES ('" + GenericName + "','" + TradeName + "','" + Preg + "','" + Indication + "','" + AdultDose + "','" + Precaution + "','" + ADR + "','" + AdditionalInformation + "', " + (MaxD + 1) + ")");
            s2.close();
            System.out.println("Insert GenericName");
            if(getOrder(Group, "Groups", "GroupRelation", "Groups", true).isEmpty()) {
                Statement s3 = con.createStatement();
                s3.executeUpdate("INSERT INTO GroupRelation VALUES ('" + Group + "','" + GenericName + "')");
                s3.close();
                System.out.println("Insert Group");
            }
            else if(!getOrder(Group, "Groups", "GroupRelation", "Groups", true).isEmpty()) {
                Statement s4 = con.createStatement();
                String tmp = null; ArrayList<String> output = new ArrayList<String>();
                output = getGenericName(Group, "Groups", "GroupRelation", true);
                for(int i = 0; i < output.size(); i++) tmp = output.get(i);
                if(tmp == null) s4.executeUpdate("UPDATE GroupRelation SET GenericName = '" + GenericName + "' WHERE Groups = '" + Group + "'");
                else if(tmp != null) s4.executeUpdate("UPDATE GroupRelation SET GenericName = '" + tmp + "," + GenericName + "' WHERE Groups = '" + Group + "'");
                s4.close();
                System.out.println("Update Group");
            }
            if(getOrder(CommonName, "CommonName", "CommonGroupRelation", "CommonName", true).isEmpty()) {
                Statement s6 = con.createStatement();
                s6.executeUpdate("INSERT INTO CommonGroupRelation VALUES ('" + CommonName + "','" + Group + "')");
                s6.close();
                System.out.println("Insert CommonName");
            }
            else if(!getOrder(CommonName, "CommonName", "CommonGroupRelation", "CommonName", true).isEmpty()) {
                Statement st = con.createStatement();
                String tmp = getOrder(CommonName, "CommonName", "CommonGroupRelation", "Groups", true).get(0);
                Statement s7 = con.createStatement();
                if(tmp != null && !getOrder(tmp, "Groups", "CommonGroupRelation", "Groups", false).isEmpty()) {
                    if(getOrder(Group, "Groups", "CommonGroupRelation", "Groups", false).isEmpty()) s7.executeUpdate("UPDATE CommonGroupRelation SET Groups = '" + tmp + "," + Group + "' WHERE CommonName = '" + CommonName + "'");
                }
                else s7.executeUpdate("UPDATE CommonGroupRelation SET Groups = '" + Group + "' WHERE CommonName = '" + CommonName + "'");
                s7.close(); st.close();
                System.out.println("Update CommonName");
            }
            if(getOrder(TradeName, "TradeName", "TradeRelation", "TradeName", true).isEmpty()) {
                Statement s8 = con.createStatement();
                s8.executeUpdate("INSERT INTO TradeRelation VALUES ('" + TradeName + "','" + GenericName + "')");
                s8.close();
                System.out.println("Insert Trade");
            } 
            if(Caution == 1) {
                Statement s9 = con.createStatement();
                String tmp = null; ArrayList<String> output = new ArrayList<String>();
                output = getGenericName("จำหน่ายตามแพทย์สั่งขายตามหมอสั่งจำหน่ายตามหมอสั่งขายตามใบรับรองแพทย์จำหน่ายตามใบรับรองแพทย์", "Caution", "UseRelation", true);
                for(int i = 0; i < output.size(); i++) tmp = output.get(i);
                s9.executeUpdate("UPDATE UseRelation SET GenericName = '" + tmp + "," + GenericName + "' WHERE Caution = 'จำหน่ายตามแพทย์สั่งขายตามหมอสั่งจำหน่ายตามหมอสั่งขายตามใบรับรองแพทย์จำหน่ายตามใบรับรองแพทย์'");
                s9.close();
                System.out.println("Insert Generic to Caution Type 1");
            }
            if(Caution == 2) {
                Statement s9 = con.createStatement();
                String tmp = null; ArrayList<String> output = new ArrayList<String>();
                output = getGenericName("จำหน่ายได้ทั่วไปตามร้านขายยาหาซื้อได้ทั่วไปตามร้านขายยามีขายได้ทั่วไปตามร้านขายยา", "Caution", "UseRelation", true);
                for(int i = 0; i < output.size(); i++) tmp = output.get(i);
                s9.executeUpdate("UPDATE UseRelation SET GenericName = '" + tmp + "," + GenericName + "' WHERE Caution = 'จำหน่ายได้ทั่วไปตามร้านขายยาหาซื้อได้ทั่วไปตามร้านขายยามีขายได้ทั่วไปตามร้านขายยา'");
                s9.close();
                System.out.println("Insert Generic to Caution Type 2");
            }
            if(Caution == 3) {
                Statement s9 = con.createStatement();
                String tmp = null; ArrayList<String> output = new ArrayList<String>();
                output = getGenericName("ไม่มีจำหน่ายวางจำหน่ายไม่มีขายไม่ขาย", "Caution", "UseRelation", true);
                for(int i = 0; i < output.size(); i++) tmp = output.get(i);
                s9.executeUpdate("UPDATE UseRelation SET GenericName = '" + tmp + "," + GenericName + "' WHERE Caution = 'ไม่มีจำหน่ายวางจำหน่ายไม่มีขายไม่ขาย'");
                s9.close();
                System.out.println("Insert Generic to Caution Type 3");
            }
        }
    }
    
    public void updateRow(String GenericName, String TradeName, String Preg, String Indication, String AdultDose, String Precaution, String ADR, String AdditionalInformation, String Group, String CommonName, int Caution) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rec = null;
        if(!GenericName.equals(",")) rec = s.executeQuery("SELECT * FROM PharmacyInformation WHERE GenericName = '" + GenericName + "'");
        int det = 0;
        while((rec != null) && (rec.next())) det = rec.getInt("Detail");
        s.close();
        deleteRow(GenericName);
        insertRow(GenericName, TradeName, Preg, Indication, AdultDose, Precaution, ADR, AdditionalInformation, Group, CommonName, Caution);
        Statement s1 = con.createStatement();
        s1.executeUpdate("UPDATE PharmacyInformation SET Detail = " + det + " WHERE GenericName = '" + GenericName + "'");
        s1.close();
    }
    
    public void deleteRow(String GenericName) throws SQLException {
        if(!getGenericName(GenericName, "GenericName", "PharmacyInformation", true).isEmpty()) {
            String Group = getOrder(GenericName, "GenericName", "GroupRelation", "Groups", false).get(0);
            Statement s1 = con.createStatement();
            String tmp = null;
            ArrayList<String> output = new ArrayList<String>(Arrays.asList(getGenericName(GenericName, "GenericName", "GroupRelation", false).get(0).split(",")));
            for(int i = 0; i < output.size(); i++) {
                if(!output.get(i).equals(GenericName)) {
                    if(i == 0) tmp = output.get(i);
                    else if(i < output.size() && i > 0 && tmp == null) tmp = output.get(i);
                    else if(i < output.size() && i > 0) tmp = tmp + "," + output.get(i);
                }
            }
            s1.executeUpdate("UPDATE GroupRelation SET GenericName = '" + tmp + "' WHERE Groups = '" + Group + "'");
            s1.close();
            if(getOrder(Group, "Groups", "GroupRelation", "GenericName", false).get(0).equals("null")) {
                Statement s4 = con.createStatement();
                s4.executeUpdate("DELETE FROM GroupRelation WHERE Groups = '" + Group + "'");
                s4.close();
            }
            if(getOrder(Group, "Groups", "GroupRelation", "Groups", true).isEmpty()) {
                String CommonName = getOrder(Group, "Groups", "CommonGroupRelation", "CommonName", false).get(0);
                Statement s5 = con.createStatement();
                String tmp2 = null;
                ArrayList<String> output2 = new ArrayList<String>(Arrays.asList(getOrder(Group, "Groups", "CommonGroupRelation", "Groups", false).get(0).split(",")));
                for(int i = 0; i < output2.size(); i++) {
                    if(!output2.get(i).equals(Group) && getOrder(Group, "Groups", "GroupRelation", "Groups", true).isEmpty()) {
                        if(i == 0) tmp2 = output2.get(i);
                        else if(i < output2.size() && i > 0 && tmp2 == null) tmp2 = output2.get(i);
                        else if(i < output2.size() && i > 0) tmp2 = tmp2 + "," + output2.get(i);
                    }
                }
                s5.executeUpdate("UPDATE CommonGroupRelation SET Groups = '" + tmp2 + "' WHERE CommonName = '" + CommonName + "'");
                s5.close();
                if(getOrder(CommonName, "CommonName", "CommonGroupRelation", "Groups", false).get(0).equals("null")) {
                    Statement s6 = con.createStatement();
                    s6.executeUpdate("DELETE FROM CommonGroupRelation WHERE CommonName = '" + CommonName + "'");
                    s6.close();
                }
            }
            Statement s2 = con.createStatement();
            s2.executeUpdate("DELETE FROM TradeRelation WHERE GenericName = '" + GenericName + "'");
            s2.close();
            String Caution = getOrder(GenericName, "GenericName", "UseRelation", "Caution", false).get(0);
            Statement s3 = con.createStatement();
            String tmp1 = null;
            ArrayList<String> output1 = new ArrayList<String>(Arrays.asList(getGenericName(GenericName, "GenericName", "UseRelation", false).get(0).split(",")));
            for(int i = 0; i < output1.size(); i++) {
                if(!output1.get(i).equals(GenericName)) {
                    if(i == 0) tmp1 = output1.get(i);
                    else if(i < output1.size() && i > 0) tmp1 = tmp1 + "," + output1.get(i);
                }
            }
            s3.executeUpdate("UPDATE UseRelation SET GenericName = '" + tmp1 + "' WHERE Caution = '" + Caution + "'");
            s3.close();
            Statement s = con.createStatement();
            s.executeUpdate("DELETE FROM PharmacyInformation WHERE GenericName = '" + GenericName + "'");
            s.close();
        }
    }
    
    public ArrayList<String> CommonToGeneric(String CommonName) throws SQLException {
        ArrayList<String> group = new ArrayList<String>();
        ArrayList<String> generic = new ArrayList<String>();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM CommonGroupRelation WHERE CommonName LIKE '%" + CommonName + "%'");
        ResultSet rec1 = ps.executeQuery();
        while(rec1.next()) {
            ArrayList<String> unit = new ArrayList<String>(Arrays.asList(rec1.getString("Groups").split(",")));
            group.addAll(unit);
        }
        ps.close();
        for(int i = 0; i < group.size(); i ++) generic.addAll(getGenericName(group.get(i), "Groups", "GroupRelation", true));
        return generic;
    }
    
    public ArrayList<String> CautionToGeneric(String Caution) throws SQLException {
        Statement s = con.createStatement();
        ResultSet rec = s.executeQuery("SELECT * FROM UseRelation WHERE Caution LIKE '%" + Caution + "%'");
        ArrayList<String> generic = new ArrayList();
        while(rec.next()) {
            ArrayList<String> tmp = new ArrayList<String>(Arrays.asList(rec.getString("GenericName").split(",")));
            generic.addAll(tmp);
        }
        s.close();
        return generic;
    }
    
    public String[] TradeToGeneric(String TradeName) throws SQLException {
        String[] output = new String[2];
        output[0] = TradeName;
        output[1] = getGenericName(TradeName, "TradeName", "TradeRelation", false).get(0);
        return output;
    }
    
    public ArrayList<ArrayList<String>> getAllData(String GenericName) throws SQLException {
        ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
        if(!getGenericName(GenericName, "GenericName", "PharmacyInformation", true).isEmpty()) {
            ArrayList<String> tmp1 = new ArrayList<String>(); tmp1.add(GenericName); table.add(tmp1);  //index 0 is GenericName
            table.add(getOrder(GenericName, "GenericName", "PharmacyInformation", "TradeName", true));    //index 1 is TradeName
            table.add(getOrder(GenericName, "GenericName", "GroupRelation", "Groups", false));             //index 2 is Group
            table.add(getOrder(getOrder(GenericName, "GenericName", "GroupRelation", "Groups", false).get(0), "Groups", "CommonGroupRelation", "CommonName", false));  //index 3 is CommonName
            table.add(getOrder(GenericName, "GenericName", "PharmacyInformation", "Preg", true));         //index 4 is Preg
            table.add(getOrder(GenericName, "GenericName", "PharmacyInformation", "Indication", true));   //index 5 is Indication
            table.add(getOrder(GenericName, "GenericName", "PharmacyInformation", "AdultDose", true));    //index 6 is DultDose
            table.add(getOrder(GenericName, "GenericName", "PharmacyInformation", "Precaution", true));   //index 7 is Precaution
            table.add(getOrder(GenericName, "GenericName", "PharmacyInformation", "ADR", true));          //index 8 is ADR
            table.add(getOrder(GenericName, "GenericName", "PharmacyInformation", "AdditionalInformation", true));  //index 9 is AdditionInformation
            table.add(getOrder("Furosemide", "GenericName", "UseRelation", "Caution", false));             //index 10 is Caution
            return table;
        }
        else return null;
    }
    
    public void closeConnection() throws SQLException {
        con.close();
    }
    
}

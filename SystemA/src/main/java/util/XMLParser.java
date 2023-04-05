package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.systemA.AConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

public class XMLParser {
    public void testParser(){
        Connection con = AConnection.getConnection();
        PreparedStatement ps = null;
        int rs = 0;
        Document doc = null;
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(new FileReader(new File("src/main/java/schema/choiceA.xml")));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Element foo = (Element) i.next();
            Vector<String> employee = new Vector<String>();
            for (Iterator j = foo.elementIterator(); j.hasNext();) {
                Element tmp = (Element) j.next();
                employee.add(tmp.getStringValue());
            }
            Vector<String> allName = new Vector<String>();
            String sql = "insert into 选课 values('"+ employee.elementAt(0);
            for (int k = 1; k < employee.size(); k++)
                sql += "','" + employee.elementAt(k);
            sql += "')";
            try {
                ps = con.prepareStatement(sql);
                rs = ps.executeUpdate();
            } catch (SQLException throwables) {
                System.out.println(sql);
                throwables.printStackTrace();
            }
        }
    }
}

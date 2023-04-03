package transform;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
 
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import util.Content;

public class Trans {
    public static Content doXsl(String xslPath,String xmlPath){
        Content content=new Content();
        try {
            ByteArrayOutputStream byteRep = new ByteArrayOutputStream();
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				StreamSource source = new StreamSource(xmlPath);
				StreamResult result = new StreamResult(byteRep);
				StreamSource style = new StreamSource(xslPath);
				Transformer transformer = transformerFactory
						.newTransformer(style);
				transformer.setOutputProperty(
						javax.xml.transform.OutputKeys.ENCODING, "utf-8");
				transformer.transform(source, result);
				content.setFormUrl(xmlPath);
				content.setToContent(byteRep.toString());
				content.setTranformUrl(xslPath);
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return content;
    }
    public static void outPut(Content content,String filePath){
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter(filePath));
            System.out.println(content.getToContent());
            bw.write(content.getToContent());
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

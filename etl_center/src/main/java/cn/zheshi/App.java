package cn.zheshi;

import cn.zheshi.trans.Trans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@SpringBootApplication
public class App {
    private static String outPath="src/main/java/schema/f_class.xml";
    private static String fromPath="src/main/java/schema/classA.xml";
    private static String xslPath="src/main/java/schema/formatClass.xsl";
    public static void main(String[] args) {
//        File f=new File(fromPath);
//        BufferedReader reader=null;
//        StringBuilder content=new StringBuilder();
//        try {
//            reader=new BufferedReader(new FileReader(f));
//            String line=reader.readLine();
//            while (line!=null){
//                content.append(line);
//                line=reader.readLine();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(String.valueOf(content));
//        Trans.outPut(Trans.doXsl(xslPath, String.valueOf(content)),outPath);
        SpringApplication.run(App.class, args);
    }
}
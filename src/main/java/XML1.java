import util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;

public class XML1 {
    public static void main(String[] args) {
        String[] names={"张三","李四","王五","赵四","皮卡丘"};
        String[] births={"2001-01-01","2001-02-02","2001-03-03","2001-04-04","2001-05-05"};
        String[] scoreTypes={"平时成绩","期末成绩","总评成绩"};
        ArrayList<Student> students=new ArrayList<Student>();
        for(int i=0;i<names.length;++i){
            StudentPersonInfo personInfo=new StudentPersonInfo(names[i],"男",births[i],"njuse");
            ArrayList<Score> scores=new ArrayList<Score>();
            for(int k=0;k<5;++k){
                ArrayList<AScore> aScores=new ArrayList<AScore>();
                for(int j=0;j<scoreTypes.length;++j){
                    AScore aScore=new AScore(scoreTypes[j], (int) (Math.random()*100));
                    aScores.add(aScore);
                }
                Score score=new Score("0000"+k,aScores);
                scores.add(score);
            }
            Student student=new Student(191251001+i,personInfo,scores);
            students.add(student);
        }
        AllStudents allStudents=new AllStudents(students);
        try {

            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(AllStudents.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // for getting nice formatted output
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // specify the location and name of xml file to be created
            File XMLfile = new File(".\\xml1.xml");

            // Writing to XML file
            jaxbMarshaller.marshal(allStudents, XMLfile);
            // Writing to console
            jaxbMarshaller.marshal(allStudents, System.out);

        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }

    }
}

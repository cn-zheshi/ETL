import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import util.*;

public class Main {
    public static void main(String[] args) {

        StudentPersonInfo personInfo=new StudentPersonInfo("姓名","男","20011202","njuse");
        ArrayList<Score> scores=new ArrayList<Score>();
        AScore aScore=new AScore("平时成绩",10);
        ArrayList<AScore> aScores=new ArrayList<AScore>();
        aScores.add(aScore);
        Score score=new Score("10",aScores);
        scores.add(score);
        Student student=new Student(1,personInfo,scores);
        ArrayList<Student> students=new ArrayList<Student>();
        students.add(student);
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
            System.out.println(student);
            e.printStackTrace();
        }

    }
}
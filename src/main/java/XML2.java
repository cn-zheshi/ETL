
import util.*;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XML2 {
    public static void main(String[] args) {

        String[] scoreTypes={"平时成绩","期末成绩","总评成绩"};
        ArrayList<CourseScore> courseScores=new ArrayList<CourseScore>();
        try {

            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(AllStudents.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File("./xml1.xml");

            // this will create Java object - country from the XML file
            AllStudents allStudents = (AllStudents) jaxbUnmarshaller.unmarshal(XMLfile);
            System.out.println(allStudents.students.get(0));
            for (int i=0;i<5;++i){
                for(int j=0;j<scoreTypes.length;++j){
                    String courseId = null;
                    ArrayList<OtherScore> otherScores=new ArrayList<OtherScore>();
                    for (Student student:allStudents.students){
                        int id=student.studentId;
                        Score s=student.studentCourseScores.get(i);
                        courseId=s.courseId;
                        AScore as=s.score.get(j);
                        OtherScore otherScore=new OtherScore(id, as.score);
                        int k=0;
                        for(k=0;k<otherScores.size();++k){
                            if(otherScore.score<otherScores.get(k).score){
                                otherScores.add(k,otherScore);
                                break;
                            }
                        }
                        if(k==otherScores.size()){
                            otherScores.add(otherScore);
                        }
                    }
                    CourseScore courseScore=new CourseScore(scoreTypes[j],courseId,otherScores);
                    courseScores.add(courseScore);
                }
            }
            ScoreList scoreList=new ScoreList(courseScores);
            jaxbContext = JAXBContext.newInstance(ScoreList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            File XML2file = new File(".\\xml2.xml");


            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // Writing to XML file
            jaxbMarshaller.marshal(scoreList, XML2file);
            // Writing to console
            jaxbMarshaller.marshal(scoreList, System.out);
        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }

    }
}
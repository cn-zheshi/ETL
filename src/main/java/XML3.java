import util.CourseScore;
import util.OtherScore;
import util.ScoreList;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XML3 {
    public static void main(String[] args) {

        try {

            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(ScoreList.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File(".\\xml2.xml");

            // this will create Java object - country from the XML file
            ScoreList scoreList = (ScoreList) jaxbUnmarshaller.unmarshal(XMLfile);

            for (int i=0;i<scoreList.courseScore.size();++i){
                CourseScore courseScore=scoreList.courseScore.get(i);
                for (int j=0;j<courseScore.scores.size();++j){
                    OtherScore otherScore=courseScore.scores.get(j);
                    if(otherScore.score>=60){
                        courseScore.scores.remove(j);
                        --j;
                    }
                }
                if(courseScore.scores.isEmpty()){
                    scoreList.courseScore.remove(i);
                    --i;
                }
            }
            File XML3file = new File(".\\xml3.xml");
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // Writing to XML file
            jaxbMarshaller.marshal(scoreList, XML3file);
            // Writing to console
            jaxbMarshaller.marshal(scoreList, System.out);
        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }

    }
}
package util;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

public class CourseScore {
    @XmlAttribute(name="成绩性质")
    public String scoreType;
    @XmlAttribute(name = "课程编号")
    public  String courseId;
    @XmlElement(name = "成绩")
    public ArrayList<OtherScore> scores;

    public CourseScore(){}

    public CourseScore(String scoreType,String courseId,ArrayList<OtherScore> scores){
        this.scoreType=scoreType;
        this.scores=scores;
        this.courseId=courseId;
    }

}

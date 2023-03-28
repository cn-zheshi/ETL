package util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "课程成绩列表")
public class ScoreList {
    @XmlElement(name = "课程成绩")
    public ArrayList<CourseScore> courseScore;
    public ScoreList(){}
    public ScoreList(ArrayList<CourseScore> courseScore){
        this.courseScore=courseScore;
    }
}

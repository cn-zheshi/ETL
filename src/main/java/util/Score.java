package util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

//@XmlRootElement(namespace = "util.Student",name = "学生成绩信息")
public class Score {
    @XmlElement(name = "课程编号")
    public String courseId;
    @XmlElement(name = "成绩")
    public ArrayList<AScore> score;
    public Score(String courseId,ArrayList<AScore> score){
        this.score=score;
        this.courseId=courseId;
    }
    public Score(){}
}

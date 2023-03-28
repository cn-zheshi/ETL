package util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

public class Student {
    @XmlElement(name = "学号")
    public int studentId;
    @XmlElement(name = "个人基本信息")
    public StudentPersonInfo studentPersonInfos;
    @XmlElementWrapper(name = "学生成绩信息集合")
    @XmlElement(name = "学生成绩信息")
    public ArrayList<Score> studentCourseScores;

    public Student(int studentId,StudentPersonInfo studentPersonInfos, ArrayList<Score> studentCourseScores) {
        this.studentId=studentId;
        this.studentPersonInfos = studentPersonInfos;
        this.studentCourseScores = studentCourseScores;
    }

    public Student() {}

    @Override
    public String toString() {
        String str=studentPersonInfos.toString();
        if(!studentCourseScores.isEmpty())
            for(Score s:studentCourseScores){
                str+=(":"+s.toString());
            }
        return str;
    }
}
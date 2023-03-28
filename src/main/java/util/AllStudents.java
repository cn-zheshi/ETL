package util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlRootElement(name = "学生信息集合")
public class AllStudents {
    @XmlElement(name = "学生信息")
    public ArrayList<Student> students;
    public AllStudents(){}
    public AllStudents(ArrayList<Student> students){
        this.students=students;
    }
}

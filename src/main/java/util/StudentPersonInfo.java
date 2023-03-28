package util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//@XmlRootElement(namespace = "util.Student",name = "学生基本信息")
public class StudentPersonInfo {
    @XmlElement(name="姓名")
    public String name;
    @XmlElement(name="性别")
    public String sex;
    @XmlElement(name="生日")
    public String birth;
    @XmlElement(name="隶属部门名称")
    public String department;

    public StudentPersonInfo(String name,String sex,String birth,String department){
        this.name=name;
        this.sex=sex;
        this.birth=birth;
        this.department=department;
    }
    public StudentPersonInfo(){}
    @Override
    public String toString() {
        return "StudentPersonInfo{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}

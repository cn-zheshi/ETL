package util;

import javax.xml.bind.annotation.XmlElement;

public class OtherScore {
    @XmlElement(name = "学号")
    public int studentId;
    @XmlElement(name = "得分")
    public int score;
    public  OtherScore(){}
    public OtherScore(int studentId,int score){
        this.studentId=studentId;
        this.score=score;
    }
}

package util;

import javax.xml.bind.annotation.XmlElement;

public class AScore {
    @XmlElement(name = "成绩性质")
    public String scoreType;
    @XmlElement(name = "分数")
    public int score;
    public AScore(String scoreType,int score){
        this.score=score;
        this.scoreType=scoreType;
    }
    public AScore(){}
}

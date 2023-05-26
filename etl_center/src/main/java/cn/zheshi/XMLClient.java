package cn.zheshi;

import cn.zheshi.net.HttpHelper;
import cn.zheshi.trans.Trans;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class XMLClient {
    private static final String basePath = "src/main/java/schema/";

    private static final String formatChoice = "formatClassChoice.xsl";
    private static final String formatClass = "formatClass.xsl";
    private static final String formatStudent = "formatStudent.xsl";
    private static final String transStudent = "studentTo";
    private static final String transClass = "classTo";
    private static final String transChoice = "choiceTo";
    private static final String suffix = ".xsl";
    //TODO:设置三个服务器的url
    private static final String serverAURL = "http://localhost:5050";
    private static final String serverBURL = "http://localhost:5051";
    private static final String serverCURL = "http://localhost:5052";
    private static final String courseSuffix = "/course";
    private static final String choiceSuffix = "/select";
    private static final String chooseSuffix = "/choose";
    private static final String dropSuffix = "/drop";
    private static final String studentSuffix = "/student";
    // 已选课程
    private static final String courseChosed = "/courseChosed";
    private static final String openCourseShare = "/openCourseShare";
    private static final String updateScore = "/updateScore";


    private String getToUrl(String to){
        String toUrl=null;
        switch (to){
            case "A": {
                toUrl = serverAURL;
                break;
            }
            case "B": {
                toUrl = serverBURL;
                break;
            }
            case "C": {
                toUrl = serverCURL;
                break;
            }
        }
        return toUrl;
    }


    @RequestMapping("/askSharingCourse")
    public String askSharingCourse(@RequestParam("from") String from,
                                   @RequestParam("to") String to){
        //请求课程信息,返回值保存在toClassXML
        String toUrl = getToUrl(to);
        toUrl=toUrl+courseSuffix;
        String toClassXML= null;
        try {
            toClassXML = HttpHelper.sendGet(toUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String formatClassXML=Trans.doXsl(basePath+formatClass, toClassXML).getToContent();
        String fromClassXML=Trans.doXsl(basePath+transClass+from+suffix,formatClassXML).getToContent();
        return fromClassXML;
    }
    // 选课请求
    @RequestMapping("/selectCourse")
    public String chooseClass(@RequestParam("from") String from,
                              @RequestParam("to") String to,
            @RequestBody String classChoice){
        // 将classChoice转为JSONObject
        JSONObject classChoiceJSON = JSONObject.parseObject(classChoice);
        String classChoiceXML = classChoiceJSON.getString("xml");
        String formatChoiceXML=Trans.doXsl(basePath+formatChoice, classChoiceXML).getToContent();
        String toChoiceXML=Trans.doXsl(basePath+transChoice+to+suffix, formatChoiceXML).getToContent();
        System.out.println(toChoiceXML);
        //向目标服务器发送选课请求,返回值保存在res
        String toUrl=getToUrl(to);
        toUrl=toUrl+chooseSuffix;
        String res= null;
        try {
            res = HttpHelper.sendPost(toUrl,toChoiceXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    // 退课请求
    @RequestMapping("/unselectCourse")
    public String dropClasss(@RequestParam("from") String from,
                             @RequestParam("to") String to,
                             @RequestBody String classChoice) {
        // 将classChoice转为JSONObject
        JSONObject classChoiceJSON = JSONObject.parseObject(classChoice);
        String classChoiceXML = classChoiceJSON.getString("xml");
        String formatChoiceXML=Trans.doXsl(basePath+formatChoice, classChoiceXML).getToContent();
        String toChoiceXML=Trans.doXsl(basePath+transChoice+to+suffix, formatChoiceXML).getToContent();
        //向目标服务器发送选课请求,返回值保存在res
        String toUrl=getToUrl(to);
        toUrl=toUrl+dropSuffix;
        String res= null;
        try {
            res = HttpHelper.sendPost(toUrl,toChoiceXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/getAllStudents")
    public String getAllStudents(@RequestParam("from") String from, @RequestParam("to") String to){
        // 获取to服务器上的所有学生信息
        String toUrl = getToUrl(to);
        toUrl = toUrl + studentSuffix;
        String toStudentXML= null;
        System.out.println("toUrl:"+toUrl);
        try {
            toStudentXML = HttpHelper.sendGet(toUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String formatStudentXML = Trans.doXsl(basePath+formatStudent, toStudentXML).getToContent();
        String fromStudentXML = Trans.doXsl(basePath+transStudent+from+suffix,formatStudentXML).getToContent();
        return fromStudentXML;
    }

    @RequestMapping("/getAllCourses")
    public String getAllCourses(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("studentNo") String studentNo){
        // 获取to服务器上的所有课程信息
        String toUrl = getToUrl(to);
        toUrl=toUrl + courseSuffix + "?studentNo=" + studentNo;
        String toChoiceXML= null;
        try {
            toChoiceXML = HttpHelper.sendGet(toUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String formatClassXML = Trans.doXsl(basePath+formatClass, toChoiceXML).getToContent();
        String fromClassXML=Trans.doXsl(basePath+transClass+from+suffix,formatClassXML).getToContent();
        return fromClassXML;
    }

    @RequestMapping("/getAllChoices")
    public String getAllChoices(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("studentNo") String studentNo) {
        // 获取这名学生在to服务器上的所有选课信息
        String toUrl = getToUrl(to);
        toUrl=toUrl + choiceSuffix + "?studentNo=" + studentNo;
        String toChoiceXML= null;
        try {
            toChoiceXML = HttpHelper.sendGet(toUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String formatChoiceXML = Trans.doXsl(basePath+formatChoice, toChoiceXML).getToContent();
        String fromChoiceXML=Trans.doXsl(basePath+transChoice+from+suffix,formatChoiceXML).getToContent();
        return fromChoiceXML;
    }

    // openShareCourse
    @RequestMapping("/openShareCourse")
    public String openShareCourse(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("courseNo") String courseNo) {
        String toUrl = getToUrl(to);
        toUrl = toUrl + openCourseShare + "?courseNo=" + courseNo;
        String res = null;
        try {
            res = HttpHelper.sendGet(toUrl);
        } catch (HttpException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    // updateScore
    @RequestMapping("/updateScore")
    public String updateScore(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("score") String score, @RequestBody String classChoice) {
        // 将classChoice转为JSONObject
        JSONObject classChoiceJSON = JSONObject.parseObject(classChoice);
        String classChoiceXML = classChoiceJSON.getString("xml");
        String formatChoiceXML=Trans.doXsl(basePath+formatChoice, classChoiceXML).getToContent();
        String toChoiceXML=Trans.doXsl(basePath+transChoice+to+suffix, formatChoiceXML).getToContent();
        //向目标服务器发送选课请求,返回值保存在res
        String toUrl=getToUrl(to);
        toUrl=toUrl+updateScore + "?score=" + score;
        System.out.println(toUrl);
        String res= null;
        try {
            res = HttpHelper.sendPost(toUrl,toChoiceXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

}

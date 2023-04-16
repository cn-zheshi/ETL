package cn.zheshi;

import cn.zheshi.net.HttpHelper;
import cn.zheshi.trans.Trans;
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
    private static final String serverBURL = "";
    private static final String serverCURL = "";
    private static final String courseSuffix = "/course";
    private static final String choiceSuffix = "/select";

    private static final String studentSuffix = "/student";

    // 已选课程
    private static final String courseChosed = "/courseChosed";


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
    @RequestMapping("/classChoice")
    public String chooseClass(@RequestParam("from") String from,
                              @RequestParam("to") String to,
            @RequestBody String classChoice){
        String formatChoiceXML=Trans.doXsl(basePath+formatChoice, classChoice).getToContent();
        String toChoiceXML=Trans.doXsl(basePath+transChoice+to+suffix, formatChoiceXML).getToContent();
        //向目标服务器发送选课请求,返回值保存在res
        String toUrl=getToUrl(to);
        toUrl=toUrl+choiceSuffix;
        String res= null;
        try {
            res = HttpHelper.sendPost(toUrl,toChoiceXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/getAllStudents")
    public String getAllStudents(){
        //TODO:请求所有学生信息
        return "test";
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
}

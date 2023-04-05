import http.MyHttpServer;
import org.systemA.Login;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        // 打开界面
        Login.main(args);
        //开启Http服务端
        MyHttpServer.main(args);
    }
}

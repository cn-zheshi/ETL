package cn.zheshi.net;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;
import org.neo4j.driver.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Neo4jUtil {
    private static Driver neo4jDriver;
    private static final Logger log = LoggerFactory.getLogger(Neo4jUtil.class);
    public static void init(String uri, String username, String password) {
        neo4jDriver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }
    /**
     * 测试neo4j连接是否打开
     */
    public static boolean isNeo4jOpen() {
        try (Session session = neo4jDriver.session()) {
            log.debug("连接成功：" + session.isOpen());
            return session.isOpen();
        }
    }
    public void close() throws Exception {
        neo4jDriver.close();
    }

    public static String generateJson() {
        return "";
    }

    public static void main(String[] args) {
//        Neo4jUtil.init("bolt://44.198.182.100:7687", "neo4j", "land-logic-basics");
//        Neo4jUtil.isNeo4jOpen();
    }

}

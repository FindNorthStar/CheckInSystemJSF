package edu.bupt.checkinsystem.util;

import java.util.HashMap;
import java.util.Map;

public class MacUtils {
    public static Map<String, String> getMacIpList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("10.125.103.169", "AAAAAAAAAAAA");
        map.put("10.125.103.17",  "BBBBBBBBBBBB");
        map.put("10.125.103.19",  "CCCCCCCCCCCC");
        map.put("10.125.103.159", "DDDDDDDDDDDD");
        map.put("127.0.0.1", "EEEEEFEEEEEE");
        return map;
    }
}

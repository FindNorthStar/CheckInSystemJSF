package edu.bupt.checkinsystem.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MacUtils {
    public static Map<String, String> getMacIpList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("10.125.103.169", "AAAAAAAAAAAA");
        map.put("10.125.103.17",  "BBBBBBBBBBBB");
        map.put("10.125.103.19",  "CCCCCCCCCCCC");
        map.put("10.125.103.159", "DDDDDDDDDDDD");
        return map;
    }
}

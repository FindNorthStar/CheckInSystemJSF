package edu.bupt.checkinsystem.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wym on 16-7-5.
 */
public class NetUtils {

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public static String getIpAddress() {
        return getRequest().getRemoteAddr();
    }

    public static String getMacAddress() {
        return getMacIpList().get(getIpAddress());
    }

    public static String getHost() {
        return getRequest().getHeader("Host");
    }

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
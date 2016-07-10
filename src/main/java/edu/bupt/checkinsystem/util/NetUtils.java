package edu.bupt.checkinsystem.util;

import org.omnifaces.util.Faces;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wym on 16-7-5.
 */
public class NetUtils {

    public static HttpServletRequest getRequest() {
        return Faces.getRequest();
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

    public static boolean isLocalHost() throws URISyntaxException {
        String host = getHost();
        URI uri = new URI("http://" + host);

        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(uri.getHost());
        } catch (UnknownHostException e) {
            return false;
        }

        return "localhost".equals(inetAddress.getHostName());
    }

    public static Map<String, String> getMacIpList() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("10.125.103.169", "AAAAAAAAAAAA");
        map.put("10.125.103.17", "BBBBBBBBBBBB");
        map.put("10.125.103.19", "CCCCCCCCCCCC");
        map.put("10.125.103.159", "DDDDDDDDDDDD");
        map.put("127.0.0.1", "EEEEEFFFEFEE");
        return map;
    }

    public static String stripMacAddress(String macAddr) {
        return macAddr.toUpperCase().replaceAll("[^A-F0-9]", "");
    }
}

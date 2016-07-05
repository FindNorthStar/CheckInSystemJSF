package edu.bupt.checkinsystem.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wym on 16-7-5.
 */
public class IpUtils {

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public static String getIpAddress() {
        return getRequest().getRemoteAddr();
    }

    public static String getMacAddress() {
        return MacUtils.getMacIpList().get(getIpAddress());
    }

    public static String getHost() {
        return getRequest().getHeader("Host");
    }
}

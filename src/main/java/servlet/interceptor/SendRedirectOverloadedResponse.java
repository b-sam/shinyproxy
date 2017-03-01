package servlet.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@SuppressWarnings("javadoc")
class SendRedirectOverloadedResponse extends HttpServletResponseWrapper {

    private HttpServletRequest m_request;
    private String prefix = null;

    public SendRedirectOverloadedResponse(HttpServletRequest inRequest, HttpServletResponse response) {
        super(response);
        this.m_request = inRequest;
        this.prefix = getPrefix(inRequest);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        System.out.println("Going originally to:" + location); //$NON-NLS-1$
        String finalurl = null;

        if (isUrlAbsolute(location)) {
            System.out.println("This url is absolute. No scheme changes will be attempted"); //$NON-NLS-1$
            finalurl = location;
        } else {
            finalurl = fixForScheme(this.prefix + location);
            System.out.println("Going to absolute url:" + finalurl); //$NON-NLS-1$
        }
        super.sendRedirect(finalurl);
    }

    public boolean isUrlAbsolute(String url) {
        String lowercaseurl = url.toLowerCase();
        if (lowercaseurl.startsWith("http") == true) { //$NON-NLS-1$
            return true;
        }
		return false;
    }

    public String fixForScheme(String url) {
        //alter the url here if you were to change the scheme
        return url;
    }

    public String getPrefix(HttpServletRequest request) {
        StringBuffer str = request.getRequestURL();
        String url = str.toString();
        String uri = request.getRequestURI();
        System.out.println("requesturl:" + url); //$NON-NLS-1$
        System.out.println("uri:" + uri); //$NON-NLS-1$
        int offset = url.indexOf(uri);
        String prefix_t = url.substring(0, offset);
        System.out.println("prefix:" + prefix_t); //$NON-NLS-1$
        return prefix_t;
    }
}
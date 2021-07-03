package org.iushu.xss.components;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * @author iuShu
 * @since 6/8/21
 */
public class SecureHttpRequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    private static Pattern[] patterns = new Pattern[] {  // patterns for avoid xss
            Pattern.compile("<script>(.*?)</script>",Pattern.CASE_INSENSITIVE),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
            Pattern.compile("</script>",Pattern.CASE_INSENSITIVE),
            Pattern.compile("<script(.*?)>",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
            Pattern.compile("eval\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
            Pattern.compile("expression\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
            Pattern.compile("javascript:",Pattern.CASE_INSENSITIVE),
            Pattern.compile("vbscript:",Pattern.CASE_INSENSITIVE),
            // available to add more patterns here ..
    };

    public SecureHttpRequestWrapper(HttpServletRequest request) {
        super(request);

        // read and handle parameter/attribute
        StringBuilder str = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            int bytes;
            char[] charBuffer = new char[128];
            while ((bytes = reader.read(charBuffer)) != -1)
                str.append(charBuffer, 0, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        body = str.toString();  // read stream as string
    }

    public String getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String encodedBody = stripXss(body);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(encodedBody.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public boolean isFinished() {
                return inputStream.available() == 0;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (ArrayUtils.isEmpty(values))
            return values;

        String[] encodedValues = new String[values.length];
        for (int i = 0; i < values.length; i++)
            encodedValues[i] = stripXss(values[i]);
        return encodedValues;
    }

    @Override
    public String getHeader(String name) {
        return stripXss(super.getHeader(name));
    }

    /**
     * filter and replace content to avoid attack
     */
    private String stripXss(String text) {
        if (StringUtils.isBlank(text))
            return text;

        text = ESAPI.encoder().canonicalize(text, false, false);
        return patternReplace(text);
    }

    private String patternReplace(String text) {
        if (StringUtils.isBlank(text))
            return text;

        text = text.replaceAll("\0", "");
        for (Pattern pattern : patterns)
            text = pattern.matcher(text).replaceAll("");
        return text;
    }

}
package io.github.lvyahui8.web.wrapper;


import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/4 23:23
 */
public class ResettableHttpServletRequest extends HttpServletRequestWrapper {

    String httpBody;

    public ResettableHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        httpBody = IOUtils.toString(request.getReader());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpBody.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return inputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(),StandardCharsets.UTF_8));
    }

    public String getHttpBody() {
        return httpBody;
    }
}

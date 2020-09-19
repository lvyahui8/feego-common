package io.github.lvyahui8.web.wrapper;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * @author yahui.lv lvyahui8@gmail.com
 * @date 2020/4/5 11:49
 */
@Slf4j
public class TeeHttpServletResponse extends HttpServletResponseWrapper {

    TeeServletOutputStream teeOutputStream;

    HttpServletResponse response;

    PrintWriter writer;

    ServletOutputStream rawOutputStream;

    public TeeHttpServletResponse(HttpServletResponse response) throws IOException {
        super(response);
        this.response = response;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (rawOutputStream == null) {
            rawOutputStream = response.getOutputStream();
            teeOutputStream = new TeeServletOutputStream(rawOutputStream);
        }

        return teeOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (rawOutputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            teeOutputStream = new TeeServletOutputStream(response.getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(teeOutputStream,response.getCharacterEncoding()),true);
        }
        return writer;
    }


    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (teeOutputStream != null) {
            teeOutputStream.flush();
        }
    }

    public String getHttpBody() throws UnsupportedEncodingException {
        return new String(teeOutputStream.getCopy(), response.getCharacterEncoding());
    }

    static class TeeServletOutputStream extends ServletOutputStream {
        ServletOutputStream outputStream;
        ByteArrayOutputStream copy;

        public TeeServletOutputStream(ServletOutputStream outputStream) {
            this.outputStream = outputStream;
            this.copy = new ByteArrayOutputStream(2048);
        }

        @Override
        public boolean isReady() {
            return outputStream.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            outputStream.setWriteListener(writeListener);
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            copy.write(b);
        }

        public byte[] getCopy() {
            return copy.toByteArray();
        }
    }
}

package com.expercise.interpreter;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;

@Service
public class JavaScriptOutputWriter extends Writer {

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        OutputMessageAggregator.append(new String(cbuf, off, len));
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

}

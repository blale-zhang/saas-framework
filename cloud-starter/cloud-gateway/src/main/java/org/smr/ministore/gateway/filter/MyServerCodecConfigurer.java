package org.smr.ministore.gateway.filter;

import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;

import java.util.List;

public class MyServerCodecConfigurer implements ServerCodecConfigurer {
    @Override
    public ServerDefaultCodecs defaultCodecs() {
        return null;
    }

    @Override
    public CustomCodecs customCodecs() {
        return null;
    }

    @Override
    public void registerDefaults(boolean b) {

    }

    @Override
    public List<HttpMessageReader<?>> getReaders() {
        return null;
    }

    @Override
    public List<HttpMessageWriter<?>> getWriters() {
        return null;
    }
}

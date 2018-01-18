package com.expercise.interpreter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InterpreterClient {

    @Value("${interpreter.api.url}")
    private String interpreterApiUrl;

    public InterpretResponse interpret(InterpretRequest interpretRequest) {
        return new RestTemplate().postForEntity(interpreterApiUrl + "/eval", interpretRequest, InterpretResponse.class).getBody();
    }
}

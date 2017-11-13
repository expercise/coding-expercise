package com.expercise.interpreter;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InterpreterClient {

    public InterpretResponse interpret(InterpretRequest interpretRequest) {
        return new RestTemplate().postForEntity("http://localhost:9119/eval", interpretRequest, InterpretResponse.class).getBody();
    }
}

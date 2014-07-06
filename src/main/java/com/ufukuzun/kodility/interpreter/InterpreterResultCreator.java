package com.ufukuzun.kodility.interpreter;

import com.ufukuzun.kodility.service.i18n.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InterpreterResultCreator {

    @Autowired
    private MessageService messageService;

    public InterpreterResult successResult() {
        return InterpreterResult.createSuccessResult();
    }

    public InterpreterResult failedResultWithoutMessage() {
        return InterpreterResult.createFailedResult();
    }

    public InterpreterResult noResultFailedResult() {
        InterpreterResult failedResult = failedResultWithoutMessage();
        failedResult.setResult(messageService.getMessage("interpreter.noResult"));
        return failedResult;
    }

    public InterpreterResult syntaxErrorFailedResult() {
        InterpreterResult failedResult = failedResultWithoutMessage();
        failedResult.setResult(messageService.getMessage("interpreter.syntaxError"));
        return failedResult;
    }

}

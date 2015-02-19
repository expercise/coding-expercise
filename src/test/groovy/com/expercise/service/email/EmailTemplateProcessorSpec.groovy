package com.expercise.service.email

import com.expercise.service.i18n.MessageService
import org.thymeleaf.context.Context
import spock.lang.Specification

class EmailTemplateProcessorSpec extends Specification {

    EmailTemplateProcessor processor

    TemplateEngineWrapper templateEngineWrapper = Mock()
    MessageService messageService = Mock()

    Context contextArgumentCaptor

    def setup() {
        processor = new EmailTemplateProcessor(templateEngineWrapper: templateEngineWrapper, messageService: messageService)
    }

    def "should create multilingual email from proper parameters"() {
        when:
        String emailContent = processor.createEmail("contentFileName", ["paramKey": "paramValue"])

        then:
        emailContent == "email content with paramValue"

        and: "send right parameters to the template engine"
        1 * templateEngineWrapper.process("contentFileName", {
            contextArgumentCaptor = it
        } as Context) >> "email content with paramValue"
        contextArgumentCaptor.getVariables().get("paramKey") == "paramValue"
        contextArgumentCaptor.getVariables().get("msg") == messageService
    }

}

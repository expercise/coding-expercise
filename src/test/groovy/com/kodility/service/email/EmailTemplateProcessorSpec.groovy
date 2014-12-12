package com.kodility.service.email

import org.thymeleaf.context.Context
import spock.lang.Specification

class EmailTemplateProcessorSpec extends Specification {

    private EmailTemplateProcessor processor

    private TemplateEngineWrapper templateEngineWrapper = Mock()

    private Context contextArgumentCaptor

    def setup() {
        processor = new EmailTemplateProcessor(templateEngineWrapper: templateEngineWrapper)
    }

    def "should create multilingual email from proper parameters"() {
        when:
        String emailContent = processor.createEmail("contentFileName", ["paramKey":"paramValue"])
        then:
        emailContent == "email content with paramValue"
        and: "send right parameters to the template engine"
        1 * templateEngineWrapper.process("contentFileName", {contextArgumentCaptor = it}) >> "email content with paramValue"
        contextArgumentCaptor.getVariables().get("paramKey") == "paramValue"
    }

}

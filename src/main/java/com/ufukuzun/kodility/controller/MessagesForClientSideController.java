package com.ufukuzun.kodility.controller;

import com.ufukuzun.kodility.controller.utils.BrowserCacheableContent;
import com.ufukuzun.kodility.service.i18n.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/generatedResources/js")
public class MessagesForClientSideController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/messages_{lingo}-{buildId}.js", produces = "application/javascript; charset=utf-8")
    @ResponseBody
    public String getMessages(HttpServletRequest request, HttpServletResponse response) {
        return new BrowserCacheableContent() {
            @Override
            public String generateContent() {
                return prepareMessagesForClientSide();
            }
        }.getContent(request, response);
    }

    private String prepareMessagesForClientSide() {
        List<String> keyValuePairsAsList = messageService.getAllMessages().entrySet().stream()
                .map(e -> String.format("\"%s\": \"%s\"", e.getKey(), e.getValue().replace("\"", "\\\"")))
                .collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var messages = {\n\t");
        stringBuilder.append(StringUtils.join(keyValuePairsAsList, ",\n\t"));
        stringBuilder.append("\n};");

        return stringBuilder.toString();
    }

}

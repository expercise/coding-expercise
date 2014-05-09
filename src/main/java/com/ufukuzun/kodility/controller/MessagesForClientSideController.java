package com.ufukuzun.kodility.controller;

import com.ufukuzun.kodility.service.i18n.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/js")
public class MessagesForClientSideController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/messages.js", produces = "application/javascript; charset=utf-8")
    @ResponseBody
    public String getMessages() {   // TODO ufuk: performance ? caching ? not modified response ?
        return prepareMessagesForClientSide();
    }

    private String prepareMessagesForClientSide() {
        Map<String, String> allMessages = messageService.getAllMessages();

        List<String> keyValuePairsAsList = new ArrayList<>();
        for (Map.Entry<String, String> entry : allMessages.entrySet()) {
            keyValuePairsAsList.add(String.format("\"%s\": \"%s\"", entry.getKey(), entry.getValue().replace("\"", "\\\"")));
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var messages = {");
        stringBuilder.append(StringUtils.join(keyValuePairsAsList, ",\n"));
        stringBuilder.append("};");

        return stringBuilder.toString();
    }

}

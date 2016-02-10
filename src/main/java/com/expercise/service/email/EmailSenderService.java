package com.expercise.service.email;

import com.expercise.service.email.model.Email;

public interface EmailSenderService {

    void send(Email email);

}

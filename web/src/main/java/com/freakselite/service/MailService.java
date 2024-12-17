package com.freakselite.service;

import com.freakselite.model.MailMessage;

public interface MailService {
    // == send mail message ==
    boolean sendMessage(MailMessage mail);
}

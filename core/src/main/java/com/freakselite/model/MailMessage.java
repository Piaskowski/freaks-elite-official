package com.freakselite.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MailMessage {
    // == fields ==
    @NotEmpty(message = "Imię jest obowiązkowe.")
    private String name;
    @NotEmpty(message = "Adres email jest obowiązkowy.")
    private String email;
    @NotEmpty(message = "Temat jest obowiązkowy.")
    private String subject;
    @NotEmpty(message = "Treść jest obowiązkowa.")
    private String content;
}

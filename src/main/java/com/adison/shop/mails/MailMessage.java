package com.adison.shop.mails;

import lombok.*;

import java.io.Serializable;

//should implement for us to be able to use the object as the basis of an email message
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailMessage implements Serializable {

    @NonNull
    private String recipient;
    @NonNull
    private String subject;
    @NonNull
    private String text;
}

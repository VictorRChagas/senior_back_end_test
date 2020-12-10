package br.com.senior.techicaltest.vendas.util;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    private MessageSource messageSource;

    public MessageUtil(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    public String get(String id, Object... args) {
        try {
            return messageSource.getMessage(id, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return id;
        }
    }
}

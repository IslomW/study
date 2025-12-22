package com.sharom.service.impl;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Locale;
import java.util.ResourceBundle;

@ApplicationScoped
public class I18nService {
    private static final String BUNDLE_BASE_NAME = "messages";


    public String getMessage(String key, String lang) {
        Locale locale = lang != null ? new Locale(lang) : Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
        return bundle.getString(key);
    }


    public String getMessage(String key, String lang, Object... args) {
        String template = getMessage(key, lang);
        return java.text.MessageFormat.format(template, args);
    }

}

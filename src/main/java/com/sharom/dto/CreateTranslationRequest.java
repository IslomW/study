package com.sharom.dto;

public record CreateTranslationRequest(Long wordId,
                                       String lang,
                                       String translation
) {
}

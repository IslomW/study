package com.sharom.dto;

import com.sharom.enums.PosType;

public record CreateWordRequest(String word,
                                String lang,
                                PosType posType,
                                Long levelId) {
}

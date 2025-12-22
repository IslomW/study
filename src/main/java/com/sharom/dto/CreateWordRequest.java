package com.sharom.dto;

import com.sharom.enums.PosType;

public record CreateWordRequest(String word,
                                PosType posType,
                                Long levelId) {
}

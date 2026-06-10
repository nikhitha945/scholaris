package com.scholaris.dto;

import jakarta.validation.constraints.NotBlank;

public record QueryRequest(@NotBlank String sql) {
}

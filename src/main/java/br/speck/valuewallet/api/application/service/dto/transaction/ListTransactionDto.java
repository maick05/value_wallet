package br.speck.valuewallet.api.application.service.dto.transaction;

import org.springframework.data.domain.Pageable;

public record ListTransactionDto(
        Pageable pagination
) {
}



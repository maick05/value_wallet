package br.speck.valuewallet.api.application.service.dto.transaction;

import br.speck.valuewallet.api.domain.enums.OperationType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record CreateTransactionDto(
        @NotBlank @NotEmpty String title,
        String description,
        @NotNull LocalDate transactionDate,
        LocalDate paymentDate,
        @NotNull OperationType operationType,
        @NotNull @Positive
        Double value,
        @PositiveOrZero
        Double paidValue,
        List<@NotBlank String> tags) {
}

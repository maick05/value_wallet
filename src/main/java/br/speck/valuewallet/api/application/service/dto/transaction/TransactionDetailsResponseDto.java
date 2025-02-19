package br.speck.valuewallet.api.application.service.dto.transaction;

import br.speck.valuewallet.api.domain.entity.mongodb.TransactionEntity;
import br.speck.valuewallet.api.domain.enums.OperationType;

import java.time.LocalDate;
import java.util.List;

public record TransactionDetailsResponseDto(
        String id,
        String title,
        String description,
        LocalDate transactionDate,
        LocalDate paymentDate,
        OperationType operationType,
        double value,
        double paidValue,
        List<String> tags) {

    public TransactionDetailsResponseDto(TransactionEntity transaction) {
        this(transaction.getId(), transaction.getTitle(), transaction.getDescription(), transaction.getTransactionDate(), transaction.getPaymentDate(), transaction.getOperationType(), transaction.getValue(), transaction.getPaidValue(), transaction.getTags());
    }
}

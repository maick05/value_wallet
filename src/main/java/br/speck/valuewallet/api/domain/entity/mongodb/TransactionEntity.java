package br.speck.valuewallet.api.domain.entity.mongodb;

import br.speck.valuewallet.api.application.service.dto.transaction.CreateTransactionDto;
import br.speck.valuewallet.api.domain.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity extends AbstractMongoDBEntity {
    private String title;
    private String description;
    private LocalDate transactionDate;
    private LocalDate paymentDate;
    private OperationType operationType;
    private double value;
    private double paidValue;
    private List<String> tags;

    public TransactionEntity(CreateTransactionDto paramsDTO) {
        this.title = paramsDTO.title();
        this.description = paramsDTO.description();
        this.transactionDate = paramsDTO.transactionDate();
        this.paymentDate = paramsDTO.paymentDate();
        this.operationType = paramsDTO.operationType();
        this.value = paramsDTO.value();
        this.paidValue = paramsDTO.paidValue() != null ? paramsDTO.paidValue() : 0.0;
        this.tags = paramsDTO.tags();
    }

    public TransactionEntity() {
    }

    public TransactionEntity(String id, String title, String description, LocalDate transactionDate, LocalDate paymentDate, OperationType operationType, double value, double paidValue, List<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.transactionDate = transactionDate;
        this.paymentDate = paymentDate;
        this.operationType = operationType;
        this.value = value;
        this.paidValue = paidValue;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public double getValue() {
        return value;
    }

    public double getPaidValue() {
        return paidValue;
    }

    public List<String> getTags() {
        return tags;
    }
}

package br.speck.valuewallet.api.application.service.transaction;

import br.speck.valuewallet.api.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.application.service.CommandService;
import br.speck.valuewallet.api.application.service.dto.transaction.CreateTransactionDto;
import br.speck.valuewallet.api.domain.entity.mongodb.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTransactionService implements CommandService<TransactionEntity, CreateTransactionDto> {

    @Autowired
    private TransactionRepository repository;

    @Override
    public TransactionEntity execute(CreateTransactionDto paramsDTO) {
        return repository.save(new TransactionEntity(paramsDTO));
    }
}


package br.speck.valuewallet.api.application.service.transaction;

import br.speck.valuewallet.api.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.application.service.CommandService;
import br.speck.valuewallet.api.application.service.dto.transaction.GetTransactionDetailsDto;
import br.speck.valuewallet.api.application.service.dto.transaction.ListTransactionDto;
import br.speck.valuewallet.api.application.service.dto.transaction.TransactionDetailsResponseDto;
import br.speck.valuewallet.api.domain.entity.mongodb.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetTransactionService implements CommandService<Page<TransactionDetailsResponseDto>, ListTransactionDto> {

    @Autowired
    private TransactionRepository repository;

    @Override
    public Page<TransactionDetailsResponseDto> execute(ListTransactionDto paramsDTO) {
        return repository.findAll(paramsDTO.pagination()).map(TransactionDetailsResponseDto::new);
    }


    public TransactionDetailsResponseDto getDetails(GetTransactionDetailsDto paramsDTO) throws ClassNotFoundException {
        Optional<TransactionEntity> transaction = repository.findById(paramsDTO.id());
        if (transaction.isEmpty()) throw new ClassNotFoundException();
        return new TransactionDetailsResponseDto(transaction.get());
    }
}
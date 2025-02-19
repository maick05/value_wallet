package br.speck.valuewallet.api.adapter.controller;

import br.speck.valuewallet.api.application.service.dto.transaction.CreateTransactionDto;
import br.speck.valuewallet.api.application.service.dto.transaction.GetTransactionDetailsDto;
import br.speck.valuewallet.api.application.service.dto.transaction.ListTransactionDto;
import br.speck.valuewallet.api.application.service.dto.transaction.TransactionDetailsResponseDto;
import br.speck.valuewallet.api.application.service.transaction.CreateTransactionService;
import br.speck.valuewallet.api.application.service.transaction.GetTransactionService;
import br.speck.valuewallet.api.domain.entity.mongodb.TransactionEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("transactions")
public class TransactionController {
    @Autowired
    private CreateTransactionService createTransactionService;

    @Autowired
    private GetTransactionService getTransactionService;

    @PostMapping
    public ResponseEntity<TransactionDetailsResponseDto> create(@RequestBody @Valid CreateTransactionDto paramsDTO, UriComponentsBuilder uriBuilder) {
        TransactionEntity transaction = createTransactionService.execute(paramsDTO);
        var uri = uriBuilder.path("/transactions/{id}").buildAndExpand(transaction.getId()).toUri();
        return ResponseEntity.created(uri).body(new TransactionDetailsResponseDto(transaction));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionDetailsResponseDto>> list(@PageableDefault(size = 10, sort = {"id"}) Pageable pagination) {
        Page<TransactionDetailsResponseDto> transactions = getTransactionService.execute(new ListTransactionDto(pagination));
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDetailsResponseDto> getDetails(@PathVariable String id) throws ClassNotFoundException {
        TransactionDetailsResponseDto transaction = getTransactionService.getDetails(new GetTransactionDetailsDto(id));
        return ResponseEntity.ok(transaction);
    }
}

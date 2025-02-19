package br.speck.valuewallet.api.application.service.transaction;

import br.speck.valuewallet.api.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.application.service.dto.transaction.CreateTransactionDto;
import br.speck.valuewallet.api.domain.entity.mongodb.TransactionEntity;
import br.speck.valuewallet.api.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private CreateTransactionService sut;

    @Test
    void executeShouldSaveAndReturnTransactionEntity() {
        CreateTransactionDto dto = new CreateTransactionDto(
                "Compra no Mercado",
                "Compra de alimentos e itens de limpeza",
                LocalDate.now(),
                LocalDate.now(),
                OperationType.DEBIT,
                150.0,
                150.0,
                List.of("alimentação", "limpeza")
        );
        TransactionEntity expectedEntity = new TransactionEntity(dto);

        // mock
        when(repository.save(any(TransactionEntity.class))).thenReturn(expectedEntity);

        // Act
        TransactionEntity result = sut.execute(dto);

        // Capturar parametro
        ArgumentCaptor<TransactionEntity> captor = ArgumentCaptor.forClass(TransactionEntity.class);
        verify(repository).save(captor.capture());
        TransactionEntity capturedEntity = captor.getValue();

        // asserts
        assertNotNull(capturedEntity, "A entidade enviada para salvar não deve ser nula.");
        assertEquals(expectedEntity, result, "A entidade retornada deve ser igual à entidade esperada.");
    }
}

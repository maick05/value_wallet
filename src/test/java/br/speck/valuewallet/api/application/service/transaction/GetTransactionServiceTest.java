package br.speck.valuewallet.api.application.service.transaction;

import br.speck.valuewallet.api.adapter.repository.TransactionRepository;
import br.speck.valuewallet.api.application.service.dto.transaction.GetTransactionDetailsDto;
import br.speck.valuewallet.api.application.service.dto.transaction.ListTransactionDto;
import br.speck.valuewallet.api.application.service.dto.transaction.TransactionDetailsResponseDto;
import br.speck.valuewallet.api.domain.entity.mongodb.TransactionEntity;
import br.speck.valuewallet.api.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetTransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private GetTransactionService sut;

    @Test
    void getDetailsShouldReturnTransactionResponse() throws ClassNotFoundException {
        TransactionEntity entityMock = new TransactionEntity(
                "any_id",
                "Compra no Mercado",
                "Compra de alimentos e itens de limpeza",
                LocalDate.now(),
                LocalDate.now(),
                OperationType.DEBIT,
                150.0,
                150.0,
                List.of("alimentação", "limpeza")
        );

        TransactionDetailsResponseDto expected = new TransactionDetailsResponseDto(
                entityMock
        );

        // mock
        when(repository.findById(any())).thenReturn(Optional.of(entityMock));

        // Act
        TransactionDetailsResponseDto actual = sut.getDetails(new GetTransactionDetailsDto("any_id"));

        // Capturar parametro
        ArgumentCaptor<String> captor = ArgumentCaptor.captor();
        verify(repository).findById(captor.capture());

        // asserts
        assertEquals("any_id", captor.getValue(), "Parametro igual ao esperado.");
        assertEquals(expected, actual, "A entidade retornada deve ser igual à entidade esperada.");
    }

    @Test
    void executeShouldReturnListOfTransactionResponse() {
        TransactionEntity entityMock = new TransactionEntity(
                "any_id",
                "Compra no Mercado",
                "Compra de alimentos e itens de limpeza",
                LocalDate.now(),
                LocalDate.now(),
                OperationType.DEBIT,
                150.0,
                150.0,
                List.of("alimentação", "limpeza")
        );

        TransactionDetailsResponseDto expectedResp = new TransactionDetailsResponseDto(
                entityMock
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<TransactionEntity> pageEnt = new PageImpl<>(List.of(entityMock), pageable, 1);
        Page<TransactionDetailsResponseDto> expected = new PageImpl<>(List.of(expectedResp), pageable, 1);

        // mock
        when(repository.findAll(pageable)).thenReturn(pageEnt);

        // Act
        Page<TransactionDetailsResponseDto> actual = sut.execute(new ListTransactionDto(pageable));

        // asserts
        assertNotNull(actual, "A entidade retornada não pode ser nula.");
        assertEquals(expected, actual, "A entidade retornada deve ser igual à entidade esperada.");
    }

    @Test
    public void getDetailsShouldThrowsClassNotFoundException() {
        GetTransactionDetailsDto paramsDTO = new GetTransactionDetailsDto("fake_id");

        when(repository.findById(paramsDTO.id())).thenReturn(Optional.empty());

        assertThrows(ClassNotFoundException.class, () -> {
            sut.getDetails(paramsDTO);
        });
    }
}

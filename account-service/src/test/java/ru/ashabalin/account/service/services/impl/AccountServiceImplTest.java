package ru.ashabalin.account.service.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ashabalin.account.service.exceptions.AccountNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.ashabalin.account.service.services.impl.AccountServiceImpl.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AccountServiceImpl.class})
public class AccountServiceImplTest {

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountServiceImpl accountService;

    @Test
    public void getAmountTest() {
        when(jdbcTemplate.queryForObject(COUNT, Long.class, 1)).thenReturn(1L);
        when(jdbcTemplate.queryForObject(SELECT, Long.class, 1)).thenReturn(10L);
        Long amount = accountService.getAmount(1);
        assertThat(amount).isEqualTo(10L);
    }

    @Test
    public void getAmountExceptionTest() {
        when(jdbcTemplate.queryForObject(COUNT, Long.class, 1)).thenReturn(0L);
        assertThrows(AccountNotFoundException.class, () ->
                accountService.getAmount(1));
    }

    @Test
    public void addAmountBalanceExistTest() {
        when(jdbcTemplate.queryForObject(COUNT, Long.class, 1)).thenReturn(1L);
        accountService.addAmount(1, 10L);
        verify(jdbcTemplate).update(UPDATE, 10L, 1);
    }

    @Test
    public void addAmountBalanceDidntExistTest() {
        when(jdbcTemplate.queryForObject(COUNT, Long.class, 1)).thenReturn(0L);
        accountService.addAmount(1, 10L);
        verify(jdbcTemplate).update(INSERT, 1, 10L);
    }
}

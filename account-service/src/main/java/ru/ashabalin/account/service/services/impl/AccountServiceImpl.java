package ru.ashabalin.account.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ashabalin.account.service.exceptions.AccountNotFoundException;
import ru.ashabalin.account.service.services.AccountService;

@Service
@CacheConfig(cacheNames = "account-balance")
public class AccountServiceImpl implements AccountService {

    protected static final String COUNT = "SELECT COUNT(id) FROM account WHERE id=?";
    protected static final String SELECT = "SELECT balance FROM account WHERE id=?";
    protected static final String INSERT = "INSERT INTO account (id, balance) VALUES (?, ?)";
    protected static final String UPDATE = "UPDATE account SET balance=balance+? WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Cacheable
    @Override
    public Long getAmount(Integer id) {
        if (isAccountExist(id)) {
            return jdbcTemplate.queryForObject(SELECT, Long.class, id);
        } else {
            throw new AccountNotFoundException("Account with id = " + id + " not found");
        }
    }

    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public void addAmount(Integer id, Long value) {
        if (isAccountExist(id)) {
            jdbcTemplate.update(UPDATE, value, id);
        } else {
            jdbcTemplate.update(INSERT, id, value);
        }
    }

    private boolean isAccountExist(Integer id) {
        return jdbcTemplate.queryForObject(COUNT, Long.class, id) > 0;
    }
}

package com.simform.hibernate1.service;

import com.simform.hibernate1.entity.*;
import com.simform.hibernate1.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BankRepository bankRepository;

    public Account create(Account account) {

        account.getCustomer().add(account);
        List<Bank> banks = bankRepository.findByBankName(account.getBank().getBankName());
        if (!banks.isEmpty()) {
            Bank bank = banks.get(0);
            bank.addAccount(account);
        } else {
            account.getBank().addAccount(account);
        }
        accountRepository.save(account);
        return account;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findAccountDetailsByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public void deleteByAccountNumber(String accountNumber) {
        Account byAccountNumber = accountRepository.findByAccountNumber(accountNumber);
        accountRepository.delete(byAccountNumber);
    }

    public Account updateByAccountNumber(String accountNumber, Account account) {
        Account byAccountNumber = accountRepository.findByAccountNumber(accountNumber);
        byAccountNumber.getCustomer().add(account);
        byAccountNumber.getBank().addAccount(account);
        account.setId(byAccountNumber.getId());
        accountRepository.save(account);
        return account;
    }
}

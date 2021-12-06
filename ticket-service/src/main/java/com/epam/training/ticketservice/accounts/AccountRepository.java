package com.epam.training.ticketservice.accounts;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {

    Account findAccountByUsername(String username);
}

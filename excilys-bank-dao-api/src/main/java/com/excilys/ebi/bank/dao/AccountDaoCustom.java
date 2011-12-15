package com.excilys.ebi.bank.dao;

import java.util.List;

import com.excilys.ebi.bank.model.entity.Account;
import com.excilys.ebi.bank.model.entity.User;

public interface AccountDaoCustom {

	List<Account> findByUserFetchCardsOrderByNumberAsc(User user);

	Account findByNumberFetchCardsOrderByNumberAsc(String accountNumber);

	long countAccountsByIdAndUserLogin(Integer id, String login);
}

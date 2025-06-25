package com.boot.dao;

import java.util.List;

import com.boot.model.Balance;

public interface BalanceDao {
	void add(Balance item);
	Balance get(int id);
	List<Balance>getAll();
	void update(Balance item);
	void delete(int id);
}

package com.boot.service;

import java.util.List;

import com.boot.model.Balance;

public interface BalanceService {
	boolean addItem(Balance item);
	Balance getItem(int id);
	List<Balance>getAll();
	void update(Balance item);
	boolean delete(int id);
}

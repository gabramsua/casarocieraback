package com.boot.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.boot.model.Balance;
import com.boot.repository.BalanceRepository;

@Repository
public class BalanceImpl implements BalanceDao {
	
	@Autowired
	BalanceRepository repository;

	@Override
	public void add(Balance item) {
		repository.save(item);
	}

	@Override
	public Balance get(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Balance> getAll() {
		return repository.findAll(); 
	}

	@Override
	public void update(Balance item) {
		repository.save(item);
	}

	@Override
	public void delete(int id) {
		repository.deleteById(id);
	}

}

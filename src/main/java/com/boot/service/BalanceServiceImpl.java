package com.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.dao.BalanceDao;
import com.boot.model.Balance;

@Service
public class BalanceServiceImpl implements BalanceService {
	
	@Autowired
	BalanceDao dao;

	@Override
	public boolean addItem(Balance item) {
		if(dao.get(item.getId()) != null){
			dao.add(item);
			return true;
		}
		return false;
	}

	@Override
	public Balance getItem(int id) {
		return dao.get(id);
	}

	@Override
	public List<Balance> getAll() {
		return dao.getAll();
	}

	@Override
	public void update(Balance item) {
		if(dao.get(item.getId()) != null) {
			dao.update(item);
		}
	}

	@Override
	public boolean delete(int id) {
		if(dao.get(id) != null) {
			dao.delete(id);
			return true;
		}
		return false;
	}

}

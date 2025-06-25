package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.Balance;

public interface BalanceRepository extends JpaRepository<Balance, Integer>{

}

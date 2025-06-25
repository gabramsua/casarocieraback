package com.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boot.model.Balance;
import com.boot.service.BalanceService;

@RestController
public class CategoriaController {

	@Autowired
	BalanceService service;
	
	@GetMapping(value="balance/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Balance getItem(@PathVariable("id") int id) {
		return service.getItem(id);
	}
	@GetMapping(value="/balances", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Balance>getAll() {
		return service.getAll();
	}
	@PostMapping(value="balance", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
	public String add(@RequestBody Balance item) {
		return String.valueOf(service.addItem(item));
	}
	@PutMapping(value="balance", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody Balance item) {
		service.update(item);
	}
	@DeleteMapping(value="balance/{id}")
	public void delete(@PathVariable("id")int id) {
		service.delete(id);
	}
}

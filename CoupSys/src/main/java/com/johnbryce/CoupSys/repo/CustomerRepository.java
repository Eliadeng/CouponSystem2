package com.johnbryce.CoupSys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbryce.CoupSys.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Customer findByEmailAndPassword(String email, String password);

	Customer findByEmail(String email);

}
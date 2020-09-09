package com.johnbryce.CoupSys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbryce.CoupSys.exceptions.NotAllowedException;
import com.johnbryce.CoupSys.repo.CompanyRepository;
import com.johnbryce.CoupSys.repo.CouponRepository;
import com.johnbryce.CoupSys.repo.CustomerRepository;

@Service
public abstract class ClientService {
	@Autowired
	protected CompanyRepository companyRepository;
	@Autowired
	protected CustomerRepository customerRepository;
	@Autowired
	protected CouponRepository couponRepository;

	public abstract boolean login(String email, String password) throws NotAllowedException;
}

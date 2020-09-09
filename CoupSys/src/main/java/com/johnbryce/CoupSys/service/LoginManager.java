package com.johnbryce.CoupSys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.johnbryce.CoupSys.exceptions.NotAllowedException;

@Service
public class LoginManager {

	@Autowired
	AdminService adminService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CompanyService companyService;

	public ClientService login(String email, String password, ClientType clientType) throws NotAllowedException {
		switch (clientType) {
		case Administrator:
			if (adminService.login(email, password) == true) {

				return adminService;
			}
		case Customer:
			if (customerService.login(email, password) == true) {
				return customerService;
			}
		case Company:
			if (companyService.login(email, password) == true) {
				return companyService;
			}
		default:
			throw new NotAllowedException("email or password or type wrongs");
		}
	}
}

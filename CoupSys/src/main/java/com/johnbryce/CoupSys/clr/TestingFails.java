package com.johnbryce.CoupSys.clr;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;

import com.johnbryce.CoupSys.beans.Category;
import com.johnbryce.CoupSys.beans.Coupon;
import com.johnbryce.CoupSys.beans.Customer;
import com.johnbryce.CoupSys.service.AdminService;
import com.johnbryce.CoupSys.service.ClientType;
import com.johnbryce.CoupSys.service.CustomerService;
import com.johnbryce.CoupSys.service.LoginManager;
import com.johnbryce.CoupSys.util.Utilities;

//@Component
//@Order(value = 4)
public class TestingFails implements CommandLineRunner {

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerService customerService;

	@SuppressWarnings("deprecation")
	@Override
	public void run(String... args) throws Exception {
		// trying to login
		System.out.println("Login manager failes: ");
		System.out.println(loginManager.login("admin@admin.com", "company", ClientType.Administrator));
		System.out.println(loginManager.login("eliade@gmail.com", "Eliadi", ClientType.Customer));
		System.out.println(loginManager.login("coca@gmail.com ", "6666", ClientType.Company));

		// trying to delete Company - the id is not exists
		adminService.deleteCompany(8);

		// trying do print company that doesn't exists
		System.out.println(adminService.getOneCompany(11));

		// trying to print company by email and password
		System.out.println(adminService.getCompanyByEmailAndPassword("ksp@gmail.com", "ksp123"));

		// trying to add customer that already exists
		Customer customer = new Customer("Dana", "Lavon", "yossishak@gmail.com", "1234");
		adminService.addCustomer(customer);

		// trying to update some customer without updating his details in the system
		Customer customer2 = new Customer("Amit", "Reuveni", "amit@gmail.com", "amit123");
		adminService.updateCustomer(customer2);

		// trying to delete customer
		adminService.deleteCustomer(customer2);

		// trying to print customer that doesn't exists in the system
		System.out.println(adminService.getOneCustomer(13));

		// trying to update coupon without updating first his details in the system
		Coupon coupon = new Coupon(2, Category.Drinks, "15%", "15% on all the menu",
				Utilities.convertUtilDateToSQL(new Date(2020, 5, 14)),
				Utilities.convertUtilDateToSQL(new Date(2022, 3, 11)), 30, 10, "pepsi");
		adminService.updateCoupon(coupon);

		// trying to purchase coupon that doesn't exists in the system
		customerService.setCustomerID(1);
		customerService.purchaseCoupon(coupon);

		// trying to purchase coupon when amount = 0
		customerService.setCustomerID(1);
		adminService.getOneCoupon(1).setAmount(0);
		customerService.purchaseCoupon(adminService.getOneCoupon(1));

		// trying to purchase coupon that had already expired
		customerService.setCustomerID(2);
		customerService.purchaseCoupon(adminService.getOneCoupon(2));

	}

}

package com.johnbryce.CoupSys.clr;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.johnbryce.CoupSys.beans.Customer;
import com.johnbryce.CoupSys.service.AdminService;
import com.johnbryce.CoupSys.service.CustomerService;
import com.johnbryce.CoupSys.util.PrintUtils;

@Component
@Order(value = 2)
public class CustomerTesting implements CommandLineRunner {

	@Autowired
	private PrintUtils printUtils;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerService customerService;

	@Override
	public void run(String... args) throws Exception {
		printUtils.seperateLines("Customer Methods:");

		Customer c1 = new Customer("Moshe", "Leibovitch", "mosheleib@gmail.com", "moshele123");
		Customer c2 = new Customer("Eliad", "Engelstein", "eliade@gmail.com", "eliadeng123");
		Customer c3 = new Customer("Matan", "Becker", "matanbeck@gmail.com", "matanbecker123");
		Customer c4 = new Customer("Kobi", "Aizler", "kobiaiz@gmail.com", "kobaizler123");
		Customer c5 = new Customer("Ethan", "Moses", "moses@gmail.con", "ethanmoses459");
		List<Customer> custoArrays = Arrays.asList(c1, c2, c3, c4, c5);

		// adding the customer
		for (int i = 0; i < custoArrays.size(); i++) {
			adminService.addCustomer(custoArrays.get(i));
		}
		printUtils.printCustomersWithOutCoupon();

		// customer login - success
		System.out.println("Customer success?: " + customerService.login("mosheleib@gmail.com", "moshele123"));
		// customer login - fails
		System.out.println("Customer success?: " + customerService.login("mosheleibov@gmail.com", "moshele1234569"));
		System.out.println();

		// customer exists - true
		System.out.println("customer exists? " + adminService.isCustomerExists("eliade@gmail.com", "eliadeng123"));
		// customer exists - false
		System.out.println("customer exists? " + adminService.isCustomerExists("eliadengels@gmail.com", "eliadeng123579"));
		System.out.println();

		// update customer
		c1.setEmail("mosheleib123@gmail.com");
		adminService.updateCustomer(c1);
		System.out.println("After updating customer " + c1.getId() + " :");
		printUtils.printOneCustomerWithOutCoupon(c1);

		// delete customer
		adminService.deleteCustomer(c5);
		System.out.println("customers detailes after deleting: ");
		printUtils.printCustomersWithOutCoupon();

		// get one customer
		System.out.println("get customer :");
		printUtils.printOneCustomerWithOutCoupon(adminService.getOneCustomer(3));

	}

}

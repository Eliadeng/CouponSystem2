package com.johnbryce.CoupSys.clr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.johnbryce.CoupSys.beans.Category;
import com.johnbryce.CoupSys.beans.Company;
import com.johnbryce.CoupSys.beans.Coupon;
import com.johnbryce.CoupSys.beans.Customer;
import com.johnbryce.CoupSys.exceptions.NotAllowedException;
import com.johnbryce.CoupSys.exceptions.NotExistException;
import com.johnbryce.CoupSys.schedule.CouponExpirationDailyJob;
import com.johnbryce.CoupSys.service.AdminService;
import com.johnbryce.CoupSys.service.ClientType;
import com.johnbryce.CoupSys.service.CustomerService;
import com.johnbryce.CoupSys.service.LoginManager;
import com.johnbryce.CoupSys.util.PrintUtils;
import com.johnbryce.CoupSys.util.Utilities;

@Component
@Order(value = 3)
public class CouponTesting implements CommandLineRunner {

	@Autowired
	private PrintUtils printUtils;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CouponExpirationDailyJob couponExpirationDailyJob;

	@Autowired
	private LoginManager loginManager;

	public void addCouponToCompany(Coupon coupon) throws NotExistException, NotAllowedException {
		if (!adminService.isCompanyExists(coupon.getCompanyID())) {
			throw new NotExistException("The company is not exists in the system, you canno't add coupon...");
		}
		Company company = adminService.getOneCompany(coupon.getCompanyID());
		company.addCoupon(coupon);
		adminService.addCoupon(coupon);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run(String... args) throws Exception {
		printUtils.seperateLines("Coupon Methods");

		// Dates for coupon 1:
		Date d1 = new Date(2020, 9, 14);
		Date d2 = new Date(2021, 3, 12);
		// Dates for coupon 2:
		Date d3 = new Date(2020, 5, 22);
		Date d4 = new Date(2020, 8, 3);
		// Dates for coupon 3:
		Date d5 = new Date(2020, 11, 11);
		Date d6 = new Date(2020, 12, 22);
		// Dates for coupon 4:
		Date d7 = new Date(2021, 1, 1);
		Date d8 = new Date(2021, 2, 1);
		// Dates for coupon 5:
		Date d9 = new Date(2021, 3, 7);
		Date d10 = new Date(2021, 5, 10);

		// create the coupons
		List<Coupon> coupons = new ArrayList<>();
		Coupon c1 = new Coupon(1, Category.Drinks, "sale", "30%", Utilities.convertUtilDateToSQL(d1),
				Utilities.convertUtilDateToSQL(d2), 10, 10, "coca");
		addCouponToCompany(c1);
		coupons.add(c1);
		Coupon c2 = new Coupon(2, Category.Drinks, "50%", "50% on all the bottles", Utilities.convertUtilDateToSQL(d3),
				Utilities.convertUtilDateToSQL(d4), 150, 3, "pepsi");
		addCouponToCompany(c2);
		coupons.add(c2);
		Coupon c3 = new Coupon(3, Category.Pharmacy, "10%", "10% on all the loations",
				Utilities.convertUtilDateToSQL(d5), Utilities.convertUtilDateToSQL(d6), 76, 8, "superpharm");
		addCouponToCompany(c3);
		coupons.add(c3);
		Coupon c4 = new Coupon(4, Category.Pharmacy, "1+1", "1+1 on all the Axe", Utilities.convertUtilDateToSQL(d7),
				Utilities.convertUtilDateToSQL(d8), 200, 10, "goodpharm");
		addCouponToCompany(c4);
		coupons.add(c4);
		Coupon c5 = new Coupon(1, Category.Drinks, "sale All", "26% discount", Utilities.convertUtilDateToSQL(d9),
				Utilities.convertUtilDateToSQL(d10), 10, 10, "coca");
		addCouponToCompany(c5);
		coupons.add(c5);

		printUtils.printCompanies(coupons);

		System.out.println("The coupons are: ");
		printUtils.printCoupons();

		// coupon exists? - true
		System.out.println("does coupon exists? " + adminService.isCouponExists(1));
		// coupon exists? - false
		System.out.println("does coupon exists? " + adminService.isCouponExists(10));

		// update coupon
		c1.setDescription("30% sale on all the cans");
		adminService.updateCoupon(c1);
		System.out.println("coupon " + c1.getId() + " after update: ");
		printUtils.printOneCoupon(c1);
		adminService.updateCoupon(c2);
		printUtils.printOneCoupon(c2);

		// delete coupon
		adminService.deleteCoupon(c4);
		System.out.println("coupons after delete ");
		printUtils.printCoupons();

		// get one coupon
		System.out.println("one coupon: ");
		printUtils.printOneCoupon(adminService.getOneCoupon(3));

		// purchase coupon
		printUtils.seperateLines("Coupon purchase and delete");
		customerService.setCustomerID(1);
		customerService.purchaseCoupon(c1);
		printUtils.printOneCustomer(adminService.getOneCustomer(1));
		customerService.setCustomerID(2);
		customerService.purchaseCoupon(c1);
		printUtils.printOneCustomer(adminService.getOneCustomer(2));
		System.out.println();
		printUtils.printCustomers();

		// delete coupon and his purchases
		List<Customer> customers = customerService.getAllCustomers();
		for (Customer customer : customers) {
			if (customer.getCoupons() == c1) {
				customer.getCoupons().remove(c1);
				adminService.updateCustomer(customer);
			}
		}
		adminService.deleteCoupon(c1);
		customerService.deleteCustomersVScoupons(c1.getId());
		System.out.println("Print customers details after deleting coupons:");
		printUtils.printCustomers();


		// login manager successfully
		printUtils.seperateLines("Login Manager:");
		System.out.println("Login manager success:");
		System.out.println(loginManager.login("admin@admin.com", "admin", ClientType.Administrator));
		System.out.println(loginManager.login("mosheleib123@gmail.com", "moshele123", ClientType.Customer));
		System.out.println(loginManager.login("coca@gmail.com", "1111", ClientType.Company));

		printUtils.printCompanies(adminService.getAllCoupons());

		// coupon expired check
		printUtils.seperateLines("CouponExpired Running:");
		System.out.println("The coupons before deleting: ");
		printUtils.printCoupons();
		couponExpirationDailyJob.deleteCouponsExpired();
		System.out.println("The coupons after deleting:");
		printUtils.printCoupons();

	}

}

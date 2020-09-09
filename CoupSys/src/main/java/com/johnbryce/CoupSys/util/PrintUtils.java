package com.johnbryce.CoupSys.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbryce.CoupSys.beans.Company;
import com.johnbryce.CoupSys.beans.Coupon;
import com.johnbryce.CoupSys.beans.Customer;
import com.johnbryce.CoupSys.service.AdminService;

@Service
public class PrintUtils {

	@Autowired
	private AdminService adminService;

	public void printCompaniesWithOutCoupon() {
		List<Company> companies = adminService.getAllCompanies();
		System.out.printf("%5s %10s %20s %20s", "CompanyID", "Name", "Email", "Password");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		for (Company company : companies) {
			System.out.format("%5s %15s %25s %10s", company.getId(), company.getName(), company.getEmail(),
					company.getPassword());
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printCompanies(List<Coupon> coupons) {
		List<Company> companies = adminService.getAllCompanies();
		for (int i = 0; i < companies.size(); i++) {
			for (int j = 0; j < coupons.size(); j++) {
				if (companies.get(i).getId() == coupons.get(j).getCompanyID()) {
					List<Coupon> tmp = companies.get(i).getCoupons();
					tmp.add(coupons.get(j));
					companies.get(i).setCoupons(tmp);
				}
			}
		}
		System.out.printf("%5s %10s %20s %20s %20s", "CompanyID", "Name", "Email", "Password", "Coupons");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		for (Company company : companies) {
			System.out.format("%5s %15s %25s %10s %15s", company.getId(), company.getName(), company.getEmail(),
					company.getPassword(), company.getCoupons());
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printOneCompany(Company company) {
		System.out.printf("%5s %10s %20s %20s %20s", "CompanyID", "Name", "Email", "Password", "Coupons");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.format("%5s %15s %25s %15s %10s", company.getId(), company.getName(), company.getEmail(),
				company.getPassword(), company.getCoupons());
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printOneCompanyWithOutCoupon(Company company) {
		System.out.printf("%5s %10s %20s %20s", "CompanyID", "Name", "Email", "Password");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.format("%5s %15s %25s %15s", company.getId(), company.getName(), company.getEmail(),
				company.getPassword());
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printCustomers() {
		List<Customer> customers = adminService.getAllCustomers();
		System.out.printf("%5s %10s %10s %15s %17s %17s", "customerID", "firstName", "lastName", "email", "password",
				"Coupons");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		for (Customer customer : customers) {
			System.out.format("%5s %10s %13s %20s %17s %17s", customer.getId(), customer.getFirstName(),
					customer.getLastName(), customer.getEmail(), customer.getPassword(), customer.getCoupons());
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printCustomersWithOutCoupon() {
		List<Customer> customers = adminService.getAllCustomers();
		System.out.printf("%5s %10s %10s %15s %17s", "customerID", "firstName", "lastName", "email", "password");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		for (Customer customer : customers) {
			System.out.format("%5s %10s %13s %20s %17s", customer.getId(), customer.getFirstName(),
					customer.getLastName(), customer.getEmail(), customer.getPassword());
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printOneCustomer(Customer customer) {
		System.out.printf("%5s %10s %10s %15s %17s %17s", "customerID", "firstName", "lastName", "email", "password",
				"Coupons");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.format("%5s %10s %13s %20s %17s %17s", customer.getId(), customer.getFirstName(),
				customer.getLastName(), customer.getEmail(), customer.getPassword(), customer.getCoupons());
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printOneCustomerWithOutCoupon(Customer customer) {
		System.out.printf("%5s %10s %10s %15s %17s", "customerID", "firstName", "lastName", "email", "password");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.format("%5s %10s %13s %20s %17s", customer.getId(), customer.getFirstName(),
				customer.getLastName(), customer.getEmail(), customer.getPassword());
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printCoupons() {
		List<Coupon> coupons = adminService.getAllCoupons();
		System.out.printf("%5s %5s %5s %5s %15s %22s %20s %17s %7s %10s", "couponID", "companyID", "categoryID",
				"title", "description", "startDate", "endDate", "amount", "price", "image");
		System.out.println();
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------");
		for (Coupon coupon : coupons) {
			System.out.format("%5s %5s %15s %5s %25s %22s %22s %5s %7s %15s", coupon.getId(), coupon.getCompanyID(),
					coupon.getCategoryID(), coupon.getTitle(), coupon.getDescription(), coupon.getStartDate(),
					coupon.getEndDate(), coupon.getAmount(), coupon.getPrice(), coupon.getImage());
			System.out.println();
		}
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void printOneCoupon(Coupon coupon) {
		System.out.printf("%5s %5s %5s %5s %15s %22s %20s %17s %7s %10s", "couponID", "companyID", "categoryID",
				"title", "description", "startDate", "endDate", "amount", "price", "image");
		System.out.println();
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.format("%5s %5s %15s %5s %25s %22s %22s %5s %7s %15s", coupon.getId(), coupon.getCompanyID(),
				coupon.getCategoryID(), coupon.getTitle(), coupon.getDescription(), coupon.getStartDate(),
				coupon.getEndDate(), coupon.getAmount(), coupon.getPrice(), coupon.getImage());
		System.out.println();
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
	}

	public void seperateLines(String name) {
		System.out.println();
		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}
		System.out.println();
		System.out.println("--------------------------------- " + name + "-----------------------------");
		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}
		System.out.println();
		System.out.println();
	}

	Random random = new Random();

	// create random dates
	@SuppressWarnings("deprecation")
	public List<Date> createDate(int year) {
		List<Date> dates = new ArrayList<Date>();
		Date d1 = null, d2 = null;
		boolean flag = true;
		while (flag) {
			d1 = (Date) new Date(year, random.nextInt(12) + 1, random.nextInt(30) + 1);
			d2 = new Date(year, random.nextInt(12) + 1, random.nextInt(30) + 1);
			if (d1.before(d2)) {
				flag = false;
			}
		}
		dates.add(d1);
		dates.add(d2);
		return dates;
	}

}
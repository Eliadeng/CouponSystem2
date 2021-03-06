package com.johnbryce.CoupSys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.johnbryce.CoupSys.beans.Coupon;
import com.johnbryce.CoupSys.beans.Customer;
import com.johnbryce.CoupSys.dbdao.CouponDBDAO;
import com.johnbryce.CoupSys.dbdao.CustomerDBDAO;
import com.johnbryce.CoupSys.exceptions.NotAllowedException;
import com.johnbryce.CoupSys.exceptions.NotExistException;

import lombok.Getter;
import lombok.Setter;

import com.johnbryce.CoupSys.beans.Category;

@Service
@Scope("prototype")
@Setter
@Getter
public class CustomerService extends ClientService {

	private int customerID;

	@Autowired
	private CustomerDBDAO customerDBDAO;

	@Autowired
	private CouponDBDAO couponDBDAO;

	@Override
	public boolean login(String email, String password) {
		if (customerDBDAO.isCustomerExists(email, password) == false) {
			return false;
		}
		return true;
	}

	public void purchaseCoupon(Coupon coupon) throws NotExistException, NotAllowedException {
		if (couponDBDAO.isCouponExists(coupon.getId()) == false) {
			throw new NotExistException("The couponID doesn't exists in the system");
		}
		Customer customer = customerDBDAO.getOneCustomer(customerID);
		List<Coupon> customerCoupons = customer.getCoupons();
		for (Coupon coupon2 : customerCoupons) {
			if (coupon2.getId() == coupon.getId()) {
				throw new NotAllowedException("You are not allowed to buy this coupon twice");
			}
		}
		if (coupon.getAmount() == 0) {
			throw new NotAllowedException("You can't buy a coupon when amount is 0");
		}
		if (coupon.getEndDate().before(new Date())) {
			throw new NotAllowedException("This coupon is already expired");
		}
		customer.addCoupon(coupon);
		System.out.println("coupon " + coupon.getId() + " amount before purchase: " + coupon.getAmount());
		coupon.setAmount(coupon.getAmount() - 1);
		System.out.println("coupon " + coupon.getId() + " amount after purchase: " + coupon.getAmount());
		customerDBDAO.updateCustomer(customer);
	}

	public void deleteCustomersVScoupons(int couponID) {
		couponDBDAO.deleteCouponVs(couponID);
	}

	public List<Coupon> getCustomerCoupons() {
		return customerDBDAO.getOneCustomer(customerID).getCoupons();
	}

	public List<Coupon> getCustomerCoupons(Category category) {
		List<Coupon> coupons = customerDBDAO.getOneCustomer(customerID).getCoupons();
		List<Coupon> temp = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getCategoryID() == category) {
				temp.add(coupon);
			}
		}
		return temp;
	}

	public List<Coupon> getCustomerCoupons(double maxPrice) {
		List<Coupon> coupons = customerDBDAO.getOneCustomer(customerID).getCoupons();
		List<Coupon> temp = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() < maxPrice) {
				temp.add(coupon);
			}
		}
		return temp;
	}

	public Customer getCustomerDetails() {
		return customerDBDAO.getOneCustomer(customerID);
	}

	public List<Customer> getAllCustomers() {
		return customerDBDAO.getAllCustomers();
	}

	@Override
	public String toString() {
		return "CustomerService login successfully!!";
	}

	public void printWrongMessage() {
		System.out.print("The detailes are wrong,CustomerService login failed...");
	}

}

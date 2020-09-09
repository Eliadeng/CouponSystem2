package com.johnbryce.CoupSys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbryce.CoupSys.beans.Company;
import com.johnbryce.CoupSys.beans.Coupon;
import com.johnbryce.CoupSys.beans.Customer;
import com.johnbryce.CoupSys.dbdao.CompanyDBDAO;
import com.johnbryce.CoupSys.dbdao.CouponDBDAO;
import com.johnbryce.CoupSys.dbdao.CustomerDBDAO;
import com.johnbryce.CoupSys.exceptions.AlreadyExistException;
import com.johnbryce.CoupSys.exceptions.NotAllowedException;
import com.johnbryce.CoupSys.exceptions.NotExistException;

@Service
public class AdminService extends ClientService {
	@Autowired
	private CompanyDBDAO companyDBDAO;

	@Autowired
	private CouponDBDAO couponDBDAO;

	@Autowired
	private CustomerDBDAO customerDBDAO;

	@Override
	public boolean login(String email, String password) {
		return email.equals("admin@admin.com") && password.equals("admin");
	}

	public boolean isCompanyExists(int id) {
		return companyDBDAO.isCompanyExists(id);
	}

	public void addCompany(Company company) throws AlreadyExistException {
		if (companyRepository.findByEmail(company.getEmail()) != null
				|| (companyRepository.findByName(company.getName()) != null)) {
			throw new AlreadyExistException();
		}
		companyDBDAO.addCompany(company);
	}

	public void updateCompany(Company company) throws NotExistException, NotAllowedException {
		if (companyRepository.findById(company.getId()) == null) {
			throw new NotExistException("The id doesn't exists");
		}
		Company company2 = companyRepository.getOne(company.getId());
		if (!company.getName().equals(company2.getName())) {
			throw new NotAllowedException("You are not allowed to change name company");
		}
		companyDBDAO.updateCompany(company);
	}

	public void deleteCompany(int companyID) throws NotExistException {
		if (!companyRepository.existsById(companyID)) {
			throw new NotExistException("The company doesn't exists in the system");
		}
		List<Coupon> originalCoupons = couponDBDAO.getAllCoupons();
		for (Coupon coupon : originalCoupons) {
			if (coupon.getCompanyID() == companyID) {
				couponDBDAO.deleteCoupon(coupon);
			}
		}
		companyDBDAO.deleteCompany(companyDBDAO.getOneCompany(companyID));
	}

	public List<Company> getAllCompanies() {
		return companyDBDAO.getAllCompanies();
	}

	public Company getOneCompany(int companyID) throws NotExistException {
		if (!companyDBDAO.isCompanyExists(companyID)) {
			throw new NotExistException("The company is not exists by this id");
		}
		return companyDBDAO.getOneCompany(companyID);
	}

	public void addCustomer(Customer customer) throws AlreadyExistException {
		if (customerRepository.findByEmail(customer.getEmail()) != null) {
			throw new AlreadyExistException("The customer is already exists");
		}
		customerDBDAO.addCustomer(customer);
	}

	public void updateCustomer(Customer customer) throws NotAllowedException {
		if (customerRepository.findById(customer.getId()) == null) {
			throw new NotAllowedException("The id doesn't exists in the system");
		}
		customerDBDAO.updateCustomer(customer);
	}

	public void deleteCustomer(Customer customer) throws NotExistException {
		if (customerRepository.findById(customer.getId()) == null) {
			throw new NotExistException("The ID doesn't exists in the system");
		}
		customerDBDAO.deleteCustomer(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerDBDAO.getAllCustomers();
	}

	public Customer getOneCustomer(int customerID) throws NotExistException {
		if (!customerRepository.existsById(customerID)) {
			throw new NotExistException("The customer doesn't exists in the system");
		}
		return customerDBDAO.getOneCustomer(customerID);
	}

	public void addCoupon(Coupon coupon) throws NotAllowedException {
		List<Coupon> coupons = couponDBDAO.getAllCoupons();
		for (Coupon coupon2 : coupons) {
			if (coupon2.getCompanyID() == coupon.getCompanyID()) {
				if (coupon2.getTitle().equals(coupon.getTitle())) {
					throw new NotAllowedException("the title is the same between the 2 coupons");
				}
			}
		}
		couponDBDAO.addCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) throws NotExistException {
		if (couponDBDAO.isCouponExists(coupon.getId()) == false) {
			throw new NotExistException("The Coupon doesn't exists in the system");
		}
		couponDBDAO.updateCoupon(coupon);
	}

	public void deleteCoupon(Coupon coupon) {
		couponDBDAO.deleteCoupon(coupon);
	}

	public List<Coupon> getAllCoupons() {
		return couponDBDAO.getAllCoupons();
	}

	public Coupon getOneCoupon(int couponID) {
		return couponDBDAO.getOneCoupon(couponID);
	}

	public Company getCompanyByEmailAndPassword(String email, String password) throws NotExistException {
		if (companyDBDAO.getCompanyByEmailAndPassword(email, password) == null) {
			throw new NotExistException("The company doesn't exists by this details");
		}
		return companyDBDAO.getCompanyByEmailAndPassword(email, password);
	}

	public boolean isCouponExists(int id) {
		return couponDBDAO.isCouponExists(id);
	}

	public boolean isCustomerExists(String email, String password) {
		return customerDBDAO.isCustomerExists(email, password);
	}

	@Override
	public String toString() {

		return "AdminService login successfully!!";
	}

}
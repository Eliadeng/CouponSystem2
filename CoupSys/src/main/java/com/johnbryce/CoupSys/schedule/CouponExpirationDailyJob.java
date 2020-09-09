package com.johnbryce.CoupSys.schedule;


import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.johnbryce.CoupSys.beans.Coupon;
import com.johnbryce.CoupSys.exceptions.NotExistException;
import com.johnbryce.CoupSys.service.AdminService;
import com.johnbryce.CoupSys.util.PrintUtils;

@Component
public class CouponExpirationDailyJob extends Thread {
	@Autowired
	AdminService adminService;

	@Autowired
	PrintUtils printUtils;

	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	public void deleteCouponsExpired() throws NotExistException {
		List<Coupon> coupons = adminService.getAllCoupons();
		for (int i = 0; i < coupons.size(); i++) {
			if (coupons.get(i).getEndDate().before(new Date())) {
				System.out.println("Going to delete coupon: " + coupons.get(i).getId());
				printUtils.printOneCoupon(coupons.get(i));
				adminService.deleteCoupon(coupons.get(i));
			}
		}
	}
}

package com.english_center.schedule;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.english_center.common.utils.Pagination;
import com.english_center.entity.UserCourse;
import com.english_center.service.UserCourseService;
import com.english_center.service.UserService;

@Component
public class Schedule {

	@Autowired
	UserCourseService userCourseService;

	@Autowired
	UserService userService;

//	@Scheduled(fixedRate = 4000)
//	public void scheduleOtp() throws Exception {
//		Date currentDate = new Date();
//
//		Users users = userService.findOne(1);
//		long durationInMillis = currentDate.getTime() - (users.getOtpCreatedAt().getTime() + 1000 * 60 * 3);
//
//		System.out.println(durationInMillis);
//	}

//	schedule chạy lúc 2h sáng mỗi ngày
	@Scheduled(cron = "0 0 2 * * ?")
	public void scheduleUserCourse() throws Exception {

		Date currentDate = new Date();

		List<UserCourse> listUserCourses = userCourseService.spGUserCourse(-1, 1, -1, 0, 0, new Pagination(0, 20), 0)
				.getResult();

		listUserCourses.forEach(x -> {
			long durationInMillis = currentDate.getTime() - x.getCreatedAt().getTime();
			long daysBetween = TimeUnit.MILLISECONDS.toDays(durationInMillis) + 1;

			if (daysBetween > 180) {

				try {
					System.out.println(daysBetween);
					userCourseService.spUUserCourse(x.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

	}
}

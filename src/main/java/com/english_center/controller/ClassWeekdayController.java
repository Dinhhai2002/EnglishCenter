package com.english_center.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.english_center.common.utils.StringErrorValue;
import com.english_center.entity.Class;
import com.english_center.entity.ClassWeekday;
import com.english_center.entity.Hour;
import com.english_center.request.CRUDClassWeekdayRequest;
import com.english_center.response.BaseResponse;
import com.english_center.response.ClassWeekdayResponse;
import com.english_center.service.ClassService;
import com.english_center.service.ClassWeekdayService;
import com.english_center.service.HourService;

@RestController
@RequestMapping("/api/v1/class-weekday")
public class ClassWeekdayController extends BaseController {
	@Autowired
	ClassWeekdayService classWeekdayService;

	@Autowired
	ClassService classService;

	@Autowired
	HourService hourService;

//	@SuppressWarnings("rawtypes")
//	@PostMapping("/create")
//	public ResponseEntity<BaseResponse> create(@Valid @RequestBody CRUDClassWeekdayRequest wrapper) throws Exception {
//
//		BaseResponse response = new BaseResponse();
//
//		Class checkClass = classService.findOne(wrapper.getClassId());
//
//		if (checkClass == null) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError("Lớp học không tồn tại!");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		if (wrapper.getWeekdayId() <= 0 || wrapper.getWeekdayId() > 7) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError("Ngày trong tuần không hợp lệ.Vui lòng chọn lại ngày trong tuần phù hợp!");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//		if (checkClass.getTeacherId() <= 0) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError(
//					"Lớp học hiện tại chưa gán cho bất kì giáo viên nào.Vui lòng gán giáo viên cho lớp học trước khi tạo lịch học!");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		ClassWeekday checkClassWeekday = classWeekdayService.findByAll(wrapper.getClassId(), wrapper.getWeekdayId(),
//				wrapper.getFromHour(), wrapper.getToHour());
//
//		if (checkClassWeekday != null) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError("Lớp học trong tuần đã tồn tại!");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		int fromHour = Integer.parseInt(wrapper.getFromHour().substring(0, 2));
//		int toHour = Integer.parseInt(wrapper.getToHour().substring(0, 2));
//		int fromMinute = Integer.parseInt(wrapper.getFromHour().substring(3, 5));
//		int toMinute = Integer.parseInt(wrapper.getToHour().substring(3, 5));
//
//		if (fromHour < 0 || fromHour > 23 || toHour < 0 || toHour > 23 || fromMinute < 0 || fromMinute > 59
//				|| toMinute < 0 || toMinute > 59) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError("Dữ liệu không hợp lệ");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		if (fromHour > toHour) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError("Thời gian kết thúc phải lớn hơn giờ bắt đầu");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		if (fromHour < 7 || toHour > 22) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError("Thời gian tạo phải nằm trong giờ làm việc.Phải nằm trong khoảng 07:00 - 22:00");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		if (((fromHour >= 12 && fromHour < 13) || (fromHour > 12 && fromHour <= 13 && fromMinute < 30))
//				|| ((toHour >= 12 && toHour < 13 && fromMinute > 0)
//						|| (toHour > 12 && toHour <= 13 && toMinute < 30))) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError(
//					"Không thể tạo thời gian khóa học trong khoảng thời từ 12h-13h30 Vì đây là thời gian nghỉ trưa!Vui lòng chọn khoảng thời gian khác!");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		if ((toMinute == 0 && (Math.abs((toHour - fromHour - 1)) * 60 + (Math.abs(fromMinute - toMinute))) != 90)
//				|| toMinute != 0 && (Math.abs((toHour - fromHour)) * 60 + (Math.abs(fromMinute - toMinute))) != 90) {
//			response.setStatus(HttpStatus.BAD_REQUEST);
//			response.setMessageError(
//					"Thời gian tiết học không hợp lệ.Khoảng thời gian bắt đầu và kết thúc phải là 1 giờ 30 phút!");
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		List<Class> listClass = classService.getAllByTeacherId(checkClass.getTeacherId());
//		// xử lí map kiểm tra giờ dạy của giáo viên
//		listClass.stream().map(x -> {
//			List<ClassWeekday> listClassWeekdays = new ArrayList<>();
//			try {
//				listClassWeekdays = classWeekdayService.getAllByClassId(x.getId());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			listClassWeekdays.stream().map(y -> {
//				int fromHourCheck = Integer.parseInt(y.getFromHour().substring(0, 2));
//				int toHourCheck = Integer.parseInt(y.getToHour().substring(0, 2));
//				int fromMinuteCheck = Integer.parseInt(y.getFromHour().substring(3, 5));
//				int toMinuteCheck = Integer.parseInt(y.getFromHour().substring(3, 5));
//
//				// check giờ và phút trùng nhau
//				if (y.getFromHour().equals(wrapper.getFromHour()) && y.getToHour().equals(wrapper.getToHour())) {
//					response.setStatus(HttpStatus.BAD_REQUEST);
//					response.setMessageError(
//							"Giáo viên lớp học này hiện tại đang có giờ ở một lớp học trong hệ thống.Vui lòng xét lại Giờ bắt đầu và kết thúc cho phù hợp!");
//					return new ResponseEntity<>(response, HttpStatus.OK);
//				}
//				System.out
//						.println((Math.abs(fromHour - fromHourCheck) * 60 + (Math.abs(fromMinute - fromMinuteCheck))));
//				// check khoảng thời gian giữa 2 tiết
//				if ((Math.abs(fromHour - fromHourCheck) * 60 + (Math.abs(fromMinute - fromMinuteCheck))) < 90) {
//					response.setStatus(HttpStatus.BAD_REQUEST);
//					response.setMessageError(
//							"Giáo viên lớp học này hiện tại đang có giờ ở một lớp học trong hệ thống.Vui lòng xét lại Giờ bắt đầu và kết thúc cho phù hợp!");
//					return new ResponseEntity<>(response, HttpStatus.OK);
//				}
//
//				return 0;
//			}).collect(Collectors.toList());
//
//			return 0;
//		}).collect(Collectors.toList());
//
//		// hàm map không return nên sau khi map thì kiểm tra status response để handle
//		if (response.getStatus() == 400) {
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
//
//		ClassWeekday classWeekday = new ClassWeekday();
//		classWeekday.setClassId(wrapper.getClassId());
//		classWeekday.setHourId(wrapper.getWeekdayId());
//		classWeekday.setFromHour(wrapper.getFromHour());
//		classWeekday.setToHour(wrapper.getToHour());
//
//		classWeekdayService.create(classWeekday);
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<ClassWeekdayResponse>> findOne(@PathVariable("id") int id) throws Exception {

		BaseResponse<ClassWeekdayResponse> response = new BaseResponse();

		ClassWeekday classWeekday = classWeekdayService.findOne(id);

		if (classWeekday == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CLASS_WEEKDAY_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setData(new ClassWeekdayResponse(classWeekday));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/create")
	public ResponseEntity<BaseResponse<ClassWeekdayResponse>> create(
			@Valid @RequestBody CRUDClassWeekdayRequest wrapper) throws Exception {

		BaseResponse<ClassWeekdayResponse> response = new BaseResponse();

		Class checkClass = classService.findOne(wrapper.getClassId());

		if (checkClass == null) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CLASS_NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (checkClass.getTeacherId() <= 0) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CLASS_IS_NOT_TEACHER);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

//		tạo biến để check điều kiện lớp học trong tuần
		int[] indexHolder = { 0 };
		if (wrapper.getListHourId().size() != 3) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.CLASS_WEEKDAY_IS_LIMITED_IN_WEEK);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		wrapper.getListHourId().stream().forEach(x -> {
			Hour hour = new Hour();
			try {
				hour = hourService.findOne(x);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (hour == null) {
				response.setStatus(HttpStatus.BAD_REQUEST);
				response.setMessageError(StringErrorValue.HOUR_NOT_FOUND);
				return;
			}
			indexHolder[0] += hour.getWeekdayId();

		});

		if (response.getStatus() == 400) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (indexHolder[0] != 9 && indexHolder[0] != 12) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessageError(StringErrorValue.THREE_HOUR_IS_FALSE);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		/*
		 * B1 : Lấy danh sách lớp học của giáo viên đang đảm nhận lớp mà bạn muốn thêm
		 * lịch B2 : Map listClass để lấy danh sách lịch học trong tuần từ bảng
		 * classWeekday B3 : Map listClassWeekdays để kiểm tra xem có lịch học nào bị
		 * trùng trong list lịch học truyền từ request xuống hay không? Nếu có thì báo
		 * lỗi giáo viên đứng lớp đó đã có lịch trong giờ này rồi!
		 */

		List<Class> listClass = classService.getAllByTeacherId(checkClass.getTeacherId());
		// xử lí map kiểm tra giờ dạy của giáo viên
		listClass.stream().map(x -> {
			List<ClassWeekday> listClassWeekdays = new ArrayList<>();
			try {
				listClassWeekdays = classWeekdayService.getAllByClassId(x.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}

			listClassWeekdays.stream().map(y -> {

				wrapper.getListHourId().stream().forEach(hourId -> {
					Hour hour = new Hour();
					try {
						hour = hourService.findOne(hourId);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (y.getHourId() == hourId) {
						response.setStatus(HttpStatus.BAD_REQUEST);
						response.setMessageError(String.format(
								"Giáo viên hiện tại đã có lịch học này ở lớp %s vào lúc %s đến %s.Vui lòng chọn lịch học khác cho phù hợp!",
								hour.getTime(), hour.getFromHour(), hour.getToHour()));
						return;
					}

				});

				return 0;
			}).collect(Collectors.toList());

			return 0;
		}).collect(Collectors.toList());

		// hàm map không return nên sau khi map thì kiểm tra status response để handle
		if (response.getStatus() == 400) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		/*
		 * stream để tạo lịch học cho lớp học này
		 */
		wrapper.getListHourId().stream().forEach(hourId -> {
			Hour hour = new Hour();
			try {
				hour = hourService.findOne(hourId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ClassWeekday classWeekday = new ClassWeekday();
			classWeekday.setClassId(wrapper.getClassId());
			classWeekday.setHourId(hourId);
			classWeekday.setFromHour(hour.getFromHour());
			classWeekday.setToHour(hour.getToHour());
			try {
				classWeekdayService.create(classWeekday);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

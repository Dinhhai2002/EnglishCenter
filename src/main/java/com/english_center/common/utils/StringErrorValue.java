package com.english_center.common.utils;

public class StringErrorValue {

	// user

	public static final String USER_NOT_FOUND = "Người dùng không tồn tại!";
	public static final String USER_IS_EXIST = "Người dùng đã tồn tại!";
	public static final String LOGIN_FAIL = "Tên tài khoản hoặc mật khẩu không chính xác!";
	public static final String USER_REGISTER_IS_AUTHENTICATING = "Tên tài khoản và email đang được người dùng khác đăng kí và đang trong quá trình xác thực!";
	public static final String NAME_USER_IS_EXIST = "Tên người dùng đã tồn tại.Vui lòng nhập tên người dùng khác!";
	public static final String MAIL_USER_IS_EXIST = "Email đã tồn tại.Vui lòng nhập Email khác!";
	public static final String PHONE_USER_IS_EXIST = "số điện thoại đã tồn tại.Vui lòng nhập số điện thoại khác!";
	public static final String USER_IS_LOCKED = "Người dùng hiện tại đã bị khóa,không thể truy cập vào hệ thống được!";
	public static final String ERROR_CONFIRM_PASSWORD_AND_CONFIRM = "mật khẩu mới và xác nhận mật khẩu không chính xác!";
	public static final String PASSWORD_IS_NOT_CORRECT = "mật khẩu hiện tại không chính xác!";
	public static final String USER_IS_NOT_TEACHER = "người dùng không phải là giáo viên!";
	public static final String USER_IS_NOT_ALLOW_REGISTER_COURSE = "Người dùng đang có quyền admin hoặc giáo viên.Không thể đăng ký khóa học này được!";
	public static final String OTP_IS_NOT_CORRECT = "Mã otp không chính xác!";
	public static final String OTP_IS_NOT_USING = "Bạn chưa thực hiện tạo mã OTP theo yêu cầu!";
	public static final String OTP_IS_EXPIRED = "Mã otp này đã hết hạn!";
	public static final String OTP_IS_NOT_CONFIRM = "Bạn chưa thực hiện bước xác thực otp để reset mật khẩu!";
	public static final String USER_NOT_LOCK = "Bạn không thể khóa chính tài khoản của bạn!";
	public static final String ACCOUNT_GOOLE_IS_NOT_PERMIT = "Tài khoản đăng nhập từ Google không thể thực hiện chức năng quên mật khẩu!";

	// Category Course
	public static final String CATEGORY_COURSE_NOT_FOUND = "Danh mục khóa học không tồn tại!";
	public static final String CATEGORY_COURSE_IS_EXIST = "Danh mục khóa học đã tồn tại!";
	public static final String CATEGORY_COURSE_IS_COURSE_ACTIVE = "Danh mục này đang có khóa học hoạt động.Vui lòng tắt trạng thái của các khóa học trước khi đổi trạng thái danh mục!";

	// Category Exam
	public static final String CATEGORY_EXAM_NOT_FOUND = "Danh mục kì thi không tồn tại!";
	public static final String CATEGORY_EXAM_IS_EXIST = "Danh mục kì thi đã tồn tại!";
	public static final String CATEGORY_EXAM_IS_EXAM_ACTIVE = "Danh mục này đang có đề thi hoạt động.Vui lòng tắt trạng thái của các đề thi trước khi đổi trạng thái danh mục!";

	// Exam
	public static final String EXAM_NOT_FOUND = "Bài thi không tồn tại!";
	public static final String EXAM_IS_EXIST = "Bài thi đã tồn tại!";
	public static final String NAME_EXAM_IS_EXIST = "Tên bài thi đã tồn tại!";
	public static final String EXAM_IS_NOT_LIST_QUESTIONS = "Bài thi chưa có danh sách câu hỏi.Vui lòng tải danh sách câu hỏi lên trước khi bật đề thi!";
	public static final String EXAM_IS_NOT_AUDIO = "Bài thi chưa có audio nge.Vui lòng tải audio lên trước khi bật đề thi!";

	// Topic
	public static final String TOPIC_NOT_FOUND = "Topic không tồn tại!";
	public static final String TOPIC_IS_EXIST = "Topic đã tồn tại!";
	public static final String NAME_TOPIC_IS_EXIST = "Tên Topic đã tồn tại!";

	// Class
	public static final String CLASS_NOT_FOUND = "Lớp học không tồn tại!";
	public static final String CLASS_IS_EXIST = "Lớp học đã tồn tại!";
	public static final String CLASS_IS_NOT_TEACHER = "Lớp học hiện tại chưa có giáo viên.Vui lòng chọn giáo viên trước khi chọn lịch học!";
	public static final String CHANGE_STATUS_CLASS_IS_NOT_TEACHER = "Lớp học chưa có giáo viên.Không thể bật hoạt động lớp học!";
	public static final String LIST_STUDENT_CLASS_IS_LIMITED = "Danh sách sinh viên thuộc lớp học này đã đủ số lượng.Vui lòng chọn lớp khác!";

	// Course
	public static final String COURSE_NOT_FOUND = "Khóa học không tồn tại!";
	public static final String COURSE_IS_EXIST = "Khóa học đã tồn tại!";
	public static final String NAME_COURSE_IS_EXIST = "Tên khóa học đã tồn tại!";
	public static final String COURSE_IS_CLASS_ACTIVE = "Khóa học đang có lớp học tồn tại.Vui lòng tắt trạng thái của lớp học thuộc khóa học trước khi tắt trạng thái khóa học!";
	public static final String COURSE_IS_USER_REGISTED = "Khóa học đang có người dùng đăng kí sử dụng.Không thể tắt khóa học này!";
	// Chapter
	public static final String CHAPTER_NOT_FOUND = "Chương học không tồn tại!";
	public static final String CHAPTER_IS_EXIST = "Chương học đã tồn tại!";
	public static final String CHAPTER_IS_LESSONS_ACTIVE = "Chương học đang có bài học tồn tại hoạt động.Vui lòng tắt trạng thái bài học thuộc chương học trước khi tắt trạng thái chương học!";

	// Lessons
	public static final String LESSONS_NOT_FOUND = "Bài học không tồn tại!";
	public static final String LESSONS_IS_EXIST = "Bài học đã tồn tại!";
	public static final String LESSONS_IS_NOT_VIDEO = "Bài học này hiện tại chưa có video nên không thể hoạt động được.Vui lòng cập nhật video trước khi đổi trạng thái hoạt động của bài học!";

	// note
	public static final String NOTE_NOT_FOUND = "Ghi chú không tồn tại!";

	// videoWatchHistory
	public static final String VIDEO_WATCH_HISTORY_NOT_FOUND = "Thời gian xem video người dùng không tồn tại!";

	// Level
	public static final String LEVEL_NOT_FOUND = "Level không tồn tại!";
	public static final String LEVEL_IS_EXIST = "Level đã tồn tại!";

	// UserCourse
	public static final String USER_COURSE_NOT_FOUND = "sinh viên thuộc khóa học không tồn tại!";
	public static final String USER_COURSE_IS_EXIST = "sinh viên thuộc khóa học đã tồn tại!";

	// Payment
	public static final String COURSE_IS_REGISTED = "Bạn đã đăng kí khóa học này rồi!";

	// classStudent
	public static final String STUDENT_IS_EXIST_IN_CLASS = "Học sinh đã tồn tại trong lớp học!";
	public static final String CLASS_STUDENT_NOT_FOUND = "Sinh viên trong lớp học không tồn tại!";

	// ClassWeekday
	public static final String CLASS_WEEKDAY_NOT_FOUND = "Lịch học không tồn tại!";
	public static final String CLASS_WEEKDAY_IS_EXIST = "Lịch học đã tồn tại!";
	public static final String CLASS_WEEKDAY_IS_LIMITED_IN_WEEK = "1 lớp học chỉ tạo được 3 tiết học trên 1 tuần Và phải chọn đủ 3 ngày trong tuần!";
	public static final String CLASS_IS_NOT_CLASS_WEEKDAY = "Lớp học chưa có lịch học.Không thể bật hoạt động lớp học!";

	// Hour
	public static final String HOUR_NOT_FOUND = "Giờ học không tồn tại!";
	public static final String THREE_HOUR_IS_FALSE = "3 tiết học phải nằm ở 3 thứ 2,4,6 hoặc 3,5,7!";

	// Comments
	public static final String COMMENTS_NOT_FOUND = "Bình luận không tìm thấy!";

	// ReplyComments
	public static final String REPLYCOMMENTS_NOT_FOUND = "ReplyComments không tìm thấy!";

	// results
	public static final String RESULTS_NOT_FOUND = "Bảng kết quả không tìm thấy!";

	// resultDetail
	public static final String RESULTS_DETAIL_NOT_FOUND = "Bảng kết quả chi tiết không tìm thấy!";

	// target
	public static final String TARGET_NOT_FOUND = "Mục tiêu không tìm thấy!";
	public static final String TIME_GREATER_CURRENT_DATE = "Thời gian phải lớn hơn ngày hiện tại!";

	// Languages
	public static final String LANGUAGES_NOT_FOUND = "Ngôn ngữ không tìm thấy!";
	public static final String LANGUAGES_IS_EXIST = "Ngôn ngữ đã tồn tại!";

	// Audio
	public static final String AUDIO_IS_NOT_SUPPORT_TYPE = "Chỉ hỗ trợ file mp3 và mp4!";

	// Post
	public static final String POST_NOT_FOUND = "Bài viết không tìm thấy!";

	// Rating
	public static final String RATING_NOT_FOUND = "Đánh giá không tìm thấy!";

	// promotion
	public static final String PROMOTION_NOT_FOUND = "khuyến mãi không tồn tại!";
	public static final String POINT_USER_NOT_PERMIT = "Số điểm của người dùng không đủ để áp dụng cho khuyến mãi này!";
	public static final String NUMBER_PROMOTION_NOT_PERMIT = "Số lượng khuyến mãi hoạt động tối đa là 5!";

	// Category Blog
	public static final String CATEGORY_BLOG_NOT_FOUND = "Danh mục Blog không tồn tại!";
	public static final String CATEGORY_BLOG_IS_EXIST = "Danh mục Blog đã tồn tại!";

	// banner
	public static final String BANNER_NOT_FOUND = "banner không tìm thấy!";

	// image
//	public static final String COMMENTS_NOT_FOUND = "Bình luận không tìm thấy!";

	public static String nameChapterIsExist(String nameCourse, String nameChapter) {
		return String.format("Tên chương học \"%s\" thuộc khóa học \"%s\" này đã tồn tại.Vui lòng đặt tên khác!",
				nameChapter, nameCourse);
	}

	public static String nameLessonsIsExist(String nameLessons, String nameChapter, String nameCourse) {
		return String.format(
				"Bài học có tên \"%s\" đã tồn tại ở khóa học \"%s\" thuộc chương học \"%s\". Vui lòng đặt tên khác!",
				nameLessons, nameCourse, nameChapter);
	}

	public static String lessonsIsActive(String nameLessons) {
		return String.format("Đã có bài học tên \"%s\" hoạt động.Bạn không thể bật hoạt động cho bài học này",
				nameLessons);
	}

}

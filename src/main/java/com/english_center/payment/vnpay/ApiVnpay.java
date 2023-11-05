//package com.english_center.payment.vnpay;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.TimeZone;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.english_center.response.BaseResponse;
//
//@RestController()
//@RequestMapping("/api/v1/authentication/vnpay")
//public class ApiVnpay {
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@GetMapping("/get-info")
//	public ResponseEntity<BaseResponse> create() throws Exception {
//
//		BaseResponse response = new BaseResponse();
//		String vnp_TxnRef = ConfigVnpay.getRandomNumber(8);
//		String vnp_TmnCode = ConfigVnpay.vnp_TmnCode;
//
//		long amount = 1000000;
//
//		Map<String, String> vnp_Params = new HashMap<>();
//
//		vnp_Params.put("vnp_Version", ConfigVnpay.vnp_Version);
//		vnp_Params.put("vnp_Command", ConfigVnpay.vnp_Command);
//		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//		vnp_Params.put("vnp_Amount", String.valueOf(amount));
//		vnp_Params.put("vnp_CurrCode", "VND");
//		vnp_Params.put("vnp_BankCode", "NCB");
//		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
//		vnp_Params.put("vnp_OrderType", "other");
//		vnp_Params.put("vnp_Locale", "vn");
//		vnp_Params.put("vnp_ReturnUrl", ConfigVnpay.vnp_ReturnUrl);
//		vnp_Params.put("vnp_IpAddr", "13.160.92.202");
//
//
//
//		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//		String vnp_CreateDate = formatter.format(cld.getTime());
//		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
//		cld.add(Calendar.MINUTE, 15);
//		String vnp_ExpireDate = formatter.format(cld.getTime());
//		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//		List fieldNames = new ArrayList(vnp_Params.keySet());
//		Collections.sort(fieldNames);
//		StringBuilder hashData = new StringBuilder();
//		StringBuilder query = new StringBuilder();
//		Iterator itr = fieldNames.iterator();
//		while (itr.hasNext()) {
//			String fieldName = (String) itr.next();
//			String fieldValue = (String) vnp_Params.get(fieldName);
//			if ((fieldValue != null) && (fieldValue.length() > 0)) {
//				// Build hash data
//				hashData.append(fieldName);
//				hashData.append('=');
//				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//				// Build query
//				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//				query.append('=');
//				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//				if (itr.hasNext()) {
//					query.append('&');
//					hashData.append('&');
//				}
//			}
//		}
//		String queryUrl = query.toString();
//		String vnp_SecureHash = ConfigVnpay.hmacSHA512(ConfigVnpay.secretKey, hashData.toString());
//		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//		String paymentUrl = ConfigVnpay.vnp_PayUrl + "?" + queryUrl;
//
//		response.setData(paymentUrl);
//
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//
//}

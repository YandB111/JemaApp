///* 
//*  Project : JemaApp
//*  Author  : Raj Khatri
//*  Date    : 06-May-2023
//*
//*/
//
//package com.jema.app.controllers;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//import javax.persistence.Tuple;
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
//import com.google.gson.Gson;
//import com.jema.app.dto.AttandanceDto;
//import com.jema.app.dto.AttendanceView;
//import com.jema.app.dto.PageRequestDTO;
//import com.jema.app.dto.PageResponseDTO;
//import com.jema.app.entities.Attendance;
//import com.jema.app.response.GenericResponse;
//import com.jema.app.service.AttendanceService;
//import com.jema.app.utils.AppUtils;
//import com.jema.app.utils.AttendanceUtils;
//import com.jema.app.utils.Constants;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import springfox.documentation.spring.web.json.Json;
//
//@Api(value = "Attendance Controller")
//@RestController
//public class SettingAttendanceController extends ApiController {
//
//	protected Logger logger = LoggerFactory.getLogger(SettingAttendanceController.class);
//
//	@Autowired
//	AttendanceService attendanceService;
//
//	@Autowired
//	AttendanceUtils attendanceUtils;
//
//	@Autowired
//	private EntityManager entityManager;
//
//	@Autowired
//	private Gson gson;
//
//	/*
//	 * ======================== Attendance ADD ==================================
//	 */
//	@CrossOrigin
//	@GetMapping(value = ATTENDANCE_ADD, produces = "application/json")
//	public ResponseEntity<?> add() {
//		logger.info("Request:In Attendance Controller for Add Attendance :{} ");
//		GenericResponse genericResponse = new GenericResponse();
//
//		List<Attendance> mAttendanceList = attendanceUtils.getAttendance();
//		attendanceService.save(mAttendanceList);
//
//		return new ResponseEntity<GenericResponse>(
//				genericResponse.getResponse(mAttendanceList, "Attendance successfully added", HttpStatus.OK),
//				HttpStatus.OK);
//
//	}
//
//	/*
//	 * ======================== Get All Attendance ======================
//	 */
//
//	@ApiOperation(value = "Get All Attendance with Pagination", response = Iterable.class)
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
//			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
//	@CrossOrigin
//	@PostMapping(value = ATTENDANCE_FIND_ALL, produces = "application/json")
//	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
//		logger.info("Rest request to get all Attendance {} ", pageRequestDTO);
//		Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, pageRequestDTO.getSort()).ignoreCase());
//		Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sortBy);
//
//		Page<Attendance> page;
//		Long recordsCount = 0l;
////		if (pageRequestDTO.getKeyword() == null || pageRequestDTO.getKeyword().trim().isEmpty()) {
////			page = attendanceService.findAll(pageable);
////		} else {
////			page = attendanceService.findAll(pageable);
////		}
//
//		String baseBuery = "select count(*) over() as total, e.name as name, a.id as id, a.emp_id as emp_id, "
//				+ "e.employeeid as employee_id,  e.designation designation, a.leave_type as leave_type, "
//				+ "lt.name leave_type_name, "
//				+ "a.leave_status as leave_status, a.date as date, d.name department from attendance a "
//				+ "left join employee e on a.emp_id = e.id "
//				+ "left join leavetype lt on a.leave_type = lt.id "
//				+ "left join department d on e.department = d.id";
//		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
//			baseBuery = baseBuery + " where e.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
////			query.setParameter("name", pageRequestDTO.getKeyword().trim());
//		}
//
//		baseBuery = baseBuery
//				+ " group by e.name, a.id, a.emp_id, e.employeeid, e.designation, d.name, a.leave_type, a.leave_status, a.date, lt.name order by a.id DESC";
//		// create a query to retrieve MyEntity objects
//		Query query = null;
//		try {
//			query = entityManager.createNativeQuery(baseBuery, Tuple.class);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// set the maximum number of results to retrieve (i.e., the page size)
//		query.setMaxResults(pageRequestDTO.getPageSize());
//
//		// set the index of the first result to retrieve (i.e., the offset)
//		query.setFirstResult(pageRequestDTO.getPageNumber() * pageRequestDTO.getPageSize());
//
//		// execute the query and obtain the list of entities for the requested page
//		List<Tuple> tuples = query.getResultList();
//
//		List<AttendanceView> dataList = AppUtils.parseTuple(tuples, AttendanceView.class, gson);
////		attendanceView.convertIntoDTO(tuples);
//
//		try {
//			recordsCount = dataList.get(0).getTotal();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
//		return onSuccess(obj, Constants.ATTENDANCE_FETCHED);
//	}
//
//	/*
//	 * ======================== Mark Attendance========================
//	 */
//
//	@ApiOperation(value = "Mark Attendance", response = Iterable.class)
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
//			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
//	@CrossOrigin
//	@PutMapping(value = ATTENDANCE_MARK, produces = "application/json")
//	public ResponseEntity<GenericResponse> updateStatus(@RequestBody List<Long> idsArrays,
//			@PathVariable Integer status) {
//		log.info("REST Request In Attendance Controller to update status {} {},", idsArrays, status);
//		int res = attendanceService.markAttendance(status, idsArrays);
//		GenericResponse response = new GenericResponse();
//		if (res > 0) {
//			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Attendance Successfully Marked");
//
//			return new ResponseEntity<>(response.getResponse("", "Attendance successfully Marked", HttpStatus.OK),
//					HttpStatus.OK);
//		} else {
//			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Attendance");
//
//			return new ResponseEntity<>(response.getResponse("", "Invalid Attendance", HttpStatus.OK), HttpStatus.OK);
//		}
//	}
//	
//	@CrossOrigin
//	 @PutMapping(value = ATTENDANCE_Status,produces = "applicaiton/json")
//	    public ResponseEntity<Object> updateAttendanceStatus(
//	            @PathVariable Long id,
//	            @RequestBody AttandanceDto request) {
//	        try {
//	            Attendance updatedAttendance = attendanceService.updateLeaveStatus(id, request.getLeaveStatus());
//	            return ResponseEntity.ok(updatedAttendance);
//	        } catch (NotFoundException e) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//	                    .body("Attendance not found with id: " + id);
//	        }
//	    }
//
//
//}

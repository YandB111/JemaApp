/* 
*  Project : Jema
*  Author  : Raj Khatri
*  Date    : 10-Mar-2023
*
*/

package com.jema.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jema.app.response.GenericResponse;

public abstract class ApiController {

	public final Logger log = LoggerFactory.getLogger(ApiController.class);

	protected static final String API_PATH = "/api/v1";
	public static final String LOGIN = API_PATH + "/user/authenticate";
	public static final String SIGNUP = API_PATH + "/user/signup";
	public static final String PASSWORD = API_PATH + "/user/authenticate";
	public static final String PASSWORD_CHANGE = API_PATH + "/user/passwordChange";

	public static final String DOCUMENT = API_PATH + "/document";
	public static final String DOCUMENT_UPLOAD = DOCUMENT + "/upload";

	public static final String BOARD_UNIVERSITY = API_PATH + "/boarduniversity";
	public static final String BOARD_UNIVERSITY_ADD = BOARD_UNIVERSITY + "/add";
	public static final String BOARD_UNIVERSITY_UPDATE = BOARD_UNIVERSITY + "/{id}";
	public static final String BOARD_UNIVERSITY_FIND_ALL = BOARD_UNIVERSITY + "/findAll";
	public static final String BOARD_UNIVERSITY_DELETE = BOARD_UNIVERSITY + "/delete";

	public static final String LEAVE_MANAGEMENT = API_PATH + "/leaveManagement";
	public static final String LEAVE_MANAGEMENT_ADD = LEAVE_MANAGEMENT + "/add";
	public static final String LEAVE_MANAGEMENT_FIND_ALL = LEAVE_MANAGEMENT + "/findAll";
	public static final String FIND_EMPLOYEE_ALL_LEAVES = LEAVE_MANAGEMENT + "/findEmployeeAllLeaves/{id}";

	public static final String LEAVE_TYPE = API_PATH + "/leaveType";
	public static final String LEAVE_TYPE_ADD = LEAVE_TYPE + "/add";
	public static final String LEAVE_TYPE_FIND_ONE = LEAVE_TYPE + "/findById/{id}";
	public static final String LEAVE_TYPE_UPDATE = LEAVE_TYPE + "/{id}";
	public static final String LEAVE_TYPE_FIND_ALL = LEAVE_TYPE + "/findAll";
	public static final String LEAVE_TYPE_DELETE = LEAVE_TYPE + "/delete";

	public static final String TAX = API_PATH + "/tax";
	public static final String TAX_ADD = TAX + "/add";
	public static final String TAX_FIND_ONE = TAX + "/findById/{id}";
	public static final String TAX_UPDATE = TAX + "/{id}";
	public static final String TAX_FIND_ALL = TAX + "/findAll";
	public static final String TAX_DELETE = TAX + "/delete";
	public static final String TAX_STATUS = TAX + "/status/{status}";

	public static final String ALLOWANCE = API_PATH + "/allowance";
	public static final String ALLOWANCE_ADD = ALLOWANCE + "/add";
	public static final String ALLOWANCE_FIND_ONE = ALLOWANCE + "/findById/{id}";
	public static final String ALLOWANCE_UPDATE = ALLOWANCE + "/{id}";
	public static final String ALLOWANCE_FIND_ALL = ALLOWANCE + "/findAll";
	public static final String ALLOWANCE_DELETE = ALLOWANCE + "/delete";
	public static final String ALLOWANCE_STATUS = ALLOWANCE + "/status/{status}";

	public static final String DOCUMENT_ADD = DOCUMENT + "/add";
	public static final String DOCUMENT_FIND_ONE = DOCUMENT + "/findById/{id}";
	public static final String DOCUMENT_UPDATE = DOCUMENT + "/{id}";
	public static final String DOCUMENT_FIND_ALL = DOCUMENT + "/findAll";
	public static final String DOCUMENT_DELETE = DOCUMENT + "/delete";
	public static final String DOCUMENT_STATUS = DOCUMENT + "/status/{status}";

	public static final String INCREMENT = API_PATH + "/increment";
	public static final String INCREMENT_ADD = INCREMENT + "/add";
	public static final String INCREMENT_UPDATE = INCREMENT + "/{id}";
	public static final String INCREMENT_FIND_ALL = INCREMENT + "/findAll/{employeeid}";

	public static final String TYPE = API_PATH + "/type";
	public static final String TYPE_ADD = TYPE + "/add";
	public static final String TYPE_FIND_ONE = TYPE + "/findById/{id}";
	public static final String TYPE_UPDATE = TYPE + "/{id}";
	public static final String TYPE_FIND_ALL = TYPE + "/findAll";
	public static final String TYPE_DELETE = TYPE + "/delete";

	public static final String ORDER_REASON = API_PATH + "/orderReason";
	public static final String ORDER_REASON_ADD = ORDER_REASON + "/add";
	public static final String ORDER_REASON_FIND_ONE = ORDER_REASON + "/findById/{id}";
	public static final String ORDER_REASON_UPDATE = ORDER_REASON + "/{id}";
	public static final String ORDER_REASON_FIND_ALL = ORDER_REASON + "/findAll";
	public static final String ORDER_REASON_DELETE = ORDER_REASON + "/delete";

	public static final String INVENTORY_SETTING_QUANTITY_LIMIT = API_PATH + "/inventorySetting/quantityLimit";
	public static final String INVENTORY_SETTING_QUANTITY_ADD = INVENTORY_SETTING_QUANTITY_LIMIT + "/add";
	public static final String INVENTORY_SETTING_QUANTITY_FIND_ONE = INVENTORY_SETTING_QUANTITY_LIMIT
			+ "/findById/{id}";
	public static final String INVENTORY_SETTING_QUANTITY_UPDATE = INVENTORY_SETTING_QUANTITY_LIMIT + "/{id}";
	public static final String INVENTORY_SETTING_QUANTITY_FIND_ALL = INVENTORY_SETTING_QUANTITY_LIMIT + "/findAll";
	public static final String INVENTORY_SETTING_QUANTITY_DELETE = INVENTORY_SETTING_QUANTITY_LIMIT + "/delete";

	public static final String INVENTORY_SETTING_TAX = API_PATH + "/inventorySetting/tax";
	public static final String INVENTORY_SETTING_TAX_ADD = INVENTORY_SETTING_TAX + "/add";
	public static final String INVENTORY_SETTING_TAX_FIND_ONE = INVENTORY_SETTING_TAX + "/findById/{id}";
	public static final String INVENTORY_SETTING_TAX_UPDATE = INVENTORY_SETTING_TAX + "/{id}";
	public static final String INVENTORY_SETTING_TAX_FIND_ALL = INVENTORY_SETTING_TAX + "/findAll";
	public static final String INVENTORY_SETTING_TAX_DELETE = INVENTORY_SETTING_TAX + "/delete";
	public static final String INVENTORY_SETTING_TAX_STATUS = INVENTORY_SETTING_TAX + "/status/{status}";

	public static final String CUSTOMER_SETTING_TAX = API_PATH + "/customerSetting/tax";
	public static final String CUSTOMER_SETTING_TAX_ADD = CUSTOMER_SETTING_TAX + "/add";
	public static final String CUSTOMER_SETTING_TAX_FIND_ONE = CUSTOMER_SETTING_TAX + "/findById/{id}";
	public static final String CUSTOMER_SETTING_TAX_UPDATE = CUSTOMER_SETTING_TAX + "/{id}";
	public static final String CUSTOMER_SETTING_TAX_FIND_ALL = CUSTOMER_SETTING_TAX + "/findAll";
	public static final String CUSTOMER_SETTING_TAX_DELETE = CUSTOMER_SETTING_TAX + "/delete";
	public static final String CUSTOMER_SETTING_TAX_STATUS = CUSTOMER_SETTING_TAX + "/status/{status}";

	public static final String ELUTION_CUSTOMER_SETTING_TAX = API_PATH + "/elution/customerSetting/tax";
	public static final String ELUTION_CUSTOMER_SETTING_TAX_ADD = ELUTION_CUSTOMER_SETTING_TAX + "/add";
	public static final String ELUTION_CUSTOMER_SETTING_TAX_FIND_ONE = ELUTION_CUSTOMER_SETTING_TAX + "/findById/{id}";
	public static final String ELUTION_CUSTOMER_SETTING_TAX_UPDATE = ELUTION_CUSTOMER_SETTING_TAX + "/{id}";
	public static final String ELUTION_CUSTOMER_SETTING_TAX_FIND_ALL = ELUTION_CUSTOMER_SETTING_TAX + "/findAll";
	public static final String ELUTION_CUSTOMER_SETTING_TAX_DELETE = ELUTION_CUSTOMER_SETTING_TAX + "/delete";
	public static final String ELUTION_CUSTOMER_SETTING_TAX_STATUS = ELUTION_CUSTOMER_SETTING_TAX + "/status/{status}";

	public static final String INVENTORY_SETTING_STATUS = API_PATH + "/inventorySetting/status";
	public static final String INVENTORY_SETTING_STATUS_ADD = INVENTORY_SETTING_STATUS + "/add";
	public static final String INVENTORY_SETTING_STATUS_FIND_ONE = INVENTORY_SETTING_STATUS + "/findById/{id}";
	public static final String INVENTORY_SETTING_STATUS_UPDATE = INVENTORY_SETTING_STATUS + "/{id}";
	public static final String INVENTORY_SETTING_STATUS_FIND_ALL = INVENTORY_SETTING_STATUS + "/findAll";
	public static final String INVENTORY_SETTING_STATUS_DELETE = INVENTORY_SETTING_STATUS + "/delete";
	public static final String INVENTORY_SETTING_STATUS_STATUS = INVENTORY_SETTING_STATUS + "/status/{status}";

	public static final String CUSTOMER_SETTING_STATUS = API_PATH + "/customerSetting/status";
	public static final String CUSTOMER_SETTING_STATUS_ADD = CUSTOMER_SETTING_STATUS + "/add";
	public static final String CUSTOMER_SETTING_STATUS_FIND_ONE = CUSTOMER_SETTING_STATUS + "/findById/{id}";
	public static final String CUSTOMER_SETTING_STATUS_UPDATE = CUSTOMER_SETTING_STATUS + "/{id}";
	public static final String CUSTOMER_SETTING_STATUS_FIND_ALL = CUSTOMER_SETTING_STATUS + "/findAll";
	public static final String CUSTOMER_SETTING_STATUS_DELETE = CUSTOMER_SETTING_STATUS + "/delete";
	public static final String CUSTOMER_SETTING_STATUS_STATUS = CUSTOMER_SETTING_STATUS + "/status/{status}";

	public static final String ELUTION_CUSTOMER_SETTING_STATUS = API_PATH + "/elution/customerSetting/status";
	public static final String ELUTION_CUSTOMER_SETTING_STATUS_ADD = ELUTION_CUSTOMER_SETTING_STATUS + "/add";
	public static final String ELUTION_CUSTOMER_SETTING_STATUS_FIND_ONE = ELUTION_CUSTOMER_SETTING_STATUS
			+ "/findById/{id}";
	public static final String ELUTION_CUSTOMER_SETTING_STATUS_UPDATE = ELUTION_CUSTOMER_SETTING_STATUS + "/{id}";
	public static final String ELUTION_CUSTOMER_SETTING_STATUS_FIND_ALL = ELUTION_CUSTOMER_SETTING_STATUS + "/findAll";
	public static final String ELUTION_CUSTOMER_SETTING_STATUS_DELETE = ELUTION_CUSTOMER_SETTING_STATUS + "/delete";
	public static final String ELUTION_CUSTOMER_SETTING_STATUS_STATUS = ELUTION_CUSTOMER_SETTING_STATUS
			+ "/status/{status}";

	public static final String ELUTION_MACHINE_SETTING_RAW = API_PATH + "/elution/machineSetting/raw";
	public static final String ELUTION_MACHINE_SETTING_RAW_ADD = ELUTION_MACHINE_SETTING_RAW + "/add";
	public static final String ELUTION_MACHINE_SETTING_RAW_FIND_ONE = ELUTION_MACHINE_SETTING_RAW + "/findById/{id}";
	public static final String ELUTION_MACHINE_SETTING_RAW_UPDATE = ELUTION_MACHINE_SETTING_RAW + "/{id}";
	public static final String ELUTION_MACHINE_SETTING_RAW_FIND_ALL = ELUTION_MACHINE_SETTING_RAW + "/findAll";
	public static final String ELUTION_MACHINE_SETTING_RAW_DELETE = ELUTION_MACHINE_SETTING_RAW + "/delete";
	public static final String ELUTION_MACHINE_SETTING_RAW_STATUS = ELUTION_MACHINE_SETTING_RAW + "/status/{status}";

	public static final String ELUTION_MACHINE_SERVICE = API_PATH + "/elution/machineService";
	public static final String ELUTION_MACHINE_SERVICE_ADD = ELUTION_MACHINE_SERVICE + "/add";
	public static final String ELUTION_MACHINE_SERVICE_FIND_ALL = ELUTION_MACHINE_SERVICE + "/findAll";
	public static final String ELUTION_MACHINE_UPDATE_LAST_DATES = ELUTION_MACHINE_SERVICE + "/extend/{id}";

	public static final String ELUTION_MACHINE_WORKING_STATUS = API_PATH + "/elution/machineWorkingStatus";
	public static final String ELUTION_MACHINE_WORKING_STATUS_ADD = ELUTION_MACHINE_WORKING_STATUS + "/add";
	public static final String ELUTION_MACHINE_WORKING_STATUS_FIND_ALL = ELUTION_MACHINE_WORKING_STATUS + "/findAll";

	public static final String ELUTION_CUSTOMER_SETTING_LEAD_STATUS = API_PATH + "/elution/customerSetting/leadStatus";
	public static final String ELUTION_CUSTOMER_SETTING_LEAD_STATUS_ADD = ELUTION_CUSTOMER_SETTING_LEAD_STATUS + "/add";
	public static final String ELUTION_CUSTOMER_SETTING_LEAD_STATUS_FIND_ONE = ELUTION_CUSTOMER_SETTING_LEAD_STATUS
			+ "/findById/{id}";
	public static final String ELUTION_CUSTOMER_SETTING_LEAD_STATUS_UPDATE = ELUTION_CUSTOMER_SETTING_LEAD_STATUS
			+ "/{id}";
	public static final String ELUTION_CUSTOMER_SETTING_LEAD_STATUS_FIND_ALL = ELUTION_CUSTOMER_SETTING_LEAD_STATUS
			+ "/findAll";
	public static final String ELUTION_CUSTOMER_SETTING_LEAD_STATUS_DELETE = ELUTION_CUSTOMER_SETTING_LEAD_STATUS
			+ "/delete";
	public static final String ELUTION_CUSTOMER_SETTING_LEAD_STATUS_STATUS = ELUTION_CUSTOMER_SETTING_LEAD_STATUS
			+ "/status/{status}";

	public static final String ATTENDANCE = API_PATH + "/attendance";
	public static final String ATTENDANCE_ADD = ATTENDANCE + "/add";
	public static final String ATTENDANCE_FIND_ALL = ATTENDANCE + "/findAll";
	public static final String ATTENDANCE_MARK = ATTENDANCE + "/mark/{status}";
	public static final String ATTENDANCE_Status = ATTENDANCE + "/mark/{leave-status}";

	public static final String SALARY = API_PATH + "/salary";
	public static final String SALARY_ADD = SALARY + "/add";
	public static final String SALARY_FIND_ALL = SALARY + "/findAll";
	public static final String FIND_EMPLOYEE_ALL_SALARY = SALARY + "/findEmployeeAllSalaries/{id}";
	public static final String SALARY_STATUS = SALARY + "/status/{status}";

	public static final String BRANCH = API_PATH + "/branch";
	public static final String BRANCH_ADD = BRANCH + "/add";
	public static final String BRANCH_UPDATE = BRANCH + "/{id}";
	public static final String BRANCH_FIND_ALL = BRANCH + "/findAll";
	public static final String BRANCH_FIND_ONE = BRANCH + "/findById/{id}";
	public static final String BRANCH_DELETE = BRANCH + "/delete";
	public static final String BRANCH_STATUS = BRANCH + "/status/{id}/{status}";

	public static final String ACCOUNT_DETAILS = API_PATH + "/account";
	public static final String ACCOUNT_DETAILS_ADD = ACCOUNT_DETAILS + "/add";
	public static final String ACCOUNT_DETAILS_UPDATE = ACCOUNT_DETAILS + "/{id}";
	public static final String ACCOUNT_DETAILS_FIND_ALL = ACCOUNT_DETAILS + "/findAll";
	public static final String ACCOUNT_DETAILS_FIND_ONE = ACCOUNT_DETAILS + "/findById/{id}";
	public static final String ACCOUNT_DETAILS_DELETE = ACCOUNT_DETAILS + "/delete";
	public static final String ACCOUNT_DETAILS_UPDATE_BALANCE = ACCOUNT_DETAILS + "/balance";
	public static final String ACCOUNT_PAYMENT = ACCOUNT_DETAILS + "/paymentById/{id}";
	public static final String ACCOUNT_PAYMENT_UPDATE = ACCOUNT_DETAILS + "/balance/transfer";
	public static final String ACCOUNT_PAYMENT_AMOUNTPAID = ACCOUNT_DETAILS + "/balance/amountPaid";

	public static final String ACCOUNT_DETAILS_BALANCE_TOTAL = ACCOUNT_DETAILS + "/calculateTotalBalance";

	public static final String DEPARTMENT = API_PATH + "/department";
	public static final String DEPARTMENT_ADD = DEPARTMENT + "/add";
	public static final String DEPARTMENT_UPDATE = DEPARTMENT + "/{id}";
	public static final String DEPARTMENT_FIND_ALL = DEPARTMENT + "/findAll";
	public static final String DEPARTMENT_FIND_ONE = DEPARTMENT + "/findById/{id}";
	public static final String DEPARTMENT_DELETE = DEPARTMENT + "/delete";
	public static final String DEPARTMENT_STATUS = DEPARTMENT + "/status/{id}/{status}";

	public static final String INVENTORY_REQUEST = API_PATH + "/inventoryRequest";
	public static final String INVENTORY_REQUEST_ADD = INVENTORY_REQUEST + "/add";
	public static final String INVENTORY_REQUEST_FIND_ALL = INVENTORY_REQUEST + "/findAll";
	public static final String INVENTORY_REQUEST_STATUS = INVENTORY_REQUEST + "/status/{id}/{status}";
	public static final String INVENTORY_REQUEST_INVOICE = INVENTORY_REQUEST + "/invoice";
	public static final String INVENTORY_REQUEST_FIND_ONE = INVENTORY_REQUEST + "/findById/{id}";
	public static final String INVENTORY_REQUEST_FIND_BY_VENDOR = INVENTORY_REQUEST + "/findByVendorId/{id}";
	public static final String INVENTORY_REQUEST_FIND_BY_VENDOR_GETALL = INVENTORY_REQUEST + "/findAllVendor";

	public static final String INVENTORY_REQUEST_MARK_OFF = INVENTORY_REQUEST + "/markOff";
	public static final String INVENTORY_REQUEST_CANCEL = INVENTORY_REQUEST + "/cancel";
	public static final String INVENTORY_REQUEST_RETURN = INVENTORY_REQUEST + "/return";
	public static final String INVENTORY_REQUEST_CANCELED_FIND_ONE = INVENTORY_REQUEST + "/canceled/findById/{id}";
	public static final String INVENTORY_REQUEST_RETURNED_FIND_ONE = INVENTORY_REQUEST + "/returned/findById/{id}";
	public static final String INVENTORY_REQUEST_PRICE_HISTORY = INVENTORY_REQUEST + "/priceHistory/add";
	public static final String INVENTORY_REQUEST_PRICE_HISTORY_FIND_ALL = INVENTORY_REQUEST
			+ "/priceHistory/findAll/{id}";
	public static final String INVENTORY_REQUEST_STATUS_HISTORY = INVENTORY_REQUEST + "/StatusHistory/findById/{id}";

	public static final String CUSTOMER_ORDER = API_PATH + "/customerOrder";
	public static final String CUSTOMER_ORDER_ADD = CUSTOMER_ORDER + "/add";
	public static final String CUSTOMER_ORDER_FIND_ALL = CUSTOMER_ORDER + "/findAll";
	public static final String CUSTOMER_ORDER_STATUS = CUSTOMER_ORDER + "/status/{id}/{status}";
	public static final String CUSTOMER_ORDER_INVOICE = CUSTOMER_ORDER + "/invoice";
	public static final String CUSTOMER_ORDER_FIND_ONE = CUSTOMER_ORDER + "/findById/{id}";
	public static final String CUSTOMER_ORDER_CANCEL = CUSTOMER_ORDER + "/cancel";
	public static final String CUSTOMER_ORDER_RETURN = CUSTOMER_ORDER + "/return";
	public static final String CUSTOMER_ORDER_MARK_OFF = CUSTOMER_ORDER + "/markOff";
	public static final String CUSTOMER_ORDER_STATUS_HISTORY = CUSTOMER_ORDER + "/StatusHistory/findById/{id}";
	public static final String CUSTOMER_ORDER_CANCELED_FIND_ONE = CUSTOMER_ORDER + "/canceled/findById/{id}";
	public static final String CUSTOMER_ORDER_RETURNED_FIND_ONE = CUSTOMER_ORDER + "/returned/findById/{id}";
	public static final String CUSTOMER_ORDER_PRICE_HISTORY = CUSTOMER_ORDER + "/priceHistory/add";
	public static final String CUSTOMER_ORDER_PRICE_HISTORY_FIND_ALL = CUSTOMER_ORDER + "/priceHistory/findAll/{id}";
	public static final String CUSTOMER_ORDER_FIND_BY_CUSTOMER = CUSTOMER_ORDER + "/findByCustomerId/{id}";
	public static final String CUSTOMER_ORDER_FIND_TOTALSUM_PRICE = CUSTOMER_ORDER + "/findTotal/prices";
	public static final String CUSTOMER_ORDER_FIND_TOTAL = CUSTOMER_ORDER + "/findTotal/prices";

	public static final String EMPLOYEE = API_PATH + "/employee";
	public static final String EMPLOYEE_ADD = EMPLOYEE + "/add";
	public static final String EMPLOYEE_UPDATE = EMPLOYEE + "/{id}";
	public static final String EMPLOYEE_FIND_ALL = EMPLOYEE + "/findAll";
	public static final String EMPLOYEE_FIND_ONE = EMPLOYEE + "/findById/{id}";
	public static final String EMPLOYEE_DELETE = EMPLOYEE + "/delete";
	public static final String EMPLOYEE_STATUS = EMPLOYEE + "/status/{id}/{status}";

	public static final String CHEMICAL = API_PATH + "/chemical";
	public static final String CHEMICAL_ADD = CHEMICAL + "/add";
	public static final String CHEMICAL_UPDATE = CHEMICAL + "/{id}";
	public static final String CHEMICAL_FIND_ALL = CHEMICAL + "/findAll";
	public static final String CHEMICAL_FIND_ONE = CHEMICAL + "/findById/{id}";
	public static final String CHEMICAL_DELETE = CHEMICAL + "/delete";
	public static final String CHEMICAL_STATUS = CHEMICAL + "/status/{id}/{status}";

	public static final String VENDOR = API_PATH + "/vendor";
	public static final String VENDOR_ADD = VENDOR + "/add";
	public static final String VENDOR_UPDATE = VENDOR + "/{id}";
	public static final String VENDOR_FIND_ALL = VENDOR + "/findAll";
	public static final String VENDOR_FIND_ALL_BY_CHEMICAL_ID = VENDOR + "/findAll/{id}";
	public static final String VENDOR_FIND_ONE = VENDOR + "/findById/{id}";
	public static final String VENDOR_DELETE = VENDOR + "/delete";
	public static final String VENDOR_STATUS = VENDOR + "/status/{id}/{status}";

	public static final String CUSTOMER = API_PATH + "/customer";
	public static final String CUSTOMER_ADD = CUSTOMER + "/add";
	public static final String CUSTOMER_UPDATE = CUSTOMER + "/{id}";
	public static final String CUSTOMER_FIND_ALL = CUSTOMER + "/findAll";
	public static final String CUSTOMER_FIND_ONE = CUSTOMER + "/findById/{id}";
	public static final String CUSTOMER_DELETE = CUSTOMER + "/delete";

	public static final String CUSTOMER_STATUS = CUSTOMER + "/status/{id}/{status}";
	public static final String CUSTOMER_BLOCK = CUSTOMER + "/block/{id}/{block}";

	public static final String ELUTION_CUSTOMER = API_PATH + "/elution/customer";
	public static final String ELUTION_CUSTOMER_ADD = ELUTION_CUSTOMER + "/add";
	public static final String ELUTION_CUSTOMER_UPDATE = ELUTION_CUSTOMER + "/{id}";
	public static final String ELUTION_CUSTOMER_FIND_ALL = ELUTION_CUSTOMER + "/findAll";
	public static final String ELUTION_CUSTOMER_FIND_ONE = ELUTION_CUSTOMER + "/findById/{id}";
	public static final String ELUTION_CUSTOMER_DELETE = ELUTION_CUSTOMER + "/delete";

	public static final String ELUTION_CUSTOMER_STATUS = ELUTION_CUSTOMER + "/status/{id}/{status}";
	public static final String ELUTION_CUSTOMER_BLOCK = ELUTION_CUSTOMER + "/block/{id}/{block}";

	public static final String ELUTION_MACHINE = API_PATH + "/elution/machine";
	public static final String ELUTION_MACHINE_ADD = ELUTION_MACHINE + "/add";
	public static final String ELUTION_MACHINE_UPDATE = ELUTION_MACHINE + "/{id}";
	public static final String ELUTION_MACHINE_FIND_ALL = ELUTION_MACHINE + "/findAll";
	public static final String ELUTION_MACHINE_DELETE = ELUTION_MACHINE + "/delete";
	public static final String ELUTION_MACHINE_FIND_ONE = ELUTION_MACHINE + "/findById/{id}";

	public static final String About_Info = API_PATH + "/setting/agreement";
	public static final String AboutAgreement = About_Info + "/add";

	public static final String About_add_get = About_Info + "/findAll";

	protected static final String Finanace_Account_ADD = API_PATH + "/financeRecord/add";
	protected static final String Finanace_Account_Update = API_PATH + "/financeRecord/update/{id}";
	protected static final String Finanace_Account_Delete = API_PATH + "/financeRecord/delete/{id}";
	protected static final String Finanace_Account_FindAll = API_PATH + "/financeRecord/findAll";
	protected static final String Finanace_Account_PayementType = API_PATH + "/financeRecordPaymentType/findAll";
	protected static final String Finanace_Account_RecordType = API_PATH + "/financeRecordType/findAll";
	protected static final String Finanace_Account_StatusType = API_PATH + "/financeRecordStatusType/findAll";
	protected static final String Finanace_Account_AddType = API_PATH + "/financeRecordAddType/findAll";
	protected static final String Finanace_Account_GETADMINTYPESALL = API_PATH + "/financeRecordGetAdminType/findAll";
	protected static final String FINANCE_DOCUMENT_UPLOAD = API_PATH + "/documentFileTypeAdd/findAll";
	protected static final String Finanace_Account_FILTER = API_PATH + "/financeRecordGetAdminType/filter";
	protected static final String Finanace_Account_FINANCERECORD = API_PATH
			+ "/financeRecordGetAdminType/financeRecordSum";
	protected static final String Finanace_Account_CALCULATE_BALANCE_ACCOUNT = API_PATH
			+ "/financeRecordBalanceAccouts/allAccounts";
	protected static final String Finanace_Account_BALANCE_CUSTOMERORDER_PRICE = API_PATH
			+ "/financeRecordBalanceAccouts/customerOrderPrice";
	protected static final String Finanace_Account_BALANCE_EMPLOYEE_TOTALSALARY = API_PATH
			+ "/financeRecordBalanceAccouts/employeeTotalSalary";
	protected static final String Finanace_Account_RECORD_FINDBYID = API_PATH + "/financeRecordFindBy/{id}";

	protected static final String ELUTION_Finanace_Account_ADD = API_PATH + "/elutionFinanceRecord/add";
	protected static final String ELUTION_Finanace_Account_Update = API_PATH + "/elutionFinanceRecord/update/{id}";
	protected static final String ELUTION_Finanace_Account_Delete = API_PATH + "/elutionFinanceRecord/delete/{id}";
	protected static final String ELUTION_Finanace_Account_FindAll = API_PATH + "/elutionFinanceRecord/findAll";
	protected static final String ELUTION_Finanace_Account_PayementType = API_PATH
			+ "/elutionFinanceRecordPaymentType/findAll";
	protected static final String ELUTION_Finanace_Account_RecordType = API_PATH + "/elutionFinanceRecordType/findAll";
	protected static final String ELUTION_Finanace_Account_StatusType = API_PATH
			+ "/elutionFinanceRecordStatusType/findAll";
	protected static final String ELUTION_Finanace_Account_AddType = API_PATH + "/elutionFinanceRecordAddType/findAll";
	protected static final String Elution_Finanace_Account_CALCULATE_BALANCE_ACCOUNT = API_PATH
			+ "/elutionFinanceAccountBalance/allAccounts";
	protected static final String Elution_Finanace_Account_BALANCE_CUSTOMERORDER_PRICE = API_PATH
			+ "/elutionFinanceCustomerOrder/priceTotal";
	protected static final String  Elution_Finanace_Account_BALANCE_EMPLOYEE_TOTALSALARY = API_PATH
			+ "/elutionFinanceEmployeeSalary/priceTotal";
	protected static final String ELUTION_Finanace_Account_RECORD_FINDBYID = API_PATH
			+ "/elutionFinanceRecordFindBy/{id}";
	protected static final String FELUTION_inanace_Account_GETADMINTYPESALL = API_PATH
			+ "/elutionFinanceRecordGetAdminType/findAll";
	protected static final String ELUTION_Finanace_Account_FILTER = API_PATH
			+ "/elutionFinanceRecordGetAdminType/filter";
	protected static final String ELUTION_Finanace_Account_FINANCERECORD = API_PATH
			+ "/elutionFinanceRecordGetAdminType/financeRecordSum";

	public static final String ELUTION_ORDER = API_PATH + "/elution/order";
	public static final String ELUTION_ORDER_ADD = ELUTION_ORDER + "/add";
	public static final String ELUTION_ORDER_FIND_ALL = ELUTION_ORDER + "/findAll";
	public static final String ELUTION_ORDER_FIND_ONE = ELUTION_ORDER + "/findById/{id}";
	public static final String ELUTION_ORDER_CANCEL = ELUTION_ORDER + "/cancel";
	public static final String ELUTION_ORDER_INVOICE = ELUTION_ORDER + "/invoice";
	public static final String ELUTION_ORDER_FIND_BY_CUSTOMER = ELUTION_ORDER + "/findByCustomerId/{id}";
	public static final String ELUTION_ORDER_STATUS = ELUTION_ORDER + "/status/{id}/{status}";
	public static final String ELUTION_ORDER_STATUS_HISTORY = ELUTION_ORDER + "/StatusHistory/findById/{id}";
	public static final String ELUTION_ORDER_CANCELED_FIND_ONE = ELUTION_ORDER + "/canceled/findById/{id}";

	public static final String ADD_INVOICE = API_PATH + "/accountInvoice/add";
	public static final String ADD_INVOICE_FINDALL = API_PATH + "/accountInvoiceRecord/findAll";
	public static final String ADD_INVOICE_UPDATE_BY_ID = API_PATH + "/accountInvoiceRecord/update/{id}";
	public static final String ADD_INVOICE_DOWNLOAD = API_PATH + "/accountInvoiceRecord/download/{id}";
	public static final String ADD_INVOICE_DELETE = API_PATH + "/accountInvoiceRecord/delete-invoice/{id}";
	public static final String ADD_INVOICE_FINDBYID = API_PATH + "/accountInvoiceRecord//{id}";

	public static final String ELUTION_ADD_INVOICE = API_PATH + "/elutionAccountInvoice/add";
	public static final String ELUTION_ADD_INVOICE_FINDALL = API_PATH + "/elutionAccountInvoiceRecord/findAll";
	public static final String ELUTION_ADD_INVOICE_UPDATE_BY_ID = API_PATH + "/elutionAccountInvoiceRecord/update/{id}";
	public static final String ELUTION_ADD_INVOICE_DOWNLOAD = API_PATH + "/elutionAccountInvoiceRecord/download/{id}";
	public static final String ELUTION_ADD_INVOICE_DELETE = API_PATH
			+ "/elutionAccountInvoiceRecord/delete-invoice/{id}";
	public static final String ELUTION_ADD_INVOICE_FINDBYID = API_PATH + "/elutionAccountInvoiceRecord/{id}";

	public static final String ACCOUNT_FINANCE_TAX = API_PATH + "/accountFinanceTax";
	public static final String ACCOUNT_FINANCE_ADD = ACCOUNT_FINANCE_TAX + "/add";
	public static final String ACCOUNT_FINANCE_UPDATE_BY_ID = ACCOUNT_FINANCE_TAX + "/update/{id}";
	public static final String ACCOUNT_FINANCE_DELETE_BY_ID = ACCOUNT_FINANCE_TAX + "/delete/{id}";
	public static final String ACCOUNT_FINANCE_GETALL = ACCOUNT_FINANCE_TAX + "/findall";
	public static final String ACCOUNT_FINANCE_GETALL_BY_TAXTPE = ACCOUNT_FINANCE_TAX + "/findby-tax-type/{taxType}";
	public static final String ACCOUNT_FINANCE_GETALL_BY_ID = ACCOUNT_FINANCE_TAX + "/api/account-finance-tax/{id}";
	protected static final String ADMIN_ADD_TYPE = API_PATH + "/financeRecord";
	protected static final String ADMIN_ADD_TYPE_ADD = API_PATH + "/add";
	protected static final String ADMIN_ADD_TYPE_UPDATE = API_PATH + "/update/{id}";
	protected static final String ADMIN_ADD_TYPE_DELETE = API_PATH + "/delete/{id}";
	protected static final String ADMIN_ADD_TYPE_FINDALL = API_PATH + "/findAll";

	protected static final String AUDIT_RECONSILE_GETALLDATA = API_PATH + "/auditAndReconsile/allData";
	protected static final String AUDIT_RECONSILE_GETALLRecords = API_PATH + "/auditAndReconsile/fndAll";
	protected static final String AUDIT_RECONSILE_GETVERICATION = API_PATH + "/auditAndReconsile/update-verified";
	protected static final String AUDIT_RECONSILE_FILTER_VERIFY = API_PATH + "/getByVerified";

	protected static final String RECONSILE_GETALLDATA = API_PATH + "/reconsile/allData";
	protected static final String RECONSILE_GETALLRecords = API_PATH + "/reconsile/fndAll";
	protected static final String RECONSILE_GETVERICATION = API_PATH + "/reconsile/update-verified";
	protected static final String RECONSILE_FILTER_VERIFY = API_PATH + "reconsile/getByVerified";

	protected static final String AUDIT_RECONSILE_VERIFY = API_PATH + "/auditAndReconsile/verify/{id}";

	public ResponseEntity<GenericResponse> onSuccess(Object result, String message) {
		GenericResponse genericResponse = new GenericResponse();
		log.info("result {}", result);
		return new ResponseEntity<>(genericResponse.getResponse(result, message, HttpStatus.OK), HttpStatus.OK);
	}

	public ResponseEntity<GenericResponse> onSuccess(String message) {
		log.info("on Success {}", message);
		GenericResponse genericResponse = new GenericResponse();
		return new ResponseEntity<>(genericResponse.getResponse(message, HttpStatus.OK), HttpStatus.OK);

	}

	public ResponseEntity<GenericResponse> onFailure(String message) {
		log.info("onFailure {}", message);
		GenericResponse genericResponse = new GenericResponse();
		return new ResponseEntity<>(genericResponse.getResponse(message, HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}
}

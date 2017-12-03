package com.zteict.web.phoneaccount.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import com.zteict.tool.common.Constant;
import com.zteict.tool.utils.JEscape;
import com.zteict.tool.utils.JsonUtil;

import com.zteict.web.phoneaccount.model.PhoneAccount;
import com.zteict.web.phoneaccount.service.PhoneAccountService;

import com.zteict.web.system.action.base.BaseController;
import com.zteict.web.system.model.SysUserBean;

@Controller
@RequestMapping(value = "/phoneaccount")
public class PhoneAccountController extends BaseController {

	@Autowired
	PhoneAccountService phoneaccountService;

	@RequestMapping(value = "/phoneaccount/phoneaccountList")
	public ModelAndView GroupList() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/phoneaccount/phoneaccount");

		return view;
	}

	
	
	@RequestMapping(value = "/getInfoCList")
	public void getInfoCList(HttpServletRequest request,
			HttpServletResponse response) {
		// String phoneaccountid = request.getParameter("phoneaccountid");

		/*
		 * int pageCount = Integer.parseInt(request.getParameter("rows"));//
		 * request.getParameter("pageCount"); int curPage =
		 * Integer.parseInt(request.getParameter("page"));
		 */

		String data = request.getParameter("data");
		JSONObject jsonIN;
		JSONObject jsonOut = new JSONObject();

		String rst = "";
		try {

			jsonIN = new JSONObject(data);
			
			
			SysUserBean user = (SysUserBean) request.getSession().getAttribute(Constant.SESSION_USER);
			if(user!=null)
			{
				jsonIN.put("compy_name", user.getName());
			}
			
			doGetList(jsonIN, jsonOut);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			try {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "");
				jsonOut.put("total", 0);
				jsonOut.put("datalist", "");
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}
		rst = jsonOut.toString();
		JsonUtil.responseOutWithJson(response, rst);

	}
	
	/**
	 * 页面-获取phoneaccount列表
	 * 
	 * @param clientType
	 * @param packageName
	 * @param curPage
	 *            当前页
	 * @param pageCount
	 *            每页多少条
	 * @return
	 */
	@RequestMapping(value = "/getInfoList")
	public void getInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		// String phoneaccountid = request.getParameter("phoneaccountid");

		/*
		 * int pageCount = Integer.parseInt(request.getParameter("rows"));//
		 * request.getParameter("pageCount"); int curPage =
		 * Integer.parseInt(request.getParameter("page"));
		 */

		String data = request.getParameter("data");
		JSONObject jsonIN;
		JSONObject jsonOut = new JSONObject();

		String rst = "";
		try {

			jsonIN = new JSONObject(data);
			doGetList(jsonIN, jsonOut);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			try {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "");
				jsonOut.put("total", 0);
				jsonOut.put("datalist", "");
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}
		rst = jsonOut.toString();
		JsonUtil.responseOutWithJson(response, rst);

	}

	private void doGetList(JSONObject jsonIN, JSONObject jsonOut)
			throws JSONException {

		String compy_name = jsonIN.optString("compy_name");
		String ip = jsonIN.optString("ip");
		String id = jsonIN.optString("id");
		String city = jsonIN.optString("city");

		int pageCount = jsonIN.optInt("rows");// request.getParameter("pageCount");
		int curPage = jsonIN.optInt("page");

		PhoneAccount query = new PhoneAccount();
		query.setPage(curPage);
		query.setPageCount(pageCount);

		query.setIp(ip);
		query.setCity(city);
		query.setAccountid(id);
		query.setCompany_name(compy_name);

		List<PhoneAccount> infos = phoneaccountService
				.getPhoneAccountPageList(query);
		int total = phoneaccountService.getPhoneAccountPageListCount(query);

		Gson gs = new Gson();
		String jsStr = gs.toJson(infos);

		jsonOut.put("ResponseCode", "200");
		jsonOut.put("ResponseMsg", "");
		jsonOut.put("total", total);
		jsonOut.put("datalist", jsStr);
	}

	/**
	 * 添加或者更新phoneaccount信息
	 * 
	 * @param request
	 * @param response
	 * @date 2016-6-22
	 * @author zj
	 */
	@RequestMapping(value = "/addOrUpdate")
	public void addOrUpdate(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		JSONObject jsonOut = new JSONObject();
		String data = request.getParameter("data");
		JSONObject jsonIN;

		try {

			jsonIN = new JSONObject(data);

			int recordid = jsonIN.optInt("recordid");
			String accountid = jsonIN.optString("accountid");
			String pass = jsonIN.optString("pass");
			String ip_refresh_interval = jsonIN
					.optString("ip_refresh_interval");
			String city = jsonIN.optString("city");
			String company_userid = jsonIN.optString("company_userid");

			PhoneAccount phoneaccount = new PhoneAccount();
			phoneaccount.setPass(pass);
			phoneaccount.setAccountid(accountid);
			phoneaccount.setIp_refresh_interval(ip_refresh_interval);
			phoneaccount.setCity(city);
			phoneaccount.setCompany_userid(company_userid);

			SysUserBean user = (SysUserBean) session
					.getAttribute(Constant.SESSION_USER);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());

			int rst = -1;

			if (recordid != 0) {
				phoneaccount = phoneaccountService
						.getPhoneAccountInfoById(recordid);
				phoneaccount.setUpdate_date(time);
				phoneaccount.setIp_refresh_interval(ip_refresh_interval);

				phoneaccount.setCity(city);
				phoneaccount.setAccountid(accountid);
				phoneaccount.setCompany_userid(company_userid);

				/*
				 * if (user != null) phoneaccount.setUpdatedby(user.getName());
				 */

				rst = phoneaccountService.updatePhoneAccount(phoneaccount);

			} else {
				/*
				 * if (user != null) phoneaccount.setCreator(user.getName());
				 */
				phoneaccount.setCreate_date(time);

				rst = phoneaccountService.addPhoneAccount(phoneaccount);
			}

			if (rst > 0) {
				jsonOut.put("ResponseCode", 200);
				jsonOut.put("ResponseMsg", "OK");
			} else {
				jsonOut.put("ResponseCode", 201);
				jsonOut.put("ResponseMsg", "FAILED");
			}

		} catch (Exception e2) {
			try {
				jsonOut.put("ResponseCode", "201");
				jsonOut.put("ResponseMsg", "FAILED");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		JsonUtil.responseOutWithJson(response, jsonOut.toString());

	}

	/**
	 * 删除phoneaccount
	 * 
	 * @param request
	 * @return
	 * @date 2016-8-22
	 */
	@RequestMapping(value = "/del")
	public @ResponseBody
	Map delBanner(HttpServletRequest request) {
		Map res = new HashMap();
		res.put("ResponseCode", "201");
		res.put("ResponseMsg", "删除失败");
		String data = request.getParameter("data");
		JSONObject jsonIN;

		try {

			jsonIN = new JSONObject(data);

			int recordid = jsonIN.optInt("recordid");

			if (phoneaccountService.deletePhoneAccount(recordid) > 0) {
				res.put("ResponseCode", "200");
				res.put("ResponseMsg", "删除成功");
			} else {
				res.put("ResponseCode", "201");
				res.put("ResponseMsg", "删除失败");
			}
		} catch (Exception e) {

		}

		return res;
	}

}

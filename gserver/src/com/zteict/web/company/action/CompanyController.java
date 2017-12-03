package com.zteict.web.company.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.zteict.tool.common.Constant;
import com.zteict.tool.utils.JEscape;
import com.zteict.tool.utils.JsonUtil;

import com.zteict.web.company.model.Company;
import com.zteict.web.company.service.CompanyService;

import com.zteict.web.system.action.base.BaseController;
import com.zteict.web.system.model.SysUserBean;

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {

	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/company/companyList")
	public ModelAndView GroupList() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/company/company");

		return view;
	}

	@RequestMapping(value = "/getDetail")
	public void getCompanyInfoByAccountId(HttpServletRequest request,
			HttpServletResponse response)
	{
		//String data = request.getParameter("data");
		JSONObject jsonIN;
		JSONObject jsonOut = new JSONObject();

		String rst = "";
		try {


			
			SysUserBean user = (SysUserBean) request.getSession().getAttribute(Constant.SESSION_USER);
			if(user!=null)
			{
				
			}

			Company detail= companyService.getCompanyInfoByAccountId(user.getUserid());

			
			
			Gson gs = new Gson();
			String jsStr = gs.toJson(detail);

			jsonOut.put("ResponseCode", "200");
			jsonOut.put("ResponseMsg", "");
			jsonOut.put("total", 1);
			jsonOut.put("datalist", jsStr);

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
	 * 页面-获取company列表
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
		// String companyid = request.getParameter("companyid");

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

			String company_name = jsonIN.optString("company_name");
			//String ip = jsonIN.optString("ip");
			String id = jsonIN.optString("id");
			//String city = jsonIN.optString("city");

			int pageCount = jsonIN.optInt("rows");// request.getParameter("pageCount");
			int curPage = jsonIN.optInt("page");

			Company query = new Company();
			query.setPage(curPage);
			query.setPageCount(pageCount);

			//query.setIp(ip);
			//query.setCity(city);
			query.setAccountid(id);
			query.setCompany_name(company_name);

			List<Company> infos = companyService
					.getCompanyPageList(query);
			int total = companyService.getCompanyPageListCount(query);

			Gson gs = new Gson();
			String jsStr = gs.toJson(infos);

			jsonOut.put("ResponseCode", "200");
			jsonOut.put("ResponseMsg", "");
			jsonOut.put("total", total);
			jsonOut.put("datalist", jsStr);

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
	 * 添加或者更新company信息
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
			
			String company_name = jsonIN.optString("company_name");
			String desc = jsonIN.optString("desc");
			String ip_desc = jsonIN.optString("ip_desc");

			Company company = new Company();
			company.setAccountid(accountid);
			company.setPass(pass);
			company.setIp_refresh_interval(ip_refresh_interval);
			company.setCompany_name(company_name);
			company.setDesc_info(desc);
			company.setIp_desc(ip_desc);

			SysUserBean user = (SysUserBean) session
					.getAttribute(Constant.SESSION_USER);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());

			int rst = -1;

			if (recordid != 0) {
				company = companyService
						.getCompanyInfoById(recordid);
				company.setUpdate_date(time);
				company.setIp_refresh_interval(ip_refresh_interval);
				company.setCompany_name(company_name);
				company.setDesc_info(desc);
				company.setIp_desc(ip_desc);

				/*
				 * if (user != null) company.setUpdatedby(user.getName());
				 */

				rst = companyService.updateCompany(company);

			} else {
				/*
				 * if (user != null) company.setCreator(user.getName());
				 */
				company.setCreate_date(time);

				rst = companyService.addCompany(company);
			}

			if (rst > 0) {
				jsonOut.put("ResponseCode", 200);
				jsonOut.put("ResponseMsg", "OK");
			} else {
				jsonOut.put("ResponseCode", 201);
				jsonOut.put("ResponseMsg", "FAILED");
			}

		} catch (Exception e2) {
			System.out.println(e2.getMessage());
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
	 * 删除company
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

			if (companyService.deleteCompany(recordid) > 0) {
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

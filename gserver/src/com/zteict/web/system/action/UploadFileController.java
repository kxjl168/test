package com.zteict.web.system.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zteict.tool.common.Md5EncryptFile;
import com.zteict.tool.config.ConfigReader;
import com.zteict.tool.httpPost.FormFieldKeyValuePair;
import com.zteict.tool.httpPost.HttpPostEmulator;
import com.zteict.tool.utils.FileUtil;


/**
 * 文件上传
 * 
 * @author kangyongji
 * 
 */
@Controller
public class UploadFileController {

	// 日志记录对象
	private Logger logger = Logger.getLogger(UploadFileController.class);

	@RequestMapping(value = "/fileUploadError")
	public void fileUploadError(HttpServletRequest request, HttpServletResponse response) {
		// 文件过大
		String uploadUrl = "205";
		response.setCharacterEncoding("utf-8");
		StringBuffer htmlBuffer = new StringBuffer();
		htmlBuffer
				.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">         ")
				.append("<html>                                                                                ")
				.append("<head>                                                                                                                        ")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />                                                     ")
				.append("<meta http-equiv=\"pragma\" content=\"no-cache\"/>                                                                            ")
				.append("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\"/>                                                    ")
				.append("<meta http-equiv=\"expires\" content=\"0\"/>                                                                                  ")
				.append("    <title>fileupload</title>                                                                                                 ")
				.append("                                                                                                                              ")
				.append("</head>                                                                                                                       ")
				.append("<body>                                                                                                                        ")
				.append("<input type='text' id='uploadUrl' value='" + uploadUrl
						+ "'  >                                                                 ")
				.append("</body>                                                                                                                       ")
				.append("</html>                                                                                                                       ");
		try {
			response.getWriter().println(htmlBuffer.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 存储图标文件
	 * 
	 * @param receiveFile
	 * @param request
	 * @param response
	 * @param model
	 * @date 2016-6-23
	 * @author zj
	 */
	@RequestMapping(value = "/logoUploadFile")
	public void uploadfile(@RequestParam(value = "logoUploadURL", required = false) MultipartFile receiveFile,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		String curDir = "logo"; // 使用配置的基础目录+ logo目录

		double file_max_size = 0.1; // 100k
		file_max_size = (Long) Math
				.round(ConfigReader.getInstance().getPropertyDouble("LOGO_MAXSIZE", file_max_size) * 1024 * 1024);

		long size = (long) file_max_size;

		String rst = doUploadToDir(receiveFile, curDir, size);

		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(rst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 存储大图标文件，惠民首页大图标
	 * 
	 * @param receiveFile
	 * @param request
	 * @param response
	 * @param model
	 * @date 2017-5-31
	 * @author zj
	 */
	@RequestMapping(value = "/biglogoUploadFile")
	public void biglogoUploadFile(@RequestParam(value = "logoUploadURL", required = false) MultipartFile receiveFile,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		String curDir = "logo"; // 使用配置的基础目录+ logo目录

		double file_max_size = 0.5; // 500k
		file_max_size = (Long) Math
				.round(ConfigReader.getInstance().getPropertyDouble("LOGO_MAXSIZE", file_max_size) * 1024 * 1024);

		long size = (long) file_max_size;

		String rst = doUploadToDir(receiveFile, curDir, size);

		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(rst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 存储其他文件文件
	 * 
	 * @param receiveFile
	 * @param request
	 * @param response
	 * @param model
	 * @date 2016-6-23
	 * @author zj
	 */
	@RequestMapping(value = "/UploadCKFile")
	public void UploadCKFile(@RequestParam(value = "upload", required = false) MultipartFile receiveFile,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		String CKEditorFuncNum=request.getParameter("CKEditorFuncNum");
		
		String curDir = "pic"; // 使用配置的基础目录+ logo目录

		double file_max_size = 10; // 10M
		file_max_size = (Long) Math
				.round(ConfigReader.getInstance().getPropertyDouble("PIC_MAXSIZE", file_max_size) * 1024 * 1024);
		long size = (long) file_max_size;

		String url = doUploadToDirCK(receiveFile, curDir, size);

		
		
	String rst=	"<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction("+CKEditorFuncNum+", '"+url+"', 'OK');</script>"; 
		if(!url.contains("http"))
			rst=	"<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction("+CKEditorFuncNum+", '"+url+"', 'FAIL');</script>";
		try {
			
			
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(rst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 存储其他文件文件
	 * 
	 * @param receiveFile
	 * @param request
	 * @param response
	 * @param model
	 * @date 2016-6-23
	 * @author zj
	 */
	@RequestMapping(value = "/UploadFile")
	public void UploadFile(@RequestParam(value = "fileUploadURL", required = false) MultipartFile receiveFile,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		String curDir = "apk"; // 使用配置的基础目录+ logo目录

		long file_max_size = 20L; // 20M
		file_max_size = (long) ConfigReader.getInstance().getPropertyInt("VID_MAXSIZE", (int) file_max_size) * 1024
				* 1024; // 配置文件为M

		String rst = doUploadToDir(receiveFile, curDir, file_max_size);

		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(rst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 上传至指定目录
	 * 
	 * @param receiveFile
	 * @param curDir
	 * @return
	 * @date 2016-6-24
	 * @author zj
	 */
	private String doUploadToDirCK(MultipartFile receiveFile, String curDir, Long MaxFileSize) {
		String fileName = receiveFile.getOriginalFilename();
		String returnCode = "200";
		String returnMsg = "";
		String NewfullName = ""; // 文件名
		String md5 = "";
		String httpURL = ""; // 下载路径
		try {
			if (fileName != null && !"".equals(fileName)) {

				if (MaxFileSize != 0 && receiveFile.getSize() <= MaxFileSize) {
					// 将文件保存到文件服务器
					String responsedata = "";
					// String serverUrl =
					// ConfigReader.getInstance().getProperty("CHANGEHEADUrl");
					String serverUrl = ConfigReader.getInstance().getProperty("FILE_SVR_PATH")
							+ "FileSvr/UploadFile.action";
					ArrayList<FormFieldKeyValuePair> fds = new ArrayList<FormFieldKeyValuePair>();

					FormFieldKeyValuePair p1 = new FormFieldKeyValuePair("path", curDir);
					fds.add(p1);

					try {
						responsedata = HttpPostEmulator.sendHttpPostRequestByMutiPartFile(serverUrl, fds,
								new MultipartFile[] { receiveFile });
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						// 文件大小超标
						returnCode = "201";
						returnMsg = "上传失败";
					}

					// 解析responsedate
					JSONObject jsonRes = new JSONObject(responsedata);
					if ((jsonRes.getString("ResponseCode")).equals("200")) {

						// jsonOut.put("ResponseCode", "200");
						// jsonOut.put("ResponseMsg", "OK");
						// jsonOut.put("FileUrl", httpURL);
						// jsonOut.put("relativeURL", relativeURL);
						// jsonOut.put("downURL", downURL);
						// jsonOut.put("httpDownURL", httpDownURL);

						returnCode = "200";
						returnMsg = "OK";
						httpURL = jsonRes.optString("relativeURL");
						md5 = jsonRes.optString("md5");
						NewfullName = jsonRes.optString("newname");

					} else {
						returnCode = "201";
						returnMsg = jsonRes.optString("ResponseMsg");
					}

				} else {
					// 文件大小超标
					returnCode = "205";
					returnMsg = "文件大小超标!最大限制为" + FileUtil.getReadableFileSize(String.valueOf(MaxFileSize));
				}

			} else {
				// 没有选择上传文件
				returnCode = "202";
			}
		} catch (Exception e) {
			logger.error(e.toString());
			// 上传文件失败
			returnCode = "203";

		}

		StringBuffer htmlBuffer = new StringBuffer();
		if (returnCode.equals("200")) {

			String HTTP_PATH = ConfigReader.getInstance().getProperty("FILE_SVR_HTTP_OUTER_PATH");

			htmlBuffer.append(HTTP_PATH+httpURL);
		} else {
			htmlBuffer.append(returnMsg);

		}

		return htmlBuffer.toString();

	}

	/**
	 * 上传至指定目录
	 * 
	 * @param receiveFile
	 * @param curDir
	 * @return
	 * @date 2016-6-24
	 * @author zj
	 */
	private String doUploadToDir(MultipartFile receiveFile, String curDir, Long MaxFileSize) {
		String fileName = receiveFile.getOriginalFilename();
		String returnCode = "200";
		String returnMsg = "";
		String NewfullName = ""; // 文件名
		String md5 = "";
		String httpURL = ""; // 下载路径
		try {
			if (fileName != null && !"".equals(fileName)) {

				if (MaxFileSize != 0 && receiveFile.getSize() <= MaxFileSize) {
					// 将文件保存到文件服务器
					String responsedata = "";
					// String serverUrl =
					// ConfigReader.getInstance().getProperty("CHANGEHEADUrl");
					String serverUrl = ConfigReader.getInstance().getProperty("FILE_SVR_PATH")
							+ "FileSvr/UploadFile.action";
					ArrayList<FormFieldKeyValuePair> fds = new ArrayList<FormFieldKeyValuePair>();

					FormFieldKeyValuePair p1 = new FormFieldKeyValuePair("path", curDir);
					fds.add(p1);

					try {
					
						logger.error("begin to upload to "+serverUrl);
						responsedata = HttpPostEmulator.sendHttpPostRequestByMutiPartFile(serverUrl, fds,
								new MultipartFile[] { receiveFile });
						
						logger.error("upload success! "+responsedata);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						// 文件大小超标
						returnCode = "201";
						returnMsg = "上传失败";
					}

					// 解析responsedate
					JSONObject jsonRes = new JSONObject(responsedata);
					if ((jsonRes.getString("ResponseCode")).equals("200")) {

						// jsonOut.put("ResponseCode", "200");
						// jsonOut.put("ResponseMsg", "OK");
						// jsonOut.put("FileUrl", httpURL);
						// jsonOut.put("relativeURL", relativeURL);
						// jsonOut.put("downURL", downURL);
						// jsonOut.put("httpDownURL", httpDownURL);

						returnCode = "200";
						returnMsg = "OK";
						httpURL = jsonRes.optString("relativeURL");
						md5 = jsonRes.optString("md5");
						NewfullName = jsonRes.optString("newname");

					} else {
						returnCode = "201";
						returnMsg = jsonRes.optString("ResponseMsg");
					}

				} else {
					// 文件大小超标
					returnCode = "205";
					returnMsg = "文件大小超标!最大限制为" + FileUtil.getReadableFileSize(String.valueOf(MaxFileSize));
				}

			} else {
				// 没有选择上传文件
				returnCode = "202";
			}
		} catch (Exception e) {
			logger.error(e.toString());
			// 上传文件失败
			returnCode = "203";

		}

		StringBuffer htmlBuffer = new StringBuffer();
		htmlBuffer
				.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">          ")
				.append("<html>                                                                                ")
				.append("<head>                                                                                                                        ")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />                                                     ")
				.append("<meta http-equiv=\"pragma\" content=\"no-cache\"/>                                                                            ")
				.append("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\"/>                                                    ")
				.append("<meta http-equiv=\"expires\" content=\"0\"/>                                                                                  ")
				.append("    <title>fileupload</title>                                                                                                 ")
				.append("                                                                                                                              ")
				.append("</head>                                                                                                                       ")
				.append("<body>                                                                                                                        ");

		if (returnCode.equals("200")) {

			htmlBuffer.append("<input type='text' id='filesize' value='" + receiveFile.getSize()
					+ "'  >                                                                    ");
			htmlBuffer.append("<input type='text' id='oldName' value='" + receiveFile.getOriginalFilename()
					+ "'  >                                                                    ");
			htmlBuffer
					.append("<input type='text' id='uploadUrl' value='" + httpURL
							+ "'  >                                                                    ")
					.append("<input type='text' id='fileMd5' value='" + md5
							+ "'  >                                                                    ")
					.append("<input type='text' id='newFileName' value='" + NewfullName
							+ "'  >                                                                    ");
		} else {
			htmlBuffer.append("<input type='text' id='uploadUrl' value='" + returnCode + "'  >  ");

			htmlBuffer.append("<input type='text' id='returnMsg' value='" + returnMsg + "'  >  ");
		}

		htmlBuffer
				.append("</body>                                                                                                                       ")
				.append("</html>                                                                                                                       ");

		return htmlBuffer.toString();

	}

	/**
	 * 上传至指定目录
	 * 
	 * @param receiveFile
	 * @param curDir
	 * @return
	 * @date 2016-6-24
	 * @author zj
	 */
	private String doUploadToDir_old(MultipartFile receiveFile, String curDir, Long MaxFileSize) {
		String fileName = receiveFile.getOriginalFilename();
		String returnCode = "200";
		String returnMsg = "";
		String NewfullName = ""; // 文件名
		String md5 = "";
		String httpURL = ""; // 下载路径
		try {
			if (fileName != null && !"".equals(fileName)) {

				if (MaxFileSize != 0 && receiveFile.getSize() <= MaxFileSize) {
					// 获取 服务器上文件存储绝对路径
					String uploadPath = ConfigReader.getInstance().getProperty("FILE_SAVE_PATH");
					// web访问相对路径
					String fileURL = ConfigReader.getInstance().getProperty("HTTP_PATH");

					String saveDir = null;
					saveDir = uploadPath + curDir + "/"; // 使用配置的基础目录+ logo目录
					File dir = new File(saveDir);
					if (!dir.exists())
						dir.mkdirs();
					File file = new File(fileName);
					String saveFileName = null;

					String os = System.getProperty("os.name");
					if (os != null && (os.startsWith("win") || os.startsWith("Win"))) {
						saveFileName = file.getName();
					} else {
						int begin = file.getName().lastIndexOf("\\");

						saveFileName = file.getName().substring(begin + 1);
					}

					String suffix = saveFileName.substring(saveFileName.lastIndexOf(".") + 1);
					StringBuffer newName = new StringBuffer();

					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");

					newName.append(format.format(new Date()));
					Random rod = new Random();
					for (int i = 0; i < 3; i++) {
						newName.append(rod.nextInt(10));
					}
					newName.append(saveFileName.substring(saveFileName.lastIndexOf(".")));

					// httpURL += "gaga_files/fileupload/upgradepackagefile/"
					// + newName;
					String httpURL2 = fileURL + curDir + "/" + newName;

					httpURL = curDir + "/" + newName;
					File temp = new File(saveDir + newName); // 存储相对路径，http路径从配置文件读取

					// 判断文件是否存在
					if (!temp.exists()) {
						File file2Server = new File(saveDir, newName.toString());
						receiveFile.transferTo(file2Server);

						if (os != null && (os.startsWith("win") || os.startsWith("Win"))) {
							saveFileName = file.getName();
						} else {
							int begin = file.getName().lastIndexOf("\\");

							saveFileName = file.getName().substring(begin + 1);
						}
						NewfullName = saveFileName;

						md5 = new Md5EncryptFile().getMD5(file2Server);

						logger.info("============FILE_SVR==============");
						logger.info("文件存放的绝对路径:" + saveDir);

						logger.info("文件访问路径值:  " + httpURL2);
						logger.info("数据库存储路径值:  " + httpURL);
						logger.info("============FILE_SVR===END===========");

						// 文件上传完成
						returnCode = "200";

					} else {
						// 文件已经存在
						returnCode = "206";
					}
				} else {
					// 文件大小超标
					returnCode = "205";
					returnMsg = "文件大小超标!最大限制为" + FileUtil.getReadableFileSize(String.valueOf(MaxFileSize));
				}

			} else {
				// 没有选择上传文件
				returnCode = "202";
			}
		} catch (Exception e) {
			logger.error(e.toString());
			// 上传文件失败
			returnCode = "203";

		}

		StringBuffer htmlBuffer = new StringBuffer();
		htmlBuffer
				.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">          ")
				.append("<html>                                                                                ")
				.append("<head>                                                                                                                        ")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />                                                     ")
				.append("<meta http-equiv=\"pragma\" content=\"no-cache\"/>                                                                            ")
				.append("<meta http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\"/>                                                    ")
				.append("<meta http-equiv=\"expires\" content=\"0\"/>                                                                                  ")
				.append("    <title>fileupload</title>                                                                                                 ")
				.append("                                                                                                                              ")
				.append("</head>                                                                                                                       ")
				.append("<body>                                                                                                                        ");

		if (returnCode.equals("200")) {
			htmlBuffer
					.append("<input type='text' id='uploadUrl' value='" + httpURL
							+ "'  >                                                                    ")
					.append("<input type='text' id='fileMd5' value='" + md5
							+ "'  >                                                                    ")
					.append("<input type='text' id='newFileName' value='" + NewfullName
							+ "'  >                                                                    ");
		} else {
			htmlBuffer.append("<input type='text' id='uploadUrl' value='" + returnCode + "'  >  ");

			htmlBuffer.append("<input type='text' id='returnMsg' value='" + returnMsg + "'  >  ");
		}

		htmlBuffer
				.append("</body>                                                                                                                       ")
				.append("</html>                                                                                                                       ");

		return htmlBuffer.toString();

	}

	/**
	 * 删除文件
	 * 
	 * @param request
	 * @param URL
	 * @return
	 */
	@RequestMapping(value = "/deleteFile")
	public @ResponseBody int deleteFile(HttpServletRequest request, String URL) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		return deleteFile(URL, realPath);
	}

	public int deleteFile(String URL, String realPath) {
		try {
			URL = new String(URL.getBytes("ISO-8859-1"), "UTF-8");
			logger.info("删除的url：" + URL);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
		}
		if ("" == URL || "".equals(URL)) {
			return 0;
		}
		String os = System.getProperty("os.name");
		if (os != null && (os.startsWith("win") || os.startsWith("Win"))) {
			String realPath_tymp = realPath.substring(0, realPath.lastIndexOf("\\", realPath.length()));
			realPath = realPath_tymp.substring(0, realPath_tymp.lastIndexOf("\\", realPath_tymp.length()))
					+ "\\gaga_files\\fileupload\\upgradepackagefile\\";
		} else {
			String realPath_tymp = realPath.substring(0, realPath.lastIndexOf("/", realPath.length()));
			realPath = realPath_tymp.substring(0, realPath_tymp.lastIndexOf("/", realPath_tymp.length()))
					+ "/gaga_files/fileupload/upgradepackagefile/";
		}
		URL = realPath + URL;
		File file = new File(URL);
		int a = -1;
		// 判断当前文件是否存在
		if (file.exists()) {
			// 判断文件删除是否成功
			if (file.delete()) {
				// 文件刪除成功
				a = 1;
				logger.info(URL + "文件删除成功！");
			} else {
				// 文件刪除失敗
				a = 2;
				logger.info(URL + "文件删除失败！");
			}
		} else {
			// 所刪文件不存在
			a = 0;
			logger.info(URL + "文件不存在！");
		}
		return a;
	}
}

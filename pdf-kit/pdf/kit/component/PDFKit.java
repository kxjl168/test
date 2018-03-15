package pdf.kit.component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pdf.kit.component.builder.HeaderFooterBuilder;
import pdf.kit.component.builder.PDFBuilder;
import pdf.kit.exception.FreeMarkerException;
import pdf.kit.exception.PDFException;
import pdf.kit.util.FreeMarkerUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Locale;

@Slf4j
public class PDFKit {

	public String exportToDoc(Object data, String PathName,
			String fileTemplateName, String outName) {

		setSaveFilePath(PathName + outName);

		// String templatePath=getPDFTemplatePath(fileName);
		// String templateFileName=getTemplateName(templatePath);
		// String templateFilePath=getTemplatePath(templatePath);
		if (StringUtils.isEmpty(PathName)) {
			throw new FreeMarkerException("templatePath can not be empty!");
		}
		try {
			System.out.println("output:"+saveFilePath);
			Configuration config = new Configuration(
					Configuration.VERSION_2_3_25);
			config.setDefaultEncoding("UTF-8");
			//config.setEncoding(Locale.forLanguageTag("LA"), "UTF-8");

			String path = FreeMarkerUtil.class.getClassLoader().getResource("")
					.getPath();
			String TemplatePath = path + "/templates/";

			config.setDirectoryForTemplateLoading(new File(TemplatePath));
			config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			config.setLogTemplateExceptions(false);
			Template template = config.getTemplate(fileTemplateName);
			StringWriter writer = new StringWriter();

			if (StringUtils.isEmpty(saveFilePath)) {
				saveFilePath = getDefaultSavePath(outName);
			}
			FileOutputStream fos = new FileOutputStream(saveFilePath);

			Writer out = new BufferedWriter(
					new OutputStreamWriter(fos, "utf-8"), 10240);
			template.process(data, out);
			writer.flush();
			// String html = writer.toString();
			return saveFilePath;
		} catch (Exception ex) {
			throw new FreeMarkerException("FreeMarkerUtil process fail", ex);
		}
	}

	// PDF页眉、页脚定制工具
	private HeaderFooterBuilder headerFooterBuilder;
	//最后保存的文件路径
	private String saveFilePath;

	/**
	 * @description 导出pdf到文件
	 * @param fileName
	 *            输出PDF文件名
	 * @param data
	 *            模板所需要的数据
	 * 
	 */
	public String exportToFile( Object data,String pathName, 
			String templateName,String fileName) {

		setSaveFilePath(pathName + fileName);
		String htmlData = FreeMarkerUtil.getContent( templateName,
				data);
		if (StringUtils.isEmpty(saveFilePath)) {
			saveFilePath = getDefaultSavePath(fileName);
		}
		File file = new File(saveFilePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileOutputStream outputStream = null;
		try {
			System.out.println("output:"+saveFilePath);
			// 设置输出路径
			outputStream = new FileOutputStream(saveFilePath);
			// 设置文档大小
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			// 设置页眉页脚
			PDFBuilder builder = new PDFBuilder(headerFooterBuilder, data);
			builder.setPresentFontSize(10);
			writer.setPageEvent(builder);

			// 输出为PDF文件
			convertToPDF(writer, document, htmlData);
		} catch (Exception ex) {
			throw new PDFException("PDF export to File fail", ex);
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
		return saveFilePath;

	}

	/**
	 * 生成PDF到输出流中（ServletOutputStream用于下载PDF）
	 * 
	 * @param ftlPath
	 *            ftl模板文件的路径（不含文件名）
	 * @param data
	 *            输入到FTL中的数据
	 * @param response
	 *            HttpServletResponse
	 * @return
	 */
	public OutputStream exportToResponse(String ftlPath, Object data,
			HttpServletResponse response) {

		// String html= FreeMarkerUtil.getContent(ftlPath,data);

		try {
			OutputStream out = null;
			/*
			 * ITextRenderer render = null; out = response.getOutputStream();
			 * //设置文档大小 Document document = new Document(PageSize.A4); PdfWriter
			 * writer = PdfWriter.getInstance(document, out); //设置页眉页脚
			 * PDFBuilder builder = new PDFBuilder(headerFooterBuilder,data);
			 * writer.setPageEvent(builder); //输出为PDF文件
			 * convertToPDF(writer,document,html);
			 */
			return out;
		} catch (Exception ex) {
			throw new PDFException("PDF export to response fail", ex);
		}

	}

	
	class myFontProvider extends XMLWorkerFontProvider {
		
		
		public myFontProvider(String fontPath) {
			// TODO Auto-generated constructor stub
			super(fontPath);
		}
		
		
		  @Override
		  public Font getFont(final String fontname, final String encoding,
		    final boolean embedded, final float size, final int style,
		    final BaseColor color) {
		   BaseFont bf = null;
		   try {
		    bf = BaseFont.createFont("LeelawUI","UFT-8",
		      BaseFont.NOT_EMBEDDED);
		   } catch (DocumentException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }
		   Font font = new Font(bf, size, style, color);
		   font.setColor(color);
		   return font;
		  }
		 }
	
	/**
	 * @description PDF文件生成
	 */
	private void convertToPDF(PdfWriter writer, Document document,
			String htmlString) {
		// 获取字体路径
		String fontPath = getFontPath();
		document.open();
		InputStream css= XMLWorkerHelper.class.getResourceAsStream("/default.css");
		
	/*	System.out.println("htmlString-len:"+htmlString.length());
		try {
			System.out.println("htmlString-byte-len:"+htmlString.getBytes("utf-8").length);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		String path = FreeMarkerUtil.class.getClassLoader().getResource("")
				.getPath();
		
		try {
			//大部门css样式写在ftl中才有用
			String cssPath = path + "/templates/pdf.css";
			InputStream css2=new FileInputStream(cssPath);
			
			
			XMLWorkerHelper.getInstance().parseXHtml(writer, document,
					new ByteArrayInputStream(htmlString.getBytes("utf-8")),
					css2,
					Charset.forName("UTF-8"),
					new XMLWorkerFontProvider(fontPath));
		} catch (IOException e) {
			e.printStackTrace();
			throw new PDFException("PDF文件生成异常", e);
		} finally {
			document.close();
		}

	}

	/**
	 * @description 创建默认保存路径
	 */
	private String getDefaultSavePath(String fileName) {
		String classpath = PDFKit.class.getClassLoader().getResource("")
				.getPath();
		String saveFilePath = classpath + "pdf/" + fileName;
		File f = new File(saveFilePath);
		if (!f.getParentFile().exists()) {
			f.mkdirs();
		}
		return saveFilePath;
	}

	/**
	 * @description 获取字体设置路径
	 */
	public static String getFontPath() {
		String classpath = PDFKit.class.getClassLoader().getResource("")
				.getPath();
		String fontpath = classpath + "fonts/";
	//	fontpath=fontpath.substring(1);
		//System.out.println("fpath:"+fontpath);
		return fontpath;
	}

	public HeaderFooterBuilder getHeaderFooterBuilder() {
		return headerFooterBuilder;
	}

	public void setHeaderFooterBuilder(HeaderFooterBuilder headerFooterBuilder) {
		this.headerFooterBuilder = headerFooterBuilder;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

}
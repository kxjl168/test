package pdf.kit.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pdf.kit.Col;
import pdf.kit.PdfHelper;
import pdf.kit.ReportDetailData;

public class Main {

	public static void main(String[] args) {

		List<TestObject> ds = new ArrayList<TestObject>();
		for (int i = 0; i < 10; i++) {
			TestObject t1 = new TestObject("1123123", "2111", "3", "4");
			ds.add(t1);
		}

		ReportDetailData dData = new ReportDetailData();
		dData.getLstdata().addAll(ds);
		dData.setTitle("detail22~");
		//自动添加序列id
		dData.setColname(Arrays.asList(new Col[] { new Col("id", 50),new Col("head1", 50),
				new Col("head2", 50), new Col("head3", 150),new Col("head4", 150) }));
		dData.setColattr(Arrays.asList(new String[] {"col1","col2","col3","col4"}));
		
		

		String title="title";
		List<ReportDetailData> datas=new ArrayList<ReportDetailData>();
		datas.add(dData);
		datas.add(dData);
		String lang="en_US";
		String fileType="pdf";//doc
		String fileName="1.pdf";
		
		/*String fileType="doc";//doc
		String fileName="1.doc";
		*/
	
		PdfHelper.saveToDetailFile(title, datas, lang, fileType, fileName);

	}

	private static void TestData() {

	}
}

package pdf.kit.component.chart.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import pdf.kit.component.chart.AbstractChart;
import pdf.kit.component.chart.CType;

/**
 * Created by fgm on 2017/5/15.
 * 设置默认的画图工具
 *
 */
@Slf4j
public class DefaultBarChart extends AbstractChart {
    private int width;
    private int height;
    
    
    public DefaultBarChart(){
    	type=CType.BAR;
    }
    
    protected void initPlot(JFreeChart chart) {

    	
    	
        CategoryPlot plot = chart.getCategoryPlot();
        super.initDefaultXYPlot(plot);
        //设置节点的值显示
      
        
        BarRenderer bRender=new BarRenderer();
           bRender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        bRender.setBaseItemLabelsVisible(true);
        bRender.setBasePositiveItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        //bRender.setItemMargin(0.5);//同组间的距离
        bRender.setMaximumBarWidth(0.05f); // 单个的最大宽度
        
        plot.setRenderer(bRender);
     
        plot.setBackgroundPaint(new Color(0xFF,0xFF,0xFF));
        
        plot.setOutlinePaint(new Color(0xFF,0xFF,0xFF));
        
        
    	CategoryAxis domainAxis = plot.getDomainAxis();
		
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);// 横轴
																				// lable
				
     
    }
    @Override
    public int getWidth() {
        if(width==0){
            return super.getWidth();
        }
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        if(height==0){
            return super.getHeight();
        }
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }
}

package pdf.kit.component.chart.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PieLabelDistributor;
import org.jfree.chart.plot.PieLabelRecord;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
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
public class DefaultPieChart extends AbstractChart {
    private int width;
    private int height;
    
    
    public DefaultPieChart(){
    	type=CType.PIE;
    }
    
    protected void initPlot(JFreeChart chart) {

    	  PiePlot plot =(PiePlot) chart.getPlot();
    
    	 plot.setLabelFont(getFont(Font.PLAIN, 13));
        plot.setLabelGenerator(new  StandardPieSectionLabelGenerator("{0} ({2} percent)"));  
     //   plot.setLabelBackgroundPaint(new Color(20, 20, 220));  
        plot.setLegendLabelToolTipGenerator(new StandardPieSectionLabelGenerator("Tooltip for legend item {0}"));  
        
        
        plot.setBackgroundPaint(new Color(0xFF,0xFF,0xFF));
        
        plot.setOutlinePaint(new Color(0xFF,0xFF,0xFF));
       
        //指定 section 轮廓线的颜色
        plot.setBaseSectionOutlinePaint(new Color(0xFF, 0xFF, 0xFF)); 
        plot.setSectionOutlineStroke(new BasicStroke(2)); 
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

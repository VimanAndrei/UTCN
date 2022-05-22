package View;

import Model.Parfume;
import Model.ParfumeService;
import Model.ParfumeStore;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class BarChart extends JFrame {
    private ParfumeService parfumeService;

    public BarChart(ParfumeService parfumeService ) {
        this.parfumeService=parfumeService;
        initUI();
    }

    private void initUI() {
        CategoryDataset dataset = createDataset();

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
    }
    private CategoryDataset createDataset() {

        var dataset = new DefaultCategoryDataset();
        for (ParfumeStore ps:parfumeService.getParfumeStoreList()){
            for (Parfume p:ps.getParfumes()) {
                dataset.setValue(p.getParfumeInfo().getPrice(), p.getParfumeInfo().getManufacturerName(), p.getParfumeName()+" din "+ps.getStoreName());
            }
        }

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart barChart = ChartFactory.createBarChart(
                "Pret produse",
                "",
                "Pret",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }

}



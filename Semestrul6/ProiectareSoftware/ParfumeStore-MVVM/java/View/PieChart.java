package View;

import Model.Parfume;
import Model.ParfumeService;
import Model.ParfumeStore;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;


public class PieChart extends JFrame {
    private ParfumeService parfumeService;

    public PieChart(ParfumeService parfumeService ) {
        this.parfumeService=parfumeService;
        initUI();
    }

    private void initUI() {

        DefaultPieDataset dataset = createDataset();

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Produse vandute");
        setLocationRelativeTo(null);
    }

    private DefaultPieDataset createDataset() {

        var dataset =  new DefaultPieDataset();
        for (ParfumeStore ps:parfumeService.getParfumeStoreList()){
            double sellProducts = 0;
            for (Parfume p:ps.getParfumes()) {
                sellProducts+=p.getParfumeInfo().getNumberOfSoldCopies();
            }
            dataset.setValue(ps.getStoreName(),sellProducts);
        }

        return dataset;
    }

    private JFreeChart createChart(DefaultPieDataset dataset) {

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Produse Vandute",
                dataset,
                false, true, false);

        return pieChart;
    }

}

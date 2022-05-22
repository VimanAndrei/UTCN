package ViewModel.CommandsEmployee;

import Model.ParfumeService;
import View.BarChart;
import View.BarChartDisponibility;
import View.PieChart;
import ViewModel.ICommand;
import ViewModel.VMEmployee;

public class ChartCommand implements ICommand {
    private VMEmployee vmEmployee;
    private ParfumeService parfumeService;

    public ChartCommand(VMEmployee vmEmployee, ParfumeService parfumeService) {
        this.vmEmployee = vmEmployee;
        this.parfumeService = parfumeService;
    }

    @Override
    public void Execute() {
        var barChart = new BarChart(parfumeService);
        barChart.setVisible(true);

        var pieChart = new PieChart(parfumeService);
        pieChart.setVisible(true);

        var barChartDisponibility = new BarChartDisponibility(parfumeService);
        barChartDisponibility.setVisible(true);

    }
}

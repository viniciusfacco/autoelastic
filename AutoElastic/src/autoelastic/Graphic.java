/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoelastic;

import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author vinicius.rodrigues
 */
public class Graphic {
    
    private JPanel grafico;
    private ChartPanel myChartPanel;
    private String[] legendas;
    private DefaultCategoryDataset dataset;
    private DefaultValueDataset valuedataset;
    private ThermometerPlot termometro;
    
    public Graphic(JPanel painelline){
        grafico = painelline;   
        legendas = new String[] {"Used CPU","Available CPU","Maximum Threshold","Minimum Threshold"};
        dataset = new DefaultCategoryDataset();
        //popula_dataset();
        criaGraficoLine(grafico);
        //criaGraficoBar(graficos[1]);
        //criaGraficoThermometer(graficos[2]);
    }

    private void criaGraficoLine(JPanel grafico) {
        //CategoryDataset cds = createDataset();
        String titulo = "Monitoring";
        String eixoy = "CPU";
        String txt_legenda = "";
        boolean legenda = true;
        boolean tooltips = true;
        boolean urls = false;
        //JFreeChart graf = ChartFactory.createBarChart3D(titulo, txt_legenda, eixoy, cds, PlotOrientation.VERTICAL, legenda, tooltips, urls);
        JFreeChart graf = ChartFactory.createLineChart(titulo, txt_legenda, eixoy, dataset, PlotOrientation.VERTICAL, legenda, tooltips, urls);
        myChartPanel = new ChartPanel(graf, true);
        myChartPanel.setSize(grafico.getWidth(), grafico.getHeight());
        myChartPanel.setVisible(true);
        grafico.removeAll();
        grafico.add(myChartPanel);
        grafico.revalidate();
        grafico.repaint();
    }
    
     private void criaGraficoBar(JPanel grafico) {
        //CategoryDataset cds = createDataset();
        String titulo = "Monitoring";
        String eixoy = "CPU";
        String txt_legenda = "";
        boolean legenda = true;
        boolean tooltips = true;
        boolean urls = false;
        //JFreeChart graf = ChartFactory.createBarChart3D(titulo, txt_legenda, eixoy, cds, PlotOrientation.VERTICAL, legenda, tooltips, urls);
        JFreeChart graf = ChartFactory.createBarChart(titulo, txt_legenda, eixoy, dataset, PlotOrientation.VERTICAL, legenda, tooltips, urls);
        myChartPanel = new ChartPanel(graf, true);
        myChartPanel.setSize(grafico.getWidth(), grafico.getHeight());
        myChartPanel.setVisible(true);
        grafico.removeAll();
        grafico.add(myChartPanel);
        grafico.revalidate();
        grafico.repaint();
    }
     
    private void criaGraficoThermometer(JPanel grafico) {

        valuedataset = new DefaultValueDataset(90.0);
        termometro = new ThermometerPlot(valuedataset);
        JFreeChart graf = new JFreeChart(termometro);
        termometro.setInsets(new RectangleInsets(1D, 1D, 1D, 1D));
        termometro.setPadding(new RectangleInsets(1D, 1D, 1D, 1));
        termometro.setUnits(1);
        termometro.setGap(3);
        termometro.setSubrange(ThermometerPlot.WARNING, 20.0, 80.0);
        termometro.setSubrangePaint(ThermometerPlot.WARNING, Color.red);
        termometro.setUnits(ThermometerPlot.UNITS_NONE);
        termometro.setThermometerStroke(new BasicStroke(2.0f));
        termometro.setThermometerPaint(Color.lightGray);
        termometro.setOutlineVisible(false);
        termometro.setBulbRadius(30);
        termometro.setColumnRadius(15);
        termometro.setUpperBound(100.0);
        
        myChartPanel = new ChartPanel(graf, true);
        myChartPanel.setSize(grafico.getWidth(), grafico.getHeight());
        myChartPanel.setVisible(true);
        grafico.removeAll();
        grafico.add(myChartPanel);
        grafico.revalidate();
        grafico.repaint();
    }
    
    public void update(int contador, int cpu_utilizada, int cpu_disponivel, float threshold_max, float threshold_min){
        dataset.addValue(cpu_utilizada, legendas[0], Integer.toString(contador));
        dataset.addValue(cpu_disponivel, legendas[1], Integer.toString(contador));
        dataset.addValue(threshold_max, legendas[2], Integer.toString(contador));
        dataset.addValue(threshold_min, legendas[3], Integer.toString(contador)); 
        //termometro.setUpperBound(cpu_disponivel);
        //valuedataset.setValue(cpu_utilizada);
        //termometro.setSubrange(ThermometerPlot.WARNING, threshold_min, threshold_max);
        grafico.repaint();
    }

    public void resize() {
        myChartPanel.setSize(grafico.getWidth(), grafico.getHeight());
    }
    
    private void popula_dataset(){
        dataset.addValue(50, legendas[0], Integer.toString(1));
        dataset.addValue(100, legendas[1], Integer.toString(1));
        dataset.addValue(80, legendas[2], Integer.toString(1));
        dataset.addValue(40, legendas[3], Integer.toString(1));        
        dataset.addValue(60, legendas[0], Integer.toString(2));
        dataset.addValue(100, legendas[1], Integer.toString(2));
        dataset.addValue(80, legendas[2], Integer.toString(2));
        dataset.addValue(40, legendas[3], Integer.toString(2));        
        dataset.addValue(55, legendas[0], Integer.toString(3));
        dataset.addValue(100, legendas[1], Integer.toString(3));
        dataset.addValue(80, legendas[2], Integer.toString(3));
        dataset.addValue(40, legendas[3], Integer.toString(3));        
        dataset.addValue(70, legendas[0], Integer.toString(4));
        dataset.addValue(100, legendas[1], Integer.toString(4));
        dataset.addValue(80, legendas[2], Integer.toString(4));
        dataset.addValue(40, legendas[3], Integer.toString(4));        
    }

    public void initialize() {
        dataset.clear();
        grafico.repaint();
    }
}

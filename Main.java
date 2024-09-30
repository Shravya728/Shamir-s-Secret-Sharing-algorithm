package src;

import org.apache.commons.cli.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.List;

public class Main {
    private static void setArguments(Options options) {
        options.addOption("S", "secret", true, "Provide the original secret to be shared.");
        options.addOption("s", "shares", true, "Provide the number of shares to generate for sharing the secret.");
        options.addOption("t", "threshold", true, "Provide the minimum number of shares required to reconstruct the secret.");
    }

    private static CommandLine getArguments(String[] args, Options options) {
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Error parsing command line arguments: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public static void main(String[] args) {
        Options options = new Options();
        setArguments(options);
        CommandLine cmd = getArguments(args, options);

        int secret = Integer.parseInt(cmd.getOptionValue("secret"));
        int shares = Integer.parseInt(cmd.getOptionValue("shares"));
        int threshold = Integer.parseInt(cmd.getOptionValue("threshold"));

        // Shamir Secret without Galois Field
        ShamirSecret shamirSecret = new ShamirSecret(secret, shares, threshold);
        shamirSecret.generateShares();
        List<int[]> sharesList = shamirSecret.getShares();
        System.out.println("Shares without Galois Field:");
        for (int[] share : sharesList) {
            System.out.println("x: " + share[0] + ", y: " + share[1]);
        }
        int reconstructedSecret = shamirSecret.reconstruct(sharesList);
        System.out.println("Reconstructed Secret without Galois Field: " + reconstructedSecret);

        // Plotting
        plotShares(sharesList, "Shares without Galois Field");

        // Shamir Secret with Galois Field
        ShamirSecretGalois shamirSecretGalois = new ShamirSecretGalois(secret, shares, threshold);
        shamirSecretGalois.generateShares();
        List<int[]> galoisSharesList = shamirSecretGalois.getShares();
        System.out.println("Shares with Galois Field:");
        for (int[] share : galoisSharesList) {
            System.out.println("x: " + share[0] + ", y: " + share[1]);
        }
        int reconstructedGaloisSecret = shamirSecretGalois.reconstruct(galoisSharesList);
        System.out.println("Reconstructed Secret with Galois Field: " + reconstructedGaloisSecret);

        // Plotting
        plotShares(galoisSharesList, "Shares with Galois Field");
    }

    private static void plotShares(List<int[]> shares, String title) {
        XYSeries series = new XYSeries("Shares");
        for (int[] share : shares) {
            series.add(share[0], share[1]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(title, "x-axis", "y-axis", dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}

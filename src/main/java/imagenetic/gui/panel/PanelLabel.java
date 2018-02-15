package imagenetic.gui.panel;

import imagenetic.common.Bridge;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelLabel extends JPanel {

    private JLabel lblGenerationCountValue;
    private JLabel lblAverageFitnessValue;
    private JLabel lblBestFitnessValue;

    public PanelLabel() {
        this.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        FlowLayout flowLayout = (FlowLayout) this.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);

        JLabel lblGenerationCount = new JLabel("Generációk száma:");
        this.add(lblGenerationCount);

        lblGenerationCountValue = new JLabel("0");
        this.add(lblGenerationCountValue);

        JLabel lblAverageFitness = new JLabel("Átlagos fitness:");
        this.add(lblAverageFitness);

        lblAverageFitnessValue = new JLabel("0");
        this.add(lblAverageFitnessValue);

        JLabel lblBestFitness = new JLabel("Legjobb fitness:");
        this.add(lblBestFitness);

        lblBestFitnessValue = new JLabel("0");
        this.add(lblBestFitnessValue);
    }

    public void updateLabels() {
        lblGenerationCountValue.setText(String.format("%s", Bridge.sceneSide.getNumberOfGenerations()));
        lblAverageFitnessValue.setText(String.format("%1.3f", Bridge.sceneSide.getAverageFitness()));
        lblBestFitnessValue.setText(String.format("%1.3f", Bridge.sceneSide.getBestFitness()));
    }
}

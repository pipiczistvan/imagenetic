package imagenetic.gui.frame.main.panel.control.panel.settings;

import net.miginfocom.swing.MigLayout;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

@Component
public class PanelSettings extends JPanel {

    @Wire
    public PanelSettings(final SpinnerPopulationCount spinnerPopulationCount, final SpinnerMutationRate spinnerMutationRate,
                         final SpinnerElitismRate spinnerElitismRate, final SpinnerCriteriaRate spinnerCriteriaRate,
                         final ChoiceSelectionOperator choiceSelectionOperator, final ChoiceMutationOperator choiceMutationOperator,
                         final ChoiceCrossoverOperator choiceCrossoverOperator, final CheckBoxMultiCheck checkBoxMultiCheck) {
        this.setAlignmentY(TOP_ALIGNMENT);
        this.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Beállítások", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        this.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][]"));

        // POPULATION COUNT
        JLabel lblPopulationCount = new JLabel("Populáció számossága:");
        this.add(lblPopulationCount, "cell 0 0");
        lblPopulationCount.setHorizontalAlignment(SwingConstants.LEFT);

        this.add(spinnerPopulationCount, "cell 2 0,growx");

        // MUTATION RATE
        JLabel lblMutationRate = new JLabel("Mutációs ráta:");
        this.add(lblMutationRate, "cell 0 1");
        this.add(spinnerMutationRate, "cell 2 1,growx");

        // ELITISM
        JLabel lblElitism = new JLabel("Elitizmus:");
        this.add(lblElitism, "cell 0 2");

        this.add(spinnerElitismRate, "cell 2 2,growx");

        // CRITERIA
        JLabel lblCriteria = new JLabel("Kritérium:");
        this.add(lblCriteria, "cell 0 3");

        this.add(spinnerCriteriaRate, "cell 2 3,growx");

        // SELECTION OPERATOR
        JLabel lblSelectionOperator = new JLabel("Szelekciós operátor:");
        this.add(lblSelectionOperator, "cell 0 4");

        JLabel lblSelectionOperatorHelp = new JLabel(" ? ");
        lblSelectionOperatorHelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSelectionOperatorHelp.setFont(new Font("Tahoma", Font.BOLD, 11));
        this.add(lblSelectionOperatorHelp, "cell 1 4");

        this.add(choiceSelectionOperator, "cell 2 4,growx");

        // CROSSOVER OPERATOR
        JLabel lblCrossoverOperator = new JLabel("Keresztezési operátor:");
        this.add(lblCrossoverOperator, "cell 0 5");

        JLabel lblCrossoverOperatorHelp = new JLabel(" ? ");
        lblCrossoverOperatorHelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblCrossoverOperatorHelp.setFont(new Font("Tahoma", Font.BOLD, 11));
        this.add(lblCrossoverOperatorHelp, "cell 1 5");

        this.add(choiceCrossoverOperator, "cell 2 5,growx");

        // MUTATION OPERATOR
        JLabel lblMutationOperator = new JLabel("Mutációs operátor:");
        this.add(lblMutationOperator, "cell 0 6");

        JLabel lblMutationOperatorHelp = new JLabel(" ? ");
        lblMutationOperatorHelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblMutationOperatorHelp.setFont(new Font("Tahoma", Font.BOLD, 11));
        this.add(lblMutationOperatorHelp, "cell 1 6");

        this.add(choiceMutationOperator, "cell 2 6,growx");

        // MULTI CHECK
        JLabel lblMultiCheck = new JLabel("Kétoldali egyezés:");
        this.add(lblMultiCheck, "cell 0 7");

        this.add(checkBoxMultiCheck, "cell 2 7,alignx right");
    }
}

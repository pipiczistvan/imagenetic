package imagenetic.gui.frame.dialog;

import net.miginfocom.swing.MigLayout;
import piengine.core.base.resource.ResourceLoader;

import javax.swing.*;
import java.awt.*;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;

public class DialogCrossoverOperator extends JDialog {

    private final ResourceLoader imageLoader = new ResourceLoader(get(IMAGES_LOCATION), "png");

    public DialogCrossoverOperator() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setTitle("Keresztezési operátorok");
        setModalityType(ModalityType.APPLICATION_MODAL);
        setBounds(0, 0, 510, 380);
        setIconImage(new ImageIcon(imageLoader.getUrl("icon")).getImage());
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        {
            JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            getContentPane().add(tabbedPane, BorderLayout.CENTER);
            {
                JPanel panelOnePoint = new JPanel();
                tabbedPane.addTab("Egypontos", null, panelOnePoint, null);
                panelOnePoint.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaOnePoint = new JTextArea();
                    textareaOnePoint.setBackground(SystemColor.menu);
                    textareaOnePoint.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaOnePoint.setEditable(false);
                    textareaOnePoint.setLineWrap(true);
                    textareaOnePoint.setWrapStyleWord(true);
                    textareaOnePoint.setText("Egy véletlenszerűen választott pontig az első szülő tulajdonságai öröklődnek, azt követően pedig a másik szülőé.");
                    panelOnePoint.add(textareaOnePoint, "cell 0 0,grow");
                }
                {
                    JLabel lblOnePoint = new JLabel("");
                    lblOnePoint.setHorizontalAlignment(SwingConstants.CENTER);
                    lblOnePoint.setIcon(new ImageIcon(imageLoader.getUrl("desc/onePoint")));
                    panelOnePoint.add(lblOnePoint, "cell 0 2,alignx center");
                }
            }
            {
                JPanel panelMultiPoint = new JPanel();
                tabbedPane.addTab("Többpontos", null, panelMultiPoint, null);
                panelMultiPoint.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaMultiPoint = new JTextArea();
                    textareaMultiPoint.setWrapStyleWord(true);
                    textareaMultiPoint.setText("A szülűegyedek tulajdonságai több, véletlenszerűen választott pont mentén váltakozva öröklődnek.");
                    textareaMultiPoint.setLineWrap(true);
                    textareaMultiPoint.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaMultiPoint.setEditable(false);
                    textareaMultiPoint.setBackground(SystemColor.menu);
                    panelMultiPoint.add(textareaMultiPoint, "cell 0 0,grow");
                }
                {
                    JLabel lblMultiPoint = new JLabel("");
                    lblMultiPoint.setIcon(new ImageIcon(imageLoader.getUrl("desc/multiPoint")));
                    panelMultiPoint.add(lblMultiPoint, "cell 0 2,alignx center");
                }
            }
            {
                JPanel panelUniform = new JPanel();
                tabbedPane.addTab("Egyenletes", null, panelUniform, null);
                panelUniform.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaUniform = new JTextArea();
                    textareaUniform.setEditable(false);
                    textareaUniform.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaUniform.setBackground(SystemColor.menu);
                    textareaUniform.setWrapStyleWord(true);
                    textareaUniform.setLineWrap(true);
                    textareaUniform.setRows(1);
                    textareaUniform.setText("A két szülőegyedtől a gének egyforma eséllyel öröklődnek.");
                    panelUniform.add(textareaUniform, "cell 0 0,grow");
                }
                {
                    JLabel lblUniform = new JLabel("");
                    lblUniform.setHorizontalAlignment(SwingConstants.CENTER);
                    lblUniform.setIcon(new ImageIcon(imageLoader.getUrl("desc/uniform")));
                    panelUniform.add(lblUniform, "cell 0 2,growx,aligny top");
                }
            }
            {
                JPanel panelAverage = new JPanel();
                tabbedPane.addTab("\u00C1tlag alapú", null, panelAverage, null);
                panelAverage.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaAverage = new JTextArea();
                    textareaAverage.setWrapStyleWord(true);
                    textareaAverage.setText("A szülőegyedektől örökölt génekből, a szülők fitneszértékével súlyozott átlagával új gének keletkeznek.");
                    textareaAverage.setRows(1);
                    textareaAverage.setLineWrap(true);
                    textareaAverage.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaAverage.setEditable(false);
                    textareaAverage.setBackground(SystemColor.menu);
                    panelAverage.add(textareaAverage, "cell 0 0,grow");
                }
                {
                    JLabel lblAverage = new JLabel("");
                    lblAverage.setIcon(new ImageIcon(imageLoader.getUrl("desc/average")));
                    lblAverage.setHorizontalAlignment(SwingConstants.CENTER);
                    panelAverage.add(lblAverage, "cell 0 2,alignx center");
                }
            }
        }
    }
}

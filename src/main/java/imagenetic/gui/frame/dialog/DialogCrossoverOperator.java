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
                    textareaOnePoint.setText("Egy v\u00E9letlenszer\u0171en v\u00E1lasztott pontig az els\u0151 sz\u00FCl\u0151 tulajdons\u00E1gai \u00F6r\u00F6kl\u0151dnek, azt k\u00F6vet\u0151en pedig a m\u00E1sik sz\u00FCl\u0151\u00E9.");
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
                tabbedPane.addTab("T\u00F6bbpontos", null, panelMultiPoint, null);
                panelMultiPoint.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaMultiPoint = new JTextArea();
                    textareaMultiPoint.setWrapStyleWord(true);
                    textareaMultiPoint.setText("A sz\u00FCl\u0171egyedek tulajdons\u00E1gai t\u00F6bb, v\u00E9letlenszer\u0171en v\u00E1lasztott pont ment\u00E9n v\u00E1ltakozva \u00F6r\u00F6kl\u0151dnek.");
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
                    textareaUniform.setText("A k\u00E9t sz\u00FCl\u0151egyedt\u0151l a g\u00E9nek egyforma es\u00E9llyel \u00F6r\u00F6kl\u0151dnek.");
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
                tabbedPane.addTab("\u00C1tlag alap\u00FA", null, panelAverage, null);
                panelAverage.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaAverage = new JTextArea();
                    textareaAverage.setWrapStyleWord(true);
                    textareaAverage.setText("A sz\u00FCl\u0151egyedekt\u0151l \u00F6r\u00F6k\u00F6lt g\u00E9nekb\u0151l, a sz\u00FCl\u0151k fitnesz\u00E9rt\u00E9k\u00E9vel s\u00FAlyozott \u00E1tlag\u00E1val \u00FAj g\u00E9nek keletkeznek.");
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

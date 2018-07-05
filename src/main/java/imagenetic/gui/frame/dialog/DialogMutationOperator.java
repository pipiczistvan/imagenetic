package imagenetic.gui.frame.dialog;

import net.miginfocom.swing.MigLayout;
import piengine.core.base.resource.ResourceLoader;

import javax.swing.*;
import java.awt.*;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;

public class DialogMutationOperator extends JDialog {

    private final ResourceLoader imageLoader = new ResourceLoader(get(IMAGES_LOCATION), "png");

    public DialogMutationOperator() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setTitle("Mutációs operátorok");
        setModalityType(ModalityType.APPLICATION_MODAL);
        setBounds(0, 0, 510, 380);
        setIconImage(new ImageIcon(imageLoader.getUrl("icon")).getImage());
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        {
            JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            getContentPane().add(tabbedPane, BorderLayout.CENTER);
            {
                JPanel panelRandom = new JPanel();
                tabbedPane.addTab("Véletlenszerű", null, panelRandom, null);
                panelRandom.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaRandom = new JTextArea();
                    textareaRandom.setBackground(SystemColor.menu);
                    textareaRandom.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaRandom.setEditable(false);
                    textareaRandom.setLineWrap(true);
                    textareaRandom.setWrapStyleWord(true);
                    textareaRandom.setText("Az egyed génjei véletlenszerűen megváltoznak.");
                    panelRandom.add(textareaRandom, "cell 0 0,grow");
                }
                {
                    JLabel lblRandom = new JLabel("");
                    lblRandom.setHorizontalAlignment(SwingConstants.CENTER);
                    lblRandom.setIcon(new ImageIcon(imageLoader.getUrl("desc/random_mut")));
                    panelRandom.add(lblRandom, "cell 0 2,alignx center");
                }
            }
            {
                JPanel panelSwap = new JPanel();
                tabbedPane.addTab("Géncsere", null, panelSwap, null);
                panelSwap.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaSwap = new JTextArea();
                    textareaSwap.setWrapStyleWord(true);
                    textareaSwap.setText("Két véletlenszerűen kiválasztott gén helyet cserél egymással.");
                    textareaSwap.setLineWrap(true);
                    textareaSwap.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaSwap.setEditable(false);
                    textareaSwap.setBackground(SystemColor.menu);
                    panelSwap.add(textareaSwap, "cell 0 0,grow");
                }
                {
                    JLabel lblSwap = new JLabel("");
                    lblSwap.setIcon(new ImageIcon(imageLoader.getUrl("desc/swap")));
                    panelSwap.add(lblSwap, "cell 0 2,alignx center");
                }
            }
            {
                JPanel panelScramble = new JPanel();
                tabbedPane.addTab("Összekeverés", null, panelScramble, null);
                panelScramble.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaScramble = new JTextArea();
                    textareaScramble.setEditable(false);
                    textareaScramble.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaScramble.setBackground(SystemColor.menu);
                    textareaScramble.setWrapStyleWord(true);
                    textareaScramble.setLineWrap(true);
                    textareaScramble.setRows(1);
                    textareaScramble.setText("A gének sorrendje véletlenszerűen megváltozik.");
                    panelScramble.add(textareaScramble, "cell 0 0,grow");
                }
                {
                    JLabel lblScramble = new JLabel("");
                    lblScramble.setHorizontalAlignment(SwingConstants.CENTER);
                    lblScramble.setIcon(new ImageIcon(imageLoader.getUrl("desc/scramble")));
                    panelScramble.add(lblScramble, "cell 0 2,growx,aligny top");
                }
            }
            {
                JPanel panelInversion = new JPanel();
                tabbedPane.addTab("Inverzió", null, panelInversion, null);
                panelInversion.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaInversion = new JTextArea();
                    textareaInversion.setWrapStyleWord(true);
                    textareaInversion.setText("A gének az inverz értéküket veszik fel.");
                    textareaInversion.setRows(1);
                    textareaInversion.setLineWrap(true);
                    textareaInversion.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaInversion.setEditable(false);
                    textareaInversion.setBackground(SystemColor.menu);
                    panelInversion.add(textareaInversion, "cell 0 0,grow");
                }
                {
                    JLabel lblInversion = new JLabel("");
                    lblInversion.setIcon(new ImageIcon(imageLoader.getUrl("desc/inversion")));
                    lblInversion.setHorizontalAlignment(SwingConstants.CENTER);
                    panelInversion.add(lblInversion, "cell 0 2,alignx center");
                }
            }
        }
    }
}

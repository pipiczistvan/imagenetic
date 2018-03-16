package imagenetic.gui.frame.dialog;

import net.miginfocom.swing.MigLayout;
import piengine.core.base.resource.ResourceLoader;

import javax.swing.*;
import java.awt.*;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;

public class DialogSelectionOperator extends JDialog {

    private final ResourceLoader imageLoader = new ResourceLoader(get(IMAGES_LOCATION), "png");

    public DialogSelectionOperator() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setTitle("Szelekciós operátorok");
        setModalityType(ModalityType.APPLICATION_MODAL);
        setBounds(0, 0, 510, 380);
        setIconImage(new ImageIcon(imageLoader.getUrl("icon")).getImage());
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        {
            JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            getContentPane().add(tabbedPane, BorderLayout.CENTER);
            {
                JPanel panelTournament = new JPanel();
                tabbedPane.addTab("Párok versenyeztetése", null, panelTournament, null);
                panelTournament.setLayout(new MigLayout("", "[grow]", "[][][]"));
                {
                    JTextArea textareaTournament = new JTextArea();
                    textareaTournament.setBackground(SystemColor.menu);
                    textareaTournament.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaTournament.setEditable(false);
                    textareaTournament.setLineWrap(true);
                    textareaTournament.setWrapStyleWord(true);
                    textareaTournament.setText("K\u00E9t egyed v\u00E9letlenszer\u0171 kiv\u00E1laszt\u00E1sa ut\u00E1n abb\u00F3l lesz sz\u00FCl\u0151egyed, amelyiknek nagyon a fitnesz \u00E9rt\u00E9ke. A m\u00E1sik sz\u00FCl\u0151 kiv\u00E1laszt\u00E1sa ennek a m\u00F3dszernek a megism\u00E9tl\u00E9s\u00E9vel t\u00F6rt\u00E9nik.");
                    panelTournament.add(textareaTournament, "cell 0 0,grow");
                }
                {
                    JLabel lblTournament = new JLabel("");
                    lblTournament.setHorizontalAlignment(SwingConstants.CENTER);
                    lblTournament.setIcon(new ImageIcon(imageLoader.getUrl("desc/tournament")));
                    panelTournament.add(lblTournament, "cell 0 2,alignx center");
                }
            }
            {
                JPanel panelAptitude = new JPanel();
                tabbedPane.addTab("Rangsor", null, panelAptitude, null);
                panelAptitude.setLayout(new MigLayout("", "[grow]", "[grow][]"));
                {
                    JTextArea textAreaAptitude = new JTextArea();
                    textAreaAptitude.setWrapStyleWord(true);
                    textAreaAptitude.setText("A sz\u00FCl\u0151egyedek a fitnesz\u00E9rt\u00E9k\u00FCk ar\u00E1ny\u00E1ban ker\u00FClnek kiv\u00E1laszt\u00E1sra.");
                    textAreaAptitude.setLineWrap(true);
                    textAreaAptitude.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textAreaAptitude.setEditable(false);
                    textAreaAptitude.setBackground(SystemColor.menu);
                    panelAptitude.add(textAreaAptitude, "cell 0 0,grow");
                }
                {
                    JLabel lblAptitude = new JLabel("");
                    lblAptitude.setIcon(new ImageIcon(imageLoader.getUrl("desc/aptitude")));
                    panelAptitude.add(lblAptitude, "cell 0 1,alignx center");
                }
            }
            {
                JPanel panelRanking = new JPanel();
                tabbedPane.addTab("Rangsor alap\u00FA", null, panelRanking, null);
                panelRanking.setLayout(new MigLayout("", "[grow]", "[][]"));
                {
                    JTextArea textareaRank = new JTextArea();
                    textareaRank.setEditable(false);
                    textareaRank.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaRank.setBackground(SystemColor.menu);
                    textareaRank.setWrapStyleWord(true);
                    textareaRank.setLineWrap(true);
                    textareaRank.setRows(1);
                    textareaRank.setText("Enn\u00E9l a szelekci\u00F3s m\u00F3dszern\u00E9l az egyedek k\u00F6z\u00F6tt fitneszelt\u00E9r\u00E9s nagys\u00E1ga nem j\u00E1tszik szerepet, hanem a kiv\u00E1laszt\u00E1s a fitnesz alapj\u00E1n bet\u00F6lt\u00F6tt hely szerint t\u00F6rt\u00E9nik. \u00CDgy a nagyobb fitnesz \u00E9rt\u00E9k\u0171 egyed nagyobb es\u00E9llyel versenyez, viszont a kisebbek is kapnak es\u00E9lyt.");
                    panelRanking.add(textareaRank, "cell 0 0,grow");
                }
                {
                    JLabel lblRank = new JLabel("");
                    lblRank.setHorizontalAlignment(SwingConstants.CENTER);
                    lblRank.setIcon(new ImageIcon(imageLoader.getUrl("desc/rank")));
                    panelRanking.add(lblRank, "cell 0 1,growx,aligny top");
                }
            }
            {
                JPanel panelRandom = new JPanel();
                tabbedPane.addTab("V\u00E9letlenszer\u0171", null, panelRandom, null);
                panelRandom.setLayout(new MigLayout("", "[grow]", "[grow][]"));
                {
                    JTextArea textAreaRandom = new JTextArea();
                    textAreaRandom.setWrapStyleWord(true);
                    textAreaRandom.setText("Az \u00F6sszes egyed azonos es\u00E9llyel ker\u00FCl kiv\u00E1laszt\u00E1sra.");
                    textAreaRandom.setRows(1);
                    textAreaRandom.setLineWrap(true);
                    textAreaRandom.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textAreaRandom.setEditable(false);
                    textAreaRandom.setBackground(SystemColor.menu);
                    panelRandom.add(textAreaRandom, "cell 0 0,grow");
                }
                {
                    JLabel lblRandom = new JLabel("");
                    lblRandom.setIcon(new ImageIcon(imageLoader.getUrl("desc/random")));
                    lblRandom.setHorizontalAlignment(SwingConstants.CENTER);
                    panelRandom.add(lblRandom, "cell 0 1,alignx center");
                }
            }
        }
    }
}

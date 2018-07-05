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
                    textareaTournament.setText("Két egyed véletlenszerű kiválasztása után abból lesz szülőegyed, amelyiknek nagyon a fitnesz értéke. A másik szülő kiválasztása ennek a módszernek a megismétlésével történik.");
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
                    textAreaAptitude.setText("A szülőegyedek a fitneszértékük arányában kerülnek kiválasztásra.");
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
                tabbedPane.addTab("Rangsor alapú", null, panelRanking, null);
                panelRanking.setLayout(new MigLayout("", "[grow]", "[][]"));
                {
                    JTextArea textareaRank = new JTextArea();
                    textareaRank.setEditable(false);
                    textareaRank.setFont(new Font("Tahoma", Font.PLAIN, 11));
                    textareaRank.setBackground(SystemColor.menu);
                    textareaRank.setWrapStyleWord(true);
                    textareaRank.setLineWrap(true);
                    textareaRank.setRows(1);
                    textareaRank.setText("Ennél a szelekciós módszernél az egyedek között fitneszeltérés nagysága nem játszik szerepet, hanem a kiválasztás a fitnesz alapján betöltött hely szerint történik. Úgy a nagyobb fitnesz értékű egyed nagyobb eséllyel versenyez, viszont a kisebbek is kapnak esélyt.");
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
                tabbedPane.addTab("Véletlenszerű", null, panelRandom, null);
                panelRandom.setLayout(new MigLayout("", "[grow]", "[grow][]"));
                {
                    JTextArea textAreaRandom = new JTextArea();
                    textAreaRandom.setWrapStyleWord(true);
                    textAreaRandom.setText("Az összes egyed azonos eséllyel kerül kiválasztásra.");
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

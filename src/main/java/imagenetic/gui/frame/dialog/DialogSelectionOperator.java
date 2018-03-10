package imagenetic.gui.frame.dialog;

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
        setBounds(0, 0, 450, 300);
        setIconImage(new ImageIcon(imageLoader.getUrl("icon")).getImage());
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        {
            JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            getContentPane().add(tabbedPane, BorderLayout.CENTER);
            {
                JPanel panelRanking = new JPanel();
                tabbedPane.addTab("Rangsor", null, panelRanking, null);
                panelRanking.setLayout(new BorderLayout(0, 0));
                {
                    JTextArea textareaRank = new JTextArea();
                    textareaRank.setBackground(Color.WHITE);
                    textareaRank.setWrapStyleWord(true);
                    textareaRank.setLineWrap(true);
                    textareaRank.setEditable(false);
                    textareaRank.setText("Ennél a szelekciós módszernél az egyedek között fitneszeltérés nagysága nem játszik szerepet, hanem a kiválasztás a fitnesz alapján betöltött hely szerint történik. Így a nagyobb fitnesz értékű egyed nagyobb eséllyel versenyez, viszont a kisebbek is kapnak esélyt.");
                    panelRanking.add(textareaRank);
                }
            }
            {
                JPanel panelTournament = new JPanel();
                tabbedPane.addTab("Versenyeztetés", null, panelTournament, null);
                panelTournament.setLayout(new BorderLayout(0, 0));
                {
                    JTextArea textareaTournament = new JTextArea();
                    textareaTournament.setLineWrap(true);
                    textareaTournament.setWrapStyleWord(true);
                    textareaTournament.setEditable(false);
                    textareaTournament.setText("A módszer két egyed véletlesznerű kiválasztásával kezdődik, amelyek közül az kerül ki győztesen, amelyiknek nagyobb a fitnesze.");
                    panelTournament.add(textareaTournament, BorderLayout.CENTER);
                }
            }
        }
    }
}

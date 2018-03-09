package imagenetic.gui.frame.dialog;

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
        setBounds(0, 0, 450, 300);
        setIconImage(new ImageIcon(imageLoader.getUrl("gene")).getImage());
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        {
            JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            getContentPane().add(tabbedPane, BorderLayout.CENTER);
            {
                JPanel panelRanking = new JPanel();
                tabbedPane.addTab("Egyenletes", null, panelRanking, null);
                panelRanking.setLayout(new BorderLayout(0, 0));
            }
        }
    }
}

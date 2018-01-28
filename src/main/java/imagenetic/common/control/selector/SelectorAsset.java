package imagenetic.common.control.selector;

import imagenetic.common.control.button.ButtonAsset;
import imagenetic.common.control.button.ButtonAssetArgument;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import puppeteer.annotation.premade.Wire;

import java.awt.*;

public class SelectorAsset extends GuiAsset<SelectorAssetArgument> {

    private ButtonAsset buttonAsset;

    @Wire
    public SelectorAsset(final AssetManager assetManager) {
        super(assetManager);
    }

    @Override
    public void initialize() {
        buttonAsset = createAsset(ButtonAsset.class, new ButtonAssetArgument(
                arguments.defaultImageName,
                arguments.hoverImageName,
                arguments.pressImageName,
                arguments.viewport,
                arguments.text,
                this::onClick
        ));
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadAssets(buttonAsset)
                .build();
    }

    private void onClick() {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        arguments.onSelected.invoke(dialog.getFiles());
    }
}

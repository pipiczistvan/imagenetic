package imagenetic.scene.asset.ui;

import imagenetic.common.control.button.ButtonAsset;
import imagenetic.common.control.button.ButtonAssetArgument;
import imagenetic.common.control.label.LabelAsset;
import imagenetic.common.control.label.LabelAssetArgument;
import imagenetic.common.control.selector.SelectorAsset;
import imagenetic.common.control.selector.SelectorAssetArgument;
import piengine.core.base.api.Resizable;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import puppeteer.annotation.premade.Wire;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UiAsset extends GuiAsset<UiAssetArgument> implements Resizable {

    private static final float MARGIN = 0.05f;

    private ButtonAsset buttonAsset;
    private SelectorAsset selectorAsset;
    private LabelAsset label1, label2;

    @Wire
    public UiAsset(final AssetManager assetManager) {
        super(assetManager);
    }

    @Override
    public void initialize() {
        buttonAsset = createAsset(ButtonAsset.class, new ButtonAssetArgument(
                "buttonDefault",
                "buttonHover",
                "buttonPress",
                arguments.viewport,
                "Please press me!",
                this::onClick));

        selectorAsset = createAsset(SelectorAsset.class, new SelectorAssetArgument(
                this,
                "buttonDefault",
                "buttonHover",
                "buttonPress",
                arguments.viewport,
                "Select file!",
                this::onSelected
        ));

        label1 = createAsset(LabelAsset.class, new LabelAssetArgument("Test text1", arguments.viewport, 0.4f));
        label2 = createAsset(LabelAsset.class, new LabelAssetArgument("Test text2", arguments.viewport, 0.2f));

        setTransformations(arguments.viewport.x, arguments.viewport.y, arguments.viewport.x, arguments.viewport.y);
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadAssets(buttonAsset)
                .loadAssets(selectorAsset)
                .loadAssets(label1)
                .loadAssets(label2)
                .build();
    }

    @Override
    public void resize(final int oldWidth, final int oldHeight, final int width, final int height) {
        setTransformations(oldWidth, oldHeight, width, height);
    }

    public void updateLabels() {
        label1.setText("Generaciok szama: " + arguments.geneticAlgorithm.getNumberOfGenerations());
    }

    private void setTransformations(final int oldWidth, final int oldHeight, final int width, final int height) {
        float scaleX = (float) oldWidth / (float) width;
        float scaleY = (float) oldHeight / (float) height;

        transformLabels(scaleX, scaleY);
        transformButtons(scaleX, scaleY);
    }

    private void transformLabels(float scaleX, float scaleY) {
        label1.scale(scaleX, scaleY, 1);
        label2.scale(scaleX, scaleY, 1);

        float currentPosX = label1.getScale().x - 1 + label1.getScale().x * MARGIN;
        float currentPosY = 1 - label1.getScale().y - label1.getScale().y * MARGIN;

        label1.setPosition(currentPosX, currentPosY, 0);
        currentPosX += label1.getScale().x * label1.getMaxLength() * LabelAsset.FONT_SIZE + label1.getScale().x * MARGIN;

        label2.setPosition(currentPosX, currentPosY, 0);
    }

    private void transformButtons(float scaleX, float scaleY) {
        buttonAsset.scale(scaleX, scaleY, 1);
        selectorAsset.scale(scaleX, scaleY, 1);

        float currentPosX = buttonAsset.getScale().x * ButtonAsset.SCALE_X + buttonAsset.getScale().x * MARGIN;
        float currentPosY = buttonAsset.getScale().y * ButtonAsset.SCALE_Y + buttonAsset.getScale().y * MARGIN;

        buttonAsset.setPosition(1f - currentPosX, 1f - currentPosY, 0);
        currentPosY += (buttonAsset.getScale().y * ButtonAsset.SCALE_Y * 2) + buttonAsset.getScale().y * MARGIN;

        selectorAsset.setPosition(1f - currentPosX, 1f - currentPosY, 0);
    }

    private void onClick() {
        System.out.println("Button clicked");
    }

    private void onSelected(final File[] files) {
        if (files.length > 0) {
            try {
                BufferedImage image = ImageIO.read(files[0]);
                arguments.geneticAlgorithm.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

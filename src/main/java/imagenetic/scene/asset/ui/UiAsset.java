package imagenetic.scene.asset.ui;

import imagenetic.common.control.button.ButtonAsset;
import imagenetic.common.control.button.ButtonAssetArgument;
import imagenetic.common.control.selector.SelectorAsset;
import imagenetic.common.control.selector.SelectorAssetArgument;
import imagenetic.scene.asset.fps.FpsAsset;
import imagenetic.scene.asset.fps.FpsAssetArgument;
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

    private static final float MARGIN = 0.1f;

    private ButtonAsset buttonAsset;
    private SelectorAsset selectorAsset;
    private FpsAsset fpsAsset;

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

        fpsAsset = createAsset(FpsAsset.class, new FpsAssetArgument(arguments.viewport));

        setTransformations(arguments.viewport.x, arguments.viewport.y, arguments.viewport.x, arguments.viewport.y);
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadAssets(buttonAsset)
                .loadAssets(selectorAsset)
                .loadAssets(fpsAsset)
                .build();
    }

    @Override
    public void resize(final int oldWidth, final int oldHeight, final int width, final int height) {
        setTransformations(oldWidth, oldHeight, width, height);
    }

    private void setTransformations(final int oldWidth, final int oldHeight, final int width, final int height) {
        float scaleX = (float) oldWidth / (float) width;
        float scaleY = (float) oldHeight / (float) height;

        float currentPosX = 0;
        float currentPosY = 0.2f;
        buttonAsset.scale(scaleX, scaleY, 1);
        currentPosX += (buttonAsset.getScale().x * ButtonAsset.SCALE_X) + ButtonAsset.SCALE_X * MARGIN;
        currentPosY += (buttonAsset.getScale().y * ButtonAsset.SCALE_Y) + ButtonAsset.SCALE_X * MARGIN;
        buttonAsset.setPosition(
                1f - currentPosX,
                1f - currentPosY,
                0);
//        currentPosX += (buttonAsset.getScale().x * ButtonAsset.SCALE_X * 2) + ButtonAsset.SCALE_X * MARGIN;
        currentPosY += (buttonAsset.getScale().y * ButtonAsset.SCALE_Y * 2) + ButtonAsset.SCALE_Y * MARGIN;

        selectorAsset.scale(scaleX, scaleY, 1);
        selectorAsset.setPosition(
                1f - currentPosX,
                1f - currentPosY,
                0);
//        currentPosX += (selectorAsset.getScale().x * ButtonAsset.SCALE_X * 2) + ButtonAsset.SCALE_X * MARGIN;
        currentPosY += (selectorAsset.getScale().y * ButtonAsset.SCALE_Y * 2) + ButtonAsset.SCALE_Y * MARGIN;
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

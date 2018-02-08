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
    private LabelAsset lblNumberOfGenerations;
    private LabelAsset lblNumberOfPopulations;
    private LabelAsset lblBestFitness;
    private LabelAsset lblAverageFitness;

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

        lblNumberOfGenerations = createAsset(LabelAsset.class, new LabelAssetArgument("Generaciok szama: ", arguments.viewport, 0.35f));
        lblNumberOfPopulations = createAsset(LabelAsset.class, new LabelAssetArgument("Populaciok szama: ", arguments.viewport, 0.3f));
        lblAverageFitness = createAsset(LabelAsset.class, new LabelAssetArgument("Atlag fitness: ", arguments.viewport, 0.3f));
        lblBestFitness = createAsset(LabelAsset.class, new LabelAssetArgument("Legjobb fitness: ", arguments.viewport, 0.3f));

        setTransformations(arguments.viewport.x, arguments.viewport.y, arguments.viewport.x, arguments.viewport.y);
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadAssets(buttonAsset)
                .loadAssets(selectorAsset)
                .loadAssets(lblNumberOfGenerations)
                .loadAssets(lblNumberOfPopulations)
                .loadAssets(lblAverageFitness)
                .loadAssets(lblBestFitness)
                .build();
    }

    @Override
    public void resize(final int oldWidth, final int oldHeight, final int width, final int height) {
        setTransformations(oldWidth, oldHeight, width, height);
    }

    public void updateLabels() {
        lblNumberOfGenerations.setText(String.format("Generaciok szama: %s", arguments.geneticAlgorithm.getNumberOfGenerations()));
        lblNumberOfPopulations.setText(String.format("Populaciok szama: %s", arguments.geneticAlgorithm.getNumberOfPopulations()));
        lblAverageFitness.setText(String.format("Atlag fitness: %1.3f", arguments.geneticAlgorithm.getAverageFitness()));
        lblBestFitness.setText(String.format("Legjobb fitness: %1.3f", arguments.geneticAlgorithm.getBestFitness()));
    }

    private void setTransformations(final int oldWidth, final int oldHeight, final int width, final int height) {
        float scaleX = (float) oldWidth / (float) width;
        float scaleY = (float) oldHeight / (float) height;

        transformLabels(scaleX, scaleY);
        transformButtons(scaleX, scaleY);
    }

    private void transformLabels(float scaleX, float scaleY) {
        lblNumberOfGenerations.scale(scaleX, scaleY, 1);
        lblNumberOfPopulations.scale(scaleX, scaleY, 1);
        lblAverageFitness.scale(scaleX, scaleY, 1);
        lblBestFitness.scale(scaleX, scaleY, 1);

        float currentPosX = lblNumberOfGenerations.getScale().x * LabelAsset.FONT_SIZE - 1 + lblNumberOfGenerations.getScale().x * MARGIN;
        float currentPosY = -lblNumberOfGenerations.getScale().y * LabelAsset.FONT_SIZE - 1 + lblNumberOfGenerations.getScale().y * MARGIN * 2;

        lblNumberOfGenerations.setPosition(currentPosX, currentPosY, 0);
        currentPosX += lblNumberOfGenerations.getScale().x * lblNumberOfGenerations.getMaxLength() * LabelAsset.FONT_SIZE + lblNumberOfGenerations.getScale().x * MARGIN;

        lblNumberOfPopulations.setPosition(currentPosX, currentPosY, 0);
        currentPosX += lblNumberOfPopulations.getScale().x * lblNumberOfPopulations.getMaxLength() * LabelAsset.FONT_SIZE + lblNumberOfPopulations.getScale().x * MARGIN;

        lblAverageFitness.setPosition(currentPosX, currentPosY, 0);
        currentPosX += lblAverageFitness.getScale().x * lblAverageFitness.getMaxLength() * LabelAsset.FONT_SIZE + lblAverageFitness.getScale().x * MARGIN;

        lblBestFitness.setPosition(currentPosX, currentPosY, 0);
        currentPosX += lblBestFitness.getScale().x * lblBestFitness.getMaxLength() * LabelAsset.FONT_SIZE + lblBestFitness.getScale().x * MARGIN;
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

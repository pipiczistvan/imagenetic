package imagenetic.common.control.label;

import org.joml.Vector2f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import piengine.visual.writing.font.domain.Font;
import piengine.visual.writing.font.manager.FontManager;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.domain.TextConfiguration;
import piengine.visual.writing.text.manager.TextManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.writing.text.domain.TextConfiguration.textConfig;

public class LabelAsset extends GuiAsset<LabelAssetArgument> {

    public static final float FONT_SIZE = 1.4f;

    private final FontManager fontManager;
    private final TextManager textManager;

    private Font font;
    private Text label;
    private TextConfiguration config;

    @Wire
    public LabelAsset(final AssetManager assetManager, final FontManager fontManager, final TextManager textManager) {
        super(assetManager);
        this.fontManager = fontManager;
        this.textManager = textManager;
    }

    @Override
    public void initialize() {
        font = fontManager.supply("candara", arguments.viewport);
        config = textConfig()
                .withFont(font)
                .withColor(ColorUtils.BLACK)
                .withOutlineColor(new Color(0.7f))
                .withOffset(new Vector2f(-0.004f))
                .withCentered(false)
                .withFontSize(FONT_SIZE)
                .withMaxLineLength(arguments.maxLength)
                .withText(arguments.text);
        label = textManager.supply(config, this);
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadTexts(label)
                .build();
    }

    public float getMaxLength() {
        return arguments.maxLength;
    }

    public void setText(final String text) {
        textManager.update(label, config.withText(text));
    }
}

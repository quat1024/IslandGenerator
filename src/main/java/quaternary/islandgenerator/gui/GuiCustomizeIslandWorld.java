package quaternary.islandgenerator.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiCustomizeIslandWorld extends GuiScreen {
	public GuiCustomizeIslandWorld(GuiCreateWorld parentScreen, String settings) {
		this.parentScreen = parentScreen;
		this.settings = settings;
	}
	
	private GuiCreateWorld parentScreen;
	private String settings; //todo
	
	private GuiButton doneButton;
	private GuiButton cancelButton;
	
	private GuiTextField settingsField;
	
	private GuiLabel wipLabel;
	
	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(doneButton = new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("gui.done")));
		buttonList.add(cancelButton = new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel")));
		
		settingsField = new GuiTextField(2, mc.fontRenderer, 50, 40, width - 100, 20);
		settingsField.setMaxStringLength(1280);
		settingsField.setText(settings);
		
		labelList.clear();
		labelList.add(wipLabel = new GuiLabel(mc.fontRenderer, 0, 20, height / 2, 100, 30, 0xFFFFFF));
		wipLabel.addLine("This is as WIP as it looks");
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		settingsField.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		settingsField.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(!settingsField.textboxKeyTyped(typedChar, keyCode)) {
			super.keyTyped(typedChar, keyCode);
		}
	}
	
	@Override
	public void updateScreen() {
		settingsField.updateCursorCounter();
		super.updateScreen();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == doneButton) {
			settings = settingsField.getText();
			parentScreen.chunkProviderSettingsJson = settings;
			mc.displayGuiScreen(parentScreen);
		} else if(button == cancelButton) {
			mc.displayGuiScreen(parentScreen);
		}
	}
}

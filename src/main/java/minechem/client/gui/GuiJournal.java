package minechem.client.gui;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.entity.player.EntityPlayer;

import minechem.client.util.RenderHelper;
import minechem.common.Compendium;
import minechem.common.Config;
import minechem.common.registry.JournalRegistry;
import minechem.common.util.LogHelper;
import repackage.net.afterlifelochie.fontbox.Fontbox;
import repackage.net.afterlifelochie.fontbox.document.Document;
import repackage.net.afterlifelochie.fontbox.document.Element;
import repackage.net.afterlifelochie.fontbox.document.formatting.TextFormat;
import repackage.net.afterlifelochie.fontbox.layout.DocumentProcessor;
import repackage.net.afterlifelochie.fontbox.layout.LayoutException;
import repackage.net.afterlifelochie.fontbox.layout.PageWriter;
import repackage.net.afterlifelochie.fontbox.layout.components.PageProperties;
import repackage.net.afterlifelochie.fontbox.render.BookGUI;

/**
 *
 */
public class GuiJournal extends BookGUI {

	private String[] authorList;
	private int top, left;

	/**
	 * @param who           the player
	 * @param knowledgeKeys a array with all knowledgeKeys of the pages to display
	 * @param authors       a list of authors
	 */
	public GuiJournal(EntityPlayer who, String[] knowledgeKeys, String[] authors) {
		super(UpMode.TWOUP, new Layout[]{new Layout(10, 5), new Layout(138, 5)});

		authorList = authors;

		try {
			/* Create a document */
			Document document = new Document();
			try {
				/* Copy the list of elements */
				List<Element> elements;
				if (Config.playerPrivateKnowledge) {
					elements = JournalRegistry.getJournalFor(who);
				} else {
					elements = JournalRegistry.getJournalFor(knowledgeKeys);
				}
				/* Write elements => document */
				document.pushAll(elements);
			} catch (Throwable thrown) {
				LogHelper.exception(thrown, Level.WARN);
			}

			/* Set up page formatting */
			TextFormat defaultFormat = new TextFormat(Fontbox.fromName("Note this"));
			TextFormat headingFormat = new TextFormat(Fontbox.fromName("Ampersand"));

			PageProperties properties = new PageProperties(221, 380, defaultFormat);
			properties.headingFormat(headingFormat);
			properties.bothMargin(2).lineheightSize(4).spaceSize(4).densitiy(0.33f);

			/* Write elements => page stream */
			PageWriter writer = new PageWriter(properties);
			DocumentProcessor.generatePages(Compendium.Fontbox.tracer(), document, writer);
			writer.close();

			/* Update system pages */
			changePages(writer.pages(), writer.index());
		} catch (LayoutException layout) {
			LogHelper.exception(layout, Level.ERROR);
		} catch (IOException ioex) {
			LogHelper.exception(ioex, Level.ERROR);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	private void drawFoldedPages() {
		if (ptr > 1) {
			// Draw folded page on the left
			drawTexturedModalRect(5, 163, 0, 188, 21, 21);
		}
		if (ptr + 2 < pages.size()) {
			// Draw folded page on the right
			drawTexturedModalRect(230, 160, 21, 188, 21, 21);
		}
	}

	private void drawJournalBackground() {
		drawTexturedModalRect(0, 0, 0, 0, 256, 188);
	}

	@Override
	protected void keyTyped(char c, int keycode) throws IOException {
		super.keyTyped(c, keycode);
		/* Don't listen to KEY_LEFT or KEY_RIGHT; already handled */
		if (keycode == Keyboard.KEY_DOWN) {
			previous();
		}
		if (keycode == Keyboard.KEY_UP) {
			next();
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		mouseX -= left;
		mouseY -= top;
		if (wasRightTabClicked(mouseX, mouseY, mouseButton)) {
			next();
		}

		if (wasLeftTabClicked(mouseX, mouseY, mouseButton)) {
			previous();
		}

		LogHelper.debug("mouseX:" + mouseX + " mouseY:" + mouseY + " mouseButton:" + mouseButton);
	}

	private boolean wasRightTabClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			if (mouseX >= 230 && mouseX <= 230 + 21) {
				if (mouseY >= 160 && mouseY <= 160 + 21) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean wasLeftTabClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			if (mouseX >= 5 && mouseX <= 5 + 21) {
				if (mouseY >= 163 && mouseY <= 163 + 21) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onPageChanged(BookGUI gui, int whatPtr) {

	}

	@Override
	public void drawBackground(int mx, int my, float frame) {
		GL11.glPushMatrix();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glTranslatef(left = width / 2 - 128, top = height / 2 - 94, 0.0f);
		RenderHelper.bindTexture(Compendium.Resource.GUI.journal);
		drawJournalBackground();
		if (pages != null) {
			drawFoldedPages();
		}
	}

	@Override
	public void drawForeground(int mx, int my, float frame) {
		GL11.glPopMatrix();
	}
}

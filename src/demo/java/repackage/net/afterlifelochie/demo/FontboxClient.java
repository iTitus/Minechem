package repackage.net.afterlifelochie.demo;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import repackage.net.afterlifelochie.fontbox.Fontbox;
import repackage.net.afterlifelochie.fontbox.api.PrintOutputTracer;
import repackage.net.afterlifelochie.fontbox.font.FontException;
import repackage.net.afterlifelochie.fontbox.font.GLFont;

public class FontboxClient extends FontboxServer {

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		try {
			Fontbox.setTracer(new PrintOutputTracer());
			GLFont.fromTTF(Fontbox.tracer(), 22.0f, new ResourceLocation("fontbox", "fonts/daniel.ttf"));
			GLFont.fromTTF(Fontbox.tracer(), 22.0f, new ResourceLocation("fontbox", "fonts/notethis.ttf"));
			GLFont.fromTTF(Fontbox.tracer(), 22.0f, new ResourceLocation("fontbox", "fonts/ampersand.ttf"));
		} catch (FontException f0) {
			f0.printStackTrace();
		}
	}

}

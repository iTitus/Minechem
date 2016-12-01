package minechem.asm;

//import codechicken.core.launch.DepLoader;

import java.util.Map;

import net.minecraftforge.classloading.FMLForgePlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.TransformerExclusions({"minechem.asm."})
public class LoadingPlugin implements IFMLLoadingPlugin {

	public static boolean runtimeDeobfEnabled = FMLForgePlugin.RUNTIME_DEOBF;

	public LoadingPlugin() {
		//DepLoader.load();
	}

	@Override
	public String[] getASMTransformerClass() {
		return null/*new String[]{getAccessTransformerClass()}*/;
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null/*"minechem.asm.MinechemTransformer"*/;
	}
}

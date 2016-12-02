package minechem.common.proxy;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import org.apache.logging.log4j.Level;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import minechem.client.font.SimpleModelFontRenderer;
import minechem.client.handler.ClientEventHandler;
import minechem.client.render.tile.TESRCentrifuge;
import minechem.client.render.tile.TESRCrucible;
import minechem.client.render.tile.TESRElectrolysis;
import minechem.client.render.tile.TESRMicroscope;
import minechem.common.Compendium;
import minechem.common.chemical.ChemicalBase;
import minechem.common.chemical.ChemicalBase.Form;
import minechem.common.item.ItemChemical;
import minechem.common.registry.BlockRegistry;
import minechem.common.registry.ItemRegistry;
import minechem.common.tile.TileCentrifuge;
import minechem.common.tile.TileCrucible;
import minechem.common.tile.TileElectrolysis;
import minechem.common.tile.TileMicroscope;
import minechem.common.util.LogHelper;
import repackage.net.afterlifelochie.fontbox.font.FontException;
import repackage.net.afterlifelochie.fontbox.font.GLFont;

public class ClientProxy extends CommonProxy {

	public static SimpleModelFontRenderer modelFontRenderer;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

		LogHelper.debug("Registering Renderers...");

		ModelLoader.setCustomModelResourceLocation(ItemRegistry.journal, 0, new ModelResourceLocation(ItemRegistry.journal.getRegistryName(), "inventory"));

		String[] types = {"element", "molecule"};
		Form[] forms = Form.values();

		ModelResourceLocation[] resLocs = new ModelResourceLocation[1 + types.length * forms.length];
		resLocs[0] = new ModelResourceLocation(ItemRegistry.chemical.getRegistryName().toString() + "/" + "tube", "inventory");
		for (int i = 0; i < types.length; i++) {
			for (int j = 0; j < forms.length; j++) {
				resLocs[1 + i * forms.length + j] = new ModelResourceLocation(ItemRegistry.chemical.getRegistryName().toString() + "/" + forms[j].toString() + "_" + types[i], "inventory");
			}
		}

		ModelLoader.registerItemVariants(ItemRegistry.chemical, resLocs);
		ModelLoader.setCustomMeshDefinition(ItemRegistry.chemical, new ItemMeshDefinition() {

			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				ChemicalBase chemicalBase = ItemChemical.getChemicalBase(stack);
				if (chemicalBase != null) {
					return resLocs[1 + (chemicalBase.isElement() ? 0 : 1) * forms.length + chemicalBase.form.ordinal()];
				}
				return resLocs[0];
			}
		});

		//TODO: Add renderer for augmented items
		ModelLoader.setCustomModelResourceLocation(ItemRegistry.itemAugmented, 0, new ModelResourceLocation(ItemRegistry.itemAugmented.getRegistryName(), "inventory"));

		ModelLoader.setCustomStateMapper(BlockRegistry.light, new StateMapperBase() {

			final ModelResourceLocation modResLoc = new ModelResourceLocation(BlockRegistry.light.getRegistryName(), "normal");

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modResLoc;
			}
		});
		ModelLoader.setCustomStateMapper(BlockRegistry.redstone, new StateMapperBase() {

			final ModelResourceLocation modResLoc = new ModelResourceLocation(BlockRegistry.redstone.getRegistryName(), "normal");

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modResLoc;
			}
		});

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockRegistry.centrifuge), 0, new ModelResourceLocation(BlockRegistry.centrifuge.getRegistryName(), "inventory"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileCentrifuge.class, new TESRCentrifuge());
		//TODO: Remove this hack, replace with JSON
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockRegistry.centrifuge), 0, TileCentrifuge.class);

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockRegistry.electricCrucible), 0, new ModelResourceLocation(BlockRegistry.electricCrucible.getRegistryName(), "inventory"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrucible.class, new TESRCrucible());
		//TODO: Remove this hack, replace with JSON
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockRegistry.electricCrucible), 0, TileCrucible.class);

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockRegistry.electrolysis), 0, new ModelResourceLocation(BlockRegistry.electrolysis.getRegistryName(), "inventory"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileElectrolysis.class, new TESRElectrolysis());
		//TODO: Remove this hack, replace with JSON
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockRegistry.electrolysis), 0, TileElectrolysis.class);

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockRegistry.opticalMicroscope), 0, new ModelResourceLocation(BlockRegistry.opticalMicroscope.getRegistryName(), "inventory"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileMicroscope.class, new TESRMicroscope());
		//TODO: Remove this hack, replace with JSON
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockRegistry.opticalMicroscope), 0, TileMicroscope.class);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);

		Matrix4f m = new Matrix4f();
		m.m20 = 1f / 128f;
		m.m01 = m.m12 = -m.m20;
		m.m33 = 1;
		m.setTranslation(new Vector3f(1, 1, 0));
		Minecraft mc = Minecraft.getMinecraft();
		modelFontRenderer = new SimpleModelFontRenderer(mc.gameSettings, new ResourceLocation("minecraft", "textures/font/ascii.png"), mc.getTextureManager(), false, m, DefaultVertexFormats.ITEM) {

			@Override
			protected float renderUnicodeChar(char c, boolean italic) {
				return super.renderDefaultChar(' ', italic);
			}
		};
		((IReloadableResourceManager) mc.getResourceManager()).registerReloadListener(modelFontRenderer);

		mc.getItemColors().registerItemColorHandler(new IItemColor() {

			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (tintIndex == 0) {
					ChemicalBase chemicalBase = ItemChemical.getChemicalBase(stack);
					if (chemicalBase != null) {
						return chemicalBase.getColour();
					}
				}
				return -1;
			}
		}, ItemRegistry.chemical);

		LogHelper.debug("Registering Fonts...");
		try {
			GLFont.fromTTF(Compendium.Fontbox.tracer(), 22.0f, new ResourceLocation(Compendium.Naming.id, "fonts/daniel.ttf"));
			GLFont.fromTTF(Compendium.Fontbox.tracer(), 22.0f, new ResourceLocation(Compendium.Naming.id, "fonts/notethis.ttf"));
			GLFont.fromTTF(Compendium.Fontbox.tracer(), 22.0f, new ResourceLocation(Compendium.Naming.id, "fonts/ampersand.ttf"));
		} catch (FontException font) {
			LogHelper.exception(font, Level.ERROR);
		}
	}

	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event) {
		for (Form form : Form.values()) {
			ModelResourceLocation modResLoc = new ModelResourceLocation(ItemRegistry.chemical.getRegistryName().toString() + "/" + form.toString() + "_element", "inventory");
			IBakedModel baseBakedModel = event.getModelRegistry().getObject(modResLoc);
			//TODO: Fix font rendering
			//event.getModelRegistry().putObject(modResLoc, new ChemicalItemBakedModel((IPerspectiveAwareModel) baseBakedModel));
		}

		//TODO: Add renderer for augmented items
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().world;
	}

	/**
	 * Get the current lang code
	 *
	 * @return eg. 'en_US'
	 */
	public String getCurrentLanguage() {
		return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
	}

	@Override
	public World getWorld(MessageContext context) {
		return Minecraft.getMinecraft().world;
	}

	@Override
	public EntityPlayer getPlayer(MessageContext context) {
		return Minecraft.getMinecraft().player;
	}

}

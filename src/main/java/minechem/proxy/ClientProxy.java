package minechem.proxy;

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

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import minechem.Compendium;
import minechem.chemical.ChemicalBase;
import minechem.chemical.ChemicalBase.Form;
import minechem.client.font.SimpleModelFontRenderer;
import minechem.helper.LogHelper;
import minechem.item.chemical.ChemicalItem;
import minechem.item.chemical.ChemicalItemBakedModel;
import minechem.registry.BlockRegistry;
import minechem.registry.ItemRegistry;
import repackage.net.afterlifelochie.fontbox.font.FontException;
import repackage.net.afterlifelochie.fontbox.font.GLFont;

public class ClientProxy extends CommonProxy {

	public static SimpleModelFontRenderer modelFontRenderer;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		LogHelper.debug("Registering Renderers...");

		ModelLoader.setCustomModelResourceLocation(ItemRegistry.journal, 0, new ModelResourceLocation(ItemRegistry.journal.getRegistryName(), "inventory"));

		String[] types = {"element", "molecule"};
		Form[] forms = Form.values();

		ModelResourceLocation[] resLocs = new ModelResourceLocation[types.length * forms.length];
		for (int i = 0; i < types.length; i++) {
			for (int j = 0; j < forms.length; j++) {
				resLocs[i * forms.length + j] = new ModelResourceLocation(ItemRegistry.chemicalItem.getRegistryName().toString() + "/" + forms[j].toString() + "_" + types[i], "inventory");
			}
		}

		ModelLoader.registerItemVariants(ItemRegistry.chemicalItem, resLocs);
		ModelLoader.setCustomMeshDefinition(ItemRegistry.chemicalItem, new ItemMeshDefinition() {

			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				ChemicalBase chemicalBase = ChemicalItem.getChemicalBase(stack);
				if (chemicalBase != null) {
					return resLocs[(chemicalBase.isElement() ? 0 : 1) * forms.length + chemicalBase.form.ordinal()];
				}
				return resLocs[0];
			}
		});

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockRegistry.centrifugeBlock), 0, new ModelResourceLocation(BlockRegistry.centrifugeBlock.getRegistryName(), "inventory"));

		ModelLoader.setCustomStateMapper(BlockRegistry.blockLight, new StateMapperBase() {

			final ModelResourceLocation modResLoc = new ModelResourceLocation(BlockRegistry.blockLight.getRegistryName(), "normal");

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modResLoc;
			}
		});
		ModelLoader.setCustomStateMapper(BlockRegistry.blockRedstone, new StateMapperBase() {

			final ModelResourceLocation modResLoc = new ModelResourceLocation(BlockRegistry.blockRedstone.getRegistryName(), "normal");

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modResLoc;
			}
		});

		// RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		// ISBRH_ID = RenderingRegistry.getNextAvailableRenderId();
		//
		// OpticalMicroscopeTileEntityRenderer opticalMicroscopeRenderer = new
		// OpticalMicroscopeTileEntityRenderer();
		// ClientRegistry.bindTileEntitySpecialRenderer(OpticalMicroscopeTileEntity.class,
		// opticalMicroscopeRenderer);
		// MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.opticalMicroscope),
		// new BasicItemRenderer(opticalMicroscopeRenderer, new
		// OpticalMicroscopeTileEntity()));
		//
		// ElectrolysisTileEntityRenderer electrolysisRenderer = new
		// ElectrolysisTileEntityRenderer();
		// ClientRegistry.bindTileEntitySpecialRenderer(ElectrolysisTileEntity.class,
		// electrolysisRenderer);
		// MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.electrolysisBlock),
		// new BasicItemRenderer(electrolysisRenderer, new
		// ElectrolysisTileEntity()));
		//
		// ElectricCrucibleTileEntityRenderer electricCrucibleRenderer = new
		// ElectricCrucibleTileEntityRenderer();
		// ClientRegistry.bindTileEntitySpecialRenderer(ElectricCrucibleTileEntity.class,
		// electricCrucibleRenderer);
		// MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.electricCrucibleBlock),
		// new BasicItemRenderer(electricCrucibleRenderer, new
		// ElectricCrucibleTileEntity()));

//		 CentrifugeTileEntityRenderer centrifugeRenderer = new
//		 CentrifugeTileEntityRenderer();
//		 ClientRegistry.bindTileEntitySpecialRenderer(CentrifugeTileEntity.class,
//		 centrifugeRenderer);
//		 MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegistry.centrifugeBlock),
//		new BasicItemRenderer(centrifugeRenderer, new
//		 CentrifugeTileEntity()));

		// RenderingRegistry.registerBlockHandler(BlockRegistry.blockLight.getRenderType(),
		// new LightRenderer());
		// MinecraftForgeClient.registerItemRenderer(ItemRegistry.chemicalItem,
		// new ChemicalItemRenderer());
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
					ChemicalBase chemicalBase = ChemicalItem.getChemicalBase(stack);
					if (chemicalBase != null) {
						return chemicalBase.getColour();
					}
				}
				return -1;
			}
		}, ItemRegistry.chemicalItem);

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
			ModelResourceLocation modResLoc = new ModelResourceLocation(ItemRegistry.chemicalItem.getRegistryName().toString() + "/" + form.toString() + "_element", "inventory");
			IBakedModel baseBakedModel = event.getModelRegistry().getObject(modResLoc);
			event.getModelRegistry().putObject(modResLoc, new ChemicalItemBakedModel((IPerspectiveAwareModel) baseBakedModel));
		}
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

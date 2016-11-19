package minechem.item.chemical;

import java.util.List;
import javax.vecmath.Matrix4f;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import net.minecraftforge.client.model.IPerspectiveAwareModel;

import minechem.chemical.ChemicalBase;
import minechem.chemical.Element;

public class ChemicalItemBakedModel implements IPerspectiveAwareModel {

	private final IPerspectiveAwareModel baseBakedModel;
	private final Element element;
	private final LoadingCache<Element, IBakedModel> modelCache;
	private ImmutableList<BakedQuad> cachedFontQuads;

	public ChemicalItemBakedModel(IPerspectiveAwareModel baseBakedModel, Element element) {
		this.baseBakedModel = baseBakedModel;
		this.element = element;
		this.modelCache = null;
	}

	public ChemicalItemBakedModel(IPerspectiveAwareModel baseBakedModel) {
		this.baseBakedModel = baseBakedModel;
		this.element = null;
		this.modelCache = CacheBuilder.newBuilder().build(new CacheLoader<Element, IBakedModel>() {

			public IBakedModel load(Element element) throws Exception {
				return new ChemicalItemBakedModel(baseBakedModel, element);
			}
		});
	}

	protected IBakedModel getActualModel(Element element) {
		return element != null ? modelCache.getUnchecked(element) : baseBakedModel;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		builder.addAll(baseBakedModel.getQuads(state, side, rand));

		//FIXME: Add element text back
		// if (cachedFontQuads == null) {
		// SimpleModelFontRenderer fontRenderer = ClientProxy.modelFontRenderer;
		// fontRenderer.drawString(element.shortName, 0, 0, -1);
		// cachedFontQuads = fontRenderer.build();
		// }
		//
		// builder.addAll(cachedFontQuads);

		return builder.build();
	}

	@Override
	public boolean isAmbientOcclusion() {
		return baseBakedModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return baseBakedModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return baseBakedModel.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return baseBakedModel.getParticleTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return baseBakedModel.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ChemicalItemOverrideList.INSTANCE;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		Pair<? extends IBakedModel, Matrix4f> pair = baseBakedModel.handlePerspective(cameraTransformType);
		return Pair.of(this, pair.getRight());
	}

	private static class ChemicalItemOverrideList extends ItemOverrideList {

		public static ChemicalItemOverrideList INSTANCE = new ChemicalItemOverrideList();

		private ChemicalItemOverrideList() {
			super(ImmutableList.of());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
			if (originalModel instanceof ChemicalItemBakedModel) {
				ChemicalBase chemicalBase = ChemicalItem.getChemicalBase(stack);
				if (chemicalBase != null && chemicalBase.isElement()) {
					return ((ChemicalItemBakedModel) originalModel).getActualModel((Element) chemicalBase);
				}
			}
			return originalModel;
		}
	}
}

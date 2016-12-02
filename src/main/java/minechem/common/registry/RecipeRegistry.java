package minechem.common.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;

import minechem.common.recipe.AugmentRecipe;
import minechem.common.recipe.WrapperRecipe;

public class RecipeRegistry {

	private static final RecipeRegistry recipes = new RecipeRegistry();

	public static RecipeRegistry getInstance() {
		return recipes;
	}

	public void init() {
		GameRegistry.addRecipe(new AugmentRecipe());
		GameRegistry.addRecipe(new WrapperRecipe());
	}

}

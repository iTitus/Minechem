package minechem.helper;

import net.minecraft.util.EnumFacing;

public class MathHelper {

	public static int clamp(int val, int min, int max) {
		if (val < min) {
			return min;
		}
		if (val > max) {
			return max;
		}
		return val;
	}

	public static float translateValue(float value, float leftMin, float leftMax, float rightMin, float rightMax) {
		float leftRange = leftMax - leftMin;
		float rightRange = rightMax - rightMin;
		float valueScaled = (value - leftMin) / leftRange;
		return rightMin + (valueScaled * rightRange);
	}

	public static EnumFacing getHorizontal(int index) {
		EnumFacing facing = EnumFacing.getFront(index);
		if (facing.getAxis() == EnumFacing.Axis.Y) {
			return EnumFacing.NORTH;
		}
		return facing;
	}
}

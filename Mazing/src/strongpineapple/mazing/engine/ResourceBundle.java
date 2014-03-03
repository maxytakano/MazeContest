package strongpineapple.mazing.engine;

public class ResourceBundle {
	private static final int BLOCK_INDEX = 0;
	private static final int TOWER_INDEX = 1;
	
	private int[] resources = new int[2];
	
	public ResourceBundle(int block, int tower) {
		this.resources[BLOCK_INDEX] = block;
		this.resources[TOWER_INDEX] = tower;
	}
	
	/**
	 * Initializes a bundle from the internal resources field.
	 */
	private ResourceBundle(int[] resources) {
		this.resources = resources;
	}
	
	public int getBlock() {
		return resources[BLOCK_INDEX];
	}
	
	public int getTower() {
		return resources[TOWER_INDEX];
	}
	
	public ResourceBundle add(ResourceBundle bundle) {
		int[] result = new int[resources.length];
		for (int i = 0; i < resources.length; i++) {
			result[i] = resources[i] + bundle.resources[i];
		}
		
		return new ResourceBundle(result);
	}
	
	public ResourceBundle sub(ResourceBundle bundle) {
		int[] result = new int[resources.length];
		for (int i = 0; i < resources.length; i++) {
			result[i] = resources[i] - bundle.resources[i];
		}
		
		return new ResourceBundle(result);
	}
	
	public boolean canBuy(ResourceBundle itemCost) {
		return !sub(itemCost).isAnyNegative();
	}
	
	/**
	 * @return true if any of the resources in this bundle have a negative value.
	 */
	public boolean isAnyNegative() {
		for (int resource : resources) {
			if (resource < 0) {
				return true;
			}
		}
		
		return false;
	}
}

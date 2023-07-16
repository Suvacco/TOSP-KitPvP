package oldschoolproject.kits.managers;

import oldschoolproject.kits.*;

public enum KitEnum {
	
	PvP(new PvP()),
	Archer(new Archer()),
	Avatar(new Avatar()),
	Kangaroo(new Kangaroo());
	
	Kit kit;

	KitEnum(Kit kit) {
		this.kit = kit;
	}
	
	public Kit getStaticInstance() {
		return this.kit;
	}
	
	public Kit instanceKit() {
		return this.kit.createInstance();
	}
	
}

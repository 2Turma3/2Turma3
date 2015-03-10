package maze.logic;

public class Weapon extends Entity {
	public enum Type{SWORD, DART, SHIELD}
	private Type type;
	
	public Weapon(Type type, Position pos, boolean active) {
		super(pos, active);
		this.setType(type);
	}
	
	public boolean equals(Object object){
		return object != null && 
				object instanceof Weapon && 
				this.getType().equals(((Weapon)object).getType());
		
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}

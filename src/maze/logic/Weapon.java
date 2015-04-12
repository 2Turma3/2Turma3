package maze.logic;

import java.io.Serializable;

public class Weapon extends Entity implements Serializable {

	private static final long serialVersionUID = 838252283178966858L;

	public enum Type{SWORD, DART, SHIELD}
	private Type type;
	
	public Weapon(Type type, Position pos) {
		super(pos);
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

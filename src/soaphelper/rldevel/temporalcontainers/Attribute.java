package soaphelper.rldevel.temporalcontainers;

public class Attribute {
	
	private String name;
	private String type;
	
	public Attribute(){}
		
	public String getName() {
		return name;
	}

	public String getPrettyName() {
		return name.substring(0, 1).toUpperCase()+name.substring(1);
	}

	public String getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}
}
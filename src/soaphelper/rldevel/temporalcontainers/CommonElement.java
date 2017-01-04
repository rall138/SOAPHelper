package soaphelper.rldevel.temporalcontainers;

public class CommonElement extends Element {
	
	private CommonElement parentElement;
	
	public CommonElement(){}
	
	public CommonElement(CommonElement parentElement){
		this.parentElement = parentElement;
	}
	
	public CommonElement getParentElement(){
		return this.parentElement;
	}
	
	public void setParentElement(CommonElement parentElement){
		this.parentElement = parentElement;
	}
}

package soaphelper.rldevel.definitiongen;

import java.util.Calendar;
import java.util.List;

import org.apache.tools.ant.BuildFileRule;
import org.apache.tools.ant.Task;

import soaphelper.generalhelpers.CommonPathFix;
import soaphelper.generalhelpers.CustomStringBuilder;
import soaphelper.generalhelpers.LoggerThread;
import soaphelper.generalhelpers.CommonPathFix.PATH_NAME;
import soaphelper.rldevel.temporalcontainers.*;

public class ClassGenerator extends Task{

	private static final String tabspace = "\t";
	private static final String classHeaderComment = 
			tabspace+"/**\n"
			+tabspace+"* Generated class via ClassGenerator.xml\n"
			+tabspace+"* Creation date: "+Calendar.getInstance().getTime()+"\n"
			+tabspace+"* Creator: rlomez\n"
			+tabspace+"**/";

	private LoggerThread log = new LoggerThread();
	private BuildFileRule bfr = new BuildFileRule();
	private CustomStringBuilder builder = null;
	private CustomStringBuilder attributeBuilder = null;
	private CustomStringBuilder getterAndSetterBuilder = null;
	
	public void execute(){
		generateClasses(parsePatternDefinition());
	}
	
	private List<Element> parsePatternDefinition(){
		bfr.configureProject(CommonPathFix
				.getHardCodedPath(PATH_NAME.CLASSGENERATOR_XML).getPath());
		
		WSDLDefinitionParser parser = new WSDLDefinitionParser();
		return parser.parseDefinition();
	}
	
	private void generateClasses(List<Element> elementList){
		for (Element element : elementList){
			initializeCustomStringBuilders();
			generateClassHeader(element);
			generateAttributes(element);
			generateOverridableMethods(element);
			builder.append(attributeBuilder.toString());
			builder.append(getterAndSetterBuilder.toString());
			builder.appendLn("}");
			generateClasses(element.getName(), builder.toString());
			
		}
	}
	
	private void initializeCustomStringBuilders(){
		this.builder = new CustomStringBuilder();
		this.attributeBuilder = new CustomStringBuilder();
		this.getterAndSetterBuilder = new CustomStringBuilder();
	}
	
	private void generateClassHeader(Element element){
		log.writeSingleMessage("<<< Generating class "+element.getPrettyName()+" >>>");
		builder = new CustomStringBuilder();
		builder.appendLn("package pattern01.helpers.generated;");

		builder.clrlf();
		builder.appendLn("import org.ksoap2.serialization.KvmSerializable;");
		builder.appendLn("import org.ksoap2.serialization.PropertyInfo;");
		builder.appendLn("import java.util.Hashtable;");
		
		builder.clrlf();
		builder.appendLn(classHeaderComment);
		builder.appendLn("public class "+element.getPrettyName()+" implements KvmSerializable{");
	}
	
	private void generateAttributes(Element element){
		for(Attribute attr : element.getAttribute_collection()){
			attributeBuilder.appendLn(2,"private "+attr.getType()+" "+attr.getName()+";");
		}
	}
	
	private void generateOverridableMethods(Element element){
		
		//GETPROPERTY
		getterAndSetterBuilder.clrlf();
		getterAndSetterBuilder.appendLn(2,"@Override");
		getterAndSetterBuilder.appendLn(2,"Public Object getproperty(int i){");
		getterAndSetterBuilder.appendLn(2,"Object data = null;");
		int index =0;
		for (Attribute attr : element.getAttribute_collection()){
			getterAndSetterBuilder.appendLn(3, "switch(i){");
			getterAndSetterBuilder.appendLn(4, "case "+index+":");
			getterAndSetterBuilder.appendLn(5, "data ="+attr.getName()+";");
			getterAndSetterBuilder.appendLn(5, "break;");
		}
		getterAndSetterBuilder.appendLn(4, "default:");
		getterAndSetterBuilder.appendLn(5, "data = null;");
		getterAndSetterBuilder.appendLn(3, "}");
		getterAndSetterBuilder.appendLn(3, "return data;");
		getterAndSetterBuilder.appendLn(2, "}");
		
		getterAndSetterBuilder.clrlf();
		getterAndSetterBuilder.appendLn(2,"@Override");
		getterAndSetterBuilder.appendLn(2,"public int getPropertyCount() {");
		getterAndSetterBuilder.appendLn(3,"return 0;");
		getterAndSetterBuilder.appendLn(2, "}");
		
		getterAndSetterBuilder.clrlf();
		getterAndSetterBuilder.appendLn(2,"@Override");
		getterAndSetterBuilder.appendLn(2,"public void setProperty(int i, Object o) {");
		getterAndSetterBuilder.appendLn(2,"}");

		getterAndSetterBuilder.clrlf();
		getterAndSetterBuilder.appendLn(2,"@Override");
		getterAndSetterBuilder.appendLn(2,"public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {");
		getterAndSetterBuilder.appendLn(2,"}");		
		
	}
	
	private void generateClasses(String className, String classBody){
		bfr.getProject().setProperty("filename", "../generated/"+className+".java");
		bfr.getProject().setProperty("message", classBody);
		bfr.executeTarget("fileRelative");
	}
	
}
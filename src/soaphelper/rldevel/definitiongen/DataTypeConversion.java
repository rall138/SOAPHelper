package soaphelper.rldevel.definitiongen;


public class DataTypeConversion {
	
	public static String getProcessedType(String data_type){
		String processedType = "";
		if (data_type.equals("xsd:string")){
			processedType = "String";
		}else if (data_type.equals("xsd:date")){
			processedType = "java.utils.Date";
		}else{
			processedType =data_type.replace("xsd:", "").replace("tns1:", "").replace(".", "_");
		}
		return processedType;
	}
	
}

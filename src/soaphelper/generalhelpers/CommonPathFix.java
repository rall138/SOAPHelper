package soaphelper.generalhelpers;

import java.io.File;
import java.net.URI;

/***
 * Nasty work-around for getting location of some necessary files
 * @author rlomez
 *
 */
public class CommonPathFix {

	public enum PATH_NAME{CLASSGENERATOR_XML, WORKSPACE_LOC, WSDL_XML};
	
	public static URI getHardCodedPath(PATH_NAME path_name){
		File file = null;
		String os = System.getProperty("os.name");
		switch (path_name) {
			case WSDL_XML:
				file = new File(getHardCodedPath(PATH_NAME.WORKSPACE_LOC).getPath()+				
					barFix(os, "/generalconfig/PatternDefinition.xml"));
			break;
			case CLASSGENERATOR_XML:
				file = new File(getHardCodedPath(PATH_NAME.WORKSPACE_LOC).getPath()+
					barFix(os, "/src/soaphelper/rldevel/definitiongen/ClassGenerator.xml"));
				break;
			case WORKSPACE_LOC:
				if (os.indexOf("Windows")>=0){
					file = new File("E:\\EclipseWorkspace\\SOAPHelper");
				}else{
					file = new File("/home/ricardo/Documents/Eclipse commiters workspace/SOAPHelper");
				}
				break;
		} 
		return file.toURI();
	}
	
	private static String barFix(String os, String relative_path){
		if(os.indexOf("Windows")>=0){
			relative_path = relative_path.replace("/", "\\");
		}
		return relative_path;
	}
	
}

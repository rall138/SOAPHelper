package soaphelper.rldevel.definitiongen;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import soaphelper.generalhelpers.CommonPathFix;
import soaphelper.generalhelpers.CommonPathFix.PATH_NAME;
import soaphelper.rldevel.temporalcontainers.*;

public class WSDLDefinitionParser {

	private XPath xpath = XPathFactory.newInstance().newXPath();
	private String expression = "";
	
	public WSDLDefinitionParser(){}
	
	public List<Element> parseDefinition(){
		List<Element> elementList = new ArrayList<>();
		expression = "/definitions/types/schema/complexType";
		try {
			URI classInstanceXml_uri = CommonPathFix.getHardCodedPath(PATH_NAME.WSDL_XML);
			InputSource is = new InputSource(classInstanceXml_uri.getPath());
			
			Element element;
			Attribute attr;
			NodeList complexTypes = (NodeList) xpath.evaluate(expression, is, XPathConstants.NODESET);
			for (int index = 0; index < complexTypes.getLength(); index++){
				element = new Element();
				element.setName(complexTypes.item(index).getAttributes().getNamedItem("name").getNodeValue().replace(".", "_"));
				expression = "/definitions/types/schema/complexType[@name="+element.getName()+"]/sequence/element";
				NodeList elementsOnSequence = (NodeList) xpath.evaluate(expression, is, XPathConstants.NODESET);
				for (int index2 = 0; index2 < elementsOnSequence.getLength(); index2++){
					attr = new Attribute();
					attr.setName(elementsOnSequence.item(index).getAttributes().getNamedItem("name").getNodeValue());
					attr.setType(DataTypeConversion.getProcessedType(elementsOnSequence
							.item(index).getAttributes().getNamedItem("type").getNodeValue()));
				}
				elementList.add(element);
			}
		} catch (XPathExpressionException | IllegalStateException e) {
			e.printStackTrace();
		}
		return elementList;
	}
	
}

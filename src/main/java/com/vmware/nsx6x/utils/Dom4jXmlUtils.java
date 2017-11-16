package com.vmware.nsx6x.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Dom4jXmlUtils {
    /**
     * Get Document instance by File
     * @param filePath
     * @return
     */
	 public static Document getDomInstance(String filePath)
	 {
	  Document document = null;
	  SAXReader saxReader = new SAXReader();
	  try {
	   document = saxReader.read(new File(filePath));
	  } catch (DocumentException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	  return document;
	 }
	 
	 /**
	  * Get Single Node's value by File
	  * @param filePath
	  * @param nodeHierarchic
	  * @return
	  */
	 public static String getSingleNodeValue(String filePath, String nodeHierarchic)
	 {
	  String nodeValue = "";
	  Document dom = getDomInstance(filePath);
	  Node node = dom.selectSingleNode(nodeHierarchic);
	  nodeValue = node.getStringValue();
	  return nodeValue;
	 }
	 
	 /**
	  * Get Specific Node's value by File
	  * @param filePath
	  * @param nodeHierarchic
	  * @param index
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	public static String getSpecificNodeValue(String filePath, String nodeHierarchic, int index)
	 {
	  String nodeValue = "";
	  Document dom = getDomInstance(filePath);
	  List<Node> nodeList = dom.selectNodes(nodeHierarchic);
	  nodeValue = nodeList.get(index-1).getStringValue();
	  return nodeValue;
	 }
	 
	 /**
	  * Get Node value list by File
	  * @param filePath
	  * @param nodeHierarchic
	  * @return
	  */
	 @SuppressWarnings("unchecked")
	public static List<String> getNodeValueList(String filePath, String nodeHierarchic)
	 {
	  Document dom = getDomInstance(filePath);
	  List<Node> nodeList = dom.selectNodes(nodeHierarchic);
	  List<String> nodeValueList = new ArrayList<String>();
	  for(Node node : nodeList)
	  {
	   nodeValueList.add(node.getStringValue());
	  }
	  return nodeValueList;
	 }
	 
	 /**
	  * modify the specific node value by String
	  * @param xmlContents
	  * @param nodeHierarchic
	  * @param index
	  * @param nodeValue
	  * @return
	  * @throws DocumentException
	  */
	 public static String modifySpecificNodeValue(String xmlContents, String nodeHierarchic, int index, String nodeValue)
	 {
	  String finaleXmlContents = "";
	  Document dom = null;
	try {
		dom = DocumentHelper.parseText(xmlContents);
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  @SuppressWarnings("unchecked")
	List<Node> nodeList = dom.selectNodes(nodeHierarchic);
	  nodeList.get(index-1).setText(nodeValue);
	  finaleXmlContents = dom.asXML();
	 
	  return finaleXmlContents;
	 }
	 
	 /**
	  * modify the specific node value by File
	  * @param filePath
	  * @param nodeHierarchic
	  * @param index
	  * @param nodeValue
	  * @return
	  * @throws DocumentException
	  */
	 @SuppressWarnings("unchecked")
	public static String modifySpecificNodeValueByFile(String filePath, String nodeHierarchic, int index, String nodeValue)
	 {
	  String finaleXmlContents = "";
	  Document dom = getDomInstance(filePath);
	  List<Node> nodeList = dom.selectNodes(nodeHierarchic);
	  nodeList.get(index-1).setText(nodeValue);
	  finaleXmlContents = dom.asXML();
	 
	  return finaleXmlContents;
	 }
	 
	 /**
	  * get specific Node Value by String
	  * @param xmlContents
	  * @param nodeHierarchic
	  * @param index
	  * @return
	  * @throws Exception
	  */
	 @SuppressWarnings("unchecked")
	public static String getSpecificNodeValueByString(String xmlContents, String nodeHierarchic, int index)
	 {
		  String nodeValue = "";
		  Document dom = null;
			try {
				dom = DocumentHelper.parseText(xmlContents);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			  List<Node> nodeList = dom.selectNodes(nodeHierarchic);
			  nodeValue = nodeList.get(index-1).getStringValue();
			  return nodeValue;
	 }
}

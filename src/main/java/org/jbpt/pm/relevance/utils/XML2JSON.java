package org.jbpt.pm.relevance.utils;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FilenameUtils;
import org.jbpt.pm.relevance.models.Bundle;
import org.jbpt.pm.relevance.models.JArc;
import org.jbpt.pm.relevance.models.JNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XML2JSON {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File folder = new File(args[0]);
		File[] listOfFiles = folder.listFiles();

		for (File fXmlFile : listOfFiles) {
			if (fXmlFile.isFile()) {
				System.out.println(fXmlFile.getName());
				List<JNode> nodes = new ArrayList<JNode>();
				List<JArc> arcs = new ArrayList<JArc>();
				try {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fXmlFile);

					doc.getDocumentElement().normalize();

					NodeList snList = doc.getElementsByTagName("StartNode");

					for (int temp = 0; temp < snList.getLength(); temp++) {

						Node nNode = snList.item(temp);

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;
							JNode n = new JNode(Integer.parseInt(eElement.getAttribute("index")), "Process Start", 0);
							nodes.add(n);
						}
					}

					NodeList nList = doc.getElementsByTagName("Node");

					for (int temp = 0; temp < nList.getLength(); temp++) {

						Node nNode = nList.item(temp);

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;
							JNode n = new JNode(Integer.parseInt(eElement.getAttribute("index")),
									eElement.getAttribute("activity"),
									Integer.parseInt(eElement.getElementsByTagName("Frequency").item(0).getAttributes()
											.getNamedItem("total").getTextContent()));
							nodes.add(n);
						}
					}

					NodeList nnList = doc.getElementsByTagName("EndNode");

					for (int temp = 0; temp < nnList.getLength(); temp++) {

						Node nNode = nnList.item(temp);

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;
							JNode n = new JNode(Integer.parseInt(eElement.getAttribute("index")), "Process End", 0);
							nodes.add(n);
						}
					}

					NodeList eList = doc.getElementsByTagName("Edge");

					for (int temp = 0; temp < eList.getLength(); temp++) {

						Node eNode = eList.item(temp);

						if (eNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) eNode;
							JArc a = new JArc(Integer.parseInt(eElement.getAttribute("sourceIndex")),
									Integer.parseInt(eElement.getAttribute("targetIndex")),
									Integer.parseInt(eElement.getElementsByTagName("Frequency").item(0).getAttributes()
											.getNamedItem("total").getTextContent()));
							arcs.add(a);
						}
					}

					Bundle dfg = new Bundle(nodes, arcs);
					PrintStream write = new PrintStream(
							new File(args[1] + FilenameUtils.removeExtension(fXmlFile.getName()) + ".json"));
					dfg.toJson(write);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

}

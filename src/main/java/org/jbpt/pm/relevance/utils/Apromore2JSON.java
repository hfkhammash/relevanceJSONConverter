package org.jbpt.pm.relevance.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.jbpt.pm.relevance.models.Bundle;
import org.jbpt.pm.relevance.models.JArc;
import org.jbpt.pm.relevance.models.JNode;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Apromore2JSON {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File folder = new File(args[0]);
		File[] listOfFiles = folder.listFiles();

		for (File fXmlFile : listOfFiles) {
			if (fXmlFile.isFile()) {
				System.out.println(fXmlFile.getName());

				List<JNode> nodes = new ArrayList<JNode>();
				List<JArc> arcs = new ArrayList<JArc>();

				// JsonParser jsonParser = new JsonParser();

				JsonElement jsonTree;

				try {
					jsonTree = JsonParser.parseReader(new FileReader(args[0] +"/"+ fXmlFile.getName()));
					if (jsonTree.isJsonArray()) {
						JsonArray arrayFirstLevel = jsonTree.getAsJsonArray();
						for (int i = 0; i < arrayFirstLevel.size(); i++) {
							JsonObject elementsFirst = (JsonObject) arrayFirstLevel.get(i);
							if (elementsFirst.get("data").getAsJsonObject().keySet().contains("name")) {
								String name = elementsFirst.get("data").getAsJsonObject().get("name").getAsString();
								if (!name.isEmpty()) {
									String oriname = elementsFirst.get("data").getAsJsonObject().get("oriname")
											.getAsString();
									int id = elementsFirst.get("data").getAsJsonObject().get("id").getAsInt();
									String delims = "\n\n";
									String[] tokens = name.split(delims);
									int freq = Integer.parseInt(tokens[1]);
									JNode n = new JNode(id, oriname, freq);
									nodes.add(n);
								}
								if (name.isEmpty()) {
									String oriname = elementsFirst.get("data").getAsJsonObject().get("oriname")
											.getAsString();
									int id = elementsFirst.get("data").getAsJsonObject().get("id").getAsInt();
									if (oriname.equals("|>")) {
										JNode n = new JNode(id, "Process Start", 0);
										nodes.add(n);
									}
									if (oriname.equals("[]")) {
										JNode n = new JNode(id, "Process End", 0);
										nodes.add(n);
									}
								}
							}

							if (elementsFirst.get("data").getAsJsonObject().keySet().contains("label")) {
								String sourceIndex = elementsFirst.get("data").getAsJsonObject().get("source")
										.getAsString();
								String targetIndex = elementsFirst.get("data").getAsJsonObject().get("target")
										.getAsString();
								String label = elementsFirst.get("data").getAsJsonObject().get("label").getAsString();
								JArc a = new JArc(Integer.parseInt(sourceIndex), Integer.parseInt(targetIndex),
										Integer.parseInt(label));
								arcs.add(a);
							}
						}
					}
				} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Bundle dfg = new Bundle(nodes, arcs);
				PrintStream write;
				try {
					write = new PrintStream(
							new File(args[1] +"/"+ FilenameUtils.removeExtension(fXmlFile.getName()) + ".json"));
					dfg.toJson(write);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

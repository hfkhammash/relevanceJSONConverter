package org.jbpt.pm.relevance.models;

import java.io.PrintStream;
import java.util.List;

public class Bundle {
	public List<JNode> nodes;
    public List<JArc> arcs;
    public Bundle(List<JNode> nodes, List<JArc> arcs) {
		super();
		this.nodes = nodes;
		this.arcs = arcs;
	}

    public void toJson(PrintStream out) {
        out.println("{");
        out.println("\t\"nodes\": [");
       
        for(int i = 0; i< nodes.size();i++) {
        	JNode n = nodes.get(i);
        	if (n!= null) out.print(n.toJSON());
        	if (i+1 != nodes.size()) out.println(","); else out.println();
        }
        
        out.println("\t],");
        out.println("\t\"arcs\": [");
        
        for(int i = 0; i< arcs.size();i++) {
        	JArc a = arcs.get(i);
        	if (a!= null) out.print(a.toJSON());
        	if (i+1 != arcs.size()) out.println(","); else out.println();
        }
        
        out.println("\t]");
        out.println("}");
    }
}
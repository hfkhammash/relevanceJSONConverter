package org.jbpt.pm.relevance.models;

public class JNode {
	
	    private Integer id;
	    private String label;
	    private Integer freq;

	    public Integer getId() { return id; }
	    public String getLabel() { return label; }
	    public Integer getFreq() { return freq; }

	    public JNode(Integer id, String label, Integer freq) {
	        this.id = id;
	        this.label = label;
	        this.freq = freq;
	    }

	    @Override
	    public String toString() {
	        return "{id=" + id + ", label='" + label + '\'' + ", freq=" + freq + '}';
	    }
	    public String toDot() {
	        return String.format("\tn%d [label=\"%s\\n%d\"];", id, label, freq);
	    }
	    
	    public String toJSON() {
	        return String.format("\t{\"label\":\"%s\", \"freq\": %d, \"id\": %d}",label, freq, id);
	    }

}

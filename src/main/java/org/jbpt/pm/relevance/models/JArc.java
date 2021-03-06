package org.jbpt.pm.relevance.models;

public class JArc {

	    private Integer from;
	    private Integer to;
	    private Integer freq;

	    public Integer getFrom() {
	        return from;
	    }
	    public Integer getTo() {
	        return to;
	    }
	    public Integer getFreq() { return freq; }

	    public JArc(Integer from, Integer to, Integer freq) {
	        this.from = from;
	        this.to = to;
	        this.freq = freq;
	    }

	    public String toString() {
	        return String.format("(%d) - [%d] -> (%d)", from, freq, to);
	    }
	    public String toDot() {
	        return String.format("\tn%d -> n%d [label=\"%d\"];", from, to, freq);
	    }
	
	    public String toJSON() {
	        return String.format("\t{\"from\": %d, \"to\": %d, \"freq\": %d}",from, to, freq);
	    }
}

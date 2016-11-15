package wlfe.common;

/**
 * Basic key value type class.
 * header = key and property = value;
 * @author Matthew Micciolo
 *
 */
public class DataTableColumn {
	
	private String header;
    private String property;
    
    /**
     * Constructor takes a header and property
     * @author Matthew Micciolo
     * @param header header
     * @param property property
     */
    public DataTableColumn(String header, String property) {
        this.header = header;
        this.property = property;
    }
    
    /**
     * Get the header
     * @author Matthew Micciolo
     * @return header
     */
    public String getHeader() {
        return header;
    }
    
    /** 
     * Get the property
     * @author Matthew Micciolo
     * @return property
     */
    public String getProperty() {
        return property;
    } 
    
    /**
     * Set the header
     * @author Matthew Micciolo
     * @param header header to set
     */
    public void setHeader(String header) {
    	this.header = header;
    }
    
    /**
     * Set the property
     * @author Matthew Micciolo
     * @param property property to set
     */
    public void setProperty(String property) {
    	this.property = property;
    }
}

package wlfe.common;

import java.util.List;

public class DataTableColumn {
	
	private String header;
    private String property;
    
    public DataTableColumn(String header, String property) {
        this.header = header;
        this.property = property;
    }
    
    public String getHeader() {
        return header;
    }
    
    public String getProperty() {
        return property;
    } 
    
    public void setHeader(String header) {
    	this.header = header;
    }
    
    public void setProperty(String property) {
    	this.property = property;
    }
    
	public static String getPropertyFromHeader(String header, List<DataTableColumn> columns) {
		for(DataTableColumn col : columns) {
			if(col.getHeader().equals(header)) {
				return col.getProperty();
			}
		}
		return "";
	}
	
	public static void setPropertyFromHeader(String header, String property, List<DataTableColumn> columns) {
		for(DataTableColumn col : columns) {
			if(col.getHeader().equals(header)) {
				col.setProperty(property);
			}
		}
	}
}

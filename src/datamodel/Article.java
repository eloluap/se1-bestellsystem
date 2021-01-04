package datamodel;

public class Article {
	private String id;
	private String description;
	private long unitPrice;
	private int unitsInStore;
	
	protected Article(String id, String descr, long price, int units) {
		this.id = id;
		this.setDescription(descr);
		this.setUnitPrice(price);
		this.setUnitsInStore(units);
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String descr) {
		if (descr == null) {
			this.description = "";
		} else {
			this.description = descr;	
		}
	}
	
	public long getUnitPrice() {
		return this.unitPrice;
	}
	
	public void setUnitPrice (long price) {
		if (price < 0 || price == Long.MAX_VALUE) {
			this.unitPrice = 0;
		} else {
			this.unitPrice = price;			
		}
	}
	
	public int getUnitsInStore() {
		return this.unitsInStore;
	}
	
	public void setUnitsInStore(int number) {
		if (number < 0 || number == Integer.MAX_VALUE) {
			this.unitsInStore = 0;
		} else {
			this.unitsInStore = number;			
		}
	}
}
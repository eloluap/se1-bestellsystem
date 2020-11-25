package datamodel;

public class OrderItem {
	private String description;
	private final Article article;
	private int unitsOrdered;
	
	protected OrderItem(String descr, Article article, int units) {
		this.description = descr;
		this.article = article;
		this.unitsOrdered = units;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String descr) {
		this.description = descr;
	}
	
	public Article getArticle() {
		return this.article;
	}
	
	public int getUnitsOrdered() {
		return this.unitsOrdered;
	}
	
	public void setUnitsOrdered(int number) {
		this.unitsOrdered = number;
	}
}
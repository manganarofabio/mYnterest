package Model;

public class News {
	
	
	private String titolo;
	private String url;
	private String contenuto;
	Interest interesse;
	
	
	public News(String titolo, String url, String contenuto, Interest interesse) {
		super();
		this.titolo = titolo;
		this.url = url;
		this.contenuto = contenuto;
		this.interesse = interesse;
	}


	public String getTitolo() {
		return titolo;
	}


	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getContenuto() {
		return contenuto;
	}


	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}


	public Interest getInteresse() {
		return interesse;
	}


	public void setInteresse(Interest interesse) {
		this.interesse = interesse;
	}
	
	
	
	

}

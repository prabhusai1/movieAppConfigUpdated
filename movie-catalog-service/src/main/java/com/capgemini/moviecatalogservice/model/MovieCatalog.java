package com.capgemini.moviecatalogservice.model;

import java.util.List;

public class MovieCatalog {
	private List<CatalogItem> catalogItems;

	public MovieCatalog() {
		super();
	}

	public List<CatalogItem> getCatalogItems() {
		return catalogItems;
	}

	public void setCatalogItems(List<CatalogItem> catalogItems) {
		this.catalogItems = catalogItems;
	}

	public MovieCatalog(List<CatalogItem> catalogItems) {
		super();
		this.catalogItems = catalogItems;
	}

}

package com.dbiservices.dam.engine;

import java.util.ArrayList;

public class DocTable {

	private ArrayList<String> headers = new ArrayList<>();
	private ArrayList<DocRow> rows = new ArrayList<>();

	public ArrayList<DocRow> getRows() {
		return rows;
	}

	public void setRows(ArrayList<DocRow> rows) {
		this.rows = rows;
	}

	public ArrayList<String> getHeaders() {
		return headers;
	}

	public void setHeaders(ArrayList<String> headers) {
		this.headers = headers;
	}
}

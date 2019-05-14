package com.ngopidevteam.pranadana.crud.model;

import com.google.gson.annotations.SerializedName;

public class DatanyaItem{

	@SerializedName("nim")
	private String nim;

	@SerializedName("nama")
	private String nama;

	@SerializedName("jurusan")
	private String jurusan;

	@SerializedName("id")
	private String id;

	public void setNim(String nim){
		this.nim = nim;
	}

	public String getNim(){
		return nim;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setJurusan(String jurusan){
		this.jurusan = jurusan;
	}

	public String getJurusan(){
		return jurusan;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"DatanyaItem{" + 
			"nim = '" + nim + '\'' + 
			",nama = '" + nama + '\'' + 
			",jurusan = '" + jurusan + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
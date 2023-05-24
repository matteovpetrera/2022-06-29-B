package it.polito.tdp.itunes.model;

public class BilancioAlbum implements Comparable<BilancioAlbum>{
	
	int bilancio;
	Album album;
	public BilancioAlbum(int bilancio, Album album) {
		super();
		this.bilancio = bilancio;
		this.album = album;
	}
	/**
	 * @return the bilancio
	 */
	public int getBilancio() {
		return bilancio;
	}
	/**
	 * @return the album
	 */
	public Album getAlbum() {
		return album;
	}
	@Override
	public String toString() {
		return "BilancioAlbum [bilancio=" + bilancio + ", album=" + album + "]";
	}
	@Override
	public int compareTo(BilancioAlbum o) {
		// TODO Auto-generated method stub
		return o.getBilancio()-this.bilancio;
	}
	
	
}

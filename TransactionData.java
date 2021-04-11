package com.example.demo;

import java.io.Serializable;
import java.time.LocalDate;

public class TransactionData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tsc_id;
	private LocalDate tsc_date;
	private String name;
	private int tsc_amount;
	private int lar_tsc;
	public TransactionData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TransactionData(int tsc_id, LocalDate tsc_date, String name, 
			int tsc_amount, int lar_tsc) {
		super();
		this.tsc_id = tsc_id;
		this.tsc_date = tsc_date;
		this.name = name;
		this.tsc_amount = tsc_amount;
		this.lar_tsc = lar_tsc;
	}
	public int getTsc_id() {
		return tsc_id;
	}
	public void setTsc_id(int tsc_id) {
		this.tsc_id = tsc_id;
	}
	public LocalDate getTsc_date() {
		return tsc_date;
	}
	public void setTsc_date(LocalDate tsc_date) {
		this.tsc_date = tsc_date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTsc_amount() {
		return tsc_amount;
	}
	public void setTsc_amount(int tsc_amount) {
		this.tsc_amount = tsc_amount;
	}
	public int getLar_tsc() {
		return lar_tsc;
	}
	public void setLar_tsc(int lar_tsc) {
		this.lar_tsc = lar_tsc;
	}
	@Override
	public String toString() {
		return "TransactionData [tsc_id=" + tsc_id + ", tsc_date=" + tsc_date + ", name=" + name + ", tsc_amount="
				+ tsc_amount + ", lar_tsc=" + lar_tsc + "]";
	}
}

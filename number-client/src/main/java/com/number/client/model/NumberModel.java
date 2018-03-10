package com.number.client.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NumberModel {

	@Id
	private long number;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate insertDate;
	
	public long getNumber() {
		return number;
	}
	
	public void setNumber(long number) {
		this.number = number;
	}
	
	public LocalDate getInsertDate() {
		return insertDate;
	}
	
	public void setInsertDate(LocalDate insertDate) {
		this.insertDate = insertDate;
	}
	
	
}

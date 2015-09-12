package br.com.paulobueno.model;

import java.io.Serializable;

/**
 * POJO para senha
 * 
 * @author paulo.bueno
 *
 */
public class Password implements Serializable {
	
	private static final long serialVersionUID = -8248742628248768645L;
	public static final int NORMAL_PASSWORD = 0;
	public static final int PREFERENTIAL_PASSWORD = 1;
	public static final String NORMAL_FORMAT = "N%1$05d";
	public static final String PREFERENTIAL_FORMAT = "P%1$05d";
	
	private int number;
	private String code;
	private int type;
	private boolean called;

	public boolean isCalled() {
		return called;
	}

	public void setCalled(boolean called) {
		this.called = called;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}

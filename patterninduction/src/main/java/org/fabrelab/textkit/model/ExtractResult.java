package org.fabrelab.textkit.model;

public class ExtractResult {
	String address;
	String status;
	boolean success;
	
	public ExtractResult(String address, String status, boolean success) {
		super();
		this.address = address;
		this.status = status;
		this.success = success;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "ExtractResult [address=" + address + ", status=" + status + ", success=" + success + "]";
	}
	
}

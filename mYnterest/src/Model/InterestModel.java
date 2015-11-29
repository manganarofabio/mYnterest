package Model;

import mYnterest.Interest;

public class InterestModel {

	
	User u;
	Interest i;
	
	public Interest getI() {
		return i;
	}

	public void setI(Interest i) {
		this.i = i;
	}

	public InterestModel(User u, Interest i) {
		super();
		this.u = u;
		this.i=i;	
	}

	public User getU() {
		return u;
	}
	
	public void setU(User u) {
		this.u = u;
	}



}

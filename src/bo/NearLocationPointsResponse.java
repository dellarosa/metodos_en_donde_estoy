package edu.palermo.dondeestoy.bo;

public class NearLocationPointsResponse extends BaseResponse{

	private LocationPoint[] list = null;
	
	public LocationPoint[] getList() {
		return list;
	}

	public void setList(LocationPoint[] list) {
		this.list = list;
	}
	
}

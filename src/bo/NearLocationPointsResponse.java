package bo;

public class NearLocationPointsResponse {

	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private LocationPoint[] list = null;
	
	public LocationPoint[] getList() {
		return list;
	}

	public void setList(LocationPoint[] list) {
		this.list = list;
	}
	
}

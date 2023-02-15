public class TableDataModel implements Comparable<TableDataModel> {

	private String aekoLink;
	private String longData;
	private String latData;
	private String cRS;

	private TableDataModel() {
		//dont allow creation of Object without elements
	}

	public String getAekoLink() {
		return aekoLink;
	}

	public void setAekoLink(String aekoLink) {
		this.aekoLink = aekoLink;
	}

	public String getLongData() {
		return longData;
	}

	public void setLongData(String longData) {
		this.longData = longData;
	}

	public String getLatData() {
		return latData;
	}

	public void setLatData(String latData) {
		this.latData = latData;
	}

	public String getcRS() {
		return cRS;
	}

	public void setcRS(String cRS) {
		this.cRS = cRS;
	}

	public static TableDataModel newInstance(String aekoLink,String longData, String latData, String cRs ) {
		TableDataModel model = new TableDataModel();
		model.setAekoLink(aekoLink);
		model.setLongData(longData);
		model.setLatData(latData);
		model.setcRS(cRs);
		return model;
	}

	@Override
	public int compareTo(TableDataModel o) {
		return this.getAekoLink().compareTo(o.getAekoLink());
	}

	public Object[] getDataAsObjectList() {
		Object[] columnVals = new Object[4];
		columnVals[0] = this.getAekoLink();
		columnVals[1] = this.getLongData();
		columnVals[2] = this.getLatData();
		columnVals[3] = this.getcRS();
		return columnVals;
	}

}
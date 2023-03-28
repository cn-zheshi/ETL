package util;

public class Content {
    private String formUrl;//源文件地址
	private String tranformUrl;//转换文件地址
	private String toContent;//转换后的内容
	public String getFormUrl() {
		return formUrl;
	}
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	public String getTranformUrl() {
		return tranformUrl;
	}
	public void setTranformUrl(String tranformUrl) {
		this.tranformUrl = tranformUrl;
	}
	public String getToContent() {
		return toContent;
	}
	public void setToContent(String toContent) {
		this.toContent = toContent;
	}
}

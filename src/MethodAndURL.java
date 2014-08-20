
public class MethodAndURL {
	private String method;
	private String URL;
	
	public MethodAndURL(String method, String URL) {
		this.method = method;
		this.URL = URL;
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getURL() {
		return URL;
	}
	
	public String getName() {
		int hasQuery = URL.indexOf('?');
		if (hasQuery != -1) {
			return "\"name\":" + "\"" + URL.substring(URL.indexOf("//") + 2, hasQuery) + "\",";
		} else {
			return "\"name\":" + "\"" + URL.substring(URL.indexOf("//") + 2);
		}
		
	}
	
	public String toString() {
		return method + " : " + URL;
	}
}

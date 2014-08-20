import java.io.*;
import java.util.*;


public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/Shan/Documents/workspace_java/Postman/src/parent.json"), "UTF-8"));  
		PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("/Users/Shan/Documents/workspace_java/Postman/src/parent_output.json"), "UTF-8"));  
		String line, prevLine = "";  
		MethodAndURL methodAndUrl;
		int id = 0;
		int idCounter = 0;
		long timestamp = 1408512522146L;
		ArrayList<MethodAndURL> collection = new ArrayList<MethodAndURL>();
		
		
		String beforeOrder = "{\"id\": \"64d16f4f-320b-2234-6fa8-f031b8b29c6a\",\"name\": \"schoolmint\",\"description\": \"\",";
		String order = "\"order\" :[";
		String afterOrder = "\"folders\": [], \"timestamp\":" + timestamp + ",\"synced\": false,";
		String beforeRequests = "\"requests\": [";
		String requests = "";
		String afterRequests = "]}";
		
		String betweenURLAndMethod = "\"pathVariables\": {},\"preRequestScript\": \"\", ";
		String betweenMethodAndName = "\"data\": [],\"dataMode\": \"params\",";
		String afterName = "\"description\": \"\",\"descriptionFormat\": \"html\", \"time\": 1408512881534,\"version\": 2,\"responses\": [],\"tests\": \"\",\"collectionId\": \"64d16f4f-320b-2234-6fa8-f031b8b29c6a\",\"synced\": false},";
		
		
		while ((line = in.readLine()) != null) {
			if (line.trim().isEmpty()) {
				continue;
			}
			methodAndUrl = getMethodAndURL(line, prevLine);
			if (methodAndUrl == null || collection.contains(methodAndUrl)) {
				prevLine = line;
				continue;
			}
			collection.add(methodAndUrl);
			requests += "{" + generateId(id) + methodAndUrl.getURL() + betweenURLAndMethod + 
					methodAndUrl.getMethod() + betweenMethodAndName + methodAndUrl.getName() + afterName;
			idCounter++;
			id++;
		}
		for (int i = 1; i < idCounter; i++) {
			order += "\"" + i + "\",";
		}
		order += "\"" + idCounter + "\"],";
		
		out.print(beforeOrder);
		out.print(order);
		out.print(afterOrder);
		out.print(beforeRequests);
		out.print(requests.substring(0, requests.length()-1));
		out.print(afterRequests);
		
		out.close();  
		in.close();
		System.out.println("Done!");
	}
	
	public static MethodAndURL getMethodAndURL(String line, String prevLine) {
		int indexOfURL = line.indexOf("\"url\": \"");
		int indexOfMethod = prevLine.indexOf("\"method\": \"");
		if (indexOfURL == -1 || indexOfMethod == -1) {
			return null;
		}
		String urlAddress = line.substring(indexOfURL);
		String method = prevLine.substring(indexOfMethod);
		if (urlAddress.indexOf("localhost:3081") == -1) {
			return null;
		}
		return new MethodAndURL(method, urlAddress);
	}
	
	public static String generateId(int id) {
		id++;
		return "\"id\":\"" + id + "\",";
	}
	
}
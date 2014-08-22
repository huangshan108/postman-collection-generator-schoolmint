import java.io.*;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	private static String collectionName;
	private static String backendAPI;
	
	public void start() throws JSONException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/Shan/Documents/workspace_java/Postman/src/admin.json"), "UTF-8"));  
		PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("/Users/Shan/Documents/workspace_java/Postman/src/admin_output.json"), "UTF-8"));  
		String line, prevLine = "";  
		MethodAndURL methodAndUrl;
		int id = 0;
		int folderId = 100000;
		long timestamp = 1408512522146L;
		ArrayList<MethodAndURL> collection = new ArrayList<MethodAndURL>();
		ArrayList<Folder> allFolders = new ArrayList<Folder>();
		Request request;
		
		SessionIdentifierGenerator SID = new SessionIdentifierGenerator();
		String collectionId = "\"" + SID.nextSessionId() + "\"";
		collectionName = "schoolmint";
		String beforeOrder = "{\"id\": " + collectionId + ",\"name\": \"" + collectionName + "\",\"description\": \"\",";
		String order = "\"order\" :[],";
		String folderString = "\"folders\" :[";
		String afterOrder = "],\"timestamp\":" + timestamp + ",\"synced\": false,";
		String beforeRequests = "\"requests\": [";
		String requests = "";
		String requestString = "";
		String afterRequests = "]}";
		
		String betweenURLAndMethod = "\"pathVariables\": {},\"preRequestScript\": \"\", ";
		String betweenMethodAndName = "\"data\": [],\"dataMode\": \"params\",";
		String afterName = "\"description\": \"\",\"descriptionFormat\": \"html\", \"time\": 1408512881534,\"version\": 2,\"responses\": [],\"tests\": \"\",\"collectionId\": " + collectionId + ",\"synced\": false},";
		
		
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
			requestString = "{" + generateId(id) + methodAndUrl.getURL() + betweenURLAndMethod + 
					methodAndUrl.getMethod() + betweenMethodAndName + methodAndUrl.getName() + afterName;
			request = new Request(id, methodAndUrl.getName(), requestString);
			requests += request;
			
			Folder folderObj = new Folder(generateFolderId(folderId), request.getGroup(), collectionName, collectionId);
			//Add the new request into folder
			if (allFolders.contains(folderObj)) {	//if the folder already been created
				allFolders.get(allFolders.indexOf(folderObj)).addInFolder(request.getId());
			} else { //create a new folder and add into it
				folderObj.addInFolder(request.getId());
				allFolders.add(folderObj);
			}
			id++;
			folderId++;
			prevLine = line;
		}
		

		
		//creating folders
		for (Folder f: allFolders) {
			folderString += f;
		}
		
		
		String rawJSON = beforeOrder + order + folderString.substring(0, folderString.length() - 1) + afterOrder + beforeRequests + requests.substring(0, requests.length()-1) + afterRequests;
		JSONObject json = new JSONObject(rawJSON); 
		out.print(json.toString(4));
//		out.print(rawJSON);
		out.close();  
		in.close();
		System.out.println("Done!");
	}
	
	public static void main(String[] args) throws IOException, JSONException {
		Main m = new Main();
		m.start();
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
		return "\"id\":\"" + id + "\",";
	}
	
	public static String generateFolderId(int folderId) {
		return "\"" + folderId + "\",";
	}
}
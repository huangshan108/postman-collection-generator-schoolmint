import java.util.ArrayList;


public class Folder {
	private String folderId;
	private String folderName;
	private ArrayList<String> order;
	private String collectionName;
	private String collectionId;
	
	public Folder(String folderId, String folderName, String collectiionName, String collectionId) {
		this.folderId = folderId;
		this.folderName = folderName;
		this.collectionName = collectiionName;
		this.collectionId = collectionId;
		this.order = new ArrayList<>();
	}
}

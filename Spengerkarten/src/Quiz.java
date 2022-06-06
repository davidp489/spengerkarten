import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {
	
	//Hier ist die HashMap und so drinnen (Technik hinter dem Ganzen)
	
	// Name vom Quiz
	private String quizName;
	
	// values (zb vokabeln)
	private HashMap<String, String> values;
	
	public Quiz(String quizName) {
		values = new HashMap<>();
		this.quizName = quizName;
	}
	
	public String getName() {
		return quizName;
	}
	
	// vokabel hinzufügen
	public void addVocab(String key, String value) {
		values.put(key, value);
	}
	
	public String getValue(String key) {
		return values.get(key);
	}
	
	// optional, muss man nicht benutzen, geht aber auch
	public HashMap<String, String> getMap(){
		return values;
	}
	
	// damit kann man mit einem index durch alle keys durchgehen
	// in kombination mit getValue(key) hast du dann alles was du brauchst
	public String getKeyFromIndex(int index) {
		return new ArrayList<String>(values.keySet()).get(index);
	}
	
	public int getSize() {
		return values.size();
	}
	
	// schaut ob key bzw value ein Pfad ist
	public boolean isPath(String s) {
		if(s.contains("\\")||s.contains("/")) return true;
		return false;
	}
	
}






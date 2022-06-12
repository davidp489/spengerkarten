import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

public class Quiz {
	
	//Hier ist die HashMap und so drinnen (Technik hinter dem Ganzen)
	

	// Name vom Quiz
	private String name;
	
	// values (zb vokabeln)
	private LinkedHashMap<String, String> values;
	
	public Quiz(String quizName) {
		values = new LinkedHashMap<>();
		this.name = quizName;
	}
	
	public String getName() {
		return name;
	}
	
	// vokabel hinzufügen
	public void addVocab(String key, String value) {
		values.put(key, value);
	}
	
	public String getValue(String key) {
		return values.get(key);
	}
	
	// optional, muss man nicht benutzen, geht aber auch
	public LinkedHashMap<String, String> getMap(){
		return values;
	}
	
	// damit kann man mit einem index durch alle keys durchgehen
	// in kombination mit getValue(key) hat man sowohl key als auch value
	public String getKeyFromIndex(int index) {
		return new ArrayList<String>(values.keySet()).get(index);
	}
	
	public int getSize() {
		return values.size();
	}
	
	@Override
	public String toString() {
		return "Quiz [name=" + name + ", values=" + values + "]";
	}
	
}






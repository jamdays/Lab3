package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private ArrayList countryCode = new ArrayList();
    private Map<String, JSONObject> countryCodesToLangCodes = new HashMap<String, JSONObject>();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                countryCode.add(jsonArray.getJSONObject(i).getString("alpha3"));
                countryCodesToLangCodes.put(jsonArray.getJSONObject(i).getString("alpha3"), jsonArray.getJSONObject(i));
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> tempList = new ArrayList<String>(countryCodesToLangCodes.get(country).keySet());
        tempList.remove(0);
        tempList.remove(1);
        tempList.remove(2);
        return tempList;
    }

    @Override
    public List<String> getCountries() {
        List<String> tempList = new ArrayList(countryCode);
        return tempList;
    }

    @Override
    public String translate(String country, String language) {
        return countryCodesToLangCodes.get(country).getString(language);
    }
}

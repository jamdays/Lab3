package org.translation;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator(null);

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        String quit = "quit";
        CountryCodeConverter converter = new CountryCodeConverter();
        LanguageCodeConverter langConverter = new LanguageCodeConverter();
        while (true) {
            String country = promptForCountry(translator);
            if (quit.equals(country)) {
                break;
            }
            String language = promptForLanguage(translator, converter.fromCountry(country));
            if (language.equals(quit)) {
                break;
            }
            String langCode = langConverter.fromLanguage(language);
            String countryCode = converter.fromCountry(country);
            System.out.println(country + " in " + language + " is " + translator.translate(countryCode, langCode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        CountryCodeConverter converter = new CountryCodeConverter();
        List<String> countries = translator.getCountries();
        for (int i = 0; i < countries.size(); i++) {
            countries.set(i, converter.fromCountryCode(countries.get(i)));
        }
        Collections.sort(countries);
        System.out.println(countries);

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        LanguageCodeConverter converter = new LanguageCodeConverter();

        List<String> langs = translator.getCountryLanguages(country);
        for (int i = 0; i < langs.size(); i++) {
            langs.set(i, converter.fromLanguageCode(langs.get(i)));
        }
        Collections.sort(langs);
        System.out.println(langs);

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}

package spellchecker;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JFileChooser;

public class spellcheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File fileName = getInputFileNameFromUser();
        Set<String> setOfAllWords = new HashSet<>();

    /* try with resources automatically close stream after work */
        assert fileName != null;
        try (Stream<String> stream = Files.lines(fileName.toPath())) {

      /* Lambdas and stream api allow us to read all file with additionally modifications 'on the fly' */
            setOfAllWords = stream
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        }

        String word;
        try (Scanner scanner = new Scanner(System.in)) {
      /* This block guarantees reading the line for the first time  */
            do {
                System.out.println("please, type the word for check, or 'exit' for close the program: ");
                word = scanner.next().toLowerCase();

                printSuggestionsByWord(setOfAllWords, word);

            }
            while (!word.equalsIgnoreCase("exit"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void printSuggestionsByWord(Set<String> setOfAllWords, String word) {
        Set<String> suggestions = new TreeSet<>();

        for (int i = 0; i < word.length(); i++) {

            String substring1 = null;
            String substring2 = null;
            String suggest = null;

            substring1 = word.substring(0, i);
            substring2 = word.substring(i + 1);

            suggest = substring1 + substring2;
            if (setOfAllWords.contains(suggest) && !(suggestions.contains(suggest))) {
                suggestions.add(suggest);
            }

            for (char ch = 'a'; ch <= 'z'; ch++) {
                suggest = substring1 + ch + substring2;
                if (setOfAllWords.contains(suggest) && !(suggestions.contains(suggest))) {
                    suggestions.add(suggest);
                }
            }

            substring1 = word.substring(0, i);
            substring2 = word.substring(i);

            for (char ch = 'a'; ch <= 'z'; ch++) {
                suggest = substring1 + ch + substring2;
                if (setOfAllWords.contains(suggest) && !(suggestions.contains(suggest))) {
                    suggestions.add(suggest);
                }
            }

            char ch = ' ';
            suggest = substring1 + ch + substring2;
            if (setOfAllWords.contains(substring1) && setOfAllWords.contains(substring2)) {
                suggestions.add(suggest);
            }
        }
        if (suggestions.size() == 0) {
            System.out.println("no suggestions");
        } else {
            for (String s : suggestions) {
                System.out.printf("%s ", s);
            }
            System.out.println();
        }
    }

    private static File getInputFileNameFromUser() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION) {
            return new File(
                    "C:\\Users\\amedh\\eclipse-workspace\\UNIT 5\\src\\spellchecker\\words.txt");
        } else {
            return fileDialog.getSelectedFile();
        }
    }

}
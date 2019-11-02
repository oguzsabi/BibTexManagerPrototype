import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jbibtex.BibTeXEntry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class JBibTex {
    public static Collection readBibTexLibrary() {
        try {
            // A file with the exact location of the bib file is created. The location is stored in bibFilePath
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open BibTex Library");
            File file = fileChooser.showOpenDialog(new Stage());

            // A reader created to read the contents of the bib file.
            BufferedReader reader = new BufferedReader(
                    // Just passing the file into the reader makes the reader have all the contents of the file
                    new FileReader(file)
            );

            // A BibTex Database is created here. org.jbibtex part can be omitted
            // This database is just a variable that stores all the values of the file
            org.jbibtex.BibTeXDatabase bibTeXDatabase;

            // BibTex Parser created here.
            org.jbibtex.BibTeXParser bibTeXParser = new org.jbibtex.BibTeXParser();
            //Database initialized to the parsed version of the reader/file by using the bibTexParser created above.
            bibTeXDatabase = bibTeXParser.parse(reader);

            // Each BibTex Entry can be mapped to their key with the code below.
            // Map<Key, BibTeXEntry> myMap = bibTeXDatabase.getEntries();

            // A BibTex Entry collection is created and it includes all the entries within that specific file
            Collection<BibTeXEntry> entries = bibTeXDatabase.getEntries().values();

            // This for loop loops through each entry in the bib file
//            for (BibTeXEntry entry: entries) {
//
//                // @@@ IMPORTANT PART @@@
//                // Every field of each entry is mapped as a key, value pair
//                Map<Key, Value> allFields = entry.getFields();
//                // Type of the entry is printed before each entry
//                System.out.println("Entry Type: " + entry.getType());
//                // For each field in an entry we loop through them and get them as key, value pairs and print them.
//                // author (key) = J. R. R. Tolkien (value)
//                allFields.forEach((key, value) -> System.out.println("\t" + key + " = " + value.toUserString()));
//
//                System.out.println();
//            }
            reader.close();
            return entries;

        } catch (org.jbibtex.ParseException e) {
            System.err.println("The BibTex file format is not correct. Please check your file.");
        } catch (IOException e) {
            System.err.println("There is an error related to the bib file. Please check the location of the bib file");
        }

        return null;
    }
}

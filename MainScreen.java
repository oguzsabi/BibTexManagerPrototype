import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jbibtex.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainScreen {
    @FXML private Button createButton;
    @FXML private MenuItem addNewEntryMenuItem;
    @FXML private MenuItem openLibraryMenuItem;
    @FXML private MenuItem createLibraryMenuItem;
    @FXML private TableView<Map> tableView;
    @FXML private TableColumn<Map, Integer> numberColumn;
    @FXML private TableColumn<Map, String> entryTypeColumn;
    @FXML private TableColumn<Map, String> authorEditorColumn;
    @FXML private TableColumn<Map, String> titleColumn;
    @FXML private TableColumn<Map, Object> yearColumn;
    @FXML private TableColumn<Map, String> journalBookTitleColumn;
    @FXML private TableColumn<Map, String> bibTexKeyColumn;

    public void addNewEntryMenuItemAction() {

    }

    public void openLibraryMenuItemAction() {
        Collection<BibTeXEntry> entries = JBibTex.readBibTexLibrary();

        ObservableList<Map> entries1 = FXCollections.observableArrayList();

        Key numberKey = new Key("#");

        numberColumn.setCellValueFactory(new MapValueFactory<>(numberKey));
        titleColumn.setCellValueFactory(new MapValueFactory<>(BibTeXEntry.KEY_TITLE));
        entryTypeColumn.setCellValueFactory(new MapValueFactory<>(BibTeXEntry.KEY_TYPE));
        authorEditorColumn.setCellValueFactory(new MapValueFactory<>(BibTeXEntry.KEY_AUTHOR));
//        authorEditorColumn.setCellValueFactory(new MapValueFactory<>(BibTeXEntry.KEY_EDITOR));
        yearColumn.setCellValueFactory(new MapValueFactory<>(BibTeXEntry.KEY_YEAR));
        journalBookTitleColumn.setCellValueFactory(new MapValueFactory<>(BibTeXEntry.KEY_JOURNAL));
        bibTexKeyColumn.setCellValueFactory(new MapValueFactory<>(BibTeXEntry.KEY_KEY));

        int rowNumber = 1;

        assert entries != null;
        for (BibTeXEntry entry: entries) {
            Map<Key, Object> myMap = new HashMap<Key, Object>();

//            entries1.add(entry);



            // @@@ IMPORTANT PART @@@
            // Every field of each entry is mapped as a key, value pair
            Map<Key, Value> allFields = entry.getFields();
//            System.out.println(allFields);
//            entries1.add(allFields);
            // Type of the entry is printed before each entry
//            System.out.println("Entry Type: " + entry.getType());
            // For each field in an entry we loop through them and get them as key, value pairs and print them.
            // author (key) = J. R. R. Tolkien (value)
//            System.out.println("---------------> " + allFields.get(BibTeXEntry.KEY_AUTHOR).toUserString());
//            System.out.println("\t" + key + " = " + value.toUserString())
            myMap.put(numberKey, rowNumber);
            myMap.put(BibTeXEntry.KEY_TYPE, entry.getType().toString());
            allFields.forEach((key, value) -> addEntryFieldsIntoMap(key, value, myMap));
            myMap.put(BibTeXEntry.KEY_KEY, entry.getKey().toString());



//            System.out.println(myMap);
            entries1.add(myMap);
//            tableView.setItems(entries1);

            System.out.println();

            rowNumber++;
        }

//        entries1.add(myMap);

//        System.out.println(tableView.getColumns());

        tableView.setItems(entries1);
//        System.out.println(entries1);
    }

    public void addEntryFieldsIntoMap(Key key, Value value, Map map) {
        if (!key.getValue().equals("year")) {
            map.put(key, value.toUserString());
        } else {
            try {
                map.put(key, Integer.parseInt(value.toUserString()));
            } catch (NumberFormatException e) {
                map.put(key, value.toUserString());
            }
        }
    }
}

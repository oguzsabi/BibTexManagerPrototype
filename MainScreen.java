import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.jbibtex.*;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainScreen extends Thread {
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
    private boolean aRowIsSelected = false;
    private int currentRowIndex = -1;

    public void addNewEntryMenuItemAction() {

    }

    public void openLibraryMenuItemAction() {
        titleColumn.setCellFactory(TooltippedTableCell.forTableColumn());
        authorEditorColumn.setCellFactory(TooltippedTableCell.forTableColumn());
        journalBookTitleColumn.setCellFactory(TooltippedTableCell.forTableColumn());

        Collection<BibTeXEntry> entries = JBibTex.readBibTexLibrary();

        if (entries != null) {
            ObservableList<Map> entriesForColumns = FXCollections.observableArrayList();

            Key numberKey = new Key("No");

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
                Map<Key, Object> myMap = new HashMap<>();
                myMap.put(numberKey, rowNumber);
                myMap.put(BibTeXEntry.KEY_TYPE, entry.getType().toString());

                // @@@ IMPORTANT PART @@@
                // Every field of each entry is mapped as a key, value pair
                Map<Key, Value> allFields = entry.getFields();
                allFields.forEach((key, value) -> addEntryFieldsIntoMap(key, value, myMap));

                myMap.put(BibTeXEntry.KEY_KEY, entry.getKey().toString());
                entriesForColumns.add(myMap);

                rowNumber++;
            }

            tableView.setItems(entriesForColumns);
        }
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

    public void rowSelected() {
        Map<Key, Value> currentRow;

        if (!aRowIsSelected) {
            currentRow = tableView.getSelectionModel().getSelectedItem();
            currentRowIndex = tableView.getSelectionModel().getFocusedIndex();
            aRowIsSelected = true;
        } else {
            if (tableView.getSelectionModel().isSelected(currentRowIndex)) {
                tableView.getSelectionModel().clearSelection();
                aRowIsSelected = false;
            } else {
                currentRow = tableView.getSelectionModel().getSelectedItem();
                currentRowIndex = tableView.getSelectionModel().getFocusedIndex();
            }
        }
    }
}

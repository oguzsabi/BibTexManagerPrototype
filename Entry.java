public class Entry {
    private Integer number = 0;
    private String entryType = "";
    private String authorEditor = "";
    private String title = "";
    private String year = "";
    private String journalBookTitle = "";
    private String bibTexKey = "";

    public Entry() {
    }

    public Entry(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getAuthorEditor() {
        return authorEditor;
    }

    public void setAuthorEditor(String authorEditor) {
        this.authorEditor = authorEditor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getJournalBookTitle() {
        return journalBookTitle;
    }

    public void setJournalBookTitle(String journalBookTitle) {
        this.journalBookTitle = journalBookTitle;
    }

    public String getBibTexKey() {
        return bibTexKey;
    }

    public void setBibTexKey(String bibTexKey) {
        this.bibTexKey = bibTexKey;
    }
}

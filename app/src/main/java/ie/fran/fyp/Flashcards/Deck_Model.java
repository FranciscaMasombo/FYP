package ie.fran.fyp.Flashcards;

public class Deck_Model {


    String decktitle;
    String category;
    String keydoes;

    public Deck_Model() {
    }

    public Deck_Model(String decktitle) {
        this.decktitle = decktitle;
        this.category = category;
        this.keydoes = keydoes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDecktitle() {
        return decktitle;
    }

    public void setDecktitle(String decktitle) {
        this.decktitle = decktitle;
    }

    public String getKeydoes() {
        return keydoes;
    }

    public void setKeydoes(String keydoes) {
        this.keydoes = keydoes;
    }

}

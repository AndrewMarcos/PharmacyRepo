package romance.pharmacy.andrew_marcos.pharmacyrepo.Data;

/**
 * Created by Andrew Samir on 6/7/2016.
 */
public class data_news {

    String pic_1;
    String pic_2;
    String text;


    public data_news(String pic_1, String pic_2, String text) {
        this.pic_1 = pic_1;
        this.pic_2 = pic_2;
        this.text = text;
    }

    public String getPic_1() {
        return pic_1;
    }

    public String getPic_2() {
        return pic_2;
    }

    public String getText() {
        return text;
    }
}

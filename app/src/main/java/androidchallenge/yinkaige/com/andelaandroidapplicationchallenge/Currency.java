package androidchallenge.yinkaige.com.andelaandroidapplicationchallenge;

/**
 * Created by Yinka Ige on 25/10/2017.
 */

public class Currency {
    private int id;
    private String name;
    private String abv;
    private int drawableImg;

    Currency(int id, String name, String abv) {
        this.id = id;
        this.name = name;
        this.abv = abv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public int getDrawableImg() {
        return drawableImg;
    }

    public void setDrawableImg(int drawableImg) {
        this.drawableImg = drawableImg;
    }
}

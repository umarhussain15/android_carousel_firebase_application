package carousel.teamo.com.carouselapplication;

/**
 * Created by Umar on 08-Apr-17.
 */

public class RecyclerHelperPOJO {

    String id;

    ScorePOJO scorePOJO;

    public RecyclerHelperPOJO(String id, ScorePOJO scorePOJO) {
        this.id = id;
        this.scorePOJO = scorePOJO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ScorePOJO getScorePOJO() {
        return scorePOJO;
    }

    public void setScorePOJO(ScorePOJO scorePOJO) {
        this.scorePOJO = scorePOJO;
    }
}

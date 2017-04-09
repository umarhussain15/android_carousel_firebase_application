package carousel.teamo.com.carouselapplication;

/**
 * Created by Umar on 08-Apr-17.
 */

public class ScorePOJO {

    public String name;

    public int score;

    public ScorePOJO() {
    }

    public ScorePOJO(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

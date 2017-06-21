package prototype.vn.autoparser.BackendModels;

/**
 * Created by narku on 2017-06-21.
 */

public class Classified {
    int id;
    int parse_url_id;
    int classified_id;
    int user_id;

    String url;
    String name;

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    String img;

}

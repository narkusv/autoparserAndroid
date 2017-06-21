package prototype.vn.autoparser.BackendModels;

/**
 * Created by narku on 2017-06-21.
 */

public class ParseUrls {
    public int id;
    public String url;

    public pivot pivot;

    public class pivot {
        public int user_id;
        public int parse_url_id;
        public String name;
    }
}

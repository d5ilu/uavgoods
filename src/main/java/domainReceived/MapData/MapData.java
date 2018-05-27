package domainReceived.MapData;

public class MapData {
    String token;
    String notice;
    int time;
    Map_All map;

    public MapData(String token, String notice, int time, Map_All map) {
        this.token = token;
        this.notice = notice;
        this.time = time;
        this.map = map;
    }

    public String getToken() {
        return token;
    }

    public String getNotice() {
        return notice;
    }

    public int getTime() {
        return time;
    }

    public Map_All getMap() {
        return map;
    }
}


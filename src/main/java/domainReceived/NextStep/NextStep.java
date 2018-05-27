package domainReceived.NextStep;

import java.util.List;

public class NextStep {

    String token;
    String notice;
    int match_status;
    int time;
    List<UAV_we_item> UAV_we;
    int we_value;
    List<UAV_enemy_item> UAV_enemy;
    int enemy_value;
    List<goods_item> goods;

    public NextStep(String token, String notice, int match_status, int time, List<UAV_we_item> UAV_we, int we_value, List<UAV_enemy_item> UAV_enemy, int enemy_value, List<goods_item> goods) {
        this.token = token;
        this.notice = notice;
        this.match_status = match_status;
        this.time = time;
        this.UAV_we = UAV_we;
        this.we_value = we_value;
        this.UAV_enemy = UAV_enemy;
        this.enemy_value = enemy_value;
        this.goods = goods;
    }

    public String getToken() {
        return token;
    }

    public String getNotice() {
        return notice;
    }

    public int getMatch_status() {
        return match_status;
    }

    public int getTime() {
        return time;
    }

    public List<UAV_we_item> getUAV_we() {
        return UAV_we;
    }

    public int getWe_value() {
        return we_value;
    }

    public List<UAV_enemy_item> getUAV_enemy() {
        return UAV_enemy;
    }

    public int getEnemy_value() {
        return enemy_value;
    }

    public List<goods_item> getGoods() {
        return goods;
    }
}


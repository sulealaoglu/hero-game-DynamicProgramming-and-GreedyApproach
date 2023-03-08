public class Hero {
    private String hero_name,type;
    private int gold,attack_point;
private double profit;
private boolean available;
    public Hero(String hero_name, String type, int gold, int attack_point) {
        this.hero_name = hero_name;
        this.type = type;
        this.gold = gold;
        this.attack_point = attack_point;
        profit=(double)gold/(double)attack_point;
        available=true;
    }
    public void display() {
        System.out.println("Name:" + hero_name + "---Type:" + type + "---Attack Points:" + attack_point + "---Gold:" + gold);

    }

    public String getHero_name() {
        return hero_name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setHero_name(String hero_name) {
        this.hero_name = hero_name;
    }

    public String getType() {
        return type;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getAttack_point() {
        return attack_point;
    }

    public void setAttack_point(int attack_point) {
        this.attack_point = attack_point;
    }
}

import java.util.ArrayList;
import java.util.Locale;

public class HeroSet {
    public ArrayList<Hero> heroSet = new ArrayList<Hero>();

    public HeroSet() {
        this.heroSet = heroSet;
    }

    public void addHeroSet(HeroSet hs) {
        int i = 0;
        if (hs == null) {
            return;
        }
        while (hs.heroSet.size() > i) {
            Hero h = hs.heroSet.get(i);
            heroSet.add(h);
            i++;
        }
    }

    public void display() {
        int sum = 0;
        for (int j = 0; j < heroSet.size(); j++) {
            Hero h = heroSet.get(j);
            sum += h.getGold();
            System.out.println("Name:" + h.getHero_name() + "---Type:" + h.getType() + "---Attack Points:" + h.getAttack_point() + "---Gold:" + h.getGold());
        }
        System.out.println("Total Cost: " + sum);
    }
    public ArrayList<Hero> transfer() {
        ArrayList<Hero>army=new ArrayList<>();
        int sum = 0;
        for (int j = 0; j < heroSet.size(); j++) {
            Hero h = heroSet.get(j);
            sum += h.getAttack_point();
            army.add(h);
        }
        Test.result_DP=sum;
        return army;
    }

    public boolean contains(String type) {
        for (int i = 0; i < heroSet.size(); i++) {
            Hero h = heroSet.get(i);
            if (h.getType().toLowerCase(Locale.ROOT).equals(type.toLowerCase(Locale.ROOT)))
                return true;
        }
        return false;
    }

    public void removeRepeat(int i) {
        heroSet.remove(i);
    }

    public int find(String type) {
        for (int i = 0; i < heroSet.size(); i++) {
            Hero h = heroSet.get(i);
            if (h.getType().toLowerCase(Locale.ROOT).equals(type.toLowerCase(Locale.ROOT))) {
                return i;
            }
        }
        return -1;
    }
}

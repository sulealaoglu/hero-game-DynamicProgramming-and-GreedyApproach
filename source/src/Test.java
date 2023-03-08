import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static ArrayList<Hero> heroes = new ArrayList<>();
    public static int counter = 0;
    static int MAX_ALLOWED_LEVEL = 9;
    static int GOLD_AMOUNT = 1200;
    static int NUMBER_OF_PIECES = 10;
    public static ArrayList<Hero> sorted_list_by_gold = new ArrayList<>();
    public static ArrayList<Hero> sorted_list_by_profit = new ArrayList<>();
    public static ArrayList<Hero> army_dp = new ArrayList<>();
    static int result_DP;
    public static void main(String[] args) {
        takeInput();
        String csvFile = "input_1.csv";
        read(csvFile);
        long start, end;
        start = System.currentTimeMillis();
        dynamicProgrammingApproach();
        end = System.currentTimeMillis();
        long dp_time = end - start;
        System.out.println("=====================TRIAL #1=====================");
        System.out.println("Computer's Greedy Approach Results");
        start = System.currentTimeMillis();
        int ga_result = greedyApproach();
        end = System.currentTimeMillis();
        long ga_time = end - start;
        System.out.println("Total Attack Points: " + ga_result);
        System.out.println("GA TIME: "+ga_time+"ms");
        System.out.println("-------------------------------------");
        System.out.println("User's Dynamic Programming Results");
        displayArmy(army_dp);
        System.out.println("Total Attack Points : " + result_DP);
        System.out.println("DP TIME: "+dp_time+"ms");
        System.out.println("=====================TRIAL #2=====================");
        System.out.println("Computer's Random Approach Results");
        start = System.currentTimeMillis();
        int ra_result = randomApproach();
        end = System.currentTimeMillis();
        long ra_time = end - start;
        System.out.println("Total Attack Points: " + ra_result);
        System.out.println("RA TIME: "+ra_time+"ms");
        System.out.println("-------------------------------------");
        System.out.println("User's Dynamic Programming Results");
        displayArmy(army_dp);
        System.out.println("Total Attack Points: " + result_DP);
        System.out.println("DP TIME: "+dp_time+"ms");
    }

    public static void read(String csvFile) {
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            boolean flag = true;
            int num = MAX_ALLOWED_LEVEL * NUMBER_OF_PIECES;
            String old_hero = "", new_hero = "";
            for (int i = 0; i < MAX_ALLOWED_LEVEL; i++) {
                for (int j = 0; j < NUMBER_OF_PIECES; j++) {
                    if (flag)
                        line = br.readLine();
                    if (line != null && flag) {
                        tempArr = line.split(",");
                        Hero hero = new Hero(tempArr[0], tempArr[1], Integer.parseInt(tempArr[2]), Integer.parseInt(tempArr[3]));
                        heroes.add(hero);
                        counter++;
                        old_hero = tempArr[1].toLowerCase(Locale.ROOT);
                    }
                    flag = true;
                }
                line = br.readLine();
                Hero hero = null;
                if (line != null) {
                    tempArr = line.split(",");
                    new_hero = tempArr[1].toLowerCase(Locale.ROOT);
                    hero = new Hero(tempArr[0], tempArr[1], Integer.parseInt(tempArr[2]), Integer.parseInt(tempArr[3]));
                    flag = false;
                }

                while (old_hero.equals(new_hero) && line != null) {
                    tempArr = line.split(",");
                    new_hero = tempArr[1].toLowerCase(Locale.ROOT);
                    hero = new Hero(tempArr[0], tempArr[1], Integer.parseInt(tempArr[2]), Integer.parseInt(tempArr[3]));
                    if (old_hero.equals(new_hero))
                        line = br.readLine();
                }
                if (hero != null && counter < num) {
                    heroes.add(hero);
                    counter++;
                }
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void takeInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Max Allowed Level");
        MAX_ALLOWED_LEVEL = sc.nextInt();
        System.out.println("Enter Gold Amount");
        GOLD_AMOUNT = sc.nextInt();
        System.out.println("Enter Number of Pieces");
        NUMBER_OF_PIECES = sc.nextInt();
    }

    static int dynamicProgrammingApproach() {
        int i, j;
        int n = heroes.size();
        int[][] K = new int[n + 1][GOLD_AMOUNT + 1];
        HeroSet[][] heroSet = new HeroSet[n + 1][GOLD_AMOUNT + 1];
        if (MAX_ALLOWED_LEVEL == 1) { // we can choose just 1 pawn
            Hero max_hero = heroes.get(0);
            if (max_hero.getGold() > GOLD_AMOUNT) {
                System.out.println("Insufficient gold");
                return 0;
            }
            for (int k = 0; k < n; k++) {
                if (heroes.get(k).getAttack_point() > max_hero.getAttack_point() && heroes.get(k).getGold() < GOLD_AMOUNT)
                    max_hero = heroes.get(k);
            }
            max_hero.display();
            army_dp.add(max_hero);
            return max_hero.getGold();
        }
        for (i = 0; i <= n; i++) {
            for (j = 0; j <= GOLD_AMOUNT; j++) {
                if (i == 0 || j == 0)
                    K[i][j] = 0;
                else if (heroes.get(i - 1).getGold() <= j)
                    maxHero(i, j, K, heroSet);
                else {
                    K[i][j] = K[i - 1][j];
                    heroSet[i][j] = heroSet[i - 1][j];
                }
            }
        }
        if (heroSet[n][GOLD_AMOUNT] == null)
            System.out.println("Insufficient gold");
        else
            army_dp = heroSet[n][GOLD_AMOUNT].transfer();
        return K[n][GOLD_AMOUNT];
    }

    public static void maxHero(int i, int j, int[][] K, HeroSet[][] H) {
        if (heroes.get(i - 1).getAttack_point() + K[i - 1][j - heroes.get(i - 1).getGold()] < K[i - 1][j]) { //the values in the top row are valid
            K[i][j] = K[i - 1][j];
            H[i][j] = H[i - 1][j];
        } else { //new value
            int repeat = -1;
            boolean flag = false;
            HeroSet set = H[i - 1][j - heroes.get(i - 1).getGold()]; //old heroes
            if (set == null)
                add(K, H, i, j, set);
            if (set != null) {
                for (int k = 0; k < set.heroSet.size(); k++)
                    if (set.contains(heroes.get(i - 1).getType())) { //check if exist the same type
                        flag = true;
                        repeat = set.find(heroes.get(i - 1).getType());
                        break;
                    }
                if (!flag){
                    add(K, H, i, j, set);}
                else {
                    int removed = K[i - 1][j - heroes.get(i - 1).getGold()] - set.heroSet.get(repeat).getAttack_point();
                    Hero same_type=set.heroSet.get(repeat);
                    Hero newest=heroes.get(i - 1);
                    if (newest.getAttack_point()>same_type.getAttack_point() ) { //the newest bigger than older
                        K[i][j] = newest.getAttack_point() + removed; //newest+set which is removed from repeat
                        HeroSet hs = new HeroSet();
                        set.removeRepeat(repeat);
                        hs.addHeroSet(set);
                        hs.heroSet.add(newest);
                        H[i][j] = hs;
                    } else {
                        K[i][j] = K[i - 1][j];
                        H[i][j] = H[i - 1][j];
                    }
                }
            }
        }
    }

    public static void add(int[][] K, HeroSet[][] H, int i, int j, HeroSet set) {
        K[i][j] = heroes.get(i - 1).getAttack_point() + K[i - 1][j - heroes.get(i - 1).getGold()];
        HeroSet hs = new HeroSet();
        hs.heroSet.add(heroes.get(i - 1));
        hs.addHeroSet(set);
        H[i][j] = hs;
    }

    public static Hero findCheapest() {
        sorted_list_by_gold = (ArrayList<Hero>) heroes.clone();
        Comparator<Hero> compare = Comparator.comparing(Hero::getGold);
        List<Hero> sorted = sorted_list_by_gold.stream().sorted(compare).toList();
        return sorted.get(0);
    }

    public static int greedyApproach() {
        heroes = sortbyProfit();
        ArrayList<Hero> army = new ArrayList<>();
        int remaining_gold = GOLD_AMOUNT;
        int i = 0;
        StringBuilder types = new StringBuilder();
        int sum = 0;
        Hero cheapest = findCheapest();
        int attack_points = 0;
        while (remaining_gold >= cheapest.getGold()) {
            while (types.toString().contains(heroes.get(i).getType().toLowerCase(Locale.ROOT))) {
                if (i < heroes.size() - 1)
                    i++;
                else {
                    displayArmy(army);
                    return attack_points;
                }

            }
            Hero hero = heroes.get(i);
            army.add(hero);
            types.append(hero.getType().toLowerCase(Locale.ROOT));
            remaining_gold -= hero.getGold();
            attack_points += hero.getAttack_point();
            i++;
        }
        displayArmy(army);
        return attack_points;
    }

    static int randomApproach() {
        Random rnd = new Random();
        ArrayList<Hero> army = new ArrayList<>();
        int remaining_gold = GOLD_AMOUNT;
        Hero cheapest = sorted_list_by_gold.get(sorted_list_by_gold.size() - 1);
        StringBuilder types = new StringBuilder();
        int i;
        int choosen_level = 0;
        int attack_points = 0;
        while (remaining_gold >= cheapest.getGold() && choosen_level < MAX_ALLOWED_LEVEL) {
            i = rnd.nextInt(sorted_list_by_gold.size());
            while (types.toString().contains(sorted_list_by_gold.get(i).getType().toLowerCase(Locale.ROOT)))
                i = rnd.nextInt(sorted_list_by_gold.size());
            Hero hero = sorted_list_by_gold.get(i);
            if (remaining_gold >= hero.getGold()) {
                army.add(hero);
                sorted_list_by_gold.remove(i);
                if (hero == cheapest)
                    cheapest = sorted_list_by_gold.get(sorted_list_by_gold.size() - 1);
                types.append(hero.getType().toLowerCase(Locale.ROOT));
                remaining_gold -= hero.getGold();
                choosen_level++;
                attack_points += hero.getAttack_point();
            }
        }
        displayArmy(army);
        return attack_points;
    }

    public static void displayArmy(ArrayList<Hero> army) {
        int sum = 0;
        for (int j = 0; j < army.size(); j++) {
            Hero h = army.get(j);
            sum += h.getGold();
            System.out.println("Name:" + h.getHero_name() + "---Type:" + h.getType() + "---Attack Points:" + h.getAttack_point() + "---Gold:" + h.getGold());
        }
        System.out.println("Total Cost: " + sum);
    }

    static ArrayList<Hero> sortbyProfit() {
        sorted_list_by_profit = (ArrayList<Hero>) heroes.clone();
        Comparator<Hero> compare = Comparator.comparing(Hero::getProfit);
        List<Hero> sorted = sorted_list_by_profit.stream().sorted(compare).collect(Collectors.toList());
        return (ArrayList<Hero>) sorted;
    }

    static void sortbyGold(ArrayList<Hero> arr) {
        int n = arr.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr.get(j).getGold() < arr.get(j + 1).getGold()) {
                    Collections.swap(arr, j + 1, j);
                }
        System.out.println();
    }

}



package hcg.hcggraph;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeMap;

public class CustomDoses {
    private static TreeMap<Integer, Integer> hourAndDose = new TreeMap<>();

    public static TreeMap<Integer, Integer> getHourAndDose() {
        return hourAndDose;
    }

    public static void addHourAndDose(int dose, int hour) {
        hourAndDose.put(hour, dose);
    }

    public static void clear() {
        hourAndDose = new TreeMap<>();
    }

    public static String getLegendTitle() {
        NavigableSet<Integer> key = hourAndDose.navigableKeySet();
        Iterator<Integer> i = key.iterator();
        String title = "";
        while (i.hasNext()) {
            int hour = i.next();
            title += "\nsubsequent dose: " + hourAndDose.get(hour) + " at " + hour;
        }
        return title;
    }
//    private static ArrayList<Integer> dose = new ArrayList<>();
//    private static ArrayList<Integer> hour = new ArrayList<>();
//
//    public static ArrayList<Integer> getDose() {
//        return dose;
//    }
//
//    public static ArrayList<Integer> getHour() {
//        return hour;
//    }
//
//    public static void addDoseAndHour(int dose, int time) {
//        CustomDoses.dose.add(dose);
//        CustomDoses.hour.add(time);
//    }
//
//    public static void clearDose() {
//        dose = new ArrayList<>();
//    }
//
//    public static void clearHour() {
//        hour = new ArrayList<>();
//    }
}

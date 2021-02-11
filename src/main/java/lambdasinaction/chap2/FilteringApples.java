package lambdasinaction.chap2;

import java.util.*;

public class FilteringApples {

    public static void main(String... args) {

        List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

        // [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        List<Apple> greenApples = filterApplesByColor(inventory, "green");
        System.out.println(greenApples);

        // [Apple{color='red', weight=120}]
        List<Apple> redApples = filterApplesByColor(inventory, "red");
        System.out.println(redApples);

        // [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        List<Apple> greenApples2 = filter(inventory, new AppleColorPredicate());
        System.out.println(greenApples2);

        // [Apple{color='green', weight=155}]
        List<Apple> heavyApples = filter(inventory, new AppleWeightPredicate());
        System.out.println(heavyApples);

        // []
        List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());
        System.out.println(redAndHeavyApples);

        // Using Anonymous class
        // [Apple{color='red', weight=120}]
        List<Apple> redApples2 = filter(inventory, new ApplePredicate() {
            public boolean test(Apple a) {
                return a.getColor().equals("red");
            }
        });
        System.out.println(redApples2);

        //
        List<String> printList = prettyPrintApples(inventory, new SimpleFormatter());
        for (String s : printList) {
            System.out.println(s);
        }

        List<String> printList2 = prettyPrintApples(inventory, new AppleFancyFormatter());
        for (String s : printList2) {
            System.out.println(s);
        }
    }

    // First Attempt
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterRedApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("red".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    // Second Attempt
    public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    // Third Attempt
    public static List<Apple> filterApples(List<Apple> inventory, boolean flag, String color, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ((color.equals(apple.getColor())) || (!flag && apple.getWeight() > weight)) {
                result.add(apple);
            }
        }
        return result;
    }

    // Fourth Attempt
    public static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    // PrettyPrintApples
    public static List<String> prettyPrintApples(List<Apple> inventory, AppleFormatter applePrint) {
        List<String> applePrintList = new ArrayList<>();
        for (Apple apple : inventory) {
            applePrintList.add(applePrint.format(apple));
        }
        return applePrintList;
    }


    // Apple class
    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }

    // Apple Predicates
    interface ApplePredicate {
        public boolean test(Apple a);
    }

    static class AppleWeightPredicate implements ApplePredicate {
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    static class AppleColorPredicate implements ApplePredicate {
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }
    }

    static class AppleRedAndHeavyPredicate implements ApplePredicate {
        public boolean test(Apple apple) {
            return "red".equals(apple.getColor())
                    && apple.getWeight() > 150;
        }
    }

    // Apple Formatter
    interface AppleFormatter {
        public String format(Apple apple);
    }

    static class AppleFancyFormatter implements AppleFormatter {

        @Override
        public String format(Apple apple) {
            String characteristic = apple.getWeight() > 150 ? "Heavy" : "Light";
            return String.format("A %s %s apple", characteristic, apple.getColor());
        }
    }

    static class SimpleFormatter implements AppleFormatter {

        @Override
        public String format(Apple apple) {
            return String.format("An apple of %s g weight", apple.getWeight());
        }
    }


    // Generic Predicate
    interface Predicate<T> {
        public boolean test(T a);
    }

    // Generic Filter Method
    public static <T> List<T> filter(List<T> inventory, Predicate p) {
        List<T> result = new ArrayList<>();
        for (T t : inventory) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }
}
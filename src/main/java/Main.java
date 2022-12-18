import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    static List<Integer> countBusInCity = new ArrayList<>();
    static List<Integer> balanceCity = new ArrayList<>();
    static int countBusAll = 0;

    public static void main(String[] args) throws IOException {
        List<Route> routeList = readInputFile();
        List<Event> eventList = formEventList(routeList);
        Collections.sort(eventList, Comparator.comparing(Event::getTime).thenComparing(Event::getType).thenComparing(Event::getCity));
        boolean flag = true;
        for (Integer integer : balanceCity) {
            if (integer != 0) {
                flag = false;
            }
        }
        if (flag) {
            for (Event event : eventList) {
                if (event.getType() == 1) {
                    lowerCountBusInCity(event.getCity());
                } else {
                    addCountBusInCity(event.getCity());
                }
            }
            System.out.println(countBusAll);
        } else {
            System.out.println("-1");
        }
    }

    private static void lowerCountBusInCity(int city) {
        if (countBusInCity.get(city) > 0) {
            countBusInCity.set(city, countBusInCity.get(city) - 1);
        } else {
            countBusAll++;
        }
    }

    private static void addCountBusInCity(int city) {
        countBusInCity.set(city, countBusInCity.get(city) + 1);
    }

    static List<Route> readInputFile() throws IOException {
        List<Route> routeList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String[] str = reader.readLine().split(" ");
        int countCity = Integer.parseInt(str[0]);
        countBusInCity.add(0);
        balanceCity.add(0);
        for (int i = 0; i < countCity; i++) {
            countBusInCity.add(0);
            balanceCity.add(0);
        }
        int countRout = Integer.parseInt(str[1]);
        for (int i = 0; i < countRout; i++) {
            str = reader.readLine().split(" ");
            routeList.add(new Route(Integer.parseInt(str[0]), transformTimeStringFormatToCountMinute(str[1]),
                    Integer.parseInt(str[2]), transformTimeStringFormatToCountMinute(str[3])));
        }
        return routeList;
    }

    static List<Event> formEventList(List<Route> routeList) {
        List<Event> eventList = new ArrayList<>();
        for (Route route : routeList) {
            if (route.getTimeStart() > route.getTimeEnd()) {
                countBusAll++;
            }
            eventList.add(new Event(1, route.getCityStart(), route.getTimeStart()));
            eventList.add(new Event(-1, route.getCityEnd(), route.getTimeEnd()));
            balanceCity.set(route.getCityStart(), balanceCity.get(route.getCityStart()) - 1);
            balanceCity.set(route.getCityEnd(), balanceCity.get(route.getCityEnd()) + 1);

        }
        return eventList;
    }

    static int transformTimeStringFormatToCountMinute(String str) {
        String[] time = str.split(":");
        return (Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1]);
    }

}

class Route {
    private int cityStart, cityEnd, timeStart, timeEnd;

    public Route(int cityStart, int timeStart, int cityEnd, int timeEnd) {
        this.cityStart = cityStart;
        this.cityEnd = cityEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getCityStart() {
        return cityStart;
    }

    public int getCityEnd() {
        return cityEnd;
    }

    public int getTimeStart() {
        return timeStart;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    @Override
    public String toString() {
        return "Route{" +
                "cityStart=" + cityStart +
                ", cityEnd=" + cityEnd +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                '}';
    }
}

class Event {
    private int type, city, time;

    public Event(int type, int city, int time) {
        this.type = type;
        this.city = city;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public int getCity() {
        return city;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", city=" + city +
                ", time=" + time +
                '}';
    }
}
package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        final List<UserMealWithExceed> filteredMealWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredMealWithExceeded.forEach(meal -> System.out.println(meal));
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesByDate = mealList.stream().collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                Collectors.summingInt(meal -> meal.getCalories())));
        return mealList.stream().filter((meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)))
                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesByDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceedForLoop(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> dailyCalories = new HashMap<>();
        for(UserMeal meal:mealList){
            LocalDate mealDate=meal.getDateTime().toLocalDate();
            dailyCalories.put(mealDate, dailyCalories.getOrDefault(mealDate,0)+meal.getCalories());
        }
        List<UserMealWithExceed> mealExceeded=new ArrayList<>();
        for (UserMeal meal:mealList){
            LocalDateTime dateTime=meal.getDateTime();
            if(TimeUtil.isBetween(dateTime.toLocalTime(),startTime,endTime)){
                mealExceeded.add(new UserMealWithExceed(dateTime,meal.getDescription(),meal.getCalories(),
                        dailyCalories.get(dateTime.toLocalDate())>caloriesPerDay));
            }
        }
        return mealExceeded;
    }
}
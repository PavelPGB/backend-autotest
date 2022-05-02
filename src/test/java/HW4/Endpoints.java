package HW4;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Endpoints {
    USER_CONNECT("/users/connect"),
    MEALPLANNER_USERNAME_SHOPPING_LIST_ITEMS("/mealplanner/{username}/shopping-list/items"),
    MEALPLANNER_USERNAME_SHOPPING_LIST_ITEMS_ID("/mealplanner/{username}/shopping-list/items/{id}"),
    MEALPLANNER_USERNAME_SHOPPING_LIST("/mealplanner/{username}/shopping-list"),
    RECIPES_COMPLEXSEARCH("/recipes/complexSearch"),
    RECIPES_CUISINE("/recipes/cuisine");

    @Getter
    final String endpoint;
}
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import reader.Reader;

public class dayTwentyOne {

  public static void main(String[] args) {
    Set<String> allIngredients = new HashSet<>();
    Set<String> allAllergens = new HashSet<>();

    Reader.readInput("/resources/inputd21.txt", dayTwentyOne.class)
        .forEach(line -> {
          String[] split = line.replace(")", "").replace("(", "")
              .split("contains");
          allIngredients.addAll(Arrays.stream(split[0].split(" ")).map(String::strip).collect(
              Collectors.toList()));
          allAllergens.addAll(Arrays.stream(split[1].split(", ")).map(String::strip).collect(
              Collectors.toList()));
        });

    Map<String, Set<String>> mapIngredientNotAllergens = new HashMap<>();
    allIngredients.forEach(in -> mapIngredientNotAllergens.put(in, new HashSet<>()));
    Map<String, Integer> mapIngredientsCount = new HashMap<>();

    Reader.readInput("/resources/inputd21.txt", dayTwentyOne.class)
        .forEach(line -> {
          String[] split = line.replace(")", "").replace("(", "")
              .split("contains");
          Arrays.stream(split[0].split(" ")).forEach(ingredient -> {
            ingredient = ingredient.strip();
            if (mapIngredientsCount.containsKey(ingredient)) {
              mapIngredientsCount
                  .put(ingredient, mapIngredientsCount.get(ingredient) + 1);
            } else {
              mapIngredientsCount.put(ingredient, 1);
            }
          });
          allIngredients.stream()
              .filter(i -> Arrays.stream(split[0].split(" ")).noneMatch(i::equals))
              .forEach(i -> Arrays.stream(split[1].split(",")).forEach(a -> {
                a = a.strip();
                mapIngredientNotAllergens.get(i).add(a);
              }));
        });
    List<String> listIngredientsWithoutAllergens = new ArrayList<>();
    mapIngredientNotAllergens.forEach((i, lsA) -> {
      if (lsA.equals(allAllergens)) {
        listIngredientsWithoutAllergens.add(i);
      }
    });

    //part 1
    listIngredientsWithoutAllergens.stream().map(mapIngredientsCount::get).reduce(Integer::sum)
        .ifPresent(System.out::println);
    listIngredientsWithoutAllergens.forEach(mapIngredientNotAllergens::remove);
    listIngredientsWithoutAllergens.forEach(allIngredients::remove);
    Map<String, String> mapIngredientSpecificAllergen = new HashMap<>();
    while (!mapIngredientNotAllergens.isEmpty()) {
      mapIngredientNotAllergens.forEach((i, as) -> {
        List<String> lastAllergens = allAllergens.stream().filter(a -> !as.contains(a))
            .collect(Collectors.toList());
        if (lastAllergens.size() == 1) {
          String allergen = lastAllergens.get(0);
          allAllergens.remove(allergen);
          mapIngredientSpecificAllergen.put(allergen, i);
        }
      });
      mapIngredientSpecificAllergen.forEach((as, i) -> mapIngredientNotAllergens.remove(i));
    }

    //part 2
    mapIngredientSpecificAllergen.forEach((a, i) -> allAllergens.add(a));
    allAllergens.stream().sorted(String::compareTo).map(mapIngredientSpecificAllergen::get)
        .reduce((a, b) -> a.strip() + "," + b.strip()).ifPresent(System.out::println);
  }
}

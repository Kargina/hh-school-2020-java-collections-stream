package tasks;

import common.Area;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 implements Task {

  //Получился сложночитаемый код, но он работает
  //Со стримами не получилось это задание
  private Set<String> getPersonDescriptions(Collection<Person> persons,
                                            Map<Integer, Set<Integer>> personAreaIds,
                                            Collection<Area> areas) {

    HashSet<String> set = new HashSet<>();

    for (Person p: persons) {
      for (Map.Entry<Integer, Set<Integer>> entry : personAreaIds.entrySet()) {
        if (entry.getKey().equals(p.getId())) {

          for (Integer areaId: entry.getValue()) {
            for (Area area: areas) {
              if (areaId.equals(area.getId())) {
                set.add(p.getFirstName() + " - " + area.getName());
              }
            }
          }
        }
      }
    }

    return set;
  }

  @Override
  public boolean check() {
    List<Person> persons = List.of(
        new Person(1, "Oleg", Instant.now()),
        new Person(2, "Vasya", Instant.now())
    );
    Map<Integer, Set<Integer>> personAreaIds = Map.of(1, Set.of(1, 2), 2, Set.of(2, 3));
    List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
    return getPersonDescriptions(persons, personAreaIds, areas)
        .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo"));
  }
}

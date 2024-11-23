package org.theoriok.adventofcode.y2015;

import com.google.common.collect.Collections2;
import org.theoriok.adventofcode.Day;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.function.Predicate.not;

public class Day21 implements Day<Integer, Integer> {

    private static final int PLAYER_HIT_POINTS = 100;
    private static final List<Weapon> WEAPONS = List.of(
        new Weapon("Dagger", 8, 4),
        new Weapon("Shortsword", 10, 5),
        new Weapon("Warhammer", 25, 6),
        new Weapon("Longsword", 40, 7),
        new Weapon("Greataxe", 74, 8)
    );
    private static final List<Armour> ARMOURS = List.of(
        new Armour("Leather", 13, 1),
        new Armour("Chainmail", 31, 2),
        new Armour("Splintmail", 53, 3),
        new Armour("Bandedmail", 75, 4),
        new Armour("Platemail", 102, 5)
    );
    private static final List<Ring> RINGS = List.of(
        new Ring("Damage +1", 25, 1, 0),
        new Ring("Damage +2", 50, 2, 0),
        new Ring("Damage +3", 100, 3, 0),
        new Ring("Defense +1", 20, 0, 1),
        new Ring("Defense +2", 40, 0, 2),
        new Ring("Defense +3", 80, 0, 3)
    );

    private final Boss boss;
    private final List<Equipment> equipments;

    public Day21(List<String> input) {
        boss = Boss.from(input);
        equipments = getEquipments();
    }

    private List<Equipment> getEquipments() {
        List<Equipment> equipments = new ArrayList<>();
        for (List<Ring> rings : ringsCombinations()) {
            for (Weapon weapon : WEAPONS) {
                equipments.add(new Equipment(weapon, Optional.empty(), rings));
                for (Armour armour : ARMOURS) {
                    equipments.add(new Equipment(weapon, Optional.of(armour), rings));
                }
            }
        }
        return equipments;
    }

    private Set<List<Ring>> ringsCombinations() {
        Collection<List<Ring>> twoRings = Collections2.permutations(RINGS).stream()
            .map(list -> list.subList(0, 2))
            .collect(Collectors.toSet());
        Collection<List<Ring>> oneRing = RINGS.stream()
            .map(List::of)
            .collect(Collectors.toSet());
        Collection<List<Ring>> noRing = Set.of(emptyList());
        Set<List<Ring>> rings = Stream.concat(twoRings.stream(), Stream.concat(oneRing.stream(), noRing.stream()))
            .collect(Collectors.toSet());
        return rings;
    }

    @Override
    public Integer firstMethod() {
        return equipments.stream()
            .filter(equipment -> equipment.fight(boss))
            .mapToInt(Equipment::totalCost)
            .min().orElse(Integer.MAX_VALUE);
    }

    @Override
    public Integer secondMethod() {
        return equipments.stream()
            .filter(not(equipment -> equipment.fight(boss)))
            .mapToInt(Equipment::totalCost)
            .max().orElse(Integer.MIN_VALUE);
    }

    record Boss(int hitPoints, int damage, int armour) {
        static Boss from(List<String> input) {
            int hitPoints = Integer.parseInt(input.getFirst().split(": ")[1]);
            int damage = Integer.parseInt(input.get(1).split(": ")[1]);
            int armour = Integer.parseInt(input.getLast().split(": ")[1]);
            return new Boss(hitPoints, damage, armour);
        }
    }

    record Weapon(String name, int cost, int damage) {
    }

    record Armour(String name, int cost, int armour) {
    }

    record Ring(String name, int cost, int damage, int armour) {
    }

    record Equipment(Weapon weapon, Optional<Armour> armour, List<Ring> rings) {
        int totalCost() {
            return weapon.cost + armour.map(Armour::cost).orElse(0) + rings.stream().mapToInt(Ring::cost).sum();
        }

        boolean fight(Boss boss) {
            var playerHealth = PLAYER_HIT_POINTS;
            var bossHealth = boss.hitPoints;

            while (true) {
                bossHealth -= Math.max(playerDamage() - boss.armour, 1);
                if (bossHealth <= 0) {
                    return true;
                }
                playerHealth -= Math.max(boss.damage - playerArmour(), 1);
                if (playerHealth <= 0) {
                    return false;
                }
            }
        }

        private int playerArmour() {
            return armour.map(Armour::armour).orElse(0) + rings.stream().mapToInt(Ring::armour).sum();
        }

        private int playerDamage() {
            return weapon.damage + rings.stream().mapToInt(Ring::damage).sum();
        }
    }
}

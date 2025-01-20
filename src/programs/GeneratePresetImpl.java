package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeneratePresetImpl implements GeneratePreset {
    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        if (unitList == null || unitList.isEmpty()) {
            unitList = new ArrayList<>();
            unitList.add(new Unit("Stub", "Swordsman", 100, 15, 50, "melee", new HashMap<>(), new HashMap<>(), 0, 0));
        }
        unitList.sort(Comparator.comparingDouble(u -> -((double) u.getBaseAttack() / u.getCost() + (double) u.getHealth() / u.getCost())));
        List<Unit> selected = new ArrayList<>();
        int pts = 0;
        for (Unit t : unitList) {
            int m = Math.min(11, maxPoints / t.getCost());
            for (int i = 0; i < m && pts + t.getCost() <= maxPoints; i++) {
                Map<String, Double> a = t.getAttackBonuses() == null ? new HashMap<>() : new HashMap<>(t.getAttackBonuses());
                Map<String, Double> d = t.getDefenceBonuses() == null ? new HashMap<>() : new HashMap<>(t.getDefenceBonuses());
                Unit u = new Unit(t.getName(), t.getUnitType(), t.getHealth(), t.getBaseAttack(), t.getCost(), t.getAttackType(), a, d, t.getxCoordinate(), t.getyCoordinate());
                u.setName(t.getUnitType() + " " + i);
                u.setAlive(true);
                u.setProgram(t.getProgram());
                selected.add(u);
                pts += t.getCost();
            }
        }
        Random r = new Random();
        for (Unit u : selected) {
            boolean ok = false;
            while (!ok) {
                int xx = r.nextInt(3);
                int yy = r.nextInt(21);
                boolean clash = false;
                for (Unit o : selected) {
                    if (o != u && o.getxCoordinate() == xx && o.getyCoordinate() == yy) {
                        clash = true;
                        break;
                    }
                }
                if (!clash) {
                    u.setxCoordinate(xx);
                    u.setyCoordinate(yy);
                    ok = true;
                }
            }
        }
        Army army = new Army();
        army.setUnits(selected);
        army.setPoints(pts);
        return army;
    }
}

package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.ArrayList;
import java.util.List;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {
    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        List<Unit> r = new ArrayList<>();
        for (List<Unit> row : unitsByRow) {
            if (!row.isEmpty()) {
                if (isLeftArmyTarget) r.add(row.get(0)); else r.add(row.get(row.size() - 1));
            }
        }
        return r;
    }
}

package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog;
    public void setPrintBattleLog(PrintBattleLog p) {
        this.printBattleLog = p;
    }
    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        while (true) {
            List<Unit> alive = getAllAlive(playerArmy, computerArmy);
            if (alive.isEmpty()) break;
            alive.sort(Comparator.comparingInt(Unit::getBaseAttack).reversed());
            for (Unit unit : alive) {
                if (!unit.isAlive()) continue;
                Unit target = unit.getProgram().attack();
                if (target != null) {
                    target.setHealth(target.getHealth() - unit.getBaseAttack());
                    if (target.getHealth() <= 0) {
                        target.setHealth(0);
                        target.setAlive(false);
                    }
                    if (printBattleLog != null) {
                        printBattleLog.printBattleLog(unit, target);
                    }
                }
            }
            boolean p = playerArmy.getUnits().stream().anyMatch(Unit::isAlive);
            boolean c = computerArmy.getUnits().stream().anyMatch(Unit::isAlive);
            if (!p || !c) break;
        }
    }
    private List<Unit> getAllAlive(Army p, Army c) {
        List<Unit> r = new ArrayList<>();
        for (Unit u : p.getUnits()) if (u.isAlive()) r.add(u);
        for (Unit u : c.getUnits()) if (u.isAlive()) r.add(u);
        return r;
    }
}

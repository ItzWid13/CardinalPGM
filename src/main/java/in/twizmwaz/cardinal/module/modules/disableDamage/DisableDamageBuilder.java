package in.twizmwaz.cardinal.module.modules.disableDamage;

import in.parapengu.commons.utils.StringUtils;
import in.twizmwaz.cardinal.match.Match;
import in.twizmwaz.cardinal.module.Module;
import in.twizmwaz.cardinal.module.ModuleBuilder;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisableDamageBuilder implements ModuleBuilder {

    @Override
    public List<Module> load(Match match) {
        List<Module> results = new ArrayList<>();
        Set<DamageCause> damageTypes = new HashSet<>(128);
        boolean ally = true, self = true, enemy = true, other = true;
        for (Element itemRemove : match.getDocument().getRootElement().getChildren("disabledamage")) {
            for (Element item : itemRemove.getChildren("damage")) {
                damageTypes.add(DamageCause.valueOf(item.getText().toUpperCase().replaceAll(" ", "_")));
                if (DamageCause.valueOf(StringUtils.technicalName(item.getText())) == DamageCause.BLOCK_EXPLOSION) {
                    try {
                        ally = !item.getAttribute("ally").getValue().equalsIgnoreCase("false");
                    } catch (NullPointerException ex) {
                        //Attribute does not exist
                    }
                    try {
                        self = !item.getAttribute("self").getValue().equalsIgnoreCase("false");
                    } catch (NullPointerException ex) {
                        //Attribute does not exist
                    }
                    try {
                        enemy = !item.getAttribute("enemy").getValue().equalsIgnoreCase("false");
                    } catch (NullPointerException ex) {
                        //Attribute does not exist
                    }
                    try {
                        other = !item.getAttribute("other").getValue().equalsIgnoreCase("false");
                    } catch (NullPointerException ex) {
                        //Attribute does not exist
                    }
                }
            }
        }
        DisableDamage disableDamage = new DisableDamage(damageTypes);
        disableDamage.setBlockExplosionAlly(ally);
        disableDamage.setBlockExplosionSelf(self);
        disableDamage.setBlockExplosionEnemy(enemy);
        disableDamage.setBlockExplosionOther(other);
        results.add(disableDamage);
        return results;
    }

}

package in.twizmwaz.cardinal.module.modules.toolRepair;

import in.parapengu.commons.utils.StringUtils;
import in.twizmwaz.cardinal.match.Match;
import in.twizmwaz.cardinal.module.ModuleBuilder;
import in.twizmwaz.cardinal.module.ModuleCollection;
import org.bukkit.Material;
import org.jdom2.Element;

import java.util.HashSet;
import java.util.Set;

public class ToolRepairBuilder implements ModuleBuilder {

    @Override
    public ModuleCollection load(Match match) {
        ModuleCollection results = new ModuleCollection();
        Set<Material> materials = new HashSet<>(128);
        for (Element itemRemove : match.getDocument().getRootElement().getChildren("toolrepair")) {
            for (Element item : itemRemove.getChildren("tool")) {
                materials.add(StringUtils.convertStringToMaterial(item.getText()));
            }
        }
        results.add(new ToolRepair(materials));
        return results;
    }

}

package in.twizmwaz.cardinal.regions;

import in.twizmwaz.cardinal.GameHandler;
import in.twizmwaz.cardinal.regions.parsers.*;
import in.twizmwaz.cardinal.regions.parsers.modifiers.CombinationParser;
import in.twizmwaz.cardinal.regions.parsers.modifiers.MirrorParser;
import in.twizmwaz.cardinal.regions.parsers.modifiers.TranslateParser;
import in.twizmwaz.cardinal.regions.type.*;
import in.twizmwaz.cardinal.regions.type.combinations.ComplementRegion;
import in.twizmwaz.cardinal.regions.type.combinations.IntersectRegion;
import in.twizmwaz.cardinal.regions.type.combinations.NegativeRegion;
import in.twizmwaz.cardinal.regions.type.combinations.UnionRegion;
import in.twizmwaz.cardinal.regions.type.modifications.MirroredRegion;
import in.twizmwaz.cardinal.regions.type.modifications.TranslatedRegion;
import org.bukkit.block.Block;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.List;

/**
 * Created by kevin on 10/26/14.
 */
public abstract class Region {

    public static Region getRegion(Element element, Document document) {
        switch (element.getName().toLowerCase()) {
            case "block":
                return new BlockRegion(new BlockParser(element));
            case "point":
                return new PointRegion(new PointParser(element));
            case "circle":
                return new CircleRegion(new CircleParser(element));
            case "cuboid":
                return new CuboidRegion(new CuboidParser(element));
            case "cylinder":
                return new CylinderRegion(new CylinderParser(element));
            case "empty":
                return new EmptyRegion(new EmptyParser(element));
            case "rectangle":
                return new RectangleRegion(new RectangleParser(element));
            case "sphere":
                return new SphereRegion(new SphereParser(element));
            case "complement":
                return new ComplementRegion(new CombinationParser(element, document));
            case "intersect":
                return new IntersectRegion(new CombinationParser(element, document));
            case "negative":
                return new NegativeRegion(new CombinationParser(element, document));
            case "union":
            case "regions":
                return new UnionRegion((new CombinationParser(element, document)));
            case "translate":
                return new TranslatedRegion(new TranslateParser(element));
            case "mirror":
                return new MirroredRegion(new MirrorParser(element));
            case "region":
                if (element.getAttributeValue("name") != null) {
                    for (Element regionElement : document.getRootElement().getChildren("regions")) {
                        for (Element givenRegion : regionElement.getChildren()) {
                            try {
                                if (givenRegion.getAttributeValue("name").equalsIgnoreCase(element.getAttributeValue("name"))) {
                                    return getRegion(givenRegion);
                                }
                            } catch (NullPointerException e) {
                            }
                            for (Element givenChild : givenRegion.getChildren()) {
                                if (givenChild.getAttributeValue("name").equalsIgnoreCase(element.getAttributeValue("name"))) {
                                    return getRegion(givenChild);
                                }
                            }
                        }
                    }
                } else {
                    return getRegion(element.getChildren().get(0));
                }
            default:
                if (element.getAttributeValue("region") != null) {
                    for (Element regionElement : document.getRootElement().getChildren("regions")) {
                        for (Element givenRegion : regionElement.getChildren()) {
                            if (givenRegion.getName().equalsIgnoreCase("apply"))
                                continue;
                            if (givenRegion.getAttributeValue("region").equalsIgnoreCase(element.getAttributeValue("region"))) {
                                return getRegion(givenRegion);
                            }
                        }
                    }
                }
                return null;
        }
    }

    public static Region getRegion(Element element) {
        return getRegion(element, GameHandler.getGameHandler().getMatch().getDocument());
    }

    public abstract boolean contains(BlockRegion region);

    public abstract boolean contains(PointRegion point);

    public abstract PointRegion getRandomPoint();

    public abstract BlockRegion getCenterBlock();

    public abstract List<Block> getBlocks();

}

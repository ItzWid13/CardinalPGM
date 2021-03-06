package in.twizmwaz.cardinal.regions.type;

import in.parapengu.commons.utils.OtherUtil;
import in.twizmwaz.cardinal.regions.Region;
import in.twizmwaz.cardinal.regions.parsers.CylinderParser;
import in.twizmwaz.cardinal.util.NumUtils;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class CylinderRegion extends Region {

    private double baseX;
    private double baseY;
    private double baseZ;
    private double radius;
    private double height;

    public CylinderRegion(double baseX, double baseY, double baseZ, double radius, double height) {
        super();
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseZ = baseZ;
        this.radius = radius;
        this.height = height;
    }

    public CylinderRegion(CylinderParser parser) {
        this.baseX = parser.getBaseX();
        this.baseY = parser.getBaseY();
        this.baseZ = parser.getBaseZ();
    }

    public double getBaseX() {
        return baseX;
    }

    public double getBaseY() {
        return baseY;
    }

    public double getBaseZ() {
        return baseZ;
    }

    public double getRadius() {
        return radius;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean contains(BlockRegion region) {
        return (Math.hypot(Math.abs(region.getX() - getBaseX()), Math.abs(region.getZ() - getBaseZ())) <= getRadius()) && NumUtils.checkInterval(region.getY(), getBaseY(), getBaseY() + getHeight());

    }

    @Override
    public boolean contains(PointRegion region) {
        return (Math.hypot(Math.abs(region.getX() - getBaseX()), Math.abs(region.getZ() - getBaseZ())) <= getRadius()) && NumUtils.checkInterval(region.getY(), getBaseY(), getBaseY() + getHeight());

    }

    @Override
    public PointRegion getRandomPoint() {
        double a = OtherUtil.getRandom(0, radius);
        double b = OtherUtil.getRandom(0, 360);
        double c = OtherUtil.getRandom(0, height);

        return new PointRegion(this.baseX + a * Math.cos(b), this.baseY + c, this.baseZ + a * Math.sin(b));
    }

    @Override
    public BlockRegion getCenterBlock() {
        return (new BlockRegion(this.baseX, this.baseY + .5 * height, this.baseZ));
    }

    @Override
    public List<Block> getBlocks() {
        List<Block> results = new ArrayList<>();
        CuboidRegion bound = new CuboidRegion(baseX - radius, baseY, baseZ - radius, baseX + radius, baseY + height, baseZ + radius);
        for (Block block : bound.getBlocks()) {
            if (contains(new BlockRegion(block.getX(), block.getY(), block.getZ()))) results.add(block);
        }
        return results;
    }
}

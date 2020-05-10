package com.blamejared.contenttweaker.blocks.wrappers;

import com.blamejared.contenttweaker.blocks.wrappers.MCAxisAlignedBB;
import com.blamejared.contenttweaker.blocks.wrappers.MCVec3d;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCBlockPos;
import com.blamejared.crafttweaker.impl.util.MCDirectionAxis;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.MCAxisAlignedBB")
@Document("mods/contenttweaker/block/MCAxisAlignedBB")
@ZenWrapper(wrappedClass = "net.minecraft.util.math.AxisAlignedBB", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCAxisAlignedBB {
    private final AxisAlignedBB internal;

    public MCAxisAlignedBB(AxisAlignedBB internal){
        this.internal = internal;
    }

    public AxisAlignedBB getInternal() {
        return this.internal;
    }

    @ZenCodeType.Method
    public double getXSize() {
        return internal.getXSize();
    }


    @ZenCodeType.Method
    public boolean contains(MCVec3d vec) {
        return internal.contains((vec).getInternal());
    }


    @ZenCodeType.Method
    public MCAxisAlignedBB offset(MCBlockPos pos) {
        return new MCAxisAlignedBB(internal.offset((pos).getInternal()));
    }


    @ZenCodeType.Method
    public MCAxisAlignedBB grow(double value) {
        return new MCAxisAlignedBB(internal.grow(value));
    }


    @ZenCodeType.Method
    public MCAxisAlignedBB grow(double x, double y, double z) {
        return new MCAxisAlignedBB(internal.grow(x, y, z));
    }


    @ZenCodeType.Method
    public double getMax(MCDirectionAxis axis) {
        return internal.getMax((axis).getInternal());
    }


    /**
     * Returns the average length of the edges of the bounding box.
     */
    @ZenCodeType.Method
    public double getAverageEdgeLength() {
        return internal.getAverageEdgeLength();
    }


    @ZenCodeType.Method
    public boolean intersects(MCVec3d min, MCVec3d max) {
        return internal.intersects((min).getInternal(), (max).getInternal());
    }


    @ZenCodeType.Method
    public MCVec3d getCenter() {
        return new MCVec3d(internal.getCenter());
    }


    @ZenCodeType.Method
    public String toString() {
        return (internal.toString());
    }


    @ZenCodeType.Method
    public boolean contains(double x, double y, double z) {
        return internal.contains(x, y, z);
    }


    @ZenCodeType.Method
    public boolean equals(Object p_equals_1_) {
        return internal.equals((p_equals_1_));
    }


    @ZenCodeType.Method
    public MCAxisAlignedBB expand(MCVec3d p_216361_1_) {
        return new MCAxisAlignedBB(internal.expand((p_216361_1_).getInternal()));
    }


    @ZenCodeType.Method
    public MCAxisAlignedBB expand(double x, double y, double z) {
        return new MCAxisAlignedBB(internal.expand(x, y, z));
    }


    @ZenCodeType.Method
    public boolean hasNaN() {
        return internal.hasNaN();
    }


    @ZenCodeType.Method
    public double getYSize() {
        return internal.getYSize();
    }


    @ZenCodeType.Method
    public boolean intersects(MCAxisAlignedBB other) {
        return internal.intersects((other).getInternal());
    }


    @ZenCodeType.Method
    public int hashCode() {
        return internal.hashCode();
    }


    @ZenCodeType.Method
    public MCAxisAlignedBB intersect(MCAxisAlignedBB other) {
        return new MCAxisAlignedBB(internal.intersect((other).getInternal()));
    }


    @ZenCodeType.Method
    public double getZSize() {
        return internal.getZSize();
    }


    /**
     * Creates a new {@link AxisAlignedBB} that has been contracted by the given amount, with positive changes decreasing
     * max values and negative changes increasing min values.
     * <br/>
     * If the amount to contract by is larger than the length of a side, then the side will wrap (still creating a valid
     * AABB - see last sample).
     * <h3>Samples:</h3>
     * <table>
     * <tr><th>Input</th><th>Result</th></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 4, 4, 4).contract(2, 2, 2)</code></pre></td><td><pre><samp>box[0.0,
     * 0.0, 0.0 -> 2.0, 2.0, 2.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(0, 0, 0, 4, 4, 4).contract(-2, -2, -
     * 2)</code></pre></td><td><pre><samp>box[2.0, 2.0, 2.0 -> 4.0, 4.0, 4.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(5, 5, 5, 7, 7, 7).contract(0, 1, -1)</code></pre></td><td><pre><samp>box[5.0,
     * 5.0, 6.0 -> 7.0, 6.0, 7.0]</samp></pre></td></tr>
     * <tr><td><pre><code>new AxisAlignedBB(-2, -2, -2, 2, 2, 2).contract(4, -4, 0)</code></pre></td><td><pre><samp>box[-
     * 8.0, 2.0, -2.0 -> -2.0, 8.0, 2.0]</samp></pre></td></tr>
     * </table>
     * <h3>See Also:</h3>
     * <ul>
     * <li>{@link #expand(double, double, double)} - like this, except for expanding.</li>
     * <li>{@link #grow(double, double, double)} and {@link #grow(double)} - expands in all directions.</li>
     * <li>{@link #shrink(double)} - contracts in all directions (like {@link #grow(double)})</li>
     * </ul>
     * @return A new modified bounding box.
     */
    @ZenCodeType.Method
    public MCAxisAlignedBB contract(double x, double y, double z) {
        return new MCAxisAlignedBB(internal.contract(x, y, z));
    }


    @ZenCodeType.Method
    public double getMin(MCDirectionAxis axis) {
        return internal.getMin((axis).getInternal());
    }


    @ZenCodeType.Method
    public MCAxisAlignedBB offset(MCVec3d vec) {
        return new MCAxisAlignedBB(internal.offset((vec).getInternal()));
    }


    /**
     * Creates a new {@link AxisAlignedBB} that is expanded by the given value in all directions. Equivalent to {@link
     * #grow(double)} with value set to the negative of the value provided here. Passing a negative value to this method
     * values will grow the AABB.
     * <br/>
     * Side lengths will be decreased by 2 times the value of the parameter, since both min and max are changed.
     * <br/>
     * If contracting and the amount to contract by is larger than the length of a side, then the side will wrap (still
     * creating a valid AABB - see samples on {@link #grow(double, double, double)}).
     * @return A modified AABB.
     */
    @ZenCodeType.Method
    public MCAxisAlignedBB shrink(double value) {
        return new MCAxisAlignedBB(internal.shrink(value));
    }


    @ZenCodeType.Method
    public MCAxisAlignedBB union(MCAxisAlignedBB other) {
        return new MCAxisAlignedBB(internal.union((other).getInternal()));
    }


    @ZenCodeType.Method
    public MCAxisAlignedBB offset(double x, double y, double z) {
        return new MCAxisAlignedBB(internal.offset(x, y, z));
    }


    @ZenCodeType.Method
    public boolean intersects(double x1, double y1, double z1, double x2, double y2, double z2) {
        return internal.intersects(x1, y1, z1, x2, y2, z2);
    }


}

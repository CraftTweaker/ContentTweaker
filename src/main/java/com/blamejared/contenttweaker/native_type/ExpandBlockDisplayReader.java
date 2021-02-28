package com.blamejared.contenttweaker.native_type;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/contenttweaker/API/world/MCBlockDisplayReader")
@NativeTypeRegistration(value = IBlockDisplayReader.class, zenCodeName = "mods.contenttweaker.world.MCBlockDisplayReader")
public class ExpandBlockDisplayReader {
    /**
     * Gets the tile entity data for a tile entity at a given position.
     *
     * @param pos The position of the tile entity.
     * @return The data of the tile entity.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static IData getTileData(IBlockDisplayReader internal, BlockPos pos) {
        CompoundNBT nbt = new CompoundNBT();
        TileEntity te = internal.getTileEntity(pos);
        return te == null ? new MapData() : NBTConverter.convert(te.write(nbt));
    }

    /**
     * Gets the block state at a given position.
     *
     * @param pos The position to look up.
     * @return The block state at the position.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static BlockState getBlockState(IBlockDisplayReader internal, BlockPos pos) {
        return internal.getBlockState(pos);
    }

    /**
     * Gets if can see sky at a given position
     *
     * @param pos The position to look up.
     * @return The block state at the position.
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static boolean canSeeSky(IBlockDisplayReader internal, BlockPos pos) {
        return internal.canSeeSky(pos);
    }

    /**
     * Gets the light level at a given position
     * @param pos The position to look up.
     * @return The light level at the position
     *
     * @docParam pos new BlockPos(0, 1, 2)
     */
    @ZenCodeType.Method
    public static int getLightValue(IBlockDisplayReader internal, BlockPos pos) {
        return internal.getLightValue(pos);
    }
}

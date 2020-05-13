package com.blamejared.contenttweaker.blocks.types.machine;

import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.crafttweaker.impl.util.*;
import mcp.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.network.*;

import javax.annotation.*;
import java.util.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CoTBlockTile extends Block implements IIsCoTBlock {
    
    
    private final CoTBlockItem item;
    private final CoTCapabilityConfigurationManager capabilities;
    
    
    public CoTBlockTile(BuilderMachine builderMachine, MCResourceLocation location) {
        super(builderMachine.getBlockBuilder().getBlockProperties());
        this.setRegistryName(location.getInternal());
        this.item = new CoTBlockItem(this, builderMachine.getBlockBuilder().getItemProperties());
        this.capabilities = builderMachine.getCapabilities();
        MachineBlockRegistry.ALL_BLOCKS.add(this);
    }
    
    public CoTCapabilityConfigurationManager getCapabilityConfiguration() {
        return capabilities;
    }
    
    @Nonnull
    @Override
    public IIsCotItem getItem() {
        return item;
    }
    
    @Nonnull
    @Override
    @SuppressWarnings("DuplicatedCode") //Reason: Will be changed later
    public Collection<WriteableResource> getResourcePackResources() {
        final MCResourceLocation location = getMCResourceLocation();
        final Collection<WriteableResource> out = new HashSet<>();
        out.add(new WriteableResourceImage(ImageType.BLOCK, location));
        out.add(new WriteableResource(ResourceType.ASSETS, FileExtension.JSON, location, "models", "block")
                .withContent("{\n" + "    \"parent\": \"block/cube_all\",\n" + "    \"textures\": {\n" + "        \"all\": \"%s:block/%s\"\n" + "    }\n" + "}\n", location
                        .getNamespace(), location.getPath()));
        
        out.add(new WriteableResource(ResourceType.ASSETS, FileExtension.JSON, location, "blockstates")
                .withContent("{\n" + "    \"variants\": {\n" + "        \"\": {\"model\" : \"%s:block/%s\"}\n" + "    }\n" + "}\n", location
                        .getNamespace(), location.getPath()));
        
        out.addAll(capabilities.getResourcePackResources(getMCResourceLocation()));
        
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.singleton(new WriteableResourceLootTableItem(getMCResourceLocation()));
    }
    
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    
    @Nullable
    @Override
    public CoTTile createTileEntity(BlockState state, IBlockReader world) {
        final CoTCapabilityInstanceManager instanceManager = new CoTCapabilityInstanceManager(this);
        final CoTTile coTTile;
        if(instanceManager.requiresTickableTile()) {
            coTTile = new CoTTileTicking(getMCResourceLocation().getInternal(), instanceManager);
        } else {
            coTTile = new CoTTile(getMCResourceLocation().getInternal(), instanceManager);
        }
        instanceManager.registerTile(coTTile);
        return coTTile;
    }
    
    @Override
    @Deprecated
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()) {
            final TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof CoTTile) {
                ((CoTTile) tileEntity).onBlockBroken(state, worldIn, pos, newState, isMoving);
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        if(!worldIn.isRemote) {
            NetworkHooks.openGui((ServerPlayerEntity) player, state.getContainer(worldIn, pos), pos);
        }
        return ActionResultType.SUCCESS;
    }
    
    @Nullable
    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        final TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof CoTTile) {
            return (CoTTile) tileEntity;
        }
        return null;
    }
    
    public boolean hasTickingCapabilities() {
        return new CoTCapabilityInstanceManager(this).requiresTickableTile();
    }
}

package com.teamacronymcoders.contenttweaker.api;

import java.util.HashMap;
import java.util.Map;

public class Mappings {
    private static Map<String, String> soundEventMappings;
    private static Map<String, String> materialMappings;

    public static Map<String, String> getSoundEventMappings() {
        if (soundEventMappings == null) {
            soundEventMappings = new HashMap<>();
            soundEventMappings.put("field_185858_k", "anvil");
            soundEventMappings.put("field_185854_g", "cloth");
            soundEventMappings.put("field_185853_f", "glass");
            soundEventMappings.put("field_185849_b", "ground");
            soundEventMappings.put("field_185857_j", "ladder");
            soundEventMappings.put("field_185852_e", "metal");
            soundEventMappings.put("field_185850_c", "plant");
            soundEventMappings.put("field_185855_h", "sand");
            soundEventMappings.put("field_185856_i", "snow");
            soundEventMappings.put("field_185851_d", "stone");
            soundEventMappings.put("field_185848_a", "wood");
        }
        return soundEventMappings;
    }

    public static Map<String, String> getMaterialMappings() {
        if (materialMappings == null) {
            materialMappings = new HashMap<>();
            materialMappings.put("field_151579_a", "air");
            materialMappings.put("field_151574_g", "anvil");
            materialMappings.put("field_175972_I", "barrier");
            materialMappings.put("field_151570_A", "cactus");
            materialMappings.put("field_151568_F", "cake");
            materialMappings.put("field_151593_r", "carpet");
            materialMappings.put("field_151594_q", "circuits");
            materialMappings.put("field_151571_B", "clay");
            materialMappings.put("field_151580_n", "cloth");
            materialMappings.put("field_151589_v", "coral");
            materialMappings.put("field_151596_z", "crafted_snow");
            materialMappings.put("field_151566_D", "dragon_egg");
            materialMappings.put("field_151581_o", "fire");
            materialMappings.put("field_151592_s", "glass");
            materialMappings.put("field_151572_C", "gourd");
            materialMappings.put("field_151577_b", "grass");
            materialMappings.put("field_151578_c", "ground");
            materialMappings.put("field_151588_w", "ice");
            materialMappings.put("field_151573_f", "iron");
            materialMappings.put("field_151587_i", "lava");
            materialMappings.put("field_151584_j", "leaves");
            materialMappings.put("field_151598_x", "packed_ice");
            materialMappings.put("field_76233_E", "piston");
            materialMappings.put("field_151585_k", "plants");
            materialMappings.put("field_151567_E", "portal");
            materialMappings.put("field_151591_t", "redstone_light");
            materialMappings.put("field_151576_e", "rock");
            materialMappings.put("field_151595_p", "sand");
            materialMappings.put("field_151597_y", "snow");
            materialMappings.put("field_151583_m", "sponge");
            materialMappings.put("field_189963_J", "structure_void");
            materialMappings.put("field_151590_u", "tnt");
            materialMappings.put("field_151582_l", "vine");
            materialMappings.put("field_151586_h", "water");
            materialMappings.put("field_151569_G", "web");
            materialMappings.put("field_151575_d", "wood");
        }

        return materialMappings;
    }
}

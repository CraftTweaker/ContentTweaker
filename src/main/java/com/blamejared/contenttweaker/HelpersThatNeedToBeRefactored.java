package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.blocks.functions.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.impl.data.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.mojang.datafixers.util.*;

import java.util.*;
import java.util.stream.*;

public class HelpersThatNeedToBeRefactored {
    
    public static MapData getAllValues(MCBlockProperties properties, BlockStateToModelMapping blockStateToModelMapping, MCResourceLocation location) {
        final Collection<MCBlockStateProperty> blockStatePropertyList = properties.getBlockStatePropertiesForBlockStateJson();
        
        final List<Map.Entry<String, List<String>>> allowedValues = new ArrayList<>(blockStatePropertyList
                .stream()
                .collect(Collectors.toMap(MCBlockStateProperty::getName, MCBlockStateProperty::getAllowedValues))
                .entrySet());
        final OptionalInt reduce = allowedValues.stream()
                .mapToInt(e -> e.getValue().size())
                .reduce((left, right) -> left * right);
        
        final Map<String, IData> variants;
        if(!reduce.isPresent()) {
            variants = Collections.singletonMap("", blockStateToModelMapping.mapModel(location, Collections
                    .emptyMap()));
        } else {
            variants = IntStream.range(0, reduce.getAsInt())
                    .mapToObj(i -> getVariant(i, location, blockStateToModelMapping, allowedValues))
                    .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        }
        final Map<String, IData> out = Collections.singletonMap("variants", new MapData(variants));
        return new MapData(out);
    }
    
    private static Pair<String, IData> getVariant(int number, MCResourceLocation location, BlockStateToModelMapping mapping, List<Map.Entry<String, List<String>>> allowedEntries) {
        final Map<String, String> result = new HashMap<>();
        for(Map.Entry<String, List<String>> allowedEntry : allowedEntries) {
            final List<String> value = allowedEntry.getValue();
            result.put(allowedEntry.getKey(), value.get(number % value.size()));
            number = number / value.size();
        }
        final IData iData = mapping.mapModel(location, result);
        
        final StringJoiner stringJoiner = new StringJoiner(",");
        result.forEach((s, s2) -> stringJoiner.add(String.format("%s=%s", s, s2)));
        return new Pair<>(stringJoiner.toString(), iData);
    }
}

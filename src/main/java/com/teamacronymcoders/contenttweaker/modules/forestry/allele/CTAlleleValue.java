package com.teamacronymcoders.contenttweaker.modules.forestry.allele;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import forestry.core.genetics.alleles.IAlleleValue;

public class CTAlleleValue implements ICTObject<IAlleleValue> {
    private final IAlleleValue allele;

    public CTAlleleValue(IAlleleValue allele) {
        this.allele = allele;
    }

    @Override
    public IAlleleValue getInternal() {
        return allele;
    }
}

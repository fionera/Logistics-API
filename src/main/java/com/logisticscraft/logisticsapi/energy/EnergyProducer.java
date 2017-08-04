package com.logisticscraft.logisticsapi.energy;

import javax.annotation.Nonnull;

/**
 * @author JARvis (Пётр) PROgrammer
 */
public interface EnergyProducer extends EnergyStorage {

    @Override
    default long getMaxOutput() {
        return 0;
    }

    @Nonnull
    @Override
    default EnergySharePriority getEnergySharePriority() {
        return EnergySharePriority.ALWAYS;
    }
}

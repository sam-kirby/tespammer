package uk.bobbytables.tespammer.common;

import net.minecraft.tileentity.TileEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TESpammerHandler {
    private static Logger LOGGER = LogManager.getLogger("TES");
    public static void logHandler(TileEntity tileEntity) {
        LOGGER.info("##############################");
        LOGGER.info("Adding tile to world: {}", TileEntity.getKey(tileEntity.getClass()).toString());
        LOGGER.info("Position: {}, Dimension: {}", tileEntity.getPos(), tileEntity.getWorld().provider.getDimension());
        LOGGER.info("##############################");
    }
}

package uk.bobbytables.tespammer.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.Name("TESpammer")
@IFMLLoadingPlugin.MCVersion("1.12.2")
public class TESpammerLoadingPlugin implements IFMLLoadingPlugin {
    static Logger LOGGER = LogManager.getLogger("TESpammer_Core");

    static String ADD_TILE_ENTITIES;
    static String ADD_TILE_ENTITY;
    static String ADD_TILE_ENTITY_DESC;

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"uk.bobbytables.tespammer.core.TESpammerTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        boolean dev = !(boolean) data.get("runtimeDeobfuscationEnabled");
        ADD_TILE_ENTITIES = dev ? "addTileEntities" : "b";
        ADD_TILE_ENTITY = dev ? "addTileEntity" : "a";
        ADD_TILE_ENTITY_DESC = dev ? "(Lnet/minecraft/tileentity/TileEntity;)Z" : "(Lavj;)Z";
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}

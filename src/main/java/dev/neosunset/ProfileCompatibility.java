package dev.neosunset;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public final class ProfileCompatibility {
    private static final Set<String> BUILT_INS =
            Set.of("neosunset", "minecraft", "java", "fabricloader", "quilt_loader", "forge", "neoforge");

    private ProfileCompatibility() {
    }

    public static boolean isVerified() {
        return Detected.VERIFIED;
    }

    static boolean areBuiltIns(List<String> ids) {
        return ids.contains("neosunset") && ids.contains("minecraft")
                && ids.stream().allMatch(BUILT_INS::contains);
    }

    private static final class Detected {
        static final boolean VERIFIED = inspect();
    }

    private static boolean inspect() {
        try {
            if (hasClass("net.minecraftforge.fml.ModList")) {
                return inspectFml("net.minecraftforge");
            }
            if (hasClass("net.neoforged.fml.ModList")) {
                return inspectFml("net.neoforged");
            }
            if (hasClass("net.fabricmc.loader.api.FabricLoader")) {
                Class<?> loader = Class.forName("net.fabricmc.loader.api.FabricLoader");
                Class<?> container = Class.forName("net.fabricmc.loader.api.ModContainer");
                Class<?> metadata = Class.forName("net.fabricmc.loader.api.metadata.ModMetadata");
                return inspectMods(loader, "getInstance", "getAllMods",
                        container.getMethod("getMetadata"), metadata.getMethod("getId"));
            }
        } catch (ReflectiveOperationException | LinkageError | RuntimeException ignored) {
            // A missing or incompatible loader API is an unverified profile.
        }
        return false;
    }

    private static boolean inspectFml(String namespace) throws ReflectiveOperationException {
        Class<?> loader = Class.forName(namespace + ".fml.ModList");
        Class<?> metadata = Class.forName(namespace + ".forgespi.language.IModInfo");
        return inspectMods(loader, "get", "getMods", null, metadata.getMethod("getModId"));
    }

    private static boolean inspectMods(Class<?> loader, String instanceMethod, String modsMethod,
                                       Method metadataMethod, Method readId) throws ReflectiveOperationException {
        Object instance = loader.getMethod(instanceMethod).invoke(null);
        var ids = new java.util.ArrayList<String>();
        for (Object mod : (Iterable<?>) loader.getMethod(modsMethod).invoke(instance)) {
            Object metadata = metadataMethod == null ? mod : metadataMethod.invoke(mod);
            ids.add((String) readId.invoke(metadata));
        }
        return areBuiltIns(ids);
    }

    private static boolean hasClass(String name) {
        try {
            Class.forName(name, false, ProfileCompatibility.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }
}

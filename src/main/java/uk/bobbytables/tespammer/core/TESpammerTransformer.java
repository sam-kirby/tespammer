package uk.bobbytables.tespammer.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static uk.bobbytables.tespammer.core.TESpammerLoadingPlugin.*;

public class TESpammerTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!transformedName.equals("net.minecraft.world.World")) {
            return basicClass;
        }

        LOGGER.info("Transforming: {}", transformedName);

        boolean transformed = false;

        ClassReader classReader = new ClassReader(basicClass);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(ADD_TILE_ENTITIES) && methodNode.desc.equals("(Ljava/util/Collection;)V")) {
                Iterator<AbstractInsnNode> insnNodeIterator = methodNode.instructions.iterator();
                while (insnNodeIterator.hasNext()) {
                    AbstractInsnNode abstractInsnNode = insnNodeIterator.next();
                    if (abstractInsnNode.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
                        if (methodInsnNode.name.equals(ADD_TILE_ENTITY) && methodInsnNode.desc.equals(ADD_TILE_ENTITY_DESC)) {
                            InsnList insnList = new InsnList();
                            insnList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                            insnList.add(new MethodInsnNode(
                                    Opcodes.INVOKESTATIC,
                                    "uk/bobbytables/tespammer/common/TESpammerHandler",
                                    "logHandler",
                                    "(Lnet/minecraft/tileentity/TileEntity;)V",
                                    false
                            ));
                            methodNode.instructions.insert(methodInsnNode, insnList);
                            transformed = true;
                            LOGGER.info("Transformed successfully");
                            break;
                        }
                    }
                }
            }
        }

        if (!transformed) {
            LOGGER.error("Transformation failed");
            return basicClass;
        }

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}

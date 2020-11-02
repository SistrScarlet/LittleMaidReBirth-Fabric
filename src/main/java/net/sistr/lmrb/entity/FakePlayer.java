package net.sistr.lmrb.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;

public class FakePlayer extends ServerPlayerEntity {

    public FakePlayer(ServerWorld world, GameProfile profile) {
        super(world.getServer(), world, profile, new ServerPlayerInteractionManager(world));
    }

}

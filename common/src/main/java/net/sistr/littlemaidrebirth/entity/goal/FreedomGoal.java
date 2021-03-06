package net.sistr.littlemaidrebirth.entity.goal;

import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.sistr.littlemaidrebirth.entity.Tameable;

import java.util.EnumSet;

//雇い主が居ない場合も発動する
public class FreedomGoal<T extends PathAwareEntity & Tameable> extends WanderAroundFarGoal {
    private final T tameable;
    private final double distance;
    private final double distanceSq;
    private BlockPos freedomPos;
    private int reCalcCool;

    public FreedomGoal(T mob, double speedIn, double distance) {
        super(mob, speedIn);
        this.tameable = mob;
        this.distance = distance;
        this.distanceSq = distance * distance;
        setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (tameable.getMovingState() != Tameable.MovingState.FREEDOM) {
            return false;
        }
        return super.canStart();
    }

    @Override
    public void start() {
        super.start();
        freedomPos = this.tameable.getFreedomPos();
    }

    @Override
    public void tick() {
        super.tick();
        if (freedomPos == null) {
            return;
        }
        if (freedomPos.getSquaredDistance(mob.getPos(), true) < distanceSq) {
            return;
        }
        if (0 < --reCalcCool) {
            return;
        }
        reCalcCool = 20;
        //freedomPosを目指して移動
        mob.getNavigation().startMovingTo(freedomPos.getX(), freedomPos.getY(), freedomPos.getZ(), speed);
        Path path = mob.getNavigation().getCurrentPath();
        if (path != null && path.getEnd() != null && path.getEnd().getManhattanDistance(freedomPos) < distance) {
            return;
        }
        mob.getNavigation().stop();
        //移動しても着きそうにない場合はTP
        if (mob.world.isSpaceEmpty(mob.getBoundingBox().offset(mob.getPos().multiply(-1)).offset(freedomPos))) {
            mob.teleport(freedomPos.getX() + 0.5D, freedomPos.getY(), freedomPos.getZ() + 0.5D);
        }

    }

    @Override
    public void stop() {
        super.stop();
        freedomPos = null;
        reCalcCool = 0;
    }
}

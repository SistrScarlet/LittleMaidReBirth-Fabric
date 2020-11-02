package net.sistr.lmrb.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.sistr.lmml.client.renderer.MultiModelRenderer;
import net.sistr.lmml.maidmodel.IModelCaps;
import net.sistr.lmml.maidmodel.ModelMultiBase;
import net.sistr.lmrb.entity.Tameable;
import net.sistr.lmrb.entity.LittleMaidEntity;

@Environment(EnvType.CLIENT)
public class MaidModelRenderer extends MultiModelRenderer<LittleMaidEntity> {

    public MaidModelRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void syncCaps(LittleMaidEntity entity, ModelMultiBase model, float partialTicks) {
        super.syncCaps(entity, model, partialTicks);
        model.setCapsValue(IModelCaps.caps_aimedBow, entity.isAimingBow());
        model.setCapsValue(IModelCaps.caps_isWait, entity.getMovingState().equals(Tameable.WAIT));
        model.setCapsValue(IModelCaps.caps_isContract, entity.hasTameOwner());
    }

}

package com.gdut.dkmfromcg.ojbk_ui.progressing.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;

import com.gdut.dkmfromcg.ojbk_ui.progressing.animation.SpriteAnimatorBuilder;
import com.gdut.dkmfromcg.ojbk_ui.progressing.sprite.RectSprite;


/**
 * Created by ybq.
 */
public class RotatingPlane extends RectSprite {
    @Override
    protected void onBoundsChange(Rect bounds) {
        setDrawBounds(clipSquare(bounds));
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        float fractions[] = new float[]{0f, 0.5f, 1f};
        return new SpriteAnimatorBuilder(this).
                rotateX(fractions, 0, -180, -180).
                rotateY(fractions, 0, 0, -180).
                duration(1200).
                easeInOut(fractions)
                .build();
    }
}

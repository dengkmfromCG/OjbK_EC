package com.gdut.dkmfromcg.ojbk_ui.progressing;


import com.gdut.dkmfromcg.ojbk_ui.progressing.sprite.Sprite;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.ChasingDots;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.Circle;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.CubeGrid;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.DoubleBounce;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.FadingCircle;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.FoldingCube;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.MultiplePulse;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.MultiplePulseRing;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.Pulse;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.PulseRing;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.RotatingCircle;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.RotatingPlane;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.ThreeBounce;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.WanderingCubes;
import com.gdut.dkmfromcg.ojbk_ui.progressing.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}

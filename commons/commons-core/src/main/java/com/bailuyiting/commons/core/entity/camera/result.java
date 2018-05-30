package com.bailuyiting.commons.core.entity.camera;

/**
 * 识别结果
 */
public class result {

    private PlateResult plateResult;

    /**
     *
     *
     */
    public PlateResult getPlateResult() {
        return plateResult;
    }

    public void setPlateResult(PlateResult plateResult) {
        this.plateResult = plateResult;
    }
    /**
     *
     */
    @Override
    public String toString() {
        return "result{" +
                "plateResult=" + plateResult +
                '}';
    }
}

package com.bailuyiting.commons.core.entity.camera;

/**
 *  //车牌识别信息
 */
public class PlateResult {
    private String bright; //预留
    private String carBright;//车身亮度（ 预留）
    private String carColor;//车身颜色（ 预留）
    private String colorType;//车牌颜色值
    private String colorValue;//颜色值（ 预留）
    private String confidence;//可信度
    private String direction;//行驶方向
    private String imagePath;//图片路径（ 预留）
    private String license;//车牌号
    private String timeUsed;//识别耗时
    private String triggerType;//触发类型
    private String type;//车牌类型
    private String platecolor;//车牌颜色
    private String carLogo;//车标
    private String carType;//车型
    private String plateBright;//车牌亮度
    private String recotime;//识别时间
    private String imageFile;//全景图数据 base64 编码
    private String imageFileLen;//全景图数据长度
    private String imageFragmentFile;//车牌小图数据 base64 编码
    private String imageFragmentFileLen; //车牌小图数据长度
    /**
     *
     */
    public String getBright() {
        return bright;
    }

    public void setBright(String bright) {
        this.bright = bright;
    }

    public String getCarBright() {
        return carBright;
    }

    public void setCarBright(String carBright) {
        this.carBright = carBright;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public String getColorValue() {
        return colorValue;
    }

    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(String timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlatecolor() {
        return platecolor;
    }

    public void setPlatecolor(String platecolor) {
        this.platecolor = platecolor;
    }

    public String getCarLogo() {
        return carLogo;
    }

    public void setCarLogo(String carLogo) {
        this.carLogo = carLogo;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getPlateBright() {
        return plateBright;
    }

    public void setPlateBright(String plateBright) {
        this.plateBright = plateBright;
    }

    public String getRecotime() {
        return recotime;
    }

    public void setRecotime(String recotime) {
        this.recotime = recotime;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageFileLen() {
        return imageFileLen;
    }

    public void setImageFileLen(String imageFileLen) {
        this.imageFileLen = imageFileLen;
    }

    public String getImageFragmentFile() {
        return imageFragmentFile;
    }

    public void setImageFragmentFile(String imageFragmentFile) {
        this.imageFragmentFile = imageFragmentFile;
    }

    public String getImageFragmentFileLen() {
        return imageFragmentFileLen;
    }

    public void setImageFragmentFileLen(String imageFragmentFileLen) {
        this.imageFragmentFileLen = imageFragmentFileLen;
    }
    /**
     *
     */
    @Override
    public String toString() {
        return "PlateResult{" +
                "bright='" + bright + '\'' +
                ", carBright='" + carBright + '\'' +
                ", carColor='" + carColor + '\'' +
                ", colorType='" + colorType + '\'' +
                ", colorValue='" + colorValue + '\'' +
                ", confidence='" + confidence + '\'' +
                ", direction='" + direction + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", license='" + license + '\'' +
                ", timeUsed='" + timeUsed + '\'' +
                ", triggerType='" + triggerType + '\'' +
                ", type='" + type + '\'' +
                ", platecolor='" + platecolor + '\'' +
                ", carLogo='" + carLogo + '\'' +
                ", carType='" + carType + '\'' +
                ", plateBright='" + plateBright + '\'' +
                ", recotime='" + recotime + '\'' +
                ", imageFile='" + imageFile + '\'' +
                ", imageFileLen='" + imageFileLen + '\'' +
                ", imageFragmentFile='" + imageFragmentFile + '\'' +
                ", imageFragmentFileLen='" + imageFragmentFileLen + '\'' +
                '}';
    }
}

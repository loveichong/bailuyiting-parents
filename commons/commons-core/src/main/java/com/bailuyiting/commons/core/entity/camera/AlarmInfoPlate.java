package com.bailuyiting.commons.core.entity.camera;

/**
 * 相机识别结果实体类
 */
public class AlarmInfoPlate {
    private String resultType;//0 即时上报的结果 1续传的结果（数字）
    private String channel;//出入口标志
    private String deviceName;//设备名称
    private String ipaddr;//IP 地址
    private String seriaIno;//设备序列号（ROMID
    private String nParkID;//停车场 ID
    private String ParkName;//停车场名称
    private String ParkDoor;//门口名称（ 例如东门）
    private result result;//结果
    /**
     *
     */
    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getSeriaIno() {
        return seriaIno;
    }

    public void setSeriaIno(String seriaIno) {
        this.seriaIno = seriaIno;
    }

    public String getnParkID() {
        return nParkID;
    }

    public void setnParkID(String nParkID) {
        this.nParkID = nParkID;
    }

    public String getParkName() {
        return ParkName;
    }

    public void setParkName(String parkName) {
        ParkName = parkName;
    }

    public String getParkDoor() {
        return ParkDoor;
    }

    public void setParkDoor(String parkDoor) {
        ParkDoor = parkDoor;
    }

    public com.bailuyiting.commons.core.entity.camera.result getResult() {
        return result;
    }

    public void setResult(com.bailuyiting.commons.core.entity.camera.result result) {
        this.result = result;
    }
    /**
     *
     */
    @Override
    public String toString() {
        return "AlarmInfoPlate{" +
                "resultType='" + resultType + '\'' +
                ", channel='" + channel + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", ipaddr='" + ipaddr + '\'' +
                ", seriaIno='" + seriaIno + '\'' +
                ", nParkID='" + nParkID + '\'' +
                ", ParkName='" + ParkName + '\'' +
                ", ParkDoor='" + ParkDoor + '\'' +
                ", result=" + result +
                '}';
    }
}

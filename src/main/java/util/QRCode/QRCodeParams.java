
package util.QRCode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeParams {
    private String txt;                //二维码内容
    private String qrCodeUrl;          //二维码网络路径
    private String filePath;           //二维码生成物理路径
    private String fileName;           //二维码生成图片名称（包含后缀名）
    private String logoPath;           //logo图片
    private Integer width = 300;           //二维码宽度
    private Integer height = 300;          //二维码高度
    private Integer onColor = 0xFF000000;  //前景色
    private Integer offColor = 0xFFFFFFFF; //背景色
    private Integer margin = 1;            //白边大小，取值范围0~4
    private ErrorCorrectionLevel level = ErrorCorrectionLevel.L;  //二维码容错率

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public Integer getOnColor() {
        return onColor;
    }

    public void setOnColor(Integer onColor) {
        this.onColor = onColor;
    }

    public Integer getOffColor() {
        return offColor;
    }

    public void setOffColor(Integer offColor) {
        this.offColor = offColor;
    }

    public ErrorCorrectionLevel getLevel() {
        return level;
    }

    public void setLevel(ErrorCorrectionLevel level) {
        this.level = level;
    }

    /**
     * 返回文件后缀名
     *
     * @return
     */
    public String getSuffixName() {
        String imgName = this.getFileName();
        if (imgName != null && !"".equals(imgName)) {
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            return suffix;
        }
        return "";
    }

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }
}
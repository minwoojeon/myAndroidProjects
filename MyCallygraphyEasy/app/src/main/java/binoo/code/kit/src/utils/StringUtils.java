package binoo.code.kit.src.utils;

/**
 * Created by binoo on 2017-12-02.
 */

public final class StringUtils {

    public static boolean isBusy = false;

    public String byteToString(byte[] dataArr){
        isBusy = true;
        StringBuilder sb = new StringBuilder();
        for (byte data : dataArr) {
            sb.append(data);
        }
        isBusy = false;
        return sb.toString();
    }
    public String byteToStringDec(byte[] dataArr){
        isBusy = true;
        StringBuilder sb = new StringBuilder();
        for (byte data : dataArr) {
            sb.append(data);
        }
        isBusy = false;
        return sb.toString();
    }
}

//package lib.ubint.jp.commonlib.util;
//
//import java.util.StringTokenizer;
//
//import com.google.android.gms.maps.model.LatLng;
//
///**
// * Map関係の有用な機能を提供するクラスです。
// */
//public class MapUtil {
//    /**
//     * タグ
//     */
//    @SuppressWarnings("unused")
//    private static final String TAG = MapUtil.class.getSimpleName();
//
//    /**
//     * 日本測地系から世界測地系に変換します。
//     *
//     * @param loc 位置情報
//     * @return 位置情報
//     */
//    public static LatLng getWorldLatLng(String lat, String lng) {
//        double lat1 = changeLLFormat(lat);
//        double lon1 = changeLLFormat(lng);
//        double latitude = lat1 - lat1 * 0.00010695 + lon1 * 0.000017464 + 0.0046017;
//        double longitude = lon1 - lat1 * 0.000046038 - lon1 * 0.000083043 + 0.010040;
//        return new LatLng(latitude, longitude);
//    }
//
//    /**
//     * 60進表記(度・分・秒数値)から10進表記(小数点数値)に変換します。
//     *
//     * @param LatLng 位置情報
//     * @return 位置情報
//     */
//    public static double changeLLFormat(String LatLng) {
//        StringTokenizer st = new StringTokenizer(LatLng, ".");
//        String[] LatLngs = new String[4];
//        int i = 0;
//        while (st.hasMoreTokens()) {
//            String token = st.nextToken();
//            if (i == 0) {
//                if (token.charAt(i) == '+') {
//                    token = token.substring(1);
//                }
//            }
//            LatLngs[i++] = token;
//        }
//        return Double.parseDouble(LatLngs[0]) + (Double.parseDouble(LatLngs[1]) / 60) + (Double.parseDouble(LatLngs[2]) / 60 / 60);
//    }
//
//    /**
//     * 移動開始位置情報から移動終了位置情報までの距離を返します。
//     *
//     * @param startLocation 移動開始位置情報
//     * @param endLocation   移動終了位置情報
//     * @return 移動距離
//     */
//    public static double getDistance(LatLng startLocation, LatLng endLocation) {
//        double lat1 = startLocation.latitude;
//        double lat2 = endLocation.latitude;
//        double lon1 = startLocation.longitude;
//        double lon2 = endLocation.longitude;
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//        double c = 2 * Math.asin(Math.sqrt(a));
//        return 6366000 * c;
//    }
//}
package shop.mtcoding.blogv2._core.util;

// 오버로딩
public class Script {

    // 경고창 + 뒤로가기
    public static String back(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        // 홑따옴표 안에 쌍따옴표 안에 ++안에 변수
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }

    // 이동
    public static String href(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("location.href='" + url + "';");
        // 홑따옴표 안에 쌍따옴표 안에 ++안에 변수 ('"+변수+"')
        sb.append("</script>");
        return sb.toString();
    }

    // 경고창 + 이동
    public static String href(String url, String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        // 홑따옴표 안에 쌍따옴표 안에 ++안에 변수
        sb.append("location.href='" + url + "';");
        sb.append("</script>");
        return sb.toString();
    }
}

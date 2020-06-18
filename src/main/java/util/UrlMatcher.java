package util;

public class UrlMatcher {
    private final String[] patterns;
    public UrlMatcher(String... patterns){
        this.patterns = patterns;
    }

    public boolean matches(String url){
        for (String pattern : patterns){

            if(url.matches(pattern)){
                return true;
            }
        }
        return false;
    }

    public static boolean isApi(String url){
        return url.matches("/api.*");
    }
}

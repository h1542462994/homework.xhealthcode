package dao;

public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    public ApiResponse(){}
    public ApiResponse(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ApiResponse<?> userNoPass(){
        return new ApiResponse<>(405, "用户未登录", null);
    }
    public static ApiResponse<?> fail(String msg) {
        return new ApiResponse<>(401, msg, null);
    }
}

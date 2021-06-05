package test.runtime;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockFactory {
    private String _markToken;

    public HttpServletRequest mockRequest(String token) {
        var request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{
                new Cookie("_token", token)
        });
        return request;
    }

    public HttpServletResponse mockResponse() {
        var response = Mockito.mock(HttpServletResponse.class);
        Mockito.doAnswer(invocation -> {
            Cookie cookie = invocation.getArgument(0);
            assertEquals("_token", cookie.getName());
            _markToken = cookie.getValue();
            return null;
        }).when(response).addCookie(Mockito.any());
        return response;
    }


    public String getMarkToken() {
        return _markToken;
    }

    public void setMarkToken(String markToken) {
        this._markToken = markToken;
    }
}

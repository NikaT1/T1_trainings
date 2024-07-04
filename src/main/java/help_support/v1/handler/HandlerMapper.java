package help_support.v1.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface HandlerMapper {
    void mapRequest(HttpServletRequest request, HttpServletResponse response) throws IOException;
}

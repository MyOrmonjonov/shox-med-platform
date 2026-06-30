package uz.shox.med.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * Request muvaffaqiyatli bajarildimi
     */
    private boolean success;

    /**
     * HTTP Status (200, 400, 404...)
     */
    private int status;

    /**
     * Foydalanuvchiga ko'rsatiladigan xabar
     */
    private String message;

    /**
     * Asosiy ma'lumot
     */
    private T data;

    /**
     * Validation xatolari
     */
    private List<String> errors;

    /**
     * API endpoint
     */
    private String path;

    /**
     * Har bir request uchun unikal ID
     */
    @Builder.Default
    private String traceId = UUID.randomUUID().toString();

    /**
     * Response vaqti
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();


    // ================= SUCCESS =================

    public static <T> ApiResponse<T> success(int status, String message, T data) {

        return ApiResponse.<T>builder()
                .success(true)
                .status(status)
                .message(message)
                .data(data)
                .build();

    }

    public static <T> ApiResponse<T> success(String message, T data) {

        return success(200, message, data);

    }

    public static <T> ApiResponse<T> success(T data) {

        return success(200, "Success", data);

    }

    public static <T> ApiResponse<T> success(String message) {

        return success(200, message, null);

    }


    // ================= ERROR =================

    public static <T> ApiResponse<T> error(int status,
                                           String message,
                                           List<String> errors,
                                           String path) {

        return ApiResponse.<T>builder()
                .success(false)
                .status(status)
                .message(message)
                .errors(errors)
                .path(path)
                .build();

    }

    public static <T> ApiResponse<T> error(String message) {

        return ApiResponse.<T>builder()
                .success(false)
                .status(400)
                .message(message)
                .build();

    }

}
//package com.example.hrms_platform_document.service;
//
//
//import com.example.config.SupabaseProperties;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//@Service
//public class SupabaseStorageService {
//
//    private static final String BUCKET = "hrms-documents";
//
//    private final SupabaseProperties props;
//
//    public SupabaseStorageService(SupabaseProperties props) {
//        this.props = props;
//    }
//
//    // Upload file to Supabase Storage
//    public String upload(MultipartFile file, String path) throws Exception {
//
//        String uploadUrl =
//                props.getUrl()
//                        + "/storage/v1/object/"
//                        + BUCKET + "/"
//                        + path;
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(uploadUrl))
//                .header("Authorization",
//                        "Bearer " + props.getServiceRoleKey())
//                .header("Content-Type", file.getContentType())
//                .PUT(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
//                .build();
//
//        HttpClient.newHttpClient()
//                .send(request, HttpResponse.BodyHandlers.discarding());
//
//        return path;
//    }
//
//    // Generate signed URL for download
//    public String generateSignedUrl(String path, int expirySeconds)
//            throws Exception {
//
//        String signUrl =
//                props.getUrl()
//                        + "/storage/v1/object/sign/"
//                        + BUCKET + "/"
//                        + path
//                        + "?expiresIn=" + expirySeconds;
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(signUrl))
//                .header("Authorization",
//                        "Bearer " + props.getServiceRoleKey())
//                .POST(HttpRequest.BodyPublishers.noBody())
//                .build();
//
//        HttpResponse<String> response =
//                HttpClient.newHttpClient()
//                        .send(request, HttpResponse.BodyHandlers.ofString());
//
//        // Response looks like: {"signedURL":"/storage/v1/object/public/..."}
//        String signedPath =
//                response.body().split("\"signedURL\":\"")[1]
//                        .split("\"")[0];
//
//        return props.getUrl() + signedPath;
//    }
//}

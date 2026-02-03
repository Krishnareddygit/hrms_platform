package com.example.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupabaseProperties {

    // 🔹 Read directly from ENV (no application.properties needed)
    private final String url = System.getenv("SUPABASE_URL");

    // 🔹 Already present in application.properties
    @Value("${supabase.service-role-key}")
    private String serviceRoleKey;

    public String getUrl() {
        if (url == null || url.isBlank()) {
            throw new IllegalStateException(
                    "SUPABASE_URL environment variable is not set"
            );
        }
        return url;
    }

    public String getServiceRoleKey() {
        return serviceRoleKey;
    }

    // 🔍 Optional debug (remove later)
    @PostConstruct
    public void debug() {
        System.out.println("Supabase URL = " + url);
        System.out.println("Supabase Service Role Key loaded = "
                + (serviceRoleKey != null));
    }
}

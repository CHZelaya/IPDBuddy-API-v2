package beto.projects.ipdbuddyapiv2.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Map;

public class JwtDebugger {

    public static void debugToken(String jwt) {
        try {
            // Split the token into parts: header.payload.signature
            String[] parts = jwt.split("\\.");
            if (parts.length < 2) {
                System.out.println("Invalid JWT format.");
                return;
            }

            // Decode the header (Base64Url)
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            System.out.println("==== JWT Header ====");
            System.out.println(headerJson);

            // Optionally parse it into a Map to extract 'kid'
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> headerMap = mapper.readValue(headerJson, Map.class);

            if (headerMap.containsKey("kid")) {
                System.out.println("Key ID (kid): " + headerMap.get("kid"));
            } else {
                System.out.println("No 'kid' field found in the header.");
            }

        } catch (Exception e) {
            System.out.println("Error decoding JWT: " + e.getMessage());
        }
    }
}
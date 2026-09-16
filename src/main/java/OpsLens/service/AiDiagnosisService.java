package OpsLens.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiDiagnosisService {

    private final RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/" +
                    "gemini-3.6-flash:generateContent?key=";

    public AiDiagnosisService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getDiagnosis(
            String serviceName,
            String metricName,
            double anomalousValue,
            double averageValue) {

        try {

            // Create the prompt for Gemini
            String prompt = String.format(
                    "An anomaly was detected in service '%s'. " +
                            "The metric '%s' reported a value of %.2f, " +
                            "which significantly deviates from the recent " +
                            "average of %.2f. " +
                            "Provide a brief technical diagnosis of what might " +
                            "be causing this anomaly and what action should be taken. " +
                            "Keep the response under 80 words.",
                    serviceName,
                    metricName,
                    anomalousValue,
                    averageValue
            );

            // Create the "parts" section
            Map<String, Object> part = new HashMap<>();
            part.put("text", prompt);

            // Create the "content" section
            Map<String, Object> content = new HashMap<>();
            content.put("parts", List.of(part));

            // Create the complete Gemini request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", List.of(content));

            // Set HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Combine headers + request body
            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(requestBody, headers);

            // Send request to Gemini
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            GEMINI_URL + apiKey,
                            request,
                            Map.class
                    );

            // Get response body
            Map body = response.getBody();

            if (body == null) {
                return "Diagnosis unavailable: Empty response from Gemini";
            }

            // Extract candidates
            List candidates = (List) body.get("candidates");

            if (candidates == null || candidates.isEmpty()) {
                return "Diagnosis unavailable: No Gemini response generated";
            }

            // Get first candidate
            Map firstCandidate = (Map) candidates.get(0);

            // Get content
            Map responseContent =
                    (Map) firstCandidate.get("content");

            if (responseContent == null) {
                return "Diagnosis unavailable: Gemini response has no content";
            }

            // Get response parts
            List responseParts =
                    (List) responseContent.get("parts");

            if (responseParts == null || responseParts.isEmpty()) {
                return "Diagnosis unavailable: Gemini response has no parts";
            }

            // Get first part
            Map firstPart =
                    (Map) responseParts.get(0);

            // Get actual AI-generated text
            String diagnosis =
                    (String) firstPart.get("text");

            if (diagnosis == null || diagnosis.isBlank()) {
                return "Diagnosis unavailable: Gemini returned empty text";
            }

            return diagnosis;

        } catch (Exception e) {

            return "Diagnosis unavailable: " + e.getMessage();
        }
    }
}
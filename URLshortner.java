import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class URLShortener {

    // In-memory storage for long-to-short URL mapping
    private static final Map<String, String> urlMapping = new HashMap<>();
    private static final String BASE_URL = "http://short.ly/";
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new Random();

    // Function to shorten a long URL
    public static String shortenURL(String longURL) {
        // Check if the long URL is already mapped
        if (urlMapping.containsKey(longURL)) {
            return urlMapping.get(longURL); // Return the existing short URL
        }

        // Generate a new unique short URL
        String shortURL = generateShortURL();

        // Ensure no collision in the short URL
        while (urlMapping.containsValue(shortURL)) {
            shortURL = generateShortURL();
        }

        // Store the long URL with its corresponding short URL
        urlMapping.put(longURL, BASE_URL + shortURL);

        return BASE_URL + shortURL;
    }

    // Function to expand a short URL to its original long URL
    public static String expandURL(String shortURL) {
        // Extract the unique short URL part from the full short URL
        String shortCode = shortURL.replace(BASE_URL, "");

        // Search for the long URL
        for (Map.Entry<String, String> entry : urlMapping.entrySet()) {
            if (entry.getValue().endsWith(shortCode)) {
                return entry.getKey(); // Return the original long URL
            }
        }

        // If not found, return an error message
        return "Error: Short URL does not exist or is invalid!";
    }

    // Helper function to generate a random short URL code
    private static String generateShortURL() {
        StringBuilder shortURL = new StringBuilder();
        for (int i = 0; i < 6; i++) { // Short URL length is 6 characters
            int index = RANDOM.nextInt(CHARACTERS.length());
            shortURL.append(CHARACTERS.charAt(index));
        }
        return shortURL.toString();
    }

    // A simple main method for user interaction
    public static void main(String[] args) {
        System.out.println("URL Shortener System");

        // Example usage of the shorten and expand functions
        String longURL1 = "https://www.example.com";
        String longURL2 = "https://www.openai.com";
        
        // Shorten URLs
        String shortURL1 = shortenURL(longURL1);
        String shortURL2 = shortenURL(longURL2);
        
        // Print the short URLs
        System.out.println("Shortened URL for 'https://www.example.com': " + shortURL1);
        System.out.println("Shortened URL for 'https://www.openai.com': " + shortURL2);
        
        // Expand URLs
        System.out.println("Expanded URL for " + shortURL1 + ": " + expandURL(shortURL1));
        System.out.println("Expanded URL for " + shortURL2 + ": " + expandURL(shortURL2));

        // Test error handling with an invalid short URL
        String invalidShortURL = "http://short.ly/invalid";
        System.out.println("Expanded URL for " + invalidShortURL + ": " + expandURL(invalidShortURL));
    }
}

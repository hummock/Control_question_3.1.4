package web.spring314v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestClient {

    static String URL = "http://91.241.64.178:7081/api/users";
    static RestTemplate restTemplate = new RestTemplate();
    static String resultHeader = "";

    public static void main(String[] args) {
        SpringApplication.run(RestClient.class, args);
        String cookie = getCookie();
        System.out.println("///////////////////////");
        System.out.println("Cookie--> " + cookie);
        System.out.println("----------------");

        String header1 = saveUser(cookie);
        resultHeader += header1;
        System.out.println("header1--> " + header1);
        System.out.println("----------------");

        String header2 = updateUser(cookie);
        resultHeader += header2;
        System.out.println("header2--> " + header2);
        System.out.println("----------------");

        String header3 = deleteUser(cookie);
        resultHeader += header3;
        System.out.println("header3--> " + header3);
        System.out.println("----------------");

        System.out.println("resultHeader--> " + resultHeader);
        System.out.println("----------------");

    }

    private static String getCookie() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        String cookie = response.getHeaders().getFirst("set-cookie");
        return cookie;
    }

    private static String saveUser(String cookie) {
        User user = new User(3L, "James", "Brown", (byte) 40);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, request, String.class);
        return response.getBody();
    }

    private static String updateUser(String cookie) {
        User userUpdate = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<User> request = new HttpEntity<>(userUpdate, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, request, String.class);
        return response.getBody();
    }

    private static String deleteUser(String cookie) {
        String tempURL = URL + "/3";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(tempURL, HttpMethod.DELETE, request, String.class);
        return response.getBody();
    }


}

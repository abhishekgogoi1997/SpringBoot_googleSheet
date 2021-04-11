package com.example.demo.projectcontroller;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;



@SuppressWarnings("deprecation")
@Controller
public class HomePageControl {
	private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final String APPLICATION_NAME = "Google Sheets Example";
	
	private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	private static final String USER_IDENTIFIER = "USER";
	
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
	
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	
	@Value("${google.oauth.callback.uri}")
	private String CALLBACK_URI;
	
	@Value("${google.secret.key.path}")
	private Resource gdSecretKeys;
	
//	@Value("${google.credentials.folder.path}")
//	private static Resource credentialsFolder;
	
	private static GoogleAuthorizationCodeFlow flow;
	
	@PostConstruct
	 public static Credential authorize()  throws IOException , GeneralSecurityException {
		InputStream in = HomePageControl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		
		 if (in == null) {
	            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
	        }
		
		 GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY,  new InputStreamReader(in));
//		 File tokenFolder = new File(Environment.getExternalStorageDirectory() +
//		            File.separator + TOKENS_DIRECTORY_PATH);
//		    if (!tokenFolder.exists()) {
//		        tokenFolder.mkdirs();
//		    }
		flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, secrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
		LocalServerReceiver receiver= new LocalServerReceiver.Builder().setPort(8888).build();
		Credential cred = new AuthorizationCodeInstalledApp(flow, receiver).authorize(USER_IDENTIFIER);
		return cred;
		
	}
	@GetMapping(value= {"/"})
	
	public String showHomePage() throws Exception{
//		boolean isUserAuthenticated = false;
//		Credential cred = flow.loadCredential(USER_IDENTIFIER);
//		if(cred!=null) {
//			boolean tokenValid = cred.refreshToken();
//			if(tokenValid) {
//				isUserAuthenticated=true;
//			}
//		}
//		return isUserAuthenticated?"index.html":"dashboard.html";
		return "index.html";
	}
	
	private static final String path = "/listfiles";
	
	@GetMapping(path)
		
	public @ResponseBody Object listfiles() throws GeneralSecurityException, IOException{
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Credential cred = authorize();
		Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
                .setApplicationName(APPLICATION_NAME)
                .build();
		
		final String spreadsheetId = "15FJWNCEM0SRCrblcV62BYHhzvB-bgnbdbrb03d9RPwY";
		final String range = "Txn Data!A2:E4";
		
		 ValueRange response = service.spreadsheets().values()
	                .get(spreadsheetId, range)
	                .execute();
		 Object responseList = new ArrayList<>();
		 
		 List<List<Object>> values = response.getValues();
	        if (values == null || values.isEmpty()) {
	            System.out.printf("Data not found");
	            return null;
	        } else {
	            System.out.println("Name, Lar_tsc");
	            for (List row : values) {
	                // Print columns A and E, which correspond to indices 0 and 4.
	            	System.out.printf("%s, %s\n", row.get(0), row.get(4));
	            	responseList = row.get(0); 	              
	            }
	       return responseList;
	            
	}


	}
}

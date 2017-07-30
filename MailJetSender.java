import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Email;

public class MailJetSender {
	
	static String APIKEY = "";
	static String PRIVATEKEY = "";
	static String FROM = "chiheb.design@gmail.com";
	

	public static  void sendV3(String title ,String subject ,String text , String html ,JSONArray to,JSONArray files ) throws MailjetException, MailjetSocketTimeoutException{
		
		  MailjetClient client;
	      MailjetRequest request;
	      MailjetResponse response;
	      
	      client = new MailjetClient(APIKEY, PRIVATEKEY);
	      
	      request = new MailjetRequest(Email.resource)
	                        .property(Email.FROMEMAIL, FROM)
	                        .property(Email.FROMNAME, title)
	                        .property(Email.SUBJECT, subject);
	                        
	      if(text != null){
	    	 request.property(Email.TEXTPART, text);
	      }
	      
	      if (html != null){
	    	 request.property(Email.HTMLPART, html);
	      }
	      
	      if(files != null) {
             request.property(Email.ATTACHMENTS, files );
	      }
	      request.property(Email.RECIPIENTS, to);
          response = client.post(request);
	      System.out.println(response.getData());
	    
	}
	
	// read the file into 64 bit encoded string
	private static String readfile(String filename) throws IOException{
		   File fff = new File(filename);
		   FileInputStream fileInputStream = new FileInputStream(fff);
		   int byteLength=(int) fff.length(); 
		   byte[] filecontent = new byte[byteLength];
		   fileInputStream.read(filecontent,0,byteLength);
		   byte[] encoded = Base64.getEncoder().encode(filecontent);

		return new String(encoded);

	}
	
	
	
	
	public static void main(String [] args) throws MailjetException, MailjetSocketTimeoutException, IOException
	{
		
		

		JSONArray to = new JSONArray();
		to.put(new JSONObject().put("Email", "chiheb.design@gmail.com"));
        
		
		JSONArray files = new JSONArray();	
		String filename = "you file path";
		String result = readfile(filename);
		String mime = Files.probeContentType(Paths.get(filename));
		
		files.put(new JSONObject()
                      .put("Content-type",mime )
                      .put("Filename", "test.pdf")
                      .put("content",result)
                );
		
		
		sendV3("titre" ,"object - sendToManyV3" ,"text" , null ,to ,files );
	
		
	}
}

package com.xvg.rest;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sound.midi.Track;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;

@Path("/")
public class XvgStarter {

	MongoClient mongo;
	DB db;
	DBCollection table;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Calendar cal = Calendar.getInstance();
	static boolean flag = true;
	/* Find your sid and token at twilio.com/user/account */
    public static final String ACCOUNT_SID = "AC8568c4a1dcd6c0d0f95824e1fbf49bdb";
    public static final String AUTH_TOKEN = "f97f19c26ef439ff662910d2d7459b6a";
    
	@GET
	@Path("")
	public Response getMsg() {
		mongoDbSetUp();
		String body = mongoDbGetData();
		return Response.status(200).entity(body).build();

	}

	@GET
	@Path("/sendsms")
	public Response sendSMS() {
		/*String body = "Initialised";
		try{
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		 
        Account account = client.getAccount();
 
        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", "9873136398")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", "7838275450")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("Body", "Where's Bhavuk?"));
        Message sms = messageFactory.create(params);
        body = "SMS Sent";
		}catch(Exception e){
			e.printStackTrace();
			body = "SMS Not Sent";
		}
		return Response.status(200).entity(body).build();
*/	return Response.status(200).entity("sms sent to Gurinder").build();
	}

	
	
	private String mongoDbGetData() {
		// TODO Auto-generated method stub
		DBCursor cursorDoc = table.find();
		JSON json = new JSON();
		String serialize = json.serialize(cursorDoc);
		return serialize;
	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	 public Response getContext(Track track) {
		String result = "Track saved : " + track;
		System.out.println(result);
		
		return Response.status(201).entity(result).build();
		
			 }
	private void mongoDbSetUp() {
		try {
			mongo = new MongoClient("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mongo.dropDatabase("xvg");
		db = mongo.getDB("xvg");
		table = db.getCollection("user");

		BasicDBObject document1 = new BasicDBObject();
		document1.put("name", "Gurinder");
		document1.put("id", 23);
		document1.put("contact", "9873136398");
		document1.put("last_seen", dateFormat.format(cal.getTime()));
		document1.put("loc",
				new BasicDBObject("lat", 28.592140).append("lon", 77.046048));

		BasicDBObject document2 = new BasicDBObject();
		document2.put("name", "Prachi");
		document2.put("id", 24);
		document2.put("contact", "9910922122");
		document2.put("last_seen", dateFormat.format(cal.getTime()));
		document2.put("loc",
				new BasicDBObject("lat", 28.445491).append("lon", 77.312407));

		BasicDBObject document3 = new BasicDBObject();
		document3.put("name", "Rahul");
		document3.put("id", 25);
		document3.put("contact", "9910922111");
		document3.put("last_seen", dateFormat.format(cal.getTime()));
		document3.put("loc",
				new BasicDBObject("lat", 28.473213).append("lon", 77.018917));

		BasicDBObject document4 = new BasicDBObject();
		document4.put("name", "Bhavuk");
		document4.put("id", 26);
		document4.put("contact", "9910922111");
		document4.put("last_seen",dateFormat.format(cal.getTime()));

		List<DBObject> documents = new ArrayList<DBObject>();
		documents.add(document1);
		documents.add(document2);
		documents.add(document3);
		documents.add(document4);
		document4.put("loc",
				new BasicDBObject("lat", 28.459497).append("lon", 77.026638));

		table.insert(documents);

	}

}
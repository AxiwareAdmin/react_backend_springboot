package com.accurate.erp.utility.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class SendMail {
	
/*public boolean SendMails(String tomail , String subject , String body) {*/
	public static void main(String[] args) {
		
	  	boolean flag = false;
	 //Declare recipient's & sender's e-mail id.
    String destmailid = "axiware.technologies@gmail.com";
    String sendrmailid = "rajgudesachin1997@gmail.com";	  
  /* //Mention user name and password as per your configuration
    final String uname = "rajgudesachin1997@gmail.com";
    final String pwd = "msisorbkfleeuhro";*/
   /* String smtphost = "smtp.gmail.com";
   //Set properties and their values
    Properties propvls = new Properties();
    propvls.put("mail.smtp.auth", "true");
    propvls.put("mail.smtp.starttls.enable", "true");
    propvls.put("mail.smtp.host", smtphost);
    propvls.put("mail.smtp.port", "25");*/
    //Create a Session object & authenticate uid and pwd
    /*Session sessionobj = Session.getInstance(propvls,
       new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication(uname, pwd);
	   }
       });*/
    
   // SendMail ss = new SendMail();
   //System.out.println(ss.SendMails(sendrmailid,destmailid));
    /*try {
	   //Create MimeMessage object & set values
	   Message messageobj = new MimeMessage(sessionobj);
	   messageobj.setFrom(new InternetAddress(sendrmailid));
	   messageobj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destmailid));
	   messageobj.setSubject("Invoice number 123");
	   messageobj.setText("dear sir invoice is created");
	  //Now send the message
	   Transport.send(messageobj);
	   flag = true;
	   System.out.println("Your email sent successfully....");
    } catch (MessagingException exp) {
    	flag = false;
       throw new RuntimeException(exp);
    }*/
   // return flag;
	}
	
	public boolean SendMails(String tomail , String subject , String body) {
		
		boolean flag = false;
		try {
			
			 //Mention user name and password as per your configuration
		    final String uname = "rajgudesachin1997@gmail.com";
		    final String pwd = "msisorbkfleeuhro";
			
			 String smtphost = "smtp.gmail.com";
			   //Set properties and their values
			    Properties propvls = new Properties();
			    propvls.put("mail.smtp.auth", "true");
			    propvls.put("mail.smtp.starttls.enable", "true");
			    propvls.put("mail.smtp.host", smtphost);
			    propvls.put("mail.smtp.port", "25");
			
			Session sessionobj = Session.getInstance(propvls,
				       new javax.mail.Authenticator() {
				          protected PasswordAuthentication getPasswordAuthentication() {
				             return new PasswordAuthentication(uname, pwd);
					   }
				       });
			   //Create MimeMessage object & set values
			 
			   Message messageobj = new MimeMessage(sessionobj);
			   messageobj.setFrom(new InternetAddress(uname));
			   messageobj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(tomail));
			   messageobj.setSubject(subject);
			   //messageobj.setText(body);
			   messageobj.setContent(body,"text/html");
			  //Now send the message
			   Transport.send(messageobj);
			   flag = true;
			   System.out.println("Your email sent successfully....");
		    } catch (MessagingException exp) {
		       throw new RuntimeException(exp);
		    }
		return flag;
	}

}
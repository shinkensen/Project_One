package hello;
import java.time.LocalDate;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
public class App {
    public static String day(String num){
        int num2=Integer.parseInt(num);
        if (num2>3){
            String ret=num2+"th";
            return ret;
        }
        if (num2==3){
            String ret=num2+"rd";
            return ret;
        }
        else if (num2==2){
            String ret=num2+"nd";
            return ret;
        }
        else{
            String ret=num2+"st";
            return ret;
        }
    }
    public static String month(String s){
        int s1=Integer.parseInt(s);
        if (s1==1){
            return "January";
        }
        if (s1==2){
            return "February";
        }
        if (s1==3){
            return "March";
        }
        if (s1==4){
            return "April";
        }
        if (s1==5){
            return "May";
        }
        if (s1==6){
            return "June";
        }
        if (s1==7){
            return "July";
        }
        if (s1==8){
            return "August";
        }
        if (s1==9){
            return "September";
        }
        if (s1==10){
            return "October";
        }
        if (s1==11){
            return "November";
        }
        if (s1==12){
            return "December";
        }
        else{
            return "Error: Issue with Date/Month";
        }
    }

    public static void main(String[] args) {
        // Replace with your credentials and recipient
        final String fromEmail = "leetocoda@gmail.com";//if you want a different sender email adress, use that with its App Password
        final String password = "qorz ntaq fzht phqg";
        final String toEmail = "noobywinner587@gmail.com";//set this to your email

        // This is just for gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // What the message actually says goes here, just keep this part please
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            LocalDate today = LocalDate.now();
            String s=(today.toString()).substring(5, 7);
            String date=month(s)+" "+day((today.toString()).substring(8, 10));
            message.setSubject("Reminder to do the Daily Problem!( "+date+" )");
            message.setText("Hello, this is your daily reminder to do the daily leetcode problem!\n\nToday's LeetCode Problem: \n"+ LeetCodeDaily.DLP());
            String problemTitle = LeetCodeDaily.DLP();
            String problemLink = "https://leetcode.com/problemset/all/"; // or build from titleSlug if available
            String delist = "https://monkeytype.com/";
            message.setText("Hello, this is your daily reminder to do the daily leetcode problem!\n\nToday's LeetCode Problem: \n"+ LeetCodeDaily.DLP()+"\nLink: "+ problemLink+"\n\nOther tools that can help you today:\nMonkeyType: "+delist+"\nW3 Schools: https://w3schools.org/");
            // Sending:
            Transport.send(message);
            System.out.println("✅ Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //plans for future: make it so that this runs automatically in github actions, after that, make it so that it comes off a .json database that stores user gmails and sends the message
        //to their inbox every day at like 9:30 or 8. Then make it so that a google form api can make a pull request to add an email specified by the google doc to the database so that people can add the gmails and subscribe to this newsletter.
    }
}

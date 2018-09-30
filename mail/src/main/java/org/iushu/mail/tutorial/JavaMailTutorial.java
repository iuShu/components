package org.iushu.mail.tutorial;

import com.sun.mail.imap.DefaultFolder;
import com.sun.mail.imap.IMAPMessage;

import javax.mail.*;
import javax.mail.event.ConnectionAdapter;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.FolderAdapter;
import javax.mail.event.FolderEvent;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by iuShu on 18-9-29
 */
public class JavaMailTutorial {

    public static final int HOLD_MESSAGES_OR_FILES = Folder.HOLDS_MESSAGES + Folder.HOLDS_FOLDERS;

    private static final String DEFAULT_FOLDER = "inbox";

    public static void main(String[] args) throws Exception {
        String username = "HentonWu128@outlook.com";
        String password = "58CSM@outlook";
        Properties props = new Properties();
//        props.put("mail.debug", "true");

        Authenticator auth = new TutorialAuthenticator(username, password);
        URLName urlName = new URLName("imaps://smtp-mail.outlook.com/");    // imap: not login  imaps: login with ssl
        Session session = Session.getDefaultInstance(props, auth);
        Store store = session.getStore(urlName);

        store.connect();    // A0 CAPABILITY A1 AUTHENTICATE A2 CAPABILITY

        DefaultFolder folder = (DefaultFolder) store.getDefaultFolder();
        folder.addConnectionListener(new TutorialConnectionListener());
        folder.addFolderListener(new TutorialFolderListener());

        Folder[] folders = folder.list();   // A3 LIST
        Folder inbox = null;
        for (Folder f : folders) {
            if (DEFAULT_FOLDER.equalsIgnoreCase(f.getName())) {
                inbox = f;
                break;
            }
        }

        // A4 STATUS
        System.err.println(">>> url: " + inbox.getURLName());
        System.err.println(">>> type: " + inbox.getType());
        System.err.println(">>> count: " + inbox.getMessageCount());

        // A5 EXAMINE
        inbox.open(Folder.READ_ONLY);   // for more further operations, open Folder is necessary.

        // A6 FETCH A7 FETCH A8 FETCH
        Message[] messages = inbox.getMessages();
        for (Message message : messages) {
            IMAPMessage msg = (IMAPMessage) message;
            System.out.println(">>>");
            System.out.println(">>> sender: " + msg.getSender());
            System.out.println(">>> subject: " + msg.getSubject());
            System.out.println(">>> date: " + msg.getSentDate());
            System.out.println(">>> recipient: " + msg.getAllRecipients()[0].toString());
            System.out.println(">>> body: " + msg.getContent());
            System.out.println(">>> desc: " + msg.getDescription());
            System.out.println(">>> encoding: " + msg.getEncoding());
            System.out.println(">>> filename: " + msg.getFileName());
        }

        if (inbox.isOpen())
            inbox.close();
        if (folder.isOpen())
            folder.close();

//        details();
    }

    private static void details() {
        System.out.println(">>> ------------------ IMAPMessage Fields -----------------------------");
        Field[] declaredFields = IMAPMessage.class.getDeclaredFields();
        Field[] fields = IMAPMessage.class.getFields();
        Arrays.stream(declaredFields).forEach(f -> System.out.println(">>> declared: " + f.getType().getName() + "\t" + f.getName()));
        Arrays.stream(fields).forEach(f -> System.out.println(">>> field: " + f.getType().getName() + "\t" + f.getName()));
        System.out.println(">>> -------------------------------------------------------------------");
    }

}

class TutorialAuthenticator extends Authenticator {

    private String username;
    private String password;

    public TutorialAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}

class TutorialConnectionListener extends ConnectionAdapter {

    @Override
    public void opened(ConnectionEvent e) {
        whatHappen(e);
    }

    @Override
    public void disconnected(ConnectionEvent e) {
        whatHappen(e);
    }

    @Override
    public void closed(ConnectionEvent e) {
        whatHappen(e);
    }

    private void whatHappen(ConnectionEvent e) {
        int type = e.getType();
        String event = "[unknown]";
        if (type == ConnectionEvent.OPENED)
            event = "[opened] ";
        else if (type == ConnectionEvent.DISCONNECTED)
            event = "[disconnected] ";
        else if (type == ConnectionEvent.CLOSED)
            event = "[closed] ";
        System.out.println(event + e.getSource());
    }
}

class TutorialFolderListener extends FolderAdapter {

    @Override
    public void folderCreated(FolderEvent e) {
        Folder folder = e.getFolder();
        System.out.println("[created] " + folder.getFullName());
    }

    @Override
    public void folderRenamed(FolderEvent e) {
        Folder folder = e.getFolder();
        System.out.println("[renamed] " + folder.getFullName());
    }

    @Override
    public void folderDeleted(FolderEvent e) {
        Folder folder = e.getFolder();
        System.out.println("[deleted] " + folder.getFullName());
    }
}
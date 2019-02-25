package com.shtohryn.bussinessObjects;

import com.shtohryn.entities.Message;
import com.shtohryn.entities.User;
import com.shtohryn.pages.DraftPage;
import com.shtohryn.pages.InboxPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailInboxBO {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private InboxPage inboxPage;
    private DraftPage draftPage;
    private User user = new User();
    private Message message = new Message();
    private static Logger logger = LogManager.getLogger(GmailInboxBO.class);

    public GmailInboxBO(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, 10);
        this.inboxPage = new InboxPage(webDriver);
        this.draftPage = new DraftPage(webDriver);
        this.user = user;
        this.message = message;
    }

    public void writeAndSaveMessage() {
        inboxPage.saveMessage(message.getMessage(), message.getEmail(), message.getSubject());
        logger.info("Message has been created and saved");
        inboxPage.enterDrafts();
        logger.info("Drafts page opened");
    }

    public void sendDraft() {
        draftPage.openDraft();
        draftPage.sendDraft();
        logger.info("Message has been sent");
    }

    public boolean isSent() {
        try {
            wait.until(ExpectedConditions.visibilityOf(inboxPage.getLableSentMessage()));
        } catch (Exception ex) {
            logger.error("ERROR! Message did not sent! TEST ENDING");
            return false;
        }
        return true;
    }
}

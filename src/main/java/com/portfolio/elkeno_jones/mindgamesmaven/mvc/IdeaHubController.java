package com.portfolio.elkeno_jones.mindgamesmaven.mvc;

import com.portfolio.elkeno_jones.mindgamesmaven.dao.FeatureDao;
import com.portfolio.elkeno_jones.mindgamesmaven.dao.IdeaDao;
import com.portfolio.elkeno_jones.mindgamesmaven.dao.UserDao;
import com.portfolio.elkeno_jones.mindgamesmaven.dao.UserDaoImpl;
import com.portfolio.elkeno_jones.mindgamesmaven.model.Feature;
import com.portfolio.elkeno_jones.mindgamesmaven.model.Idea;
import com.portfolio.elkeno_jones.mindgamesmaven.model.User;
import com.portfolio.elkeno_jones.mindgamesmaven.service.SecurityImpl;
import com.portfolio.elkeno_jones.mindgamesmaven.util.JavaMailer;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Elkeno
 */
@Controller
public class IdeaHubController {

    @Autowired
    private IdeaDao ideaDao;
    @Autowired
    private FeatureDao featureDao;
    @Autowired
    private SecurityImpl sec;
    @Autowired
    private UserDao userDao;

    private static final String ERROR_VIEW = "error";
    private static final String REDIRECT_VIEW = "/redirect";

    @RequestMapping(value = "/file", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> downloadTextResource(HttpServletRequest req,
            HttpServletResponse response,
            @RequestParam(value = "ideaId", required = true) Integer ideaId,
            @RequestParam(value = "fileName", required = false, defaultValue = "test") String fileName)
            throws IOException, Exception {

        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");

        if (!sec.checkAccess(token, userId)) {
            throw new Exception("Not a valid session - sign in to complete action");
        }
        Idea ideaToExport = ideaDao.getIdeaById(ideaId);
        if (!ideaToExport.getUserId().equals(userId)) {
            throw new Exception("Invalid user for idea!");
        }
        List<Feature> theFeatures = featureDao.getFeaturesByIdeaId(ideaId);
        List<String> lines = getLines(ideaToExport, theFeatures);

        String exportFileName = ideaToExport.getIdeaTitle() + ".txt";
        Path file = Paths.get(exportFileName);
        Files.write(file, lines, Charset.forName("UTF-8"));

        response.setContentType("text/plain");
        response.addHeader("Content-Disposition", "attachment; filename=" + exportFileName);
        try {
            Files.copy(file, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ResponseEntity("Failed creating file.", HttpStatus.FAILED_DEPENDENCY);
        }
        return new ResponseEntity("", HttpStatus.OK);
    }

    @RequestMapping(value = "/email", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> sendEmailResource(HttpServletRequest req,
            HttpServletResponse response,
            @RequestParam(value = "ideaId", required = true) Integer ideaId,
            @RequestParam(value = "emailTo", required = false, defaultValue = "") String emailTo,
            @RequestParam(value = "subject", required = false, defaultValue = "") String subject,
            @RequestParam(value = "emailBody", required = false, defaultValue = "") String emailBody)
            
            throws IOException, Exception {

        HttpSession ses = req.getSession();
        String token = (String) ses.getAttribute("userToken");
        Integer userId = (Integer) ses.getAttribute("userId");

        if (!sec.checkAccess(token, userId)) {
            throw new Exception("Not a valid session - sign in to complete action");
        }
        Idea ideaToExport = ideaDao.getIdeaById(ideaId);
        if (!ideaToExport.getUserId().equals(userId)) {
            throw new Exception("Invalid user for idea!");
        }
        List<Feature> theFeatures = featureDao.getFeaturesByIdeaId(ideaId);
        List<String> lines = getLines(ideaToExport, theFeatures);

        try {
            String exportFileName = ideaToExport.getIdeaTitle() + ".txt";
            Path file = Paths.get(exportFileName);
            Files.write(file, lines, Charset.forName("UTF-8"));

            if(subject.isEmpty()) {
                subject = ideaToExport.getIdeaTitle();
            }
            if(emailBody.isEmpty()) {
                emailBody = ideaToExport.getIdeaTitle();
            }
            
            if (emailTo.isEmpty()) {
                User user = userDao.getUserById(userId);
                if (user.getEmailAddress() != null && !user.getEmailAddress().isEmpty()) {
                    emailTo = user.getEmailAddress();
                } else {
                    return new ResponseEntity("No registered email found",
                            HttpStatus.BAD_REQUEST);
                }
            }

            ApplicationContext context
                    = new ClassPathXmlApplicationContext("Spring-Mail.xml");

            JavaMailer mm = (JavaMailer) context.getBean("javaMailer");
            mm.sendMailWithAttachment("norepley.ideaorganizer@gmail.com",
                    emailTo, subject, emailBody, file, exportFileName);

        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            return new ResponseEntity("Request failed", HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity("Email sent.", HttpStatus.OK);
    }

    protected List<String> getLines(Idea idea, List<Feature> features) {
        String lineBreak = "\n"; // gives one blank line
        String carriageReturn = "\r\n"; //gives to lines
        String line = "---------------------------------------------------------";
        String description = "Description";
        String featuresStr = "Features";
        String notAvailable = "Not Available";
        String statusStr = "Status";
        String created = "Created";
        String inProgressStr = "In Progress";
        String completeStr = "Compeleted";

        List<String> lines = new ArrayList<String>();

        StringBuilder heading = new StringBuilder(idea.getIdeaTitle());
        if (idea.getAlias() != null && !idea.getAlias().isEmpty()) {
            String alias = " (Alias: " + idea.getAlias() + ")";
            heading.append(alias);
        }
        lines.add(heading.toString());
        if (idea.getCreatedDate() != null && !idea.getCreatedDate().isEmpty()) {
            lines.add(created + ": " + idea.getCreatedDate());
        }
        String status = "";
        if (idea.isIsCompleted()) {
            status = completeStr;
        } else if (idea.isIsInProgress()) {
            status = inProgressStr;
        }
        lines.add(statusStr + ": " + status);
        lines.add(line);
        lines.add(description);
        if (idea.getDescriptionLong() == null
                || idea.getDescriptionLong().equals("")) {
            lines.add(notAvailable);
        } else {
            lines.add(idea.getDescriptionLong());
        }
        lines.add(lineBreak);
        lines.add(featuresStr);
        lines.add(line);
        for (Feature feat : features) {
            lines.add("- " + feat.getDescriptionShort());
            lines.add(feat.getDescriptionLong());
            lines.add(lineBreak);
        }

        return lines;
    }
}

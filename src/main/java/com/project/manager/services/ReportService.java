package com.project.manager.services;

import com.itextpdf.text.DocumentException;
import com.project.manager.entities.Project;
import com.project.manager.entities.Task;
import com.project.manager.exceptions.EmailValidationException;
import com.project.manager.models.Report;
import com.project.manager.models.mail.MailSubject;
import com.project.manager.models.mail.ProjectReportMail;
import com.project.manager.models.task.TaskReportModel;
import com.project.manager.repositories.ProjectRepository;
import com.project.manager.services.mail.MailFactory;
import com.project.manager.ui.AlertManager;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import com.project.manager.utils.PDFReportGenerator;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Service where is implemented all login necessary to generate report in pdf and sent them to isEmailValid or just save in
 * given localization
 */
@Log4j
@Service
public class ReportService {

    private MailFactory mailFactory;
    private SessionService sessionService;
    private SceneManager sceneManager;
    private ProjectRepository projectRepository;

    public ReportService(MailFactory mailFactory, ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.mailFactory = mailFactory;
        this.sessionService = SessionService.getInstance();
        this.sceneManager = SceneManager.getInstance();
    }

    /**
     * This is mail method which is responsible to ger prepared report object, generate pdf using previously
     * provided report object.
     * Then if some parameters have right value method sent report to provided isEmailValid, or to actual logged user or
     * create report file in set localization
     *
     * @param sendToMe this is value which is responsible to execute sending report to actual logged user
     * @param email    this value is isEmailValid of user which will get isEmailValid message with report is value isn't empty
     * @param path     this is path where application will save report pdf
     */
    public void generateReport(boolean sendToMe, String email, String path) throws EmailValidationException {
        String fileToDelete = "";
        try {
            FileOutputStream fos = null;
            if (shouldGenerateReport(sendToMe, email, path)) {
                Report report = prepareReport();
                PDFReportGenerator generator = new PDFReportGenerator();
                byte[] pdf = generator.createReportPDF(report);

                if (sendToMe || !email.isEmpty()) {
                    ProjectReportMail mail = new ProjectReportMail();
                    fos = new FileOutputStream(report.getTitle() + " Report.pdf");
                    fileToDelete = report.getTitle() + " Report.pdf";
                    fos.write(pdf);
                    mail = prepareMail(report, mail);
                    if (sendToMe) {
                        sentMailWithReport(sessionService.getUserModel().getEmail(), mail);
                    }
                    if (!email.isEmpty()) {
                        sentMailWithReport(email, mail);
                    }
                }
                if (!path.isEmpty()) {
                    fos = saveReportDocument(path, pdf);
                }
                refreshProjectObject(report.getActualDate());
                AlertManager.showInformationAlert("Success!", "Email was send.");
            }
            sceneManager.closeNewWindow(SceneType.GENERATE_REPORT);
            fos.close();
        } catch (DocumentException | IOException e) {
            AlertManager.showErrorAlert("Warning!", e.getMessage());
            log.error(e.getMessage());
        } finally {
            File file = new File(fileToDelete);
            file.delete();
        }
    }

    /**
     * This method is refreshing project last generated report date
     *
     * @param actualDate actual date to set in project last report date
     */
    private void refreshProjectObject(LocalDate actualDate) {
        Project project = sessionService.getProject();
        project.setLastRapportDate(actualDate);
        projectRepository.save(project);
    }

    /**
     * Function which save report in set localization
     *
     * @param path where will be saved report
     * @param pdf  file to save
     * @return {@link FileOutputStream } object to manager stream of data which contains pdf file
     * @throws IOException in case of file problems
     */
    private FileOutputStream saveReportDocument(String path, byte[] pdf) throws IOException {
        FileOutputStream fos;
        if (!path.contains(".pdf")) path = path.concat(".pdf");
        fos = new FileOutputStream(path);
        fos.write(pdf);
        fos.close();
        return fos;
    }

    /**
     * Method to send mails object to set isEmailValid in parameter
     *
     * @param email address where application will send mail with report
     * @param mail  object with report attachment
     */
    private void sentMailWithReport(String email, ProjectReportMail mail) throws EmailValidationException {
        mail.setRecipientName(email);
        mail.setRecipient(email);
        mailFactory.sendMail(mail);
    }

    /**
     * Method which prepare mail object with necessary date
     *
     * @param report object where is stored some data needed to prepare mail message
     * @param mail   original mail object which will be send to user
     * @return mail because this object will be send by {@link MailFactory } instance
     */
    private ProjectReportMail prepareMail(Report report, ProjectReportMail mail) {
        File file = new File(report.getTitle() + " Report.pdf");
        mail.setSubject(MailSubject.CUSTOMER_REPORT);
        mail.setProjectName(report.getTitle());
        if (Optional.ofNullable(report.getLastReportDate()).isPresent()) {
            mail.setStartDate(report.getLastReportDate().toString());
        } else {
            mail.setStartDate("This is first report of this project");
        }
        mail.setEndDate(report.getActualDate().toString());
        mail.setAttachment(file);
        return mail;
    }

    /**
     * Method to prepare report object from original project
     *
     * @return prepared report object
     */
    private Report prepareReport() {
        Project project = sessionService.getProject();
        List<List<TaskReportModel>> tasks = getTasks(project.getTasks());

        return Report.builder()
                .title(project.getProjectName())
                .lastReportDate(project.getLastRapportDate())
                .actualDate(LocalDate.now())
                .team(getTeam(project))
                .newTasks(tasks.get(0))
                .currentTasks(tasks.get(1))
                .doneTasks(tasks.get(2)).build();
    }

    /**
     * That method is responsible for organizing tasks from project in suitable order
     *
     * @param tasks this is set of tasks from original project
     * @return list which contains three lists of tasks in order { BACKLOG, ACTUAL, DONE}
     */
    private List<List<TaskReportModel>> getTasks(Set<Task> tasks) {
        List<List<TaskReportModel>> resultTasks = Arrays.asList(new ArrayList<TaskReportModel>(),
                new ArrayList<TaskReportModel>(),
                new ArrayList<TaskReportModel>());
        for (Task t : tasks) {
            TaskReportModel taskReportModel = TaskReportModel.convert(t);
            switch (taskReportModel.getTaskStatus()) {
                case PRODUCT_BACKLOG:
                    resultTasks.get(0).add(taskReportModel);
                    break;
                case DONE:
                    resultTasks.get(2).add(taskReportModel);
                    break;
                default:
                    resultTasks.get(1).add(taskReportModel);
                    break;
            }
        }
        return resultTasks;
    }

    /**
     * That method fetch manager and members from team and organizing them into suitable order
     *
     * @param project original object from database which will be used to generate report
     * @return list of members and manager, manager is always on first place in list
     */
    private List<String> getTeam(Project project) {
        List<String> team = new ArrayList<>();
        team.add(project.getManager().getFirstName() + " " + project.getManager().getLastName());
        project.getMembers().forEach(u -> team.add(u.getFirstName() + " " + u.getLastName()));
        return team;
    }

    /**
     * This method provides boolean value to decide that application should generate report
     *
     * @param sendToMe boolean value to decide that actual logged user want get report on his mail
     * @param email    value to send report file to provided user isEmailValid, it will happen when isEmailValid is not empty
     * @param path     to save report file in our computer
     * @return logic value to decide that application should generate report
     */
    private boolean shouldGenerateReport(boolean sendToMe, String email, String path) {
        return sendToMe || !email.isEmpty() || !path.isEmpty();
    }
}
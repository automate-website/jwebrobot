package website.automate.jwebrobot.report;

import website.automate.jwebrobot.report.model.ExecutionReport;

public interface ReportWriter {

    void writeReport(String reportPath, ExecutionReport report);
}

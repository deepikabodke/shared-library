import hudson.model.*
import hudson.tasks.test.AbstractTestResultAction
import jenkins.model.*

import java.text.DecimalFormat
import java.text.SimpleDateFormat

ArrayList<LinkedHashMap<String, String>> call() {
    return buildNotificationMessage()
}

ArrayList<LinkedHashMap<String, String>> buildNotificationMessage() {
    try {
        AbstractTestResultAction testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
        def build = currentBuild

        long startMillis = currentBuild.getStartTimeInMillis()
        int durationMillis = build.duration
        def format = new SimpleDateFormat('dd-MMM-YYYY hh:mm:ss a Z z')
        String startedAt = format.format(new Date(startMillis))

        long finishMillis = startMillis + durationMillis
        String completedAt = format.format(new Date(finishMillis))

        // Duration duration = Duration.between(Instant.ofEpochMilli(startMillis), Instant.ofEpochMilli(finishMillis));
        // readableDuration = duration.toString().substring(2).replaceAll(~'(\\d[HMS])(?!$)', '$1 ').toLowerCase();
        String readableDuration = build.getDurationString()
        String took = readableDuration.replace('and counting', '')

        ArrayList<LinkedHashMap<String, String>> facts = ["Started At", "${startedAt}", "\n Completed At", "${completedAt}", "\n Took", "${took}"]
        String branchName = env.GIT_BRANCH
        if (branchName) {
            facts.addAll([name: "Branch", template: branchName])
        }
        captureTestDetails(testResultAction, facts)
        return facts
    } catch (Exception exception) {
        echo "Error: Exception while Capturing Test Result metrics. Marking the build as UNSTABLE"
        exception.printStackTrace()
        return null
    }
}

void captureTestDetails(AbstractTestResultAction testResultAction, ArrayList<LinkedHashMap<String, String>> facts) {
    if (testResultAction != null) {
        DecimalFormat df = new DecimalFormat("##.##%")
        int totalTests = testResultAction.totalCount
        if (totalTests > 0) {
            int failed = testResultAction.failCount
            String failedDiff = testResultAction.failureDiffString
            int skipped = testResultAction.skipCount
            int passed = totalTests - failed - skipped
            String passedPercent = df.format(passed / totalTests)
            String failedPercent = df.format(failed / totalTests)
            String skippedPercent = df.format(skipped / totalTests)
            facts.addAll([[name: "Total Tests", template: "${totalTests}"],
                          [name: "Passed", template: "${passed}"],
                          [name: "Passed Percentage", template: "${passedPercent}"],
                          [name: "Failed", template: "${failed} ${failedDiff}"],
                          [name: "Failed Percentage", template: "${failedPercent}"]])
            if (skipped > 0) {
                facts.addAll([[name: "Skipped", template: "${skipped}"],
                              [name: "Skipped Percentage", template: "${skippedPercent}"]]);
            }
        }
    } else {
        facts.add([name: "Test Summary", template: "Test results unavailable"])
    }
}

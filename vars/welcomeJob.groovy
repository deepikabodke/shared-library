import hudson.tasks.test.AbstractTestResultAction
import hudson.model.Actionable


def call(String name = 'User') {
    echo "Welcome, ${name}."
    return buildNotification()
}

void buildNotification()
{
    AbstractTestResultAction testResultAction =  currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
    if (testResultAction != null) {
        def totalNumberOfTests = testResultAction.totalCount
        def failedNumberOfTests = testResultAction.failCount
        def failedDiff = testResultAction.failureDiffString
        def skippedNumberOfTests = testResultAction.skipCount
        def passedNumberOfTests = totalNumberOfTests - failedNumberOfTests - skippedNumberOfTests
        emailTestReport = "Tests Report:\n Passed: ${passedNumberOfTests}; Failed: ${failedNumberOfTests} ${failedDiff}; Skipped: ${skippedNumberOfTests}  out of ${totalNumberOfTests} "
    }
}

/*ArrayList<LinkedHashMap<String, String>> buildNotificationMessage() {
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

        ArrayList<LinkedHashMap<String, String>> facts = [[name: "Started At", template: "${startedAt}"],
                                                          [name: "Completed At", template: "${completedAt}"],
                                                          [name: "Took", template: "${took}"]
        ]

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
}*/
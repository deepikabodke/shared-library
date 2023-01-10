def call(Map<String, String> args) {
    catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
        notify(args)
    }

}

def notify(Map<String, String> args) {
// publish HTML report
        // gather test result metrics
        def testResultsNotificationFacts = getTestResults()
}